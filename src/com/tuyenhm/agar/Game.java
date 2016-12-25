/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuyenhm.agar;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ImageBackground;
import com.tuyenhm.agar.collision.PlayerToAgarCollision;
import com.tuyenhm.agar.collision.PlayerToObstacleCollision;
import com.tuyenhm.agar.controller.AIController;
import com.tuyenhm.agar.controller.Controller;
import com.tuyenhm.agar.controller.PlayerController;
import com.tuyenhm.agar.events.CollisionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Game scene
 * @author tuyenhuynh
 */
public class Game extends com.golden.gamedev.GameObject{
    /**
     * Map width
     */
    public static final int TOTAL_WIDTH = 6000; 
    /**
     * Map height
     */
    public static final int TOTAL_HEIGHT = 6000;
    
    private static final int MAX_AGAR_COUNT = 500; 
    
    private static final int AGAR_GEN_INTERVAL = 50 ; 
    
    private static final int AGAR_SIZE = 30; 
    
    private static final int OBSTACLE_SIZE = 120;
    
    private static final int INIT_AGAR_COUNT = 10;
    
    private static final int INIT_SPRITE_SIZE = 100;
    
    private static final int OBSTACLE_COUNT =  30; 
    
    private static final int BOT_COUNT = 10;
    
    private Color[] colors = new Color[]{Color.BLUE, Color.GREEN, Color.GRAY, 
        Color.CYAN, Color.ORANGE, Color.PINK, Color.YELLOW, Color.MAGENTA, 
        Color.WHITE}; 
    
    private Timer timer = new Timer(AGAR_GEN_INTERVAL); 
    
    private Random random = new Random(); 
    
    private ImageBackground background; 
    
    private final SpriteGroup agarGroup = new SpriteGroup("Agars"); 
    
    private final SpriteGroup spriteGroup = new SpriteGroup("Player"); 
    
    private final SpriteGroup  obstacleGroup = new SpriteGroup("Obstacles"); 
    
    private Sprite playerSprite; 
    
    private final List<Controller> controllers = new ArrayList<>(); 
    
    private final List<Agar> agarList = new ArrayList<>(); 
    
    private final List<Obstacle> obstacleList = new ArrayList<>(); 
    
    private PlayerToAgarCollision playerAgarCm;
    private PlayerToObstacleCollision playerObstacleCm; 
    
    private final List<Sprite> spriteList = new ArrayList<>(); 
    
    /**
     * Constructor
     * @param gameEngine 
     */
    public Game(GameEngine gameEngine) {
        super(gameEngine); 
    }
    
    /**
     * Init Resources
     */
    @Override
    public void initResources() {
        try{
            // set Images from resource files
            BufferedImage playerImage = ImageIO.read(new File("resources/PRIMITIVE_PLANT.png"));
            BufferedImage botImage = ImageIO.read(new File("resources/PRIMITIVE_ANIMAL.png"));
            
            // create player object
            playerSprite = new Sprite(100); 
            playerSprite.setSpeed(0.1);
            playerSprite.setColor(Color.RED);
            playerSprite.setIcon(playerImage);
            playerSprite.setPosition(new Point(320, 240));
            
            // add player to sprite list
            spriteGroup.add(playerSprite);
            spriteList.add(playerSprite); 
            
            // add player controller
            controllers.add(new PlayerController(this, playerSprite));
            
            //generate AI bots
            Random r = new Random();
            for(int i = 0; i < BOT_COUNT; i++)
            {
                boolean added = true; 
                do {
                    Point point = new Point(random.nextInt(TOTAL_WIDTH - 200), random.nextInt(TOTAL_HEIGHT -200)); 
                    added = addNewBot(botImage, point);    
                } while (!added); 
            }
            
            // set game background
            background = new ImageBackground(ImageIO.read(new File("resources/background.jpg")));
            background.setClip(0, 0, this.dimensions().width, this.dimensions().height);
            
            // set background for object groups
            agarGroup.setBackground(background);
            spriteGroup.setBackground(background);
            obstacleGroup.setBackground(background);
            
            // generate agars
            for(int i = 0 ; i < INIT_AGAR_COUNT ; ++i) {
                Agar agar = generateAgar(new Point(50 + random.nextInt(800), 50 + random.nextInt(600)));
            }
            
            // generate obstacles in first scene
            Point p = new Point(random.nextInt(700), random.nextInt(500)); 
            boolean overlapped  = true; 
            do {
                // check obstacle is overlapped with other objects if put it to Point p
                int gap = 20;
                p = new Point(random.nextInt(700), random.nextInt(500)); 
                for(Sprite sprite: spriteList) {
                    Point position = sprite.getPosition();
                    overlapped = p.x >= position.x - sprite.getSize() - gap && p.x <= position.x + gap + sprite.getSize() 
                    ||  p.y >= position.y - sprite.getSize() -gap && p.y <= position.y + sprite.getSize() + gap;
                }
            } while(overlapped);
            // generate obstacle to point p
            generateObstacle(p);
            
            // generate obstacle in full map
            for(int i = 0 ; i < OBSTACLE_COUNT; ++i) {
                Point point = new Point(random.nextInt(TOTAL_WIDTH - 200), random.nextInt(TOTAL_HEIGHT -200)); 
                generateObstacle(point); 
            }
            
            playerAgarCm = new PlayerToAgarCollision(); 
            playerAgarCm.setCollisionGroup(spriteGroup, agarGroup);
            playerObstacleCm = new PlayerToObstacleCollision();
            playerObstacleCm.setCollisionGroup(spriteGroup, obstacleGroup);
            
            playerObstacleCm.pixelPerfectCollision = true; 
            
        }catch(IOException ex ){
            ex.printStackTrace();
        }
    }
    
