
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {

    static int width = 800;      //Dimensions of the Panel
    static int height = 500;
    static int unit = 40;      // Size of ech unit

    Timer timer;       //For checking the State of the Game(Making snake look like its Moving)

    static int delay = 280;    // in what time we want to check the state
    Random random;       // For Food Spawns

    int fx , fy;     //Coordinates to place the food randomly
    int body = 3;         //Defining the body of the Snake

    char dir = 'R' ;
    int score = 0;
    boolean flag = false;

    static int snakeSize = (width * height) / (unit * unit);      // Defining max Size of the Snake

    int xSnake[] = new int[snakeSize];   //the x and y blocks of the snake in these Arrays
    int ySnake[] = new int[snakeSize] ;


    Panel(){
        setPreferredSize(new Dimension(width , height));

        this.setBackground(Color.BLACK);

        this.setFocusable(true);       // to make sure that the keyboard input is on the Game

        random = new Random();

        this.addKeyListener(new Key());    //Adding the keyListener to the Panel

        game_start();          // Function to start the Game
    }

    public void game_start(){
        spawnFood();            // Spawning the Food

        flag = true;       //Telling that the game is started

        timer = new Timer(delay, this); //Starting the Timer with Delay

        timer.start();
    }

    public void spawnFood(){
                                 // Setting the random coordinates for the food in 50 multiple
        fx = random.nextInt ((int)(width / unit)) * unit;
        fy = random.nextInt ((int)(height / unit)) * unit;

    }

    public void checkHit(){
                  //checking the snakes head collision with body or walls
        for(int i = body ; i > 0; i--){
                                            //checking for body collision
            if((xSnake[0] == xSnake[i]) && (ySnake[0] == ySnake[i])){
                flag = false;
               }

            // Checking for the Walls
            if(xSnake[0] < 0){
                flag = false;
              }
            if(xSnake[0] > width){
                flag = false;
            }
            if(ySnake[0] < 0){
                flag = false;
            }
            if(ySnake[0] > height){
                flag = false;
             }
        if(flag == false){
            timer.stop();
          }
        }
     }
                                                   // Intermediate function to call the draw function
     public void paintComponent(Graphics graphic){

        super.paintComponent(graphic);

        draw(graphic);

     }

     public void draw(Graphics graphic) {

         if (flag) {
             // Setting the parameters for the food block
             graphic.setColor(Color.ORANGE);
             graphic.fillOval(fx, fy, unit, unit);

             //Setting parameters for the Snake
             for (int i = 0; i < body; i++) {
                 //for Head
                 if (i == 0) {
                     graphic.setColor(Color.DARK_GRAY);
                     graphic.fillRect(xSnake[i], ySnake[i], unit, unit);
                 }
                 //For body of the Snake
                 else {
                     graphic.setColor(Color.GREEN);
                     graphic.fillRect(xSnake[i], ySnake[i], unit, unit);
                 }
             }
             //Drawing the Score display
             graphic.setColor(Color.BLUE);
             graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));

             FontMetrics f = getFontMetrics(graphic.getFont());
             // Drawstring takes the string to draw, starting position in x and
             // the starting position in y
             graphic.drawString("Score: " + score, (width - f.stringWidth("Score: " + score)) / 2, graphic.getFont().getSize());
         }
         else{
             gameOver(graphic);
         }
     }

     public void gameOver(Graphics graphic){
         graphic.setColor(Color.GRAY);
         graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));

         FontMetrics f = getFontMetrics(graphic.getFont());
         // Drawstring takes the string to draw, starting position in x and
         // the starting position in y
         graphic.drawString("Score: " + score, (width - f.stringWidth("Score: " + score)) / 2, graphic.getFont().getSize());

         //Graphic for the Game Over Text
         graphic.setColor(Color.BLUE);
         graphic.setFont(new Font("Comic Sans", Font.BOLD, 60));

         FontMetrics f2 = getFontMetrics(graphic.getFont());
         // Drawstring takes the string to draw, starting position in x and
         // the starting position in y
         graphic.drawString("GAME OVER !! " , (width - f2.stringWidth("GAME OVER !! ")) / 2, height/2);

         //Graphic for the Restart Prompt the Game
         graphic.setColor(Color.MAGENTA);
         graphic.setFont(new Font("Comic Sans", Font.BOLD, 35));

         FontMetrics f3 = getFontMetrics(graphic.getFont());
         // Drawstring takes the string to draw, starting position in x and
         // the starting position in y
         graphic.drawString("PRESS [R] TO RESTART " , (width - f3.stringWidth("PRESS [R] TO RESTART ")) / 2, height/2 - 150);

     }

     public void move(){
                                        //Loop for moving the Snake's Body part except head
        for(int i = body; i > 0; i--){
            xSnake[i] = xSnake[i-1];
            ySnake[i] = ySnake[i -1];
        }
                                       // For the update the movement of the Head Coordinates
        switch(dir){
            case 'U':
            ySnake[0] = ySnake[0] - unit;
                break;
            case 'D':
                ySnake[0] = ySnake[0] + unit;
                break;
            case 'L':
                xSnake[0] = xSnake[0] - unit;
                break;
            case 'R':
                xSnake[0] = xSnake[0] + unit;
                break;
        }
     }

     public void checkScore(){
        if((fx == xSnake[0]) && (fy == ySnake[0])){
            body++;
            score++;
            spawnFood();
        }
    }

    public class Key extends KeyAdapter{         //To add Functionality to the key input by the User
    @Override
        public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                if(dir != 'R'){
                    dir = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(dir != 'L'){
                    dir = 'R';
                 }
                break;
            case KeyEvent.VK_DOWN:
                if(dir != 'U'){
                    dir = 'D';
                }
                break;
            case KeyEvent.VK_UP:
                if(dir != 'D'){
                    dir = 'U';
                }
                break;
            case KeyEvent.VK_R:
                if(!flag) {
                            //Setting all the Initial condition to restart Game
                    score = 0;
                    body = 3;
                    dir = 'R';
                    Arrays.fill(xSnake, 0);
                    Arrays.fill(ySnake, 0);
                    game_start();
                }
                break;
        }
    }
    }

    @Override
    public void actionPerformed(ActionEvent args0){    //It will check state of the Game(Frame per second)

        if(flag){
            move();
            checkScore();
            checkHit();
          }
        repaint();
    }
}
