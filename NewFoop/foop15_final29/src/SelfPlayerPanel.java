/**
 * @(#)Text4.java
 *
 *
 * @author
 * @version 1.00 2016/1/17
 */


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SelfPlayerPanel extends JPanel implements MouseListener {
	int cmdnum;
	JLabel handJL, nameJL, drawJL;
	String hand, draw;
	boolean buttonAct;
	GUIMgr gui;

    public SelfPlayerPanel(int x, int y, int length, int width, GUIMgr g) {
    	gui = g;
    	handJL = new JLabel(new ImageIcon("pic/card_back.jpg"));
    	drawJL = new JLabel();
    	drawJL.setVisible(false);
    	this.setLocation(x, y);
    	this.setSize(length, width);
    	nameJL = new JLabel();
    	this.setBackground(null);
    	this.setOpaque(false);
    	this.setLayout(null);
    	this.addAndSet(nameJL, 50, 5, 80, 10);
    	this.addAndSet(handJL, 10, 15, 90, 120);
    	this.addAndSet(drawJL, 110, 15, 90, 120);
    	buttonAct = false;
    	handJL.addMouseListener(this);
    	drawJL.addMouseListener(this);
    	cmdnum = 0;
    }

    public void setDead() {
    	handJL.setIcon(new ImageIcon("pic/card_back_die.jpg"));
    }

    private void addAndSet(JComponent j, int x, int y, int length, int width) {
    	j.setLocation(x, y);
    	j.setSize(length, width);
    	this.add(j);
    }

    public void setName(String name) {
    	nameJL.setText(name);
    	nameJL.setForeground(new Color(255, 255, 0));
    }

    public void setHand(String n) {
    	hand = n;
    	String str = "pic/starship_";
    	handJL.setIcon(new ImageIcon(str + n + "_s.jpg"));
    }

    public void setDraw(String n) {
    	draw = n;
    	String str = "pic/starship_";
    	drawJL.setIcon(new ImageIcon(str + n + "_s.jpg"));
    	drawJL.setVisible(true);
    	buttonAct = true;
    }

    public void actButton(int n) {
    	buttonAct = true;
    	cmdnum = n;
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
    		if(e.getSource() == handJL) {
    			if(cmdnum == 0) {
    				if(hand.charAt(0) != '5'){
    					gui.selfPlaceCard(hand);
    					setHand(draw);
    				} else
    					gui.selfPlaceCard(draw);
    			} else
    				choice = cmdnum;
    		} else if(e.getSource() == drawJL) {
    			if(hand.charAt(0) != '5' && draw.charAt(0) != '5') {
    				choice = 1;
    				gui.selfPlaceCard(draw);
    			} else if(draw.charAt(0) != '5'){
    				gui.selfPlaceCard(draw);
    			} else {
    				gui.selfPlaceCard(hand);
    				setHand(draw);
    			}
    		}
    		buttonAct = false;
    		drawJL.setVisible(false);
    		gui.sendToClient(choice);
    	}
    }

}