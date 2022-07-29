package dev.tarico.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tarico.daos.LocationDAO;
import dev.tarico.daos.LocationDAOImplementation;
import dev.tarico.models.Location;
import dev.tarico.services.URLParser;

/**
 * Manages HTTP requests for all locations.
 * 
 * @author Tara Arico 7.29.2022
 */
@WebServlet(urlPatterns = "/locations/")
public class LocationsServlet extends HttpServlet {
    LocationDAO dao = new LocationDAOImplementation();
    URLParser parser = new URLParser();
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Takes a GET request for all locations. 
     * 
     * @param req - initial HTTP request
     * @param resp - HTTP response to with all available Location data 
     * 
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Location> locations = dao.findAll();
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().print(mapper.writeValueAsString(locations));
        } catch(Exception e) {
            //silently fail for now
        }
    }
}
