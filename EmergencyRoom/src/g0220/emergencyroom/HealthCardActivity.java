package g0220.emergencyroom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

/**
 * Retrieves health card file for the given patient. This is used to get the
 * patients records, update vitals or check them in
 * 
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 * 
 */
public class HealthCardActivity extends Search {

	/**
	 * represents the health card number for the current patient
	 */
	private Integer number;

	/**
	 * represents the current patient
	 */
	private Patient patient;

	/**
	 * When a health card activity is created, this method retrieves info from
	 * intent and then finds the current patient.
	 * It also displays the patient's urgency (if available), whether they have
	 * been seen by a doctor (if checked in) and their personal information.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health_card);
		Intent intent = getIntent();
		Health_Card HC = (Health_Card) intent.getSerializableExtra("hcKey");
		TextView healthCard = (TextView) findViewById(R.id.healthCard);
		Integer number = HC.getHealthCardNumber();
		Patient_Manager_Update();
		Patient_Manager registry = super.getNurses();
		ArrayList<Patient> patients = registry.getPatientList();
		
		
		for (Patient p : patients) {
			Integer patientnumber = p.getHc();
			if (patientnumber.equals(number)) {
				setPatient(p);
				break;
			} else {
			}
		}
		healthCard.setText(this.getPatient().toString() + '\n');
		setNumber(this.getPatient().getHc());
		TextView urgency = (TextView) findViewById(R.id.urgency);
		urgency.setText(showUrgency());
		
		String seenbydoctor = "This patient has not been seen yet.";
		TextView doctortime = (TextView) findViewById(R.id.seendoctorresult);
		doctortime.setText(seenbydoctor);
		try {
			if (!(ArrivalCheck(getPatient().getHc()))){
				doctortime.setVisibility(TextView.GONE);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (MainActivity.isNurse == true){ //Makes Write prescriptions unavailable to nurses
			View b = findViewById(R.id.addPrescrip);
			b.setVisibility(View.GONE);
			View c = findViewById(R.id.button2);
			c.setVisibility(View.VISIBLE);
			View d = findViewById(R.id.checkIn);
			d.setVisibility(View.VISIBLE);
			View e = findViewById(R.id.seenByDoctor);
			e.setVisibility(View.VISIBLE);
		}
		else if (MainActivity.isNurse == false){ //Makes every button but Write prescriptions and get Records unavailable to physicians
			View b = findViewById(R.id.addPrescrip);
			b.setVisibility(View.VISIBLE);
			View c = findViewById(R.id.button2);
			c.setVisibility(View.GONE);
			View d = findViewById(R.id.checkIn);
			d.setVisibility(View.GONE);
			View e = findViewById(R.id.seenByDoctor);
			e.setVisibility(View.GONE);
		}

	}

	/**
	 * Getter for patient
	 * 
	 * @return current patient
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * Setter for patient
	 * 
	 * @param current
	 *            patient
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.health_card, menu);
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
	 * This method begins the next activity, giving Health_Card associated with
	 * patient
	 * Accessible by: Physicians and nurses
	 * 
	 * @param view
	 */
	public void getRecords(View view) {

		Intent intent = new Intent(this, HCRecords.class);
		Health_Card HC = new Health_Card(getNumber());
		intent.putExtra("hcKey1", HC);
		startActivity(intent);
	}

