import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import com.microsoft.sqlserver.jdbc.*;	// For connecting to SQL Server
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * Abstract: Final Project
* @author TLA
* @since  12/2/2024
* @version 1.0
 */
public class CVehicleFinal {
	private static Connection m_conAdministrator;
	//define the table, primary key, and column
	public static final float CAR_DAILY_PRICE = 50.0f;
	public static final float MOTORBIKE_DAILY_PRICE = 40.0f;
	public static final float TRAILER_DAILY_PRICE = 75.0f;
	/**
	 * main method: Final Project
	 * @param args
	 */
	public static void main(String[] args) {
		String strPhoneNumber = "";
		String strEmail = "";
		String strName = "";
		String strPickupDate = "";
		int aintRentalDays[];
		aintRentalDays = new int[3];
		int aintCarType[];
		aintCarType = new int[3];
		float asngTotalRental[] = new float[3];
		float sngTotalRentalAll = 0;
		
		String strResponce = "YES";
		int intIndex = 0;
		
		//Database Part 1
        try {
        	
    			// Can we connect to the database?
    			if ( OpenDatabaseConnectionSQLServer( ) == true )
    			{	
					// Yes, load the list
    				LoadListFromDatabase( "TLocations", "intLocationID" , "strLocationName" , "strAddress" , "strCity" , "strState" , "strZip" );
    			}
    			else
    			{
    				// No, warn the user ...
    				System.out.println("Error loading the table");
    			}
    			
    		System.out.println("Process Complete");
        }
            catch 	(Exception e){
            	System.out.println("An I/O error occurred: " + e.getMessage());
        	}
        
        //Get and Validate 
        strName = GetAndValidateName();
		strPhoneNumber = GetAndValidatePhone();
		strEmail = GetAndValidateEmail();
		strPickupDate = GetAndValidatePickup();
		
		//Get the variables for each vehicle and store them in arrays
		do
		{
			aintCarType[intIndex] = GetAndValidateType();
			//Car       = 1
			//Motorbike = 2
			//Trailer   = 3
			aintRentalDays[intIndex] = GetAndValidateRentDays();
			
			if (intIndex < 2)
			{
				System.out.println("Do you want to add another vehicle? (Yes/No): ");
				strResponce = ReadStringFromUser();
			}
			
			intIndex += 1;
		} while (strResponce.toUpperCase().equals("YES") && intIndex < 3);
		
		//Do your primary display
		Display(strName, strPhoneNumber, strEmail, aintCarType);
		
		//OOP
		// Allocate space
		CCar clsCar = new CCar( );
		clsCar.setIntWheels(4);
		clsCar.setIntNumOfMPG(25);
		
		CMotorbike clsMotorbike = new CMotorbike( );
		clsMotorbike.setIntWheels(2);
		clsMotorbike.setIntNumOfMPG(45);
		
		CTrailer clsTrailer = new CTrailer( );
		clsTrailer.setIntWheels(8);
		clsTrailer.setIntNumOfMPG(0);
		
		//Get the values based off of which vehicle
		for(intIndex = 0; intIndex < aintCarType.length; intIndex += 1)
		{
			if (aintCarType[intIndex] == 1)
			{
				clsCar.Print();
				asngTotalRental[intIndex] = CAR_DAILY_PRICE * aintRentalDays[intIndex];
			}
			if (aintCarType[intIndex] == 2)
			{
				clsMotorbike.Print();
				asngTotalRental[intIndex] = MOTORBIKE_DAILY_PRICE * aintRentalDays[intIndex];
			}
			if (aintCarType[intIndex] == 3)
			{
				clsTrailer.Print();
				asngTotalRental[intIndex] = TRAILER_DAILY_PRICE * aintRentalDays[intIndex];
			}
			sngTotalRentalAll += asngTotalRental[intIndex];
			System.out.println("");
		}
		
		//Final Display
		DisplayFinal(asngTotalRental, sngTotalRentalAll);
	}
	

