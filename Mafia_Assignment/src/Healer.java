import java.util.HashMap;

public class Healer extends Player{
    public Healer(int ID, int status, int HP) {
        super(ID, status, HP);
    }

    public int Heal(HashMap<Integer,Integer> alivePlayers)
    {
        int position=Village.getRandomInteger(alivePlayers.size()-1,0);
        int choosen_player=-1;
        for (int id:alivePlayers.keySet()) {
            if(position==0)
            {
                choosen_player=id;
                break;
            }
            position--;
        }

        return choosen_player;
    }


    @Override
    public void takeDamage(int id, HashMap<Integer, Player> all_player, double damage) {
        all_player.get(id).setHP(all_player.get(id).getHP()-damage);
    }
}
