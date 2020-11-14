import java.text.DecimalFormat;
import java.util.*;

public class Village {
    private final int total_player;
    private final HashMap<Integer,Player> allPlayers;

    private final DataBase<Healer> healerDataBase;
    private final DataBase<Mafia> mafiaDataBase;
    private final DataBase<Detective> detectiveDataBase;
    private final DataBase<Commoner> commonerDataBase;

    private int Mafia;
    private int Non_mafia;

    public Village(int total_player) {
        this.total_player = total_player;
        allPlayers =new HashMap<>();
        this.healerDataBase = new DataBase<>(new HashMap<>());
        this.mafiaDataBase = new DataBase<>(new HashMap<>());
        this.detectiveDataBase = new DataBase<>(new HashMap<>());
        this.commonerDataBase = new DataBase<>(new HashMap<>());
        allocate_role();
    }

    public HashMap<Integer, Player> getAllPlayers() {
        return allPlayers;
    }

    public DataBase<Healer> getHealerDataBase() {
        return healerDataBase;
    }

    public DataBase<Mafia> getMafiaDataBase() {
        return mafiaDataBase;
    }

    public DataBase<Detective> getDetectiveDataBase() {
        return detectiveDataBase;
    }

    public DataBase<Commoner> getCommonerDataBase() {
        return commonerDataBase;
    }

    public int getMafia() {
        return Mafia;
    }

    public int getNon_mafia() {
        return Non_mafia;
    }

    public void setMafia(int mafia) {
        Mafia = mafia;
    }

    public void setNon_mafia(int non_mafia) {
        Non_mafia = non_mafia;
    }

    private void allocate_role()
    {
        int mafia = total_player / 5;
        int detective = total_player / 5;
        int healer = Math.max(1, total_player / 10);
        int commoner = (total_player) - (mafia + detective + healer);

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 1; i <= total_player; i++) {  // for using collections.shuffle
            arrayList.add(i);
        }
        Player player1 = new Detective(-1, 1, 500); // assigning a fake id
        Define_role(detective, arrayList,player1);


        Player player2 = new Mafia(-1, 1, 500); // assigning a fake id
        Define_role(mafia,arrayList,player2);


        Player player3 = new Healer(-1, 1, 500); // assigning a fake id
        Define_role(healer,arrayList,player3);


