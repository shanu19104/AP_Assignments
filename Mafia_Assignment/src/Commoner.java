import java.util.HashMap;

public class Commoner extends Player{
    public Commoner(int ID, int status, int HP) {
        super(ID, status, HP);
    }


    @Override
    public void takeDamage(int id, HashMap<Integer, Player> all_player, double damage) {
        all_player.get(id).setHP(all_player.get(id).getHP()-damage);
    }
}
