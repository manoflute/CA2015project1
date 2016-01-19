/**
 * @(#)Text8.java
 *
 *
 * @author
 * @version 1.00 2016/1/19
 */
import java.awt.*;
import javax.swing.*;

public class GUIMgr {
	int frameSX = 1100, frameSY = 600, selfnum, right;
	JLabel c;
	Client client;
	SelfPlayerPanel pSelf;
	OtherPlayerPanel[] pOther;
	TextField tF;
	DeckPanel deck;
	RuinPanel ruin;
	DiscardPanel[] dP;
	JScrollPane jSP;

	public GUIMgr(){

	}

	public void start(Thread t1) {
		initFrame();
		display();
		t1.start();
	}

	public void setClient(Client c) {
		client = c;
		System.out.println("fuck");
	}

	public void initFrame() {
		ClientFrame frm = new ClientFrame("Lost Legacy Star Ship ver 1.0");
		frm.setSize(frameSX, frameSY);
		frm.setLocation(100, 0);
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = new JLabel();
		frm.setContentPane(c);
		c.setIcon(new ImageIcon("pic/board.jpg"));
		//c.setBackground(new Color(0, 100, 0));
		c.setLayout(null);
	}

	public void display() {
		deck = new DeckPanel(355, 20, 105, 150);
		pSelf = new SelfPlayerPanel(290, 420, 220, 150, this);
		pOther = new OtherPlayerPanel[2];
		pOther[0] = new OtherPlayerPanel(680, 190, 105, 290, this);
		pOther[1] = new OtherPlayerPanel(0, 190, 105, 290, this);
		dP = new DiscardPanel[3];
		dP[0] = new DiscardPanel(575, 190, 105, 290, true);
		dP[1] = new DiscardPanel(105, 190, 105, 290, true);
		dP[2] = new DiscardPanel(290, 300, 220, 120, false);
		tF = new TextField(800, 0, 290, 590);

		ruin = new RuinPanel(470, 30, 314, 150, this);

		c.add(deck);
		c.add(pSelf);
		c.add(pOther[0]);
		c.add(pOther[1]);
		c.add(dP[0]);
		c.add(dP[1]);
		c.add(dP[2]);
		c.add(tF);
		jSP = new JScrollPane(tF);
		jSP.setLocation(800, 0);
		jSP.setSize(290, 560);
		c.add(jSP);
		c.add(ruin);
	}

	public void getMsg(String msg) {
		String[] str = msg.split("]");
		if(str.length <= 1)
			return;
		tF.addText(str[1]);
		char type = str[0].charAt(1), type2 = str[0].charAt(2);
		//System.out.println(type);
		//System.out.println(type2);
		String[] str2 = str[0].split("\\.");
		switch(type) {
			case 'S':
				selfMessage(type2, str2);
				break;
			case 'O':
				otherMessage(type2, str2);
				break;
			case 'A':
				announceMessage(type2, str2);
				break;
		}
	}

	void print(String[] str) {
		for(String s:str) {
			System.out.println(s);
		}
	}

	private void selfMessage(char type2, String[] str) {
		switch(type2) {
			case '0':
				selfnum = Integer.valueOf(str[2]);
				pSelf.setName("Player " + selfnum);
				for(int i=0;i<2;++i) {
					pOther[i].setName("Player " + ((selfnum+i)%3+1));
				}
				break;
			case '1':
				pSelf.setHand(str[1]);
				break;
			case '2':
				deck.draw();
				pSelf.setDraw(str[1]);
				break;
			case '3':{
				char pos = str[2].charAt(0);
				if(pos == 'd') {
					deck.seeTop(str[1]);
				} else if(pos == 'r') {
					ruin.seeCard(Integer.valueOf(str[3]), str[1]);
				} else {
					pOther[getLeftRight(Integer.valueOf(str[3]))].seeCard(str[1]);
				}
				break;
			}
			case '4':{
				JOptionPane.showMessageDialog(null, "Go out to find out treasure!!!", "", JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			case '5':{
				char pos = str[2].charAt(0);
				if(pos == 'r') {
					ruin.placeNewCard();
				}
				break;
			}
			case '7':
				String question = "";
				if(str[0].charAt(3) == '1')
					question += "swap?";
				else{
					question += "shuffle?";
					deck.draw();
				}
				int n = JOptionPane.showConfirmDialog(null, "Do you want to " + question, "", JOptionPane.YES_NO_OPTION);
				n = (n+1)%2;
				sendToClient(n);
				break;
			case '8':{
				for(int i=1;i<str.length;i+=2) {
					if(str[i].charAt(0) == 'r')
						ruin.actButton();
					if(str[i].charAt(0) == 'P') {
						int num = getLeftRight(Integer.valueOf(str[i+1]));
						if(num == 2)
							pSelf.actButton((i-1)/2);
						else
							pOther[getLeftRight(Integer.valueOf(str[i+1]))].actButton((i-1)/2);
					}
				}
				break;
			}
			default:
		}
	}

	private void endSee() {
		for(OtherPlayerPanel p:pOther) {
			p.endSee();
		}
		ruin.endSee();
		deck.endSee();
	}

	public void selfPlaceCard(String n) {
		dP[2].placeNewCard(n);
	}

	private int getLeftRight(int n) {
		int m = n-selfnum-1;
		if(m < 0)
			m += 3;
		return m;
	}

	private void otherMessage(char type2, String[] str) {
		int num = 0;
		if(str.length >= 3)
			num = getLeftRight(Integer.valueOf(str[2]));
		if(num == 2)
			num = 0;
		OtherPlayerPanel p = pOther[num];
		DiscardPanel d = dP[num];
		switch(type2) {
			case '0':
				p.play();
				d.placeNewCard(str[3]);
				break;
			case '2':
				endSee();
				deck.draw();
				p.draw();
				break;
			case '4':{
				char pos = str[3].charAt(0);
				if(pos == 'r') {
					deck.draw();
					ruin.placeNewCard();
				}
				break;
			}
			default:
		}
	}

	private void announceMessage(char type2, String[] str) {
		switch(type2) {
			case '1':{
				int num = Integer.valueOf(str[2]);
				if(num == selfnum)
					JOptionPane.showMessageDialog(null, "You win!!!!!!!!!!!", "", JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "You lose.....", "", JOptionPane.ERROR_MESSAGE);
				break;
			}
			case '2':
				break;
			case '4':{
				if(Integer.valueOf(str[2]) == selfnum) {
					pSelf.setDead();
				} else {
					int num = getLeftRight(Integer.valueOf(str[2]));
					pOther[num].setDead();
				}
				break;
			}
			case '5':{
				int num = getLeftRight(Integer.valueOf(str[2]));
				dP[num].placeNewCard(str[3]);
				break;
			}
			case '6':
				JOptionPane.showMessageDialog(null, "Can't connect to server.", "", JOptionPane.ERROR_MESSAGE);
				break;
			case '7':
				JOptionPane.showMessageDialog(null, "No winner in this game...", "", JOptionPane.ERROR_MESSAGE);
				break;
			default:
		}
	}

	public void sendToClient(int msg) {
		client.printMessage(msg);
	}

}