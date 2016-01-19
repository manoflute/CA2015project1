import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
public class GameHolder {

	private List<Card> deck;
	private List<Card> ruin;
	private List<Player> players;
	private Player winner;
	private int turnPlayerIndex;
	private ServerSocket serverSocket;
	private Socket playersSockets[];
	private int playerCount;

	public GameHolder(){
		super();
	}

	public GameHolder(int port) throws IOException {
		serverSocket = new ServerSocket(port); //, 0, InetAddress.getByName("localhost"));
	}


	public Socket[] getPlayersSockets() {
		return playersSockets;
	}

	public void setPlayersSockets(Socket[] playersSockets) {
		this.playersSockets = playersSockets;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int getTurnPlayerIndex() {
		return turnPlayerIndex;
	}

	public void setTurnPlayerIndex(int turnPlayerIndex) {
		this.turnPlayerIndex = turnPlayerIndex;
	}

	public void announce(String text)  throws Exception{
		for(Player p: players){
			p.printMessage(text);
		}
		System.out.println(text);
	}

	public void tellOther(Player player, String text) throws Exception{
		for(Player p: players){
			if(player.getName().equals(p.getName())){

			}
			else{
				p.printMessage(text);
			}
		}
		System.out.println(text);
	}

	public void acceptPlayer(int totalPlayers) throws Exception{
		System.out.println("Waiting for client on port "
				+ serverSocket.getLocalPort() + "...");
		playersSockets = new Socket[totalPlayers];
		while(playerCount < totalPlayers){
			try {
				playersSockets[playerCount] = serverSocket.accept();
				System.out.println("Just connected to player" + playerCount + " from "
						+ playersSockets[playerCount].getRemoteSocketAddress());
				players.get(playerCount).setSocketOutput(new DataOutputStream(playersSockets[playerCount].getOutputStream()));
				players.get(playerCount).setSocketInput(new DataInputStream(playersSockets[playerCount].getInputStream()));
				players.get(playerCount).printMessage("[S9]Please wait...\n");
				playerCount++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void initGame(int num, DeckFactory factory)  throws Exception{
		List<Card> pool = factory.create();
		// players init()
		players = new LinkedList<Player>(); // TODO: too many player exception
		String names[] = new String[] {"Player.1", "Player.2", "Player.3", "Player.4"};
//		Collections.shuffle(Arrays.asList(names));
		for (int i = 0; i < num; ++i)
			players.add(new Player(/* "player"+i */names[i], pool.get(i)));
		acceptPlayer(num);
		turnPlayerIndex = 0;
		// ruin init()
		ruin = new LinkedList<Card>();
		addToRuin(pool.get(num));
		// deck init()
		deck = new LinkedList<Card>();
		for (Card card : pool.subList(num + 1, pool.size()))
			addToDeck(card);
		// winner
		winner = null;
		// message
		for(Player p: players){
			p.printMessage("[S0." + p.getName() + "]" + "You are " + p.getName());
		}
		announce("[A0]Game initialized!");// TODO
	}

	public void startGame()  throws Exception{
		// Phase 1
		while (true) {
			if (!moreThanTwoAlivePlayers() || (winner != null)) {
				printWinner();
				return;
			}
			else {
				Player turnPlayer = players.get(turnPlayerIndex);
				for(Player p: players){
					if(p.isAlive()){
						p.printMessage("[S1." + p.getHand().getName() + "]" + "Your hand is " + p.getHand().getName());
					}
				}
				if (turnPlayer.isAlive()) {
					if (deck.isEmpty()) {
						break;
					}
					else {
						DEBUGfield();
						Card drawn = deck.get(deck.size() - 1);
						deck.remove(deck.size() - 1);
						tellOther(turnPlayer, "[O2." + turnPlayer.getName() + "]" + turnPlayer.getName() + " draws a card from deck");
						Card playing = turnPlayer.turnPlay(drawn);
						playing.play(turnPlayer, this);
					}
				}
			}
			turnPlayerIndex = (turnPlayerIndex + 1) % players.size();
		}
		// phase2
		System.out.println("-------\nPHASE2\n-------");// TODO
		List<Player> alivePlayers = new ArrayList<Player>(players.size());
		for (Player p : players)
			if (p.isAlive())
				alivePlayers.add(p);
		for (int num = 1; num <= 8; ++num) {
			// if (winner != null) break;
			List<Player> guessers = new ArrayList<Player>(players.size());
			for (Player p : alivePlayers)
				if (num == p.getHand().getNumber())
					guessers.add(p);
			if (guessers.isEmpty()) {
			}
			else if (guessers.size() >= 2) {
				String printMsg;
				printMsg = ("[A3]conflicts: " + guessers.get(0).getName());
				for (int i = 1; i < guessers.size(); ++i)
					printMsg += " and " + guessers.get(i).getName();
				printMsg += " .";
				announce(printMsg);
			}
			else {
				Player actor = guessers.get(0);
				announce("[A0]" + num + ", " + actor.getName() + " guessing.");
				List<Card> guessable = new ArrayList<Card>(ruin);
				for (Player p : alivePlayers)
					guessable.add(p.getHand());
				Card selected = actor.findTreasure(guessable);
				if (5 == selected.getNumber()) {
					winner = actor;
					break;
				}
			}
		}
		printWinner();
	}

	/*
	 * private class searchComparator implements Comparator<Card>{ public int
	 * compare(Card A, Card B){ return A.getNumber() - B.getNumber();} }
	 */
	private void DEBUGfield() {
		System.out.println("---------------------------------");
		for (Player p : players) {
			Card hand = p.getHand();
			String msg;
			if (hand != null)
				msg = p.getName() + ": (" + hand.getName()/*
														 * +"("+hand.getPlace()+")"
														 */+ ")";
			else
				msg = p.getName() + ": ()";
			for (Card c : p.getDiscardZone()) {
				msg += (" " + c.getName()/* +"("+c.getPlace()+")" */);
			}
			System.out.println(msg);
		}
		String msg = "Ruin: ";
		for (Card c : ruin)
			msg += (" " + c.getName()/* +"("+c.getPlace()+")" */);
		System.out.println(msg);
		msg = "Deck: ";
		for (Card c : deck)
			msg += (" " + c.getName()/* +"("+c.getPlace()+")" */);
		System.out.println(msg);
	}

	public void printWinner()  throws Exception{
		if(!moreThanTwoAlivePlayers()){
			for (int i = 0; i < players.size(); i++)
				if (players.get(i).isAlive()){
					winner = players.get(i);
					break;
				}
		}
		if (winner != null){
			announce("[A1." + winner.getName() + "]" + "Winner is " + winner.getName() + ".");
		}
		else{
			announce("[A7.No winner]");
		}
	}

	public List<Card> getDeck() {
		return deck;
	};

	public List<Card> getRuin() {
		return ruin;
	};

	public List<Player> getPlayers() {
		return players;
	};

	public void setWinner(Player p) {
		winner = p;
	}

	public void addToRuin(Card c) {
		c.setPosition(new RuinPosition(ruin, ruin.size()));
		ruin.add(c);
	}

	public void addToDeck(Card c) {
		c.setPosition(new DeckPosition(deck, deck.size()));
		deck.add(c);
	}

	public void ruinShuffle()  throws Exception{
		Collections.shuffle(ruin);
		int index = 0;
		for (Card card : ruin) {
			card.setPosition(new RuinPosition(ruin, index++));
		}
		announce("[A2]Ruin shuffled!");
		// TODO:output shuffle message
	}

	private boolean moreThanTwoAlivePlayers() {
		int count = 0;
		for (Player p : players)
			if (p.isAlive())
				++count;
		return (count >= 2);
	}
}
