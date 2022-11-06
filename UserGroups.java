package twitterPackage;

import java.util.ArrayList;
import java.util.List;

public class UserGroups extends SystemEntry{
	
	private List<SystemEntry> people;
	UserGroups(String id){
		super(id);
		people = new ArrayList<SystemEntry>();
	}
	
	public void add(SystemEntry e) {
		people.add(e);
	}

	public List<SystemEntry> getPeople() {
		return people;
	}
	public void setPeople(List<SystemEntry> people) {
		this.people = people;
	}
	
	public SystemEntry findPerson(String per){
		for(SystemEntry each: people) {
			if(each.getDisplayName().equals(per)) {
				return each;
			}
		}
		return null;
	}


}
