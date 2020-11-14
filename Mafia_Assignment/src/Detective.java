import java.util.HashMap;
import java.util.Set;

public class Detective extends Player {
    public Detective(int ID, int status, int HP) {
        super(ID, status, HP);
    }

    public int Test_Player(HashMap<Integer,Integer> alive_player, HashMap<Integer,Detective> detectiveDataBase)
    {

        for (int id:alive_player.keySet()) {
            if(!detectiveDataBase.containsKey(id))
            {
                return id;
            }
        }
        return -1;
    }


    @Override
    public void takeDamage(int id, HashMap<Integer, Player> all_player, double damage) {
        all_player.get(id).setHP(all_player.get(id).getHP()-damage);
    }
}
