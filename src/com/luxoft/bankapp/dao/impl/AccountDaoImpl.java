package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.AccountDao;
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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {

    private static volatile AccountDao instance;
    private static Logger log = Logger.getLogger(AccountDaoImpl.class.getName());

    private AccountDaoImpl() {
    }

    public static AccountDao getInstance() {
        if (instance == null) {
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

            log.fine(Thread.currentThread().getName()+" insert(): preparedStatement created: "+preparedStatement);

            if (preparedStatement.executeUpdate() == 0) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" insert account "+account+" error: ",
                        new DaoException("impossible to save account in db. transaction is rolled back"));
                throw new DaoException("impossible to save account "+account+" in db. transaction is rolled back");
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs == null || !rs.next()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" insert "+account+" error: ", new DaoException("impossible to save account in db. transaction is rolled back"));
                throw new DaoException(Thread.currentThread().getName()+" impossible to save account "+account+" in db. transaction is rolled back");
            }
            Long id = rs.getLong(1);
            rs.close();
            preparedStatement.close();
            account.setId(id);
            log.fine(Thread.currentThread().getName()+" insert "+account+" successful");
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" insert "+account+" error: " + e.getMessage(), e);
        }
        closeConnection(conn);

        return account;
    }

    @Override
    public Account save(Account account) throws DaoException {
        Connection conn = openConnection();
        if (account.getId() == -1) {
            account = insert(account);
        } else {
            String sql = "update accounts set balance = (?), overdraft = (?), id_client = (?) where id = (?)";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setFloat(1, account.getBalance());
                preparedStatement.setFloat(2, account.getOverdraft());
                preparedStatement.setLong(3, account.getClient().getId());
                preparedStatement.setLong(4, account.getId());
                log.fine(Thread.currentThread().getName()+" save: preparedStatement created: "+preparedStatement);

                if (preparedStatement.executeUpdate() == 0) {
                    log.log(Level.SEVERE, Thread.currentThread().getName()+" save "+account+" error: ", new DaoException("impossible to save account in db. transaction is rolled back"));
                    throw new DaoException("impossible to save account in db. transaction is rolled back");
                }
                preparedStatement.close();
                log.fine(Thread.currentThread().getName()+" save "+account+" successful");
            } catch (SQLException e) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" save "+account+" error: " + e.getMessage(), e);
            }
        }
        closeConnection(conn);

        return account;
    }

    @Override
    public void remove(Account account) throws DaoException {
        Connection conn = openConnection();
        String sql = "delete from accounts where id = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, account.getId());
            log.fine(Thread.currentThread().getName()+" remove: preparedStatement created: "+preparedStatement);

            if (preparedStatement.executeUpdate() == 0) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" remove "+account+" error: ", new DaoException("impossible to remove account in db. transaction is rolled back"));
                throw new DaoException("impossible to remove account from db. transaction is rolled back");
            }
            preparedStatement.close();
            log.fine(Thread.currentThread().getName()+" remove "+account+" successful");
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" remove "+account+" error: " + e.getMessage(), e);
        }
        closeConnection(conn);
    }

    @Override
    public void removeAllByClient(Client client) throws DaoException {
        Connection conn = openConnection();
        String sql = "delete from accounts where id_client = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, client.getId());
            log.fine(Thread.currentThread().getName()+" removeAllByClient: preparedStatement created: "+preparedStatement);

            if (preparedStatement.executeUpdate() == 0) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" remove accounts by "+client+" error: ", new DaoException("impossible to remove accounts in db. transaction is rolled back"));
                throw new DaoException("impossible to remove accounts in db. transaction is rolled back");
            }
            preparedStatement.close();
            log.fine(Thread.currentThread().getName()+" removeAllByClient "+client+" successful");
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" removeAllByClient "+client+" error: " + e.getMessage(), e);
        }
        closeConnection(conn);
    }

    @Override
    public List<Account> getAllByClient(Client client) throws DaoException {
        List<Account> accounts = new ArrayList<>();
        Connection conn = openConnection();
        String sql = "select * from accounts where id_client = ?";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, client.getId());
            log.fine(Thread.currentThread().getName()+" getAllByClient: preparedStatement created: "+preparedStatement);

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" getAllByClient "+client+" error: ", new DaoException("impossible to get accounts from db."));
                throw new DaoException("impossible to get accounts from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next()) {
                long idAccount = rs.getLong("ID");
                float balance = rs.getFloat(2);
                float overdraft = rs.getFloat(3);
                Account account;
                if (overdraft == 0) {
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
            log.fine(Thread.currentThread().getName()+" getAllByClient "+client+" successful");
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" getAllByClient "+client+" error: " + e.getMessage(), e);
        }
        closeConnection(conn);
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
            log.log(Level.SEVERE, Thread.currentThread().getName()+" getById "+idAccount+" error: ", new DaoException("impossible to get accounts from db."));

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" getById "+idAccount+" error: ", new DaoException("impossible to get accounts from db."));
                throw new DaoException("impossible to get accounts from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            if (!rs.next()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" getById error: ", new DaoException("impossible to get accounts from db."));

            }
            float balance = rs.getFloat(2);
            float overdraft = rs.getFloat(3);
            long idClient = rs.getLong(4);
            if (overdraft == 0) {
                account = new SavingAccount();
            } else {
                account = new CheckingAccount();
                account.setOverdraft(overdraft);
            }
            account.setId(idAccount);
            account.setBalance(balance);

            if (idClient > 0) {
                Client client = DaoFactory.getClientDao().getById(idClient);
                account.setClient(client);
            }

            log.fine(Thread.currentThread().getName()+" getById "+idAccount+" successful");
            rs.close();
            preparedStatement.close();

        } catch (SQLException e) {
            log.log(Level.SEVERE, "getById "+idAccount+" error: " + e.getMessage(), e);
        }
        closeConnection(conn);
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

            if (preparedStatement.executeUpdate() == 0) {
                log.log(Level.SEVERE, "addAccountToClient "+account +" to "+client+" error: ",
                        new DaoException("impossible to add account. transaction is rolled back"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "addAccountToClient error: addAccountToClient "+account +" to "+client + " "+e.getMessage(), e);
        }

        closeConnection(conn);
    }

    @Override
    public float getBalance(Account account) throws DaoException {
        Connection conn = openConnection();
        String sql = "select balance from accounts where id = (?)";
        float balance = 0;
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, account.getId());

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, "getBalance error: "+account, new DaoException("impossible to get accounts from db."));
            }
            ResultSet rs = preparedStatement.getResultSet();
            if (!rs.next()) {
                log.log(Level.SEVERE, "getBalance error: "+account, new DaoException("impossible to get accounts from db."));
            }
            balance = rs.getFloat(1);

            rs.close();
            preparedStatement.close();

        } catch (SQLException e) {
            log.log(Level.SEVERE, "getBalance error: "+account+" " + e.getMessage(), e);
        }
        closeConnection(conn);
        return balance;
    }

}
