import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Client extends Player {
	GUIMgr gui;
	private int msg;
	private String serverName;
	private int port;

	Client(String _name, Card first, GUIMgr g, String serverName, int port) {
		// TODO Auto-generated constructor stub
		super(_name, first);
		gui = g;
		this.serverName = serverName;
		this.port = port;
	}

	public void start() {
		connectToSever(serverName, port);
		Scanner keyboard = new Scanner(System.in);
		String receiveFromServer = "";
		int returnValue;
		while((returnValue = receiveMessage(receiveFromServer)) >= 1){
//			if(returnValue == 2){
//				while(stdin(keyboard)){
//				}
//				printMessage(msg);
//			}
		}
	}


	public void connectToSever(String serverName, int port){
		try {
			System.out.println("Connecting to " + serverName + " on port "
					+ port);
			Socket socket = new Socket(serverName, port);
			System.out.println("Just connected to "
					+ socket.getRemoteSocketAddress());
			OutputStream outToServer = socket.getOutputStream();
			setSocketOutput(new DataOutputStream(outToServer));
			InputStream inFromServer = socket.getInputStream();
			setSocketInput(new DataInputStream(inFromServer));
		} catch (Exception e) {
			gui.getMsg("[A6]Can't connect to server.");
		}
	}

	public int receiveMessage(String receiveFromServer){
		try {
			receiveFromServer = getSocketInput().readUTF();
			System.out.flush();
			if(receiveFromServer.equals("Please enter an integer: ")){
				System.out.print(receiveFromServer);
				return 2;
			}
			else{
				System.out.println(receiveFromServer);
			}
			gui.getMsg(receiveFromServer);
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

	public void printMessage(int msg) {
		System.out.println(msg);
		try {
			getSocketOutput().writeInt(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public boolean stdin(Scanner keyboard){
		String msg;
		try{
			msg = keyboard.next();
			String trash = keyboard.nextLine();
			this.msg = Integer.valueOf(msg);
			return false;
		}catch(Exception e){
			System.out.println("Please enter an integer: ");
			msg = keyboard.next();
			String trash = keyboard.nextLine();
			return true;
		}
	}

}
