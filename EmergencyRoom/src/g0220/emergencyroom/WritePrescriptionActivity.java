package g0220.emergencyroom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class is used to record a new prescription for a patient. When a new
 * prescription is to be written, this activity will save the name and
 * description of the prescription to a file on the internal storage. The file
 * in storage contains all prescriptions for the current patient.
 * 
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 * 
 */
public class WritePrescriptionActivity extends Activity {

	/**
	 * Represents a Patient's health card number
	 */
	private Integer HealthCardNumber;

	/**
	 * Getter function for HealthCardNumber
	 * 
	 * @return patient's health card number
	 */
	public Integer getHealthCardNumber() {
		return HealthCardNumber;
	}

	/**
	 * Setter function for HealthCardNumber
	 * 
	 * @param healthCardNumber
	 *            represents a valid health card number
	 */
	public void setHealthCardNumber(Integer healthCardNumber) {
		HealthCardNumber = healthCardNumber;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_prescription);
		Intent intent = getIntent();
		Health_Card HC = (Health_Card) intent.getSerializableExtra("hc2Key2");
		setHealthCardNumber(HC.getHealthCardNumber());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.write_prescription, menu);
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
	 * This function gets the name and description of a prescription from the
	 * EditText view. It then writes this information to a file in the internal
	 * storage. This file contains all prescription information for the current
	 * patient and will be used for records.
	 * 
	 * @param view
	 *            The current view
	 * @throws FileNotFoundException
	 */
	@SuppressLint("SimpleDateFormat")
	public void addPrescrip(View view) throws FileNotFoundException {

		EditText prescrip_name = (EditText) findViewById(R.id.prescrip_name);
		EditText prescrip_descript = (EditText) findViewById(R.id.prescrip_descript);

		String[] values = { prescrip_name.getText().toString(),
				prescrip_descript.getText().toString() };

		if (Arrays.asList(values).contains("")) {
			Toast.makeText(getApplicationContext(), "Invalid information!",
					Toast.LENGTH_LONG).show();
		}

		else {
			String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
			.format(Calendar.getInstance().getTime());
			String prescripName = prescrip_name.getText().toString();
			String prescripDescript = prescrip_descript.getText().toString();

			String data = time + "\n" + prescripName + "\n" + prescripDescript + "\n" + "\n";

			FileOutputStream outputStream = openFileOutput(
					getHealthCardNumber().toString() + "prescrip.txt",
					Context.MODE_APPEND);
			try {
				outputStream.write(data.getBytes());
				outputStream.close();

			} catch (IOException e) {
				System.out.println("Exception occured when writing");
			}

			Intent intent = new Intent(this, VitalsUpdateSuccess.class);
			intent.putExtra("healthCardNumber", getHealthCardNumber());
			startActivity(intent);
		}
	}
}
