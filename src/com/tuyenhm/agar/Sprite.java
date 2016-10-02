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
    
    
    
    public Sprite(int size) {
        this.size = size; 
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    private void repaint(){
        if(color != null && icon != null) {
            BufferedImage bimage = new BufferedImage(this.size, 
                    this.size, 
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
            logger.info("Delta: " + delta); 
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

