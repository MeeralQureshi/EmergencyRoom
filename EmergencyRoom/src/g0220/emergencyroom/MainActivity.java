package g0220.emergencyroom;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * This is the main log-in page for the application. The activity checks the users ID and password
 * against the text-file of users and their passwords.
 * 
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 *
 */

public class MainActivity extends Activity {
	public static boolean isNurse = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	 * Allows the user to log in and checks whether the entered information matches
	 * the log in information in passwords.txt. Pop up appears if information is invalid.
	 * @param view
	 * @throws IOException
	 */
	public void logIn(View view) throws IOException {
		EditText enter_username = (EditText) findViewById(R.id.enter_username);
		EditText enter_password = (EditText) findViewById(R.id.enter_password);
		String fullLogin = enter_username.getText().toString() + ","
				+ enter_password.getText().toString() + ",";
		Intent intent = new Intent(this, Search.class);
		try {
			File logins = new File(
					"data/data/g0220.emergencyroom/files/patient_records.txt");

			if (logins != null) {
				InputStreamReader inputreader = new InputStreamReader(
						getAssets().open("passwords.txt"));
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line;
				Boolean readerClosed = false;
				Boolean loginFound = false;
				line = buffreader.readLine();
				do {
					if (line != null) {
						if (line.startsWith(fullLogin + "nurse")
								|| line.startsWith(fullLogin + "physician")) {
							buffreader.close();
							readerClosed = true;
							loginFound = true;
							startActivity(intent);

							if (line.startsWith(fullLogin + "nurse")) {
								isNurse = true;
								Toast.makeText(getApplicationContext(),
										"Logged in as a nurse",
										Toast.LENGTH_SHORT).show();
							} else if (line.startsWith(fullLogin + "physician")) {
								isNurse = false;
								Toast.makeText(getApplicationContext(),
										"Logged in as a physician",
										Toast.LENGTH_SHORT).show();
							}

						} else {
							line = buffreader.readLine();
						}
					}

				} while (line != null && readerClosed == false);
				if (loginFound == false) {
					Toast.makeText(getApplicationContext(), "Invalid login",
							Toast.LENGTH_SHORT).show();
				}
			}

		} catch (FileNotFoundException e) {
			Toast.makeText(getApplicationContext(), "File not found",
					Toast.LENGTH_SHORT).show();
		} finally {

		}

	}
}
