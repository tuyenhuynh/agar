/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Class for description Agar
 * @author tuyenhuynh
 */
public class Agar extends com.golden.gamedev.object.Sprite{
    /**
     * Agar size
     */
    public static final int AGAR_SIZE = 30; 
    
    /**
     * Constructor
     * @param size initial size
     */
    public Agar(int size) {
         this.size = size; 
    }
    
    private Color color = null; 
    
    private BufferedImage icon = null;
    
    private Graphics2D g2d;
    
    private int size; 
    
    /**
     * Get agar size
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Repaint function from base class
     */
    public void repaint(){ 
        if(color != null && icon != null) {
            BufferedImage bimage = new BufferedImage(AGAR_SIZE, 
                    AGAR_SIZE, 
                    BufferedImage.TYPE_INT_ARGB);
            g2d = bimage.createGraphics(); 
            g2d.setColor(color);
            g2d.fillOval(0, 0, size, size);
     
            g2d.setColor(color.darker());
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(0, 0, size  , size);
            
            this.setImage(bimage);
        }
    }
    
    /**
     * Set position for agar
     * @param position position
     */
    public void setPosition(Point position) {
        this.setX(position.getX() - 0.5);
        this.setY(position.getY() -0.5);
    }
    
    /**
     * Get agar position
     * @return position
     */
    public Point getPosition() {
        Point position = new Point((int)getX(), (int)getY());
        return position; 
    }
    
    /**
     * Set color for agar
     * @param color color
     */
    public void setColor(Color color) {
        this.color = color ; 
    }
    
    /**
     * Get agar color
     * @return 
     */
    public Color getColor() {
        return this.color; 
    }
    
    /**
     * Set icon for agar
     * @param icon icon
     */
    public void setIcon(BufferedImage icon) {
        this.icon = icon; 
        // repaint after setting icon
        repaint(); 
    }
    
}
