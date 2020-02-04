package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException {
        String host="localhost";
        InetAddress address=InetAddress.getByName(host);
        BufferedReader bufferedReader=null;
        PrintWriter printWriter=null;
        Socket s=null;
        try {
            s=new Socket(address,60000);
            InputStreamReader inputStreamReader;
            inputStreamReader=new InputStreamReader(s.getInputStream());
            bufferedReader=new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
