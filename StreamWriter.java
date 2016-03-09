package Common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class StreamWriter extends OutputStreamWriter {
	public static final String UTF8_BOM = "\uFEFF";
	public StreamWriter(String fileName) throws IOException {
		//FileOutputStream temp = new FileOutputStream(fileName);
		
		//Writer out = new OutputStreamWriter(new FileOutputStream(yourFile), "windows-1252");
		super(new FileOutputStream(fileName), "windows-1252");
		// TODO Auto-generated constructor stub
	}
	
	private static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            s = s.substring(1);
        }
        return s;
    }
	
	public StreamWriter(OutputStream arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public StreamWriter(OutputStream out, String charsetName)
			throws UnsupportedEncodingException {
		super(out, charsetName);
		// TODO Auto-generated constructor stub
	}

	public StreamWriter(OutputStream out, Charset cs) {
		super(out, cs);
		// TODO Auto-generated constructor stub
	}

	public StreamWriter(OutputStream out, CharsetEncoder enc) {
		super(out, enc);
		// TODO Auto-generated constructor stub
	}
	
	public void Write(String line)
	{
		String pline = removeUTF8BOM(line);
		try {
			super.write(pline);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void WriteLine(String line)
	{
		String finalLine = removeUTF8BOM(line) + "\n";
		
		try {
			super.write(finalLine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void Flush()
	{
		try {
			super.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
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
