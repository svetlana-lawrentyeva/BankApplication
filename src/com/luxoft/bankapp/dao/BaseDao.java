package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.dao.exceptions.DaoException;

import java.sql.Connection;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 1/27/15
 * Time: 7:26 PM
 */
public interface BaseDao {

    /**
     * Open connection to database
     */
    Connection openConnection() throws DaoException;

    /**
     * Close connection to database
     */
    void closeConnection() throws DaoException;
}
