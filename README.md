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
final dbConnection db_connection = new dbConnection(live,mReporter,Profiler);
        
```

