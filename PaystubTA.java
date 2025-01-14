import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author TLA
 * @version 1.0
 * Class: Java 1
 * Abstract: Test 2 Practical
*/
public class PaystubTA 
{
	public static final double dblSOCIALTAX = 0.062;
	public static final double dblMEDICARETAX = 0.0145;
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String strProgramEnd = "No";
		String strName = "";
		char chrMarriageStatus = 0;
		float sngHoursWorked = 0;
		float sngPayRate = 0;
		int intWithholdings = 0;
		double dblGrossPay = 0;
		//adblValues[0] = Medicare
		//adblValues[1] = Social Security
		//adblValues[2] = FICA
		//adblValues[3] = Gross Pay
		//adblValues[4] = Fed Tax
		//adblValues[5] = Net Pay
		double adblValues[] = new double[6];
		//adblValues[0] = Medicare
		//adblValues[1] = Social Security
		//adblValues[2] = FICA
		double adblValues2[] = new double[3];
		double dblFedTax = 0;
		double dblNetPay = 0;
		
		System.out.println("Paystub Calculator");
		System.out.println("-----------------------------------------------");
		System.out.println("");
		do 
		{
			//Get Input
			strName = GetName();
			if(strName.toUpperCase().equals("QUIT")) { break; }
			sngPayRate = GetHourlyWage();
			sngHoursWorked = GetHoursWorked();
			intWithholdings = GetWithholdings();
			chrMarriageStatus = GetMaritalStatus();
			
			//Do Calculations
			dblGrossPay = calculatePay(sngHoursWorked, sngPayRate);
			CalculateFICA(adblValues2, dblGrossPay);
			dblFedTax = CalculateFedTax(dblGrossPay, intWithholdings, chrMarriageStatus);
			dblNetPay = CalculateNetPay(dblGrossPay, adblValues2[2], dblFedTax);
			
			
			//Move output variables into array to store totals
			adblValues[0] += adblValues2[0];
			adblValues[1] += adblValues2[1];
			adblValues[2] += adblValues2[2];
			adblValues[3] += dblGrossPay;
			adblValues[4] += dblFedTax;
			adblValues[5] += dblNetPay;
			
			//Display Output
			DisplayOutput(strName, dblGrossPay, adblValues2, dblFedTax, dblNetPay);
			
		} while (strProgramEnd.equals("No"));
		
