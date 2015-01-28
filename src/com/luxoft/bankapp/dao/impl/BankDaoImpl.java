package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.BankDao;
import com.luxoft.bankapp.dao.BaseDao;
import com.luxoft.bankapp.dao.ClientDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.ClientComparator;
import com.luxoft.bankapp.service.BankInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    @Override
    public BankInfo getBankInfo(Bank bank) {
        BankInfo bankInfo = new BankInfo();

        try {
            bankInfo.setClientsNumber(getClientsNumber(bank));
            bankInfo.setAccountsNumber(getAccountsNumber(bank));
            bankInfo.setBankCreditSum(getBankCreditSum(bank));
            bankInfo.setClientsByCity(getClientsByCity(bank));
            bankInfo.setClientsSorted(getClientsSorted(bank));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return bankInfo;
    }

    private int getClientsNumber(Bank bank) throws DaoException {
        Connection conn = baseDao.openConnection();
        int clientsNumber = 0;
        String sql = "select count(id) from clients where id_bank = (?);";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());

            if(!preparedStatement.execute()){
                throw new DaoException("no clients found");
            }
            ResultSet rs = preparedStatement.getResultSet();
            rs.next();
            clientsNumber = rs.getInt(1);
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        baseDao.closeConnection();
        return clientsNumber;
    }

    private int getAccountsNumber(Bank bank) throws DaoException {
        Connection conn = baseDao.openConnection();
        int accountsNumber = 0;
        String sql = "select count(a.id) from accounts as a join clients as c on a.id_client = c.id where c.id_bank = (?);";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());

            if(!preparedStatement.execute()){
                throw new DaoException("no accounts found");
            }
            ResultSet rs = preparedStatement.getResultSet();
            rs.next();
            accountsNumber = rs.getInt(1);
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        baseDao.closeConnection();
        return accountsNumber;
    }

    private float getBankCreditSum(Bank bank) throws DaoException {
        Connection conn = baseDao.openConnection();
        float creditSum = 0;

        String sql = "select sum(a.balance) as credit from accounts as a join clients as c"+
                " on a.id_client = c.id where c.id_bank = (?) and a.balance <0 ;";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());

            if(!preparedStatement.execute()){
                throw new DaoException("no accounts found");
            }
            ResultSet rs = preparedStatement.getResultSet();
            rs.next();
            creditSum = rs.getFloat(1);
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        baseDao.closeConnection();
        return creditSum;
    }

    private Map<String, List<Client>> getClientsByCity(Bank bank){
        ClientDao clientDao = new ClientDaoImpl();
        Map<String, List<Client>>map = new TreeMap<>();
        try {
            List<Client>clients = clientDao.getAllClients(bank);
            for(Client client:clients){
                String city = client.getCity();
                if(map.containsKey(city)){
                    map.get(city).add(client);
                } else {
                    List<Client>clientList = new ArrayList<>();
                    clientList.add(client);
                    map.put(city, clientList);
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return map;
    }
    private Set<Client> getClientsSorted(Bank bank) throws DaoException {
        ClientDao clientDao = new ClientDaoImpl();
        Comparator<Client> c = new ClientComparator();
        Set<Client> sortedClients = new TreeSet<>(c);
        List<Client>clients = clientDao.getAllClients(bank);
        sortedClients.addAll(clients);
        return sortedClients;
    }
}
