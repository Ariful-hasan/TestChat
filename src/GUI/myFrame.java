package GUI;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import serverSide.server;

/**
 *
 * @author ary
 */
public class myFrame extends JFrame{
    
    /** Creates a new instance of myFrame */
    public JTextArea ChatBox=new JTextArea(10,45);
    private JScrollPane myChatHistory=new JScrollPane(ChatBox,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    private JTextArea UserText = new JTextArea(5,40);
    private JScrollPane myUserHistory=new JScrollPane(UserText,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JButton Send = new JButton("Send");
    private JButton Start = new JButton("Start Server!");
    private server ChatServer;
    private InetAddress ServerAddress ;
     
    
    public myFrame() {
        setTitle("Server");
        setSize(560,400);
        Container cp=getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(new JLabel("Server History"));
        cp.add(myChatHistory);
        cp.add(new JLabel("Chat Box : "));
        cp.add(myUserHistory);
        cp.add(Send);
        cp.add(Start);
         
         
         
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 
                ChatServer=new server();
                ChatServer.start();
            }
        });
        Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {ChatServer.SendMassage(ServerAddress.getHostName()+" < Server > "+UserText.getText());
            }
        });
         
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
         
         
    }
     
    public static void main(String[] args) {
        // TODO code application logic here
        myFrame myFrame = new myFrame();
    }
     


    
}
