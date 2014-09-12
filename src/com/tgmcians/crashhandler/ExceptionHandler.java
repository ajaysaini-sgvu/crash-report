package com.tgmcians.crashhandler;

/**
 * This class fetch the android version OS details like version, manufacture etc & application version.
 */
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.util.Log;

public class ExceptionHandler implements
		java.lang.Thread.UncaughtExceptionHandler {

	private Context myContext;
	private String versionName;
	private String packageName;
	private String filePath;
	private String phoneModel;
	private String androidVersion;
	private String board;
	private String brand;

	// String CPU_ABI;
	private String device;
	private String display;
	private String fingerPrint;
	private String host;
	private String ID;
	private String manufacturer;
	private String model;
	private String product;
	private String tags;
	private long time;
	private String type;
	private String user;

	private static String TAG = "Crash Analytics";

	public ExceptionHandler(Context context) {
		myContext = context;
	}

	/**
	 * Find the available internal memory
	 * 
	 * @return available internal memory
	 */
	public long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * Find the total internal memory
	 * 
	 * @return total internal memory
	 */
	public long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	/**
	 * It gets the android phone & application details
	 * 
	 * @param context
	 */
	void recoltInformations(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo pi;
			// Version
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;

			// Package name
			packageName = pi.packageName;

			// Files dir for storing the stack traces
			filePath = context.getFilesDir().getAbsolutePath();

			// Device model
			phoneModel = android.os.Build.MODEL;

			// Android version
			androidVersion = android.os.Build.VERSION.RELEASE;

			board = android.os.Build.BOARD;
			brand = android.os.Build.BRAND;

			// CPU_ABI = android.os.Build.;
			device = android.os.Build.DEVICE;
			display = android.os.Build.DISPLAY;
			fingerPrint = android.os.Build.FINGERPRINT;
			host = android.os.Build.HOST;
			ID = android.os.Build.ID;

			// Manufacturer = android.os.Build.;
			model = android.os.Build.MODEL;
			product = android.os.Build.PRODUCT;
			tags = android.os.Build.TAGS;
			time = android.os.Build.TIME;
			type = android.os.Build.TYPE;
			user = android.os.Build.USER;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
	}

	/**
	 * It creates a complete string of about the android phone + app
	 * information.
	 * 
	 * @return
	 */
	public String createInformationString() {
		String returnVal = "";

		returnVal += "Version : " + versionName;
		returnVal += "\n";
		returnVal += "Package : " + packageName;
		returnVal += "\n";
		returnVal += "FilePath : " + filePath;
		returnVal += "\n";
		returnVal += "Phone Model" + phoneModel;
		returnVal += "\n";
		returnVal += "Android Version : " + androidVersion;
		returnVal += "\n";
		returnVal += "Board : " + board;
		returnVal += "\n";
		returnVal += "Brand : " + brand;
		returnVal += "\n";
		returnVal += "Device : " + device;
		returnVal += "\n";
		returnVal += "Display : " + display;
		returnVal += "\n";
		returnVal += "Finger Print : " + fingerPrint;
		returnVal += "\n";
		returnVal += "Host : " + host;
		returnVal += "\n";
		returnVal += "ID : " + ID;
		returnVal += "\n";
		returnVal += "Model : " + model;
		returnVal += "\n";
		returnVal += "Product : " + product;
		returnVal += "\n";
		returnVal += "Tags : " + tags;
		returnVal += "\n";
		returnVal += "Time : " + time;
		returnVal += "\n";
		returnVal += "Type : " + type;
		returnVal += "\n";
		returnVal += "User : " + user;
		returnVal += "\n";
		returnVal += "Total Internal memory : " + getTotalInternalMemorySize();
		returnVal += "\n";
		returnVal += "Available Internal memory : "
				+ getAvailableInternalMemorySize();
		returnVal += "\n";
		return returnVal;
	}

	/**
	 * Append the error log with phone & app details
	 */
	public void uncaughtException(Thread thread, Throwable exception) {
		recoltInformations(myContext);
		
		String report = "";
		
		Date curDate = new Date();
		report += "Error Report collected on : " + curDate.toString();
		report += "\n";
		report += "\n";
		report += "Informations :";
		report += "\n";
		report += "==============";
		report += "\n";
		report += "\n";
		
		report += createInformationString();

		report += "\n\n";
		report += "Stack : \n";
		report += "======= \n";
		
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		exception.printStackTrace(printWriter);
		String stacktrace = result.toString();
		report += stacktrace;

		// If the exception was thrown in a background thread inside
		// AsyncTask, then the actual exception can be found with getCause
		Throwable cause = exception.getCause();
		if (cause != null) {
			report += "\n";
			report += "Cause : \n";
			report += "======= \n";
		}

		// find the cause of the crash
		while (cause != null) {
			cause.printStackTrace(printWriter);
			report += result.toString();
			cause = cause.getCause();
		}

		printWriter.close();
		report += "****  End of current Report ***";

		Intent intent = new Intent(myContext, CrashActivity.class);
		intent.putExtra("STACKTRACE", report);
		
		// create a file in SD card & write the error log in it
		// later send this file in the email to developer
		
		new Helper().saveAsFile(report, myContext);
		
		// start the crash activity
		myContext.startActivity(intent);
		
		// kill the process
		Process.killProcess(Process.myPid());
		System.exit(10);
	}
}
