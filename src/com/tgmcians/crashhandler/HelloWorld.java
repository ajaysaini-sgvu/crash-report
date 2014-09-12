package com.tgmcians.crashhandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.tgmcians.crashhandler.R;

/**
 * Activity - Simple activity to demonstrate that exception is being thrown &
 * start the crash activity to send logs of it to developer
 * 
 * @author Ajay
 * 
 */
public class HelloWorld extends Activity {

	// variable that value is null.
	private String userName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the default uncaught exception handler
		// whenever any crash occurs which you haven't caught then a user
		// can report to developer about that exception
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

		setContentView(R.layout.activity_hello_world);

		// set click listener on button
		findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// here we didn't initialize this variable to demonstrate this
				// scenario. so in the below line app will crash & that will
				// open crash activity
				System.out.println(userName);
			}
		});
	}
}
