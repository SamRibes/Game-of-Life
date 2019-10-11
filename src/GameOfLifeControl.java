import javax.swing.AbstractButton;		//Imports needed for program
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

public class GameOfLifeControl extends JPanel implements ActionListener 	//Inherit functionality from 'JPanel' and 'JActionListener'
{
    private static final long serialVersionUID = 1862962349L;				//Unique ID
    //Create the * variables.
	private JButton startButton;
	private JButton endButton;
	private JButton pauseButton;
	private JButton resetButton;		//Start, End, Exit and Pause buttons.
	private JLabel iterationLabel;
	private Timer timer;													//Timer(used for spacing out the iterations).
	private static int GridSize = 250;										//Grid Size.
	private long Iterations;														//Keeps the count for the number of iterations.
	private boolean[][] GameArray;                                                    //The array that the board takes place on.
	private static ArrayList<boolean[][]> GridStorage = new ArrayList<>();	//Storage array for the Grid.
	private static ArrayList<int[][]> CountStorage = new ArrayList<>();		//Storage array for the Counts.
	private static final int TickTime = 100;										//The time in milliseconds between each iteration.

    GameOfLifeControl()
    {
    	//Caption for the Start button.
        startButton = new JButton("Start Game");
        startButton.setVerticalTextPosition(AbstractButton.CENTER);			//Center the text vertically
        startButton.setHorizontalTextPosition(AbstractButton.CENTER);		//Center the text horizontally
        startButton.setMnemonic(KeyEvent.VK_S);			//Short cut key of 'S'
        startButton.setActionCommand("Start");			//Create the event/action 'Start' when clicked
        
        //Caption for the Pause button.
        pauseButton = new JButton("Pause Game");
        pauseButton.setVerticalTextPosition(AbstractButton.CENTER);			//Center the text vertically
        pauseButton.setHorizontalTextPosition(AbstractButton.CENTER);		//Center the text horizontally
        pauseButton.setMnemonic(KeyEvent.VK_P);			//Short cut key of 'S'
        pauseButton.setActionCommand("Pause");			//Create the event/action 'Start' when clicked
        pauseButton.setEnabled(false);

        //Caption for the Reset button.
        resetButton = new JButton("Reset Game");
        resetButton.setVerticalTextPosition(AbstractButton.CENTER);      //Center the text vertically
        resetButton.setHorizontalTextPosition(AbstractButton.CENTER);    //Center the text horizontally
        resetButton.setMnemonic(KeyEvent.VK_R);       	//Short cut key of 'R'
        resetButton.setActionCommand("Reset");        	//Create the event/action 'Reset' when clicked
        resetButton.setEnabled(false);      			//Initially the Reset button is disabled
        
        //Caption for the Stop button.
        endButton = new JButton("Stop Game");
        endButton.setVerticalTextPosition(AbstractButton.CENTER);        //Center the text vertically
        endButton.setHorizontalTextPosition(AbstractButton.CENTER);      //Center the text horizontally
        endButton.setMnemonic(KeyEvent.VK_T);       	//Short cut key of 'T'
        endButton.setActionCommand("Stop");        		//Create the event/action 'Stop' when clicked
        endButton.setEnabled(false);      				//Initially the stop button is disabled
        
    	//Caption for the Exit button.
		JButton exitButton = new JButton("Exit Game");
        exitButton.setVerticalTextPosition(AbstractButton.CENTER);			//Center the text vertically
        exitButton.setHorizontalTextPosition(AbstractButton.CENTER);		//Center the text horizontally
        exitButton.setMnemonic(KeyEvent.VK_X);       	//Short cut key of 'X'
        exitButton.setActionCommand("Exit");        	//Create the event/action 'Exit' when clicked
        
        //Listen for actions from the buttons.
        startButton.addActionListener(this);
        endButton.addActionListener(this);
        resetButton.addActionListener(this);
        exitButton.addActionListener(this);
        pauseButton.addActionListener(this);
        
        //Text displayed in tooltips for the buttons
        startButton.setToolTipText("Click this button to start the game");
        endButton.setToolTipText("Click this button to stop the game");
        resetButton.setToolTipText("Click this button to reset the game");
        exitButton.setToolTipText("Click here to exit the game");
        pauseButton.setToolTipText("Click here to pause the game");
        
        //Create the labels
		//Number of iterations label and number of iterations actual(displays the actual number).
		JLabel iterationCaption = new JLabel("The number of iterations so far is: ");
        iterationLabel = new JLabel("(The number of iterations goes here)");
        iterationLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));	//Actual iteration label has a border

        //Add the Components to the Form
        add(startButton);
        add(pauseButton);
        add(resetButton);
        add(endButton);
        add(exitButton);
        add(iterationCaption);
        add(iterationLabel);
        
        timer = new Timer(TickTime, this);	//Create the timer
        timer.setActionCommand("Timer");
        timer.setInitialDelay(0);			//Start the timer
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	//Check for the 'Start' event
    	if (e.getActionCommand().equals("Start")) 
        {
        	System.out.println("Start pressed");	//Logs the button press
            startButton.setEnabled(false);			//Disables the 'Start' button.
            endButton.setEnabled(true);				//Enable the 'Stop' button
            resetButton.setEnabled(true);			//Enable the 'Reset' button
            pauseButton.setEnabled(true);			//Enable the 'Pause' button
            timer.start();							//Start the 'Timer'
        }
    	
    	//Check for the 'Pause' event
    	if (e.getActionCommand().equals("Pause")) 
        {
        	System.out.println("Pause pressed");	//Logs the button press
            startButton.setEnabled(true);			//Enable the 'Start' button
            endButton.setEnabled(true);				//Enable the 'Stop' button
            pauseButton.setEnabled(false);			//Disables the 'Pause' button.
            timer.stop();							//Start the 'Timer'
        }
    	
    	//Check for the 'Reset' event
    	if (e.getActionCommand().equals("Reset")) 
        {
        	System.out.println("Reset pressed");	//Logs the button press
            startButton.setEnabled(false);			//Disables the 'Start' button
            pauseButton.setEnabled(true);			//Disables the 'Pause' button.
            endButton.setEnabled(true);				//Enable the 'Stop' button
            Iterations = 0;							//Reset the iteration counter
            repaint();								//Redraws the window
            timer.start();							//Start the 'Timer'
        }
    	
    	//Check for the 'Stop' event
    	if (e.getActionCommand().equals("Stop"))
        {
        	System.out.println("Stop pressed");		//Logs the button press
            startButton.setEnabled(true);			//Enable the 'Start' button
            endButton.setEnabled(false);			//Disables the 'Stop' button
            pauseButton.setEnabled(false);			//Disables the 'Pause' button.
            timer.stop();							//Stop the 'Timer'
            Iterations = 0;							//Reset the iteration counter
            iterationLabel.setText("Iterations shown here");	//Reset the text for the display label
        }
    	
    	//Check for the 'Timer' event
    	if (e.getActionCommand().equals("Timer"))
        {
    		System.out.println("Timer");	//Logs the action
    		Iterations++;					//Adds 1 to the iteration count
        	iterationLabel.setText(String.valueOf(Iterations));		//Sets the label value to the iteration count
			/*try {
				PrintToFile(GridStorage.get((int) Iterations));
			} catch (IOException e1) {
				e1.printStackTrace();
			}*/
        	repaint();		//Redraws the window
        }
    	
    	//Test for the 'Exit' action
    	if (e.getActionCommand().equals("Exit"))
        {
        	System.out.println("Exit");		//Logs the button press
        	timer.stop();					//Stops the timer
        	JFrame parent = (JFrame)SwingUtilities.getWindowAncestor(this);	//Gets the parent JFrame of this panel
        	parent.setVisible(false);	//Hides the Window
        	parent.dispose();			//Gets rid of the Window
        }    	
    }
    
    //Override the super class paint method
    @Override
    public void paint(Graphics g) 
    {
		Color SquareColourDead = Color.RED;		//Sets the colour for death as red.
		Color SquareColourAlive = Color.GREEN;	//Sets the colour for alive as green.
    	super.paint(g);							//Sets up the painter.
    	
    	if (Iterations == 1)					//If it is on the first iteration (initial random) array
    	{
    		GameArray = MakeInitialArray();		//Then it will call the MakeInitialArray method to create the random array
			SetCellColour(g, SquareColourDead, SquareColourAlive);
		}
    	
    	if(Iterations > 1)							//If it's past the initial array
    	{
    		GameArray = ProgressArray(GameArray);	//Then it calls the ProgressArray method to create the next iteration of the array.
			SetCellColour(g, SquareColourDead, SquareColourAlive);
		}
    	//StoreGrid(GameArray);  //Stores the finished game board in StoreGrid ArrayList
    }

	private void SetCellColour(Graphics g, Color sqaurecolourDead, Color sqaureColourAlive) {
		int i;
		int j;
		for(i = 0; i < GridSize; i++)		//Loops through the array
		{
			for(j = 0; j < GridSize; j++)
			{
				if(!GameArray[i][j])		//If the cell is false then the rectangle is red.
				{
					g.setColor(sqaurecolourDead);	//Sets the colour.
					g.fillRect(5+4*i,50+4*j,4,4);	//Draws the rectangle based on the cell number (pushes it down by 100 to avoid going over the buttons and to the right by 4 to stay off the edge)
				}
				else//If the cell is true then the rectangle is green.
				{
					g.setColor(sqaureColourAlive);	//Sets the colour.
					g.fillRect(5+4*i,50+4*j,4,4);	//Draws the rectangle based on the cell number (pushes it down by 100 to avoid going over the buttons and to the right by 4 to stay off the edge)
				}

			}
		}
	}

	private static void PrintToFile(boolean[][] GridToWrite) throws IOException
    {
    	int i, j;
    	FileWriter fileWriter = new FileWriter("C:\\Users\\Sam\\Desktop\\Grid.txt");
    	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    	bufferedWriter.newLine();
		System.out.print(GridSize);
    	for(i = 0; i < GridSize; i++)
    	{
    		for(j = 0; j < GridSize; j++)
    		{
    			if(!GridToWrite[i][j])
    			{
    				bufferedWriter.write("D ");
    			}
    			else
    			{
    				bufferedWriter.write("A ");
    			}
    		}
    		bufferedWriter.newLine();
    	}
    	bufferedWriter.close();
    	fileWriter.close();
    }
    
	private static boolean[][] ProgressArray(boolean[][] OldArray)		//Method is used to create the next iteration of an already existing game board.
	{
		int i,j, neighbourCount;		//Creates the looping integers and the variable to hold the neighbour count.
		int[][] CountArray = new int[GridSize][GridSize];	//Creates the count array to be stored in the CountStorage ArrayList.
		boolean[][] NewArray = new boolean[GridSize][GridSize];	//Creates the new array to be returned and used as the next iteration.
		
		for(i = 0; i < GridSize; i++)		//Loops through array
		{
			for(j = 0; j < GridSize; j++)
			{
				neighbourCount = neighbourCount(i, j, OldArray);	//Uses the neighbourCount Method to check the number of neighbours for each cell.
				
				CountArray[i][j] = neighbourCount;				//Adds the neighbourCount to the CountStorage ArrayList
				
				if(OldArray[i][j])						//If the cell is alive
				{
					//And it has 2 or 3 neighbours
					//Then the cell stays alive
					//Else it dies
					NewArray[i][j] = neighbourCount == 2 || neighbourCount == 3;
				}
				else//If the cell is dead
				{
					//And it has 3 neighbours
					//Then the cell comes to life
					//Else it stays dead
					NewArray[i][j] = neighbourCount == 3;
				}
			}
		}
		StoreCount(CountArray);	//Stores the neighbourCounts to the CountStorage ArrayList
		return NewArray;		//Returns the filled out and progressed array
	}
	
	private static int neighbourCount(int row, int col, boolean[][] Array) {		//Method is used to count the number of live neighbours a cell in an array has.

		int neighbours = 0, i, j;	//Creates the looping integers and the variable to hold the neighbour count.
	    
	    for(i = row - 1; i <= row + 1; i++)		//Loops through the cells around the one that is being checked (and the once that is checked)
		{
			for(j = col - 1; j <= col + 1; j++)
			{
				if(i != row || j != col)		//If the current cell isn't the one being checked then it will carry on (Else nothing happens)
				{								//Most of this class is used to check the outside limits.
												//If the cell being checked is on the edge of the board then this allows it to wrap around to the other side.
					if(i < 0 && j < 0)			//Checks if the current cell is /\ and < the board.
					{
						if(Array[GridSize - 1][GridSize - 1])		//If the cell is true then the neighbour count is added to
						{													//Same for all the checks.
							neighbours++;
						}
					}

					if(i < 0 && (j >= 0 && j < GridSize))	//Checks if the current cell is /\ the board.
					{
						if(Array[GridSize - 1][j])
						{
							neighbours++;
						}
					}

					if((i >= 0 && i < GridSize) && j < 0)	//Checks if the current cell is <.
					{
						if(Array[i][GridSize - 1])
						{
							neighbours++;
						}
					}

					if(i > GridSize && j < 0)
					{
						if(Array[i][GridSize - 1])	//Checks if the current cell is \/ and <.
						{
							neighbours++;
						}
					}

					if(i >= GridSize && j >= GridSize)		//Checks if the current cell is \/ and > the board.
					{
						if(Array[0][0])
						{
							neighbours++;
						}
					}

					if(i >= GridSize && (j >= 0 && j < GridSize))		//Checks if the current cell is \/.
					{
						if(Array[0][j])
						{
							neighbours++;
						}
					}

					if(i < 0 && j >= GridSize)			//Checks if the current cell is /\ and > the board.
					{
						if(Array[GridSize - 1][0])
						{
							neighbours++;
						}
					}

					if((i >= 0 && i < GridSize) && j >= GridSize)		//Checks if the current cell is > the board.
					{
						if(Array[i][0])
						{
							neighbours++;
						}
					}

					if((i >= 0 && i < GridSize) && (j >= 0 && j < GridSize))	//Checks if the current cell is within the board.
					{
						if(Array[i][j])
						{
							neighbours++;
						}
					}
				}
			}
		}
	    return neighbours;	//Returns the neighbour
    }
	
	private static void StoreGrid(boolean[][] Grid)		//Method used to store the the grid iterations
	{
		/*GridStorage.add(Grid);							//Adds the grid to the ArrayList GridStorage
		boolean[][] LookatGrid = GridStorage.get(1);

		for(int i = 0; i < GridSize; i++)
		{
			for(int j = 0; j < GridSize; j++)
			{
				if(!LookatGrid[i][j])
				{
					System.out.print("D ");
				}
				else
				{
					System.out.print("A ");
				}
			}
			System.out.println();
		}
		*/
	}
	
	private static void StoreCount(int[][] CountArray)		//Method used to store the the counts for each iteration
	{
		CountStorage.add(CountArray);						//Adds the counts to the ArrayList CountStorage
		int[][] lookAtGrid = CountStorage.get(1);

		for(int i = 0; i < GridSize; i++)
		{
			for(int j = 0; j < GridSize; j++)
			{
				System.out.print(lookAtGrid[i][j]);
			}
			System.out.println();
		}
	}
    
	private static boolean[][] MakeInitialArray()
	{
		int i, j;			//Creates the looping integers
		boolean[][] Array = new boolean[GridSize][GridSize];	//Creates an array to make in to the initial iteration
		double Randomno;		//Random number used to decide whether or not to make a cell dead or alive.
		
		for(i = 0; i < GridSize; i++)		//Loops through array
		{
			for(j = 0; j < GridSize; j++)
			{
				Randomno = Math.random();	//Assigns a random double to 'Randomno'
				if(Randomno < 0.9)			//If the random double is less than 0.9
				{
					Array[i][j] = false;	//Then the cell is dead
				}
				if(Randomno > 0.9)			//If the double is more than 0.9
				{
					Array[i][j] = true;		//then the cell is alive
				}
			}
		}
		return Array;		//Returns the filled up array.
	}
	
 }