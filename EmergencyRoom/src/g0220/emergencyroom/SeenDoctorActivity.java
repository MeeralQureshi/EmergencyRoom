package g0220.emergencyroom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SeenDoctorActivity extends Search {

	private Integer hcn;

	@Override
	/**
	 * Gets the health card number from the previous activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seen_doctor);
		Intent intent = getIntent();
		Health_Card HC = (Health_Card) intent.getSerializableExtra("hcKey1");
		setHcn(HC.getHealthCardNumber());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seen_doctor, menu);
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
	 * Writes when the doctor saw the patient onto the patient's health record
	 * and checks them out of the ER.
	 * 
	 * @param view
	 *            Submit button
	 * @throws FileNotFoundException
	 *             If the patient's health record is not found
	 */
	public void sumbitChanges(View view) throws FileNotFoundException {
		EditText dateseen = (EditText) findViewById(R.id.dateseen);
		EditText timeseen = (EditText) findViewById(R.id.timeseen);

		String[] values = { dateseen.getText().toString(),
				timeseen.getText().toString() };

		if (Arrays.asList(values).contains("")) {
			Toast.makeText(getApplicationContext(), "Invalid information!",
					Toast.LENGTH_LONG).show();
		}

		else {

			String time = timeseen.getText().toString();
			String date = dateseen.getText().toString();
			String data = getHcn().toString() + ',' + date + ',' + time + '\n'; // Date
																				// and
																				// time
																				// patient
																				// was
																				// seen,
																				// with
																				// HC
																				// number
			String[] newData = data.split(",");
			String writeToFile = "Patient was seen at: " + newData[1] + ", "
					+ newData[2] + "\n"; // Date and time written on record
			FileOutputStream healthRecord = openFileOutput(getHcn().toString()
					+ ".txt", Context.MODE_APPEND);
			try {
				healthRecord.write(writeToFile.getBytes());
				healthRecord.close();
			} catch (IOException e) {
				// Handle e
			}
			checkOutPatient(); // Checks out patient using helper function
			Toast.makeText(getApplicationContext(),
					"The doctor's visit has been recorded", Toast.LENGTH_SHORT)
					.show();
			Intent intent = new Intent(this, HealthCardActivity.class);
			Health_Card_Registry registry = this.getNurses().getRegistry();
			Health_Card HC;
			try {
				HC = registry.getCard(getHcn()); // Back to health card screen
				intent.putExtra("hcKey", HC);
				startActivity(intent);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Checks a patient out of the ER by removing their name from the
	 * ArrivalTimes file by temporarily writing every other patient onto a new
	 * file and renaming that file.
	 */
	public void checkOutPatient() {
		String oldFileName = "data/data/g0220.emergencyroom/files/ArrivalTimes.txt";
		String tmpFileName = "data/data/g0220.emergencyroom/files/temporary.txt";
		String hcn = getHcn().toString();

		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(oldFileName)); // ArrivalTimes.txt
			bw = new BufferedWriter(new FileWriter(tmpFileName)); // New temp
																	// file
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains(hcn))
					line = line.replace(hcn, "Checked Out"); // Replaces the
																// health card
																// number with
																// checked out
																// in old file
				bw.write(line + "\n");
			}
		} catch (Exception e) {
			return;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				//
			}
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				//
			}
		}
		// Deletes the old file
		File oldFile = new File(oldFileName);
		oldFile.delete();

		// renames the temporary files to ArrivalTimes.txt
		File newFile = new File(tmpFileName);
		newFile.renameTo(oldFile);

	}
	
	/**
	 * Gets the patients health card number
	 * 
	 * @return patient's health card number
	 */
	public Integer getHcn() {
		return hcn;
	}

	/**
	 * Sets the patients health card number to the new one
	 * 
	 * @param hcn
	 *            new health card number
	 */
	public void setHcn(Integer hcn) {
		this.hcn = hcn;
	}
}
