package com.example.crashhandler;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CrashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crash_activity);
		
		final TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText("Sorry, Something went wrong. \nPlease send error mail to admin!!");
		
		findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vm/" + ".errorTrace.txt";
				sendErrorMail(CrashActivity.this,filePath);
				finish();
			}
		});
		
	}
	
	private void sendErrorMail(Context _context , String filePath) {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		String subject = "Error Description";
		String body = "Sorry for your inconvenience .\nWe assure you that we will solve this problem as soon possible." 
					+ "\n\nThanks for using app.";
		
		sendIntent.setType("plain/text");
		sendIntent.putExtra(Intent.EXTRA_EMAIL,new String[] {"example@gmail.com"});
		sendIntent.putExtra(Intent.EXTRA_TEXT, body);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)) );
		sendIntent.setType("message/rfc822");
		_context.startActivity( Intent.createChooser(sendIntent, "App_name"));
	}
}
