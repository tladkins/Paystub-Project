/**
 * Abstract: This is the car class.
 */
public class CCar extends CVehicle {
	private int intWheels;
	private int intNumOfMPG;
	
	/**
	 * Gets how to drive.
	 */
	public void getHowToDrive()
	{
		System.out.println("Steering Wheel");
	}
	
	/**
	 * Method: Print
	 */
	public void Print()
	{
		System.out.println("Information for your car: ");
		System.out.println("Number of Wheels: " + getIntWheels());
		System.out.println("Miles Per Gallon: " + getIntNumOfMPG());
		System.out.print("You drive with a ");
		getHowToDrive();
	}
}
