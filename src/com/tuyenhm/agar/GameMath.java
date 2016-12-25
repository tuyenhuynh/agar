/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import java.awt.Point;

/**
 * Utility functions.
 * @author tuyenhuynh
 */
public class GameMath {
    
    /**
     * Convert angle from degree to radian.
     * @param angle angle (degree)
     * @return angle (radian)
     */
    public static double degreesToRadians(int angle){
        return angle* Math.PI / 180.0; 
    }
    
    /**
     * Convert angle from radian to degree.
     * @param angle angle (radian)
     * @return angle (degree)
     */
    public static int radiansToDegrees(double angle) {
        return (int)(angle * 180 /Math.PI); 
    }
    
    /**
     * Get distance between two points.
     * @param x1 coordinate x of first point
     * @param y1 coordinate y of first point
     * @param x2 coordinate x of second point
     * @param y2 coordinate x of second point
     * @return distance
     */
    public static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)); 
    }
    
    /**
     * Get distance between two points.
     * @param p1 first point
     * @param p2 second point
     * @return distance
     */
    public static double distance(Point p1, Point p2) {
        return distance(p1.x, p1.y, p2.x, p2.y);
    }
    
    /**
     * Get angle between two point in the field.
     * @param p1 first point
     * @param p2 second point
     * @return angle (degree)
     */
    public static int angle(Point p1, Point p2) {
        int angle = radiansToDegrees(Math.atan((p2.y - p1.y)/(double)(p2.x - p1.x)));
        if(p2.x < p1.x) {
            angle += 180; 
        }
        return angle; 
    }
    
    /**
     * Get opposite point of a point
     * @param p point, what is need to get opposite
     * @param center center point
     * @return opposite point
     */
    public static Point getOppositePoint(Point p, Point center) {
        int newX = 2*center.x - p.x; 
        int newY = 2*center.y - p.y; 
        return new Point(newX, newY);
    }
    
    /**
     * Get center of obstacle
     * @param obstacle-  obstacle
     * @return obstacle's center
     */
    public static Point getCenter(Obstacle obstacle) {
        Point position = obstacle.getPosition();
        int radius = (int)(obstacle.getSize()/2); 
        return new Point(position.x + radius, position.y + radius);
    }
    /**
     * Get center of agar
     * @param agar-  agar
     * @return agar's center
     */
    public static Point getCenter(Agar agar) {
        Point position = agar.getPosition();
        int radius = (int)(agar.getSize()/2); 
        return new Point(position.x + radius, position.y + radius);
    }
    /**
     * Get center of sprite
     * @param sprite-  sprite
     * @return sprite's center
     */
    public static Point getCenter(Sprite sprite) {
        Point position = sprite.getPosition();
        int radius = (int)(sprite.getSize()/2); 
        return new Point(position.x + radius, position.y + radius);
    }
    
}
