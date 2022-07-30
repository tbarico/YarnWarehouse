package dev.tarico.servlets;

import java.io.IOException;
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
}
