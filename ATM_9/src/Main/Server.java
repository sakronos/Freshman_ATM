package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("Server starting...");
        ServerSocket server=new ServerSocket(60000);
        while (true) {
            Socket s=server.accept();
            System.out.println("Accepting Connection...");
            new ServerThread(s).start();
        }
    }
}
class ServerThread extends Thread {
    private Socket s;
    public ServerThread(Socket s) {
        this.s=s;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader=null;
        PrintWriter printWriter=null;
        try {
            InputStreamReader inputStreamReader;
            inputStreamReader=new InputStreamReader(s.getInputStream());
            bufferedReader=new BufferedReader(inputStreamReader);
            printWriter=new PrintWriter(s.getOutputStream(),true);
            do {

            }while(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
