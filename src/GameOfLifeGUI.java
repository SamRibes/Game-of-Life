import javax.swing.JFrame;

//This class creates our Finch Control Form!
public class GameOfLifeGUI implements Runnable
{
	//Since the class 'Runnable' is an implementation we have to define this method
	public void run() 
	{
	    //Create and set up the Window
        JFrame frame = new JFrame("Game of Life");
        //Disable the exit 'X' and close menu
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Create and set up the content Pane based on our FinchControl class
        GameOfLifeControl newContentPane = new GameOfLifeControl();
        //Sets the panes as opaque
        newContentPane.setOpaque(true);
        //Puts the Pane in the Window
        frame.setContentPane(newContentPane);
        //Move to point 250,150 (offset from top left) and size to 830,900 pixels
        frame.setBounds(250,150,830,900);
        //Display the frame.
        frame.setVisible(true);
    }
}