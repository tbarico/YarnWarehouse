package dev.tarico.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tarico.daos.ItemDAO;
import dev.tarico.daos.ItemDAOImplementation;
import dev.tarico.models.Item;

/**
 * Manages HTTP requests for item lists.
 * 
 * @author Tara Arico 10.4.2022
 */
@WebServlet(urlPatterns="/items/")
public class ItemListServlet extends HttpServlet {
    ItemDAO dao = new ItemDAOImplementation();
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Takes a GET to list all items. 
     * 
     * @param req - initial HTTP request.
     * @param resp - HTTP response with item data.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         try {
            List<Item> itemList = dao.findAll();
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().print(mapper.writeValueAsString(itemList));
        } catch(Exception e) {
            //silently fail for now
        }
    } 
}
