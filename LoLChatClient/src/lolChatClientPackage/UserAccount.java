package lolChatClientPackage;

/**
 * 
 * @author Luis Gonzalez
 * @author Clayton Walker
 * 
 * Holds the contact information of the user along
 * with it's type (student, faculty, staff).
 *
 */
public class UserAccount {

	/*holds the Contact information of the user, 
	 * along with its type (student, faculty, staff)
	 */		
	
	//fields
	private String firstName;
	private String lastName;
	public String type;
	private ContactList contactList;
	
	/**
	 * Constructs a new UserAccount
	 * 
	 * @param fName 	FirstName
	 * @param lName 	LastName
	 * @param type  	(student, faculty, or staff)
	 */
	//userAccount Constructor
	public UserAccount(String fName, String lName, String type) {
		
		this.firstName = fName;
		this.lastName = lName;
		this.type = type;
	}
	
	/**
	 * 
	 * @return contactList	
	 */
	public ContactList getContactList() {
		return contactList;
	}
	public void setContactList(ContactList contactList) {
		this.contactList = contactList;
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
	 * Returns the type of UserAccount
	 * 
	 * @return type (Student, faculty, staff)
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

