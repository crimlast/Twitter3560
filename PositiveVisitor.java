package twitterPackage;

import java.util.HashMap;

import javax.swing.JOptionPane;

public class PositiveVisitor extends SysEntryVisitor {
	private int count = 0;
	   String[] posWord = {"good", "great", "nice",
				"awesome"};

	@Override
	public void visitUser() {
		
		HashMap<String, User> users = (HashMap<String, User>) AdminControlPanel.getInstance().getUsers();

		users.forEach((k,v) -> 
		   	count += currentUsersPositive(v));
		
		JOptionPane.showMessageDialog(null, "There are (" + count +") tweets that are positive in total" );


	}
	
	//return the number of tweets the user has with positive 
	public int currentUsersPositive(User user) {
		int solution = 0;
		for(Tweet t: user.getNewsFeed()) {
			//Checking to see the feed if its the current user's tweet
			if(t.getUser().getDisplayName().equals(user.getDisplayName())) {
				String m = t.getMessage();
				if(m.contains(posWord[0]) || m.contains(posWord[1]) || m.contains(posWord[2]) ||
						m.contains(posWord[3])) {
					solution++;
				}
			}


		}
		
		return solution;
	}

}
