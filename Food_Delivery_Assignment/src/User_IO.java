import java.text.DecimalFormat;
import java.util.*;

public class User_IO {
    public static DecimalFormat formatAnswer=new DecimalFormat("0.00");  // for formating double answer
    Zotato zotato;
    public User_IO(){
        zotato=new Zotato(0,0);
    }

    public void take_input(Scanner scanner){
        while (true){
            System.out.println("Welcome to Zotato:\n" +
                    "1) Enter as Restaurant Owner\n" +
                    "2) Enter as Customer\n" +
                    "3) Check User Details\n" +
                    "4) Company Account details\n" +
                    "5) Exit");
            int input=Integer.parseInt(scanner.nextLine());
            if(input==1){
                zotato.printallRestaurant();
                int input1=Integer.parseInt(scanner.nextLine()); // asking for Restaurant
                Restaurant restaurant=zotato.getAllRestaurant().get(input1);  // getting Restaurant
                restaurant.chooseRestaurant(scanner);
            }
            if(input==2){
                zotato.printallCustomers();
                int input1=Integer.parseInt(scanner.nextLine());
                Customer customer=zotato.getAllCustomer().get(input1);
                ArrayList<Food> foodInMenuBeforeChecking=new ArrayList<>();
                customer.goToRestaurant(scanner,zotato,foodInMenuBeforeChecking,0);
            }
            if(input==3){
                System.out.println("1) Customer List\n" +
                        "2) Restaurant List");
                int input1=Integer.parseInt(scanner.nextLine());
                if(input1==1){
                    zotato.printallCustomers();
                    int input2=Integer.parseInt(scanner.nextLine());
                    Customer customer=zotato.getAllCustomer().get(input2);
                    System.out.println(customer.toString());
                }
                if(input1==2){
                    zotato.printallRestaurant();
                    int input2=Integer.parseInt(scanner.nextLine());
                    Restaurant restaurant=zotato.getAllRestaurant().get(input2);
                    System.out.println(restaurant.toString());
                }
            }
            if(input==4){
                System.out.println("Total Company balance - INR "+formatAnswer.format(zotato.getPayment_from_Restaurants())+"/-");
                System.out.println("Total Delivery Charges Collected - INR "+zotato.getDelivery()+"/-");
            }
            if(input==5){
                break;
            }

        }
    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        User_IO user_io=new User_IO();
        user_io.take_input(scanner);
    }
}
