package activity_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    Transaction transaction = new Transaction();
    ArrayList<Account> accounts = new ArrayList<>();
    public void run(){
        try {
            int option;
            while(true) {
                System.out.println("-----------Welcome to XYZ Auction-------------");
                System.out.println("[1] Login");
                System.out.println("[2] Create Account");
                System.out.println("[3] Exit");
                option = Integer.parseInt(getInput.nextLine());

                if (option == 1) {
                    login(accounts);
                } else if (option == 2) {
                    System.out.println("Are you a Buyer or Seller?: ");
                    Account account = Account.getType(getInput.nextLine());
                    account.createAccount();
                    accounts.add(account);
                }else {
                    break;
                }
            }

        }catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void login(ArrayList<Account> account) {

        String tempEmail;
        String tempPass;
        System.out.println("-----------Log In---------------");
        System.out.println("Enter your ID: ");
        String tempID = getInput.nextLine();
        Account user = getAccount(account, tempID);

        if (user != null) {
            System.out.println("Test");
            boolean validCredentials = false;
            while (!validCredentials) {
                System.out.println("----------------------------------");
                System.out.println("Enter Email: ");
                tempEmail = getInput.nextLine();

                System.out.println("Enter Password: ");
                tempPass = getInput.nextLine();

                if (user.getEmail().equals(tempEmail) && user.getPass().equals(tempPass)) {
                    validCredentials = true;
                    switch (user.getClass().getSimpleName().charAt(0)) {
                        case 'B':
                            buyerMenu((Buyer)user, accounts, transaction);
                            break;
                        case 'S':
                            sellerMenu((Seller) user, transaction);
                            break;
                    }
                } else {
                    System.out.println("Wrong Email or Password");
                    System.out.println("Do you want to try again? (yes/no)");
                    String userInput = getInput.nextLine();
                    if (userInput.equalsIgnoreCase("no")) {
                        break;   //}
                    } else {
                        System.out.println("User not found");
                    }
                }
            }
        }
    }

    public Account getAccount(ArrayList<Account> account, String tempID){
        for (Account temp : account) {
            if (temp.getAccount_ID().equals(tempID)) {
                return temp;
            }
        }
        return null;
    }

    public void sellerMenu(Seller seller, Transaction transaction){
        int option;
        System.out.println("-----------------------------------");
        System.out.println("Welcome to Seller Section: ");
        while(true) {
            System.out.println("---------------------------------------------");
            System.out.println("[1] Add Item: ");
            System.out.println("[2] Remove Item: ");
            System.out.println("[3] List of Items: ");
            System.out.println("[4] Manage Items: ");
            System.out.println("[5] Go Back: ");
            System.out.println("Enter: ");
            option = Integer.parseInt(getInput.nextLine());
            switch (option){
                case 1 -> seller.addItem();
                case 2 -> seller.removeItem();
                case 3 -> seller.sellerItemList();
                case 4 -> sellerOptions(seller, transaction);
                default -> {
                    return;
                }
            }
        }
    }

    public void sellerOptions(Seller seller, Transaction transaction) {
        int option;
        while(true) {
            System.out.println("---------------------------------------------");
            System.out.println("[1] End Bid: ");
            System.out.println("[2] Rename Item: ");
            System.out.println("[3] Check Account Balance: ");
            System.out.println("[4] Go Back: ");
            System.out.println("Enter: ");
            option = Integer.parseInt(getInput.nextLine());
            switch (option){
                case 1 -> endBid(seller, transaction);
                case 2 -> seller.renameItem();
                case 3 -> seller.checkBankBalance();
                default -> {
                    return;
                }
            }
        }
    }

    public void endBid(Seller seller, Transaction transaction){
        int option;

        System.out.println("------------Item List-------------");
        seller.sellerItemList();
        System.out.println("Enter: ");
        option = Integer.parseInt(getInput.nextLine());
        Item product = seller.getSellerItems().get(option-1);

        String buyer_Id = transaction.getHighestBid(product.getItemID(), seller);
        Buyer buyer = findBuyer(buyer_Id);
        buyer.addBuyerItem(product);
        seller.removeItem(product.getItemID());
        transaction.removeBid(product);
    }

    public Buyer findBuyer(String buyer_ID){
        for(Account account : accounts){
            if(account instanceof Buyer){
                if(account.getAccount_ID().equals(buyer_ID))
                    return (Buyer)account;
            }
        }
        return null;
    }


    public void buyerMenu(Buyer buyer,  ArrayList<Account> list, Transaction transact){
        ArrayList<Seller> listOfSellers = getListSellers();
        ArrayList<Item> listOfItems = getItemList(listOfSellers);

        System.out.println("-----------------------------------");
        System.out.println("Welcome to Buyer Section: ");
        while(true) {
            int option;
            System.out.println("-----------------------------------");
            System.out.println("[1] Browse Items");
            System.out.println("[2] Bid on Item");
            System.out.println("[3] Check Balance");
            System.out.println("[4] Check Your Items");
            System.out.println("[5] Check Bidding List");
            System.out.println("[6] Go Back");
            System.out.println("Enter: ");
            option = Integer.parseInt(getInput.nextLine());
            switch (option){
                case 1 -> browseItems(listOfItems);
                case 2 -> bidOnItem(listOfItems, buyer, transact);
                case 3 -> checkUserBalance(buyer);
                case 4 -> buyer.checkItemList();
                case 5 -> transact.printTransactions();
                default -> {
                    return;
                }
            }
        }
    }

    public void checkUserBalance(Buyer buyer){
        System.out.println("------------------------");
        System.out.println("Your remaining balance is: Php: " + buyer.getBalance());
    }

    public void bidOnItem(ArrayList<Item> itemsList, Buyer buyer, Transaction transaction){
        displayItems(itemsList);

        System.out.println("Enter the item ID you want to place bid: ");
        int option = Integer.parseInt(getInput.nextLine());
        Item product = itemsList.get(option-1);

        updatePrice(buyer, product);

        double customerBid = getItemPrice(product);
        transaction.addTransaction(product, buyer, customerBid);
    }

    public double getItemPrice(Item item){
        if(!item.getStatus()){
            return item.getBidPrice() + item.getBidIncrement();
        }else{
            return item.getCurrentBidPrice() + item.getBidPrice();
        }
    }

    public ArrayList<Seller> getListSellers(){
        ArrayList<Seller> sellerList = new ArrayList<>();
        for(Account account : accounts){
            if(account instanceof Seller){
                Seller temp = (Seller)account;
                sellerList.add(temp);
            }
        }
        return sellerList;
    }

    public ArrayList<Item> getItemList(ArrayList<Seller> listOfSeller){
        ArrayList<Item> itemList = new ArrayList<>();
        for(Seller seller : listOfSeller){
            itemList.addAll(seller.getSellerItems());
        }
        return itemList;
    }


    public void updatePrice(Buyer buyer, Item product){
        if(!product.getStatus()){
            buyer.setBalance(product.getBidPrice() - buyer.getBalance());
            product.setCurrentBidPrice(product.getBidPrice() + product.getBidIncrement());
            product.setStatus(true);
        }else{
            buyer.setBalance(product.getCurrentBidPrice() - buyer.getBalance());
            product.setCurrentBidPrice(product.getCurrentBidPrice() + product.getBidIncrement());
        }
    }

    public void displayItems(ArrayList<Item> itemsList){
        int i = 1;
        for(Item item : itemsList){
            System.out.println(" ["+  i + "] " + item.getItemTitle());
            i++;
        }
    }
    public void browseItems(ArrayList<Item> itemsList) {
        System.out.println("--------------------------------------------");
        System.out.println("Select an item to find more information: ");
        displayItems(itemsList);
        System.out.println("Enter: ");
        int option = Integer.parseInt(getInput.nextLine());

        if (option >= 1 && option <= itemsList.size()) {
            Item selectedItem = itemsList.get(option - 1);
            selectedItem.itemDesc();
        } else {
            System.out.println("Invalid option. Please try again.");
        }
    }

    Scanner getInput = new Scanner(System.in);
}
