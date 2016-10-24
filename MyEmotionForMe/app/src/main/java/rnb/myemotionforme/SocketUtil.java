package rnb.myemotionforme;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by kimminyoung on 2016-04-22.
 */
public class SocketUtil extends  Thread{

    private static final String TAG ="DEBUG" ;
    String msg;
    String  serverIP = "192.168.21.57";
    int Port = 5100;
    Socket socket;

    public SocketUtil(String serverIP, int Port){
        this.serverIP = serverIP;
        this.Port = Port;

    }
    public void  setmessage(String msg) {
        this.msg = msg;
    }
    public void  setServerIp(String serverIP) {
        this.serverIP = serverIP;
    }


    private boolean connect(String addr, int port ){
        try {
            Log.e(TAG, addr);
            InetSocketAddress  serverAddr = new InetSocketAddress (InetAddress.getByName(serverIP), port);
            socket = new Socket();
            socket.connect(serverAddr, 5100);
        }catch (Exception ex){
            ex.printStackTrace();
            Log.e(TAG, "Socket Error creation Fail");
            return false;
        }
        return true;
    }

    public void run(){
        if(!connect(serverIP,Port)) return;
        if(socket==null) return;
        try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.print(msg+"\0");
            out.close();
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "Error! Socket");
        }

    }

}
