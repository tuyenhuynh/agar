/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 *
 * @author tuyenhuynh
 * Bridge between GTGE and interfaces of model and view
 */
public class Sprite extends com.golden.gamedev.object.Sprite{
    private static final Logger logger = Logger.getLogger(Sprite.class.getName());
    
    private Color color = null; 
    
    private BufferedImage icon = null;
    
    private int size; 
    
    private int angle = 0; 
    
    private double speed = 0 ; 
    
    private Graphics2D g2d; 
    
    private int victimCount = 0;
    
    private boolean isBot;
    
    public Sprite(int size, boolean isBot) {
        this.size = size; 
        this.isBot = isBot; 
    }
    
    public boolean isBot() {
        return this.isBot;
    }
    
    public void eat(){
        this.setSize(this.getSize() + 5);
        victimCount +=  1;
    }
    
    public void eat(int bonus){
        this.setSize(this.getSize() + bonus*5);
        victimCount +=  bonus;
    }
    
    public int getVictimCount(){
        return victimCount;
    }
    
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        repaint();
    }
    
    private void repaint(){
        if(color != null && icon != null) {
            BufferedImage bimage = new BufferedImage(this.size, 
                    this.size, 
                    BufferedImage.TYPE_INT_ARGB);
            
            this.setImage(bimage);
            g2d = bimage.createGraphics(); 
            g2d.setColor(color);
            g2d.fillOval(0, 0, size, size);
                
            g2d.setColor(color.darker());
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(0, 0, size  , size);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.setColor(Color.white);
            int iconHeight = icon.getHeight();
            int iconWidth  = icon.getWidth();
            int h_size = iconHeight + size;
            
            g2d.drawImage(icon, size/2 - iconWidth/2, size/2 - iconHeight/2, null);
            
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
    
    private boolean collision = false; 
    
    public void setDirection(int angle) {
        this.angle = angle;
        if(collision) {
            long currentMoment = System.currentTimeMillis();
            long delta = currentMoment - collisionMoment;
            if(delta > 500){
                collision = false;
            }
        }else {
            setHorizontalSpeed(speed * Math.cos(GameMath.degreesToRadians(angle)));
            setVerticalSpeed(speed * Math.sin(GameMath.degreesToRadians(angle)));         
        }
    }
    
    long collisionMoment = 0; 
    
    public void setCollision(boolean collision){
        this.collision  = collision; 
        if(collision){
            collisionMoment = System.currentTimeMillis();
        }
    }
    
    public boolean getCollision () {
        return this.collision;
    }
    
    public int getDirection(){
        return angle; 
    }
}

