package com.luxoft.bankapp.web.servlets;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.service.impl.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WithdrawServlet extends HttpServlet {
    Logger log = Logger.getLogger(BalanceServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String clientName = (String) session.getAttribute("clientName");
        Bank bank = ServiceFactory.getBankService().getByName("My bank");
        Client client = null;
        try {
            client = ServiceFactory.getClientService().getByName(bank, clientName);
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        float x = Float.parseFloat(req.getParameter("amount"));
        if(client != null){
            try {
                Account account = client.getAccounts().iterator().next();
                if(account != null) {
                    ServiceFactory.getAccountService().withdraw(account, x);
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        resp.sendRedirect("/balance");
    }
}
