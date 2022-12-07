package twitterPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class UserView {

	private JFrame frame;
	private JTextField textField_1;
	private JTextField textField;
	//Followers list
	private String listFollowings;
	private JTextArea followersListTxt;
	//NewsFeed list
	private String listFeed;
	private JTextArea FeedListTxt;
	private JTextField textField_2;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UserView window = new UserView();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public UserView(User user, Map<String, User> users) {
		initialize(user, users);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User user, Map<String ,User> users) {		
		HashMap<String,User> currentUsers = (HashMap<String, User>) users;
		User current = user;
		
		listFollowings = user.getDisplayName() + " Following(s): ";
		for(Subject u: current.getFollowing()) {
			listFollowings += "\n - " + u.getDisplayName();
		}
		
		listFeed =  user.getDisplayName() + " News Feed: ";
		for(Tweet s: current.getNewsFeed()) {
			listFeed += "\n - " + s.getUser().getDisplayName() + ": " + s.getMessage();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 370, 434);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Text field to add people to following
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(10, 11, 199, 29);
		frame.getContentPane().add(textField);
		
		//Text area for followings
		
		
		followersListTxt = new JTextArea(listFollowings);
		followersListTxt.setBounds(10, 51, 329, 91);
		frame.getContentPane().add(followersListTxt);
		
		/*
		 * Followers List
		 */
		JButton followUser = new JButton("Follow User");
		followUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = textField.getText();
				if(txt == "" || users.get(txt)== null) {
					JOptionPane.showMessageDialog(null, "Invalid Input, text is null or the user you're trying to follow doesn't exist");
				}else {
					User follower = users.get(txt);
					listFollowings += "\n - " + follower.getDisplayName();
					current.addFollowing(follower);
					follower.addFollower(current);
					
					//Updating adminControlPanel users database
					AdminControlPanel.getInstance().update(current);
					AdminControlPanel.getInstance().update(follower);
					
					//updating the following list
					followersListTxt.setText(listFollowings);
								
				}
			}
		});
		followUser.setBackground(new Color(128, 255, 255));
		followUser.setBounds(219, 11, 120, 26);
		frame.getContentPane().add(followUser);
		
		//Text Area to view the feed
		FeedListTxt = new JTextArea(listFeed);
		FeedListTxt.setBounds(10, 193, 329, 110);
		frame.getContentPane().add(FeedListTxt);
		
		//Text field to tweed and add to feed
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 153, 199, 29);
		frame.getContentPane().add(textField_1);
		
		JButton postTweetBtn = new JButton("Post Tweet");
		postTweetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//TODO add tweet
				String message = textField_1.getText();
				if(message == "") {
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}else {
					Tweet currentTweet = new Tweet(current, message);
					current.addFeed(currentTweet);
					
					//updating current user's feed with their tweet in database
					AdminControlPanel.getInstance().update(current);
					for(Subject u : current.getFollowers()) {
						if(u instanceof User) {
							((User)u).addFeed(currentTweet);
							//updating the user's followers with the user's tweets
							AdminControlPanel.getInstance().update(u);
						}
					}
					listFeed += "\n - " + user.getDisplayName() + ": " + message;
					FeedListTxt.setText(listFeed);
				}
			
			}
		});
		postTweetBtn.setBackground(new Color(255, 128, 128));
		postTweetBtn.setBounds(219, 153, 120, 29);
		frame.getContentPane().add(postTweetBtn);
		
		//Assignment 3 showing creation time *******
		textField_2 = new JTextField();
		textField_2.setText(user.getDisplayName() +" creationTime: " + user.getCreationTime());
		textField_2.setBounds(22, 332, 279, 29);
		textField_2.setColumns(10);
		textField_2.setEditable(false);
		frame.getContentPane().add(textField_2);
		
		
		
		frame.setVisible(true);
	}
}
