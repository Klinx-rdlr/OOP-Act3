package activity_3;

public class Bank {
    private static String accNumber = "B#00001";
    private String routeNum;
    private double sellerBalance;

    public Bank(){
        this.sellerBalance = 0;
    }

    public void setRouteNum(String num){
        this.routeNum = num;
    }

    public String getRouteNum() {
        return routeNum;
    }

    public void setBalance(Double payment){
        this.sellerBalance = payment;
    }

    public double getBalance(){
        return sellerBalance;
    }
}
