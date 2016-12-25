/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;

/**
 * Class game agar
 * @author tuyenhuynh
 */
public class AgarGame extends GameEngine{
    /**
     * Get GameObject based on GameID
     * @param GameID gameID
     * @return GameObject based on GameID
     */
    public GameObject getGame(int GameID) {
        return new Game(this);
    }

}