	/** 
	 * 	method name: This will load the list from the table.	
	 */
	public static boolean LoadListFromDatabase( String strTable, String strPrimaryKeyColumn, String strLocationColumn, String strAddressColumn, String strCityColumn, String strStateColumn, String strZipColumn ) {
		
		//set flag to false
		boolean blnResult = false;
		
		try
		{
			String strSelect = "";
			Statement sqlCommand = null;
			ResultSet rstTSource = null;
			int intID = 0;
			String strLocation = "";
			String strAddress = "";
			String strCity = "";
			String strState = "";
			String strZip = "";
		
			// Build the SQL string
			strSelect = "SELECT " + strPrimaryKeyColumn + ", " + strLocationColumn + ", " + strAddressColumn + ", " + strCityColumn + ", " + strStateColumn + ", " + strZipColumn 
						+ " FROM " + strTable
						+ " ORDER BY " + strLocationColumn; 
					
			// Retrieve the all the records	
			sqlCommand = m_conAdministrator.createStatement( );
			rstTSource = sqlCommand.executeQuery( strSelect );
			//give a header 
			System.out.println("Here are the pickup locations - we will call you with a location confirmation");
			// Loop through all the records
			while( rstTSource.next( ) == true )
			{
				// Get ID and Name from current row
				intID = rstTSource.getInt( 1 );
				strLocation = rstTSource.getString( 2 );
				strAddress = rstTSource.getString( 3 );
				strCity = rstTSource.getString( 4 );
				strState = rstTSource.getString( 5 );
				strZip = rstTSource.getString( 6 );
				// Print the list
				System.out.println("ID: " + intID + " Name: " + strLocation + "\t Address: " + strAddress + "\t City: " + strCity + "\t State: " + strState + " Zip: " + strZip);
			}
			// Clean up
			rstTSource.close( );
			sqlCommand.close( );
			// Success
			blnResult = true;
		}
		catch 	(Exception e) {
			System.out.println( "Error loading table" );
			System.out.println( "Error is " + e );
		}
		
		return blnResult;
		}

	/**
	 * OpenDatabaseConnectionSQLServer - get SQL db connection
	 * @return blnResult
	 */
	public static boolean OpenDatabaseConnectionSQLServer( )
	{
		boolean blnResult = false;
		
		try
		{
			SQLServerDataSource sdsTeamsAndPlayers = new SQLServerDataSource( );
			//tg-comment out --sdsTeamsAndPlayers.setServerName( "localhost" ); // localhost or IP or server name
			sdsTeamsAndPlayers.setServerName( "localhost\\SQLExpress" ); // SQL Express version
			sdsTeamsAndPlayers.setPortNumber( 1433 );
			sdsTeamsAndPlayers.setDatabaseName( "dbVehicleRental" );
			
			// Login Type:
			
				// Windows Integrated
				//tg-comment out --sdsTeamsAndPlayers.setIntegratedSecurity( true );
				
				// OR
				
				// SQL Server
			     sdsTeamsAndPlayers.setUser( "sa" );
				 sdsTeamsAndPlayers.setPassword( "9053878" );	// Empty string "" for blank password
			
			// Open a connection to the database
			m_conAdministrator = sdsTeamsAndPlayers.getConnection(  );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			System.out.println( "Cannot connect - error: " + excError );

			// Warn about SQL Server JDBC Drivers
			System.out.println( "Make sure download MS SQL Server JDBC Drivers");
		}
		
		return blnResult;
	}
	
	
	/**
	* Name: CloseDatabaseConnection
	* Abstract: Close the connection to the database
	*/ 
	public static boolean CloseDatabaseConnection( )
	{
		boolean blnResult = false;
		
		try
		{
			// Is there a connection object?
			if( m_conAdministrator != null )
			{
				// Yes, close the connection if not closed already
				if( m_conAdministrator.isClosed( ) == false ) 
				{
					m_conAdministrator.close( );
					
					// Prevent JVM from crashing
					m_conAdministrator = null;
				}
			}
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
				System.out.println( excError );
			}
			
			return blnResult;
		}
	
	
	/**
	* Name: GetAndValidateName
	* Abstract: Validate the name
	*/ 
	public static String GetAndValidateName()
	{
		String strName = "";
		
		System.out.println("Enter your name: ");
		strName = ReadStringFromUser();
		
		return strName;
	}
	
