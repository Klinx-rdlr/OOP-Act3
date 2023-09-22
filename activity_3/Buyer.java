package activity_3;

public class Buyer extends Account {
    private String shipAddress;
    private double balance;
    private static int buyerCount = 1;
    private String buyer_ID;
    Buyer(){
        super( "B#" + String.format("%03d", buyerCount));
        balance = 10000;
        buyerCount++;
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
    public String getBuyerID() {
        return buyer_ID;
    }
    @Override
    public void createAccount() {
        super.createAccount();
        System.out.println("Enter Shipping: ");
        this.shipAddress = getInput.nextLine();
        System.out.println("Here's your ID" + super.getAccount_ID());
        return;
    }
}
