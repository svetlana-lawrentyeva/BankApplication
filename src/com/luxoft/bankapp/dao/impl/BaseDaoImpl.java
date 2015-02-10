package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.BaseDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class BaseDaoImpl implements BaseDao {

    private final String CONNECTION_STRING = "jdbc:h2:tcp://localhost/~/BankApplication;MVCC=true";
    private static Logger log = Logger.getLogger(BaseDaoImpl.class.getName());

    public BaseDaoImpl(){
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName() + " org.h2.Driver error: "+
                    e.getMessage(), e);
        }
    }

    @Override
    public Connection openConnection() throws DaoException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, "sa", "");
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName() + " " + e.getMessage(), e);
        }
        log.info(Thread.currentThread().getName() + " connection is opened");
        return connection;
    }

        @Override
    public void closeConnection(Connection connection) throws DaoException {
        try {
            connection.close();
        } catch(SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName() + " " + e.getMessage(), e);
        }
            log.info(Thread.currentThread().getName() + " connection is closed");
    }

}
