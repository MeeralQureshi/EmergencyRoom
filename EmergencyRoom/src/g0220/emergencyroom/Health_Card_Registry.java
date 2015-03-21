package g0220.emergencyroom;

import java.util.ArrayList;

/**
 * Represents a database of health cards. Creates an ArrayList of Health_Card objects.
 * Allows an individual Health_Card object to be retrieved from the ArrayList
 * 
 * @author Monica Leonte, Meeral Qureshi, Owen Sawyer, Jordan Martel
 *
 */

public class Health_Card_Registry {
	
	
	private ArrayList<Health_Card> healthCards;
	
	/**
	 * Creates a list of Health Cards
	 */
	
	public Health_Card_Registry(){
		
		this.healthCards = new ArrayList<Health_Card>();
	}

	/**
	 * Adds a health card to the registry
	 * @param healthCard Health card to be added to the registry
	 */
	public void addHealthCard(Health_Card healthCard){
		this.healthCards.add(healthCard);
	}
	
	
	/**
	 * Creates a new health card 
	 * @param cardNumber The card to create
	 */
	
	public Health_Card createHealthCard(int cardNumber){
		Health_Card healthCard = new Health_Card(cardNumber);
		return healthCard;
	}
	
	/**
	 * Checks whether a card exists in the registry
	 * @param cardNumber the card number to check for
	 * @return true if the card exists, false if it doesn't
	 */
	
	public boolean cardExists(int cardNumber){
		return this.healthCards.contains(cardNumber);
	}
	
	/**
	 * Retrieves a card from the registry using the Patient's health card number.
	 * Throws an exception if the registry doesn't exist, has no health cards or there is no health card with the given number.
	 * 
	 * @param cardNumber Patient's health card number
	 * @return A patient's Health Card
	 * @throws InvalidRegistryException
	 * @throws HealthCardNotFoundException
	 */
	
	public Health_Card getCard(int cardNumber) throws HealthCardNotFoundException, InvalidRegistryException{
		if (this.healthCards == null){
			throw new InvalidRegistryException();
		} else if (this.healthCards.size() == 0){
			throw new InvalidRegistryException();
		} else {
			for (Health_Card hc: this.healthCards){
				if (hc.getHealthCardNumber() == cardNumber){
					return hc;
				}
			}
		}
		throw new HealthCardNotFoundException();
	}
}