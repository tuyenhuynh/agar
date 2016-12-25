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
 *
 * @author tuyenhuynh
 */
public class Obstacle extends com.golden.gamedev.object.Sprite{
    private int size; 
    
    private Color color; 
    
    private BufferedImage icon = null ;
    
    public Obstacle (int size) {
        this.size = size; 
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public void setPosition(Point position) {
        this.setX(position.getX() - 0.5);
        this.setY(position.getY() -0.5);
    }
    
    public Point getPosition() {
        Point position = new Point((int)getX(), (int)getY());
        return position; 
    }
    
    public void setColor (Color color) {
        this.color = color; 
    }
    
    private void repaint() {
        if(color != null && icon != null) {
            BufferedImage bimage = new BufferedImage(this.size, 
                    this.size, 
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bimage.createGraphics(); 
            g2d.setColor(color);
            g2d.fillOval(0, 0, size, size);
     
            g2d.setColor(color.darker());
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(0, 0, size  , size);
            
            g2d.drawImage(icon, 0, 0, size, size, null);
            
            this.setImage(bimage);
        }
    }
    
    public void setIcon(BufferedImage image) {
        this.icon = image; 
        repaint(); 
    }
}
