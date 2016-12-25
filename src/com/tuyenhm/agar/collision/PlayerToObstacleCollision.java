/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar.collision;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;
import com.tuyenhm.agar.GameMath;
import java.util.logging.Logger;

/**
 * Player to obstacle collision
 * @author tuyenhuynh
 */
public class PlayerToObstacleCollision extends BasicCollisionGroup{
    /**
     * Actions when collided
     * @param playerSprite Player
     * @param obstacle Obstacle
     */
    @Override
    public void collided(Sprite playerSprite, Sprite obstacle) {
        com.tuyenhm.agar.Sprite sprite = (com.tuyenhm.agar.Sprite)(playerSprite); 
        sprite.setCollision(true);
        // set player speed backward
        playerSprite.setSpeed(-sprite.getHorizontalSpeed(), -sprite.getVerticalSpeed());
    }
}
