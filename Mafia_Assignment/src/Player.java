import java.util.*;

public abstract class Player {
    private final int ID;
    private int Status;
    private double HP;

    public Player(int ID, int status, int HP) {
        this.ID = ID;
        Status = status;
        this.HP = HP;
    }

    public int getID() {
        return ID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public double getHP() {
        return HP;
    }

    public HashMap<Integer,Integer> printAlivePlayer(int AlivePlayers,HashMap<Integer,Player> allPlayers)
    {
        HashMap<Integer,Integer> arrayList=new HashMap<>();
        System.out.print(AlivePlayers+" players are remaining:");
        for (int val:allPlayers.keySet()) {
            if(allPlayers.get(val).getStatus()==1)
            {
                arrayList.put(val,0);
                System.out.print(" Player"+allPlayers.get(val).getID()+",");
            }
        }
        System.out.println(" are alive");
        return arrayList;
    }

    public int total_votes(HashMap<Integer,Integer> AlivePlayers)  // generate a random player to vote
    {
        int size=AlivePlayers.size();
        int position=Village.getRandomInteger(size-1,0);
        int ID=-1;
        for (int id:AlivePlayers.keySet()) {
            if(position>0)
            {
               position--;
            }
            else{
                ID=id;
                break;
            }
        }
        return ID;
    }

    public abstract void takeDamage(int id,HashMap<Integer,Player> all_player,double damage);
}
