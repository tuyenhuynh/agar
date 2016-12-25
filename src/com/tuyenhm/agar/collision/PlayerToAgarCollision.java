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
import java.awt.Point;
import java.util.logging.Logger;

/**
 * Player to agar collision
 * @author tuyenhuynh
 */
public class PlayerToAgarCollision extends BasicCollisionGroup{
    
    /**
     * Actions when collided
     * @param player Player
     * @param agar Agar
     */
    @Override
    public void collided(Sprite player, Sprite agar) {
        com.tuyenhm.agar.Sprite playerSprite = (com.tuyenhm.agar.Sprite)player; 
        Agar agarSprite = (Agar)agar;
        GameMath.getCenter(playerSprite);
        GameMath.getCenter(agarSprite);
        
        Point p1 = agarSprite.getPosition(), 
                    p2 = playerSprite.getPosition();
           
        // get agar center point
        Point c1 = new Point(p1.x + (int)(agarSprite.getSize()/2), p1.y + (int) (agarSprite.getSize()/2));
        // get player center point
        Point c2 = new Point(p2.x + (int)(playerSprite.getSize()/2), p2.y +(int)(playerSprite.getSize()/2));

        // check if player eating agar
        if(GameMath.distance(c1, c2) < playerSprite.getSize()/2) {
            agar.setActive(false);
        }
    }
    
}
