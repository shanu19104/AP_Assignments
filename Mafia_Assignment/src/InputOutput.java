import java.util.*;
public class InputOutput {
    public Village village;
    public Player User;
    public InputOutput(int number) {
        village=new Village(number);
    }

    public static int takeInput(){
        int number;
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                number = scanner.nextInt();
                return number;
            } catch (InputMismatchException exception) {
                System.out.print("Please input an integer : ");
            }
        }
    }

    public void chooseCharacter() {

        System.out.println("Choose a Character\n" +
                "1) Mafia\n" +
                "2) Detective\n" +
                "3) Healer\n" +
                "4) Commoner\n" +
                "5) Assign Randomly");
        int input = takeInput();
        while (input < 1 || input > 5) {
            System.out.print("Please choose right character : ");
            input = takeInput();
        }
        if(village.AssignID(input)!=null)
        {
            User= village.AssignID(input);
        }
        else{
            System.out.print("Please choose again:");
            chooseCharacter();
        }
        printDetails();
        village.startRound(User);
    }
    public void printDetails()
    {
        if(User.getClass()==Mafia.class)
        {
            System.out.println("You are Player"+User.getID());
            System.out.print("You are a mafia. Other Mafia are: [");
            for (int id:village.getMafiaDataBase().getDataArray().keySet()) {
                Mafia mafia=village.getMafiaDataBase().getDataArray().get(id);
                if(User.getID()!=mafia.getID())
                {
                    System.out.print("Player"+mafia.getID()+",");
                }
            }
            System.out.println("]");

        }
        else if(User.getClass()==Healer.class)
        {
            System.out.println("You are Player"+User.getID());
            System.out.print("You are a healer. Other Healer are: [");
            for (int id:village.getHealerDataBase().getDataArray().keySet()) {
                Healer healer=village.getHealerDataBase().getDataArray().get(id);
                if(User.getID()!=healer.getID())
                {
                    System.out.print("Player"+healer.getID()+",");
                }
            }
            System.out.println("]");
        }
        else if(User.getClass()==Detective.class)
        {
            System.out.println("You are Player"+User.getID());
            System.out.print("You are a detective. Other Detective are: [");
            for (int id:village.getDetectiveDataBase().getDataArray().keySet()) {
                Detective detective=village.getDetectiveDataBase().getDataArray().get(id);
                if(User.getID()!=detective.getID()){
                    System.out.print("Player"+detective.getID()+",");
                }
            }
            System.out.println("]");
        }
        else if(User.getClass()==Commoner.class)
        {
            System.out.println("You are Player"+User.getID());
            System.out.print("You are a commoner. Other Commoner are: [");
            for (int id:village.getCommonerDataBase().getDataArray().keySet()) {
                Commoner commoner=village.getCommonerDataBase().getDataArray().get(id);
                if(User.getID()!=commoner.getID())
                {
                    System.out.print("Player"+commoner.getID()+",");
                }
            }
            System.out.println("]");
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Mafia ");
        System.out.print("Enter the Number of players: ");
        int total_players = takeInput();
        while (total_players < 6) {
            System.out.print("Enter the Number of players more than 5 : ");
            total_players = takeInput();
        }

        InputOutput inputOutput = new InputOutput(total_players);
        inputOutput.chooseCharacter();
    }
}
