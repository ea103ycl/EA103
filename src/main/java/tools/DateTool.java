package tools;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateTool
{

	public static String timestamp2StringMin(Timestamp ts)
	{

		DateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
		String time = sdf.format(ts);
		return time;
	}

	public static String timestamp2StringSec(Timestamp ts)
	{

		DateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String time = sdf.format(ts);
		return time;
	}

	public static String timestamp2String4Ecpay(Timestamp ts)
	{

		DateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
		String time = sdf.format(ts);
		return time;
	}

}
