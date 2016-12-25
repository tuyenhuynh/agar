/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar.controller;

import com.tuyenhm.agar.Game;
import com.tuyenhm.agar.Sprite;

/**
 *
 * @author tuyenhuynh
 */
public class Controller {
    protected Sprite sprite; 
    protected Game game; 
    
    /**
     * Controller's constructor
     * @param game - Game, which contains controller
     * @param sprite - sprite, managed by controller
     */
    public Controller(Game game, Sprite sprite) {
        this.game = game; 
        this.sprite = sprite; 
    }
    
    /**
     * Update Controller status
     * @param elapsedTime - elapsed time
     */
    public void update(long elapsedTime){
        if(sprite.getX() <= 0 && sprite.getHorizontalSpeed() < 0) {
            sprite.setHorizontalSpeed(0);
        }
        if(sprite.getX() + sprite.getWidth() > game.TOTAL_WIDTH && sprite.getHorizontalSpeed() >0) {
            sprite.setHorizontalSpeed(0);
        }
        if(sprite.getY() <= 0 && sprite.getVerticalSpeed() < 0) {
            sprite.setVerticalSpeed(0);
        }
        if(sprite.getY() + sprite.getWidth() > game.TOTAL_HEIGHT && sprite.getVerticalSpeed() >0) {
            sprite.setVerticalSpeed(0);
        }
    }   
}
