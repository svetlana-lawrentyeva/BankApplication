package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.BankDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.model.impl.ClientComparator;
import com.luxoft.bankapp.service.BankInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankDaoImpl extends BaseDaoImpl implements BankDao {

    private static volatile BankDao instance;
    private static Logger log = Logger.getLogger(BankDaoImpl.class.getName());

    private BankDaoImpl(){}

    public static BankDao getInstance(){
        if(instance == null){
                    instance = new BankDaoImpl();
        }
        return instance;
    }

    @Override
    public Bank getBankByName(String name) throws DaoException {
        Bank bank;
        Connection conn = openConnection();
        String sql = "select * from banks where name = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+name+" error: ",
                        new DaoException("impossible to get the bank from db."));
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
            log.log(Level.SEVERE, Thread.currentThread().getName()+" "+name+" error: "+e.getMessage());
            throw new DaoException(e.getMessage());
        }
        closeConnection(conn);

        List<Client>clients = DaoFactory.getClientDao().getAllClients(bank);
        bank.setClients(new HashSet<>(clients));
        log.fine(Thread.currentThread().getName() + " " + name + " success");
        return bank;
    }

    @Override
    public Bank getBankById(long bankId) throws DaoException {
        Bank bank;
        Connection conn = openConnection();
        String sql = "select * from banks where id = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bankId);
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bankId+" error: ",
                        new DaoException("impossible to get the bank from db."));
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
            log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bankId+" error: "+e.getMessage());
            throw new DaoException(e.getMessage());
        }
        closeConnection(conn);
        List<Client>clients = DaoFactory.getClientDao().getAllClients(bank);
        bank.setClients(new HashSet<>(clients));
        log.fine(Thread.currentThread().getName() + " " + bankId + " success");
        return bank;
    }

    private Bank insert(Bank bank) throws DaoException {
        Connection conn = openConnection();
        String sql = "insert into banks (name) values (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, bank.getName());
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (preparedStatement.executeUpdate() == 0) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                        new DaoException("impossible to save bank in db. transaction is rolled back"));
                throw new DaoException("impossible to save bank in db. transaction is rolled back");
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs == null || !rs.next()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                        new DaoException("impossible to save the bank in db. transaction is rolled back"));
                throw new DaoException("impossible to save the bank in db. transaction is rolled back");
            }
            Long id = rs.getLong(1);
            rs.close();
            preparedStatement.close();
            bank.setId(id);

        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: "+e.getMessage());
            throw new DaoException(e.getMessage());
        }
        closeConnection(conn);
        for (Client client : bank.getClients()) {
            DaoFactory.getClientDao().save(client);
        }
        log.fine(Thread.currentThread().getName()+" "+bank+" success");
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
                log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

                if (preparedStatement.executeUpdate() == 0) {
                    log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                            new DaoException("impossible to save bank in db. transaction is rolled back"));
                    throw new DaoException("impossible to save bank in db. transaction is rolled back");
                }
                preparedStatement.close();
                closeConnection(conn);
            } catch (SQLException e) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: "+e.getMessage());
                throw new DaoException(e.getMessage());
            }
        }
        log.fine(Thread.currentThread().getName()+" "+bank+" success");
        return bank;
    }

    public void delete(Bank bank) throws DaoException {
        if (bank.getId() != -1){
            try {
                for (Client client : bank.getClients()) {
                    DaoFactory.getClientDao().remove(client);
                }
                Connection conn = openConnection();
                String sql = "delete from banks where id = (?)";
                final PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, bank.getId());
                log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

                if (preparedStatement.executeUpdate() == 0) {
                    log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                            new DaoException("impossible to delete bank in db. transaction is rolled back"));
                    throw new DaoException("impossible to delete bank in db. transaction is rolled back");
                }
                preparedStatement.close();
                closeConnection(conn);
            } catch (SQLException e) {
                log.log(Level.SEVERE, Thread.currentThread().getName() + " " + bank +
                        " error: impossible to delete bank in db. transaction is rolled back"+e.getMessage());
                throw new DaoException("impossible to delete bank in db. transaction is rolled back");
            } 
        }
        log.fine(Thread.currentThread().getName()+" "+bank+" success");
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
            log.log(Level.SEVERE, Thread.currentThread().getName() + " " + bank + " error: z"+e.getMessage());
        }
        log.fine(Thread.currentThread().getName()+" "+bank+" success");
        return bankInfo;
    }

    @Override
    public String getBankReport(Bank bank) throws DaoException {
        List<Client> clients = (DaoFactory.getClientDao().getAllClients(bank));
        bank.setClients(new HashSet<>(clients));
        int clientsNumber = getClientsNumber(bank);
        int accountNumber = getAccountsNumber(bank);
        Set<Client> sortedByBalance = getClientsSorted(bank);
        float bankCreditSum = getBankCreditSum(bank);
        Map<String, List<Client>>clientSortedByCity = getClientsByCity(bank);

        StringBuilder builder = new StringBuilder();
        builder.append("\n").append(bank.getName().toUpperCase()).append("\n");
        builder.append("\nnumber of clients: ").append(clientsNumber);
        builder.append("\nnumber of accounts: ").append(accountNumber);
        builder.append("\nbank credit sum: ").append(bankCreditSum).append("\n");
        if(clientsNumber>0){
            builder.append("\nclients:");
            for(Client client:sortedByBalance){
                builder.append("\n\n").append(client.getClientSalutation());
                if(client.getAccounts().size()>0){
                    builder.append("\naccounts:");
                    for(Account account:client.getAccounts()){
                        builder.append(account);
                        if(account.equals(client.getActiveAccount())){
                            builder.append(" <<<<< active account");
                        }
                    }
                }
            }

            builder.append("\n\nclient by city:");
            for(String city:clientSortedByCity.keySet()){
                for(Client client:clientSortedByCity.get(city)){
                    builder.append("\n").append(city).append(" ").append(client.getClientSalutation());
                }
            }
        }
        log.fine(Thread.currentThread().getName()+" "+bank+" success");
        return builder.toString();
    }

    public int getClientsNumber(Bank bank) throws DaoException {
        Connection conn = openConnection();
        int clientsNumber = 0;
        String sql = "select count(id) from clients where id_bank = (?);";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                        new DaoException("no clients found"));
                throw new DaoException("no clients found");
            }
            ResultSet rs = preparedStatement.getResultSet();
            rs.next();
            clientsNumber = rs.getInt(1);
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName() + " " + bank + " error: "+e.getMessage());
        }
        closeConnection(conn);
        log.fine(Thread.currentThread().getName() + " " + bank + " success");
        return clientsNumber;
    }

    public int getAccountsNumber(Bank bank) throws DaoException {
        Connection conn = openConnection();
        int accountsNumber = 0;
        String sql = "select count(a.id) from accounts as a join clients as c on a.id_client = c.id where c.id_bank = (?);";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                        new DaoException("no accounts found"));
                throw new DaoException("no accounts found");
            }
            ResultSet rs = preparedStatement.getResultSet();
            rs.next();
            accountsNumber = rs.getInt(1);
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName() + " " + bank + " error: "+e.getMessage());
        }
        closeConnection(conn);
        log.fine(Thread.currentThread().getName() + " " + bank + " success");
        return accountsNumber;
    }

    public float getBankCreditSum(Bank bank) throws DaoException {
        Connection conn = openConnection();
        float creditSum = 0;

        String sql = "select sum(a.balance) as credit from accounts as a join clients as c" +
                " on a.id_client = c.id where c.id_bank = (?) and a.balance <0 ;";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                        new DaoException("no accounts found"));
                throw new DaoException("no accounts found");
            }
            ResultSet rs = preparedStatement.getResultSet();
            rs.next();
            creditSum = rs.getFloat(1);
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName() + " " + bank + " error: "+e.getMessage());
        }

        closeConnection(conn);
        log.fine(Thread.currentThread().getName() + " " + bank + " success");
        return creditSum;
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank) {
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
            log.log(Level.SEVERE, Thread.currentThread().getName() + " " + bank + " error: "+e.getMessage());
        }
        log.fine(Thread.currentThread().getName()+" "+bank+" success");
        return map;
    }

    public Set<Client> getClientsSorted(Bank bank) throws DaoException {
        Comparator<Client> c = new ClientComparator();
        Set<Client> sortedClients = new TreeSet<>(c);
        List<Client> clients = DaoFactory.getClientDao().getAllClients(bank);
        sortedClients.addAll(clients);
        log.fine(Thread.currentThread().getName() + " " + bank + " success");
        return sortedClients;
    }
}