        Player player4 = new Commoner(-1, 1, 500); // assigning a fake id
        Define_role(commoner,arrayList,player4);
    }

    private void Define_role(int number,ArrayList<Integer> arrayList,Player player)
    {
        while (number>0)
        {
            Collections.shuffle(arrayList);
            int generated_number = arrayList.get(0);
            Set<Integer> key = allPlayers.keySet();
            if (!key.contains(generated_number)) {
                if (player.getClass() == Detective.class) {
                   Detective detective = new Detective(generated_number, 1, 800);
                   getAllPlayers().put(generated_number,detective);
                   detectiveDataBase.getDataArray().put(generated_number,detective);
                   setNon_mafia(getNon_mafia()+1);

                } else if (player.getClass() == Healer.class) {
                    Healer healer = new Healer(generated_number,1,800);
                    getAllPlayers().put(generated_number,healer);
                    healerDataBase.getDataArray().put(generated_number,healer);
                    setNon_mafia(getNon_mafia()+1);

                } else if (player.getClass() == Mafia.class) {
                    Mafia mafia = new Mafia(generated_number,1,2500);
                    getAllPlayers().put(generated_number,mafia);
                    mafiaDataBase.getDataArray().put(generated_number,mafia);
                    setMafia(getMafia()+1);

                } else if (player.getClass() == Commoner.class) {
                    Commoner commoner = new Commoner(generated_number,1,1000);
                    getAllPlayers().put(generated_number,commoner);
                    commonerDataBase.getDataArray().put(generated_number,commoner);
                    setNon_mafia(getNon_mafia()+1);
                }
                arrayList.remove(0);
                number--;
            }
        }
    }


    public Player AssignID(int input)
    {
        if(input==1)
        {
            for (int id:mafiaDataBase.getDataArray().keySet()) {
                return mafiaDataBase.getDataArray().get(id);
            }
        }
        else if(input==2)
        {
            for (int id:detectiveDataBase.getDataArray().keySet()) {
                return detectiveDataBase.getDataArray().get(id);
            }
        }
        else if(input==3)
        {
            for (int id:healerDataBase.getDataArray().keySet()) {
                return healerDataBase.getDataArray().get(id);
            }
        }
        else if(input==4)
        {
            for (int id:commonerDataBase.getDataArray().keySet()) {
                return commonerDataBase.getDataArray().get(id);
            }
        }
        else{
            int generate_number=getRandomInteger(4,1);
            return AssignID(generate_number);
        }
        return null;
    }

    public void startRound(Player User)
    {
        int Round=1;

        while (getNon_mafia()>1 && getMafia()>0)   // while ratio is more than 1:1
        {
            System.out.println("Round"+Round);
            HashMap<Integer,Integer> alive_Players=User.printAlivePlayer(getNon_mafia()+getMafia(),getAllPlayers());
                    // alive_Players hashmap store alive Players id as key and vote they get while voting as value
            int Mafia_Target=-1;
            int Detective_test=-1;

            if(User.getClass()==Mafia.class)
            {

                if(User.getStatus()==1) // if you mafia and you are alive
                {
                    System.out.print("Choose a target: ");
                    Mafia_Target=TakeInput();
                    while (getAllPlayers().get(Mafia_Target).getStatus()!=1)
                    {
                        System.out.print("Player is not alive.Please Enter again: ");
                        Mafia_Target=TakeInput();
                    }
                    while (getAllPlayers().get(Mafia_Target).getClass()==Mafia.class)
                    {
                        System.out.print("You can not target other Mafia. Choose another target: ");
                        Mafia_Target=TakeInput();
                    }
                    if(Mafia_Target!=-1)
                    {
                        double damage_Caused=((Mafia) User).damagecaused(Mafia_Target,getAllPlayers(),getMafiaDataBase().getDataArray());
                        User.takeDamage(Mafia_Target,getAllPlayers(),damage_Caused);
                        getAllPlayers().get(Mafia_Target).takeDamage(Mafia_Target,getAllPlayers(),damage_Caused);
                    }
                }
                else{ // if you are dead other mafia choose target
                    for (int id:getMafiaDataBase().getDataArray().keySet()) {
                        Mafia mafia=getMafiaDataBase().getDataArray().get(id);
                        if(mafia.getStatus()==1)
                        {
                            Mafia_Target=mafia.chooseTarget(alive_Players,getMafiaDataBase().getDataArray());  // mafia choose target
                            if(Mafia_Target!=-1)
                            {
                                double damage_Caused=mafia.damagecaused(Mafia_Target,getAllPlayers(),getMafiaDataBase().getDataArray());
                                mafia.takeDamage(Mafia_Target,getAllPlayers(),damage_Caused);  // mafia take damage
                                getAllPlayers().get(Mafia_Target).takeDamage(Mafia_Target,getAllPlayers(),damage_Caused); // player take damage
                            }
                            break;
                        }
                    }
                    System.out.println("Mafias have chosen their target.");

                }
            }

            else{ // if user is not mafia then mafia perform there action
                for (int id:getMafiaDataBase().getDataArray().keySet()) {
                    Mafia mafia=getMafiaDataBase().getDataArray().get(id);
                    if(mafia.getStatus()==1)
                    {
                        Mafia_Target=mafia.chooseTarget(alive_Players,getMafiaDataBase().getDataArray());
                        if(Mafia_Target!=-1)
                        {
                            double damage_Caused=mafia.damagecaused(Mafia_Target,getAllPlayers(),getMafiaDataBase().getDataArray());
                            mafia.takeDamage(Mafia_Target,getAllPlayers(),damage_Caused);
                            getAllPlayers().get(Mafia_Target).takeDamage(Mafia_Target,getAllPlayers(),damage_Caused);
                        }
                        break;
                    }
                }
                System.out.println("Mafias have chosen their target.");
            }

            if(User.getClass()==Detective.class)
            {
                if(User.getStatus()==1)
                {
                    System.out.print("Choose a player to test: ");
                    Detective_test=TakeInput();
                    while (getAllPlayers().get(Detective_test).getStatus()!=1)
                    {
                        System.out.print("Player is not alive.Please Enter again: ");
                        Detective_test=TakeInput();
                    }
                    while (getAllPlayers().get(Detective_test).getClass()==Detective.class)
                    {
                        System.out.print("You cannot test a Detective. Choose a player to test: ");
                        Detective_test= TakeInput();
                    }
                    if(getAllPlayers().get(Detective_test).getClass()!=Mafia.class)
                    {
                        System.out.println("Player"+Detective_test+" is not a mafia.");
                    }
                    else
                    {
                        System.out.println("Player"+Detective_test+" is mafia. ");
                    }
                }
                else{  // if user is dead then other detective choose player to test
                    for (int id:getDetectiveDataBase().getDataArray().keySet()) {
                        Detective detective=getDetectiveDataBase().getDataArray().get(id);
                        if(detective.getStatus()==1)
                        {
                            Detective_test=detective.Test_Player(alive_Players,getDetectiveDataBase().getDataArray());
                            break;
                        }
                    }
                    System.out.println("Detectives have chosen a player to test.");
                }
            }
            else{  // if user is not detective then other detective choose target
                for (int id:getDetectiveDataBase().getDataArray().keySet()) {
                    Detective detective=getDetectiveDataBase().getDataArray().get(id);
                    if(detective.getStatus()==1)
                    {
                        Detective_test=detective.Test_Player(alive_Players,getDetectiveDataBase().getDataArray());
                        break;
                    }
                }
                System.out.println("Detectives have chosen a player to test.");
            }
            if(User.getClass()==Healer.class)
            {

                if(User.getStatus()==1)
                {
                    int Healer_heal;
                    System.out.print("Choose a player to heal: ");
                    Healer_heal= TakeInput();
                    while (getAllPlayers().get(Healer_heal).getStatus()!=1)
                    {
                        System.out.print("Player is not alive.Please Enter again: ");
                        Healer_heal=TakeInput();
                    }
                    if(Healer_heal!=0)
                    {

                        getAllPlayers().get(Healer_heal).setHP(getAllPlayers().get(Healer_heal).getHP()+500);  // update each player hp in allPlayer Hashmap

                    }

                }
                else{  // if user(Healer) is not alive
                    for (int id:getHealerDataBase().getDataArray().keySet()) {
                        Healer healer=getHealerDataBase().getDataArray().get(id);
                        if(healer.getStatus()==1)
                        {
                            int healer_id=healer.Heal(alive_Players);

                            getAllPlayers().get(healer_id).setHP(getAllPlayers().get(healer_id).getHP()+500);
                            break;
                        }
                    }
                    System.out.println("Healers have chosen someone to heal.");
                }
            }
            else{  // if user is not healer then other healer choose target
                for (int id:getHealerDataBase().getDataArray().keySet()) {
                    Healer healer=getHealerDataBase().getDataArray().get(id);
                    if(healer.getStatus()==1)
                    {
                        int healer_id=healer.Heal(alive_Players);
                        getAllPlayers().get(healer_id).setHP(getAllPlayers().get(healer_id).getHP()+500);
                        break;
                    }
                }
                System.out.println("Healers have chosen someone to heal.");

            }

            System.out.println("------------------------End of actions------------------------");

            if(Mafia_Target!=-1)  // if Mafia has choosen correct target
            {
                if(getAllPlayers().get(Mafia_Target).getHP()==0) // if hp of player targeted by mafia becomes 0 then set status of player to dead
                {
                    getAllPlayers().get(Mafia_Target).setStatus(0);
                    setNon_mafia(getNon_mafia()-1);
                    Update_dataBase(Mafia_Target);
                    alive_Players.remove(Mafia_Target); // removing from alive person arraylist
                    System.out.println("Player"+Mafia_Target+" has died.");
                }
                else{ // if after target healing power of player > mafia power
                    System.out.println("No one died");
                }
            }

            if(getMafiaDataBase().getDataArray().containsKey(Detective_test)) // if mafia has pointed out mafia then there will be no voting
            {
                getAllPlayers().get(Detective_test).setStatus(0);
                setMafia(getMafia()-1);
                Update_dataBase(Detective_test);
                alive_Players.remove(Detective_test);
                System.out.println("Player"+Detective_test+" has been voted out.");
            }
            else{
                int maxVoted_ID=Voting(User,alive_Players);
                if(getNon_mafia()>1 && getMafia()>0) // if ratio is more than 1:1 only then voting
                {
                    while (maxVoted_ID==-1)  // checking if there is something wrong happens in voting
                    {
                        maxVoted_ID=Voting(User,alive_Players);
                    }
                    System.out.println("Player"+maxVoted_ID+" has been voted out.");
                }
                if(maxVoted_ID!=-1)  // if voting is successful then update number of mafia and non-mafia player
                {
                    if(allPlayers.get(maxVoted_ID).getClass()==Mafia.class)
                    {
                        setMafia(getMafia()-1);
                    }
                    else{
                        setNon_mafia(getNon_mafia()-1);
                    }
                    getAllPlayers().get(maxVoted_ID).setStatus(0); // setting status of maxVoted id to 0
                    Update_dataBase(maxVoted_ID); // updating database after finding user is which type of player
                }
                System.out.println();

            }
            System.out.println("------------------------End of Round "+Round+"------------------------");
            Round++;
        }
        System.out.println("Game Over");

        if(getMafia()==0)
        {
            System.out.println("The Mafias have lost.");
        }
        else{
            System.out.println("The Mafias have won.");
        }
        Player_character(User);
    }

    public int Voting(Player User,HashMap<Integer,Integer> alive)
    {
            boolean[] voting_arr = new boolean[4];  // 0- Detective voting, 1- Mafia voting, 2 - healer voting , 3-commoner voting
            HashMap<Integer, Integer> alive_Players = new HashMap<>();  // copying alive hashmap to alive_players for the case if there is chance of again voting due to draw

            for (int id : alive.keySet()) {
                alive_Players.put(id, alive.get(id));
            }

            if (User.getStatus() == 1) {  // if User is alive we ask for his vote
                int voted_Player;
                System.out.print("Select a person to vote out: ");
                voted_Player = TakeInput();

                boolean Done_Voting = false;
                while (!Done_Voting) {
                    if (getAllPlayers().get(voted_Player).getStatus() != 1) {
                        System.out.print("Player is not alive. Please try again: ");
                        voted_Player = TakeInput();
                    } else {
                        if (User.getID() == getAllPlayers().get(voted_Player).getID()) {
                            System.out.print("You can not vote ourself. Please try again: ");
                            voted_Player = TakeInput();
                        } else if (getAllPlayers().get(voted_Player).getClass() == User.getClass()) {
                            System.out.print("You can not vote another");
                            if (User.getClass() == Detective.class) {
                                System.out.print(" Detective. Please vote again: ");
                            }
                            if (User.getClass() == Healer.class) {
                                System.out.print(" Healer. Please vote again: ");
                            }
                            if (User.getClass() == Mafia.class) {
                                System.out.print(" Mafia. Please vote again: ");
                            }
                            if (User.getClass() == Commoner.class) {
                                System.out.print(" Commoner. Please vote again: ");
                            }
                            voted_Player =TakeInput();
                        } else {
                            Done_Voting = true;
                        }
                    }
                }

                int total_votes = 0;
                if (User.getClass() == Detective.class) {  //each detective agree to choose same player
                    total_votes = getDetectiveDataBase().getDataArray().size();
                    voting_arr[0] = true;
                } else if (User.getClass() == Mafia.class) {  // each mafia choose same player in voting
                    total_votes = getMafiaDataBase().getDataArray().size();
                    voting_arr[1] = true;
                } else if (User.getClass() == Healer.class) {  // each healer choose same player in voting
                    total_votes = getHealerDataBase().getDataArray().size();
                    voting_arr[2] = true;
                } else if (User.getClass() == Commoner.class) {  // each commoner choose same player in voting
                    total_votes = getCommonerDataBase().getDataArray().size();
                    voting_arr[3] = true;
                }
                alive_Players.replace(voted_Player, alive_Players.get(voted_Player), total_votes);  // counting number of votes in alive_Players hashmap

            }
            other_player_votes(voting_arr, alive_Players);  // if user is alive or not alive other player vote

            int ID1 = -1;
            int ID2 = -1;
            int max_votes = -1;
            for (int id : alive_Players.keySet()) {  // checking if Players have got same votes in voting
                if (alive_Players.get(id) > max_votes) {
                    max_votes = alive_Players.get(id);
                    ID1 = id;
                } else if (alive_Players.get(id) == max_votes) {
                    ID2 = id;
                }
            }
            if (ID1 == ID2) {
                System.out.println("There is tie in voting.");
                return Voting(User, alive);
            }
            return ID1;
    }

    public void Update_dataBase(int id)
    {
        if(getAllPlayers().get(id).getClass()==Detective.class)
        {
            getDetectiveDataBase().getDataArray().remove(id);
        }
        else if(getAllPlayers().get(id).getClass()==Healer.class)
        {
            getHealerDataBase().getDataArray().remove(id);
        }
        else if(getAllPlayers().get(id).getClass()==Mafia.class)
        {
            getMafiaDataBase().getDataArray().remove(id);
        }
        else if(getAllPlayers().get(id).getClass()==Commoner.class)
        {
            getCommonerDataBase().getDataArray().remove(id);
        }
    }

    public void Player_character(Player User)
    {
        for (int id:allPlayers.keySet()) {
            if(allPlayers.get(id).getClass()==Mafia.class)
            {
                System.out.print("Player"+id);
                if(id==User.getID())
                {
                    System.out.print("[User]");
                }
                System.out.print(", ");
            }
        }
        System.out.println("were mafias");

        for (int id:allPlayers.keySet()) {
            if(allPlayers.get(id).getClass()==Detective.class)
            {
                System.out.print("Player"+id);
                if(id==User.getID())
                {
                    System.out.print("[User]");
                }
                System.out.print(", ");
            }
        }
        System.out.println("were Detectives");

        for (int id:allPlayers.keySet()) {
            if(allPlayers.get(id).getClass()==Healer.class)
            {
                System.out.print("Player"+id);
                if(id==User.getID())
                {
                    System.out.print("[User]");
                }
                System.out.print(", ");
            }
        }
        System.out.println("were Healers");

        for (int id:allPlayers.keySet()) {
            if(allPlayers.get(id).getClass()==Commoner.class)
            {
                System.out.print("Player"+id);
                if(id==User.getID())
                {
                    System.out.print("[User]");
                }
                System.out.print(", ");
            }
        }
        System.out.println("were Commoners");
        System.out.println("--End of Sample Case--");

    }

    public void other_player_votes(boolean[] voting_arr,HashMap<Integer,Integer> alive_Players) // method so that other player vote
    {
        int count=0;
        for (boolean val:voting_arr) {
            if(!val)
            {
                count++;
            }
        }
        while (count>0)
        {
            if(!voting_arr[0])  // check is detective have voted or not
            {
                if(detectiveDataBase.getDataArray().size()>0)
                {
                    for (int id:detectiveDataBase.getDataArray().keySet()) {
                        int player= detectiveDataBase.getDataArray().get(id).total_votes(alive_Players);
                        if(player!=-1)
                        {
                            int total_votes=detectiveDataBase.getDataArray().size();
                            alive_Players.replace(player,alive_Players.get(player),alive_Players.get(player)+total_votes);
                            voting_arr[0]=true;
                            break;
                        }
                    }
                }
                else{
                    voting_arr[0]=true;
                }

            }
            else if(!voting_arr[1])
            {
                if(mafiaDataBase.getDataArray().size()>0)
                {
                    for (int id:mafiaDataBase.getDataArray().keySet()) {
                        int player=mafiaDataBase.getDataArray().get(id).total_votes(alive_Players); // player represent a random player id to vote
                        if(player!=-1)
                        {
                            int total_votes=mafiaDataBase.getDataArray().size();
                            alive_Players.replace(player,alive_Players.get(player),alive_Players.get(player)+total_votes);
                            voting_arr[1]=true;
                            break;
                        }
                    }
                }
                else{
                    voting_arr[1]=true;
                }
            }
            else if(!voting_arr[2])
            {
                if(healerDataBase.getDataArray().size()>0)
                {
                    for (int id:healerDataBase.getDataArray().keySet()) {
                        int player=healerDataBase.getDataArray().get(id).total_votes(alive_Players);
                        if(player!=-1)
                        {
                            int total_votes=healerDataBase.getDataArray().size();
                            alive_Players.replace(player,alive_Players.get(player),alive_Players.get(player)+total_votes);
                            voting_arr[2]=true;
                            break;
                        }
                    }
                }
                else{
                    voting_arr[2]=true;
                }
            }
            else if(!voting_arr[3])
            {
                if(commonerDataBase.getDataArray().size()>0)
                {
                    for (int id:commonerDataBase.getDataArray().keySet()) {
                        int player=commonerDataBase.getDataArray().get(id).total_votes(alive_Players);
                        if(player!=-1)
                        {
                            int total_votes=commonerDataBase.getDataArray().size();
                            alive_Players.replace(player,alive_Players.get(player),alive_Players.get(player)+total_votes);
                            voting_arr[3]=true;
                            break;
                        }
                    }
                }
                else{
                    voting_arr[3]=true;
                }
            }
            count--;
        }

    }

    public static int getRandomInteger(int maximum, int minimum){
        return ((int) (Math.random()*(maximum - minimum))) + minimum;
    }

    public static final DecimalFormat df = new DecimalFormat("0.00");

    public int TakeInput()  // for taking input and handling wrong input
    {
        while (true) {
            try {
                Scanner scanner1 = new Scanner(System.in);
                int input;
                input=scanner1.nextInt();
                while (input<1 || input>total_player)
                {
                    System.out.print("Wrong input. Enter again: ");
                    input=scanner1.nextInt();
                }
                return input;
            } catch (InputMismatchException exception) {
                System.out.print("Please input an integer : ");
            }
        }

    }

}
