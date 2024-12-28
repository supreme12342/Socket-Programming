import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {

    ServerSocket serverSocket;
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    public Server(){
        System.out.println("The server is Runnling :");
        try{
            serverSocket = new ServerSocket(1234) ;
            socket = serverSocket.accept() ; 
            System.out.println("Client Connected :");
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(),true);

            StartReading() ;
            StartWriting() ;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void StartReading(){
        Runnable r1 = () ->{
            System.out.println("Reader Started : ");
            try{
                while (true) {
                    String Message = bufferedReader.readLine();
                    if(Message.equalsIgnoreCase("exit")){
                        System.out.println("Client Exited :");
                        break;
                    }else{
                        System.out.println("Client :" + Message );
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }   
        } ;

        new Thread(r1).start() ;
    }


    public void StartWriting(){
        Runnable r2 = () ->{
            System.out.println("Writer Started :");
            try{
                while(true){
                    BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = bufferedReader1.readLine();
                    printWriter.println(content);
                    if(content.equalsIgnoreCase("exit")){
                        socket.close();
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } ;
        new Thread(r2).start() ;
    }

    public static void main(String[] args) {
        System.out.println("This is Server :");
        new Server() ;
    }
}
