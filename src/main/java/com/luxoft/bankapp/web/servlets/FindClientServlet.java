package com.luxoft.bankapp.web.servlets;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FindClientServlet extends HttpServlet {
    private ServiceFactory serviceFactory;
    private final Logger log = Logger.getLogger(FindClientServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city");
        String clientName = req.getParameter("clientName");
        List<Client> clients = null;
        Bank bank = serviceFactory.getBankService().getByName("My bank");
        try {
            clients = serviceFactory.getClientService().getClientsByNameAndCity(bank, clientName, city);
        } catch (DaoException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        req.setAttribute("clients", clients);
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("remoteOffice/clients.jsp").forward(req, resp);
    }

    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
}
