package g0220.emergencyroom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Retrieves records saved in individual health card files to be seen on activity screen
 * 
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 * 
 *
 */

public class HCRecords extends Activity {

	/**
	 *  Integer representing a health card number
	 */
	private Integer hcn;
	
	/**
	 * Getter for hcn
	 * @return hcn instance variable
	 */
	public Integer getHcn() {
		return hcn;
	}

	/**
	 * Setter for hcn
	 * @param hcn Integer representing a health card number
	 */
	public void setHcn(Integer hcn) {
		this.hcn = hcn;
	}

	
	/**
	 * When a activity_hcrecords runs, this method is used to setup the layout and retrieve
	 * required information
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hcrecords);
		Intent intent = getIntent();
		Health_Card HC = (Health_Card) intent.getSerializableExtra("hcKey1");
		setHcn(HC.getHealthCardNumber());
		TextView Records = (TextView) findViewById(R.id.Records);
		try {
			Records.setText(getInfo());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * When a activity_hcrecords runs, this method is used to add items to the action bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hcrecords, menu);
		return true;
	}

	/**
	 * When a activity_hcrecords runs, this method is used handle action bar items
	 */
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
	 * This method retrieves information saved within the corresponding health card file, if it exists.
	 * The information is displayed in the activity screen
	 */
	public String getInfo() throws IOException{
		String hcnfile = getHcn().toString()+".txt";
		int ch;
	    StringBuffer strContent = new StringBuffer("");
	    String path = "data/data/g0220.emergencyroom/files/"+hcnfile;
	    File f = new File(path);
	    
	    String hcnprescripFile = getHcn().toString() + "prescrip.txt";
	    String pathprescrip = "data/data/g0220.emergencyroom/files/"+hcnprescripFile;
	    File prescripFile = new File(pathprescrip);
	    
	    if (f.exists()){
	    	try {
	    		FileInputStream fis = openFileInput(hcnfile);

	    		while((ch = fis.read()) != -1)
	    			strContent.append((char)ch);
	    	}
	    	catch (FileNotFoundException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }
	    else{
	    	strContent.append("No Previous Records Exist");
	    	
	    	}
	    
	    if (prescripFile.exists()){
	    	strContent.append("\nPRESCRIPTION RECORDS:\n"+ "\n");
	    	try {
	    		FileInputStream fis = openFileInput(hcnprescripFile);

	    		while((ch = fis.read()) != -1)
	    			strContent.append((char)ch);
	    	}
	    	catch (FileNotFoundException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }
	    else{
	    	strContent.append("\nNo Previous Prescription Records Exist");
	    }
	    
    return strContent.toString();
	}
	
	/**
	 * Takes the user back to the active health card
	 * @param view back to health card button
	 */
	public void backCard(View view){
		Intent intent = new Intent(this,HealthCardActivity.class);
		Health_Card searchHC = new Health_Card(this.getHcn());
		try {
			intent.putExtra("hcKey", searchHC);
			startActivity(intent);
		} catch (Exception e) {
		}
	}
	
}
