package lolChatClientPackage;

import java.io.Serializable;

/**
 * Stores basic contact information about the user.
 * 
 * @author Luis Gonzalez
 * @author Clayton Walker
 *
 */

public class Contact implements Serializable {

	/*contains basic contact information about the user. 
	 * Among the information one can find, the PID, firstName, lastName, 
	 * year, and program (e.g. CS, IT, CpE, EE, etc.)
	 */
	
	private static final long serialVersionUID = 1L;
	//Fields in Contact class
	private String PID;
	private String firstName;
	private String lastName;
	private String year;
	private String program;
	private String type;
	
	/**
	 * Contructs a new contact object. 
	 * 
	 * @param PID
	 * @param firstName
	 * @param lastName
	 * @param year
	 * @param program
	 * @param type
	 */
	//Contact Constructor
	public Contact(String PID, String firstName, String lastName, String year, String program, String type) {
		this.PID = PID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.year = year;
		this.program = program;
		this.type = type;
	}

	//Generated Getters and Setters for fields
	/**
	 *
	 * @return PID
	 */
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	
	/**
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * 
	 * @return year
	 */
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * 
	 * @return program
	 */
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	
	/**
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

