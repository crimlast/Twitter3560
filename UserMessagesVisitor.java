package twitterPackage;

import java.util.*;

import javax.swing.JOptionPane;

public class UserMessagesVisitor extends SysEntryVisitor{
	private int solution = 0;

	@Override
	public void visitUser() {
		HashMap<String, User> users = (HashMap<String, User>) AdminControlPanel.getInstance().getUsers();
		
		users.forEach((k,v) -> solution += v.getNumberOfTweets());
		
		JOptionPane.showMessageDialog(null, "There are (" + solution +") tweets in total" );

		
	}

}
