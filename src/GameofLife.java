public class GameofLife 
{
		public static void main(String[] args) 
		{
			GameofLifeGUI fgui = new GameofLifeGUI(); 		//Create the GUI object.
			javax.swing.SwingUtilities.invokeLater(fgui);	//Call the Object.
		}
}
