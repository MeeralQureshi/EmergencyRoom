package g0220.emergencyroom;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity to confirm that a patient has been checked in 
 *
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 *
 */
public class CheckedIn extends Activity {

	@Override
	/**
	 * Displays the check in time and writes the time the patient checked in to their Health record
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checked_in);
		Intent intent = getIntent();
		Patient p = (Patient) intent.getSerializableExtra("pKey");
		TextView checkInTime = (TextView) findViewById(R.id.checkInTime);
		checkInTime.setText(p.getArrivalTime());
		String checkIn = "Checked in: " + p.getArrivalTime();
		try {
			FileOutputStream outputStream = openFileOutput(p.getHc().toString()+".txt", Context.MODE_APPEND);
			outputStream.write(checkIn.getBytes());
			outputStream.close();
		} catch (IOException e){
			Toast.makeText(getApplicationContext(), "Invalid entry", Toast.LENGTH_SHORT).show();
		}
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
	 * Takes the user back to the active health card
	 * @param view back to health card button
	 */
	public void backCard(View view){ 
		Intent intent = new Intent(this,HealthCardActivity.class);
		Intent oldIntent = getIntent();
		Integer healthCard = (Integer) oldIntent.getSerializableExtra("healthCardNumber");
		Health_Card searchHC = new Health_Card(healthCard);
		
		System.out.println(healthCard);
		try {
			intent.putExtra("hcKey", searchHC);
			startActivity(intent);
		} catch (Exception e) {
		}
	}
}
