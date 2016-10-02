/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import java.awt.Point;

/**
 *
 * @author tuyenhuynh
 */
public class GameMath {
    public static double degreesToRadians(int angle){
        return angle* Math.PI / 180.0; 
    }
    
    public static int radiansToDegrees(double angle) {
        return (int)(angle * 180 /Math.PI); 
    }
    
    public static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)); 
    }
    
    public static double distance(Point p1, Point p2) {
        return distance(p1.x, p1.y, p2.x, p2.y);
    }
    
    public static int angle(Point p1, Point p2) {
        int angle = radiansToDegrees(Math.atan((p2.y - p1.y)/(double)(p2.x - p1.x)));
        if(p2.x < p1.x) {
            angle += 180; 
        }
        return angle; 
    }
    
    public static Point getOppositePoint(Point p, Point center) {
        int newX = 2*center.x - p.x; 
        int newY = 2*center.y - p.y; 
        return new Point(newX, newY);
    }
    
    public static Point getCenter(Obstacle obstacle) {
        Point position = obstacle.getPosition();
        int radius = (int)(obstacle.getSize()/2); 
        return new Point(position.x + radius, position.y + radius);
    }
    
    public static Point getCenter(Agar agar) {
        Point position = agar.getPosition();
        int radius = (int)(agar.getSize()/2); 
        return new Point(position.x + radius, position.y + radius);
    }
    
    public static Point getCenter(Sprite sprite) {
        Point position = sprite.getPosition();
        int radius = (int)(sprite.getSize()/2); 
        return new Point(position.x + radius, position.y + radius);
    }
    
}
