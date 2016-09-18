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
 * Bridge between GTGE and interfaces of model and view
 */
public class Sprite extends com.golden.gamedev.object.Sprite{
    private Color color = null; 
    
    private BufferedImage icon = null; 

    private int angle = 0; 
    
    private double speed = 0 ; 
    
    private Graphics2D g2d; 
    
    private void repaint(){
        if(color != null && icon != null) {
            BufferedImage bimage = new BufferedImage(icon.getWidth(), icon.getHeight(), BufferedImage.TYPE_INT_ARGB);
            
            g2d = bimage.createGraphics(); 
            g2d.setColor(color);
            g2d.fillOval(0, 0, bimage.getWidth(), bimage.getHeight());
            
            g2d.setColor(color.darker().darker());
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(0, 0, icon.getWidth(), icon.getHeight());
            
            g2d.drawImage(icon, 0, 0, null); 
            this.setImage(bimage);
            
        }
    }
    
    public void setPosition(Point position) {
        this.setX(position.getX() - 0.5);
        this.setY(position.getY() -0.5);
    }
    
    public Point getPosition() {
        Point position = new Point((int)getX(), (int)getY());
        return position; 
    }
    
    public void setSpeed (double speed) {
        this.speed = speed; 
        setHorizontalSpeed(speed * Math.cos(GameMath.degreesToRadians(angle)));
        setVerticalSpeed(speed * Math.sin(GameMath.degreesToRadians(angle)));
    }
    
    public double getSpeed(){
        return speed; 
    }
    
    public void setColor(Color color) {
        this.color = color ; 
    }
    
    public Color getColor() {
        return this.color; 
    }
    
    public void setIcon(BufferedImage icon) {
        this.icon = icon; 
        repaint(); 
    }
    
    public void setDirection(int angle) {
        this.angle = angle; 
        setHorizontalSpeed(speed * Math.cos(GameMath.degreesToRadians(angle)));
        setVerticalSpeed(speed * Math.sin(GameMath.degreesToRadians(angle)));
    }
    
    public int getDirection(){
        return angle; 
    }
    
}

