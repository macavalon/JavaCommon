package Common;

import java.io.IOException;
import java.util.Date;

public class reporter
{
    public reporter(String filename)
    {
        try {
        	if(!FileF.Exists(filename))
    		{
    			FileF.Create(filename);
    		
    		}
			reportFile = new StreamWriter(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @SuppressWarnings("deprecation")
	public void report(String message, Boolean log)
    {
        Date now = new Date();
        
        StringBuilder finalMsg = new StringBuilder();
        
        finalMsg.append(now.toLocaleString());
        finalMsg.append(" : ");
        finalMsg.append(type);
        finalMsg.append(" : ");
        finalMsg.append(username);
        finalMsg.append(" : round ");
        finalMsg.append(runNumber);
        finalMsg.append(" : ");
        finalMsg.append(message);
        
        Console.WriteLine(finalMsg.toString());

        if (log)
        {
            reportFile.WriteLine(finalMsg.toString());
            reportFile.Flush();
        }
        finalMsg = null;
    }

    public void setRunNumber(int runno)
    {
        runNumber = runno;
    }

    public void setType(String Type)
    {
        type = Type;
    }

    public void setUsername(String UserName)
    {
        username = UserName;
    }

    String username;
    int runNumber;
    StreamWriter reportFile;
    String type;
}

