import java.util.*;

public class Customer {
    private final String Name;
    private final String Type;
    private final String Address;
    private double Wallet;
    private double Reward;
    private final int Delivery;
    private final HashMap<Integer,ArrayList<Food>> cart; // for storing restaurant id and all food order from given Restaurant
    private final double[] expense_on_Restaurant;  // for storing restaurant id and expense on given Restaurant


    public Customer(String name, String type,String address, double wallet, double reward, int delivery) {
        Name = name;
        Type = type;
        Address=address;
        Wallet=wallet;
        Reward = reward;
        Delivery = delivery;
        expense_on_Restaurant = new double[5];
        cart=new HashMap<>();
    }

    public String getAddress() {
        return Address;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public double getReward() {
        return Reward;
    }

    public void setReward(double reward) {
        Reward = reward;
    }


    public int getDelivery() {
        return Delivery;
    }

    public double getWallet() {
        return Wallet;
    }

    public void setWallet(double wallet) {
        Wallet = wallet;
    }

    @Override
    public String toString() {
        return getName()+"("+getType()+"), "+
                getAddress()+", "+
                User_IO.formatAnswer.format(getWallet())+"/-";
    }

    public void goToRestaurant(Scanner scanner, Zotato zotato, ArrayList<Food> foodInMenuBeforeChecking, int Restaurant_id ){  // when we enter in application as Customer
        while (true) {
            System.out.println("Welcome "+getName());
            System.out.println("Customer Menu\n" +
                    "1) Select Restaurant\n" +
                    "2) checkout cart\n" +
                    "3) Reward won\n" +
                    "4) print the recent orders\n" +
                    "5) Exit");
            int input=Integer.parseInt(scanner.nextLine());
            if(input==1){
                System.out.println("Choose Restaurant");
                zotato.printallRestaurant();
                int input1=Integer.parseInt(scanner.nextLine()); // for choosing from given input Restaurants
                if(input1!=Restaurant_id){ // emptying cart if Customer order from another Restaurant
                    foodInMenuBeforeChecking=new ArrayList<>();
                }
                    Restaurant_id = input1;

                    System.out.println("Choose item by code ");
                    Restaurant restaurant = zotato.getAllRestaurant().get(input1);
                    restaurant.print_menu();  // print menu of choosen Restaurant

                    int input2 = Integer.parseInt(scanner.nextLine()); // for choosing from given menu
                    System.out.println("Enter item quantity -");

                    int input3 = Integer.parseInt(scanner.nextLine()); // for choosing quantity of food

                    addInCart(foodInMenuBeforeChecking, restaurant, input2, input3);
                    System.out.println("Item added to cart");

            }
            if(input==2){
                if(foodInMenuBeforeChecking.size()==0){
                    System.out.println("No order Found");
                }
                else {
                    checkOut_cart(foodInMenuBeforeChecking, Restaurant_id, zotato);
                    System.out.println("Delivery charges - " + getDelivery() + "/-");
                    // new Methods are for calculating bill amount and setting Rewards if Customer have Sufficient balance
                    // As every type Of Customer have different Delivery charges,Discount, Reward points so I'm performing Method Resolution(Polymorphism)
                    if (getType().equals("Elite")) {
                        Customer_Childs childs = new Elite_Customer(this);
                        calcualte_bill(childs, foodInMenuBeforeChecking, scanner, zotato, Restaurant_id);
                    } else if (getType().equals("Special")) {
                        Customer_Childs childs = new Special_Customer(this);
                        calcualte_bill(childs, foodInMenuBeforeChecking, scanner, zotato, Restaurant_id);
                    } else if (getType().equals("None")) {
                        Customer_Childs childs = new Other_Customer(this);
                        calcualte_bill(childs, foodInMenuBeforeChecking, scanner, zotato, Restaurant_id);
                    }
                    foodInMenuBeforeChecking = new ArrayList<>(); // emptying previous orders of Customer
                }
            }
            if(input==3){
                System.out.println("Reward Won: "+getReward());
            }
            if(input==4){
                printRecentOrder(zotato);
            }
            if(input==5){
              break;
            }
        }
    }
    private void addInCart(ArrayList<Food> menu_list, Restaurant restaurant, int food_id, int food_quantity){  // for adding choosen food in Customer Cart
        Food food=restaurant.getMenu().get(food_id);
        restaurant.getMenu(). get(food_id).setQuantity(food.getQuantity()-food_quantity);
        Food food1=food.createCopy(food_quantity);  // for Creating a copy of food as Customer Choose Different quantity
        menu_list.add(food1);
    }

    private void checkOut_cart(ArrayList<Food> menu_list,int Restaurant_id,Zotato zotato){  // for showing all items which Customer have ordered
        System.out.println("Items in cart - ");
        for (Food food:menu_list) {
            String answer=food.getId()+" "+zotato.getAllRestaurant().get(Restaurant_id).getName()+" - "+
                    food.getFood_Name()+" - "+food.getItem_price()+" - "+
                    food.getQuantity()+" - "+food.getOffer()+"% off";
            System.out.println(answer);
        }
    }

    private void calcualte_bill(Customer_Childs customer_childs,ArrayList<Food> menu_list,Scanner scanner,Zotato zotato,int Restaurant_id){  // for calculating bill using Method Resolution
        double bill=0;
        for (Food food:menu_list) {
            double total_bill=customer_childs.calculateBill(this,food,scanner,zotato,Restaurant_id);
            bill+=total_bill;
        }

        int Restra_discount=zotato.getAllRestaurant().get(Restaurant_id).getDiscount_On_bill();
        bill=bill-(bill)*(Restra_discount/100.0); // Restaurant discount
        bill=bill-customer_childs.discount_amount(bill); // customer discount
        bill+=getDelivery();


        System.out.println("Total order value - INR "+bill+"/-");
        System.out.println("1) Proceed to checkout");
        int input=Integer.parseInt(scanner.nextLine());
        if(input==1){
            Deduction_from_Customer(bill,zotato,Restaurant_id,menu_list,customer_childs,scanner); // checking is Customer have sufficient balance or not and giving Reward points
        }
    }


    private void Deduction_from_Customer(Double bill,Zotato zotato,int Restaurant_id,ArrayList<Food> menu_list,Customer_Childs customer_childs,Scanner scanner){ // deduce charges from Customer
        double total_amount=getReward()+getWallet();
        if(total_amount>=bill){ // if Customer Reward and wallet is more than bill amount
            double Reward_amount=getReward()-bill;  // first deducing bill amount from Rewards
            if(Reward_amount>0){
                setReward(Reward_amount);
            }
            else{
                setReward(0);
                double Wallet_amount=getWallet()+Reward_amount;
                setWallet(Wallet_amount);
            }
            if(cart.get(Restaurant_id)==null){ // adding new Arraylist for each Restaurant in Customer account
                cart.put(Restaurant_id,new ArrayList<>());
            }
            for (Food food:menu_list) {  // adding in cart
                cart.get(Restaurant_id).add(food);
            }
            zotato.getAllRestaurant().get(Restaurant_id).setOrder_taken(zotato.getAllRestaurant().get(Restaurant_id).getOrder_taken()+menu_list.size()); // setting order taken by Restra
            double charges=bill*(1/100.0);

            zotato.setDelivery(zotato.getDelivery()+getDelivery());
            expense_on_Restaurant[Restaurant_id-1]=expense_on_Restaurant[Restaurant_id-1]+ bill;

            zotato.setPayment_from_Restaurants(zotato.getPayment_from_Restaurants()+charges);  // giving Zotato their 1% on each order
            int count=Item_in_MenuList(menu_list);
            System.out.println(count+" items successfully bought for INR "+bill+"/-");
            Reward(bill,zotato,Restaurant_id);
        }
        else{
            System.out.println("Sorry ,You have insufficient Balance. Please Modify your Order -");
            ModifyOrder(customer_childs,menu_list,scanner,zotato,Restaurant_id,bill); // giving an option to Customer to modify order
        }
    }

    private void ModifyOrder(Customer_Childs customer_childs,ArrayList<Food> menu_list,Scanner scanner,Zotato zotato,int Restaurant_id,double bill){
        System.out.println("Selected item by id - ");
        for (Food food:menu_list) {  // printing all food Ordered by Restaurant
            System.out.println(food.toString());
        }

        int input=Integer.parseInt(scanner.nextLine()); // for entering food id
        System.out.println("Please enter quantity - ");
        int input1=Integer.parseInt(scanner.nextLine()); // for entering quantity

        int items_in_Restra_menu=zotato.getAllRestaurant().get(Restaurant_id).getMenu().get(input).getQuantity();
        for (Food food:menu_list) { // for updating restra_menu
            if(food.getId()==input){
                int value_of_food_before_input1=food.getQuantity();
                zotato.getAllRestaurant().get(Restaurant_id).getMenu().get(input).setQuantity(items_in_Restra_menu+value_of_food_before_input1-input1); // updating food quantity in Restra Menu
            }
        }

        if(input1!=0){
            for (Food food:menu_list) {
                if(food.getId()==input){
                    food.setQuantity(input1);
                }
            }

            calcualte_bill(customer_childs,menu_list,scanner,zotato,Restaurant_id);
        }
        else{ // if user set food Quantity to 0 I'm taking fresh order from user

            menu_list=new ArrayList<>();
            goToRestaurant(scanner,zotato,menu_list,Restaurant_id);
        }

    }

    private int Item_in_MenuList(ArrayList<Food> menuList){
        int count=0;
        for (Food food:menuList) {
            count+=food.getQuantity();
        }
        return count;
    }

    private void Reward(double bill,Zotato zotato,int Restaurant_id){ // for setting Reward Points to Customer and Restaurant
        String Resta_type=zotato.getAllRestaurant().get(Restaurant_id).getType();
        int Reward_point=0;
        if(Resta_type.equals("Authentic")){
            Restaurant restaurant=zotato.getAllRestaurant().get(Restaurant_id);
            Restaurant_childs restaurant_childs=new Authentic_Restaurant(restaurant);
            Reward_point=restaurant.RewardOnOrder(restaurant_childs,bill); // getting Reward from each type of Restaurant using Method Resolution
        }
        else if(Resta_type.equals("Fast Food")){
            Restaurant restaurant=zotato.getAllRestaurant().get(Restaurant_id);
            Restaurant_childs restaurant_childs=new Fast_Food_Restaurant(restaurant);
            Reward_point=restaurant.RewardOnOrder(restaurant_childs,bill);
        }
        else if(Resta_type.equals("None")) {
            Restaurant restaurant = zotato.getAllRestaurant().get(Restaurant_id);
            Restaurant_childs restaurant_childs = new Other_Restaurant(restaurant);
            Reward_point = restaurant.RewardOnOrder(restaurant_childs, bill);
        }
        setReward(getReward()+Reward_point); // setting Reward in Customer Account
        zotato.getAllRestaurant().get(Restaurant_id).setReward(zotato.getAllRestaurant().get(Restaurant_id).getReward()+Reward_point); // setting Reward in Restaurant account
    }


    private void printRecentOrder(Zotato zotato){
        for (int id:cart.keySet()) {
            String restra=zotato.getAllRestaurant().get(id).getName();
            String answer="";
            for (Food food:cart.get(id)) {
                answer+="Bought item: "+food.getFood_Name()+", quantity: "+
                        food.getQuantity()+", ";
            }
            answer+="for Rs "+expense_on_Restaurant[id-1]+" from Restaurant "+restra+
                    " and Delivery Charge: "+getDelivery();
            System.out.println(answer);
        }
    }

}
