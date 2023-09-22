package activity_3;

import java.util.Scanner;

public  class Account {

    private static String account_ID;
    Account(String ID){
        account_ID = ID;
    }

    private String email;
    private String password;
    private String phoneNum;
    private String homeAddress;

    public static Account getType(String type){
        return switch(type.toLowerCase()){
            case "buyer" -> new Buyer();
            case "seller" -> new Seller();
            default -> new Account("dummy");
        };
    }

    public void createAccount(){
        System.out.println("Enter Email Address: ");
        this.email = getInput.nextLine();
        System.out.println("Enter Password: ");
        this.password = getInput.nextLine();
        System.out.println("Enter Phone number: ");
        this.phoneNum = getInput.nextLine();
        System.out.println("Enter Home address: ");
        this.homeAddress = getInput.nextLine();
    };

    public String getEmail(){
        return email;
    }

    public String getPass(){
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getAccount_ID(){
        return account_ID;
    }
    Scanner getInput = new Scanner(System.in);
}
