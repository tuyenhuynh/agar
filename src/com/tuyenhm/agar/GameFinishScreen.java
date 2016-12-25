/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.background.ImageBackground;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Game finish scene
 * @author tuyenhuynh
 */
public class GameFinishScreen extends GameObject { // change Game to GameObject
    
    /**
     * background
     */
    private ImageBackground background; 
    
    /**
     * Constructor
     * @param parent game engine
     */
    public GameFinishScreen(GameEngine parent) {
       super(parent);
    }

    /**
     * Update scene
     * @param elapsedTime elapsed time
     */
    public void update(long elapsedTime) {
        background.update(elapsedTime);
        // if click then go to game scene
        if (click()) {
          parent.nextGameID = 0;
          finish();
       }
    }

    /**
     * Init resources
     */
    @Override
    public void initResources() {
        try{
            background = new ImageBackground(ImageIO.read(new File("resources/background.jpg")));
            background.setClip(0, 0, Game.dimensions().width, Game.dimensions().height);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Render screen
     * @param gd graphics 2D
     */
    @Override
    public void render(Graphics2D gd) {
        background.render(gd);
        gd.setFont(new Font("Arial", Font.BOLD, 20));
        gd.setColor(Color.RED);
        gd.drawString("CLICK INTO SCREEN TO START NEW GAME" , 170,300 );
    }

 }
 