package com.tgmcians.crashhandler;

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
import com.tgmcians.crashhandler.R;

/**
 * Activity - That appears when your application crashes.
 * @author Ajay
 *
 */
public class CrashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crash_activity);

		final TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setText("Sorry, Something went wrong. \nPlease send error logs to developer.");

		findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// so it will first save the error trace in vm folder of parent directory of SD card
				String filePath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/vm/" + ".errorTrace.txt";
				sendErrorMail(CrashActivity.this, filePath);
				finish();
			}
		});

	}

	/**
	 * This list a set of application which can send email. 
	 * Here user have to pick one apps via email will be send to developer email id.
	 * @param _context
	 * @param filePath
	 */
	private void sendErrorMail(Context mContext, String filePath) {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		String subject = "Error Description"; // here subject
		String body = "Sorry for your inconvenience .\nWe assure you that we will solve this problem as soon possible."
				+ "\n\nThanks for using app."; // here email body

		sendIntent.setType("plain/text");
		sendIntent.putExtra(Intent.EXTRA_EMAIL,
				new String[] { "example@gmail.com" }); // your developer email id
		sendIntent.putExtra(Intent.EXTRA_TEXT, body);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		sendIntent.putExtra(Intent.EXTRA_STREAM,
				Uri.fromFile(new File(filePath)));
		sendIntent.setType("message/rfc822");
		mContext.startActivity(Intent.createChooser(sendIntent, "Complete action using"));
	}
}
