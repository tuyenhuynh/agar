/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar.controller;

import com.tuyenhm.agar.Game;
import com.tuyenhm.agar.GameMath;
import com.tuyenhm.agar.Sprite;
import java.util.logging.Logger;

/**
 *
 * @author tuyenhuynh
 */
public class PlayerController extends Controller{
    private static final Logger logger = Logger.getLogger(PlayerController.class.getName());
    
    /**
     * PlayerController's constructor
     * @param game - Game, which contains controller
     * @param sprite - sprite, managed by controller
     */
    public PlayerController(Game game, Sprite sprite) {
        super(game, sprite); 
    }
    
    /**
     * Update AIController status
     * @param elapsedTime - elapsed time
     */
    public void update(long elapsedTime) {
        int angle = GameMath.angle(sprite.getPosition(), game.mousePosition());
        sprite.setDirection(angle);
    }
}
