import java.util.Scanner;

public static TreeMap<String, Object> table modifyAttr(TreeMap<String, Object> table){
		System.out.println("Give the table name that contains the attribute: ");
		String t_name = input.next();
		boolean checkTab = false;
		
		while(checkTab= false) {
			for (int i = 0; i < listoftables.size(); i++){
				if (tableobj.listoftables.contains(t_name)) {
					checkTab = true; // checks if the given name of the table exists
				
				}
			}	
				if(checkTab = false && i = listoftables.size() ) {
					System.out.println("The given name of table does not exist give another name:");
					String t_name = input.next();
					
				}
			
		}
		boolean checkAttr = false; //checks if the attribute name exists
		System.out.println("Give the attribute that you want to change: ");
		String attr_name = input.next();
		while(checkAttr = false) {
			for(int i=0; i < tableobj.listoftables.t_name.size(); i++) {
				if(tableobj.listoftables.contains(attr_name)) {
					checkAttr = true;
					int indexOfAtribute = i;
				}
			}
			if(checkAttr = false && i= tableobj.listoftables.t_name.size()) {
				System.out.println("The given name of attribute does not exist give another name:");
				String attr_name = input.next();
			}
		} 
		System.out.println("Give the new value: ");
		attr_value = object input ; 
        list.put(attr_value,indexOfAtribute);
		return (list);
}