	/**
	 * Takes user to the update vitals page for that particular patient.
	 * Accessible by: Nurses
	 * 
	 * @param view
	 *            the update vitals button
	 */
	public void updateVitals(View view) {

		Patient patient = getPatient();
		try {
			if (ArrivalCheck(patient.getHc())) {
				Intent intent2 = new Intent(this, VitalUpdate.class);
				Health_Card HC2 = new Health_Card(getNumber());
				intent2.putExtra("hc2Key", HC2);
				startActivity(intent2);
			} else {
				Toast.makeText(getApplicationContext(), "Not checked in",
						Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "Invalid entry",
					Toast.LENGTH_LONG).show();
		}
	}
	/**
	 * Bring you to a page where you can assign a doctor's visit.
	 * Accessible by: Nurses
	 * 
	 * @param View, a button leading to SeenDoctorActivity
	 */
	public void seenByDoctor(View view) {
		Patient patient = getPatient();
		try {
			if (ArrivalCheck(patient.getHc())) {  // Checks whether patient is checked in before they see a doctor
				Intent intent = new Intent(this, SeenDoctorActivity.class);
				Health_Card HC = new Health_Card(getNumber());
				intent.putExtra("hcKey1", HC);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "Not checked in",
						Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "Invalid entry",
					Toast.LENGTH_LONG).show();
		}
		
	}
	
	/**
	 * Takes the user to the write prescription activity
	 * Accessible by: Physicians
	 * @param view
	 */
	public void addPrescrip(View view) {

		Intent intent3 = new Intent(this, WritePrescriptionActivity.class);
		Health_Card HC3 = new Health_Card(getNumber());
		intent3.putExtra("hc2Key2", HC3);
		startActivity(intent3);
	}

	/**
	 * 
	 * Creates a check in time if it does not exist. If it does, returns the
	 * check in time as a Toast.
	 * Accessible by: Nurses
	 * 
	 * @param View  a button called "Check in" in the activity.
	 */
	@SuppressLint("SimpleDateFormat")
	public void checkIn(View view) throws IOException {
		if (ArrivalCheck(getNumber()) == false) { // if arrival time not on
													// record yet
			String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
					.format(Calendar.getInstance().getTime());
			time += "\n"; // 2014/08/06 16:00:22
			getPatient().setArrivalTime(time); // set the arrival time
			FileOutputStream outputStream = openFileOutput("ArrivalTimes.txt",
					Context.MODE_APPEND); // write to arrival time file
			String writing = "$" + getPatient().getHc().toString() + ',' + time
					+ '\n';
			try {
				outputStream.write(writing.getBytes()); // write a string of HCN
														// and arrival time
				outputStream.close();
			} catch (IOException e) {
				// Handle e
			}

			Patient p = getPatient();
			Intent intent3 = new Intent(this, CheckedIn.class);
			intent3.putExtra("pKey", p);
			Integer HealthCardNum = getNumber();
			intent3.putExtra("healthCardNumber", HealthCardNum);
			
			startActivity(intent3);
		} else { // if the arrival time already exists
			String hcnfile = "ArrivalTimes.txt";
			int ch;
			StringBuffer strContent = new StringBuffer("");
			String path = "data/data/g0220.emergencyroom/files/" + hcnfile;
			File f = new File(path);
			if (f.exists()) {
				try {
					FileInputStream fis = openFileInput(hcnfile); 
															

					while ((ch = fis.read()) != -1)
						strContent.append((char) ch);
				} catch (FileNotFoundException e) {
					Toast.makeText(getApplicationContext(), "File not found",
							Toast.LENGTH_SHORT).show();
				}
				String finaltime = "";
				String lines[] = strContent.toString().split("\\r?\\n");
				ArrayList<String> linesarray = new ArrayList<String>(
						Arrays.asList(lines)); // splice the arrival time from
												// the file
				for (int i = 0; i < linesarray.size(); i++) {
					if (linesarray.get(i).contains("$"+getNumber().toString()+",")) {
						finaltime = linesarray.get(i);
						break;
					} else {
					}
				}
				String parts[] = finaltime.split(",");
				Toast.makeText(getApplicationContext(),
						"Checked in at:" + "\n" + parts[1], Toast.LENGTH_LONG)
						.show(); // return arrival as toast
			} else {
				strContent.append("No Arrival Times have been recorded yet");

			}
		}
	}

	/**
	 * 
	 * Return a boolean of if the patient has an existing arrival time in the
	 * file.
	 * 
	 * @param HC an integer representation of the patient's health card
	 * @return Boolean T or F of whether the arrival time exists.
	 */

	public Boolean ArrivalCheck(Integer HC) throws IOException {
		String hcnfile = "ArrivalTimes.txt";
		int ch;
		StringBuffer strContent = new StringBuffer("");
		String path = "data/data/g0220.emergencyroom/files/" + hcnfile;
		File f = new File(path);
		if (f.exists()) { // if file exists
			try {
				FileInputStream fis = openFileInput(hcnfile);

				while ((ch = fis.read()) != -1)
					// read file as string
					strContent.append((char) ch);
			} catch (FileNotFoundException e) {
				Toast.makeText(getApplicationContext(), "File not found",
						Toast.LENGTH_SHORT).show();
			}
		} else { // else, no arrival times have been made
			strContent.append("No Arrival Times have been recorded yet");
		}
		String match = "$"+HC.toString()+",";
		if (strContent.toString().contains(match)) { // if file contains
			return true;
		} else { // else false
			return false;
		}
	}

	/**
	 * Getter function for number
	 * 
	 * @return get the health card number of the patient
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * Setter function for number
	 * 
	 * @param number
	 *            Integer referring to the Patient's health card number
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	/** 
	 * Shows the patient's urgency by reading from their file and
	 * displays a string representation on their health card page.
	 * @return
	 */
	public String showUrgency(){
		String hcnfile = getPatient().getHc().toString()+".txt";
	    BufferedReader reader;
	    String urgency = null;
	    String path = "data/data/g0220.emergencyroom/files/"+hcnfile; // Read urgency from their file
	    String line = ""; //Initialize line
	    File f = new File(path);
	    if(f.exists()){
		    try {
		        reader = new BufferedReader(new FileReader(path));
		        try {
		            while((line = reader.readLine()) != null)
		            {
		                urgency = line; //Last line will usually be urgency (Unless they have just checked in/out)
		                
		            }
		            
		            reader.close();
		            
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		    } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		    	Toast.makeText(getApplicationContext(), "File not found",
						Toast.LENGTH_SHORT).show();
		    }
		    if(urgency.contains("Urgency")){
		    	return urgency; //If their latest urgency is on the file then display it
		    }
		    else{
		    	urgency = "No recent vitals - No available urgency";
		    	return urgency; //If the latest entry is not their urgency (i.e just checked in/out) then not available
		    }
		
	    }
	    else{
	    	urgency = "No recent vitals - No available urgency";
	    	return urgency; //If file doesn't exist, then not available.
	    }

	}
	
	/**
	 * Takes the user back to the Health Card Search
	 * @param view back to health card search button
	 */
	public void searchBack(View view){
		Intent intent = new Intent(this, Search.class);
		startActivity(intent);
	}
}
