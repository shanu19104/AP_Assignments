import java.util.*;

public class Zotato {
    private final HashMap<Integer, Customer> allCustomer;
    private final HashMap<Integer,Restaurant> allRestaurant;
    private double payment_from_Restaurants;
    private int Delivery;

    public Zotato(double payment,int Delivery){
        allCustomer=new HashMap<>();
        allRestaurant=new HashMap<>();
        addCustomer();
        addRestaurant();
        payment_from_Restaurants=payment;
        this.Delivery=Delivery;
    }

    private void addCustomer(){
        Customer customer1=new Customer("Ram","Elite","DLF",1000,0,0);
        Customer customer2=new Customer("Sam","Elite","Fatehpur",1000,0,0);
        Customer customer3=new Customer("Tim","Special","Chatterpur",1000,0,20);
        Customer customer4=new Customer("Kim","None","Dwarka",1000,0,40);
        Customer customer5=new Customer("Jim","None","Rajiv Nagar",1000,0,40);
        allCustomer.put(1,customer1);
        allCustomer.put(2,customer2);
        allCustomer.put(3,customer3);
        allCustomer.put(4,customer4);
        allCustomer.put(5,customer5);
    }

    public int getDelivery() {
        return Delivery;
    }

    public void setDelivery(int delivery) {
        Delivery = delivery;
    }

    private void addRestaurant(){
        Restaurant restaurant1=new Restaurant("Shah","Authentic","Vasant Kunj", 0, 0,0);
        Restaurant restaurant2=new Restaurant("Ravi's","None","Jamia", 0, 0,0);
        Restaurant restaurant3=new Restaurant("The Chinese","Authentic","Saket", 0, 0,0);
        Restaurant restaurant4=new Restaurant("Wang's","Fast Food","CP",0, 0,0);
        Restaurant restaurant5=new Restaurant("Paradise","None","Noida", 0, 0,0);

        allRestaurant.put(1,restaurant1);
        allRestaurant.put(2,restaurant2);
        allRestaurant.put(3,restaurant3);
        allRestaurant.put(4,restaurant4);
        allRestaurant.put(5,restaurant5);
    }

    public HashMap<Integer, Customer> getAllCustomer() {
        return allCustomer;
    }

    public HashMap<Integer, Restaurant> getAllRestaurant() {
        return allRestaurant;
    }


    public double getPayment_from_Restaurants() {
        return payment_from_Restaurants;
    }

    public void setPayment_from_Restaurants(double payment_from_Restaurants) {
        this.payment_from_Restaurants = payment_from_Restaurants;
    }

    public void printallCustomers(){ // for showing all customers
        for (int id:allCustomer.keySet()) {
            String answer=id+". "+allCustomer.get(id).getName();
            if(!allCustomer.get(id).getType().equals("None")){
                answer+=" ("+allCustomer.get(id).getType()+")";
            }
            System.out.println(answer);
        }
    }

    public void printallRestaurant(){
        for (int id:allRestaurant.keySet()) {
            String answer=id+") "+allRestaurant.get(id).getName();
            if(!allRestaurant.get(id).getType().equals("None")){
                answer+=" ("+allRestaurant.get(id).getType()+")";
            }
            System.out.println(answer);
        }
    }

}
