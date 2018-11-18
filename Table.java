import java.util.*;

public class Table{

	String name;

     static HashMap<String, Table> listoftables  = new HashMap<String, Table>();
	 HashMap<String, LinkedList<Object> > table = new HashMap<String, LinkedList<Object>>(); //TreeMap that contains the rows and the values of every table
	 Scanner sc = new Scanner(System.in);
	
	public Table() {}

	public Table(String name){ //constructor
		this.name = name;
		}

  public void putValues(String rowname, LinkedList<Object> values) {

	   table.put(rowname, values);


	  }

	public void setTable(String name) {
		listoftables.put(name, this);
}

    public Table getTable(String name) {
		if(listoftables.containsKey(name)) {
			return  listoftables.get(name);
	 } else {
		 return null;
     }
}


      public boolean equals(Object o) { // override the equals method

        if (o == this) return true;
        if (!(o instanceof Table)) {
            return false;
        }
        Table table = (Table) o;
        return table.name.equals(name);
	}


	public void addRows() {
		String myval;
		boolean moreval = true;
		for (String key : table.keySet()) {
			System.out.println("Enter value for " + key + " or dot(.) to end");
			myval = sc.nextLine();
			moreval = !(myval.equals("."));
			while(moreval) {
				table.get(key).add(myval);
				System.out.println("Enter next value for " + key + " or dot(.) to end");
				myval = sc.nextLine();
				moreval = !(myval.equals("."));
			}
		}
	}
      
    
		public  Table searchTable() {
   			boolean workwithtable = true; // Work with a table
   			Table tableobj = null; //Work table
   			String name;
   			System.out.println("Give the table's name to add attributes or EXIT to exit :");
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

            public void addElements() {
                 boolean check1 = true; // checks if you want to stop adding rows and values
		 boolean check2 = true; // checks if you want to change row
                 LinkedList <Object> list = new LinkedList <Object>(); //list that contains the values of a row
                 while(check1)  {

	  				System.out.println("Give column name");
	  				String rowname = sc.nextLine();
	  				Object value; // takes every kind of a variable's type

	  					check2 = true;
	  					System.out.println("Give input");
						System.out.println("To end type: stop");
	  					System.out.println("To change row type: change");
	  					while(check2){ // while in this loop, values are added to a row

	  					  value = sc.nextLine();
	  					  if( value.equals("change")){ //change row
	  						check2 = false;
	  					  }else if( value.equals("stop")){ // stop adding rows and values to the table
	  							check2 = false;
	  							check1 = false;
	  						    }else{
	  					          list.add(value); // add the given value to the list of values
	  						    }

	  					}
	  					if( list.isEmpty()) {

	  					putValues(rowname, null); // save the rows and the lists(value's of every row) to TreeMap
	  				} else {
	 				    putValues(rowname, list);
	 				    list.clear();
 		        }
		  }
	  }
            }
