package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.BaseDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by SCJP on 27.01.15.
 */
public class BaseDaoImpl implements BaseDao {

    private Connection connection;
    private final String CONNECTION_STRING = "jdbc:h2:tcp://localhost/~/BankApplication";

    @Override
    public Connection openConnection() throws DaoException {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING, "sa", "" );
            return connection;
        } catch(ClassNotFoundException | SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void closeConnection() throws DaoException {
        try {
            connection.close();
        } catch(SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
