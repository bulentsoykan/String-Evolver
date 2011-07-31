//
//  StringOrganism.java
//  
//
//  Created by Roberto Francés on 12/01/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

//package korovasoft;

public class StringOrganism {
	public StringOrganism parent;
	public StringBuffer genome;
	public int fitness_score;
	public boolean has_parent = true;
	
	StringOrganism() {
		genome = new StringBuffer();
	}
	
	StringOrganism(StringOrganism clone) {
		genome = new StringBuffer(clone.genome);
	}
}