	/**
	* Name: GetAndValidate
	* Abstract: Main Center for the get and validate
	*/ 
	public static String GetAndValidatePhone()
	{
		String strPhone = "";
		
		// Ask user for phone number and loop until correct format is entered
		do
		{
			System.out.print( "Phone number in the format '###-###-####' or '##########': " );
			strPhone = ReadStringFromUser();
		}while( IsValidPhoneNumber( strPhone ) == false );
		
		return strPhone;
	}
	
	/**
	* Name: GetAndValidateEmail
	* Abstract: Main Center for the get and validate
	*/ 
	public static String GetAndValidateEmail()
	{
		String strEmail = "";
		
		// Ask user for email and loop until correct format is entered
		do
		{
			System.out.print( "Email: " );
			strEmail = ReadStringFromUser();
		}while( IsValidEmailAddress( strEmail ) == false );
		
		return strEmail;
	}
	
	/**
	* Name: GetAndValidatePickup
	* Abstract: Main Center for the get and validate
	*/ 
	public static String GetAndValidatePickup()
	{
		String strDate = "";
		
		// Ask user for pick-up date and loop until correct format is entered
		do
		{
			System.out.print( "Pick-up date in the format MM-DD-YYYY or MM/DD/YYYY: " );
			strDate = ReadStringFromUser();
		}while( IsValidDate( strDate ) == false );
		return strDate;
	}
	
	/**
	* Name: GetAndValidate
	* Abstract: Main Center for the get and validate
	*/ 
	public static int GetAndValidateRentDays()
	{
		int intRentDays = 0;
		do
		{
			System.out.print( "Enter the number of days you are renting the vehicle: " );
			intRentDays = ReadIntegerFromUser();
		}while( intRentDays < 0 );
		
		return intRentDays;
		
	}
	
	/**
	* Name: GetAndValidate
	* Abstract: Main Center for the get and validate
	*/ 
	public static int GetAndValidateType()
	{
		int intType = 0;
		do
		{
			System.out.println("Car       (1):");
			System.out.println("Motorbike (2):");
			System.out.println("Trailer   (3):");
			System.out.println("Enter the vehicle you are renting (numbers only): " );
			intType = ReadIntegerFromUser();
		}while( intType < 1 && intType > 3 );
		
		return intType;
		
	}
	
	/**
	* Name: ReadIntegerFromUser
	* Abstract: Reads an integer value given by the user
	* @return intValue
	*/ 
	public static int ReadIntegerFromUser( )
	{

		int intValue = 0;

		try
		{
			String strBuffer = "";	

			// Input stream
			BufferedReader burInput = new BufferedReader( new InputStreamReader( System.in ) ) ;

			// Read a line from the user
			strBuffer = burInput.readLine( );
			
			// Convert from string to integer
			intValue = Integer.parseInt( strBuffer );
		}
		catch( Exception excError )
		{
			System.out.println( excError.toString( ) );
		}
		

		// Return integer value
		return intValue;
	}
	
	/**
	* Name: ReadStringFromUser
	* Abstract: Reads a string given by the user
	* @return strBuffer
	*/ 
	public static String ReadStringFromUser( )
	{			  

		String strBuffer = "";	
		
		try
		{
			
			// Input stream
			BufferedReader burInput = new BufferedReader( new InputStreamReader( System.in ) ) ;

			// Read a line from the user
			strBuffer = burInput.readLine( );
	
		}
		catch( Exception excError )
		{
			System.out.println( excError.toString( ) );
		}
		
		// Return integer value
		return strBuffer;
	}
	
