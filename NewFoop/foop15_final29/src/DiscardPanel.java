/**
 * @(#)Text1.java
 *
 *
 * @author
 * @version 1.00 2016/1/19
 */

import java.util.*;
import java.awt.*;

import javax.swing.*;

public class DiscardPanel extends JPanel {
	int cx, cy, n;
	boolean vertical;
	String[] cards;

    public DiscardPanel(int x, int y, int length, int width, boolean v) {
    	vertical = v;
    	n = 0;
    	this.setLocation(x, y);
    	this.setSize(length, width);
    	this.setBackground(null);
    	this.setOpaque(false);
    	this.setLayout(null);
    	cards = new String[8];
    }

    private void addAndSet(JComponent j, int x, int y, int length, int width) {
    	j.setLocation(x, y);
    	j.setSize(length, width);
    	this.add(j);
    }

    public void placeNewCard(String s) {
    	cards[n] = "pic/starship_" + s + "_s.jpg";
    	n++;
    	repaint();

    }

    protected void paintComponent(Graphics g) {
    	cx = 10;
    	cy = 5;
    	Image img;
    	super.paintComponent(g);
    	for(int i=0;i<n;++i) {
    		img = Toolkit.getDefaultToolkit().getImage(cards[i]);
    		g.drawImage(img, cx, cy,this);
    		if(vertical)
    			cy += 25;
    		else
    			cx += 25;
    	}
    }

}