package test.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.MyClass;
import com.luxoft.bankapp.model.impl.*;
import junit.framework.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by SCJP on 02.02.15.
 */
public class TestService {

    @Test
    public void bankEquals(){

        Bank b1 = new Bank();
        b1.setName("my bank");

        Client client1 = new Client();
        client1.setName("harry");
        client1.setCity("dnepr");
        client1.setGender(Gender.MALE);

        Account account1 = new SavingAccount();
        account1.setBalance(10);
        client1.addAccount(account1);
        client1.setActiveAccount(account1);

        Account account11 = new CheckingAccount();
        account11.setBalance(20);
        client1.addAccount(account11);

        b1.addClient(client1);

        Bank b2 = new Bank();
        b2.setName("my bank");

        Client client2 = new Client();
        client2.setName("harry");
        client2.setCity("dnepr");
        client2.setGender(Gender.MALE);

        Account account2 = new SavingAccount();
        account2.setBalance(10);
        client2.addAccount(account2);
        client2.setActiveAccount(account2);

        Account account22 = new CheckingAccount();
        account22.setBalance(20);
        client2.addAccount(account22);

        b2.addClient(client2);

        assertTrue(isEquals(b1, b2));

    }
    @Test
    public void bankNotEquals(){
        Bank b1 = new Bank();
        b1.setName("my bank");

        Client client1 = new Client();
        client1.setName("harry");
        client1.setCity("dnepr");
        client1.setGender(Gender.MALE);

        Account account1 = new CheckingAccount();
        account1.setBalance(120);
        client1.addAccount(account1);
        client1.setActiveAccount(account1);

        Account account11 = new SavingAccount();
        account11.setBalance(20);
        client1.addAccount(account11);

        b1.addClient(client1);

        Bank b2 = new Bank();
        b2.setName("my bank");

        Client client2 = new Client();
        client2.setName("harry");
        client2.setCity("dnepr");
        client2.setGender(Gender.MALE);

        Account account2 = new SavingAccount();
        account2.setBalance(10);
        client2.addAccount(account2);
        client2.setActiveAccount(account2);

        Account account22 = new SavingAccount();
        account22.setBalance(20);
        client2.addAccount(account22);
        b2.addClient(client2);

        assertFalse(isEquals(b1, b2));

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
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
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
                            return isEquals(arr1[0], arr2[0]);
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
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean isEquals(Object o1, Object o2) {
        boolean result = false;
        Class cl1 = o1.getClass();
        Class cl2 = o2.getClass();
        if(!cl1.equals(cl2)){
            return false;
        }
        Field []superClassFields = cl1.getSuperclass().getDeclaredFields();
        for(Field f:superClassFields){
            result = compareFields(f, o1, o2);
            if(!result){
                return false;
            }
        }

        Field []fields = cl1.getDeclaredFields();
        for(Field f:fields){
            result = compareFields(f, o1, o2);
            if(!result){
                return false;
            }
        }
        return result;
    }
}
