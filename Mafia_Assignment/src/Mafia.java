import java.util.HashMap;

public class Mafia extends Player {
    public Mafia(int ID, int status, int HP) {
        super(ID, status, HP);
    }

    public double damagecaused(int id,HashMap<Integer, Player> all_player,HashMap<Integer,Mafia> mafiaDataBase)  // calculate how much damage mafia cause to each player
    {
        double total_mafia_HP=0;
        for (int ID:mafiaDataBase.keySet()) {
            if(mafiaDataBase.get(ID).getHP()>0 && mafiaDataBase.get(ID).getStatus()==1) // Player should alive with HP>0
                {
                    total_mafia_HP=total_mafia_HP+all_player.get(ID).getHP();
                }
        }
        return Math.min(total_mafia_HP,all_player.get(id).getHP());  // damage caused will min value of player hp and sum of all mafia hp
    }

    @Override
    public void takeDamage(int id, HashMap<Integer, Player> all_player,double damage) {  // each mafia take damage
        while (damage >0) {
            int Y = 0;
            for (int ID : all_player.keySet()) {
                if(all_player.get(ID).getClass()==Mafia.class)
                {
                    if (all_player.get(ID).getStatus() == 1 && all_player.get(ID).getHP() > 0) {
                        Y = Y + 1;
                    }
                }
            }

            if (Y > 0) {
                double total_damage = damage / Y;  // damage = 1000 mafia hp>0 = 4 total_damage (each mafia damage) =250

                for (int ID : all_player.keySet()) {
                    if (all_player.get(ID).getClass() == Mafia.class) {
                        if (all_player.get(ID).getHP() > 0 && all_player.get(ID).getStatus() == 1) {
                            double mafia_damage = Math.min(total_damage, all_player.get(ID).getHP());  // in case mafia have lower hp than 250 in above given example
                            all_player.get(ID).setHP(all_player.get(ID).getHP() - mafia_damage);
                            damage = damage - mafia_damage;
                        }
                    }
                }
            }
            else{
                break;
            }
            damage=Double.parseDouble(Village.df.format(damage));
        }
    }

    public int chooseTarget(HashMap<Integer,Integer> all_alivePlayers, HashMap<Integer,Mafia> all_Maifa)
    {
        for (int id:all_alivePlayers.keySet()) {
            if(!all_Maifa.containsKey(id))
            {
                return id;
            }
        }
        return -1;
    }

}
