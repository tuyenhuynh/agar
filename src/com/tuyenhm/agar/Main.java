/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import com.golden.gamedev.GameLoader;
import java.awt.Dimension;

/**
 *
 * @author tuyenhuynh
 */
public class Main {
    
    public static void main(String[] args) {
        Game game = new Game(); 
        GameLoader gameLoader = new GameLoader();
        gameLoader.setup(game, game.dimensions(), false);
        gameLoader.start();
    }
    
}
