package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.AccountDao;
import com.luxoft.bankapp.dao.BaseDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private BaseDao baseDao = new BaseDaoImpl();

    private void insert(Account account, Client client) throws DaoException {
        Connection conn = baseDao.openConnection();
        String sql = "insert into accounts (balance, overdraft, id_client) values (?, ?, ?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setFloat(1, account.getBalance());
            preparedStatement.setFloat(2, account.getOverdraft());
            preparedStatement.setLong(3, client.getId());

            if(preparedStatement.executeUpdate() == 0){
                throw new DaoException("impossible to save account in db. transaction is rolled back");
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs == null || !rs.next()){
                throw new DaoException("impossible to save account in db. transaction is rolled back");
            }
            Long id = rs.getLong(1);
            rs.close();
            preparedStatement.close();
            account.setId(id);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        baseDao.closeConnection();
    }

    @Override
    public void save(Account account, Client client) throws DaoException {
        Connection conn = baseDao.openConnection();
        if(account.getId() == -1){
            insert(account, client);
        } else {
            String sql = "update accounts set balance = (?), overdraft = (?), id_client = (?) where id = (?)";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setFloat(1, account.getBalance());
                preparedStatement.setFloat(2, account.getOverdraft());
                preparedStatement.setLong(3, client.getId());
                preparedStatement.setLong(4, account.getId());

                if(preparedStatement.executeUpdate() == 0){
                    throw new DaoException("impossible to save account in db. transaction is rolled back");
                }
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException("error while updating client "+client.getName());
            }
        }
        baseDao.closeConnection();
    }

    @Override
    public void removeByClientId(long id) throws DaoException {
        Connection conn = baseDao.openConnection();
        String sql = "delete from accounts where id_client = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            if(preparedStatement.executeUpdate() == 0){
                throw new DaoException("impossible to remove account in db. transaction is rolled back");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        baseDao.closeConnection();
    }

    @Override
    public List<Account> getClientAccounts(long id) throws DaoException {
        List<Account>accounts = null;
        Connection conn = baseDao.openConnection();
        String sql = "select * from accounts where id_client = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            if(!preparedStatement.execute()){
                throw new DaoException("impossible to get accounts from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            accounts = new ArrayList<>();
            while(rs.next()){
                long idAccount = rs.getLong("ID");
                float balance = rs.getFloat(2);
                float overdraft = rs.getFloat(3);
                Account account;
                if(overdraft == 0){
                    account = new SavingAccount();
                } else {
                    account = new CheckingAccount();
                    account.setOverdraft(overdraft);
                }
                account.setId(idAccount);
                account.setBalance(balance);

                accounts.add(account);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        baseDao.closeConnection();
        return accounts;
    }

    @Override
    public Account findAccountById(long id) throws DaoException {
        Account account = null;
        Connection conn = baseDao.openConnection();
        String sql = "select * from accounts where id = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            if(!preparedStatement.execute()){
                throw new DaoException("impossible to get the account from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            if(rs.next()){
                long idAccount = rs.getLong(1);
                float balance = rs.getFloat(2);
                float overdraft = rs.getFloat(3);
                if(overdraft == 0){
                    account = new SavingAccount();
                } else {
                    account = new CheckingAccount();
                    account.setOverdraft(overdraft);
                }
                account.setId(idAccount);
                account.setBalance(balance);
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        baseDao.closeConnection();
        return account;
    }
}
