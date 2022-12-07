package twitterPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;


/*
 * the main UI for the simple twitter
 * 
 */

public class AdminControlPanel implements Observer {
	
	//singleton pattern
	private static AdminControlPanel instance;
	
	//Tree selection
	private DefaultMutableTreeNode currentNode;
	private DefaultMutableTreeNode addNode;

	private JFrame frame;
	private JTree tree;
	private JTextField addUserText;
	private JTextField addGroupText;
	
	
	private UserGroups root;
	private UserGroups currentGroup;
	private User defaultUser;
	
	//My dataBase for Users and UserGroups
	private HashMap<String, User> users;
	
	//Database Getters for visitors
	public HashMap<String, User> getUsers() {
		return users;
	}
	public void setUsers(HashMap<String, User> users) {
		this.users = users;
	}
	public Map<String, UserGroups> getGroups() {
		return groups;
	}
	public void setGroups(Map<String, UserGroups> groups) {
		this.groups = groups;
	}


	private Map<String, UserGroups> groups;


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AdminControlPanel window = new AdminControlPanel();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	public static AdminControlPanel getInstance() {
		if(instance == null) {
			instance = new AdminControlPanel();
		}
		return instance;
	}

	/**
	 * Create the application.
	 */
	private AdminControlPanel() {
		root = new UserGroups("root");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		users= new HashMap<String, User>();
		groups = new HashMap<String, UserGroups>();
		groups.put("root" , root);
		frame = new JFrame("Simple Twitter");
		frame.setBounds(100, 100, 710, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tree = new JTree();
		tree.setModel(new DefaultTreeModel(
				 new DefaultMutableTreeNode("root") {
					{
					}
				}
			));
		tree.setBounds(10, 11, 231, 359);
		frame.getContentPane().add(tree);
		
		addUserText = new JTextField();
		addUserText.setBounds(272, 19, 182, 30);
		frame.getContentPane().add(addUserText);
		addUserText.setColumns(10);
		
		addGroupText = new JTextField();
		addGroupText.setBounds(272, 69, 191, 30);
		frame.getContentPane().add(addGroupText);
		addGroupText.setColumns(10);
		
		JButton userTotalBtn = new JButton("Show User Total");
		userTotalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Number of users: " + users.size());

			}
		});
		userTotalBtn.setBounds(283, 254, 191, 57);
		frame.getContentPane().add(userTotalBtn);
		
		//User Add
		JButton addUserBtn = new JButton("Add User");
		addUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selected = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				if(!selected.getAllowsChildren()) {
					JOptionPane.showMessageDialog(null, "This is a User, it does not have children");
				}
				
				if(addUserText.getText() == null && users.get(addUserText.getText()) == null) {
					JOptionPane.showMessageDialog(null, "Invalid Input");

				}else {
					System.out.println(selected.toString());
					User newUser = new User(addUserText.getText());
					if(defaultUser == null) defaultUser = newUser;
					groups.get(selected.toString()).add(newUser);//putting people in groups
				
					users.put(newUser.getDisplayName(), newUser);
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(addUserText.getText());
					newNode.setAllowsChildren(false);
					selected.add(newNode);
					DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
					model.reload();
					
					//Assignment 3 showing creation time
					JOptionPane.showMessageDialog(null, newUser.getDisplayName() +" creationTime: " + newUser.getCreationTime());
				}
				
			}
		});
		addUserBtn.setBounds(490, 11, 168, 47);
		frame.getContentPane().add(addUserBtn);
		
		//Group Add
		JButton addGroupBtn = new JButton("Add Group");
		addGroupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selected = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				if(!selected.getAllowsChildren()) {
					JOptionPane.showMessageDialog(null, "This is a User, it does not have children");
				}
				if(addGroupText.getText() == "") {
					JOptionPane.showMessageDialog(null, "Invalid Input");

				}else {
					groups.put(addGroupText.getText(), new UserGroups(addGroupText.getText()));
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(addGroupText.getText());
					newNode.setAllowsChildren(true);
					selected.add(newNode);
					DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
					model.reload();
				}
			}
		});
		addGroupBtn.setBounds(490, 69, 168, 30);
		frame.getContentPane().add(addGroupBtn);
		
		//Group Total JOption Pane
		JButton groupTotalBtn = new JButton("Show Group Total");
		groupTotalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Number of Groups: " + groups.size());
			}
		});
		groupTotalBtn.setBounds(484, 254, 174, 57);
		frame.getContentPane().add(groupTotalBtn);
		
		//Total number of Messages in a JOptionP Pane
		JButton showMessagesTotalBtn = new JButton("Show Messages Total");
		showMessagesTotalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				UserMessagesVisitor visitor = new UserMessagesVisitor();
				defaultUser.accept(visitor);
				
			}
		});
		showMessagesTotalBtn.setBounds(283, 322, 191, 48);
		frame.getContentPane().add(showMessagesTotalBtn);
		
		//Positive Percent View
		JButton positivePercentBtn = new JButton("Show Positive Percent");
		positivePercentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PositiveVisitor visitor = new PositiveVisitor();
				defaultUser.accept(visitor);			}
		});
		positivePercentBtn.setBounds(484, 323, 168, 47);
		frame.getContentPane().add(positivePercentBtn);
		
		//Opening up user view
		JButton viewBtn = new JButton("User View ");
		viewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selected = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				User currentUser = users.get(selected.toString());
				if(currentUser == null) {
					JOptionPane.showMessageDialog(null, "Invalid Input, Pick a User Node");
				}
				else {
					UserView a = new UserView(currentUser, users);
				}
				//create a new JFrame that updates the user
			}
		});
		viewBtn.setBounds(323, 122, 296, 30);
		frame.getContentPane().add(viewBtn);
		
		//TODO Delete when done
		JButton btnNewButton = new JButton("DebugTest");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selected = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				UserGroups currentGroup = groups.get(selected.toString());
				System.out.println("This is the people in the " + currentGroup.getDisplayName()+ "group: ");
				for(SystemEntry a: currentGroup.getPeople()) {
					if(a instanceof User) {
						System.out.println(" " + a.getDisplayName());
					}
					
				}
				
			}
		});
		btnNewButton.setBounds(272, 209, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		//Assignment 3 ID Verification********
		JButton verificationBtn = new JButton("ID Verification");
		verificationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<SystemEntry> all = new ArrayList<SystemEntry>();
//				List<User> u = new ArrayList<User>(users.values());
				List<UserGroups> g = new ArrayList<UserGroups>(groups.values());
				all.addAll(users.values());
				all.addAll(groups.values());
				boolean validation = true;
				/*due to the hashmap used, there cannot be multiple users with the same name nor groups,
					but users and groups could have the same name*/
				for(UserGroups gr : g)
				{
					//checking each group to see if it has that same name as one of the users
					if(users.containsKey(gr.getDisplayName())) validation = false;
				}
				for(SystemEntry current : all) 
				{
					if(current.getDisplayName().contains(" ")) validation = false; //if it contains a space
				}
				
				if(validation == true) 	JOptionPane.showMessageDialog(null, "Valid users and group IDs");
				else JOptionPane.showMessageDialog(null, "Invalid users and group IDs"); 
			}
		});
		
		
		verificationBtn.setBounds(434, 209, 89, 23);
		frame.getContentPane().add(verificationBtn);
		
		//Assignment 3, getting the last updated user *********
		JButton lastUserBtn = new JButton("LastUpdatedUser");
		lastUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<User> u = new ArrayList<User>(users.values());
				User userLast = null;
				for(User a : u) {
					if(userLast == null) userLast = a;
					//if the current user a has a later update time change the userLast to a;
					else if(userLast.getUpdateTime() < a.getUpdateTime()) userLast = a;
				}
				JOptionPane.showMessageDialog(null, "Last Updated User is " + userLast.getDisplayName());	
				
			}
		});
		lastUserBtn.setBounds(576, 209, 89, 23);
		frame.getContentPane().add(lastUserBtn);
		
		frame.setVisible(true);
	}


	//Observer pattern. UserView is the subject and that subject updates the local database.
	@Override
	public void update(Subject subject) {
		if(subject instanceof User) users.put(subject.getDisplayName(), (User) subject);
	}
}