    /**
     * Check if 2 sprites is overlapped
     * @param s1 first sprite
     * @param s2 second spite
     * @return true of s1 overlaps s2
     */
    private boolean isOverlapped(com.golden.gamedev.object.Sprite s1, com.golden.gamedev.object.Sprite s2) {
        int gap = 20;
        Point p1 = null, p2 = null; 
        int size1 = 0, size2 = 0; 
        if(s1 instanceof Agar) {
            size1 = AGAR_SIZE; 
            p1 = ((Agar)s1).getPosition();
        }
        if(s1 instanceof Obstacle) {
            size1 = OBSTACLE_SIZE; 
            p1 = ((Obstacle)s1).getPosition();
        }
        if(s1 instanceof Sprite) {
            size1 = ((Sprite)s1).getSize(); 
            p1 = ((Sprite)s1).getPosition();
        }
        
        if(s2 instanceof Agar) {
            size2 = AGAR_SIZE; 
            p2 = ((Agar)s2).getPosition();
        }
        if(s2 instanceof Obstacle) {
            size2 = OBSTACLE_SIZE; 
            p2 = ((Obstacle)s2).getPosition();
        }
        if(s2 instanceof Sprite) {
            size2 = ((Sprite)s2).getSize(); 
            p2 = ((Sprite)s2).getPosition();
        }
        
        boolean overlapped = GameMath.distance(p1, p2) <= size1 + size2 + gap ;
        return overlapped;        
    }
    
    /**
     * Add new bot
     * @param botImage bot image
     * @param position position
     * @return true if generate successfully
     */
    private boolean addNewBot(BufferedImage botImage, Point position){
        // create new bot object
        Sprite botSprite = new Sprite(botImage.getHeight());
        // set bot position
        botSprite.setPosition(position);
        // check if new bot is overlapped with other sprites
        for(Sprite sprite : spriteList){
            if(sprite.isActive() && isOverlapped(sprite, botSprite)){
                return false; 
            }
        }
        
        // check if new bot overlaps with obstacles
        for(Obstacle obstacle: obstacleList){
            if(isOverlapped( obstacle, botSprite)){
                return false; 
            }
        }
        // check if new bot overlaps with agars
        for (Agar agar : agarList){
            if(agar.isActive() && isOverlapped(agar, botSprite)){
                return false; 
            }
        }
        
        botSprite.setSpeed(0.1);
        botSprite.setColor(Color.GREEN);
        botSprite.setIcon(botImage);
        spriteGroup.add(botSprite);
        // add bot controller
        controllers.add(new AIController(this, botSprite, playerSprite));
        spriteList.add(botSprite); 
        
        return true;
        
    }

    /**
     * update scene
     * @param elapsedTime elapsed time
     */
    @Override
    public void update(long elapsedTime) {
        // check collisions
        playerAgarCm.checkCollision();
        playerObstacleCm.checkCollision();
        // update all game objects
        for (Controller c : controllers) {
            c.update(elapsedTime);
        }
        // update group
        agarGroup.update(elapsedTime);
        spriteGroup.update(elapsedTime);
        obstacleGroup.update(elapsedTime);
        // update backgroud
        background.update(elapsedTime);
        
        if(timer.action(elapsedTime)){
            // check if number of agars on game is less than max agar number
            if(agarList.size() < MAX_AGAR_COUNT) { 
                // random new agar position
                int x = random.nextInt(TOTAL_WIDTH - AGAR_SIZE), 
                    y = random.nextInt(TOTAL_HEIGHT - AGAR_SIZE);
                
                Point point = playerSprite.getPosition();
                // if new point is overlapped with player position
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
                // generate agar to postion (x, y)
                generateAgar(new Point(x, y)); 
            }
        }
    }
   
    /**
     * render
     * @param gd graphics
     */
    @Override
    public void render(Graphics2D gd) {
        background.render(gd);
        agarGroup.render(gd); 
        obstacleGroup.render(gd);
        spriteGroup.render(gd);
        if(playerSprite != null){
            background.setToCenter(playerSprite);
        }
    }
    
    /**
     * get current mouse position
     * @return mouse position
     */
    public Point mousePosition(){
        Point p = new Point(this.getMouseX(), this.getMouseY());
        p.x += background.getX();
        p.y += background.getY(); 
        return p;
    }
    
    /**
     * get game dimension
     * @return dimension
     */
    public static Dimension dimensions(){
        return new Dimension(800, 600);
    }
    
    /**
     * generate agar to position
     * @param position position
     * @return generated agar
     */
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
        agarList.add(agar);
        
        agarGroup.add(agar);
        return agar;
    }
    
    /**
     * generate obstacle to position
     * @param position 
     */
    private void generateObstacle(Point position) {
        try{
            // get obstacle image
            BufferedImage obstacleImage = ImageIO.read(new File("resources/danger.png"));
            Obstacle obstacle = new Obstacle(OBSTACLE_SIZE); 
            obstacleList.add(obstacle);

            obstacle.setColor(Color.YELLOW);
            obstacle.setIcon(obstacleImage);
            obstacle.setPosition(position);
            obstacle.setBackground(background);
            obstacleGroup.add(0, obstacle);            
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
