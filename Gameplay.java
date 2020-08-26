package BrickBreaker.JAVA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer time;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    private Timer timer;

    public Gameplay() {
        //Set Number and Layout of Bricks
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        //Set Timer
        timer = new Timer(delay, this);
        timer.start();
}
public void paint(Graphics g) {
    //background
    g.setColor(Color.black);
    //Setting the areas to be colored
    g.fillRect(1,2, 692, 592);
    g.fillRect(0, 0, 3, 592);
    g.fillRect(691, 0, 3, 592);

    //drawing map
    map.draw((Graphics2D)g);

    //score
    g.setColor(Color.white);
    g.setFont(new Font("serif", Font.BOLD, 25));
    g.drawString("Score: " + score, 550, 30);

    //paddle
    g.setColor(Color.green);
    g.fillRect(playerX, 550, 100, 8);

    //ball
    g.setColor(Color.yellow);
    g.fillOval(ballposX, ballposY, 20, 20);

    //won game
    if(totalBricks <= 0) {
        //End Game
        play = false;

        //Remove Ball From Screen
        ballXdir = 0;
        ballYdir = 0;

        //Sets Conditions for Win Message (Adjusted Center)
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("You Won! Score: " + score, 200, 300);

        //Win Message Second Line
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Press Enter to Restart", 232, 350);
    }

    //Game Over
    if(ballposY > 570) {
        //End Game
        play = false;

        //Removes Ball From Screen
        ballXdir = 0;
        ballYdir = 0;

        //Sets Conditions for Win Message (Adjusted Center)
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("Game Over, Score: " + score, 190, 300);

        //Win Message Second Line
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Press Enter to Restart", 232, 350);
    }
    //Closes Graphics Resources to Stop Game
    g.dispose();
}


    @Override
    public void actionPerformed(ActionEvent e) {
        //Start Timer to Begin Game
        timer.start();
        if(play) {
            //Checking if ball contacts Block
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects
            (new Rectangle(playerX, 550, 100, 8))) {
                //Returning ball after contact
                ballYdir = -ballYdir;
            }

            A: for(int i = 0; i < map.map.length; i++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i  * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);

                            //Record Brick Count and Score
                            totalBricks--;
                            score += 5;

                            //If Ball Hits Brick anywhere along its width
                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                //Return Ball After Contact at Same angle
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }


            ballposX += ballXdir;
            ballposY += ballYdir;

            //Return Ball to the Center After Contacting Sides
            if(ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if(ballposX > 670) {
                ballXdir = -ballXdir;
            }

            //Return ball to the Center After Contacting Top of Window
            if(ballposY < 0) {
                ballYdir = -ballYdir;
            }

        }
        //Refresh the window after every tiny change in locations
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    //Nothing happens with any other key press
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //If Right Arrow is Pressed
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //Sets Maximum extent of Responding to Right Arrow Press
            if(playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        //If Left Arrow is Pressed
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            //Sets Maximum extent of Responding to Left Arrow Press
            if(playerX <= 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        //If Enter is Pressed
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            //Checking Game is Not in Progress
            if(!play) {
                //Start Game
                play = true;
                //Setting Position and Direction of Ball
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;

                //Sets center of Paddle at center of screen
                playerX = 310;

                //Reset Total Score
                score = 0;

                //Setting Brick Layout and Totals
                totalBricks = 21;
                map = new MapGenerator(3,7);

                //Refresh the window after every tiny change in locations
                repaint();
            }
        }

    }

    public void moveRight() {
    play = true;
    //Sets how far paddle moves with each key press
    playerX += 20;
    }

    public void moveLeft() {
        play = true;
        //Sets how far paddle moves with each key press
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    //Nothing happens
    }
}
