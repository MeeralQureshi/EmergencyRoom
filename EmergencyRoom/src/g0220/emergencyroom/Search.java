
package g0220.emergencyroom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Search extends Activity {
	
	private Patient_Manager nurses;

	@Override
	/**
	 * Creates a new registry and reads the text file through the Patient manager class
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		try {
			Patient_Manager nurses = new Patient_Manager(this);
			setNurses(nurses);
		} catch (FileNotFoundException e) {
			Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "Invalid entry", Toast.LENGTH_SHORT).show();
		}
		if (MainActivity.isNurse == false){ //If a physician is logged in, then they can't sort the patients by urgency.
			View sortPatients = findViewById(R.id.sortPatients);
			sortPatients.setVisibility(View.GONE);
			View addPatient = findViewById(R.id.add_patient);
			addPatient.setVisibility(View.GONE);
			
		}
	}
	
	/**
	 * Gets the current patient manager
	 * @return the current patient manager
	 */
	public Patient_Manager getNurses() {
		return nurses;
	}
	
	/**
	 * Updates the patient manager and it's lists.
	 */
	public void Patient_Manager_Update() {
		try {
			Patient_Manager nurses = new Patient_Manager(this);
			setNurses(nurses);
		} catch (FileNotFoundException e) {
			Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "Invalid entry", Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * Set the new patient manager
	 * @param nurses the new patient manager
	 */
	public void setNurses(Patient_Manager nurses) {
		this.nurses = nurses;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	 * Pulls up a View of the entered patient's health card
	 * @param view Text from the search field (Entered health card number)
	 */
	public void searchHC (View view){
		Intent intent = new Intent(this,HealthCardActivity.class);
		EditText healthcardnumber = (EditText) findViewById(R.id.card_search);
		String hcn = healthcardnumber.getText().toString();
		Patient_Manager_Update();
		Health_Card_Registry registry = this.nurses.getRegistry();
		Health_Card HC;
		try {
			HC = registry.getCard(Integer.parseInt(hcn));
			intent.putExtra("hcKey", HC);
			startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Invalid entry", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * Takes the user to the add patient activity
	 * @param view Add patient button
	 */
	public void addPatient (View view){
		Intent intent = new Intent(this,AddPatient.class);
		startActivity(intent);
	}
	
	/**
	 * Takes the sorted list and carries it to the sorted patients activity
	 * @param view sort patients button
	 * @throws FileNotFoundException
	 */
	public void sortPatients(View view) throws FileNotFoundException{
		Intent sortIntent = new Intent(this,SortPatientsActivity.class);
		ArrayList<Patient> sortedList = sortByUrgency();
		sortIntent.putExtra("sortedList", sortedList);
		startActivity(sortIntent);
	}
	
	/**
	 * Reads the given patient's file for their latest urgency
	 * @param p the given patient
	 */
	public void readForUrgency(Patient p){ 
		String hcnfile = p.getHc().toString()+".txt";
	    BufferedReader reader;
	    String urgency = null;
	    String path = "data/data/g0220.emergencyroom/files/"+hcnfile;
	    String line = "";
	    File f = new File(path);
	    if(f.exists()){
		    try {
		        reader = new BufferedReader(new FileReader(path));
		        try {
		            while((line = reader.readLine()) != null)
		            {
		                urgency = line; // Will be the last line in their file unless they have checked in/out
		                
		            }
		            
		            reader.close();
		            
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		    } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		    	e.printStackTrace();
		    }
		    if(urgency.contains("Urgency")){ //If the last input is their urgency, set the patients urgency to the given number
		    	String newUrgency = urgency.substring(urgency.length() - 1);
		    	Integer urgencyNumber = Integer.parseInt(newUrgency);
		    	p.setUrgency(urgencyNumber);
		    }
		    else{
		    	p.setUrgency(-1); //If the last line is not their urgency, urgency is -1 (not valid)
		    }
		
	    }
	    else{
	    	p.setUrgency(-1); //If the file doesn't exist, their urgency is -1 (not valid)
	    }
	    
	}
	
	/**
	 * Takes the patient list from patient manager and sorts them by urgency 
	 * in descending order. If the patient's urgency is -1 (from read urgency) then
	 * it is not sorted.
	 * @return
	 * @throws FileNotFoundException
	 */
	public ArrayList<Patient> sortByUrgency() throws FileNotFoundException{
		ArrayList<Patient> patientsToSort = new ArrayList<Patient>(); //Take the patient list
		ArrayList<Patient> registryPatients = this.nurses.getPatientList();
		for(Patient p: registryPatients){
			readForUrgency(p); //Read each patient's urgency and set it.
			if(!(p.getUrgency().equals(-1))){ //If their urgency is -1 (invalid) don't add them to the list to be sorted
				patientsToSort.add(p);
			}
		}
		Collections.sort(patientsToSort);
		Collections.reverse(patientsToSort); //Sort and reverse the list to descending order
		return patientsToSort;
	}
	
	/**
	 * Takes the user back to the log in screen
	 * @param view back to log in screen button.
	 */
	public void backLogin(View view){
		Intent loginIntent = new Intent(this,MainActivity.class);
		startActivity(loginIntent);
	}
			
}
