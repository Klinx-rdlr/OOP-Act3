


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Main {
    HashMap<String, HashMap<String, ArrayList<String>>> accounts;
    /*Account ID (Key of OuterMap accounts) - Email (Key of InnerMap accounts)
    Account Details Index No : (ArrayList - value of InnerMap accounts)
    (If Buyer 0- Password, 1- Phone Number, 2- Home Address, 3- Shipping Address, 4- Balance
    (If Seller)  0- Password, 1- Phone Number, 2- Home Address, 3 - Routing Number, 4- Bank number
    */

    HashMap<String, HashMap<String, ArrayList<String>>> sellerItems;
    /*Seller ID (Key of OuterMap sellerItems) - Item ID (Key of InnerMap sellerItems)
 Item Details Index No : (ArrayList - value of InnerMap sellerItems)
 (0- foreignKey, 1- title, 2- Description, 3- Start Date, 4- End Data, 5- Bid Time, 6- current bid, 7- bid increment)
 */
    HashMap<String, HashMap<String, ArrayList<String>>> buyerStorage;
    /*Buyer ID (Key of OuterMap sellerItems) - Item ID (Key of InnerMap sellerItems)
Item Details Index No : (ArrayList - value of InnerMap sellerItems)
(0- foreignKey, 1- title, 2- Description, 3- Start Date, 4- End Data, 5- Bid Time, 6- current bid, 7- bid increment, 8-FeedBack)
*/

    HashMap<String, HashMap<String, ArrayList<String>>> transactions;
    /*Seller ID (Key of OuterMap transactions) - Buyer ID (Key of InnerMap transactions)
- ((Transaction Details(Bid Amount and Time of Bid) - value of InnerMap sellerItems))
*/

     // Container for seller and buyer balance
    HashMap<String, Double> sellerBalance;

    // Storing transactions
    Object[][] Transactions;
    HashMap<String, Double> buyerBalance;
    HashMap<String, HashMap<String, String>> sellerFeedbackList;
    Scanner getInput;

    int sellerCounter = 1;
    int buyerCounter = 1;
    int itemCounter = 1;


    public Main() {
        Transactions = new Object[3][1]; //Limited to 3 Seller and Buyers only
        accounts = new HashMap<>();
        sellerItems = new HashMap<>();
        transactions = new HashMap<>();
        sellerBalance = new HashMap<>();
        buyerBalance = new HashMap<>();
        buyerStorage = new HashMap<>();
        sellerFeedbackList = new HashMap<>();
        getInput = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.Menu();
    }


    // Used for setting unique id's
    public String setSellerID(int sellerCounter) {
        return "S#" + String.format("%03d", sellerCounter);
    }

    public String setBuyerID(int buyerCounter) {
        return "B#" + String.format("%03d", buyerCounter);
    }

    public String setItemID(int itemCounter) {
        return "I#" + String.format("%03d", itemCounter);
    }

    boolean idChecker(String userID) {
        return accounts.containsKey(userID);
    }


    public String getSellerID(String itemID) {
        for (Map.Entry<String, HashMap<String, ArrayList<String>>> sellerEntry : sellerItems.entrySet()) {
            HashMap<String, ArrayList<String>> innerMap = sellerEntry.getValue();
            if (innerMap.containsKey(itemID)) {
                return sellerEntry.getKey();
            }
        }
        return null;
    }


    // Validation functions
    boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return password.matches(passwordRegex);
    }

    boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\d{10}$";
        return phoneNumber.matches(phoneRegex);
    }

    boolean isValidRoutingNumber(String routingNumber) {
        String routingRegex = "^\\d{9}$";
        return routingNumber.matches(routingRegex);
    }
    public void Menu () {
        try {
            int option;
            while (true) {
                System.out.println("-----------Welcome to XYZ Auction-------------");
                System.out.println("[1] Login");
                System.out.println("[2] Create Account");
                System.out.println("[3] Exit");
                option = Integer.parseInt(getInput.nextLine());

                if (option == 1) {
                    login();
                } else if (option == 2) {
                    createAccount();
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void login() {

        String tempEmail;
        String tempPass;
        System.out.println("-----------Log In---------------");
        System.out.println("Enter your ID: ");
        String userID = getInput.nextLine();
        boolean checkerID = idChecker(userID);

        if (checkerID) {
            System.out.println("Test");
            boolean validCredentials = false;

            while (!validCredentials) {
                System.out.println("----------------------------------");
                System.out.println("Enter Email: ");
                tempEmail = getInput.nextLine();

                System.out.println("Enter Password: ");
                tempPass = getInput.nextLine();

                HashMap<String, ArrayList<String>> innerMap = accounts.get(userID);
                if (innerMap.containsKey(tempEmail) && innerMap.get(tempEmail).get(0).equals(tempPass)) {
                    validCredentials = true;

                    //Used for accessing menu, depending on the type of the account
                    switch (userID.charAt(0)) {
                        case 'B' -> buyerMenu(userID);
                        case 'S' -> sellerMenu(userID);
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

    void sellerPrompt(int counter) {
        String accountID = setSellerID(counter);
        if (!accounts.containsKey(accountID)) {
            String email, password, phoneNumber, homeAddress, routingNum;
            ArrayList<String> tempDetails = new ArrayList<>();
            HashMap<String, ArrayList<String>> tempData = new HashMap<>();

            System.out.println("Enter Email Address: ");
            email = getInput.nextLine();
            while (!isValidEmail(email)) {
                System.out.println("Invalid Email Address. Please enter a valid email:");
                email = getInput.nextLine();
            }

            System.out.println("Enter Password: ");
            password = getInput.nextLine();
            while (!isValidPassword(password)) {
                System.out.println("Invalid Password. Password must be at least 8 characters and contain at least one uppercase letter, one lowercase letter, and one digit:");
                password = getInput.nextLine();
            }

            System.out.println("Enter Phone number +63: ");
            phoneNumber = getInput.nextLine();
            while (!isValidPhoneNumber(phoneNumber)) {
                System.out.println("Invalid Phone number. Please enter a valid phone number:");
                phoneNumber = getInput.nextLine();
            }

            System.out.println("Enter Home address: ");
            homeAddress = getInput.nextLine();

            System.out.println("Enter Routing number: ");
            routingNum = getInput.nextLine();
            while (!isValidRoutingNumber(routingNum)) {
                System.out.println("Invalid Routing number. Routing number must be a 9-digit number:");
                routingNum = getInput.nextLine();
            }

            // Used for storing the data to sellerItems, and for initializing balance for seller
            tempDetails.add(password);
            tempDetails.add(phoneNumber);
            tempDetails.add(homeAddress);
            tempDetails.add(routingNum);
            tempData.put(email, tempDetails);
            accounts.put(accountID, tempData);
            sellerBalance.put(accountID, 0.00);
            HashMap<String, ArrayList<String>> sellerItemMap = new HashMap<>();
            sellerItems.put(accountID, sellerItemMap);

            System.out.println("Your Account ID is: " + accountID);
            sellerCounter++;
        } else {
            System.out.println("Account Already Exists");
        }
    }


    // Option for buyers
    void buyerPrompt(int counter) {
        String accountID = setBuyerID(counter);
        if (!accounts.containsKey(accountID)) {
            String email, password, phoneNumber, homeAddress, shippingAddress;
            String balance = "10000";
            ArrayList<String> tempDetails = new ArrayList<>();
            HashMap<String, ArrayList<String>> tempData = new HashMap<>();

            System.out.println("Enter Email Address: ");
            email = getInput.nextLine();
            while (!isValidEmail(email)) {
                System.out.println("Invalid Email Address. Please enter a valid email:");
                email = getInput.nextLine();
            }

            System.out.println("Enter Password: ");
            password = getInput.nextLine();
            while (!isValidPassword(password)) {
                System.out.println("Invalid Password. Password must be at least 8 characters and contain at least one uppercase letter, one lowercase letter, and one digit:");
                password = getInput.nextLine();
            }

            System.out.println("Enter Phone number +63: ");
            phoneNumber = getInput.nextLine();
            while (!isValidPhoneNumber(phoneNumber)) {
                System.out.println("Invalid Phone number. Please enter a valid phone number:");
                phoneNumber = getInput.nextLine();
            }

            System.out.println("Enter Home address: ");
            homeAddress = getInput.nextLine();

            System.out.println("Enter Shipping address: ");
            shippingAddress = getInput.nextLine();

            // Used for storing the data to buyerStorage
            tempDetails.add(password);
            tempDetails.add(phoneNumber);
            tempDetails.add(homeAddress);
            tempDetails.add(shippingAddress);
            tempDetails.add(balance);
            tempData.put(email, tempDetails);
            accounts.put(accountID, tempData);
            buyerBalance.put(accountID, Double.parseDouble(balance));
            HashMap<String, ArrayList<String>> buyerItemMap = new HashMap<>();
            buyerStorage.put(accountID, buyerItemMap);

            System.out.println("Your Account ID is: " + accountID);
            buyerCounter++;
        } else {
            System.out.println("Account Already Exists");
        }
    }

        void createAccount () {
            int accountType;

            System.out.println("---------------------");
            System.out.println("Select Account Type: ");
            System.out.println("[1] Buyer");
            System.out.println("[2] Seller");
            System.out.println("Enter: ");
            accountType = Integer.parseInt(getInput.nextLine());
            if (accountType == 1) {
                buyerPrompt(buyerCounter);
            } else {
                sellerPrompt(sellerCounter);
            }
        }

        public void sellerMenu (String sellerID){
            int option;
            System.out.println("-----------------------------------");
            System.out.println("Welcome to Seller Section: ");
            while (true) {
                System.out.println("---------------------------------------------");
                System.out.println("[1] Add Item: ");
                System.out.println("[2] Remove Item: ");
                System.out.println("[3] List of Items: ");
                System.out.println("[4] Manage Items: ");
                System.out.println("[5] Feedbacks: ");
                System.out.println("[6] Log Out: ");
                System.out.println("Enter: ");
                option = Integer.parseInt(getInput.nextLine());
                switch (option) {
                    case 1 -> addItem(sellerID);
                    case 2 -> removeItem(sellerID);
                    case 3 -> printItems(sellerID);
                    case 4 -> sellerOptions(sellerID);
                    case 5 -> sellerPrintFeedback(sellerID);
                    default -> {
                        return;
                    }
                }
            }
        }

        // Option for sellers
        public void sellerOptions (String sellerID){
            int option;
            while (true) {
                System.out.println("---------------------------------------------");
                System.out.println("[1] End Bid: ");
                System.out.println("[2] Rename Item: ");
                System.out.println("[3] Check Account Balance: ");
                System.out.println("[4] Go Back: ");
                System.out.println("Enter: ");
                option = Integer.parseInt(getInput.nextLine());
                switch (option) {
                    case 1 -> endBid(sellerID);
                    case 2 -> renameItem(sellerID);
                    case 3 -> checkSellerBalance(sellerID);
                    default -> {
                        return;
                    }
                }
            }
        }

        void addItem (String sellerID){

            String itemID = setItemID(itemCounter);
            String title, description, startDate, endDate, bidTime, currentBidPrice, bidIncrement;

            System.out.println("Enter Item title: ");
            title = getInput.nextLine();
            System.out.println("Enter Item description: ");
            description = getInput.nextLine();
            System.out.println("Enter Start date for auction: ");
            startDate = getInput.nextLine();
            System.out.println("Enter End date for auction: ");
            endDate = getInput.nextLine();
            System.out.println("Enter the time of bid: ");
            bidTime = getInput.nextLine();
            System.out.println("Enter Starting bid price: ");
            currentBidPrice = getInput.nextLine();
            System.out.println("Enter bidding increment: ");
            bidIncrement = getInput.nextLine();

            ArrayList<String> itemList = new ArrayList<>();
            //Indexing guide, proper arrangement for the Arraylist item details holder
            itemList.add(sellerID); //0
            itemList.add(title); //1
            itemList.add(description); //2
            itemList.add(startDate); //3
            itemList.add(endDate); // 4
            itemList.add(bidTime); //5
            itemList.add(currentBidPrice); //6
            itemList.add(bidIncrement); // 7


            HashMap<String, ArrayList<String>> innerMap;
            if (sellerItems.containsKey(sellerID)) {
                innerMap = sellerItems.get(sellerID);
            } else {
                innerMap = new HashMap<>();
                sellerItems.put(sellerID, innerMap);
            }

            innerMap.put(itemID, itemList);
            itemCounter++;
        }


        void printItems (String sellerID){
            System.out.println("------------List of Items--------------");
            if(sellerItems.containsKey(sellerID)) {
                int i = 1;
                System.out.println("Item Title: --- Item ID: ");
                HashMap<String, ArrayList<String>> tempItem = sellerItems.get(sellerID);
                for (String itemID : tempItem.keySet()) {
                    System.out.println("[" + i + "] " + tempItem.get(itemID).get(1) + " ---- " + itemID);
                    i++;
                }
            }else {
                System.out.println("No items found");
            }
        }

        public void removeItem (String sellerID){
            if(sellerItems.containsKey(sellerID)){
                System.out.println("----------Remove Item------------");
                printItems(sellerID);

                String itemName;
                System.out.println("Enter item id: ");
                itemName = getInput.nextLine();

                if (sellerItems.get(sellerID).get(itemName) != null) {
                    sellerItems.get(sellerID).remove(itemName);
                    System.out.println("Item Successfully removed");

                } else {
                    System.out.println("Item not found");
                }
            }else {
                System.out.println("No Item found");
            }

            System.out.println();
        }

        public void renameItem (String sellerID){
            if(sellerItems.containsKey(sellerID)) {
                System.out.println("---------Rename Item------------");
                printItems(sellerID);
                System.out.println("Enter: item id");

                String itemID = getInput.nextLine();


                System.out.println("Enter new name: ");
                String newName = getInput.nextLine();

                sellerItems.get(sellerID).get(itemID).set(1, newName);
                System.out.println();
            }else {
                System.out.println("No Items found");
            }
        }

        void checkSellerBalance (String sellerID){
            System.out.println("------------------------");
            System.out.println("Your remaining balance is: Php: " + sellerBalance.get(sellerID));
        }

        public HashMap<String, Double> getHighestBid ( String itemID){
            double highestBid = 0.0;
            String bidder = "";

            HashMap<String, ArrayList<String>> innerMap = transactions.get(itemID);
            for (Map.Entry<String, ArrayList<String>> bidEntry : innerMap.entrySet()) {
                double currentBid = Double.parseDouble(bidEntry.getValue().get(1));
                if (currentBid > highestBid) {
                    highestBid = currentBid;
                    bidder = bidEntry.getKey();
                }
            }

            HashMap<String, Double> tempDetails = new HashMap<>();
            tempDetails.put(bidder, highestBid);

            return tempDetails;
        }

        void endBid (String sellerID){
                System.out.println("------------------------");
                if (sellerItems.containsKey(sellerID)) {
                    printItems(sellerID);
                    System.out.println("Enter the Item ID you want to end bid: ");
                    String itemID = getInput.nextLine();


                    //Getting information from the winner of the bid
                    HashMap<String, Double> tempDetails = getHighestBid(itemID);
                    String buyerID = tempDetails.keySet().iterator().next();
                    Double payment = tempDetails.get(buyerID);
                    addSellerBalance(sellerID, payment);


                    //Deducting the bid to the winner's balance
                    double newBalance = buyerBalance.get(buyerID) - payment;
                    buyerBalance.replace(buyerID, newBalance);
                    
                    //Adding the Item to the winner's storage
                    ArrayList<String> tempData = sellerItems.get(sellerID).get(itemID);
                    HashMap<String, ArrayList<String>> itemCopy = new HashMap<>();
                    itemCopy.put(itemID, tempData);
                    if (!buyerStorage.containsKey(buyerID)) {
                        buyerStorage.put(buyerID, itemCopy);
                    } else {
                        buyerStorage.get(buyerID).put(itemID, tempData);
                    }

                    System.out.println("Item bid successfully ended");
                    //Removing the sold item from the transaction and seller items
                    transactions.remove(itemID);
                    sellerItems.get(sellerID).remove(itemID);
                }else{
                    System.out.println("No Items Found");
                }
        }

        void addSellerBalance (String sellerID,double payment){
            sellerBalance.replace(sellerID, payment);
        }


        public void buyerMenu (String buyerID){

            System.out.println("-----------------------------------");
            System.out.println("Welcome to Buyer Section: ");
            while (true) {
                int option;
                System.out.println("-----------------------------------");
                System.out.println("[1] Browse Items");
                System.out.println("[2] Check Item Details");
                System.out.println("[3] Bid on Item");
                System.out.println("[4] Check Balance");
                System.out.println("[5] Check Your Items");
                System.out.println("[6] Check Bidding List");
                System.out.println("[7] Log out");
                System.out.println("Enter: ");
                option = Integer.parseInt(getInput.nextLine());
                switch (option) {
                    case 1 -> browseItems();
                    case 2 -> checkItemDetails();
                    case 3 -> bidOnItem(buyerID);
                    case 4 -> checkBuyerBalance(buyerID);
                    case 5 -> buyerPrintItems(buyerID);
                    case 6 -> checkBiddingList();
                    default -> {
                        return;
                    }
                }
            }
        }

    void checkItemDetails(){
        System.out.println("----------------Check Details---------------");
        String itemID, sellerID;
        if(browseItems()){
            System.out.println("Enter Item ID: ");
            itemID = getInput.nextLine();
            System.out.println("Enter Seller ID: ");
            sellerID = getInput.nextLine();
            ArrayList<String> itemCopy = sellerItems.get(sellerID).get(itemID);
            System.out.println("-------------------------");
            System.out.println("Item Title: " + itemCopy.get(1));
            System.out.println("Item Description: " + itemCopy.get(2));
            System.out.println("Item Start Auction Date: " + itemCopy.get(3));
            System.out.println("Item End Auction Date: " + itemCopy.get(4));
            System.out.println("Item Start time of bid:  " + itemCopy.get(5));
            System.out.println("Item Starting Bid:  " + itemCopy.get(6));
            System.out.println("Item Bidding increment: " + itemCopy.get(7));
        }else{
            System.out.println("No item found");
        }
    }
    boolean browseItems() {
        if (sellerItems.isEmpty()) {
            System.out.println("No items found");
            return false;
        }

        System.out.println("----------------List of Items----------------");
        System.out.println("Item Title: -------- \tItem ID: -------- \tSeller ID: ");

        int i = 1;
        for (Map.Entry<String, HashMap<String, ArrayList<String>>> sellerEntry : sellerItems.entrySet()) {
            String sellerID = sellerEntry.getKey();
            HashMap<String, ArrayList<String>> itemMap = sellerEntry.getValue();

            for (Map.Entry<String, ArrayList<String>> itemEntry : itemMap.entrySet()) {
                String itemID = itemEntry.getKey();
                ArrayList<String> itemDetails = itemEntry.getValue();
                String itemTitle = itemDetails.get(1);

                System.out.println("[" + i + "] " + itemTitle + " ---- \t" + itemID + "-----\t" + sellerID);
                i++;
            }
        }
        System.out.println();
        return true;
    }

    public void bidOnItem(String buyerID) {
        if (browseItems()) {
            System.out.println("Enter the \"ITEM ID\" you want to place bid: ");
            String itemID = getInput.nextLine();
            String sellerID = getSellerID(itemID);

            if (sellerItems.get(sellerID).containsKey(itemID)) {
                ArrayList<String> itemData = sellerItems.get(sellerID).get(itemID);
                double currentBidPrice = Double.parseDouble(itemData.get(6));
                double bidIncrement = Double.parseDouble(itemData.get(7));

                System.out.println("Enter your bid amount: ");
                double bidAmount = Double.parseDouble(getInput.nextLine());

                if (bidAmount > currentBidPrice) {
                    double nextPrice = bidAmount + bidIncrement;
                    itemData.set(6, Double.toString(nextPrice));

                    System.out.println("Enter time of bid: ");
                    String timeOfBid = getInput.nextLine();
                    String transactionMsg = "Bid added by: " + buyerID + " --- Amount: " + bidAmount + " --- Time: " + timeOfBid;

                    HashMap<String, ArrayList<String>> tempData = transactions.getOrDefault(itemID, new HashMap<>());
                    ArrayList<String> tempDetails = new ArrayList<>();
                    tempDetails.add(buyerID); // index 0
                    tempDetails.add(Double.toString(bidAmount)); // index 1
                    tempDetails.add(transactionMsg); // index 2
                    tempData.put(buyerID, tempDetails);
                    transactions.put(itemID, tempData);
                } else {
                    System.out.println("Your bid should be higher than the current bid price.");
                }
            } else {
                System.out.println("Item not found");
            }
        }
    }

        void checkBuyerBalance (String buyerID){
            System.out.println("------------------------");
            System.out.println("Your remaining balance is: Php: " + buyerBalance.get(buyerID));
        }

        void buyerAddFeedback(String buyerID, String sellerID, String itemID, String itemTitle){
            String rating, comment, feedBackMsg;
            System.out.println("-----------Rate the seller's item------------");
            System.out.println("Enter your rating 1-10: ");
            rating = getInput.nextLine();
            System.out.println("Enter your comment: ");
            comment = getInput.nextLine();

            feedBackMsg = "Bought By: " + buyerID + "\n Item Title: " + itemTitle +"\nRating: " + rating + '\n' + "Message: " + comment;

            HashMap<String, String> feedBackCopy = new HashMap<>();
            feedBackCopy.put(itemID, feedBackMsg);

            if(!sellerFeedbackList.containsKey(sellerID)){
                sellerFeedbackList.put(sellerID, feedBackCopy);
            }else{
                sellerFeedbackList.get(sellerID).put(itemID, feedBackMsg);
            }
        }

    void sellerPrintFeedback(String sellerID) {
        System.out.println("----------Sold Items-----------");
        if (sellerFeedbackList.containsKey(sellerID)) {
            HashMap<String, String> innerMap = sellerFeedbackList.get(sellerID);

            if (innerMap.isEmpty()) {
                System.out.println("No sold items yet");
                return;
            }

            System.out.println("Item ID: ");
            int i = 1;
            for (String itemID : innerMap.keySet()) {
                System.out.println("[" + i + "] " +  itemID);
                i++;
            }

            System.out.println();
            System.out.println("[1] View Feedback");
            System.out.println("[2] Go Back");
            String option = getInput.nextLine();

            if (Integer.parseInt(option) == 1) {
                System.out.println("Enter Item ID: ");
                String tempID = getInput.nextLine();

                if (innerMap.containsKey(tempID)) {
                    sellerViewFeedBack(sellerID, tempID);
                } else {
                    System.out.println("No Item ID match found");
                }
            }
        } else {
            System.out.println("No sold items yet");
        }
    }
        void sellerViewFeedBack(String sellerID, String itemID) {
            System.out.println("---------Feedback----------");
            HashMap<String, String> feedbackCopy = sellerFeedbackList.get(sellerID);
            System.out.println(feedbackCopy.get(itemID));

            System.out.println("[1] Add Feedback");
            System.out.println("[2] Go Back");
            String option = getInput.nextLine();

            String feedbackMsgCopy = feedbackCopy.get(itemID);
            String buyerID;

            //Extracting the buyerID from the feedbackMsg
            int startIndex = feedbackMsgCopy.indexOf("Bought By: ") + 11;
            int endIndex = feedbackMsgCopy.indexOf("\n", startIndex);
            buyerID = feedbackMsgCopy.substring(startIndex, endIndex);


            if (Integer.parseInt(option) == 1) {
                sellerAddFeedback(sellerID, buyerID, itemID);
            }
        }

        void sellerAddFeedback(String sellerID, String buyerID, String itemID){
            String rating, comment, feedBackMsg;

            System.out.println("-----------Rate the buyer's transaction------------");
            System.out.println("Enter your rating 1-10: ");
            rating = getInput.nextLine();
            System.out.println("Enter your comment: ");
            comment = getInput.nextLine();

            feedBackMsg = "Sold By: " + sellerID +"\nRating: " + rating +  "\nMessage: " + comment;
            buyerStorage.get(buyerID).get(itemID).add(8, feedBackMsg);
        }
    void buyerItemDetails(String buyerID, String itemID) {
        int option;
        System.out.println("----------Item Details-----------");
        ArrayList<String> itemCopy = buyerStorage.get(buyerID).get(itemID);
        String sellerID = itemCopy.get(0);
        String itemTitle = itemCopy.get(1);

        System.out.println("Item Title: " + itemCopy.get(1));
        System.out.println("Item Seller: " + itemCopy.get(0));
        System.out.println("Item Description: " + itemCopy.get(2));
        System.out.println();
        boolean hasFeedback = itemCopy.size() > 8 && itemCopy.get(8) != null;

        if (!hasFeedback) {
            System.out.println("[1] Add Feedback");
        }

        System.out.println("[2] View Feedback");
        System.out.println("[3] Go Back");

        option = Integer.parseInt(getInput.nextLine());

        if (option == 1 && !hasFeedback) {
            buyerAddFeedback(buyerID, sellerID, itemID, itemTitle);
        } else if (option == 2) {
            System.out.println("--------------Seller Feedback-------------");
            if (hasFeedback) {
                System.out.println("Message: " + itemCopy.get(8));
            } else {
                System.out.println("No feedback yet");
            }
        }
    }
    void buyerPrintItems(String buyerID) {
        System.out.println("-------------Item Storage---------------");
        if (buyerStorage.containsKey(buyerID)) {
            HashMap<String, ArrayList<String>> innerMap = buyerStorage.get(buyerID);

            if (innerMap.isEmpty()) {
                System.out.println("No items found");
                return;
            }

            int i = 1;
            System.out.println("Item Title: ----- \tItem ID: ");
            for (String itemID : innerMap.keySet()) {
                System.out.println("[" + i + "] " + innerMap.get(itemID).get(1) + " ---- \t" + itemID);
                i++;
            }

            System.out.println();
            String option;
            System.out.println("[1] Select Item:");
            System.out.println("[0] Go back: ");
            option = getInput.nextLine();

            if (Integer.parseInt(option) == 1) {
                System.out.println();
                System.out.println("Enter Item ID: ");
                String itemID = getInput.nextLine();

                if (innerMap.containsKey(itemID)) {
                    buyerItemDetails(buyerID, itemID);
                } else {
                    System.out.println("No Item ID match found");
                }
            }
        } else {
            System.out.println("No items found");
        }
    }

        void checkBiddingList () {
            System.out.println("-----------Bidding's-----------");
            if(!transactions.isEmpty()) {
                int i = 0;
                for (String item : transactions.keySet()) {
                    System.out.println("Item: " + item);

                    HashMap<String, ArrayList<String>> innerMap = transactions.get(item);
                    for (String innerKey : innerMap.keySet()) {
                        ArrayList<String> innerList = innerMap.get(innerKey);
                        System.out.println("[" + i + "] " + innerList.get(2));
                        i++;
                    }
                }
            }else{
                System.out.println("No bids yet");
            }
        }
}

