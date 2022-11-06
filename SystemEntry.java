package twitterPackage;

import javax.swing.tree.DefaultMutableTreeNode;

//superclass for User and group
public class SystemEntry implements Visitable{
	private String id;
	
	public SystemEntry(String id) {
		this.id = id;
	}
	
	public String getDisplayName() {
		return id;
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
