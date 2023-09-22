package activity_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Transaction
{
    private HashMap<Item, HashMap<String, Double>> transactions;
    private static int counter = 1;
    private String transaction_ID;
    public Transaction(){
        transactions = new HashMap<>();
    }

    public void addTransaction(Item item, Account account, Double amount){
        // Check if the item already has associated accounts
        if(transactions.containsKey(item)){
            // If true, simply add the new account to the existing ArrayList
            transactions.get(item).put(account.getAccount_ID(), amount);
        }
        else {
            // If not, create a new ArrayList, add the account and put it in the HashMap
            HashMap<String, Double> temp = new HashMap<>();
            temp.put(account.getAccount_ID(), amount);
            transactions.put(item, temp);
        }
    }

    public HashMap<Item, HashMap<String, Double>> getTransactions() {
        return transactions;
    }

    public void printTransactions() {
        // Iterate over each entry in transactions
        for (Map.Entry<Item, HashMap<String, Double>> entry : transactions.entrySet()) {
            // Get the Item and print
            Item item = entry.getKey();
            System.out.println(item.getItemID());

            // Get inner HashMap
            HashMap<String, Double> accountAmounts = entry.getValue();

            // Iterate over the inner HashMap
            for (Map.Entry<String, Double> innerEntry : accountAmounts.entrySet()) {
                // Get the account and amount, then print
                String account = innerEntry.getKey();
                double amount = innerEntry.getValue();

                System.out.println(account + " --- " + amount);
            }

            // Add empty line for readability
            System.out.println();
        }
    }

}
