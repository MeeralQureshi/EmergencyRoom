package g0220.emergencyroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class VitalsUpdateSuccess extends Search {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vitals_update_success);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vitals_update_success, menu);
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
	 * Takes the user back to the Health Card search
	 * @param view the back button clicked
	 */
	public void searchBack(View view){
		Intent intent = new Intent(this, Search.class);
		startActivity(intent);
	}
	
	
	/**
	 * Takes user back to the active health card.
	 * @param view Back button
	 */
	public void backCard(View view){ 
		Intent intent = new Intent(this,HealthCardActivity.class);
		Intent oldIntent = getIntent();
		Integer healthCard = (Integer) oldIntent.getSerializableExtra("healthCardNumber");
		Health_Card searchHC = new Health_Card(healthCard);
		try {
			intent.putExtra("hcKey", searchHC);
			startActivity(intent);
		} catch (Exception e) {
		}
	}
}
