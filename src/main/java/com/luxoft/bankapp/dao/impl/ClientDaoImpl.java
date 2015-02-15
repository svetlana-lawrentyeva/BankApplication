package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.AccountDao;
import com.luxoft.bankapp.dao.BankDao;
import com.luxoft.bankapp.dao.ClientDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientDaoImpl extends BaseDaoImpl implements ClientDao {

    private static volatile ClientDao instance;
    private AccountDao accountDao;
    private BankDao bankDao;
    private static Logger log = Logger.getLogger(ClientDaoImpl.class.getName());

    private ClientDaoImpl(){}

    public static ClientDao getInstance(){
        if(instance == null){
                    instance = new ClientDaoImpl();
        }
        return instance;
    }

    @Override
    public Client getByName(Bank bank, String name) throws DaoException {
        Connection conn = openConnection();
        String sql = "select * from clients where id_bank = (?) and name = (?)";
        Client client;
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());
            preparedStatement.setString(2, name);
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+name+" error: ",
                        new DaoException("impossible to find the client in db."));
                throw new DaoException("impossible to find the client in db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            rs.next();
            long idClient = rs.getLong(1);
            String clientName = rs.getString(2);
            String city = rs.getString(3);
            String email = rs.getString(4);
            String phone = rs.getString(5);
            float overdraft = rs.getFloat(6);
            String genderLetter = rs.getString(7);

            Gender gender;
            switch (genderLetter) {
                case "m":
                    gender = Gender.MALE;
                    break;
                case "f":
                    gender = Gender.FEMALE;
                    break;
                default:
                    log.log(Level.SEVERE, Thread.currentThread().getName()+" "+name+" error: ",
                        new DaoException("incorrect data in db, impossible to load the client"));
                    throw new DaoException("incorrect data in db, impossible to load the client");
            }
            client = new Client();
            client.setId(idClient);
            client.setName(clientName);
            client.setCity(city);
            client.setEmail(email);
            client.setPhone(phone);
            client.setOverdraft(overdraft);
            client.setGender(gender);



            rs.close();
            preparedStatement.close();
            log.fine(Thread.currentThread().getName()+" "+name+" success");
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" "+name+" error: "+e.getMessage());
            throw new DaoException(e.getMessage());
        }
        closeConnection(conn);

        Set<Account> accounts = new HashSet<>(accountDao.getAllByClient(client));
        client.setAccounts(accounts);
        client.setBank(bank);
        log.fine(Thread.currentThread().getName()+" "+client+" success");
        return client;
    }

    @Override
    public Client getById(long idClient) throws DaoException {
        Connection conn = openConnection();
        String sql = "select * from clients where id = (?)";
        Client client = null;
        long idBank=-1;
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, idClient);

            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);
            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+idClient+" error: ",
                        new DaoException("impossible to find the client in db."));
                throw new DaoException("impossible to find the client in db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                String clientName = rs.getString(2);
                String city = rs.getString(3);
                String email = rs.getString(4);
                String phone = rs.getString(5);
                float overdraft = rs.getFloat(6);
                String genderLetter = rs.getString(7);
                idBank = rs.getLong(8);

                Gender gender;
                switch (genderLetter) {
                    case "m":
                        gender = Gender.MALE;
                        break;
                    case "f":
                        gender = Gender.FEMALE;
                        break;
                    default:
                        log.log(Level.SEVERE, Thread.currentThread().getName()+" "+idClient+" error: ",
                                new DaoException("incorrect data in db, impossible to load the client"));
                        throw new DaoException("incorrect data in db, impossible to load the client");
                }

                client = new Client();
                client.setId(idClient);
                client.setName(clientName);
                client.setCity(city);
                client.setEmail(email);
                client.setPhone(phone);
                client.setOverdraft(overdraft);
                client.setGender(gender);

            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" "+idClient+" error: "+e.getMessage());
            throw new DaoException(e.getMessage());
        }
        closeConnection(conn);

        Set<Account> accounts = new HashSet<>(accountDao.getAllByClient(client));
        client.setAccounts(accounts);
        Bank bank = bankDao.getBankById(idBank);
        client.setBank(bank);
        log.fine(Thread.currentThread().getName()+" "+idClient+" success");
        return client;
    }

    @Override
    public List<Client> getAllClients(Bank bank) throws DaoException {
        List<Client> clients = null;
        Connection conn = openConnection();
        String sql = "select * from clients where id_bank = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                        new DaoException("impossible to get clients from db."));
                throw new DaoException("impossible to get clients from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            clients = new ArrayList<>();
            while (rs.next()) {
                Client client = new Client();
                long idClient = rs.getLong(1);
                String clientName = rs.getString(2);
                String city = rs.getString(3);
                String email = rs.getString(4);
                String phone = rs.getString(5);
                float overdraft = rs.getFloat(6);
                String genderLetter = rs.getString(7);

                Gender gender;
                switch (genderLetter) {
                    case "m":
                        gender = Gender.MALE;
                        break;
                    case "f":
                        gender = Gender.FEMALE;
                        break;
                    default:
                        log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                                new DaoException("incorrect data in db, impossible to load the client"));
                        throw new DaoException("incorrect data in db, impossible to load the client");
                }
                client.setId(idClient);
                client.setName(clientName);
                client.setCity(city);
                client.setEmail(email);
                client.setPhone(phone);
                client.setOverdraft(overdraft);
                client.setGender(gender);
                client.setBank(bank);

                clients.add(client);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: "+e.getMessage());
            throw new DaoException(e.getMessage());
        }
        closeConnection(conn);

        for(Client client:clients){
            Set<Account> accounts = new HashSet<>(accountDao.getAllByClient(client));
            client.setAccounts(accounts);
        }
        log.fine(Thread.currentThread().getName()+" "+bank+" success");
        return clients;
    }

    private Client insert(Client client) throws DaoException {
        Long id=-1l;
        Connection conn = openConnection();
            String sql = "insert into clients (name, city, email, phone, overdraft, gender, id_bank)" +
                " values (?, ?, ?, ?, ?, ?, ?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getCity());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setFloat(5, client.getOverdraft());
            preparedStatement.setString(6, String.valueOf(client.getGender().toString().toLowerCase().charAt(0)));
            preparedStatement.setLong(7, client.getBank().getId());
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (preparedStatement.executeUpdate() == 0) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+client+" error: ",
                        new DaoException("impossible to save account in db. transaction is rolled back"));
                throw new DaoException("impossible to save account in db. transaction is rolled back");
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs == null || !rs.next()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+client+" error: ",
                        new DaoException("impossible to save account in db. transaction is rolled back"));
                throw new DaoException("impossible to save the client in db. transaction is rolled back");
            }
            id = rs.getLong(1);
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" "+client+" error: "+e.getMessage());
            throw new DaoException(e.getMessage());
        }
        closeConnection(conn);
        client.setId(id);

        for (Account account : client.getAccounts()) {
            accountDao.save(account);
        }
        log.fine(Thread.currentThread().getName()+" "+client+" success");
        return client;
    }

    @Override
    public Client save(Client client) throws DaoException {
        if (client.getId() == -1) {
            client = insert(client);
        } else {
            try {
                for (Account account : client.getAccounts()) {
                    accountDao.save(account);
                }
                Connection conn = openConnection();
                String sql = "update clients set name = (?), city = (?), email = (?), phone = (?), overdraft = (?), gender = (?)," +
                        " id_bank = (?) where id = (?)";
                final PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, client.getName());
                preparedStatement.setString(2, client.getCity());
                preparedStatement.setString(3, client.getEmail());
                preparedStatement.setString(4, client.getPhone());
                preparedStatement.setFloat(5, client.getOverdraft());
                preparedStatement.setString(6, String.valueOf(client.getGender().toString().toLowerCase().charAt(0)));
                preparedStatement.setLong(7, client.getBank().getId());
                preparedStatement.setLong(8, client.getId());
                log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

                if (preparedStatement.executeUpdate() == 0) {
                    log.log(Level.SEVERE, Thread.currentThread().getName()+" "+client+" error: ",
                            new DaoException("impossible to save client in db. transaction is rolled back"));
                    throw new DaoException("impossible to save client in db. transaction is rolled back");
                }
                preparedStatement.close();
                closeConnection(conn);
            } catch (SQLException e) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+client+
                        " error: impossible to save client in db. transaction is rolled back"+e.getMessage());
                throw new DaoException(e.getMessage());
            }
        }
        log.fine(Thread.currentThread().getName()+" "+client+" success");
        return client;
    }

    @Override
    public void remove(Client client) throws DaoException {
        try {
            try{
                accountDao.removeAllByClient(client);
            } catch (DaoException e){}

            Connection conn = openConnection();
            String sql = "delete from clients where id = (?)";
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, client.getId());
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (preparedStatement.executeUpdate() == 0) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+client+" error: ",
                        new DaoException("impossible to remove client in db. transaction is rolled back"));
                throw new DaoException("impossible to remove the client in db. transaction is rolled back");
            }
            preparedStatement.close();
            closeConnection(conn);
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" "+client+
                    " error: impossible to remove client in db. transaction is rolled back"+e.getMessage());
            throw new DaoException(e.getMessage());
        }
        log.fine(Thread.currentThread().getName()+" "+client+" success");
    }

    @Override
    public void removeAllByBank(Bank bank) throws DaoException {
        try {
            Connection conn = openConnection();
            String sql = "delete from clients where id_bank = (?)";
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());
            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (preparedStatement.executeUpdate() == 0) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                        new DaoException("impossible to remove client in db. transaction is rolled back"));
                throw new DaoException("impossible to remove the client in db. transaction is rolled back");
            }
            preparedStatement.close();
            closeConnection(conn);
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+
                    " error: impossible to remove client in db. transaction is rolled back", e.getMessage());
            throw new DaoException(e.getMessage());
        }
        log.fine(Thread.currentThread().getName()+" "+bank+" success");
    }

    @Override
    public List<Client> findClientsByNameAndCity(Bank bank, String name, String city) throws DaoException, SQLException {
        List<Client> clients = null;
        Connection conn = openConnection();
        String sql = "select * from clients where id_bank = (?) and name=(?) and city=(?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, city);

            log.fine(Thread.currentThread().getName() + " preparedStatement created: " + preparedStatement);

            if (!preparedStatement.execute()) {
                log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                        new DaoException("impossible to get clients from db."));
                throw new DaoException("impossible to get clients from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            clients = new ArrayList<>();
            while (rs.next()) {
                Client client = new Client();
                long idClient = rs.getLong(1);
                String email = rs.getString(4);
                String phone = rs.getString(5);
                float overdraft = rs.getFloat(6);
                String genderLetter = rs.getString(7);

                Gender gender;
                switch (genderLetter) {
                    case "m":
                        gender = Gender.MALE;
                        break;
                    case "f":
                        gender = Gender.FEMALE;
                        break;
                    default:
                        log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: ",
                                new DaoException("incorrect data in db, impossible to load the client"));
                        throw new DaoException("incorrect data in db, impossible to load the client");
                }
                client.setId(idClient);
                client.setName(name);
                client.setCity(city);
                client.setEmail(email);
                client.setPhone(phone);
                client.setOverdraft(overdraft);
                client.setGender(gender);
                client.setBank(bank);

                clients.add(client);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, Thread.currentThread().getName()+" "+bank+" error: "+e.getMessage());
            throw new DaoException(e.getMessage());
        }
        closeConnection(conn);

        for(Client client:clients){
            Set<Account> accounts = new HashSet<>(accountDao.getAllByClient(client));
            client.setAccounts(accounts);
        }
        log.fine(Thread.currentThread().getName()+" "+bank+" success");
        return clients;
    }
}
