/**
 * Abstract: This is the vehicle class.
 */
public class CVehicle {

	private int intWheels = 0;
	private int intNumOfMPG = 0;
	
	/**
	 * @return the intWheels
	 */
	public int getIntWheels() {
		return intWheels;
	}


	/**
	 * @param intWheels the intWheels to set
	 */
	public void setIntWheels(int intWheels2) {
		intWheels = intWheels2;
	}


	/**
	 * @return the intNumOfMPG
	 */
	public int getIntNumOfMPG() {
		return intNumOfMPG;
	}


	/**
	 * @param intNumOfMPG the intNumOfMPG to set
	 */
	public void setIntNumOfMPG(int intNumOfMPG2) {
		intNumOfMPG = intNumOfMPG2;
	}
	
	/**
	 * Gets how to drive.
	 */
	public void getHowToDrive()
	{
		System.out.println("Anything!");
	}
	
}
