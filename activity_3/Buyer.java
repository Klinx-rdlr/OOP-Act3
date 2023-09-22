package activity_3;

import java.util.ArrayList;

public class Buyer extends Account {
    private static int buyerCount = 1;
    private String shipAddress;
    private double balance;
    private ArrayList<Item> buyerItems;

    public Buyer() {
        super(generateBuyerID());
        buyerItems = new ArrayList<>();
        balance = 10000;
        buyerCount++;
    }

    private static String generateBuyerID() {
        return "B#" + String.format("%03d", buyerCount);
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double num){
        this.balance -= num;
    }
    @Override
    public void createAccount() {
        super.createAccount();
        System.out.println("Enter Shipping: ");
        this.shipAddress = getInput.nextLine();
        System.out.println("Here's your ID" + super.getAccount_ID());
        return;
    }

    public void checkItemList(){
        int i = 1;
        System.out.println("------------Items-----------");
        for(Item item : buyerItems){
            System.out.println(" ["+  i + "] " + item.getItemTitle());
            i++;
        }
    }

    public void addBuyerItem(Item item){
        buyerItems.add(item);
    }
}
