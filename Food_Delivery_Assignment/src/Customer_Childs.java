import java.util.*;

public interface Customer_Childs {  // Interface for Calculating bill according to Type of Customer as every Customer have different charges,Discount
    double calculateBill(Customer customer,Food menuList,Scanner scanner, Zotato zotato, int Restaurant_id);

    int discount_amount(double bill_value);
}
