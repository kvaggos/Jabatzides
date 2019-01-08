package mypkg;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;


// class responsible for adding new rows and columns after table creation.
public class Add2Table {
      if(myTable.getKeySet().size() == 0) {
         System.out.println("Please add some column first");
	 return;
       }
	Scanner s2 = new Scanner(System.in);

	public  void addAtt2Table() {
             if(Table.noTables()) { // checks if there are any tables in the database if not, then theres no point for the user to access this functionality so it returns back to main menu
		  System.out.println("you need to first create a table. Exiting.");
		  return;
	      }
	    boolean check = true;
            Table myTable = Table.searchTable(); // save the object of the table the user requested.
		if(myTable == null) { // if the user presses enter for exit then program goes back to main menu
			return;
		}
	     String resp;
			while(check) {
				System.out.println("Add rows (r) or new Columns (c). Press any other key to exit");
				resp = s2.nextLine();
				if (resp.equalsIgnoreCase("c")) {
					NewTable.createTableCols(myTable);
					myTable.fillEmptyCols();
				} else if (resp.equalsIgnoreCase("r")) {
					addRows(myTable);
				} else {
					check = false;
			    }
	         }
    }
    // method responsible for adding rows to the selected table.
    //This method loops over the the table and asks the user for an inpur for every column, then asks if the user wants to continue or not and acts accordingly
	public void addRows(Table myTable) {

			String myval;
			String coltyp;
			String key;
			boolean morerow = true;

			while(morerow) {
		           Iterator<Map.Entry<String, LinkedList<String>>> itr1 = myTable.createCopy1().entrySet().iterator();
			     while(itr1.hasNext()) { //loop through the map(table) using iterator
				  Map.Entry<String, LinkedList<String>> entry = itr1.next();
                                  key = entry.getKey();
					    if(key.equals(myTable.getPk()) && myTable.getPKInc()) { //handles the case where the PK is incremental. +1 for every new row.
					            int i = myTable.getRowNo() + 1;
						    myTable.add2Table(key, Integer.toString(i));
					     } else {
					        coltyp = myTable.getType(key).substring(0, 1);
					        getTypes(coltyp, key);
					        myval = s2.nextLine();
						      while(!checkval(myval, coltyp, myval, key, myTable)) { // call checkval method to correct the user if he inputs smth that goes against constraints
							    myval = s2.nextLine();
					              }
				                String myval1 = getCorrectValue(myval,coltyp);
				                myTable.add2Table(key, myval1);
			                      }
		            }
		          myTable.updateRowNo();
		          System.out.println("Add next row ?(Y/Any other key)");
		          myval = s2.nextLine();
			  morerow = (myval.equalsIgnoreCase("Y")); // add one more row if the user chooses to, or exit adding rows.
		        }
	}
	//method used to correct the users input.
	public static String getCorrectValue(String myval, String type) {
		 String myval1;
		   if(myval.trim().isEmpty() || myval.equalsIgnoreCase("NULL")) { // a null value in our database means that there is nothing in that particular field
		      myval1 = "null";
		   } else if(myval.contains(",") && type.equals("N")) { //makes the double value appropriate for java. 3,4 -> 3.4
			  myval1 = myval.replace(',', '.');
		   } else {
			  myval1 = myval;
		   }
		 return myval1;
       }
    // method that presents to the user his options for column types.
	public static void getTypes(String coltype, String key) { 
	       String styp = "";
		 switch (coltype) {
		   case "S" : styp = "a String"; break;
		   case "N" : styp = "a Numeric"; break;
		   case "C" : styp = "a Character"; break;
		 }
	      System.out.println("Enter " + styp + " value for " + key);
        }
    // method responsible for making sure that the user inputs valid attributes according to the constraints he selected for each column.
	public static boolean checkval(String tval, String typeof, String myval, String key, Table myTable) {
	         int s = 0;
                 boolean whiteS = false;
                   if(typeof.equalsIgnoreCase("n")) { // check if there are whitespaces in numeric input so that the appropriate message is printed to the user
                      for (char c : tval.toCharArray()) {
			      if (Character.isWhitespace(c)) {
			       whiteS = true;
			      }
                     }
	           }
	         boolean nullHandling = (myval.trim().isEmpty() || myval.equalsIgnoreCase("NULL")); // if the user inputs an empty line or null(Case Ignored) then the program accepts the value no matter the type unless no null constraint is chosen
			if (typeof.equalsIgnoreCase("n") && !tval.matches("^-?[0-9]*(.|,)?[0-9]+?$") || whiteS) {
				 if(!nullHandling) { 
				  System.out.println("Wrong type. Enter a numeric value ");
				  s++;
			          }
			}
			if (typeof.equalsIgnoreCase("c") && !tval.replaceAll("\\s+", "").matches("^[a-zA-Z]+$")) {
				if(!nullHandling) {
				  System.out.println("Wrong type. Enter a character value ");
				  s++;
			        }
			}
			if(myTable.getPk().equals(key)) {
				if(myTable.keyContainsVal(key, myval)) {
					s++;
			                System.out.println("Duplicate  values for Primary Key are not allowed. Enter a correct value.");
				}
			        if(nullHandling) {
					System.out.println("Null  value for Primary Key is not allowed. Enter a correct value.");
					s++;
		                }
		         }
		       if(myTable.getType(key).contains("1") && nullHandling) {
			   System.out.println("Constraint violation: No null values allowed for column " + key);
			   s++;
		        }
		       if(!nullHandling) {
		         if(myTable.getType(key).contains("2") && myTable.keyContainsVal(key, myval)) {
			    System.out.println("Constraint violation: No duplicate values allowed for column " + key);
			    s++;
		         }
		       }

			if(s != 0) {
			  return false;
		         } else {
			  return true;
		         }
         }
}

