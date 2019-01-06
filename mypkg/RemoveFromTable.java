package mypkg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

 public class RemoveFromTable {

	Scanner sc = new Scanner(System.in);
    
       //method responsible for the deltetion of the chosen table. Goes back to main menu after this method ends.
	public void deleteTable(Table table) {
		String s = table.getName();
		table.deleteWholeTable();
		System.out.println("Table with name: " + s + ", has been removed");
    }
    //this method clears the table of all its rows. Only columns remain
	public void clearTable(Table table) {
		if(table.getRowNo() == 0) {
			System.out.println("Please first add some rows.");
			return;
		}
		table.clearValues();
		System.out.println("Table with name:  "+ table.getName() + " , is empty now");
    }
    //method responsible for deleting a column from the table
	public void deleteColumn(Table table) {
		if(table.getKeySet() == null) {
			System.out.println("Please first add some columns.");
		}
		boolean a = true;
		String colname = "";
		 while(a && table.getKeySet() != null) {
		   System.out.println("Give the name of the column you want to delete or press enter to exit");
		   colname = sc.nextLine();
		    if(colname.equals("")) {
			   a = false;
		    } else if (table.isColumnDeleted(colname)) {
			System.out.println("Column with name: " + table.getName() + ", has been removed");
		    } else {
			System.out.println("Column with name:" + colname + " does not exist");
		   }
	        }
	     if(colname.equals(table.getPk())) { //if the selected column is PK make the appropriate changes.
		   table.setPK("");
		    if(table.getPKInc()) {
				table.setPKInc(false);
		    }
	      }
  }
    // delete one or more rows according to one or multiple conditions.
	public void deleteRow(Table table){

		if(table.getRowNo() == 0) { // no rows, no reason for this functionality to start
			System.out.println("Please first add some rows to access this functionality");
		    return;
		 }
		ViewTable V = new ViewTable();
		boolean b = true;
		String ss = "";
		String colName = "", colComp= "", conVal= "";
		ArrayList<Integer> srcSet = new ArrayList<Integer>(); // list where the indexes that must be removed are stored
		ArrayList<String> colList = new ArrayList<String>(); // list that contains the columns of the table
		Predicate<Integer> d;
		List<Integer> resSet = new ArrayList<Integer>();
		 for(int i = 0; i < table.getRowNo(); i++) {  // no conditions at first so all rows are inserted in srcSet
			  srcSet.add(i);
		 }
		 for(String s : table.getKeySet()) { // fill list with all the columns
			colList.add(s);
	         }
		 while(b) { //loop as long as the user wants to input more condition or there is only one row filtered from the previous conditions so no use for more conditions
		   colName = V.getColName(table); //get the name of the condition column
		   String coltyp = table.getType(colName).substring(0, 1); // get the type of the condition column
		   colComp = V.getColComp(colName, table); //get the type of condition (=, #, >,<)
	           conVal = V.getColVal(colName, table); // get the value of the condition
	             if(V.createShortcut(coltyp,colName, conVal, table, resSet, colComp, srcSet)) { //skips the search in some cases to speed up the program. See class viewTable for more details
		      d = Predicates.getCorrectPredicate(colComp, coltyp, table.getList(colName),conVal);
		      resSet = srcSet.parallelStream().filter(d).collect(Collectors.toList());
		    }
		    if(resSet.isEmpty()) { //if no indexes are found then the method returns, having deleted nothing
	              System.out.println("No data found matching the condition");
		      return;
		    }
		 srcSet.retainAll(resSet);  // retains only the indexes that match the condition.
		 resSet.clear();  // clear the resSet to prepare it for the next loop(if there is one)
		 System.out.println("The following row(s) will be affected by this action");
		 table.viewTable(table.createCopy1(), colList, srcSet);  // view only the specific rows that will be affected
		 System.out.println("What would you like to do? Press any other key to exit");
		 System.out.println("1. Delete rows");
		  if(srcSet.size() != 1) { // only if there is more than one row after the filters, this choice is meaningful.
		    System.out.println("2. Enter new Condition");
	      }
		  ss = sc.nextLine();
                          if(ss.equals("1")) { // Execute deletion of the filtered rows.
				int k = 0;
			    for(int i : srcSet) { // for every row filtered
				  table.deleteRow(i-k); //delete the i-k row to account for the previous deletions, so that the correct row is deleted each time.
				  k++;
			 	  table.setRows(table.getRowNo() - 1);
			  	  b = false;
			    }
			   System.out.println("Selected rows deleted successfully");
	                  } else if(ss.equals("2") && srcSet.size() == 1) { // add more conditions.
                             b = true;
                          } else {
			     return;
	                  }
	   }// close while loop
	}

	public void getRemoveMenu() {
		System.out.println("    Make a choice from 1-4. Press any other key to exit to main menu ");
		System.out.println("1. Delete a table");
		System.out.println("2. Clear a table ");
		System.out.println("3. Delete a Column from a table ");
		System.out.println("4. Delete a Row from a table ");
	}

	public void removeTable() {
		if(Table.noTables()) {
			System.out.println("You need to first create a table. Exiting.");
			return;
		}
	  Table table = Table.searchTable();
	   if(table == null){
				return;
		}
	   boolean endcheck = true;
		while(endcheck){
                        String x;
		 	getRemoveMenu();
		 	x = sc.nextLine();
		 	if (x.equals("1")) {
		 	  deleteTable(table);
		 	  endcheck = false;
		 	} else if(x.equals("2")) {
		 	  clearTable(table);
		        } else if(x.equals("3")) {
		 	  deleteColumn(table);
		 	} else if (x.equals("4")) {
		 	  deleteRow(table);
		 	} else {
		 	  endcheck = false;
 		         }
                  }
             }
}

