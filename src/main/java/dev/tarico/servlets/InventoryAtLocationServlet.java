package dev.tarico.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tarico.daos.InventoryDAO;
import dev.tarico.daos.InventoryDAOImplementation;
import dev.tarico.daos.LocationDAO;
import dev.tarico.daos.LocationDAOImplementation;
import dev.tarico.models.Inventory;
import dev.tarico.models.Location;
import dev.tarico.services.URLParser;
import dev.tarico.services.ValueChecker;

/**
 * Manages HTTP requests for inventory at a specific location.
 * 
 * @author Tara Arico 7.29.2022
 */
@WebServlet(urlPatterns = "/inventory/*")
public class InventoryAtLocationServlet extends HttpServlet {
    InventoryDAO dao = new InventoryDAOImplementation();
    LocationDAO ldao = new LocationDAOImplementation();
    URLParser parser = new URLParser();
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Takes a GET request the inventory at a given location. 
     * 
     * @param req - initial HTTP request.
     * @param resp - HTTP response with inventory data.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int locationId = parser.extractId(req.getPathInfo());
            List<Inventory> inventoryList = dao.findInventoryAtLocation(locationId);
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().print(mapper.writeValueAsString(inventoryList));
        } catch(Exception e) {
            //silently fail for now
        }
    }

    /**
     * Adds a row to the Inventory database using the HTTP body.
     * 
     * @param req - request with the data to add.
     * @param resp - appropriate response depending on whether or not
     *          the data was added successfully or not.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            InputStream reqBody = req.getInputStream();
            Inventory inventory = mapper.readValue(reqBody, Inventory.class);
            if (inventory.isValid()) {
                //verify location can handle amount
                Location location = ldao.findById(inventory.getLocationId());
                try {
                    ValueChecker.checkLocationQuantity(inventory.getQuantity(), location.getCurrentCapacity(), location.getTotalCapacity());
                } catch (RuntimeException e) {
                    resp.setStatus(400);
                    resp.getWriter().print(mapper.writeValueAsString("Unable to create Inventory. "+ e.getMessage()));
                    return;
                }
                //then check to see if item already exists at location
                Inventory existingInventory = dao.findItemAtLocation(inventory.getItemId(), inventory.getLocationId());
                boolean result = false;
                if(existingInventory == null) {
                    result = dao.addInventory(inventory);
                    if(result)
                        resp.getWriter().print(mapper.writeValueAsString("Inventory added to database."));
                } else {
                    result = dao.updateQuantity(existingInventory.getInventoryId(), inventory.getQuantity()+existingInventory.getQuantity());
                    if(result)
                        resp.getWriter().print(mapper.writeValueAsString("Inventory quantity updated in database."));
                }
                //update location's current capacity
                ldao.updateCurrentCapacity(inventory.getLocationId(), location.getCurrentCapacity()+inventory.getQuantity());
                if (result) {
                    resp.setContentType("application/json");
                    resp.setStatus(201); 
                } else {
                    resp.setStatus(400);
                    resp.getWriter().print(mapper.writeValueAsString("Unable to create Inventory."));
                }
            } else {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Unable to create Inventory."));
            }
        } catch (Exception e) {
            //silently fail for now
        }
    }

    /**
     * Updates a given row in the Inventory table.
     * 
     * @param req - request with the data to update.
     * @param resp - appropriate response depending on whether or not
     *          the data was updated successfully or not.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            InputStream reqBody = req.getInputStream();
            Inventory inventory = mapper.readValue(reqBody, Inventory.class);
            Inventory check = dao.findById(inventory.getInventoryId());
            //check to see if valid inventoryid and locationid
            if(check != null) {
                Location location = ldao.findById(inventory.getLocationId());
                if(location != null) {
                    //check if location can handle new quantity if appliciable
                    if (check.getQuantity() != inventory.getQuantity()) {
                        int quantity = inventory.getQuantity() - check.getQuantity();
                        if (quantity > 0) {
                            try {
                                ValueChecker.checkLocationQuantity(quantity, location.getCurrentCapacity(),
                                        location.getTotalCapacity());
                            } catch (RuntimeException e) {
                                resp.setStatus(400);
                                resp.getWriter().print(
                                        mapper.writeValueAsString("Unable to update Inventory. " + e.getMessage()));
                                return;
                            }
                        }
                        ldao.updateCurrentCapacity(inventory.getLocationId(), location.getCurrentCapacity()+quantity);
                    }

                    boolean result = dao.updateInventory(inventory);
                    if (result) {
                        resp.setContentType("application/json");
                        resp.setStatus(200); 
                    } else {
                        resp.setStatus(400);
                        resp.getWriter().print(mapper.writeValueAsString("Unable to update Inventory."));
                    }
                } else {
                    resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Unable to update Inventory. Invalid Location ID."));
                }
            } else {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Unable to update Inventory. Invalid Inventory ID."));
            }
        } catch (Exception e) {
            //silently fail for now
        }
    }

    /**
     * Deletes a given row in the Inventory table.
     * 
     * @param req - request with the data to delete.
     * @param resp - appropriate response depending on whether or not
     *          the data was deleted successfully or not.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            InputStream reqBody = req.getInputStream();
            Inventory inventory = mapper.readValue(reqBody, Inventory.class);
            boolean result = dao.removeInventory(inventory.getInventoryId());
            if(result) {
                resp.setStatus(200);
            } else {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Unable to delete Inventory."));
            }
        } catch (Exception e) {
            //silently fail for now
        }
    }
    
}
