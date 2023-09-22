package activity_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Seller extends Account{
    private static int sellerCount = 1;
    private String seller_ID;
    Bank bank;
    private ArrayList<Item> sellerItems;

    Seller(){
        super("S#" + String.format("%03d", sellerCount));
        this.bank = new Bank();
        sellerItems = new ArrayList<>();
        sellerCount++;
    }

    @Override
    public void createAccount() {
        super.createAccount();
        System.out.println("Enter Routing number: ");
        bank.setRouteNum(getInput.nextLine());
        System.out.println("Here's your ID" + super.getAccount_ID());
        return;
    }

    public String getSellerID() {
        return seller_ID;
    }

    public ArrayList<Item> getSellerItems() {
        return sellerItems;
    }

    public void addItem(){
        Item item = new Item();
        item.prompt();
        sellerItems.add(item);
    }
    Scanner getInput = new Scanner(System.in);
}
