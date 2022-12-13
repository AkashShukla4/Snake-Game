
import javax.swing.JFrame;

public class Frame extends JFrame{

    Frame(){
        this.add(new Panel());          //Adding the panel in the frame
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);            //We dont't want the user to change the Game Window
        this.pack();                        // Sets the window size to the preferred size.
        this.setVisible(true);             //to make the frame visible to the user
        this.setLocationRelativeTo(null);           //to set the position on the display (center)

    }

}
