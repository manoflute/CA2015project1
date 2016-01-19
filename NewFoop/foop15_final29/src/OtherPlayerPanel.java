/**
 * @(#)Text10.java
 *
 *
 * @author
 * @version 1.00 2016/1/19
 */


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class OtherPlayerPanel extends JPanel implements MouseListener {
	int cmdnum;
	JLabel handJL, nameJL, drawJL;
	boolean buttonAct, dead;
	GUIMgr gui;

    public OtherPlayerPanel(int x, int y, int length, int width, GUIMgr g) {
    	gui = g;
    	handJL = new JLabel(new ImageIcon("pic/card_back.jpg"));
    	drawJL = new JLabel(new ImageIcon("pic/card_back.jpg"));
    	drawJL.setVisible(false);
    	this.setLocation(x, y);
    	this.setSize(length, width);
    	nameJL = new JLabel();
    	this.setBackground(null);
    	this.setOpaque(false);
    	this.setLayout(null);
    	this.addAndSet(nameJL, 10, 5, 80, 10);
    	this.addAndSet(handJL, 10, 15, 90, 120);
    	this.addAndSet(drawJL, 10, 135, 90, 120);
    	buttonAct = false;
    	dead = false;
    	handJL.addMouseListener(this);
    }

    private void addAndSet(JComponent j, int x, int y, int length, int width) {
    	j.setLocation(x, y);
    	j.setSize(length, width);
    	this.add(j);
    }

    public void setDead() {
    	dead = true;
    	handJL.setIcon(new ImageIcon("pic/card_back_die.jpg"));
    }

    public void seeCard(String n) {
    	if(dead)
    		setDead();
    	else{
    		String str = "pic/starship_";
    		handJL.setIcon(new ImageIcon(str + n + "_s.jpg"));
    		try{
    			Thread.sleep(2000);
    		}catch(Exception e) {
    		}
    	}
    }

    public void endSee() {
    	if(!dead)
    		handJL.setIcon(new ImageIcon("pic/card_back.jpg"));
    }

    public void setName(String name) {
    	nameJL.setText(name);
    	nameJL.setForeground(new Color(255, 255, 0));
    }

    public void actButton(int cmdnum) {
    	buttonAct = true;
    	handJL.setIcon(new ImageIcon("pic/card_back_enable.jpg"));
    	this.cmdnum = cmdnum;
    }

    public void draw() {
    	drawJL.setVisible(true);
    }

    public void play(){
    	drawJL.setVisible(false);
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
    	if(buttonAct) {
    		buttonAct = false;
    		gui.sendToClient(cmdnum);
    	}
    }

}