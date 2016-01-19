/**
 * @(#)Text9.java
 *
 *
 * @author
 * @version 1.00 2016/1/19
 */


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class RuinPanel extends JPanel implements MouseListener {
	int n, cmdnum;
	boolean buttonAct;
	JLabel[] card;
	GUIMgr gui;

    public RuinPanel(int x, int y, int length, int width, GUIMgr g) {
    	gui = g;
    	n = 1;
    	buttonAct = false;
    	this.setLocation(x, y);
    	this.setSize(length, width);
    	this.setBackground(null);
    	this.setOpaque(false);
    	this.setLayout(null);
    	card = new JLabel[3];
    	card[0] = new JLabel(new ImageIcon("pic/card_back.jpg"));
    	card[0].addMouseListener(this);
    	this.addAndSet(card[0], 10, 5, 90, 120);
    }

    private void addAndSet(JComponent j, int x, int y, int length, int width) {
    	j.setLocation(x, y);
    	j.setSize(length, width);
    	this.add(j);
    }

    public void actButton() {
    	for(int i=0;i<n;++i){
    		card[i].setIcon(new ImageIcon("pic/card_back_enable.jpg"));
    	}
    	buttonAct = true;
    }

    public void placeNewCard() {
    	card[n] = new JLabel(new ImageIcon("pic/card_back.jpg"));
    	card[n].addMouseListener(this);
    	this.addAndSet(card[n], 10+100*n, 5, 90, 120);
    	n++;
    }

    public void seeCard(int num, String cardnum) {
    	String str = "pic/starship_";
    	card[num].setIcon(new ImageIcon(str + cardnum + "_s.jpg"));
    	try{
    		Thread.sleep(2000);
    	}catch(Exception e) {
    	}
    }

    public void endSee() {
    	for(int i=0;i<n;++i){
    		card[i].setIcon(new ImageIcon("pic/card_back.jpg"));
    	}
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    	int choice = 0;
    	if(buttonAct) {
    		for(int i=0;i<n;++i) {
    			if(e.getSource() == card[i]) {
    				choice = i;
    				break;
    			}
    		}
    		buttonAct = false;
    		gui.sendToClient(choice);
    	}
    }

}