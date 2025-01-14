/**
 * Abstract: This is the motorbike class.
 */
public class CMotorbike extends CVehicle{
	private int intWheels;
	private int intNumOfMPG;
	/**
	 * Gets how to drive.
	 */
	public void getHowToDrive()
	{
		System.out.println("Handle Bars");
	}
	
	/**
	 * Method: Print
	 */
	public void Print()
	{
		System.out.println("Information for your motorbike: ");
		System.out.println("Number of Wheels: " + getIntWheels());
		System.out.println("Miles Per Gallon: " + getIntNumOfMPG());
		System.out.print("You drive with the ");
		getHowToDrive();
	}
}