	/**
	 * Method: IsValidPhoneNumber - Check if phone number entered is in correct format
	 * @param strPhoneNumber
	 * Phone number entered by user
	 * @return blnIsValidPhoneNumber
	 */
	public static boolean IsValidPhoneNumber(String strPhoneNumber) {
		boolean blnIsValidPhoneNumber = false;
		
		try
		{
			// Declare variables
			String strStart = "^";
			String strStop = "$";
			String strDash = "\\-";
			String strPattern1 = "";
			String strPattern2 = "";
			
			// String patterns
			// ###-###-####
			strPattern1 = strStart + "\\d{3}" + strDash + "\\d{3}" + strDash + "\\d{4}" + strStop;
			// ##########
			strPattern2 = strStart + "\\d{10}" + strStop;
			
			// Does it match any of the formats?
			if( strPhoneNumber.matches( strPattern1 ) == true || 
				strPhoneNumber.matches( strPattern2 ) == true )
			{
				// Yes
				blnIsValidPhoneNumber = true;
			}
		}
		catch( Exception excError )
		{
			// Display Error Message
			System.out.println( excError );
		}
		// Return result
		return blnIsValidPhoneNumber;
	}
	
	/**
	 * Method: IsValidEmailAddress - Check if email entered is valid
	 * @param strResponse
	 * Email entered by user
	 * @return blnIsValidEmailAddress
	 */
	private static boolean IsValidEmailAddress(String strEmailAddress) {
		boolean blnIsValidEmailAddress = false;
		
		try
		{
			// Declare variables
			String strStart = "^";
			String strStop = "$";
			String strPattern = "";
			
			// Set string pattern
			strPattern = strStart + "[a-zA-Z][a-zA-Z0-9\\.\\-]*" + "@" + "[a-zA-Z][a-zA-Z0-9\\.\\-]*\\.[a-zA-Z]{2,6}" + strStop;
			
			// Does it match?
			if( strEmailAddress.matches( strPattern ) == true )
			{
				// Yes
				blnIsValidEmailAddress = true;
			}
			
		}
		catch( Exception excError )
		{
			// Display Error Message
			System.out.println( excError );
		}
		
		return blnIsValidEmailAddress;
	}
	
	/**
	 * Method: IsValidDate - Check if date entered is valid
	 * @param strResponse
	 * Date entered by user
	 * @return blnIsValidDate
	 */
	private static boolean IsValidDate(String strDate) {
		boolean blnIsValidDate = false;
		
		try
		{
			// Declare variables
			String strStart = "^";
			String strStop = "$";
			String strDash = "\\-";
			String strSlash = "\\/";
			String strPattern1 = "";
			String strPattern2 = "";
			
			// Set string pattern
			// MM-DD-YYYY
			strPattern1 = strStart + "\\d{2}" + strDash + "\\d{2}" + strDash + "\\d{4}" + strStop;
			// MM/DD/YYYY
			strPattern2 = strStart + "\\d{2}" + strSlash + "\\d{2}" + strSlash + "\\d{4}" + strStop;
			
			// Does it match?
			if( strDate.matches( strPattern1 ) == true ||
				strDate.matches( strPattern2 ) == true)
			{
				// Yes
				blnIsValidDate = true;
			}
			
		}
		catch( Exception excError )
		{
			// Display Error Message
			System.out.println( excError );
		}
		return blnIsValidDate;
	}
	
	
	/**
	* Name: Display
	* Abstract: Displays the name, phone, and email for the customer
	*/ 
	private static void Display(String strName, String strPhoneNumber, String strEmail, int[] aintCarType)
	{
		System.out.println("------------------------------");
		System.out.println("Report:-----------------------");
		System.out.println("------------------------------");
		System.out.print("Name: ");
		System.out.printf(strName + "\n");
		System.out.print("Phone: ");
		System.out.printf(strPhoneNumber + "\n");
		System.out.print("Email: ");
		System.out.printf(strEmail + "\n");
		System.out.print("\n");
	}
	
	/**
	* Name: Display Final
	* Abstract: Displays the name, phone, and email for the customer
	*/ 
	private static void DisplayFinal(float[] asngTotalRental, float sngTotalRentalAll)
	{
		int intIndex = 0;
		//Print individual values for each vehicle
		for (intIndex = 0; intIndex < asngTotalRental.length; intIndex += 1)
		{
			if (asngTotalRental[intIndex] != 0)
			{
				System.out.printf("Vehicle %d rental total:\t $%.2f\n", intIndex + 1, asngTotalRental[intIndex]);
			}
		}
		//print the total rental amount
		System.out.printf("Total Rental Amount:\t $%.2f\n", sngTotalRentalAll);
	}
}

