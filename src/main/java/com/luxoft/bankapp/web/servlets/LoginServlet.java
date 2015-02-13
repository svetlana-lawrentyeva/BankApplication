package com.luxoft.bankapp.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {
    private Logger log = Logger.getLogger(LoginServlet.class.getName());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String clientName = req.getParameter("clientName");
        if(clientName == null){
            log.warning("client not found");
            throw new ServletException("no client specified");
        }
        req.getSession().setAttribute("clientName", clientName);
        log.info("client "+clientName+" logged into ATM");

        resp.sendRedirect("bankomat/menu.html");
    }
}
