/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ColorBackground;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.background.TileBackground;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuyenhuynh
 */
public class MyGame extends Game{

    private static final int MAX_WIDTH = 1000;
    private static final int MAX_HEIGHT = 1000;
    
    Sprite cell; 
    TileBackground  tileBackground;
    BufferedImage[] tileImages; 
    int[][] tiles = new int[40][30]; // 40 x 30 tiling

    Sprite bgSprite; 
    
    @Override
    public void initResources() {
        BufferedImage image = getImage("resources/cell.png");
        cell = new Sprite(image);
    
        tileImages = new BufferedImage[] {getImage("resources/background.jpg")};
    
        // fill tiles with random value
        for (int i=0;i < tiles.length;i++){
            for (int j=0;j < tiles[0].length;j++) {
               tiles[i][j] = getRandom(0, tileImages.length-1);
            }
        }
        
        // create the background
        tileBackground = new TileBackground(tileImages, tiles);
    }

    @Override
    public void update(long l) {
        
        cell.update(l);
        
        tileBackground.update(l);

        cell.move(getMouseX() - cell.getX(), getMouseY() - cell.getY());
 
        
        if(cell.getX() + cell.getWidth() >= getWidth() -10 ) {
            System.out.println("Out of right bound");
            cell.setX(cell.getOldX());
            tileBackground.move(2, 0);
        }
        
        if(cell.getX() <=10 ) {
            System.out.println("Out of left bound"); 
            cell.setX(cell.getOldX());
            tileBackground.move(-2, 0);
        }   
        
        if( cell.getY() + cell.getHeight() >= getHeight() -10 ) {
            System.out.println("Out of top bound");
            cell.setY(cell.getOldY());
            tileBackground.move(0, 2);
        }
        
        if( cell.getY() <= 10 ) {
            System.out.println("Out of top bound");
            cell.setY(cell.getOldY());
            tileBackground.move(0, -2);
        }
    }

    @Override
    public void render(Graphics2D gd) {
        tileBackground.render(gd);
        cell.render(gd);
    }
    
    public static void main(String[] args) {
        GameLoader game = new GameLoader();
        game.setup(new MyGame(), new Dimension(600,400), false);
        game.start();
    }
    
}
