package twitterPackage;

import java.util.*;

public class User extends Subject{
	private List<Subject> followers;
	private List<Subject> following;
	private List<Tweet> feed;
	
	//the number of tweets that is by the user. Different from the items in news feed.
	private int numOfTweets;



	public User(String Id) {
		super(Id);
		numOfTweets = 0;
		followers = new ArrayList<Subject>();
		following = new ArrayList<Subject>();
		feed = new ArrayList<Tweet>();		
	}
	
	
	public List<Subject> getFollowers() {
		return followers;
	}
	public void addFollower(Subject follow) {
		followers.add(follow);
		setUpdateTime(); //Assignment 3
	}
	public void addFollowing(Subject follow) {
		following.add(follow);
		setUpdateTime(); //Assignment 3
	}
	public void setFollowers(ArrayList<Subject> followers) {
		this.followers = followers;
		setUpdateTime(); //Assignment 3
	}
	public List<Subject> getFollowing() {
		return following;
	}
	public void setFollowing(ArrayList<Subject> following) {
		this.following = following;
		setUpdateTime(); //Assignment 3
	}
	public void tweetMessage(Tweet t) {
		feed.add(t);
		setUpdateTime(); //Assignment 3
	}


	public List<Tweet> getNewsFeed() {
		return feed;
		
	}
	
	public void addFeed(Tweet t) {
		feed.add(t);
		//if the tweet was done by this user, add the number of tweets
		if(t.getUser().getDisplayName().equals(this.getDisplayName())) 
			numOfTweets++;
		setUpdateTime(); //Assignment 3

	}
	
	public void setFeed(ArrayList<Tweet> feed) {
		this.feed = feed;
		setUpdateTime(); //Assignment 3
	}
	public int getNumberOfTweets() {
		return numOfTweets;
	}
	@Override
	public void accept(SysEntryVisitor visitor) {
		visitor.visitUser();
	}

//
//	@Override
//	public void update(Subject subject) {
//		if(subject instanceof User) {
//			followers = ((User) subject).getFollowers();
//			following = ((User) subject).getFollowing();
//			feed = ((User) subject).getNewsFeed();
//		}
//		// TODO Auto-generated method stub
//		
//	}
//	



	

}
