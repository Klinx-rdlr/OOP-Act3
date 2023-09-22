package activity_3;


import java.util.Scanner;

public class Item  {

    private String item_ID;
    private static int itemCount = 1;

    Item(){
        this.item_ID = generateItemID();
        bidStatus = false;
        itemCount++;
    }
    private static String generateItemID() {
        return "B#" + String.format("%03d", itemCount);
    }
    private String itemTitle;
    private String itemDesc;
    private String startDate;
    private String endDate;
    private String bidTime;

    private double bidIncrement;
    private double bidPrice;
    private double currentBidPrice;

    private boolean bidStatus;

    public void prompt(){
        System.out.println("Enter Item title: ");
        this.itemTitle = getInput.nextLine();
        System.out.println("Enter Item description: ");
        this.itemDesc = getInput.nextLine();
        System.out.println("Enter Start date for auction: ");
        this.startDate = getInput.nextLine();
        System.out.println("Enter End date for auction: ");
        this.endDate = getInput.nextLine();
        System.out.println("Enter the time of bid: ");
        this.bidTime = getInput.nextLine();
        System.out.println("Enter Starting bid price: ");
        this.bidPrice = Double.parseDouble(getInput.nextLine());
        System.out.println("Enter bidding increment: ");
        this.bidIncrement = Double.parseDouble(getInput.nextLine());
    }

    public boolean getStatus(){
        return bidStatus;
    }

    public void setStatus(boolean value){
        this.bidStatus = value;
    }
    public String getItemID() {
        return item_ID;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getCurrentBidPrice(){
        return currentBidPrice;
    }

    public double getBidPrice(){
        return bidPrice;
    }

    public double getBidIncrement(){
        return bidIncrement;
    }

    public void setName(String name){
        this.itemTitle = name;
    }
    public void itemDesc(){
        System.out.println("-------------------------");
        System.out.println("Item Title: " + "\t Item ID#: " + getItemTitle());
        System.out.println("Item ID#: " + getItemID());
        System.out.println("Item Description: " + getItemDesc());
        System.out.println("Item Start Auction Date: " + getStartDate());
        System.out.println("Item End Auction Date: " + getEndDate());
        System.out.println("Item Starting Bid: " + getBidPrice());
        System.out.println("Item Current Bid: " + getCurrentBidPrice());
        System.out.println("Item Bidding increment: " + getBidIncrement());
    }

    Scanner getInput = new Scanner(System.in);

    public void setCurrentBidPrice(double price) {
        this.currentBidPrice = price;
    }
}
