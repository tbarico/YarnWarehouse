package dev.tarico.servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tarico.daos.ItemDAO;
import dev.tarico.daos.ItemDAOImplementation;
import dev.tarico.models.Item;
import dev.tarico.services.URLParser;

/**
 * Manages HTTP requests for specific items.
 * 
 * @author Tara Arico 10.4.2022
 */
@WebServlet(urlPatterns="/item/*")
public class ItemServlet extends HttpServlet {

    ItemDAO dao = new ItemDAOImplementation();
    URLParser parser = new URLParser();
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Deletes a given row in the Item table.
     * 
     * @param req - request with the data to delete.
     * @param resp - appropriate response depending on whether or not
     *          the data was deleted successfully or not.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            InputStream reqBody = req.getInputStream();
            Item item = mapper.readValue(reqBody, Item.class);
            boolean result = dao.removeItem(item.getItemId());
            if (result) {
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().print(mapper.writeValueAsString(item));
                return;
            } else {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Unable to remove item."));
            }
        } catch(Exception e) {
            //silently fail for now
        }
    }

    /**
     * Takes a GET request for an item. 
     * 
     * @param req - initial HTTP request.
     * @param resp - HTTP response with item data.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int itemId = parser.extractId(req.getPathInfo());
            Item item = dao.findById(itemId);
            if(item == null) {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Invalid item Id."));
                return;
            } else {
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().print(mapper.writeValueAsString(item));
            }
        } catch(Exception e) {
            //silently fail for now
        }
    }

    /**
     * Adds a row to the Item table using the HTTP body.
     * 
     * @param req - request with the data to add.
     * @param resp - appropriate response depending on whether or not
     *          the data was added successfully or not.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            InputStream reqBody = req.getInputStream();
            Item item = mapper.readValue(reqBody, Item.class);
            if(item.isValid()) {
                boolean result = dao.addItem(item);
                if(result) {
                    resp.setStatus(201);
                    resp.setContentType("application/json");
                    resp.getWriter().print(mapper.writeValueAsString(item));
                    return;
                } else {
                    resp.setStatus(400);
                    resp.getWriter().print(mapper.writeValueAsString("Unable to create item."));
                }
            } else {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Unable to create item."));
            }
        } catch(Exception e) {
            //silently fail for now
        }
    }

    /**
     * Updates a given row in the Item table.
     * 
     * @param req - request with the data to update.
     * @param resp - appropriate response depending on whether or not
     *          the data was updated successfully or not.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            InputStream reqBody = req.getInputStream();
            Item item = mapper.readValue(reqBody, Item.class);
            if(item.isValid()) {
                boolean result = dao.updateItem(item);
                if(result) {
                    resp.setStatus(201);
                    resp.setContentType("application/json");
                    resp.getWriter().print(mapper.writeValueAsString(item));
                    return;
                } else {
                    resp.setStatus(400);
                    resp.getWriter().print(mapper.writeValueAsString("Unable to update item."));
                }
            } else {
                resp.setStatus(400);
                resp.getWriter().print(mapper.writeValueAsString("Unable to update item."));
            }
        } catch(Exception e) {
            //silently fail for now
        }
    }
}
