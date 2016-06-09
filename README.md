#JavaCommon

A set of classes for performing common functions.

##.NET migration helper classes

Some replicate basic functionality of classes available in .NET 
- StreamReader
- StreamWriter 
- Console
- XmlTextReader
- XmlTextWriter
- Convert
- Directory
- FileF e.g. .Net File
- ftpPublisher
 * provides convenient functionality ontop of http://enterprisedt.com/products/edtftpj/
 * automatically reconnects on failure
 * upload single file (with a directory path e.g. dir1/dir2/file.txt creates dir1/dir2 if don't exist)
 * upload a complete folder including files
 * create folder tree
 * requires 
  * apache commons io
  * edtftpj (free edition)

##Additional useful classes
- dbConnection
 * MySQL connection class with example queries (to be extended by user!)
- reporter
 * Generic reporting class, with support prefix on log line e.g. username, type, 


#Example use
```
import Common.FileF;
import Common.reporter;
import profile.profiler; // see JavaProfiler repo

String fileName = "report.txt";

if (FileF.Exists(fileName))
{
    // remove
    FileF.Delete(fileName);
}

//create report file
reporter mReporter = new reporter(fileName);
mReporter.setUsername("admin");
mReporter.setType("wwwcrawler");

mReporter.report("some useful output",true);



profiler Profiler = new profiler();

Boolean live = true; // dbConnection internally supports two database urls, usernames, passwords

final dbConnection db_connection = new dbConnection(live,mReporter,Profiler);

String path = "/";
String username = "test_user";
String password = "test_password";
String host = "host.com";

FtpPublisher = new ftpPublisher(path, username, password, host);

FtpPublisher.UploadFile(src_filename, //source 
                        dest_filename, //destination
                        true,          //delete existing destination before upload
                        dest_foldername //destination folder to create, can be ""
                        );
                        
FtpPublisher.UploadFolder(src_dir,
                          dest_dir);
```

