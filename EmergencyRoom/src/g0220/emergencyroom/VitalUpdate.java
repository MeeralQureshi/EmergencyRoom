package g0220.emergencyroom;


import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import android.annotation.SuppressLint;
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
 * Allows the user to update the vital signs of a selected patient through their health card.
 * This activity also calculates the patient's urgency based on their vital signs and age.
 * 
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 *
 */

public class VitalUpdate extends Search {

	private Integer HealthCardNumber;
	private Patient patient;
	
	/**
	 * Gets the current patient
	 * @return the current patient
	 */
	public Patient getPatient() {
		return patient;
	}
	
	/**
	 * Sets the patient by searching through the registry for their health card number
	 */
	public void setPatient() { 
		this.patient = super.getNurses().searchPatientHC(getHealthCardNumber());
	}
	
	/**
	 * Gets the current patient's health card number
	 * @return the patient's health card number
	 */
	public Integer getHealthCardNumber() {
		return HealthCardNumber;
	}
	
	
	/**
	 * Sets the patient's health card number to the newly entered one
	 * @param healthCardNumber the new health card number
	 */
	public void setHealthCardNumber(Integer healthCardNumber) {
		HealthCardNumber = healthCardNumber;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vital_update2);
		Intent intent2 = getIntent();
		Health_Card HC2 = (Health_Card) intent2.getSerializableExtra("hc2Key");
        setHealthCardNumber(HC2.getHealthCardNumber());
        setPatient(); // For age urgency calculation
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vital_update, menu);
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
	 * Updates the patient's file with the newest vitals and their urgency
	 * @param view the update vitals button
	 * @throws IOException
	 */
	@SuppressLint("SimpleDateFormat")
	public void update(View view) throws IOException {

		EditText enter_temp = (EditText) findViewById(R.id.enter_temp);
		EditText enter_systolic = (EditText) findViewById(R.id.enter_systolic);
		EditText enter_diastolic = (EditText) findViewById(R.id.enter_diastolic);
		EditText enter_heartrate = (EditText) findViewById(R.id.enter_heartrate);
		EditText enter_time = (EditText) findViewById(R.id.enter_time);
		
		String[] values = {enter_temp.getText().toString(), enter_systolic.getText().toString(), 
				enter_diastolic.getText().toString(), enter_heartrate.getText().toString(), 
				enter_time.getText().toString()};
		
		if (Arrays.asList(values).contains("")){
			Toast.makeText(getApplicationContext(), "Invalid information!", Toast.LENGTH_LONG).show();
		}
			
	

		else
		{
			
			Double temperature = Double.valueOf(enter_temp.getText().toString());
			Integer systolic = Integer.valueOf(enter_systolic.getText().toString());
			Integer diastolic = Integer.valueOf(enter_diastolic.getText().toString());
			Integer heartrate = Integer.valueOf(enter_heartrate.getText().toString());
			Integer urgency = calculateUrgency(temperature, systolic, diastolic, heartrate); //Calculate urgency
			String time = enter_time.getText().toString();

			String times = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
			String data = '\n'+ times;
			data += "\nTime: " + time.toString();
			data += "\nTemperature: " + temperature.toString() + "\nHeartrate: " + heartrate.toString() + "\nSystolic Blood Pressure: " + systolic.toString();
			data += "\nDiastolic Blood Pressure: " + diastolic.toString() + "\nUrgency: " + urgency.toString(); // Write urgency to file
			data +="\n";
			FileOutputStream outputStream = openFileOutput(getHealthCardNumber().toString()+".txt", Context.MODE_APPEND);
			try {
				outputStream.write(data.getBytes());
				outputStream.close();
			} 	catch (IOException e){
			//Handle e
			}

			Intent intent = new Intent(this,VitalsUpdateSuccess.class);
			intent.putExtra("healthCardNumber", getHealthCardNumber()); //Send health card number to success page
			startActivity(intent);
		}
	}
	
	/** 
	 * Calculates the patients urgency (from 0-4) based on the entered vitals and the patient's age
	 * @param temp patient's temperature
	 * @param systolic patient's systolic blood pressure
	 * @param diastolic patient's diastolic blood pressure
	 * @param heartRate patient's heartrate
	 * @return the calculated number of urgency points from 0-4
	 */
	public Integer calculateUrgency(double temp, Integer systolic, Integer diastolic, Integer heartRate){
		Integer urgencyCounter = 0;
		if(temp >= 39.0){
			urgencyCounter++;
		}
		if(systolic >= 140 || diastolic >= 90){
			urgencyCounter++;
		}
		if(heartRate >= 100 || heartRate <= 50){
			urgencyCounter++;
		}
		Patient p = this.getPatient();
		if(p.getAge() < 2){
			urgencyCounter++;
		}
		p.setUrgency(urgencyCounter);
		return urgencyCounter;
	}
}

