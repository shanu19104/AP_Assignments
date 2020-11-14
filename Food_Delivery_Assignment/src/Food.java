public class Food {
    public static int ID;
    private String Food_Name;
    private int item_price;
    private int quantity;
    private String Category;
    private int Offer;
    private int id;


    public Food(String food_Name, int item_price, int quantity, String category, int offer) {
        Food_Name = food_Name;
        this.item_price = item_price;
        this.quantity = quantity;
        Category = category;
        Offer = offer;
        id=++ID;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood_Name() {
        return Food_Name;
    }

    public void setFood_Name(String food_Name) {
        Food_Name = food_Name;
    }

    public int getItem_price() {
        return item_price;
    }

    public void setItem_price(int item_price) {
        this.item_price = item_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getOffer() {
        return Offer;
    }

    public void setOffer(int offer) {
        Offer = offer;
    }

    @Override
    public String toString() {
        return getId()+" "+getFood_Name()+" "+getItem_price()+" "+
                +getQuantity()+" "+ getOffer()+"% off "+
                getCategory();
    }

    public Food createCopy(int Quantity){ // for putting same copy of food
        int id=getId();
        ID--;
        Food food=new Food(getFood_Name(),getItem_price(),Quantity,getCategory(),getOffer());
        food.setId(id);
        return food;
    }

}
