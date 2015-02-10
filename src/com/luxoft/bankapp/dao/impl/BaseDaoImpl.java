package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.BaseDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public abstract class BaseDaoImpl implements BaseDao {

    private final String CONNECTION_STRING = "jdbc:h2:tcp://localhost/~/BankApplication;MVCC=true";

    public BaseDaoImpl(){
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection openConnection() throws DaoException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

        @Override
    public void closeConnection(Connection connection) throws DaoException {
        try {
            connection.close();
        } catch(SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

}
