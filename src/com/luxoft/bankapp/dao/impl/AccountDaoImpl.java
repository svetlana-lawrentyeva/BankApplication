package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.AccountDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.SavingAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {

    private static AccountDao instance;

    private AccountDaoImpl(){}

    public static AccountDao getInstance(){
        if(instance == null){
            instance = new AccountDaoImpl();
        }
        return instance;
    }

    private Account insert(Account account) throws DaoException {
        Connection conn = openConnection();
        String sql = "insert into accounts (balance, overdraft, id_client) values (?, ?, ?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setFloat(1, account.getBalance());
            preparedStatement.setFloat(2, account.getOverdraft());
            preparedStatement.setLong(3, account.getClient().getId());

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
        closeConnection();

        return account;
    }

    @Override
    public Account save(Account account) throws DaoException {
        Connection conn = openConnection();

        if(account.getId() == -1){
            account = insert(account);
        } else {
            String sql = "update accounts set balance = (?), overdraft = (?), id_client = (?) where id = (?)";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setFloat(1, account.getBalance());
                preparedStatement.setFloat(2, account.getOverdraft());
                preparedStatement.setLong(3, account.getClient().getId());
                preparedStatement.setLong(4, account.getId());

                if(preparedStatement.executeUpdate() == 0){
                    throw new DaoException("impossible to save account in db. transaction is rolled back");
                }
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException("error while updating account "+account.getId());
            }
        }
        closeConnection();

        return account;
    }

    @Override
    public void remove(Account account) throws DaoException {
        Connection conn = openConnection();
        String sql = "delete from accounts where id = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, account.getId());

            if(preparedStatement.executeUpdate() == 0){
                throw new DaoException("impossible to remove account in db. transaction is rolled back");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
    }

    @Override
    public void removeAllByClient(Client client) throws DaoException {
        Connection conn = openConnection();
        String sql = "delete from accounts where id_client = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, client.getId());

            if(preparedStatement.executeUpdate() == 0){
                throw new DaoException("impossible to remove account in db. transaction is rolled back");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
    }

    @Override
    public List<Account> getAllByClient(Client client) throws DaoException {
        List<Account>accounts = null;
        Connection conn = openConnection();
        String sql = "select * from accounts where id_client = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, client.getId());

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
                account.setClient(client);

                accounts.add(account);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
        return accounts;
    }

    @Override
    public Account getById(long idAccount) throws DaoException {
        Account account = null;
        Connection conn = openConnection();
        String sql = "select * from accounts where id = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, idAccount);

            if(!preparedStatement.execute()){
                throw new DaoException("impossible to get the account from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            if(!rs.next()){
                throw new DaoException("impossible to get the account from db.");
            }
            float balance = rs.getFloat(2);
            float overdraft = rs.getFloat(3);
            long idClient = rs.getLong(4);
            if(overdraft == 0){
                account = new SavingAccount();
            } else {
                account = new CheckingAccount();
                account.setOverdraft(overdraft);
            }
            account.setId(idAccount);
            account.setBalance(balance);

            if(idClient > 0){
                Client client = DaoFactory.getClientDao().getById(idClient);
                account.setClient(client);
            }

            rs.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } catch (ClientNotExistsException e) {
            e.printStackTrace();
        }
        closeConnection();
        return account;
    }

    @Override
    public void addAccountToClient(Client client, Account account) throws DaoException {
        Connection conn = openConnection();

        String sql = "update accounts set id_client = (?) where id = (?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setLong(1, client.getId());
            preparedStatement.setLong(2, account.getId());

            if(preparedStatement.executeUpdate() == 0){
                throw new DaoException("impossible to add account. transaction is rolled back");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException("error while adding account "+ account.getId() + " to client " + client.getId());
        }

        closeConnection();
    }

    @Override public float getBalance(Account account) throws DaoException {
        Connection conn = openConnection();
        String sql = "select balance from accounts where id = (?)";
        float balance = 0;
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, account.getId());

            if (!preparedStatement.execute()) {
                throw new DaoException("impossible to get the account from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            if (!rs.next()) {
                throw new DaoException("impossible to get the account from db.");
            }
            balance = rs.getFloat(1);

            rs.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
        return  balance;
    }

}
