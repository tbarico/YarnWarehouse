package dev.tarico.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter that allows CrossOrigin requests.
 * 
 * @author Tara Arico 7.29.2022
 */
@WebFilter(urlPatterns = "/*")
public class CORS implements Filter {

    @Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}
    /**
     * Adds the appriopriate headers and sends the request on.
     * 
     * @param request - original request.
     * @param response - modified response.
     * @param chain - FilterChain to send the request on.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "*");
        resp.addHeader("Access-Control-Allow-Headers", "*");
        HttpServletRequest req = (HttpServletRequest) request;
        if(req.getMethod().equals("OPTIONS")) {
            resp.setStatus(202);
        }
        chain.doFilter(req, resp);       
    }
    
}
