/**
 * @(#)Text3.java
 *
 *
 * @author
 * @version 1.00 2016/1/17
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DeckPanel extends JPanel {
	JLabel deckJL, countJL;
	int count;

    public DeckPanel(int x, int y, int length, int width) {
    	deckJL = new JLabel(new ImageIcon("pic/card_back.jpg"));
    	this.setLocation(x, y);
    	this.setSize(length, width);
    	countJL = new JLabel();
    	count = 12;
    	countJL.setText("  " + count + "   ");
    	//this.setLayout(null);
    	this.add(countJL);
    	this.add(deckJL);
    }

    public void seeTop(String n) {
    	String str = "pic/starship_";
    	deckJL.setIcon(new ImageIcon(str + n + "_s.jpg"));
    	try{
    		Thread.sleep(2000);
    	}catch(Exception e) {
    	}
    }

    public void endSee() {
    	deckJL.setIcon(new ImageIcon("pic/card_back.jpg"));
    }

    public void draw() {
    	if(count > 0)
    		count--;
    	countJL.setText("  " + count + "   ");
    }

}