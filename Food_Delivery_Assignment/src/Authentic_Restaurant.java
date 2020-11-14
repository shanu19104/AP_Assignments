public class Authentic_Restaurant extends Restaurant implements Restaurant_childs{

    public Authentic_Restaurant(Restaurant restaurant) {
        super(restaurant.getName(), restaurant.getType(), restaurant.getAddress(), restaurant.getOrder_taken(), restaurant.getReward(), restaurant.getDiscount_On_bill());
    }

    @Override
    public int RewardPoint(Restaurant restaurant, double bill) {
        return (int)(bill/200)*25;
    }

}
