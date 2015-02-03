package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.MyClass;
import com.luxoft.bankapp.model.impl.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestService {

    @Test
    public void bankEquals(){
        Bank b1 = new Bank();
        b1.setName("my bank");

        Client client1 = createClient("harry", "dnepr", Gender.MALE, "111-1111111", "qqq@qq.qq", 1000);
        addAccount(AccountType.SAVING_ACCOUNT, 1200, client1);
        addAccount(AccountType.CHECKING_ACCOUNT, 2000, client1);
        b1.addClient(client1);

        Client client2 = createClient("mary", "moscow", Gender.FEMALE, "222-2222222", "www@ww.ww", 1500);
        addAccount(AccountType.SAVING_ACCOUNT, 2300, client2);
        addAccount(AccountType.CHECKING_ACCOUNT, 3100, client2);
        b1.addClient(client2);

        Bank b2 = new Bank();
        b2.setName("my bank");

        Client client3 = createClient("harry", "dnepr", Gender.MALE, "111-1111111", "qqq@qq.qq", 1000);
        addAccount(AccountType.SAVING_ACCOUNT, 1200, client3);
        addAccount(AccountType.CHECKING_ACCOUNT, 2000, client3);
        b2.addClient(client3);

        Client client4 = createClient("mary", "moscow", Gender.FEMALE, "222-2222222", "www@ww.ww", 1500);
        addAccount(AccountType.SAVING_ACCOUNT, 2300, client4);
        addAccount(AccountType.CHECKING_ACCOUNT, 3100, client4);
        b2.addClient(client4);

        assertTrue(isEquals(b1, b2));

    }

    @Test
    public void bankNotEquals(){
        Bank b1 = new Bank();
        b1.setName("my bank");

        Client client1 = createClient("harry", "dnepr", Gender.MALE, "111-1111111", "qqq@qq.qq", 1000);
        addAccount(AccountType.SAVING_ACCOUNT, 1200, client1);
        addAccount(AccountType.CHECKING_ACCOUNT, 2000, client1);
        b1.addClient(client1);

        Client client2 = createClient("mary", "moscow", Gender.FEMALE, "222-2222222", "www@ww.ww", 1500);
        addAccount(AccountType.SAVING_ACCOUNT, 2300, client2);
        addAccount(AccountType.CHECKING_ACCOUNT, 3100, client2);
        b1.addClient(client2);

        Bank b2 = new Bank();
        b2.setName("my bank");

        Client client3 = createClient("harry", "dnepr", Gender.MALE, "111-1111111", "qqq@qq.qq", 1000);
        addAccount(AccountType.SAVING_ACCOUNT, 1200, client3);
        addAccount(AccountType.CHECKING_ACCOUNT, 2000, client3);
        b2.addClient(client3);

        Client client4 = createClient("mary", "moscow", Gender.FEMALE, "222-2222232", "www@ww.ww", 1500);
        addAccount(AccountType.SAVING_ACCOUNT, 2300, client4);
        addAccount(AccountType.CHECKING_ACCOUNT, 3100, client4);
        b2.addClient(client4);

        assertFalse(isEquals(b1, b2));

    }

    private Client createClient(String name, String city, Gender gender, String phone, String email, float overdraft){
        Client client = new Client();
        client.setName(name);
        client.setGender(gender);
        client.setCity(city);
        client.setPhone(phone);
        client.setEmail(email);
        client.setOverdraft(overdraft);
        return client;
    }

    private void addAccount(AccountType accountType, float balance, Client client){
        Account account;
        switch (accountType){
            case SAVING_ACCOUNT:
                account = new SavingAccount();
                break;
            case CHECKING_ACCOUNT:
                account = new CheckingAccount();
                break;
            default:
                account = new SavingAccount();
        }
        account.setBalance(balance);
        client.addAccount(account);
    }

    private static boolean compareFields(Field f, Object o1, Object o2){
        boolean result = true;
        NoDB noDB = f.getAnnotation(NoDB.class);
        RefDB refDB = f.getAnnotation(RefDB.class);
        if(refDB!=null){
            try {
                f.setAccessible(true);
                Object ref1 = f.get(o1);
                Object ref2 = f.get(o2);
                Method getId = f.getType().getDeclaredMethod("getId");
                Object id1 = getId.invoke(ref1);
                Object id2 = getId.invoke(ref2);

                result = id1.equals(id2);

            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchMethodException e) {
                System.out.println(e.getMessage());
            } catch (InvocationTargetException e) {
                System.out.println(e.getMessage());
            }
        } else if(noDB==null){
            try {
                f.setAccessible(true);
                if(f.getType().isPrimitive() || f.getType().equals(String.class)){
                    Object fieldValue1 = f.get(o1);
                    Object fieldValue2 = f.get(o2);
                    if(!fieldValue1.equals(fieldValue2)){
                        return false;
                    }
                } else if (f.get(o1) instanceof Collection){
                    Collection col1 = (Collection) f.get(o1);
                    Collection col2 = (Collection) f.get(o2);
                    if(!col1.getClass().equals(col2.getClass())){
                        return false;
                    }
                    if(col1.size()!=col2.size()){
                        return false;
                    }
                    Iterator it1 = col1.iterator();
                    Iterator it2 = col2.iterator();

                    Object member1 = null;
                    if(it1.hasNext()){
                        member1 = it1.next();
                    }
                    Object member2 = null;
                    if(it2.hasNext()){
                        member2 = it2.next();
                    }
                    if(!member1.getClass().equals(member2.getClass())){
                        return false;
                    }
                    Object[]arr1 = col1.toArray();
                    Object[]arr2 = col2.toArray();
                    Arrays.sort(arr1);
                    Arrays.sort(arr2);
                    if(member1 instanceof MyClass){
                        for(int i = 0; i < arr1.length; ++i){
                            result = isEquals(arr1[i], arr2[i]);
                        }
                    }else{
                        result = Arrays.equals(arr1, arr2);
                    }
                } else if(f.getType().isEnum()){
                    Object enum1 = f.get(o1);
                    Object enum2 = f.get(o2);
                    result = enum1.equals(enum2);
                } else {
                    Class[]ints = f.getType().getInterfaces();
                    for(Class cl:ints){
                        if(cl.equals(MyClass.class)){
                            return isEquals(f.get(o1), f.get(o2));
                        } else {
                            result = f.get(o1).equals(f.get(o2));
                        }
                    }
                }


            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    public static boolean isEquals(Object o1, Object o2) {
        boolean result = false;
        Class cl1 = o1.getClass();
        Class cl2 = o2.getClass();
        if(!cl1.equals(cl2)){
            result = false;
        }
        Field []superClassFields = cl1.getSuperclass().getDeclaredFields();
        for(Field f:superClassFields){
            result = compareFields(f, o1, o2);
        }

        Field []fields = cl1.getDeclaredFields();
        for(Field f:fields){
            result = compareFields(f, o1, o2);
        }
        return result;
    }
}
