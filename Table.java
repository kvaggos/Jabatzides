import java.util.*;
import java.util.Scanner;
import java.util.regex.*;

public class Table {

	String name;
	static Scanner sc = new Scanner(System.in);


     Map<String, String> ColTypes = new HashMap<String, String>();
     static HashMap<String, Table> listoftables  = new HashMap<String, Table>();
	 Map<String, LinkedList<String> > table = new LinkedHashMap<String, LinkedList<String>>(); //TreeMap that contains the rows and the values of every table


	public Table(String name) { // constructor
		this.name = name;

    }

    public Table() {}

    public void putValues(String rowname, LinkedList<String> values) {

	   table.put(rowname, values);
	}

    public void clearValues(String name) {

    	for (String key : table.keySet()) {	
			table.remove(key);
			
    		}
    	
    }
    
    public void deleteWholeTable(String name) {

    	listoftables.remove(name);
    	
    }
    
    public boolean isColumnDeleted(String colname) {

    	if (table.containsKey(colname)) {
			table.remove(colname);
			return true;
    	} else {
    		return false;
    	}
    	
    }
    
    public void renameTable(String oldname, String newname) {
    	
    	Table temporarystore = listoftables.get(oldname);
    	deleteWholeTable(oldname);
    	listoftables.put(newname, temporarystore);
    	
    }
  
    public void renameColumn(String oldname, String newname) {
  	
    	LinkedList<String> temporarystore = table.get(oldname);	
    	table.remove(oldname);	
  		table.put(newname, temporarystore);
  	
    }
  
	      public void createTableCols() {
                 boolean check1 = true; // checks if you want to stop adding rows and values
			     boolean check2 = true; // checks if you want to change row
				String coltype = "a";

					LinkedList <String> list = new LinkedList <String>(); //list that contains the values of a row
	  				System.out.println("Give column name or press enter to exit");
	  				String colname = sc.nextLine();
	  				check1 = !(colname == "");
	  				while(check1)  {
	  					System.out.println("Give column type (S)tring,(I)nt or (D)ouble");
	  					while("SID".toLowerCase().indexOf(coltype.toLowerCase()) == -1 || coltype.equals("")) {
	  						coltype = sc.nextLine();
						}
	  					ColTypes.put(colname, coltype);
						putValues(colname, list);
						System.out.println("Give next column name or press enter to exit");
						colname = sc.nextLine();
	  					check1 = !(colname == "");
	  					coltype = "a";
					}
			}



		public static Table searchTable() {
   			boolean workwithtable = true; // Work with a table
   			Table tableobj = null; //Work table
   			String name;
   			System.out.println("Give the table's name or EXIT to exit :");
   			while(workwithtable){
   				name = sc.nextLine();	//read the table's name

   				if(name.equals("EXIT")) {
   					workwithtable = false;
   				} else {
   					tableobj = getTable(name);
   					if ( tableobj == null){
   						System.out.println("Table " + name + " does not exists");
   					} else {
   						workwithtable = false;
   					}
   				}
			}
			return tableobj;
		}


	public void addRows() {
		String myval="";
		String coltyp = "";
		String styp = "";
		boolean morerow = true;
		boolean datok = true;
		while(morerow) {
			for (String key : table.keySet()) {
				coltyp = ColTypes.get(key);
				switch (coltyp) {
					case "S" : styp = "a String";
					case "I" : styp = "an Integer";
					case "D" : styp = "a Double";
				}
				System.out.println("Enter " + styp + " value for " + key);
				myval = sc.nextLine();
				while(checkval(myval, coltyp)) {
					System.out.println("Enter a corect value ");
					myval = sc.nextLine();
				}
				table.get(key).add(myval);
			}
			System.out.println("Add next row ?(Yes/No)");
			myval = sc.nextLine();
			morerow = (myval.equalsIgnoreCase("yes"));
		}
	}

	public boolean checkval(String tval, String typeof) {
		if (typeof == "S") {
			return true;
		}
		//
		if (typeof == "I" && tval.matches("-?[0-9]+")) {
			return true;
		}
		if (typeof == "D" && tval.matches("\\d+\\.\\d+")) {
			return true;
		}
		return false;
	}



	public void setTable(String name) {
		listoftables.put(name, this);
   }

    public static Table getTable(String name) {
		if(listoftables.containsKey(name)) {
			return  listoftables.get(name);
	 } else {
		 return null;
     }
}

	public boolean equals(Object o) { // override the equals method

		if (o == this)
			return true;
		if (!(o instanceof Table)) {
			return false;
		}
		Table table = (Table) o;
		return table.name.equals(name);
	}


	public void viewWholeTable(){



			for (String key : table.keySet()){
				System.out.print(key + "   ");
				}
			System.out.println();


			int x = 0;

			//for(Map.Entry<String, LinkedList<Object>> entry : table.entrySet()) {
			//    System.out.println(entry.getKey() + ": " +  entry.getValue().toString());
			//}
			System.out.println("table size = " + table.size());
			for(int i = 0;i<table.size();i++){

				for (String key2 : table.keySet()){
					System.out.print(table.get(key2).get(x) + "\t");
				}
				System.out.println();
				x++;
				}
			}

}



