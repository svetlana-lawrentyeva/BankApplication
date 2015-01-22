package com.luxoft.bankapp.application.forrefact;

/**
 * Created by SCJP on 15.01.15.
 */
public class AddClientCommand //implements Command
{
//    private static final String NAME_PATTERN ="[a-zA-z]+([ '-][a-zA-Z]+)*";
//    private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//    private static final String PHONE_PATTERN="\\d{3}-\\d{7}";
//
//
//    @Override
//    public String execute(String param) {
//        Pattern pattern;
//        Matcher matcher;
//        Scanner sc = new Scanner(System.in);
//        System.out.println("city:");
//        String city = "";
//        while(city == ""){
//            city = sc.nextLine();
//        }
//        Client client = new Client(city);
//        while(client.getName()==null){
//            System.out.println("name:");
//            String name = sc.nextLine();
//        pattern = Pattern.compile(NAME_PATTERN);
//        matcher = pattern.matcher(name);
//        if(matcher.matches())
//            client.setName(name);
//        }
//
//        while(client.getGender()==null){
//            System.out.println("gender (m/f):");
//            String gender = sc.nextLine();
//        if(gender.equals("m"))client.setGender(Gender.MALE);
//        else if(gender.equals("f"))client.setGender(Gender.FEMALE);
//        }
//        while(client.getEmail()==null){
//            System.out.println("email:");
//            String email = sc.nextLine();
//            pattern = Pattern.compile(EMAIL_PATTERN);
//            matcher = pattern.matcher(email);
//            if(matcher.matches())
//                client.setEmail(email);
//        }
//        while(client.getPhone()==null){
//            System.out.println("phone:");
//            String phone = sc.nextLine();
//            pattern = Pattern.compile(PHONE_PATTERN);
//            matcher = pattern.matcher(phone);
//            if(matcher.matches())
//                client.setPhone(phone);
//        }
//        System.out.println("overdraft:");
//        float overdraft = sc.nextFloat();
//        client.setOverdraft(overdraft);
//
//        System.out.println("Client successfully added");
//        BankCommander.currentClient = client;
//        System.out.println(client);
//    }
//
//    @Override
//    public void printCommandInfo() {
//        System.out.println("Add client");
//    }
}
