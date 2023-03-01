package dev.tarico.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.tarico.daos.InventoryDAO;
import dev.tarico.daos.InventoryDAOImplementation;
import dev.tarico.models.Inventory;
import dev.tarico.models.Item;
import dev.tarico.services.URLParser;

/**
 * Manages HTTP requests a single Inventory.
 * 
 * @author Tara Arico 3.1.2023
 */
@WebServlet(urlPatterns = "/inventoryId/*")
public class InventoryServlet extends HttpServlet {
    InventoryDAO dao = new InventoryDAOImplementation();
    URLParser parser = new URLParser();
    ObjectMapper mapper = new ObjectMapper();
    
    /**
     * Takes a GET request for a specific inventory. 
     * 
     * @param req - initial HTTP request.
     * @param resp - HTTP response with inventory data.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int inventoryId = parser.extractId(req.getPathInfo());
            Inventory inventory = dao.findById(inventoryId);
            if(inventory == null) {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Invalid inventory Id."));
                return;
            } else {
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().print(mapper.writeValueAsString(inventory));
            }
        } catch(Exception e) {
            //silently fail for now
            System.out.println("I falied.");
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
            if(inventory.isValid()) {
                boolean result = dao.updateInventory(inventory);
                if(result) {
                    resp.setStatus(201);
                    resp.setContentType("application/json");
                    resp.getWriter().print(mapper.writeValueAsString(inventory));
                    return;
                } else {
                    resp.setStatus(400);
                    resp.getWriter().print(mapper.writeValueAsString("Unable to update inventory."));
                }
            } else {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Unable to update inventory."));
            }
        } catch(Exception e) {
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
            int inventoryId = parser.extractId(req.getPathInfo());
            Inventory inventory = dao.findById(inventoryId);
            boolean result = dao.removeInventory(inventoryId);
            if (result) {
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().print(mapper.writeValueAsString(inventory));
                return;
            } else {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Unable to remove inventory."));
            }
        } catch(Exception e) {
            //silently fail for now
        }
    }

}
