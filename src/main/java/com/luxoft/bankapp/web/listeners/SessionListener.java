package com.luxoft.bankapp.web.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by SCJP on 13.02.2015.
 */
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        final ServletContext context = httpSessionEvent.getSession().getServletContext();

        synchronized (SessionListener.class){
            Integer clientsConnected = (Integer) context.getAttribute("clientsConnected");
            if (clientsConnected == null) {
                clientsConnected = 1;
            } else {
                clientsConnected++;
            }
            context.setAttribute("clientsConnected", clientsConnected);
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        final ServletContext context = httpSessionEvent.getSession().getServletContext();

        synchronized (SessionListener.class){
            Integer clientsConnected = (Integer) context.getAttribute("clientsConnected");
            if(clientsConnected !=null){
                clientsConnected--;
            } else {
                System.out.println("there is no any session");
            }
            context.setAttribute("clientsConnected", clientsConnected);
        }
    }
}
