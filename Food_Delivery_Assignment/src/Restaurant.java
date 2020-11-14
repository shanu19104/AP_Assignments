import java.util.HashMap;
import java.util.Scanner;

public class Restaurant {
    private final String Name;
    private final String Type;
    private final String Address;
    private int order_taken;
    private double reward;
    private int discount_On_bill;
    private final HashMap<Integer,Food> menu;

    public Restaurant(String name, String type, String address, int order_taken, double reward, int discount_On_bill) {
        Name = name;
        Type = type;
        Address=address;
        this.order_taken = order_taken;
        this.reward = reward;
        this.discount_On_bill = discount_On_bill;
        menu=new HashMap<>();
    }

    public String getAddress() {
        return Address;
    }


    public int getOrder_taken() {
        return order_taken;
    }

    public void setOrder_taken(int order_taken) {
        this.order_taken = order_taken;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public int getDiscount_On_bill() {
        return discount_On_bill;
    }

    public void setDiscount_On_bill(int discount_On_bill) {
        this.discount_On_bill = discount_On_bill;
    }

    public HashMap<Integer, Food> getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return getName()+", "+getAddress()+", "+getOrder_taken();
    }

    public void print_menu(){
        for (int id:menu.keySet()) {
            Food food=menu.get(id);
            String answer=id+" "+getName()+" - "+food.getFood_Name()+" "+
                    food.getItem_price()+" "+food.getQuantity()+" "+food.getOffer()+"% off "
                    +food.getCategory();
            System.out.println(answer);
        }
    }

    public void chooseRestaurant(Scanner scanner){  // for choosing Restaurant when we enter as Restaurant Owner
        while (true) {
            System.out.println("Welcome "+getName());
            System.out.println(
                    "1) Add item\n" +
                            "2) Edit item\n" +
                            "3) Print Rewards\n" +
                            "4) Discount on bill value\n" +
                            "5) Exit");
            int input=Integer.parseInt(scanner.nextLine());
            if (input == 1) {
                addFoodinMenu(scanner);
            }
            if (input == 2) {
                EditFoodinMenu(scanner);
            }
            if (input == 3) {
                System.out.println("Reward Points : " + getReward());
            }
            if (input == 4) {  // for getting Discount on Restaurant
                System.out.print("Enter offer on total bill value - ");
                if(getType().equals("None")){
                    System.out.println("Sorry , Not applicable");
                }
                else {
                    int input1 = Integer.parseInt(scanner.nextLine());
                    setDiscount_On_bill(input1);
                }
            }
            if (input == 5) {
                break;
            }
        }
    }

    private void addFoodinMenu(Scanner scanner){  // for adding food in menu of Restaurant
        String Food_name,category;
        int price,quantity,offer;
        System.out.println("Enter food item details");
        System.out.println("Food Name:");
        Food_name= scanner.nextLine();
        System.out.println("item price:");
        price=Integer.parseInt(scanner.nextLine());
        System.out.println("item quantity:");
        quantity=Integer.parseInt(scanner.nextLine());
        System.out.println("item category:");
        category= scanner.nextLine();
        System.out.println("Offer:");
        offer=Integer.parseInt(scanner.nextLine());

        Food food=new Food(Food_name,price,quantity,category,offer);
        menu.put(food.getId(),food);
        System.out.println(food.toString());

    }

    private void EditFoodinMenu(Scanner scanner) {  // for giving user ease of editing if he want
        if (menu.size() != 0) {
            System.out.println("Choose item by code");
            print_menu();
            int input = Integer.parseInt(scanner.nextLine());
            Food food = menu.get(input);
            System.out.println("Choose an attribute to edit:\n" +
                    "1) Name\n" +
                    "2) Price\n" +
                    "3) Quantity\n" +
                    "4) Category\n" +
                    "5) Offer");
            int input1 = Integer.parseInt(scanner.nextLine());
            if (input1 == 1) {
                System.out.print("Enter the new Name - ");
                String food_name = scanner.nextLine();
                food.setFood_Name(food_name);
            }
            if (input1 == 2) {
                System.out.print("Enter the new price - ");
                int food_price = Integer.parseInt(scanner.nextLine());
                food.setItem_price(food_price);
            }
            if (input1 == 3) {
                System.out.print("Enter the new Quantity - ");
                int food_quantity = Integer.parseInt(scanner.nextLine());
                food.setQuantity(food_quantity);
            }
            if (input1 == 4) {
                System.out.print("Enter the new Category - ");
                String food_category = scanner.nextLine();
                food.setCategory(food_category);
            }
            if (input1 == 5) {
                System.out.print("Enter the new Offer - ");
                int food_offer = Integer.parseInt(scanner.nextLine());
                food.setOffer(food_offer);
            }
            System.out.println( food.getId()+ " " + getName() + " - " + food.getFood_Name() + " " + food.getItem_price() +
                    " " + food.getQuantity() + " " + food.getOffer() + "% off " + food.getCategory());
        }
        else{
            System.out.println("No Menu found");
        }
    }

    public int RewardOnOrder(Restaurant_childs restaurant_childs,Double bill){  // for getting Reward points from different Restaurant using 'Method Resolution'
        return restaurant_childs.RewardPoint(this,bill);
    }

}
