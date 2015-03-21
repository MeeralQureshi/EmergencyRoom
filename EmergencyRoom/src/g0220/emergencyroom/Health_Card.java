package g0220.emergencyroom;


import java.io.Serializable;

/**
 * Represents a Patient's health card. Accesses information stored in individual 
 * health card files to receive patient information.
 * 
 * 
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 *
 */

public class Health_Card implements Serializable {

	
	/**
	 * The serial ID for a Health_Card object
	 */
	private static final long serialVersionUID = 2770218534082339077L;
	/**
	 * Integer representing a health card number
	 */	
	private Integer healthCardNumber;
	
	
	/**
	 * Creates a new health card
	 * 
	 * @param healthCardNumber Patient's health card number
	 */
	
	public Health_Card(Integer healthCardNumber){
		this.setHealthCardNumber(healthCardNumber);
	}
	
	/**
	 * Get's the health card number for this card
	 * @return health card number
	 */
	public Integer getHealthCardNumber() {
		return healthCardNumber;
	}
	
	/**
	 * Set the new health card number for the card
	 * @param healthCardNumber new health card number
	 */
	public void setHealthCardNumber(Integer healthCardNumber) {
		this.healthCardNumber = healthCardNumber;
	}
			
}