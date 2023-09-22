package activity_3;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    Transaction transaction = new Transaction();
    ArrayList<Account> accounts = new ArrayList<>();
    public void run(){
        int option;
        while(true) {
            System.out.println("Welcome to XYZ Auction");
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

            /*
            System.out.println("Here's your ID: ");
            System.out.println(account instanceof Seller ? ((Seller) account).getSellerID() : ((Buyer) account).getBuyerID());
            */

                accounts.add(account);
            } else {
                break;
            }
        }
    }

    private void login(ArrayList<Account> account) {

        boolean accountON = false;
        boolean idFound = false;
        String tempEmail, tempPass;

        System.out.println("----------------------");
        System.out.println("Enter your ID: ");
        String tempID = getInput.nextLine();

        Account user = account.stream()
                .filter(acc -> acc.getAccount_ID().equals(tempID))
                .findFirst()
                .orElse(null);

        if (user != null) {
            idFound = true;
            System.out.println("Enter Email: ");
            tempEmail = getInput.nextLine();

            System.out.println("Enter Password: ");
            tempPass = getInput.nextLine();

            if (user.getEmail().equals(tempEmail) && user.getPass().equals(tempPass)) {
                accountON = true;
                switch (account.getClass().getSimpleName().charAt(0)){
                    case 'B' -> buyerMenu(user, accounts, transaction);
                    case 'S' -> sellerMenu((Seller)user);
                }
            }
        }

        System.out.println(idFound ? accountON ? "Please Come Again!" : "Wrong Email or Password" : "ID not Found");
    }

    public void sellerMenu(Seller obj){
        System.out.println("Welcome to Seller Section: ");
        while(true) {
            int option;
            System.out.println("---------------------------------------------");
            System.out.println("[1] Add Item: ");
            System.out.println("[2] Remove Item: ");
            System.out.println("[3] List of Items: ");
            System.out.println("[4] Go Back: ");
            option = Integer.parseInt(getInput.nextLine());
            switch (option){
                case 1 :
                    obj.addItem();
                    break;
                case 2 :
                default:
                    return;
            }
        }
    }

    public void buyerMenu(Account account,  ArrayList<Account> list, Transaction transact){
        ArrayList<Seller> listOfSellers =   getListSellers();
        ArrayList<Item> listOfItems = getItemList(listOfSellers);

        System.out.println("Welcome to Buyer Section: ");
        while(true) {
            int option;
            System.out.println("[1] Browse Items");
            System.out.println("[2] Buy Item");
            System.out.println("[3] Check Bidding List");
            option = Integer.parseInt(getInput.nextLine());
            switch (option){
                case 1 : browseItems(listOfItems);
                case 2 : buyItem(listOfItems, account, transact);
                case 3 : transact.printTransactions();
            }
        }
    }

    public void buyItem(ArrayList<Item> itemsList, Account account, Transaction transaction){
        Buyer buyer = (Buyer)account;
        displayItems(itemsList);

        System.out.println("Enter the you want to place bid: ");
        int option = Integer.parseInt(getInput.nextLine());
        Item product = itemsList.get(option);

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
    };


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
            System.out.println('['+ i + "] " + item.getItemTitle());
        }
    }
    public void browseItems(ArrayList<Item> itemsList){
        System.out.println("Select to find more information: ");
        displayItems(itemsList);
        System.out.println("Enter: ");
        int option = Integer.parseInt(getInput.nextLine());
        for(int i = 0; i < itemsList.size(); i++){
            if(option-1 == i){
                itemsList.get(i).getItemDesc();
            }
        }
    }
    Scanner getInput = new Scanner(System.in);
}
