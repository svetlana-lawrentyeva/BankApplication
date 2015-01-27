package com.luxoft.bankapp.dao.impl;

import com.luxoft.bankapp.dao.AccountDao;
import com.luxoft.bankapp.dao.BaseDao;
import com.luxoft.bankapp.dao.exceptions.DaoException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.exceptions.ClientNotExistsException;
import com.luxoft.bankapp.model.impl.Bank;
import com.luxoft.bankapp.model.impl.CheckingAccount;
import com.luxoft.bankapp.model.impl.Client;
import com.luxoft.bankapp.dao.ClientDao;
import com.luxoft.bankapp.model.impl.SavingAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by SCJP on 27.01.15.
 */
public class ClientDaoImpl implements ClientDao {

    private BaseDao baseDao = new BaseDaoImpl();

    @Override
    public Client findClientByName(Bank bank, String name) throws ClientNotExistsException, DaoException {
        Connection conn = baseDao.openConnection();
        String sql = "select * from clients where id_bank = (?) and name = (?)";
        Client client = null;
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());
            preparedStatement.setString(2, name);

            if(!preparedStatement.execute()){
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
            long idActiveAccount = rs.getLong(8);

            Gender gender;
            if(genderLetter.equals("m")){
                gender = Gender.MALE;
            } else if(genderLetter.equals("f")){
                gender = Gender.FEMALE;
            } else {
                throw new DaoException("incorrect data in db, impossible to load the client");
            }

            AccountDao accountDao = new AccountDaoImpl();
            Set<Account> accounts = new HashSet<>(accountDao.getClientAccounts(idClient));

            Account activeAccount = accountDao.findAccountById(idActiveAccount);

            client = new Client();
            client.setId(idClient);
            client.setName(clientName);
            client.setCity(city);
            client.setEmail(email);
            client.setPhone(phone);
            client.setOverdraft(overdraft);
            client.setGender(gender);
            client.setAccounts(accounts);
            client.setActiveAccount(activeAccount);

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        baseDao.closeConnection();
        return client;
    }

    @Override
    public Client findClientById(int id) throws ClientNotExistsException, DaoException {
        Connection conn = baseDao.openConnection();
        String sql = "select * from clients where id = (?)";
        Client client = null;
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            if(!preparedStatement.execute()){
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
            long idActiveAccount = rs.getLong(8);

            Gender gender;
            if(genderLetter.equals("m")){
                gender = Gender.MALE;
            } else if(genderLetter.equals("f")){
                gender = Gender.FEMALE;
            } else {
                throw new DaoException("incorrect data in db, impossible to load the client");
            }

            AccountDao accountDao = new AccountDaoImpl();
            Set<Account> accounts = new HashSet<>(accountDao.getClientAccounts(idClient));

            Account activeAccount = accountDao.findAccountById(idActiveAccount);

            client.setId(idClient);
            client.setName(clientName);
            client.setCity(city);
            client.setEmail(email);
            client.setPhone(phone);
            client.setOverdraft(overdraft);
            client.setGender(gender);
            client.setAccounts(accounts);
            client.setActiveAccount(activeAccount);

            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        baseDao.closeConnection();
        return client;
    }

    @Override
    public List<Client> getAllClients(Bank bank) throws DaoException {
        List<Client>clients = null;
        Connection conn = baseDao.openConnection();
        String sql = "select * from clients where id_bank = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, bank.getId());

            if(!preparedStatement.execute()){
                throw new DaoException("impossible to get clients from db.");
            }
            ResultSet rs = preparedStatement.getResultSet();
            clients = new ArrayList<>();
            while(rs.next()){

                Client client = new Client();
                long idClient = rs.getLong(1);
                String clientName = rs.getString(2);
                String city = rs.getString(3);
                String email = rs.getString(4);
                String phone = rs.getString(5);
                float overdraft = rs.getFloat(6);
                String genderLetter = rs.getString(7);
                long idActiveAccount = rs.getLong(8);

                Gender gender;
                if(genderLetter.equals("m")){
                    gender = Gender.MALE;
                } else if(genderLetter.equals("f")){
                    gender = Gender.FEMALE;
                } else {
                    throw new DaoException("incorrect data in db, impossible to load the client");
                }

                AccountDao accountDao = new AccountDaoImpl();
                Set<Account> accounts = new HashSet<>(accountDao.getClientAccounts(idClient));

                Account activeAccount = accountDao.findAccountById(idActiveAccount);

                client.setId(idClient);
                client.setName(clientName);
                client.setCity(city);
                client.setEmail(email);
                client.setPhone(phone);
                client.setOverdraft(overdraft);
                client.setGender(gender);
                client.setAccounts(accounts);
                client.setActiveAccount(activeAccount);

                clients.add(client);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        baseDao.closeConnection();
        return clients;
    }

    @Override
    public void save(Client client) throws DaoException {
        Connection conn = baseDao.openConnection();
        String sql = "insert into clients (name, city, email, phone, overdraft, balance, gender)"+
                " values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getCity());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setFloat(5, client.getOverdraft());
            preparedStatement.setFloat(6, client.getBalance());
            preparedStatement.setString(7, String.valueOf(client.getGender().getClass().getSimpleName().toLowerCase().charAt(0)));

            if(preparedStatement.executeUpdate() == 0){
                throw new DaoException("impossible to save account in db. transaction is rolled back");
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs == null || !rs.next()){
                throw new DaoException("impossible to save the client in db. transaction is rolled back");
            }
            Long id = rs.getLong(1);
            rs.close();
            preparedStatement.close();
            client.setId(id);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        baseDao.closeConnection();
    }

    @Override
    public void remove(Client client) throws DaoException {
        Connection conn = baseDao.openConnection();
        String sql = "delete from clients where id = (?)";
        try {
            final PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, client.getId());

            if(preparedStatement.executeUpdate() == 0){
                throw new DaoException("impossible to remove the client in db. transaction is rolled back");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        baseDao.closeConnection();
    }
}