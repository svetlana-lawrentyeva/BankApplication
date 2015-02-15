package com.luxoft.bankapp.service.impl;

import com.luxoft.bankapp.dao.AccountDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.impl.Client;

import java.util.List;

public class AccountService {
    private AccountDao accountDao;
    /**
     * Save account to database
     * @param account account to be saved
     */
    public Account save(Account account) throws DaoException {
        return accountDao.save(account);
    }

    /**
     * Remove account from database
     * @param account account to be removed
     */
    public void remove(Account account) throws DaoException {
        accountDao.remove(account);
    }

    /**
     * remove all accounts of the client from database
     * @param client client whose accounts will be removed
     */
    public void removeAllByClient(Client client) throws DaoException {
        accountDao.removeAllByClient(client);
    }

    /**
     * Get all accounts of the client from database
     * @param client client whose accounts will be get
     */
    public List<Account> getAllByClient(Client client) throws DaoException {
        return accountDao.getAllByClient(client);
    }

    /**
     * Get account by id
     * @param idAccount id to get account from database
     */
    public Account getById(long idAccount) throws DaoException {
        return accountDao.getById(idAccount);
    }

    /**
     * Get account info
     * @param account account detail of which will be get
     */
    public String getAccountInfo(Account account) {
        return account.toString();
    }

    /**
     * Deposit money to account
     * @param account account to deposit money
     * @param x money to deposit
     */
    public void deposit(Account account, float x) throws DaoException {
        account.setBalance(getBalance(account));
        account.deposit(x);
        accountDao.save(account);
    }

    /**
     * Withdraw money from account
     * @param account account to withdraw money
     * @param x money to withdraw
     */
    public void withdraw(Account account, float x) throws NotEnoughFundsException, DaoException {
        synchronized (ServiceFactory.monitor){
            account.setBalance(getBalance(account));
            account.withdraw(x);
            accountDao.save(account);
        }
    }

    /**
     * Transfer money between accounts
     * @param account1 account money from which will be taken
     * @param account2 account money to which will be put
     */
    public void transfer(Account account1, Account account2, float x) throws NotEnoughFundsException, DaoException {
        account1.setBalance(getBalance(account1));
        account2.setBalance(getBalance(account2));
        account1.transfer(account2, x);
        accountDao.save(account1);
        accountDao.save(account2);
    }

    /**
     * Get balance of the account
     * @param account account to get balance
     */
    public float getBalance(Account account) throws DaoException {
        return accountDao.getBalance(account);
    }

    public float getClientBalance(Client client) throws DaoException {
        return accountDao.getClientBalance(client);
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
