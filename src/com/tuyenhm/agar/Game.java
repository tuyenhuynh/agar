/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import com.tuyenhm.agar.collision.PlayerToObstacleCollision;
import com.golden.gamedev.object.CollisionManager;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ImageBackground;
import com.tuyenhm.agar.collision.PlayerToAgarCollision;
import com.tuyenhm.agar.controller.AIController;
import com.tuyenhm.agar.controller.Controller;
import com.tuyenhm.agar.controller.PlayerController;
import java.awt.BasicStroke;
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
    
    private static final int MAX_AGAR_COUNT = 0; 
    
    private static final int AGAR_GEN_INTERVAL = 500 ; 
    
    private static final int AGAR_SIZE = 30; 
    
    private static final int INIT_AGAR_COUNT = 30;
    
    private static final int OBSTACLE_COUNT =  30; 
    
    private Color[] colors = new Color[]{Color.BLUE, Color.GREEN, Color.GRAY, 
        Color.CYAN, Color.ORANGE, Color.PINK, Color.YELLOW, Color.MAGENTA, 
        Color.WHITE}; 
    
    private Timer timer = new Timer(AGAR_GEN_INTERVAL); 
    
    private Random random = new Random(); 
    
    private ImageBackground background; 
    
    private final SpriteGroup agarGroup = new SpriteGroup("Agars"); 
    
    private final SpriteGroup playerGroup = new SpriteGroup("Player"); 
    
    private final SpriteGroup  obstacleGroup = new SpriteGroup("Obstacles"); 
    
    private Sprite playerSprite; 
    
    private final List<Controller> controllers = new ArrayList<>(); 
    
    private final List<Agar> agars = new ArrayList<>(); 
    
    private final List<Obstacle> obstacles = new ArrayList<>(); 
    
    private CollisionManager playerAgarCm;
    private CollisionManager playerObstacleCm; 
    
    @Override
    public void initResources() {
        try{
            
            BufferedImage playerImage = ImageIO.read(new File("resources/PRIMITIVE_PLANT.png"));
            BufferedImage botImage = ImageIO.read(new File("resources/PRIMITIVE_ANIMAL.png"));
            
            playerSprite = new Sprite(100); 
            playerSprite.setSpeed(0.1);
            playerSprite.setColor(Color.RED);
            playerSprite.setIcon(playerImage);
            playerSprite.setPosition(new Point(320, 240));
            playerGroup.add(playerSprite);
            
            background = new ImageBackground(ImageIO.read(new File("resources/background.jpg")));
            background.setClip(0, 0, this.dimensions().width, this.dimensions().height);
            
            agarGroup.setBackground(background);
            playerGroup.setBackground(background);
            obstacleGroup.setBackground(background);
            
            for(int i = 0 ; i < INIT_AGAR_COUNT ; ++i) {
                Agar agar = generateAgar(new Point(50 + random.nextInt(800), 50 + random.nextInt(600)));
            }
            
            controllers.add(new PlayerController(this, playerSprite));
            
            for(int i = 0 ; i < 1; ++i) {
                Point p = new Point(random.nextInt(700), random.nextInt(500)); 
                generateObstacle( p); 
            }
            
            for(int i = 0 ; i < OBSTACLE_COUNT; ++i) {
                Point p = new Point(random.nextInt(TOTAL_WIDTH - 200), random.nextInt(TOTAL_HEIGHT -200)); 
                generateObstacle(p); 
            }
            
            playerObstacleCm = new PlayerToObstacleCollision();
            playerObstacleCm.setCollisionGroup(playerGroup, obstacleGroup);
            playerAgarCm = new PlayerToAgarCollision(); 
            playerAgarCm.setCollisionGroup(playerGroup, agarGroup);
            
        }catch(IOException ex ){
            ex.printStackTrace();
        }
    }

    @Override
    public void update(long elapsedTime) {
        playerAgarCm.checkCollision();
        playerObstacleCm.checkCollision();

        for (Controller c : controllers) {
            c.update(elapsedTime);
        }

        agarGroup.update(elapsedTime);
        playerGroup.update(elapsedTime);
        obstacleGroup.update(elapsedTime);
        
        background.update(elapsedTime);
        
        if(timer.action(elapsedTime)){
            if(agars.size() < MAX_AGAR_COUNT) { 
                int x = random.nextInt(TOTAL_WIDTH - AGAR_SIZE), 
                    y = random.nextInt(TOTAL_HEIGHT - AGAR_SIZE);
                
                Point point = playerSprite.getPosition();
                
                if(x >= point.x && x <=point.x + playerSprite.getSize() && y >= point.y && y <= playerSprite.getSize()) {
                    x += playerSprite.getSize() + 100;
                    y += playerSprite.getSize() + 100; 
                }
                if(x >= TOTAL_WIDTH) {
                    x -= 200; 
                }
                if(y >= TOTAL_HEIGHT){
                    y -= 200; 
                }
                generateAgar(new Point(x, y)); 
            }
        }
    }
    
    @Override
    public void render(Graphics2D gd) {
        background.render(gd);
        agarGroup.render(gd); 
        obstacleGroup.render(gd);
        playerGroup.render(gd);
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
    
    private Agar generateAgar(Point position) {
        Agar agar= new Agar(AGAR_SIZE); 
        Color color = colors[random.nextInt(colors.length - 1)]; 
        BufferedImage bimage = new BufferedImage(50, 
                50, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D    g2d = bimage.createGraphics(); 
        g2d.setColor(color);
        g2d.fillOval(0, 0, AGAR_SIZE, AGAR_SIZE);
        agar.setImage(bimage);
        agar.setPosition(position); 
        agar.setBackground(background);
        agars.add(agar);
        
        agarGroup.add(0, agar);
        return agar;
    }
    
    private void generateObstacle(Point position) {

        try{
            BufferedImage obstacleImage = ImageIO.read(new File("resources/black-stone.png"));
            Obstacle obstacle = new Obstacle(120); 
            obstacles.add(obstacle);

            Graphics2D g2d = obstacleImage.createGraphics();
            obstacle.setColor(Color.RED);
            obstacle.setIcon(obstacleImage);
            obstacle.setPosition(position);
            obstacle.setBackground(background);
            obstacle.setImage(obstacleImage);
            
            obstacleGroup.add(0, obstacle);
            
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
