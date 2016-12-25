/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar.events;

/**
 *
 * @author tuyenhuynh
 */
public interface CollisionListener {
    /**
     * Sprite collied with another.
     */
    public void collided(); 
    /**
     * Game finished.
     */
    public void gameFinished(); 
}
