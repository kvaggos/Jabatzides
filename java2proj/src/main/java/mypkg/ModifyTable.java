package mypkg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//this class is responsible for modifying/altering a table
//supported actions include modifying the tables name, the name of a column and lastly a value of a column.
public class ModifyTable {

  Scanner sc = new Scanner(System.in);

	
    // this method changes the name of a table
	public void renameTable(Table table){
		String oldname = table.getName();
		System.out.println("Give the new name of the table");
		String newname = sc.nextLine();
		 while(newname.trim().isEmpty() || oldname.equals(newname)) {
			System.out.println("Table name cant be an empty line or the same as before. Please try again");
			newname = sc.nextLine();
		 }
		Table temp = Table.getTable(oldname);
		table.deleteWholeTable();
		temp.setName(newname);
		temp.setTable();
		System.out.println("Table with name: "+ oldname + ", has been renamed to: "+ newname);
        }
    //this method renames a column name and also keeps the order of the columns intact, as the user inputed them
	public void renameColumn(Table table){
		         if(table.getMappings() == 0) {
				 System.out.println("Please first add some columns to access this functionality");
			         return;
			 }
			boolean d = true;
			String oldname = null;
			System.out.println("Which column do you want to rename?");
                          while(d) {
			    oldname = sc.nextLine();
			     if (table.containsKey(oldname)) {
				  d = false;
			     } else {
			      System.out.println("Column with name: " + oldname + " does not exist. Try again.");
			     }
			  }
			System.out.println("Give the new name of the column");
			String newname = sc.nextLine();
			 while(newname.trim().isEmpty() || newname.equals(oldname)) {
				System.out.println("Column name cant be an empty line or the same as before. Please try again");
				newname = sc.nextLine();
		         }
			table.renameColumn(oldname, newname);
			System.out.println("Column with name: " + oldname + ", has been renamed to: " + newname);
	 }


    //method used to change the values of a field of a column.
	public void changeValue(Table table) {
		if(table.getRowNo() == 0 || table.getMappings() == 0) {
			System.out.println("Please first add some rows/columns to access this functionality");
			return;
	        }
		boolean x = true;
		String colname = "";
		System.out.println("In which column is the value you want to change?");
		 while (x) {
		    if(colname.equals(table.getPk()) && table.getPKInc()) {
			System.out.println("PK is incremental. Values cannot be changed.");
		    } else if(table.containsKey(colname)) {
			x = false;
		    } else {				
		       System.out.println("Column with name: "+ colname + " does not exist. Try again.");
		    }
		 }
		 String myval = "";
		 String coltyp = table.getType(colname).substring(0,1);
		 System.out.println("Please give new value for " + colname);
		  myval = sc.nextLine();
		 	while(!Add2Table.checkval(myval, coltyp, myval, colname, table)) { //make sure the given value is compatible with constraints
		 		myval = sc.nextLine();
			}
			String myval1 = Add2Table.getCorrectValue(myval, coltyp); 
		        ViewTable V = new ViewTable();
			boolean b = true;
			String colName = "", colComp= "", conVal= "";
			ArrayList<Integer> srcSet = new ArrayList<Integer>();
			ArrayList<String> colList = new ArrayList<String>();
			Predicate<Integer> d;
			List<Integer> resSet = new ArrayList<Integer>();
			 for(int i = 0; i < table.getRowNo(); i++) {
					 srcSet.add(i);
		         }
		         for(String s : table.getKeySet()) {
				 	 colList.add(s);
	                 }
		        String ss = "";
			      while(b) {
				   colName = V.getColName(table);
				   colComp = V.getColComp(colName, table);
			           conVal = V.getColVal(colName, table);
			              if(V.createShortcut(coltyp,colName, conVal, table, resSet, colComp, srcSet)) {
				         d = Predicates.getCorrectPredicate(colComp, coltyp, table.getList(colName),conVal);
				         resSet = srcSet.parallelStream().filter(d).collect(Collectors.toList());
				      }
				 if(resSet.isEmpty()) {
			              System.out.println("No data found matching the condition");
				      return;
				  }
				 srcSet.retainAll(resSet);
				 System.out.println("The following rows will be affected by this action");
				 table.viewTable(table.createCopy1(), colList, srcSet);
				 resSet.clear();
				 System.out.println("What would you like to do? Press any other key to exit");
				 System.out.println("1. Update values");
				   if(srcSet.size() != 1) {
				 	 System.out.println("2. Enter new Condition");
				 	}
				  ss = sc.nextLine();
				   if(ss.equals("1")) { //values that match the condition are updated
				     for(int i : srcSet) {
					  table.updateRow(i, colname, myval1);
					  b = false;
				     }
			            System.out.println("Values updated successfully");
			          } else if(ss.equals("2") && srcSet.size() != 1) {
		                      b = true;
		                  } else {
					  return;
				  }
	       }
	   }

	public void getModifyMenu(){
		System.out.println("    Make a choice from 1-3. Press any other key to exit to Main Menu");
		System.out.println("1. Rename a table");
		System.out.println("2. Rename a column ");
		System.out.println("3. Change value of a column");
	}




	public void modifyTable() {

		if(Table.noTables()) {
			System.out.println("You need to first create a table. Exiting.");
		    return;
		}

		Table table = Table.searchTable();
		if(table == null){
				return;
		}
                String x;
		boolean endcheck = true;
		 while(endcheck){
		 	getModifyMenu();
		 	x = sc.nextLine();
		    if(x.equals("1")){
		 	   renameTable(table);
		    } else if (x.equals("2")){
		 	   renameColumn(table);
		    } else if (x.equals("3")){
		 	   changeValue(table);
		    } else {
			 endcheck = false;
 		    }
	      }
    }
}

