package g0220.emergencyroom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * This activity allows the user to add a patient to the database based on their
 * health card number, name and date of birth.
 * 
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 *
 */

public class AddPatient extends Search {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("hia");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_patient);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_patient, menu);
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
	 * Adds a new patient to the database based on information provided.
	 * 
	 * @param View
	 *            , Contains text boxes of patient information
	 * @throws FileNotFoundException
	 */
	public void add_patient(View view) throws FileNotFoundException {
		EditText add_hcn = (EditText) findViewById(R.id.addhcn);
		EditText add_name = (EditText) findViewById(R.id.addname);
		EditText add_day = (EditText) findViewById(R.id.addday);
		EditText add_month = (EditText) findViewById(R.id.addmonth);
		EditText add_year = (EditText) findViewById(R.id.addyear);

		String[] values = { add_hcn.getText().toString(),
				add_name.getText().toString(), add_day.getText().toString(),
				add_month.getText().toString(), add_year.getText().toString() };

		if (Arrays.asList(values).contains("")) {
			Toast.makeText(getApplicationContext(), "Invalid information!",
					Toast.LENGTH_LONG).show();
		}

		else {

			Integer hcn = Integer.parseInt(add_hcn.getText().toString());
			String name = add_name.getText().toString();
			Integer day = Integer.valueOf(add_day.getText().toString());
			Integer month = Integer.valueOf(add_month.getText().toString());
			Integer year = Integer.valueOf(add_year.getText().toString());

			Patient_Manager_Update();
			Patient_Manager registry = super.getNurses();
			String exists = "no";
			ArrayList<Patient> patients = registry.getPatientList();
			for (Patient p : patients) {
				Integer patientnumber = p.getHc();
				if (patientnumber.equals(hcn)) {
					exists = "yes";
				} else {
				}
			}
			if (exists.equals("yes")) {
				Toast.makeText(getApplicationContext(),
						"This Health Card Number already exists",
						Toast.LENGTH_LONG).show();
			} else {
				String data = hcn.toString() + ",";
				data += name + ",";
				data += year + "-" + month + "-" + day + '\n';
				FileOutputStream outputStream = openFileOutput(
						"patient_records.txt", Context.MODE_APPEND);
				try {
					outputStream.write(data.getBytes());
					outputStream.close();
				} catch (IOException e) {
					// Handle e
				}
				Intent intent = new Intent(this, HealthCardActivity.class);
				Health_Card HC = new Health_Card(hcn);
				intent.putExtra("hcKey", HC);
				startActivity(intent);
			}
		}
	}
}
