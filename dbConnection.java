package Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.json.JSONArray;
import org.json.JSONObject;

import Common.reporter;


public class dbConnection implements Runnable {

	String url = "";
	String username = "";
	String password = "";
	String dbName = "";
	reporter mReporter;
	Boolean live;

	DataSource dataSource;
	
	Boolean shuttingDown;
	Boolean shutdownFinished;
	
	
	public void ShutDown()
    {
    	mReporter.report("stopping database!",true);
    	
    	try {
			connection.close();
			mReporter.report("database stopped!",true);
			shutdownFinished = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mReporter.report("failed to stop database!",true);
		}
    	
    	
    }
    
    public Boolean ShutDownFinished()
    {
    	return shutdownFinished;
    }
	
	public dbConnection(Boolean _live,reporter _mReporter)
	{
		mReporter = _mReporter;
		shutdownFinished = false;
		
		connection = null;
		connected = false;
		live = _live;
		if(!live)
		{
			mReporter.report("test database",true);
			url = "jdbc:mysql://localhost:3306/"+dbName;
			username = "";
			password = "" ;
		}
		else
		{
			mReporter.report("live database",true);
			url = "jdbc:mysql://localhost:3306/"+dbName;
			username = "";
			password = "";
		}
		
		System.out.println("Setting up data source.");
		dataSource = null;
		dataSource = setupDataSource();
		System.out.println("Done.");
		
	}
	
	public  DataSource setupDataSource() 
	{
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername(username);
        ds.setPassword(password);
        
        ds.setUrl(url);
        return ds;
    }
	
	Connection connection;
	Boolean connected;
	
	public Boolean connectDb()
	{
		mReporter.report("connecting to database!",true);
		
		connected = false;
		connection = null;
		try 
		{
			connection = DriverManager.getConnection(url, username, password);

			mReporter.report(" database connected",true);
		    connected = true;
		} catch (SQLException e) {
			mReporter.report(" database failed to connect",true);
		    throw new IllegalStateException("Cannot connect the database!", e);
		    
		} 
		
		return connected;
	}
	
	
    // Functionality : Retrieve messages from table, not older than 30 days
    //
    // this assumes a table in you database names "messages"
    // this assumes a column in table named "msg_date_sent"
    // this assumes a column in table named "msg_content"
    
	public Boolean queryGetMessagesLast30Days(ArrayList<String> messages)
	{
		if(dataSource==null)
		{
			return false;
		}
		
		Boolean gotMessages = false;
		
		Statement stmt = null;
		ResultSet rs = null;
		Connection connection = null;
		
		String msgIds = "";
		
		Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -30);
        Date cutOffTweetDate = c.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reportDate = df.format(cutOffTweetDate);
        
        
		String query = 	" SELECT * " +
        			   	" FROM " + dbName + "messages" + 
        			   	" WHERE msg_date_sent > '" + reportDate + "'";
		
		try {
			connection = dataSource.getConnection();
	        stmt = connection.createStatement();
	        rs = stmt.executeQuery(query);

	        while (rs.next()) {
	        	String message = rs.getString("msg_content");

				messages.add(message);
							
	        }
	        
	        mReporter.report("Loaded from db " + messages.size() + " messages",true);
	        
	    } catch (SQLException e ) {
	    	e.printStackTrace();
	    } finally {
	    	if (stmt != null) { 
	        	try {
	        		stmt.close();
	        		stmt = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
	        if (connection != null) { 
	        	try {
	        		connection.close();
	        		connection = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
	        if(rs!=null)
	        {
	        	try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        }
	    }
	    
	    if(messages.size()!=0)
	    {
	    	gotMessages = true;
	    }
	    
	    return gotMessages;
	
	}
	
    // Functionality : Retrieve messages from table, based on list of unique message ids
    //                 with results return as JSONArray
    //
    // this assumes a table in you database names "messages"
    // this assumes a column in table named "msg_date_sent"
    // this assumes a column in table named "msg_content"
    // this assumes a column in table named "msg_id"
    
	public void queryGetMessagesById(ArrayList<String> msgIdList, JSONArray messageArray)
	{
		if(dataSource==null)
		{
			return;
		}
		
		Statement stmt = null;
		ResultSet rs = null;
		Connection connection = null;
		
		String msgIds = "";
		
		for (String msgId : msgIdList)
		{
			if(msgIds=="")
			{
				msgIds = "'" + msgId + "'";
			}
			else
			{
				msgIds = msgIds + "," + "'" + msgId + "'";
			}
			
		}
		
		String query = 	" SELECT * " +
        			   	" FROM " + dbName + ".messages " +
        			   	" WHERE msg_id IN ("+ msgIds +
        			   	") ORDER BY msg_date_sent DESC";
		
		try {
			connection = dataSource.getConnection();
	        stmt = connection.createStatement();
	        rs = stmt.executeQuery(query);
	        

	        int i =0;
	
            ArrayList<JSONObject> message = getFormattedResult(rs);
            
            messageArray.put(message);

	    } catch (SQLException e ) {
	    	//e.printStackTrace();
	    } finally {
	    	if (stmt != null) { 
	        	try {
	        		stmt.close();
	        		stmt = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
	        if (connection != null) { 
	        	try {
	        		connection.close();
	        		connection = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
	        if(rs!=null)
	        {
	        	try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        }
	    }
	}
	
	public  ArrayList<JSONObject> getFormattedResult(ResultSet rs) {
		ArrayList<JSONObject> resList = new ArrayList<JSONObject>();
	    try {
	        // get column names
	        ResultSetMetaData rsMeta = rs.getMetaData();
	        int columnCnt = rsMeta.getColumnCount();
	        ArrayList<String> columnNames = new ArrayList<String>();
	        for(int i=1;i<=columnCnt;i++) {
	            columnNames.add(rsMeta.getColumnName(i));
	        }

	        while(rs.next()) { // convert each object to an human readable JSON object
	            JSONObject obj = new JSONObject();
	            for(int i=1;i<=columnCnt;i++) {
	                String key = columnNames.get(i - 1);
	                String value = rs.getString(i);
	                obj.put(key, value);
	            }
	            resList.add(obj);
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            rs.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return resList;
	}
	
	
 
	
	public void run()
    {
		//this will be use to maintain the db connection ?
    
    }
}


