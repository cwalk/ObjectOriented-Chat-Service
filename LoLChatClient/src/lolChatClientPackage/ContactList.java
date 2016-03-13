package lolChatClientPackage;

import java.util.ArrayList;

import lolChatClientPackage.Contact;
import lolChatClientPackage.SpecialList;


/**
 * 
 * Collection of contacts known to the UserAccount.
 * 
 * @author Luis Gonzalez
 * @author Calyton Walker
 *
 */
public class ContactList {

	/*a collection of contacts, known to the UserAccount */
	
	//created ArrayList of special list
	public ArrayList<Contact> list;
	public SpecialList CourseList;
	public SpecialList FavoritesList;
	public SpecialList InstructorsList;
	public SpecialList SeniorList;
	public SpecialList JuniorList;
	public SpecialList SophomoreList;
	public SpecialList FreshmenList;
	
	
	/**
	 * Contructs a new array list of contacts.
	 */
	//Constructor
	public ContactList(){
		list = new ArrayList<Contact>();
	}
	
	/**
	 * Adds new contact based on year.
	 * @param contact
	 */
	public void addContact (Contact contact){
		list.add(contact);
		
		//Adding new contact. Need to import serializable and scanner
		/*System.out.println("Input First Name");
		String firstName = scn.next();*/
		
		if(contact.getYear().equalsIgnoreCase("Instructors"))
			list.add(contact);
		
		if(contact.getYear().equalsIgnoreCase("Senior"))
			list.add(contact);
		
		if(contact.getYear().equalsIgnoreCase("Junior"))
			list.add(contact);
		
		if(contact.getYear().equalsIgnoreCase("Sophomore"))
			list.add(contact);
		
		if(contact.getYear().equalsIgnoreCase("Freshmen"))
			list.add(contact);
		
	}
}

