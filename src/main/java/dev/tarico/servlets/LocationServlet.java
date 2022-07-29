package dev.tarico.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tarico.daos.LocationDAO;
import dev.tarico.daos.LocationDAOImplementation;
import dev.tarico.models.Location;
import dev.tarico.services.URLParser;

/**
 * Manages HTTP requests for a specific location. 
 * 
 * @author Tara Arico 7.29.2022
 */
@WebServlet(urlPatterns = "/location/*")
public class LocationServlet extends HttpServlet {

    LocationDAO dao = new LocationDAOImplementation();
    URLParser parser = new URLParser();
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Takes a GET request for a specific location. If no matching Location found,
     * redirects to LocationsServlet.
     * 
     * @param req - initial HTTP request
     * @param resp - appropriate HTTP response to send back with Location data (if appliciable)
     * 
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int locationId = parser.extractId(req.getPathInfo());
            Location location = dao.findById(locationId);
            if(location == null) {
                resp.setStatus(302);
                resp.sendRedirect("/yarn-warehouse/locations/");
            } else {
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().print(mapper.writeValueAsString(location));
            }
        } catch(Exception e) {
            resp.setStatus(302);
            resp.sendRedirect("/yarn-warehouse/locations/");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPut(req, resp);
    }
}
