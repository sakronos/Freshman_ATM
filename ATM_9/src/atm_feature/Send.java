package atm_feature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ui.LoginPanel;

public class Send {
	public Send() throws IOException {
		String host="localhost";
	    InetAddress address=InetAddress.getByName(host);
	    BufferedReader bufferedReader=null;
	    PrintWriter printWriter=null;
	    Socket s=null;
	    s=new Socket(address,60000);
	    InputStreamReader inputStreamReader;
	    inputStreamReader=new InputStreamReader(s.getInputStream());
	    bufferedReader=new BufferedReader(inputStreamReader);
	}
    public Account LoginPanel(long i,int p) {
		return null;
    	
    }
}
