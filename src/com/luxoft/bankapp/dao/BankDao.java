package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.service.BankInfo;

/**
 * Created:
 * User: Svetlana Lawrentyeva
 * Date: 1/27/15
 * Time: 7:24 PM
 */
public interface BankDao {
    Bank getBankByName(String name) throws DaoException;
    BankInfo getBankInfo(Bank bank);
}
