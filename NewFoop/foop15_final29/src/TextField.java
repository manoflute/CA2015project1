/**
 * @(#)Text5.java
 *
 *
 * @author
 * @version 1.00 2016/1/18
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TextField extends JTextArea {

    public TextField(int x, int y, int length, int width) {
    	this.setLocation(x, y);
    	this.setSize(length, width);
    	this.setEditable(false);
    }

    void addText(String str) {
    	this.append(str + "\n");
    }
}