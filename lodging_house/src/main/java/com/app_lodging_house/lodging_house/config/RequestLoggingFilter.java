package com.app_lodging_house.lodging_house.config;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // LOG CADA REQUEST
        System.out.println("🌐 REQUEST RECIBIDA: " + req.getMethod() + " " + req.getRequestURI());
        System.out.println("   Origin: " + req.getHeader("Origin"));
        System.out.println("   User-Agent: " + req.getHeader("User-Agent"));

        // HEADERS DE CORS DIRECTAMENTE
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        res.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization, X-Requested-With");
        res.setHeader("Access-Control-Max-Age", "3600");

        // Manejar OPTIONS preflight
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            System.out.println("✅ MANEJANDO OPTIONS PREFLIGHT");
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }
}
