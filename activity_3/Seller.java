package activity_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Seller extends Account{
    private static int sellerCount = 1;
    Bank bank;
    private ArrayList<Item> sellerItems;

    Seller(){
        super(generateSellerID());
        this.bank = new Bank();
        sellerItems = new ArrayList<>();
        sellerCount++;
    }

    private static String generateSellerID() {
        return "S#" + String.format("%03d", sellerCount);
    }

    @Override
    public void createAccount() {
        super.createAccount();
        System.out.println("Enter Routing number: ");
        bank.setRouteNum(getInput.nextLine());
        System.out.println("Here's your ID" + super.getAccount_ID());
        return;
    }

    public ArrayList<Item> getSellerItems() {
        return sellerItems;
    }

    public void addItem(){
        Item item = new Item();
        item.prompt();
        sellerItems.add(item);
    }

    public void removeItem(String item_ID){
        sellerItems.removeIf(item -> item.getItemID().equals(item_ID));
    }

    public void removeItem(){
        int i = 1;
        System.out.println("----------Remove Item------------");
        for(Item item : sellerItems){
            System.out.println(" ["+  i + "] " + item.getItemTitle());
            i++;
        }
        int option;
        option = Integer.parseInt(getInput.nextLine());
        sellerItems.remove(option-1);
        System.out.println("Item Successfully removed");
        System.out.println();
    }

    public void sellerItemList(){
        int i = 1;
        for(Item item : sellerItems){
            System.out.println(" ["+  i + "] " + item.getItemTitle());
            i++;
        }
        System.out.println();
    }

    public void renameItem(){
        System.out.println("---------Rename Item------------");
        sellerItemList();
        System.out.println("Enter: ");
        int option = Integer.parseInt(getInput.nextLine());
        Item product = sellerItems.get(option-1);
        System.out.println("Enter new name: ");
        String newName = getInput.nextLine();
        product.setName(newName);
        System.out.println();
    }
    public void setSellerBalance(Double amount){
        this.bank.setBalance(amount);
    }

    public void checkBankBalance(){
        System.out.println("-----------Bank Balance---------");
        System.out.println("Seller Balance: " +  this.bank.getBalance());
        System.out.println();
    }
    Scanner getInput = new Scanner(System.in);
}
