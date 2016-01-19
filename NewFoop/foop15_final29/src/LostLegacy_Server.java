import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class LostLegacy_Server {

	public static void main(String arg[]) {
		while (true) {
			GameHolder game = null;
			try {
				game = new GameHolder(Integer.parseInt(arg[0]));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				game.initGame(3, new DeckFactory());
				game.announce("[A0]---NEW GAME START---");
				game.startGame();
				game.announce("[A0]---GAME END---");
				Socket[] playerSockets = game.getPlayersSockets();
				for(int i = 0; i < playerSockets.length; i++){
					try {
						playerSockets[i].close();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				ServerSocket serverSocket = game.getServerSocket();
				try {
					serverSocket.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Some one disconnected\n");
				try {
					game.announce("Some one disconnected");
				} catch (Exception e2) {
					// TODO Auto-generated catch block
				}
				Socket[] playerSockets = game.getPlayersSockets();
				for(int i = 0; i < playerSockets.length; i++){
					try {
						playerSockets[i].close();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				ServerSocket serverSocket = game.getServerSocket();
				try {
					serverSocket.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				continue;
			}
		}
	}
}
