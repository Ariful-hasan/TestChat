package clientSide;

public class ReadThread extends Thread{
    @Override
    public void run(){
    
        Client chatclient=new Client();
        chatclient.ReadMassage();
    } 
    
}
