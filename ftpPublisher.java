package Common;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

import Common.Console;


class ftpPublisher
{

    public ftpPublisher(String path, String username, String password, String host)
    {
        Url = host + path ;
        User = username;
        Password = password;
        init();

    }

    public void UploadFolder(String srcfoldername,
                           String destfoldername)
    {
    	File srcDir = new File(srcfoldername);

    	Collection<File> files = FileUtils.listFiles(srcDir, TrueFileFilter.INSTANCE, null);
        
        
        for (File file : files)
        {
            String srcfilename = srcfoldername + "/" +  file.getName();
            String destfilename = destfoldername + "/" + file.getName();
            
            UploadFile(srcfilename, destfilename, false, destfoldername);
        }
    }

    public void CreateAllFolders(String destFolderName)
    {
    	String folders[] = destFolderName.split("/");
    	
    	// should create folders in order required..
    	for (String folder : folders)
    	{
    		Boolean folderCreated = false;
    		while(!folderCreated)
    		{
    			try {
                	client.chdir(folder);
                	folderCreated = true;
                } catch(Exception e) {
                	try {
	                	client.mkdir(folder);
	                	client.chdir(folder);
	                	folderCreated = true;
                	}
                	catch(Exception f) {
                		try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                	}
                }
    		}
    	
    	}
    	
    }
    
    public Boolean UploadFile(String srcfilename, 
                           String destfilename, 
                           Boolean delete,
                           String destFolderName)
    {
        // create source directory for file if required

        if (destFolderName != "")
        {
            
            reconnect();
            while (client == null)
            {
                Console.WriteLine("Reconnecting");
                reconnect();
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

            Boolean createdFolder = false;
            while (!createdFolder)
            {
                try
                {
                    Boolean folderExists = false;

                  
                    try
                    {
                       folderExists = client.existsDirectory(destFolderName);
                    }
                    catch (IOException _exception)
                    {
                        Console.WriteLine(" didn't determine whether folder exists");
                    }
                    
                  
                    CreateAllFolders(destFolderName);

                    createdFolder = true;
                    Console.WriteLine(" created folders succesfully");
                }
                catch (Exception _exception)
                {
                    Console.WriteLine(" failed to create folder " + _exception.getMessage());

                    reconnect();
                }
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //sleep 5 secs
            }

        }

        Boolean published = false;

        reconnect();
        while (client == null)
        {
            Console.WriteLine("Reconnecting");
            reconnect();
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        if (delete)
        {
            try
            {
                client.delete(destfilename);
                Console.WriteLine(" deleted succesfully");
            }
            catch (Exception _exception)
            {
                Console.WriteLine(" failed to deletfile " + _exception.getMessage());
            }
        }
        else
        {
            // does the file exist
            // if we're not deleting the file, then we won't overwrite!
            Boolean fileExists = false;
            try
            {
                fileExists = client.existsFile(destfilename);         		
            }
            catch (Exception _exception)
            {
                Console.WriteLine(" didn't determine whether file exists");
            }

            if (fileExists)
            {
                return true;
            }
        }
       
        Boolean uploaded = false;
        while (!uploaded)
        {
            try
            {
            	//may have to change directory etc...
                client.put(srcfilename, destfilename);
                published = true;
                uploaded = true;
                Console.WriteLine(" uploaded succesfully");
            }
            catch (Exception _exception)
            {
                Console.WriteLine(" failed to upload " + _exception.getMessage());

                reconnect();
            }
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //sleep 5 secs
        }


        return published;
    }



    public void init()
    {
        client = new FTPClient();
    }

    public void reconnect()
    {
        if (client != null)
        {
            try
            {
                client.quit();
                client = null;
                Console.WriteLine(" closed connection succesfully");
            }
            catch (Exception _exception)
            {
                Console.WriteLine(" failed to close " + _exception.getMessage());
                client = null;
                
            }
        }
        else
        {

            try
            {
                init();
            }
            catch (Exception _exception)
            {
                Console.WriteLine(" failed to create new ftp client " + _exception.getMessage());
            }
        }

        if (client != null)
        {
            try
            {
                client.setRemoteHost(Url);
                //client.setRemotePort(2021); //if none standard
                Console.WriteLine(" connected succesfully");
            }
            catch (Exception _exception)
            {
                Console.WriteLine(" failed to connect " + _exception.getMessage());
            }

            try
            {
            	client.connect();
                client.user(User);
                client.password(Password);
                
                Console.WriteLine(" logged in succesfully");
            }
            catch (Exception _exception)
            {
                Console.WriteLine(" failed to login " + _exception.getMessage());
            }
        }
        
    }

    FTPClient client;
    String Url;
    String User;
    String Password;
}

