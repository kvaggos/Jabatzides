import java.lang.Object;
public Table deleteAttr () {
	boolean findTable = true;
	Table tableobj = null;
	String name;
	System.out.println("Give the table's name which contains the attribute you want to delete or EXIT to exit:");
	while(findTable){
		name = sc.nextLine();
		
		if(name.equals("EXIT")) {
			findTable = false;
		} else {
			tableobj = getTable(name);
			if ( tableobj == null){
   						System.out.println("Table " + name + " does not exists");
   			} else {
   						findTable = false;
   			}	
   				
		}
	}	
	
	boolean findColumn = true;
	String columnname;
	System.out.println("Give the name of the column wich contains the attributte that you want to delete :");
	while (findColumn) {
		columnname = sc.nextLine();
		for (int i = 0; i <= name.length; i++) {
			if (columnname.equals(name.colname)) {
				findColumn = false;
			}
		}
		if  (i=name.length && findColumn = true) {
			System.out.println("This column name does not exist, please give another column name :");
		}
	}
	boolean findValue = true;
	String givenvalue;
	System.out.println("Give the content of the value tha you want to delete :");
	while (findValue) {
		givenvalue = sc.nextLine();
		for (int i = 0; i <= name.length; i++){
			if (givenvalue.equals(name.get(key))){
				name = ArrayUtils.removeElement(name, givenvalue);
			}
		}
	}
	
	
	
	
			return tableobj;
		
}
	
