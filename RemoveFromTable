import java.util.*;
import java.io.*;
import java.util.Scanner;


class RemoveFromTable {
	
	public RemoveFromTable() {
		
	}
	
	Scanner sc = new Scanner(System.in);
	Table table = null;
	
	public void deleteTable(Table table){
		
		table.deleteWholeTable(table.name);
		System.out.println("Table with name: "+ table.name +", has been removed");
		
		}

	public void clearTable(Table table){

		table.clearValues(table.name);
		System.out.println("Table with name: "+ table.name +", is empty now");
		}

	public void deleteColumn(Table table){
		
		System.out.println("Give the name of the column you want to delete");
		String colname = sc.nextLine();
		
		if (table.isColumnDeleted(colname)) {
			System.out.println("Table with name: "+ table.name +", has been removed");
			} else {
				System.out.println("Column with name:" + colname + " does not exist");
				return;
			}
		}

	public void deleteRow(Table table){

		}

	public void getRemoveMenu(){
		System.out.println("    Make a choice: ");
		System.out.println("1. Delete a table");
		System.out.println("2. Clear a table ");
		System.out.println("3. Delete a Column from a table ");
		System.out.println("4. Delete a Row from a table ");
		System.out.println("5. Exit Delete attributes from a table");
	}

	public void RemoveTable(){
		
		table = Table.searchTable();
		if(table == null){
				return;
		}

		boolean endcheck = true;
		while(endcheck){

		 	getRemoveMenu();
		 	int x = sc.nextInt();

		 	if 	(x == 1){
		 		deleteTable(table);
		 		 }else if (x == 2){
		 			clearTable(table);
		 		}else if (x == 3){
		 			deleteColumn(table);
		 		}else if (x == 4){
		 			deleteRow(table);
		 		}else if (x == 5){
		 			endcheck = false;
		 		} else {
				 System.out.println("Invalid entry. Please select a number from 1-4 or input 5 to end to go back to main menu.");
		 	}
 		}
	}
}
