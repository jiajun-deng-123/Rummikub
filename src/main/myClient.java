package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;

public class myClient {
    static LinkedList<tile> handcard=new LinkedList<tile>();
    static Socket socket;
    public static void main(String[] args) throws IOException {
        socket=new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",30000),10000);
        new Thread(new ClientThread(socket)).start();
        PrintStream ps=new PrintStream(socket.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String line=null;
        while((line=br.readLine())!=null){

            ps.println(line);




        }
    }

}

class ClientThread implements Runnable {
    private Socket socket;
    private BufferedReader br=null;
    public ClientThread(Socket socket) throws IOException{
        this.socket=socket;

        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        String content=null;
        try{
            while((content=br.readLine())!=null){
                System.out.println(content);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

}
