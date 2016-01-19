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

public class PlayerPanel extends JPanel implements MouseListener {
	JLabel handJL, nameJL, drawJL;
	boolean buttonAct;
	GUIMgr gui;

    public PlayerPanel(int x, int y, int length, int width, GUIMgr g) {
    	gui = g;
    	handJL = new JLabel(new ImageIcon("pic/card_back.jpg"));
    	drawJL = new JLabel();
    	drawJL.setVisible(false);
    	this.setLocation(x, y);
    	this.setSize(length, width);
    	nameJL = new JLabel();
    	this.setBackground(null);
    	//this.setOpaque(false);
    	this.setLayout(null);
    	this.addAndSet(nameJL, 50, 5, 80, 10);
    	this.addAndSet(handJL, 10, 15, 90, 120);
    	this.addAndSet(drawJL, 110, 15, 90, 120);
    	buttonAct = false;
    	handJL.addMouseListener(this);
    	drawJL.addMouseListener(this);
    }

    private void addAndSet(JComponent j, int x, int y, int length, int width) {
    	j.setLocation(x, y);
    	j.setSize(length, width);
    	this.add(j);
    }

    public void setName(String name) {
    	nameJL.setText(name);
    }

    public void setHand(String n) {
    	String str = "pic/starship_";
    	handJL.setIcon(new ImageIcon(str + n + "_s.jpg"));
    }

    public void setDraw(String n) {
    	String str = "pic/starship_";
    	drawJL.setIcon(new ImageIcon(str + n + "_s.jpg"));
    	drawJL.setVisible(true);
    	buttonAct = true;
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
    	int choice;
    	if(buttonAct) {
    		if(e.getSource() == handJL) {
    			choice = 0;
    		} else {
    			choice = 1;
    		}
    		buttonAct = false;
    		drawJL.setVisible(false);
    		gui.sendToClient(choice);
    	}
    }

}