package twitterPackage;

import javax.swing.tree.DefaultMutableTreeNode;

//superclass for User and group
public class SystemEntry implements Visitable{
	private String id;
	
	//Assignment 3 addition of creation time and update time to user and group *************
	private long creationTime;
	private long updateTime;
	
	public SystemEntry(String id) {
		this.id = id;
		creationTime = System.currentTimeMillis();
		updateTime = creationTime;
	}
	
	public String getDisplayName() {
		return id;
	}
	
	public long getUpdateTime()
	{
		return updateTime;
	}
	public long getCreationTime()
	{
		return creationTime;
	}
	
	public void setUpdateTime()
	{
		updateTime = System.currentTimeMillis();
	}
	
	@Override
	public String toString() {
		return getDisplayName();
	}


	@Override
	public void accept(SysEntryVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
}
