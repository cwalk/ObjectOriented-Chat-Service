package lolChatClientPackage;

import lolChatClientPackage.ContactList;

/**
 * Contains elements of a given category from the ContactList
 * 
 * @author Luis Gonzalez
 * @author Clayton Walker
 *
 */

public class SpecialList extends ContactList {

	/*a ContactList that contains elements from a given category only, 
	 * such as:
	 * CourseList, FavoritesList, InstructorsList, SeniorList, 
	 * JuniorList, SophmoreList, FreshmenList, etc. 
	 * Hence, a way to distinguish them is needed, 
	 * for example a String name or enum Category, so forth
	 */
	public String type;
	
	/**
	 * Assigns the type of category the list will be comprised of.
	 * 
	 * @param type	This can be (Favorites, Instructors, Seniors, and so on)
	 */
	public SpecialList(String type){
		super();
		this.type = type;
	}
}
