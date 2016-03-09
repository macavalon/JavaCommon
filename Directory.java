package Common;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Directory {

	public static Boolean Exists(String dir)
	{
		Path dirPath = FileSystems.getDefault().getPath(dir);
		return Files.exists(dirPath);
        
	}
	
	public static void CreateDirectory(String dir)
	{
		Path dirPath = FileSystems.getDefault().getPath(dir);
		try {
			Files.createDirectory(dirPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}
	
}
