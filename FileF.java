package Common;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardCopyOption.*;

public class FileF {
	
	public static void Delete(String filename)
	{
		File file = new File(filename);
        file.delete();
	
	}
	
	public static void Create(String filename)
	{
		String workingDir = System.getProperty("user.dir");
		File file = new File(filename);
        try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static Boolean Exists(String filename)
	{
		File file = new File(filename);
        return file.exists();
	
	}
	
	public static void Copy(String src, String dest)
	{
		Path source = FileSystems.getDefault().getPath("", src);
		Path destination = FileSystems.getDefault().getPath("", dest);
		try {
			Files.copy(source, destination, REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public static ArrayList<String> ReadAllLines(String filename) {
		
		ArrayList<String> lines =  new ArrayList<String>();
		File file = new File(filename);
		if(file.exists())
		{
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			String line = null;
			if(br!=null)
			{
				try {
					while ((line = br.readLine()) != null) {
						lines.add(line);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		}
		// TODO Auto-generated method stub
		return lines;
	}

}
