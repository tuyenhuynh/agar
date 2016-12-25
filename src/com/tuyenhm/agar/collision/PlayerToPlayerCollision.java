/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar.collision;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;
import com.tuyenhm.agar.Agar;
import com.tuyenhm.agar.GameMath;
import com.tuyenhm.agar.events.CollisionListener;
import java.awt.Point;
import java.util.logging.Logger;

/**
 * Player to player collision
 * @author tuyenhuynh
 */
public class PlayerToPlayerCollision extends BasicCollisionGroup{
    private CollisionListener listener; 
    
    /**
     * Add collision listener
     * @param collisionListener listener
     */
    public void addCollisionListener(CollisionListener collisionListener){
        this.listener = collisionListener; 
    }
    
    /**
     * Actions when collided
     * @param sprite1 player 1
     * @param sprite2 player 1
     */
    @Override
    public void collided(Sprite sprite1, Sprite sprite2) {
        com.tuyenhm.agar.Sprite customSprite1 = (com.tuyenhm.agar.Sprite)(sprite1); 
        com.tuyenhm.agar.Sprite customSprite2 = (com.tuyenhm.agar.Sprite)(sprite2); 
        
        Point p1 = customSprite1.getPosition(), 
                    p2 = customSprite2.getPosition();
           
        // get player 1 center point
        Point c1 = new Point(p1.x + (int)(customSprite1.getSize()/2), p1.y + (int) (customSprite1.getSize()/2));
        // get player 2 center point
        Point c2 = new Point(p2.x + (int)(customSprite2.getSize()/2), p2.y +(int)(customSprite2.getSize()/2));
        
        // get player 1's agar number
        int size1 = customSprite1.getVictimCount(), 
        // get player 2's agar number
                size2 = customSprite2.getVictimCount(); 
        
        // check if player 2 eating player 1
        if(size2 > size1 && GameMath.distance(c1, c2) < customSprite2.getSize()/2) {
            customSprite2.eat(customSprite1.getVictimCount());
            customSprite1.setActive(false);
            if(customSprite1.isBot()) {
                listener.collided();
            }else {
                listener.gameFinished(); 
            }
        }
        // check if player 1 eating player 2
        if(size1 > size2 && GameMath.distance(c1, c2) < customSprite1.getSize()/2) {
            customSprite1.eat(customSprite2.getVictimCount());
            customSprite2.setActive(false);
            if(customSprite2.isBot()) {
                listener.collided();
            }else {
                listener.gameFinished(); 
            }
        }
    }
}
