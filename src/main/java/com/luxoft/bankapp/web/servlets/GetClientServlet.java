package com.luxoft.bankapp.web.servlets;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetClientServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(GetClientServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id= Long.parseLong(req.getParameter("id"));
        Client client = null;
        try {
            client = ServiceFactory.getClientService().getById(id);
        } catch (ClientNotExistsException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        } catch (DaoException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        if(client!=null){
            req.setAttribute("client", client);
        }

        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("remoteOffice/client.jsp").forward(req, resp);
    }
}
