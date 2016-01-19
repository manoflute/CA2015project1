/**
 * @(#)Text7.java
 *
 *
 * @author
 * @version 1.00 2016/1/18
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class LostLegacy {
	static GUIMgr gui;
	static Client client;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String serverName = JOptionPane.showInputDialog(null, "Please input server host name.", "Input", JOptionPane.INFORMATION_MESSAGE);
		int port = Integer.valueOf(JOptionPane.showInputDialog(null, "Please input server port.", "Input", JOptionPane.INFORMATION_MESSAGE));
		gui = new GUIMgr();
		client = new Client("Player", new StarShip1(), gui, serverName, port);
		gui.setClient(client);
		Thread t1 = new Thread(new Runnable() {
	        public void run() {
	            client.start();
	        }
	    });
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            gui.start(t1);
	        }
	    });
	}

}