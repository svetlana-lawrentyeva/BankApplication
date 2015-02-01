package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.ClientDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
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


public class ClientDaoImpl extends BaseDaoImpl implements ClientDao {

    @Override
    public Client getByName(Bank bank, String name) throws ClientNotExistsException, DaoException {
        Connection conn = openConnection();
        String sql = "select * from clients where id_bank = (?) and name = (?)";
        Client client = null;
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());
            preparedStatement.setString(2, name);

            if (!preparedStatement.execute()) {
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
            if (genderLetter.equals("m")) {
                gender = Gender.MALE;
            } else if (genderLetter.equals("f")) {
                gender = Gender.FEMALE;
            } else {
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

            Set<Account> accounts = new HashSet<>(DaoFactory.getAccountDao().getAllByClient(client));

            client.setAccounts(accounts);
            client.setBank(bank);

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
        return client;
    }

    @Override
    public Client getById(long idClient) throws ClientNotExistsException, DaoException {
        Connection conn = openConnection();
        String sql = "select * from clients where id = (?)";
        Client client = null;
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, idClient);

            if (!preparedStatement.execute()) {
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
                long idBank = rs.getLong(8);

                Gender gender;
                if (genderLetter.equals("m")) {
                    gender = Gender.MALE;
                } else if (genderLetter.equals("f")) {
                    gender = Gender.FEMALE;
                } else {
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


                Set<Account> accounts = new HashSet<>(DaoFactory.getAccountDao().getAllByClient(client));

                client.setAccounts(accounts);

                Bank bank = DaoFactory.getBankDao().getBankById(idBank);
                client.setBank(bank);
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
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

            if (!preparedStatement.execute()) {
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
                if (genderLetter.equals("m")) {
                    gender = Gender.MALE;
                } else if (genderLetter.equals("f")) {
                    gender = Gender.FEMALE;
                } else {
                    throw new DaoException("incorrect data in db, impossible to load the client");
                }

                client.setId(idClient);
                client.setName(clientName);
                client.setCity(city);
                client.setEmail(email);
                client.setPhone(phone);
                client.setOverdraft(overdraft);
                client.setGender(gender);

                Set<Account> accounts = new HashSet<>(DaoFactory.getAccountDao().getAllByClient(client));

                client.setAccounts(accounts);

                clients.add(client);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
        return clients;
    }

    private Client insert(Client client) throws DaoException {
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

            if (preparedStatement.executeUpdate() == 0) {
                throw new DaoException("impossible to save account in db. transaction is rolled back");
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs == null || !rs.next()) {
                throw new DaoException("impossible to save the client in db. transaction is rolled back");
            }
            Long id = rs.getLong(1);
            rs.close();
            preparedStatement.close();
            client.setId(id);

            for (Account account : client.getAccounts()) {
                DaoFactory.getAccountDao().save(account);
                DaoFactory.getAccountDao().addAccountToClient(client, account);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
        return client;
    }

    @Override
    public Client save(Client client) throws DaoException {
        if (client.getId() == -1) {
            client = insert(client);
        } else {
            try {
                for (Account account : client.getAccounts()) {
                    DaoFactory.getAccountDao().save(account);
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

                if (preparedStatement.executeUpdate() == 0) {
                    throw new DaoException("impossible to save client in db. transaction is rolled back");
                }
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
        closeConnection();
        return client;
    }

    @Override
    public void remove(Client client) throws DaoException {
        try {
            DaoFactory.getAccountDao().removeAllByClient(client);

            Connection conn = openConnection();
            String sql = "delete from clients where id = (?)";
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, client.getId());

            if (preparedStatement.executeUpdate() == 0) {
                throw new DaoException("impossible to remove the client in db. transaction is rolled back");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
    }

    @Override
    public void removeAllByBank(Bank bank) throws DaoException {
        try {

            Connection conn = openConnection();
            String sql = "delete from clients where id_bank = (?)";
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());

            if (preparedStatement.executeUpdate() == 0) {
                throw new DaoException("impossible to remove the client in db. transaction is rolled back");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        closeConnection();
    }

    @Override
    public void addClientToBank(Bank bank, Client client) throws DaoException {
        Connection conn = openConnection();

        String sql = "update clients set id_bank = (?) where id = (?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setLong(1, bank.getId());
            preparedStatement.setLong(2, client.getId());

            if(preparedStatement.executeUpdate() == 0){
                throw new DaoException("impossible to add client. transaction is rolled back");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException("error while adding client "+ client.getId() + " to bank " + client.getId());
        }

        closeConnection();
    }
}
