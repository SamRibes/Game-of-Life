public class GameOfLife
{
		public static void main(String[] args) 
		{
			GameOfLifeGUI fgui = new GameOfLifeGUI(); 		//Create the GUI object.
			javax.swing.SwingUtilities.invokeLater(fgui);	//Call the Object.
		}
}
