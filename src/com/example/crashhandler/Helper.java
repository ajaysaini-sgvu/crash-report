package com.example.crashhandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class Helper {

	void SaveAsFile(String ErrorContent,Context context)
	{
		try {
			String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vm/";
			File dir = new File(filePath);
			
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			File file = new File(filePath, ".errorTrace.txt");
			if(file.exists())
				file.delete();
			file.createNewFile();
			
			FileOutputStream trace = new FileOutputStream(file);
			PrintStream printStream;
			printStream = new PrintStream(trace);
			printStream.println (ErrorContent);
			printStream.close();
			trace.close();
		}
		catch(Exception ioe) {
			Log.v("Crash",ioe.getMessage());
		}
	}
	
}
