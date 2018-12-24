package mypkg;

import java.util.Scanner;

public class NewTable {

	static Scanner sc = new Scanner(System.in);

	public void createTable(){
       Table tableobj = getNewTable();
        if(tableobj == null){
			    return;
		    }
		setPrimaryKey(tableobj);
		createTableCols(tableobj);
	 }


   public Table getNewTable() {
			boolean checkexistingtable = true; // checks if the table name is available
			Table tableobj = null;
		  String name;
			 while(checkexistingtable){ //while checkeistingtable = true
				 System.out.println("Give table name or press enter to exit");
				 name = sc.nextLine(); //read the table's name
				  if(name.equals("")) {
				    checkexistingtable = false;
				  } else if(Table.getTable(name) != null){ //checks if table names exists already
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

		public void setPrimaryKey(Table myTable) {
		  System.out.println("Give column name for primary key");
		  String pkval = sc.nextLine();
		 	  while(pkval.trim().isEmpty()) {
		 		 System.out.println("Primary key cannot be an empty line. Please try again");
		 		 pkval = sc.nextLine();
		 	  }
		 	System.out.println("Would you like the primary key to be auto-incremental?[Y/any other key]");
		 	String pki = sc.nextLine();
				if(pki.equalsIgnoreCase("y")) {
				 		myTable.setPKInc(true);
				 		myTable.setTypePK(pkval);
		 	  } else {
				setType(pkval, myTable);
			  }
		     myTable.setPK(pkval);
	       myTable.putValues(pkval);
	    }

	  public static void createTableCols(Table tableobj) {
		  boolean check1 = true; // checks if you want to stop adding columns
	    String colname;
		  colname = getColname(tableobj);
		  check1 = !colname.trim().isEmpty();
			 while(check1) {
			   setType(colname, tableobj);
			   addConstraints(colname, tableobj);
				 tableobj.putValues(colname);
				 colname = getColname(tableobj);
			   check1 = !colname.trim().isEmpty();
		    }
	   }

	   	 public static void setType(String colname, Table tableobj) {
	   		  String coltype;
	   		  System.out.println("Give column type (S)tring,(N)umeric or (C)haracter");
	   		  coltype = sc.nextLine();
	   		    while(coltype.length() != 1 || !"SNC".toLowerCase().contains(coltype.toLowerCase())) {
	   			    System.out.println("Invalid input. Please input S, N or C");
	   			    coltype = sc.nextLine();
	   	      }
	   	  	tableobj.setType(colname, coltype);
       }

        public static String getColname(Table tableobj) {
		       int s = 0;
		       String colname;
		       System.out.println("Please enter column name or press enter to exit");
		       colname = sc.nextLine();
			       while(tableobj.containsKey(colname)) {
				       s++;
			         System.out.println("column name already exists. Please try again");
				         if((s % 3) == 0) {
					         System.out.println("Reminder : You have already entered this/these column(s): " + tableobj.getKeySet());
				         }
		          colname = sc.nextLine();
		         }
		       return colname;
       }

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
			        } else {
				          s++;
				          String s1 = tableobj.getType(colname) + resp;
				          tableobj.setType(colname, s1);
			        }
	       } 
     }
}
