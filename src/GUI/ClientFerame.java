package GUI;


import clientSide.Client;
import clientSide.ReadThread;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ClientFerame extends JFrame{
    
    private JTextArea ChatBox=new JTextArea(10,45);
    private JScrollPane myChatHistory=new JScrollPane(ChatBox,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    private JTextArea UserText = new JTextArea(5,40);
    private JScrollPane myUserHistory=new JScrollPane(UserText,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JButton Send = new JButton("Send");
    private JButton Start = new JButton("Connect");
    private Client ChatClient;
    private ReadThread myRead=new ReadThread();
    private JTextField Server=new JTextField(20);
    private JLabel myLabel=new JLabel("Server Name :");
    private JTextField User=new JTextField(20);
    private String ServerName;
    private String UserName;
    
    
    
     public ClientFerame() {
        setResizable(false);
        setTitle("Client");
        setSize(560,400);
        Container cp=getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(new JLabel("Chat History"));
        cp.add(myChatHistory);
        cp.add(new JLabel("Chat Box : "));
        cp.add(myUserHistory);
        cp.add(Send);
        cp.add(Start);
        cp.add(myLabel);
        cp.add(Server);
        cp.add(User);
        
        //final String mess=UserText.getText().toString();
        Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ChatClient!=null) {
                     
                    System.out.println(UserText.getText());
                    //ChatClient.SendMassage(UserText.getText());
                    ChatClient.SendMessage(UserText.getText());
                }
            }
        });
        
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatClient =new Client();
                ChatClient.start();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
     
        
     
     }

        
     public static void main(String arg[]){
        ClientFerame clientFerame = new ClientFerame();
     }

    
}
