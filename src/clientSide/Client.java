package clientSide;

import java.awt.Container;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends Thread{
    
        private static final int PORT=9999;
        private LinkedList Clients;
        private ByteBuffer ReadBuffer;
        private ByteBuffer writeBuffer;
        private SocketChannel SChan;
        private Selector ReadSelector;
        private CharsetDecoder asciiDecoder;
        private String ServerName;
        private String UserName;
        private JTextField User=new JTextField(20);
        private JTextArea ChatBox=new JTextArea(10,45);
        private ReadThread myRead;
        private JTextField Server=new JTextField(20);
        
        
        public Client() {
            Clients=new LinkedList();
            ReadBuffer=ByteBuffer.allocateDirect(300);
            writeBuffer=ByteBuffer.allocateDirect(300);
            asciiDecoder = Charset.forName( "US-ASCII").newDecoder();
            //Container cp=new Container();
            //cp.add(User);
            
        }
         
        public void run() {
             
            //ClientFerame cf=new ClientFerame();
            
            ServerName=Server.getText();
            System.out.println(ServerName);
            UserName=User.getText();
             
            Connect(ServerName);
            myRead.start();
            while (true) {
                 
                ReadMassage();
                 
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ie){
                }
            }
         }
        
      public void Connect(String hostname) {
            try {
                ReadSelector = Selector.open();
                InetAddress addr = InetAddress.getByName(hostname);
                SChan = SocketChannel.open(new InetSocketAddress(addr, PORT));
                SChan.configureBlocking(false);
                 
                SChan.register(ReadSelector, SelectionKey.OP_READ, new StringBuffer());
            }
             
            catch (Exception e) {
            }
        }
        public void SendMessage(String messg) {
            prepareBuffer(UserName+" says: "+messg);
            channelWrite(SChan);
        }
         
         
        public void prepareBuffer(String massg) {
            writeBuffer.clear();
            writeBuffer.put(massg.getBytes());
            writeBuffer.putChar('\n');
            writeBuffer.flip();
        }
   

        public void channelWrite(SocketChannel client) {
            long num=0;
            long len=writeBuffer.remaining();
            while(num!=len) {
                try {
                    num+=SChan.write(writeBuffer);
                     
                    Thread.sleep(5);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch(InterruptedException ex) {
                     
                }
                 
            }
            writeBuffer.rewind();
        }
        
        
         public void ReadMassage() {
             
            try {
                 
                ReadSelector.selectNow();
                 
                Set readyKeys = ReadSelector.selectedKeys();
                 
                Iterator i = readyKeys.iterator();
                 
                while (i.hasNext()) {
                     
                    SelectionKey key = (SelectionKey) i.next();
                    i.remove();
                    SocketChannel channel = (SocketChannel) key.channel();
                    ReadBuffer.clear();
                     
                     
                    long nbytes = channel.read(ReadBuffer);
                     
                    if (nbytes == -1) {
                        ChatBox.append("You logged out !\n");
                        channel.close();
                    } else {
                         
                        StringBuffer sb = (StringBuffer)key.attachment();
                         
                        ReadBuffer.flip( );
                        String str = asciiDecoder.decode( ReadBuffer).toString( );
                        sb.append( str );
                        ReadBuffer.clear( );
                         
                         
                        String line = sb.toString();
                        if ((line.indexOf("\n") != -1) || (line.indexOf("\r") != -1)) {
                            line = line.trim();
                             
                            ChatBox.append("> "+ line);
                            ChatBox.append(""+'\n');
                            sb.delete(0,sb.length());
                        }
                    }
                }
            } catch (IOException ioe) {
            } catch (Exception e) {
            }
        }
    }

         

    

