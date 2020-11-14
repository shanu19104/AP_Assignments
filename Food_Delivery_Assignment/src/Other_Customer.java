import java.util.ArrayList;
import java.util.Scanner;

public class Other_Customer extends Customer implements Customer_Childs {
    public Other_Customer(Customer customer) {
        super(customer.getName(), customer.getType(), customer.getAddress(), customer.getWallet(), customer.getReward(),customer.getDelivery());
    }


    @Override
    public double calculateBill(Customer customer, Food food, Scanner scanner, Zotato zotato, int Restaurant_id) {
        Other_Customer other_customer=new Other_Customer(customer);
        double total_charges=0;

            int food_off=food.getOffer();
            double ith_food_charge=food.getItem_price()*food.getQuantity();
            ith_food_charge-=(ith_food_charge*food_off)/100.0;
            total_charges+=ith_food_charge;

        return total_charges;
    }

    public int discount_amount(double bill_value){
        return 0;
    }


}
