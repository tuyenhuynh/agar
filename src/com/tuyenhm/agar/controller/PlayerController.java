/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar.controller;

import com.tuyenhm.agar.Game;
import com.tuyenhm.agar.GameMath;
import com.tuyenhm.agar.Sprite;

/**
 *
 * @author tuyenhuynh
 */
public class PlayerController extends Controller{
    public PlayerController(Game game, Sprite sprite) {
        super(game, sprite); 
    }
    
    public void update(long elapsedTime) {
        int angle = GameMath.angle(sprite.getPosition(), game.mousePosition());
        sprite.setDirection(angle);
    }
}
