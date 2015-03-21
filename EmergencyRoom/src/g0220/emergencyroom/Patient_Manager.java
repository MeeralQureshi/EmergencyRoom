
package g0220.emergencyroom;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import android.content.Context;



/**
 * 
 * The Patient_Manager allows the user to access multiple Patient objects at a time. On app launch, this object is created
 * in order to correctly setup all required information
 * 
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 *
 */


public class Patient_Manager implements Serializable {
	private static final long serialVersionUID = -6414352092190872476L;
	private ArrayList<Patient> patientList;
	private Health_Card_Registry registry;
	private static Context testcontext;
	
	/**
	 * Creates a new Patient_Manager object
	 * @throws IOException 
	 */
	
	public Patient_Manager(Context context) throws IOException{
		//if file not on device, read from assets and write to device & read from it
		//else, just read from it
		this.patientList = new ArrayList<Patient>();
		this.registry = new Health_Card_Registry();
		testcontext = context;
		String path = "data/data/g0220.emergencyroom/files/patient_records.txt";
	    File f = new File(path);
	    //System.getProperty("line.separator");
	    //System.lineSeparator();
	    //String.format("%n");
		if (!f.exists()){
		  
			InputStream is;
			is = testcontext.getAssets().open("patient_records.txt");
			Scanner scanner = new Scanner(is);
			String datacopy = "";
			while (scanner.hasNextLine()) {
				datacopy += (scanner.nextLine()+System.getProperty("line.separator"));
			}
			scanner.close();	//copy the info into a string from the assets file
			FileOutputStream outputStream = testcontext.openFileOutput("patient_records.txt", Context.MODE_APPEND);
			try {
				outputStream.write(datacopy.getBytes());  //write into the new patient_records we will use
				outputStream.close();
			} catch (IOException e){
				//Handle e
			}
		}
		InputStream is = new FileInputStream(new File("data/data/g0220.emergencyroom/files/patient_records.txt"));
		Scanner scanner = new Scanner(is);
		while (scanner.hasNextLine()) {
			//Split each line into different parts
			String[] parts = scanner.nextLine().split(",");
			//create a new patient using the parts of the line
			Patient p = new Patient(Integer.parseInt(parts[0]), parts[1], parts[2]);
			//add that patient to the list
			this.patientList.add(p);
		}
		scanner.close();
		Collections.sort(patientList);
		this.generateRegistry();
		/*File file = new File("jefferson_stats.txt");
	    file.createNewFile();

	    writer = new BufferedWriter(new FileWriter(file));

	    writer.write("Number of words: " + word );
	    writer.newLine();*/
	}
	
	/**
	 * Generates a registry with pre-made health cards
	 * @return a pre-made registry
	 */
	public Health_Card_Registry generateRegistry(){
		ArrayList<Patient> patientList = this.getPatientList();
		Health_Card_Registry registry = this.registry;
		for(Patient p: patientList){
			Health_Card healthCard = registry.createHealthCard(p.getHc());
			registry.addHealthCard(healthCard);
		}
		this.setRegistry(registry);
		return this.getRegistry();
	}
	

	/**
	 * Gets the list of patients
	 * @return a list of the patients
	 */
	public ArrayList<Patient> getPatientList() {
		return patientList;
	}
	
	/**
	 * Set's a new list of patients
	 * @param patientList new list of patients
	 */
	public void setPatientList(ArrayList<Patient> patientList) {
		this.patientList = patientList;
	}
	
	/**
	 * Returns the health card registry
	 * @return the health card registry
	 */
	public Health_Card_Registry getRegistry() {
		return registry;
	}
	
	/**
	 * Set's the new health card registry
	 * @param registry new health card registry
	 */
	public void setRegistry(Health_Card_Registry registry) {
		this.registry = registry;
	}
	
	/**
	 * Searches through the list of patients by health card number
	 * @param healthCardNumber the health card number to be searched
	 * @return
	 */
	public Patient searchPatientHC(Integer healthCardNumber){
		ArrayList<Patient> patientList = this.getPatientList();
		for(Patient p: patientList){
			if(p.getHc().equals(healthCardNumber)){
				return p;
			}
		}
		return null;
	}
	
}