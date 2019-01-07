package mypkg;

import java.util.Scanner;
//class responsible for the creation of a new Table.
//Includes the setting of the tables name, adding columns and PK, setting types and constraints for each column.
public class NewTable {
    
	static Scanner sc = new Scanner(System.in);

    public void createTable() {
        Table tableobj = getNewTable(); // get the object of the newlyformed table
             if(tableobj == null) { // if the object is null that means the user opted to exit the functionality. See below.
		 return;
	     }
        setPrimaryKey(tableobj); //first our database sets the primary key of each table.
	createTableCols(tableobj); //finally the columns are created along with their types and possible constraints.
     }
      //returns the object with only the tables name set or null if the user wants to exit this functionality.
    public Table getNewTable() {
	 boolean checkexistingtable = true; // checks if the table name is available
	 Table tableobj = null;
         String name;
	    while(checkexistingtable){ //while checkeistingtable = true
		System.out.println("Give table name or press enter to exit");
		name = sc.nextLine(); //read the table's name
		  if(name.trim().isEmpty()) {
		    checkexistingtable = false;
		  } else if(Table.getTable(name) != null) { //checks if table names exists already
		        checkexistingtable = true; // make again the loop
		        System.out.println("Table already exists");
		  } else {
                        tableobj = new Table(name);//the given name becomes a table name ,add the table to the list of tables
			tableobj.setTable();
			checkexistingtable = false; // end the loop
		  }
	     }
          return tableobj;
     }
       // method responsible for creating and setting the PK
      public void setPrimaryKey(Table myTable) {
		    System.out.println("Please input column name for primary key");
		    String pkval = sc.nextLine();
		 	    while(pkval.trim().isEmpty()) {
		 		    System.out.println("Primary key cannot be an empty line. Please try again");
		 		    pkval = sc.nextLine();
		 	   }
		 	  System.out.println("Would you like the primary key to be auto-incremental?[Y/any other key]");
		   	String pki = sc.nextLine();
				 if(pki.equalsIgnoreCase("y")) {
				 		myTable.setPKInc(true); 
				 		myTable.setTypePK(pkval); //when incremental always set numeric as type.
		 	    } else {
				     setType(pkval, myTable);
			    }
		    myTable.setPK(pkval);
	            myTable.putValues(pkval); //put an empty list mapped to the column to prepare it for addition at a later stage.
	    }
       //create the rest of the columns the ones that are not primary key
	   public static void createTableCols(Table tableobj) {
		     boolean check1 = true; // checks if you want to stop adding columns
	       String colname;

		  colname = getColname(tableobj); 
		  check1 = !colname.trim().isEmpty();
			 while(check1) { //while the user doesnt input an empty line continue adding columns to the table.
			  	setType(colname, tableobj); //set type ,constraints, put the column in the map, mapped with an empty list and ask the user if he wants to coninue the process
			  	addConstraints(colname, tableobj);
				tableobj.putValues(colname);
				colname = getColname(tableobj);
			  	check1 = !colname.trim().isEmpty();
		   }
	  }
       // method responsible for setting the type of each column. The types our database offers are string(anything goes) numeric(only numeric values go) and character(only characters are accepted
	   public static void setType(String colname, Table tableobj) {
	   		String coltype;
	   		System.out.println("Please input column type (S)tring,(N)umeric or (C)haracter");
	   		coltype = sc.nextLine();
	   		 while(coltype.length() != 1 || !"SNC".toLowerCase().contains(coltype.toLowerCase())) {
	   			System.out.println("Invalid input. Please input S, N or C");
	   			coltype = sc.nextLine();
	   	         }
	   	  	 tableobj.setType(colname, coltype);
      }
      // this method returns a valid column name or "" if the user wants to stop adding columns
     public static String getColname(Table tableobj) {
		   int s = 0;
		   String colname;
		   System.out.println("Please enter column name or press enter to exit");
		   colname = sc.nextLine();
			   while(tableobj.containsKey(colname)) {
				s++;
			        System.out.println("column name already exists. Please try again");
				    if((s % 3) == 0) {
					     System.out.println("Reminder : You have already entered this/these column(s): " + tableobj.getKeySet()); //every three failed atttempts remind the user of his previous inputed columns.
				    }
		      colname = sc.nextLine();
		    }
		   return colname;
     }
      // method responsible for adding constraints for each column. Available constraints :No duplicate, no null values.
    public static void addConstraints(String colname, Table tableobj) {
	            if(colname.equals(tableobj.getPk())) {
			     return;
		    }
		  System.out.println("Would you like to add  constraint(s)?[Y/any other key]");
	          String resp = sc.nextLine();
		   if(!resp.equalsIgnoreCase("y")) {
			   return;
		   }
		  int s = 0;
		  System.out.println("Choose constraints. Press enter to exit");
	          System.out.println("1. Not null");
	          System.out.println("2. No duplicates");
		    while(s < 2 && !resp.equals("")) {
			   resp = sc.nextLine();
			      if(!(resp.equals("1") || resp.equals("2")) && !resp.equals("")) {
				      System.out.println("Invalid input. Please try again");
			      } else if(tableobj.getType(colname).contains(resp)) {
				   System.out.println("Constraint already inputed.Please try again");
			      } else {			      
				       s++;
				       String s1 = tableobj.getType(colname) + resp; //create a string that holds the type and the constraints of the column.
				       tableobj.setType(colname, s1);
			     }
	          }
     }
}

