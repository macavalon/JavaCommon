package Common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StreamReader extends FileReader {

	public StreamReader(String fileName) throws FileNotFoundException {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	public StreamReader(File file) throws FileNotFoundException {
		super(file);
		// TODO Auto-generated constructor stub
	}

	public StreamReader(FileDescriptor fd) {
		super(fd);
		// TODO Auto-generated constructor stub
	}
	
	public String ReadToEnd()
	{
		BufferedReader br = new BufferedReader(this);
		String result = "";
		String s;
		try {
			while((s = br.readLine()) != null) { 
			
				result = result + s + "\n";
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	
	}
	
	public void Close()
	{
		try {
			super.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
