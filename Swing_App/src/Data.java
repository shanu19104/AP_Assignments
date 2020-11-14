import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class Data {
    public static ArrayList<Node> register;

    public Data() throws ParseException {
       register=new ArrayList<>();
        putdata();
    }

     public  class Node extends ArrayList<Node> {
        String Name;
        int Age;
        String Tower;
        Date date;
        Date RecoverDate;

        public Node(String name, int age, String tower, Date date, Date recoverDate) {
            Name = name;
            Age = age;
            Tower = tower;
            this.date = date;
            RecoverDate = recoverDate;
        }
    }

    private  void putdata() throws ParseException {
        Node data1= new Node("Flora", 6, "A", convertDate("01/04/2020"), RecoverDate("01/04/2020"));
        Node data2= new Node("Denys", 24, "B", convertDate("01/04/2020"), RecoverDate("01/04/2020"));
        Node data3= new Node("Jim", 42, "C", convertDate("18/05/2020"), RecoverDate("18/05/2020"));
        Node data4= new Node("Hazel", 87, "D", convertDate("23/06/2020"), RecoverDate("23/06/2020"));
        Node data5= new Node("Caery", 72, "A", convertDate("01/06/2020"), RecoverDate("01/06/2020"));
        Node data6= new Node("David", 7, "B", convertDate("14/06/2020"), RecoverDate("14/06/2020"));
        Node data7= new Node("Kevim", 37, "D", convertDate("05/06/2020"), RecoverDate("05/06/2020"));
        Node data8= new Node("Tom", 67, "D", convertDate("20/06/2020"), RecoverDate("20/06/2020"));
        Node data9= new Node("Bob", 74, "A", convertDate("04/07/2020"), RecoverDate("04/07/2020"));
        Node data10= new Node("Rachel", 48, "C", convertDate("24/07/2020"), RecoverDate("24/07/2020"));
        Node data11= new Node("Thomas", 21, "C", convertDate("11/06/2020"), RecoverDate("11/06/2020"));
        Node data12= new Node("Mary", 17, "D", convertDate("21/06/2020"), RecoverDate("21/06/2020"));
        Node data13= new Node("Smith", 89, "A", convertDate("07/08/2020"), RecoverDate("07/08/2020"));
        Node data14= new Node("Pearson", 47, "B", convertDate("04/06/2020"), RecoverDate("04/06/2020"));
        Node data15= new Node("Anderson", 62, "B", convertDate("27/07/2020"), RecoverDate("27/07/2020"));
        Node data16= new Node("Johnson", 10, "D", convertDate("01/08/2020"), RecoverDate("01/08/2020"));
        Node data17= new Node("Robertz", 50, "A", convertDate("09/08/2020"), RecoverDate("09/08/2020"));
        Node data18= new Node("Julie", 86, "B", convertDate("02/05/2020"), RecoverDate("02/05/2020"));
        Node data19= new Node("Edith", 42, "D", convertDate("07/06/2020"), RecoverDate("07/06/2020"));
        Node data20= new Node("John", 95, "D", convertDate("01/06/2020"), RecoverDate("01/06/2020"));
        register.add(data1);register.add(data2);register.add(data3);register.add(data4);register.add(data5);
        register.add(data6);register.add(data7);register.add(data8);register.add(data9);register.add(data10);
        register.add(data11);register.add(data12);register.add(data13);register.add(data14);register.add(data15);
        register.add(data16);register.add(data17);register.add(data18);register.add(data19);register.add(data20);



    }

    public  Date convertDate(String date) throws ParseException {  // for converting string date to Date datatype
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.parse(date);
    }

    public Date RecoverDate(String date) throws ParseException{ // for calculating recover date
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date date1=simpleDateFormat.parse(date);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DATE,21);
        return calendar.getTime();
    }

    public int compareDate(Date date1,Date date2){ // compare 2 dates return 0 - date1==date2
                                            // return 1 - date1>date2
                                            // return -1 - date1<date2
        return date1.compareTo(date2);
    }

    public String covertdatetoString(Date date){
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }


}
