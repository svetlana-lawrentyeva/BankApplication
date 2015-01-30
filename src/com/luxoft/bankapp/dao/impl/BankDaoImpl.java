package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.BankDao;
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

public class BankDaoImpl extends BaseDaoImpl implements BankDao {

    @Override
    public Bank getBankByName(String name) throws DaoException {
        Bank bank = null;
        Connection conn = openConnection();
        String sql = "select * from banks where name = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);

            if (!preparedStatement.execute()) {
                throw new DaoException("impossible to get the bank from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            rs.next();
            long id = rs.getLong(1);
            String bankName = rs.getString(2);

            bank = new Bank();
            bank.setId(id);
            bank.setName(bankName);

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
        return bank;
    }

    @Override
    public Bank getBankById(long bankId) throws DaoException {
        Bank bank = null;
        Connection conn = openConnection();
        String sql = "select * from banks where id = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bankId);

            if (!preparedStatement.execute()) {
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
        closeConnection();
        return bank;
    }

    private Bank insert(Bank bank) throws DaoException {
        Connection conn = openConnection();
        String sql = "insert into banks (name) values (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, bank.getName());

            if (preparedStatement.executeUpdate() == 0) {
                throw new DaoException("impossible to save bank in db. transaction is rolled back");
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs == null || !rs.next()) {
                throw new DaoException("impossible to save the bank in db. transaction is rolled back");
            }
            Long id = rs.getLong(1);
            rs.close();
            preparedStatement.close();
            bank.setId(id);

            for (Client client : bank.getClients()) {
                DaoFactory.getClientDao().save(client);
                DaoFactory.getClientDao().addClientToBank(bank, client);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();

        return bank;
    }

    @Override
    public Bank save(Bank bank) throws DaoException {
        if (bank.getId() == -1) {
            bank = insert(bank);
        } else {
            try {
                for (Client client : bank.getClients()) {
                    DaoFactory.getClientDao().save(client);
                }
                Connection conn = openConnection();
                String sql = "update banks set name = (?) where id = (?)";
                final PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, bank.getName());
                preparedStatement.setLong(2, bank.getId());

                if (preparedStatement.executeUpdate() == 0) {
                    throw new DaoException("impossible to save bank in db. transaction is rolled back");
                }
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
            closeConnection();
        }
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
        Connection conn = openConnection();
        int clientsNumber = 0;
        String sql = "select count(id) from clients where id_bank = (?);";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());

            if (!preparedStatement.execute()) {
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

        closeConnection();
        return clientsNumber;
    }

    private int getAccountsNumber(Bank bank) throws DaoException {
        Connection conn = openConnection();
        int accountsNumber = 0;
        String sql = "select count(a.id) from accounts as a join clients as c on a.id_client = c.id where c.id_bank = (?);";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());

            if (!preparedStatement.execute()) {
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

        closeConnection();
        return accountsNumber;
    }

    private float getBankCreditSum(Bank bank) throws DaoException {
        Connection conn = openConnection();
        float creditSum = 0;

        String sql = "select sum(a.balance) as credit from accounts as a join clients as c" +
                " on a.id_client = c.id where c.id_bank = (?) and a.balance <0 ;";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());

            if (!preparedStatement.execute()) {
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

        closeConnection();
        return creditSum;
    }

    private Map<String, List<Client>> getClientsByCity(Bank bank) {
        Map<String, List<Client>> map = new TreeMap<>();
        try {
            List<Client> clients = DaoFactory.getClientDao().getAllClients(bank);
            for (Client client : clients) {
                String city = client.getCity();
                if (map.containsKey(city)) {
                    map.get(city).add(client);
                } else {
                    List<Client> clientList = new ArrayList<>();
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
        Comparator<Client> c = new ClientComparator();
        Set<Client> sortedClients = new TreeSet<>(c);
        List<Client> clients = DaoFactory.getClientDao().getAllClients(bank);
        sortedClients.addAll(clients);
        return sortedClients;
    }
}
