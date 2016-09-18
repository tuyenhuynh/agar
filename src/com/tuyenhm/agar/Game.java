/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.background.ImageBackground;
import com.tuyenhm.agar.controller.AIController;
import com.tuyenhm.agar.controller.Controller;
import com.tuyenhm.agar.controller.PlayerController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author tuyenhuynh
 */
public class Game extends com.golden.gamedev.Game{

    public static final int TOTAL_WIDTH = 6000; 
    public static final int TOTAL_HEIGHT = 6000;
    
    private ImageBackground background; 
    
    private final SpriteGroup spriteGroup = new SpriteGroup("Objects"); 
    
    private Sprite playerSprite; 
    
    private final List<Controller> controllers = new ArrayList<>(); 
    
    private final List<Sprite> viruses = new ArrayList<>(); 
    
    private final List<Sprite> agars = new ArrayList<>(); 
    
    private int botsCount = 3 ; 
    
    @Override
    public void initResources() {
        try{
            BufferedImage playerImage = ImageIO.read(new File("resources/PRIMITIVE_PLANT.png"));
            BufferedImage botImage = ImageIO.read(new File("resources/PRIMITIVE_ANIMAL.png"));
            
            playerSprite = new Sprite(); 
            playerSprite.setSpeed(0.1);
            playerSprite.setColor(Color.RED);
            playerSprite.setIcon(playerImage);
            playerSprite.setPosition(new Point(320, 240));
        
            Random r = new Random();
            for(int i = 0; i < 3; i++)
            {
                Sprite botSprite = new Sprite();
                botSprite.setSpeed(0.1);
                botSprite.setColor(Color.GREEN);
                botSprite.setIcon(botImage);
                botSprite.setPosition(new Point(160 + r.nextInt(320), 120 + r.nextInt(240)));
                spriteGroup.add(botSprite);
                controllers.add(new AIController(this, botSprite, playerSprite));
            }
            
            background = new ImageBackground(ImageIO.read(new File("resources/background.jpg")));
            background.setClip(0, 0, this.dimensions().width, this.dimensions().height);
            
            spriteGroup.setBackground(background);
            spriteGroup.add(playerSprite);
            
            controllers.add(new PlayerController(this, playerSprite));
            
        }catch(IOException ex ){
            ex.printStackTrace();
        }
    }

    @Override
    public void update(long l) {
        for (Controller c : controllers) {
            c.update(l);
        }
        spriteGroup.update(l);
        background.update(l);
    }

    @Override
    public void render(Graphics2D gd) {
        background.render(gd);
        spriteGroup.render(gd);
        
        if(playerSprite != null){
            background.setToCenter(playerSprite);
        }
    }
    
    public Point mousePosition(){
        Point p = new Point(this.getMouseX(), this.getMouseY());
        p.x += background.getX();
        p.y += background.getY(); 
        
        return p;
        
    }
    
    public Dimension dimensions(){
        return new Dimension(800, 600);
    }
    
}
