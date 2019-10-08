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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

public class GameofLifeControl extends JPanel implements ActionListener 	//Inherit functionality from 'JPanel' and 'JActionListener'
{
    private static final long serialVersionUID = 1862962349L;				//Unique ID
    //Create the * variables.
	private JButton startbutton, endbutton, exitbutton, pausebutton, resetbutton;		//Start, End, Exit and Pause buttons.
	private JLabel IterationLabel, IterationCaption;						//Number of iterations label and number of iterations actual(displays the actual number).
	private Timer timer;													//Timer(used for spacing out the iterations).
	private static int GridSize = 100;										//Grid Size.
	long Iterations;														//Keeps the count for the number of iterations.
	boolean GameArray[][];													//The array that the board takes place on.
	static ArrayList<boolean[][]> GridStorage = new ArrayList<boolean[][]>();	//Storage array for the Grid.
	static ArrayList<int[][]> CountStorage = new ArrayList<int[][]>();		//Storage array for the Counts.													
	static final int TickTime = 100;										//The time in milliseconds between each iteration.
    public GameofLifeControl() 
    {
    	//Caption for the Start button.
        startbutton = new JButton("Start Game");
        startbutton.setVerticalTextPosition(AbstractButton.CENTER);			//Centre the text vertically
        startbutton.setHorizontalTextPosition(AbstractButton.CENTER);		//Centre the text horizontally
        startbutton.setMnemonic(KeyEvent.VK_S);			//Short cut key of 'S'
        startbutton.setActionCommand("Start");			//Create the event/action 'Start' when clicked
        
        //Caption for the Pause button.
        pausebutton = new JButton("Pause Game");
        pausebutton.setVerticalTextPosition(AbstractButton.CENTER);			//Centre the text vertically
        pausebutton.setHorizontalTextPosition(AbstractButton.CENTER);		//Centre the text horizontally
        pausebutton.setMnemonic(KeyEvent.VK_P);			//Short cut key of 'S'
        pausebutton.setActionCommand("Pause");			//Create the event/action 'Start' when clicked
        pausebutton.setEnabled(false);

        //Caption for the Reset button.
        resetbutton = new JButton("Reset Game");
        resetbutton.setVerticalTextPosition(AbstractButton.CENTER);      //Centre the text vertically
        resetbutton.setHorizontalTextPosition(AbstractButton.CENTER);    //Centre the text horizontally
        resetbutton.setMnemonic(KeyEvent.VK_R);       	//Short cut key of 'R'
        resetbutton.setActionCommand("Reset");        	//Create the event/action 'Reset' when clicked
        resetbutton.setEnabled(false);      			//Initially the Reset button is disabled
        
        //Caption for the Stop button.
        endbutton = new JButton("Stop Game");
        endbutton.setVerticalTextPosition(AbstractButton.CENTER);        //Centre the text vertically
        endbutton.setHorizontalTextPosition(AbstractButton.CENTER);      //Centre the text horizontally
        endbutton.setMnemonic(KeyEvent.VK_T);       	//Short cut key of 'T'
        endbutton.setActionCommand("Stop");        		//Create the event/action 'Stop' when clicked
        endbutton.setEnabled(false);      				//Initially the stop button is disabled
        
    	//Caption for the Exit button.
        exitbutton = new JButton("Exit Game");
        exitbutton.setVerticalTextPosition(AbstractButton.CENTER);			//Centre the text vertically
        exitbutton.setHorizontalTextPosition(AbstractButton.CENTER);		//Centre the text horizontally
        exitbutton.setMnemonic(KeyEvent.VK_X);       	//Short cut key of 'X'
        exitbutton.setActionCommand("Exit");        	//Create the event/action 'Exit' when clicked
        
        //Listen for actions from the buttons.
        startbutton.addActionListener(this);	
        endbutton.addActionListener(this);
        resetbutton.addActionListener(this);
        exitbutton.addActionListener(this);
        pausebutton.addActionListener(this);
        
        //Text displayed in tooltips for the buttons
        startbutton.setToolTipText("Click this button to start the game");
        endbutton.setToolTipText("Click this button to stop the game");
        resetbutton.setToolTipText("Click this button to reset the game");
        exitbutton.setToolTipText("Click here to exit the game");
        pausebutton.setToolTipText("Click here to pause the game");
        
        //Create the labels
        IterationCaption = new JLabel("The number of iterations so far is: ");
        IterationLabel = new JLabel("(The number of iterations goes here)");
        IterationLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));	//Actual iteration label has a border

        //Add the Components to the Form
        add(startbutton);
        add(pausebutton);
        add(resetbutton);
        add(endbutton);
        add(exitbutton);
        add(IterationCaption);
        add(IterationLabel);
        
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
            startbutton.setEnabled(false);			//Disables the 'Start' button.
            endbutton.setEnabled(true);				//Enable the 'Stop' button
            resetbutton.setEnabled(true);			//Enable the 'Reset' button
            pausebutton.setEnabled(true);			//Enable the 'Pause' button
            timer.start();							//Start the 'Timer'
        }
    	
    	//Check for the 'Pause' event
    	if (e.getActionCommand().equals("Pause")) 
        {
        	System.out.println("Pause pressed");	//Logs the button press
            startbutton.setEnabled(true);			//Enable the 'Start' button
            endbutton.setEnabled(true);				//Enable the 'Stop' button
            pausebutton.setEnabled(false);			//Disables the 'Pause' button.
            timer.stop();							//Start the 'Timer'
        }
    	
    	//Check for the 'Reset' event
    	if (e.getActionCommand().equals("Reset")) 
        {
        	System.out.println("Reset pressed");	//Logs the button press
            startbutton.setEnabled(false);			//Disables the 'Start' button
            pausebutton.setEnabled(true);			//Disables the 'Pause' button.
            endbutton.setEnabled(true);				//Enable the 'Stop' button
            Iterations = 0;							//Reset the iteration counter
            repaint();								//Redraws the window
            timer.start();							//Start the 'Timer'
        }
    	
    	//Check for the 'Stop' event
    	if (e.getActionCommand().equals("Stop"))
        {
        	System.out.println("Stop pressed");		//Logs the button press
            startbutton.setEnabled(true);			//Enable the 'Start' button
            endbutton.setEnabled(false);			//Disables the 'Stop' button
            pausebutton.setEnabled(false);			//Disables the 'Pause' button.
            timer.stop();							//Stop the 'Timer'
            Iterations = 0;							//Reset the iteration counter
            IterationLabel.setText("Iterations shown here");	//Reset the text for the display label
        }
    	
    	//Check for the 'Timer' event
    	if (e.getActionCommand().equals("Timer"))
        {
    		System.out.println("Timer");	//Logs the action
    		Iterations++;					//Adds 1 to the iteration count
        	IterationLabel.setText(String.valueOf(Iterations));		//Sets the label value to the iteration count
			try {
				PrintToFile(GridStorage.get((int) Iterations));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
    	int i, j;		//Creates the looping integers
		Color SqaurecolourDead = Color.RED;		//Sets the colour for death as red.
		Color SqaureColourAlive = Color.GREEN;	//Sets the colour for alive as green.
    	super.paint(g);							//Sets up the painter.
    	
    	if (Iterations == 1)					//If it is on the first iteration (initial random) array
    	{
    		GameArray = MakeInitialArray();		//then it will call the MakeInitialArray method to create the random array
    		for(i = 0; i < GridSize; i++)		//Loops through the array
    		{
    			for(j = 0; j < GridSize; j++)
    			{
    				
    				if(GameArray[i][j] == false)		//If the cell is false then the rectangle is red.
    				{
    					g.setColor(SqaurecolourDead);	//Sets the colour.
    					g.fillRect(5+4*i,50+4*j,4,4);	//Draws the rectangle based on the cell number (pushes it down by 100 to avoid going over the buttons and to the right by 4 to stay off the edge)
    				}
    				
    				if(GameArray[i][j] == true)			//If the cell is true then the rectangle is green.
    				{
    					g.setColor(SqaureColourAlive);	//Sets the colour.
    					g.fillRect(5+4*i,50+4*j,4,4);	//Draws the rectangle based on the cell number (pushes it down by 100 to avoid going over the buttons and to the right by 4 to stay off the edge)
    				}
    				
    			}
    		}
    	}
    	
    	if(Iterations > 1)							//If it's past the initial array
    	{
    		GameArray = ProgressArray(GameArray);	//then it calls the ProgressArray method to create the next iteration of the array.
			for(i = 0; i < GridSize; i++)			//Loops through the array
			{
				for(j = 0; j < GridSize; j++)
				{
					if(GameArray[i][j] == false)
    				{
    					g.setColor(SqaurecolourDead);	//Sets the colour.
    					g.fillRect(5+4*i,50+4*j,4,4);	//Draws the rectangle based on the cell number (pushes it down by 100 to avoid going over the buttons and to the right by 4 to stay off the edge)
    				}
    				
					if(GameArray[i][j] == true)
    				{
    					g.setColor(SqaureColourAlive);	//Sets the colour.
    					g.fillRect(5+4*i,50+4*j,4,4);	//Draws the rectangle based on the cell number (pushes it down by 100 to avoid going over the buttons and to the right by 4 to stay off the edge)
    				}
				
				}
			}
    	}
    	StoreGrid(GameArray);  //Stores the finished game board in StoreGrid ArrayList
    }
    
    static public void PrintToFile(boolean GridToWrite[][]) throws IOException
    {
    	int i, j;
    	FileWriter writehandle = new FileWriter("C:\\Users\\Sam\\Desktop\\Grid.txt");
    	BufferedWriter bw = new BufferedWriter(writehandle);
    	bw.newLine();
    	for(i = 0; i < GridSize; i++)
    	{
    		for(j = 0; j < GridSize; j++)
    		{
    			if(GridToWrite[i][j] == false)
    			{
    				bw.write("D ");
    			}
    			if(GridToWrite[i][j] == true)
    			{
    				bw.write("A ");
    			}
    		}
    		bw.newLine();
    	}
    	bw.close();
    	writehandle.close();
    }
    
	static public boolean[][] ProgressArray(boolean OldArray[][])		//Method is used to create the next iteration of an already existing game board.
	{
		int i,j, neighbourCount;		//Creates the looping integers and the variable to hold the neighbour count.
		int CountArray[][] = new int[GridSize][GridSize];	//Creates the count array to be stored in the CountStorage ArrayList.
		boolean NewArray[][] = new boolean[(int) GridSize][(int) GridSize];	//Creates the new array to be returned and used as the next iteration.
		
		for(i = 0; i < GridSize; i++)		//Loops through array
		{
			for(j = 0; j < GridSize; j++)
			{
				neighbourCount = neighbourCount(i, j, OldArray);	//Uses the neighbourCount Method to check the number of neighbours for each cell.
				
				CountArray[i][j] = neighbourCount;				//Adds the neighbourCount to the CountStorage ArrayList
				
				if(OldArray[i][j] == true)						//If the cell is alive
				{
					if(neighbourCount == 2 || neighbourCount == 3)//And it has 2 or 3 neighbours
					{
						NewArray[i][j] = true;					//Then the cell stays alive
					}
					else
					{
						NewArray[i][j] = false;					//Else it dies
					}
				}
				
				if(OldArray[i][j] == false)						//If the cell is dead
				{
					if(neighbourCount == 3)						//And it has 3 neighbours
					{
						NewArray[i][j] = true;					//Then the cell comes to life
					}
					else
					{
						NewArray[i][j] = false;					//Else it stays dead
					}
				}
			}
		}
		StoreCount(CountArray);	//Stores the neighbourCounts to the CountStorage ArrayList
		return NewArray;		//Returns the filled out and progressed array
	}
	
	static public int neighbourCount(int row, int col, boolean Array[][]) {		//Method is used to count the number of live neighbours a cell in an array has.

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
						if(Array[GridSize - 1][GridSize - 1] == true)		//If the cell is true then the neighbour count is added to
						{													//Same for all the checks.
							neighbours++;
						}
					}
					
					if(i < 0 && (j >= 0 && j < GridSize))	//Checks if the current cell is /\ the board.
					{
						if(Array[GridSize - 1][j] == true)
						{
							neighbours++;
						}
					}
					
					if((i >= 0 && i < GridSize) && j < 0)	//Checks if the current cell is <.
					{
						if(Array[i][GridSize - 1] == true)
						{
							neighbours++;
						}
					}
					
					if(i > GridSize && j < 0)
					{
						if(Array[i][GridSize - 1] == true)	//Checks if the current cell is \/ and <.
						{
							neighbours++;
						}
					}
					
					if(i >= GridSize && j >= GridSize)		//Checks if the current cell is \/ and > the board.
					{
						if(Array[0][0] == true)
						{
							neighbours++;
						}
					}
					
					if(i >= GridSize && (j >= 0 && j < GridSize))		//Checks if the current cell is \/.
					{
						if(Array[0][j] == true)
						{
							neighbours++;
						}
					}
					
					if(i < 0 && j >= GridSize)			//Checks if the current cell is /\ and > the board.
					{
						if(Array[GridSize - 1][0] == true)
						{
							neighbours++;
						}
					}
					
					if((i >= 0 && i < GridSize) && j >= GridSize)		//Checks if the current cell is > the board.
					{
						if(Array[i][0] == true)
						{
							neighbours++;
						}
					}
					
					if((i >= 0 && i < GridSize) && (j >= 0 && j < GridSize))	//Checks if the current cell is within the board.
					{
						if(Array[i][j] == true)
						{
							neighbours++;
						}
					}
				}
			}
		}
	    return neighbours;	//Returns the neighbour
    }
	
	static public void StoreGrid(boolean Grid[][])		//Method used to store the the grid iterations
	{
		GridStorage.add(Grid);							//Adds the grid to the ArrayList GridStorage
		
		/*boolean LookatGrid[][] = GridStorage.get(1);
		
		for(int i = 0; i < GridSize; i++)
		{
			for(int j = 0; j < GridSize; j++)
			{
				if(LookatGrid[i][j] == false)
				{
					System.out.print("D ");
				}
				if(LookatGrid[i][j] == true)
				{
					System.out.print("A ");
				}
			}
			System.out.println();
		}*/
		
	}
	
	static public void StoreCount(int CountArray[][])		//Method used to store the the counts for each iteration
	{
		CountStorage.add(CountArray);						//Adds the counts to the ArrayList CountStorage
		/*boolean LookatGrid[][] = CountStorage.get(1);
		
		for(int i = 0; i < GridSize; i++)
		{
			for(int j = 0; j < GridSize; j++)
			{
				System.out.print(LookatGrid[i][j]);
			}
			System.out.println();
		}*/
	}
    
	static public boolean[][] MakeInitialArray()
	{
		int i, j;			//Creates the looping integers
		boolean Array[][] = new boolean[(int) GridSize][(int) GridSize];	//Creates an array to make in to the initial iteration
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