		//Once done, display total output
		DisplayTotalOutputs(adblValues);
	}
	
	//Display Daily Totals
	
	
	
	public static String GetName()
	{
		String strName = "";
		
		System.out.println("Employee Name: (type 'Quit' to end the program)");
		strName = ReadStringFromUser();
		
		return strName;
	}
	
	/** GetHourlyWage
	 * @return strName
	 * Abstract: Get the name of the employee
	 */
	public static float GetHourlyWage()
	{
		float sngPayRate = 0;
		
		do
		{
			System.out.println("Hourly Wage: ");
			sngPayRate = ReadFloatFromUser();
			
			if(sngPayRate <= 0)
			{
				System.out.println("Please enter a positive value.");
			}
		} while (sngPayRate <= 0);
		
		return sngPayRate;
	}
		
	
	
	/** GetHoursWorked
	 * @return strName
	 * Abstract: Get the name of the employee
	 */
	public static float GetHoursWorked()
	{
		float sngHoursWorked = 0;
		
		do
		{
			System.out.println("Hours Worked: ");
			sngHoursWorked = ReadFloatFromUser();
			
			if(sngHoursWorked <= 0)
			{
				System.out.println("Please enter a positive value.");
			}
		} while (sngHoursWorked <= 0);

		return sngHoursWorked;
	}
	
	
	
	/** GetWithholdings
	 * @return strName
	 * Abstract: Get the name of the employee
	 */
	public static int GetWithholdings()
	{
		int intWithholdings = 0;
		do
		{
			System.out.println("Withholding Exemptions: ");
			intWithholdings = ReadIntegerFromUser();
			
			if(intWithholdings < 0 || intWithholdings > 10)
			{
				System.out.println("Please enter a positive value between 0-10.");
			}
		} while(intWithholdings < 0 || intWithholdings > 10);
		
		return intWithholdings;
	}
	
	
	
	/** GetMaritalStatus
	 * @return strName
	 * Abstract: Get the name of the employee
	 */
	public static char GetMaritalStatus()
	{
		char chrMaritalStatus = 0;
		do
		{
			System.out.println("Marital Status (S = Single, M = Married): ");
			chrMaritalStatus = ReadLetterFromUser();
			chrMaritalStatus = Character.toUpperCase(chrMaritalStatus);
			if(chrMaritalStatus != 'S' && chrMaritalStatus != 'M')
			{
				System.out.println("You must enter an 'S' or 'M'.");
			}
		} while(chrMaritalStatus != 'S' && chrMaritalStatus != 'M');
		
		return chrMaritalStatus;
	}
	
	
	
	/** calculatePay
	 * @return sngGrossPay
	 */
	public static double calculatePay(float sngHoursWorked, float sngPayRate)
	{
		double dblGrossPay = 0;
		
		if (sngHoursWorked <= 40)
		{
			dblGrossPay = sngHoursWorked * sngPayRate;
		}
		else
		{
			// Takes the first 40 hours and calculates the pay
			dblGrossPay = 40 * sngPayRate;
			// Takes the remaining hours over 40 and multiplies them by the overtime payrate.	
			dblGrossPay += (sngHoursWorked - 40) * (sngPayRate * 1.5);
		}
		return dblGrossPay;
	}
	
	
	
	/** CalculateFICA
	 * Abstract: Calculate the social security tax and the medicare tax and return their sum. 
	 * @return sngFICATAX
	 */
	public static void CalculateFICA(double adblValues2[], double dblGrossPay)
	{
		adblValues2[0] = dblGrossPay * dblMEDICARETAX;
		
		adblValues2[1] = dblGrossPay * dblSOCIALTAX;
		
		adblValues2[2] = adblValues2[0] + adblValues2[1];
		
	}
	
	
	
	/** CalculateFedIncomeTax
	 * Abstract: Calculate the federal income tax based off of the Adjusted Gross Income
	 */
	public static double CalculateFedTax(double dblGrossPay, int intWithholdings, char chrMaritalStatus)
	{
		double dblFedTax = 0;
		double dblAGI = 0;
		double dblIncomeTax = 0;
		
		//Adjusted Gross Income
		dblAGI = dblGrossPay - (55.77 * intWithholdings);
		
		// Single
		if(chrMaritalStatus == 'S')
		{
			if(dblAGI > 5000)
			{
				dblFedTax = 845 + (.25 * (dblAGI - 5000));
			}
			
			if(dblAGI > 2501 && dblAGI < 5000)
			{
				dblFedTax = 345 + (.2 * (dblAGI - 2500));
			}
			
			if(dblAGI > 501 && dblAGI < 2500.99)
			{
				dblFedTax = 45 + (.15 * (dblAGI - 500));
			}
			
			if(dblAGI > 51 && dblAGI < 500.99)
			{
				dblFedTax = .1 * (dblAGI - 51);
			}
		}
		
		//Married
		if(chrMaritalStatus == 'M')
		{
			if(dblAGI > 5000)
			{
				dblFedTax = 600.5 + (.2 * (dblAGI - 5000));
			}
			
			if(dblAGI > 2501 && dblAGI < 5000)
			{
				dblFedTax = 225.5 + (.15 * (dblAGI - 2500));
			}
			
			if(dblAGI > 501 && dblAGI < 2500.99)
			{
				dblFedTax = 22.5 + (.1 * (dblAGI - 500));
			}
			
			if(dblAGI > 51 && dblAGI < 500.99)
			{
				dblFedTax = .05 * (dblAGI - 51);
			}
		}
		return dblFedTax;
	}
	
	
	/** CalculateNetPay
	 * Abstract: Calculate the net pay
	 */
	public static double CalculateNetPay(double dblGrossPay, double dblFICATax, double dblFedTax)
	{
		double dblNetpay = 0;
		
		dblNetpay = dblGrossPay - dblFICATax - dblFedTax;
		
		return dblNetpay;
	}
	
	
	
	/** DisplayOutput
	 * Abstract: Display all of the output variables
	 */
	public static void DisplayOutput(String strName, double dblGrossPay, double adblValues2[], double dblFedTax, double dblNetPay)
	{
		System.out.printf("Paycheck for \t%s\n", strName);
		System.out.printf("Gross Pay: \t\t$%8.2f\n", dblGrossPay);
		System.out.printf("Medicare Tax: \t\t$%8.2f\n", adblValues2[0]);
		System.out.printf("Social Security Tax: \t$%8.2f\n", adblValues2[1]);
		System.out.printf("FICA Tax: \t\t$%8.2f\n", adblValues2[2]);
		System.out.printf("Federal Tax: \t\t$%8.2f\n", dblFedTax);
		System.out.printf("Net Pay: \t\t$%8.2f\n", dblNetPay);
		System.out.println("----------------------------------------");
		System.out.println();
	}
	
	
	
	/** DisplayTotalOutput
	 * Abstract: Display all of the output variables
	 */
	public static void DisplayTotalOutputs(double adblValues[])
	{
		//adblValues[0] = Medicare
		//adblValues[1] = Social Security
		//adblValues[2] = FICA
		//adblValues[3] = Gross Pay
		//adblValues[4] = Fed Tax
		//adblValues[5] = Net Pay
		System.out.println("Grand Paycheck Totals ------------------------");
		System.out.println("----------------------------------------------");
		System.out.printf("Total Gross Pay: \t\t$%8.2f\n", adblValues[3]);
		System.out.printf("Total Medicare Tax: \t\t$%8.2f\n", adblValues[0]);
		System.out.printf("Total Social Security Tax: \t$%8.2f\n", adblValues[1]);
		System.out.printf("Total FICA Tax: \t\t$%8.2f\n", adblValues[2]);
		System.out.printf("Total Federal Tax: \t\t$%8.2f\n", adblValues[4]);
		System.out.printf("Total Net Pay: \t\t\t$%8.2f\n", adblValues[5]);
		System.out.println("----------------------------------------------");
		System.out.println("----------------------------------------------");
		System.out.println();
		
	}
	
	
	
	/** ReadFloatFromUser
	 * @return sngValue
	 */
	public static float ReadFloatFromUser( )
	{
	
		float sngValue = 0;
	
		try
		{
			String strBuffer = "";	
	
			// Input stream
			BufferedReader burInput = new BufferedReader( new InputStreamReader( System.in ) ) ;
	
			// Read a line from the user
			strBuffer = burInput.readLine( );
			
			// Convert from string to float
			sngValue = Float.parseFloat( strBuffer );
		}
		catch( Exception excError )
		{
			System.out.println( excError.toString( ) );
		}
		
	
		// Return float value
		return sngValue;
	}
	
	
	/** ReadStringFromUser
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
	
	
	/** ReadIntegerFromUser
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
	 * Method ReadLetterFromUser
	 * Abstract: Read a letter from the user.
	 */
	public static char ReadLetterFromUser( ) 
	{
		char chrUserLetter = 0;

		try
		{
			String strBuffer = "";
			
			// Input stream
			BufferedReader burInput = new BufferedReader( new InputStreamReader( System.in ) ) ;

			// Read a line
			strBuffer = burInput.readLine( );
			
			// Get the first Letter
			chrUserLetter = strBuffer.charAt( 0 );
			
		}
		catch( Exception excError )
		{
			System.out.println( excError.toString( ) );
		}
		
		// Return letter
		return chrUserLetter;
	}
	
}



	

	
