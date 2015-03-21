package g0220.emergencyroom;

import java.util.ArrayList;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class SortPatientsActivity extends Search {

	/**
	 * Generates buttons on screen based on the list of urgent patients and displays the list
	 * of patients in order of decreasing urgency.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort_patients);
		Intent sortedIntent = getIntent(); //Get list from previous view and list them
		@SuppressWarnings("unchecked")
		ArrayList<Patient> sortedList = (ArrayList<Patient>) sortedIntent.getSerializableExtra("sortedList");
		LinearLayout supertesting1 = (LinearLayout) findViewById(R.id.supertesting1);
		LayoutParams param = new LinearLayout.LayoutParams(
		LayoutParams.MATCH_PARENT, 120, 1.0f);
		Button[] btn = new Button[sortedList.size()];
		for (int i = 0; i < sortedList.size(); i++) { //for each patient, create a button
		    btn[i] = new Button(getApplicationContext());
		    btn[i].setText(sortedList.get(i).toString()+"\n"+ "Urgency:" + sortedList.get(i).getUrgency());
		    btn[i].setTextColor(Color.parseColor("#000000"));
		    btn[i].setTextSize(20);
		    btn[i].setHeight(50);
		    btn[i].setLayoutParams(param);
		    btn[i].setPadding(15, 5, 15, 5);
		    supertesting1.addView(btn[i]);

		    btn[i].setOnClickListener(handleOnClick(btn[i]));   
		}
	}
	
	/**
	 * Assign the proper onclick function (to the appropriate HealthCardActivity) 
	 * based on which button was pressed.
	 * 
	 * @param button, the Button being clicked.
	 */
    public View.OnClickListener handleOnClick(final Button button) {
		return new View.OnClickListener() {
		    public void onClick(View v) {
		    	Intent intent = new Intent(SortPatientsActivity.this,HealthCardActivity.class);
		    	String text = (String) button.getText();
		    	String[] parts = text.split(":");
		    	Health_Card HC = new Health_Card(Integer.parseInt(parts[0]));
				intent.putExtra("hcKey", HC);
				startActivity(intent);
		    }
		};
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sort_patients, menu);
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
}
