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
import dev.tarico.models.Inventory;
import dev.tarico.services.URLParser;

/**
 * Manages HTTP requests for inventory at a specific location.
 * 
 * @author Tara Arico 7.29.2022
 */
@WebServlet(urlPatterns = "/inventory/*")
public class InventoryAtLocationServlet extends HttpServlet {
    InventoryDAO dao = new InventoryDAOImplementation();
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
            System.out.println(inventory);
            if (inventory.isValid()) {
                boolean result = dao.addInventory(inventory);
                if (result) {
                    resp.setContentType("application/json");
                    resp.getWriter().print(mapper.writeValueAsString("Inventory added to database."));
                    resp.setStatus(201); // The default is 200
                } else {
                    resp.setStatus(400);
                    resp.getWriter().print(mapper.writeValueAsString("Unable to create Inventory."));
                }
            } else {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Unable to create Inventory."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPut(req, resp);
    }

    
}
