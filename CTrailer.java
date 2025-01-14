/**
 * Abstract: This is the Trailer class.
 */
public class CTrailer extends CVehicle{
	private int intWheels;
	private int intNumOfMPG;
	/**
	 * Gets how to drive.
	 */
	public void getHowToDrive()
	{
		System.out.println("use another vehicle to pull");
	}
	
	/**
	 * Method: Print
	 */
	public void Print()
	{
		System.out.println("Information for your trailer: ");
		System.out.println("Number of Wheels: " + getIntWheels());
		System.out.println("Miles Per Gallon: " + getIntNumOfMPG());
		
		System.out.print("You ");
		getHowToDrive();
	}
}
