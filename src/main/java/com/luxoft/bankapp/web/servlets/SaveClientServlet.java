package com.luxoft.bankapp.web.servlets;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveClientServlet extends HttpServlet {
    private ServiceFactory serviceFactory;
    private final Logger log = Logger.getLogger(SaveClientServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id;
        id = Long.parseLong(req.getParameter("id"));
        Client client = null;
        if(id==-1){
            client = new Client();
        } else {
            try {
                client = serviceFactory.getClientService().getById(Long.parseLong(req.getParameter("id")));
            } catch (ClientNotExistsException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            } catch (DaoException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        client.setName(req.getParameter("name"));
        client.setCity(req.getParameter("city"));
        client.setEmail(req.getParameter("email"));
        client.setPhone(req.getParameter("phone"));
        client.setOverdraft(Float.parseFloat(req.getParameter("overdraft")));
        Gender gender = Gender.getGender((req.getParameter("gender").substring(0,1)));
        client.setGender(gender);

        try {
            Bank bank = serviceFactory.getBankService().getByName("My bank");
            client.setBank(bank);
            serviceFactory.getClientService().save(client);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("bankomat/menu.html").forward(req, resp);

    }

    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
}
