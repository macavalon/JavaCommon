package Common;

public class Convert {

	public static Long ToInt64(String value)
	{
		Long number = Long.parseLong(value);
		
		return number;
		
	}
	
	public static int ToInt32(String value)
	{
		int number = Integer.parseInt(value);
		
		return number;
		
	}
	
	public static double ToDouble(String value)
	{
		double number = Double.parseDouble(value);
		
		return number;
		
	}
}
