import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class SwingForInterface {
    static JFrame jFrame=new JFrame("Corona Tracker");
    static Data data;
    public SwingForInterface() {
        jFrame.setLayout(null);
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jLabel=new JLabel("Please Enter date in format of dd/MM/yyyy ");
        jLabel.setBounds(130,10,400,50);
        jFrame.add(jLabel);

        JTextField jTextField=new JTextField();
        jFrame.add(jTextField);
        jTextField.setBounds(95,50,300,50);

        JLabel jLabel1=new JLabel("Please select Tower ");
        jLabel1.setBounds(180,120,400,30);
        jFrame.add(jLabel1);

        JPanel panel=new JPanel(); // jpanel which is a container that can store a group of component
                                    // here store my 4 checkboxes
        panel.setBounds(165,160,140,30);
        Checkbox checkbox1=new Checkbox("A");
        Checkbox checkbox2=new Checkbox("B");
        Checkbox checkbox3=new Checkbox("C");
        Checkbox checkbox4=new Checkbox("D");
        panel.add(checkbox1);
        panel.add(checkbox2);
        panel.add(checkbox3);
        panel.add(checkbox4);
        jFrame.add(panel);

        JButton jButton=new JButton("Display");
        jButton.setBounds(180,300,100,50);
        jFrame.add(jButton);

        jFrame.getContentPane().setBackground(Color.PINK);
        jFrame.setVisible(true);
        jFrame.show();

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean A,B,C,D;
                HashSet<String> hashSet=new HashSet<>();
                A=checkbox1.getState();
                B=checkbox2.getState();
                C=checkbox3.getState();
                D=checkbox4.getState(); //get state return true is checkbox is on
                if(A){
                    hashSet.add("A");
                }
                if(B){
                    hashSet.add("B");
                }
                if(C){
                    hashSet.add("C");
                }
                if(D){
                    hashSet.add("D");
                }
                String date=jTextField.getText();
                try {
                    finalAns(hashSet,date); // as parseExecption in Data class as we are formatting string date to Date datatype in convertDate and RecoverDate method
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });
    }

    private void finalAns(HashSet<String> ans, String date) throws ParseException {
        Data data=new Data();
        Date date1=data.convertDate(date);
        int[] recovered=new int[4]; // for counting the recovered persons in different tower
                                    // where 0th position-A,1-B,2-C,3-D
        int[] patient=new int[4];
        ArrayList<ArrayList<Data.Node>> recoveredcontainer=new ArrayList<>(); // for storing recovered patient
        ArrayList<ArrayList<Data.Node>> activecontainer=new ArrayList<>();
        for (int i = 0; i <4; i++) {
            recoveredcontainer.add(i,new ArrayList<>());
            activecontainer.add(i,new ArrayList<>());
        }

        for (Data.Node n:Data.register) {
            if(ans.contains(n.Tower)){
                int position=n.Tower.charAt(0)-'A';
                if(data.compareDate(date1,n.date)>=0){
                    if(data.compareDate(date1,n.RecoverDate)>0){
                        recovered[position]++;
                        recoveredcontainer.get(position).add(n);
                    }
                    else{
                        patient[position]++;
                        activecontainer.get(position).add(n);
                    }
                }
            }
        }

        String totalans="";
        for (int i = 0; i <4; i++) {
            if(recovered[i]>0 || patient[i]>0){  // checking if user given tower have any Active or Recovered Cases upto given date
                totalans+=" Tower "+(char)('A'+i);
                totalans+="\n";
                totalans+="Recovered Cases - "+recovered[i]+"\n";
                    for (Data.Node node : recoveredcontainer.get(i)) {
                        totalans+=" Name - > "+node.Name+", Age -> "+node.Age+", Reported Date -> "+data.covertdatetoString(node.date) +", Recovery Date -> "+data.covertdatetoString(node.RecoverDate)+"\n";
                    }
                    totalans+="\n";
                totalans+="Active Cases - "+patient[i]+"\n";
                    for (Data.Node node:activecontainer.get(i)) {
                        totalans+=" Name - > "+node.Name+", Age -> "+node.Age+", Reported Date -> "+data.covertdatetoString(node.date)+", Recovery Date :"+data.covertdatetoString(node.RecoverDate)+"\n";
                    }
                    totalans+=("-----------------------------------------------------------------------------------------\n");
            }
        }
        if(totalans.length()==0){
            totalans+="No Cases Found!";
        }
        JOptionPane.showMessageDialog(jFrame,totalans);
    }


    public static void main(String[] args) throws ParseException {
        new SwingForInterface();
        data=new Data();
    }
}
