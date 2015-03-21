package g0220.emergencyroom;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * Represents a Patient in the Emergency Room
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 *
 */

public class Patient implements Comparable<Object>, Serializable {

	
	/**
	 * Serial ID for Patient
	 */
	private static final long serialVersionUID = 4470842967220490038L;
	/**
	 * Contains a patient's vital signs
	 */
	public ArrayList<Integer> vitals;
	/**
	 * Contains a patient's old vital signs (when a patient has their vitals updated)
	 */
	public Set<ArrayList<Integer>> oldVitals;
	/**
	 * Contains all update times
	 */
	public ArrayList<Integer> timeOfLastUpdate;
	/**
	 * Times when patient's vitals were updated
	 */
	public Set<ArrayList<Integer>> setOfTimes;
	/**
	 * Contains arrival time of patient
	 */
	private String arrivalTime;
	/**
	 * Contains the health card number of the patient
	 */
	private Integer hc;
	/**
	 * Patient's name
	 */
	private String name;
	/**
	 * Patient's date of birth
	 */
	private String dob;
	/**
	 * Patient's age (for future use)
	 */
	private Integer age;
	
	private Integer urgency;
	
	/**
	 * 
	 * Creates a new Patient Object using given parameters
	 * 
	 * @param hc the health card number of the Patient
	 * @param name the name of the Patient
	 * @param dob the date of birth of the Patient
	 * @param hcr a valid Health_Card_Registry
	 */
	public Patient(Integer hc, String name, String dob) {
        this.setName(name);
        this.setHc(hc);
        this.setDob(dob);
        this.setAge(calculateAge(dob));
		this.vitals = new ArrayList<Integer>();
		this.arrivalTime = "Not checked in";
		this.urgency = null; //Added Urgency field + getters and setters
		
	}

	public Integer getUrgency() {
		return urgency;
	}

	public void setUrgency(Integer urgency) {
		this.urgency = urgency;
	}

	/**
	 * Returns the patient's health card number
	 * @return Patient's Health Card Number
	 */
	public Integer getHc() {
		return hc;
	}
	
	/**
	 * Set the new health card number
	 * @param hc patient's new health card number
	 */
	public void setHc(Integer hc) {
		this.hc = hc;
	}
	/**
	 * Get the patient's name
	 * @return the patient's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the patient's new name
	 * @param name new name for patient
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the patient's date of birth
	 * @return the patient's date of birth
	 */
	public String getDob() {
		return dob;
	}
	
	/**
	 * Set the patient's new date of birth
	 * @param dob new date of birth
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	/**
	 * Get the patient's age
	 * @return the patient's age
	 */
	public Integer getAge() {
		int age = this.calculateAge(getDob());
		this.setAge(age);
		return this.age;
	}
	
	/**
	 * Set the patient's new age
	 * @param age patient's new age
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	
	/**
	 * Patients are comparable by Urgency
	 */
	public int compareTo(Object obj) {
		if (((Patient) obj).getUrgency() == this.getUrgency())
			return 0;
		else if (((Patient) obj).getUrgency() > this.getUrgency())
			return -1;
		else
			return 1;
	}
	/**
	 * Calculates the age of the Patient using the Patient's date of birth
	 * (For future use)
	 * 
	 * @param dob the date of birth of the Patient
	 * @return Integer representing the age of the Patient
	 */
	@SuppressLint("SimpleDateFormat")
	public Integer calculateAge(String dob) {
		Date todaysDate = new Date();
		Date dateOfBirth = new Date();
		Integer age = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar todaysDateCalendar = new GregorianCalendar();
		Calendar dateOfBirthCalendar = new GregorianCalendar();

		try {
			dateOfBirth = format.parse(dob);
		} catch (ParseException e) {
			System.out.print("Date of birth not parceable as " + format);
			e.printStackTrace();
		}
		todaysDateCalendar.setTime(todaysDate);
		dateOfBirthCalendar.setTime(dateOfBirth);
		age = todaysDateCalendar.get(Calendar.YEAR) - dateOfBirthCalendar.get(Calendar.YEAR);
		if (dateOfBirthCalendar.get(Calendar.MONTH) > todaysDateCalendar.get(Calendar.MONTH)){
			age--;
		}
		else if (dateOfBirthCalendar.get(Calendar.MONTH) == todaysDateCalendar.get(Calendar.MONTH)){
			if (dateOfBirthCalendar.get(Calendar.DATE) > todaysDateCalendar.get(Calendar.DATE)){
			    age--;
			}
		}
        
		return age;
	}
	/**
	 * Stores the arrival time of the Patient using given parameters
	 * 
	 * @param arrivalTimeMinutes the minutes after the hour the Patient arrived
	 * @param arrivalTimeHour the hour the Patient arrived
	 */
	public void checkIn(String arrivalTime){
		
		this.setArrivalTime(arrivalTime);
	}
	
	/**
	 * Gets the time of the last update
	 * @return time of the last update
	 */
	public ArrayList<Integer> getTimeOfLastUpdate() {
		return this.timeOfLastUpdate;
	}
	
	/**
	 * Gets the set of times
	 * @return the set of times
	 */
	public Set<ArrayList<Integer>> getSetOfTimes() {
		return this.setOfTimes;
	}

	/**
	 * Returns the arrival time minutes
	 * @return the arrival time minutes
	 */
	public String getArrivalTime() {
		return this.arrivalTime;
	}

	
	/**
	 * Sets the hour of arrival
	 * @param arrivalTimeHour hour of arrival
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * Gets the a list of the latest vitals
	 * @return the latest vitals
	 */
	public ArrayList<Integer> getVitals() {
		return this.vitals;
	}
	
	/**
	 * Gets a list of old vitals
	 * @return the older vitals
	 */
	public Set<ArrayList<Integer>> getOldVitals() {
		return this.oldVitals;
	}
	
	/**
	 * Returns a string representation of the Patient
	 * @return the Patient as a string
	 */
	public String toString(){
		return (String.valueOf(this.hc) + ": " + this.name + ", " + this.dob);
		
	}

}
