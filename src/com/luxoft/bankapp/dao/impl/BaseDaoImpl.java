package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.BaseDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public abstract class BaseDaoImpl implements BaseDao {

    private Connection connection;
    private final String CONNECTION_STRING = "jdbc:h2:tcp://localhost/~/BankApplication";
    private JdbcConnectionPool cp;
    public static Lock lock = new ReentrantLock();

    public BaseDaoImpl(){
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cp = JdbcConnectionPool.create(CONNECTION_STRING, "sa", "" );
    }

    @Override
    public Connection openConnection() throws DaoException {
        try {
            connection = cp.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

        @Override
    public void closeConnection() throws DaoException {
        try {
            connection.close();
        } catch(SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

}
