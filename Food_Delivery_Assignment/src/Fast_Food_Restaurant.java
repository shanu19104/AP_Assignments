public class Fast_Food_Restaurant extends Restaurant implements Restaurant_childs {
    public Fast_Food_Restaurant(Restaurant restaurant) {
        super(restaurant.getName(), restaurant.getType(), restaurant.getAddress(), restaurant.getOrder_taken(), restaurant.getReward(), restaurant.getDiscount_On_bill());
    }

    @Override
    public int RewardPoint(Restaurant restaurant, double bill) {
        Fast_Food_Restaurant fast_food_restaurant=new Fast_Food_Restaurant(restaurant);
        return (int)(bill/150)*10;
    }
}
