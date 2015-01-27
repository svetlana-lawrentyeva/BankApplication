package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.BaseDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.dao.BankDao;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.SavingAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by SCJP on 27.01.15.
 */
public class BankDaoImpl implements BankDao {

    private BaseDao baseDao = new BaseDaoImpl();
    @Override
    public Bank getBankByName(String name) throws DaoException {
        Bank bank = null;
        Connection conn = baseDao.openConnection();
        String sql = "select * from banks where name = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);

            if(!preparedStatement.execute()){
                throw new DaoException("impossible to get the bank from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            rs.next();
            long id = rs.getLong(1);
            String bankName = rs.getString(2);

            bank.setId(id);
            bank.setName(bankName);

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        baseDao.closeConnection();
        return bank;
    }
}
