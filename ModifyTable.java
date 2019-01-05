import java.util.*;
import java.io.*;
import java.util.Scanner;

public class ModifyTable {

	Scanner sc = new Scanner(System.in);
	Table table = null;
	
	public void renameTable(Table table){
		
		String oldname = table.name;
		
		System.out.println("Give the new name of the table");
		String newname = sc.nextLine();
		
		table.renameTable(oldname, newname);
		System.out.println("Table with name: "+ oldname +", has been renamed to: "+ newname);
		
		}
	
	public void renameColumn(Table table){
	
			System.out.println("Which column do you want to rename?");
			String oldname = sc.nextLine();
		
			System.out.println("Give the new name of the column");
			String newname = sc.nextLine();
		
			table.renameColumn(oldname, newname);
			System.out.println("Column with name: "+ oldname +", has been renamed to: "+ newname);
	
		}
	
	public void getModifyMenu(){
		System.out.println("    Make a choice: ");
		System.out.println("1. Rename a table");
		System.out.println("2. Rename a column ");
		System.out.println("3. Change the values of a column");
		System.out.println("4. Exit Modify attributes from a table");
	}
	
	public void RemoveTable(){
		
		table = Table.searchTable();
		if(table == null){
				return;
		}

		boolean endcheck = true;
		while(endcheck){

		 	getModifyMenu();
		 	int x = sc.nextInt();

		 	if 	(x == 1){
		 		renameTable(table);
		 		 }else if (x == 2){
		 			renameColumn(table);
		 		}else if (x == 3){
		 			//deleteColumn(table);
		 		}else if (x == 4){
		 			endcheck = false;
		 		} else {
				 System.out.println("Invalid entry. Please select a number from 1-3 or input 4 to end to go back to main menu.");
		 	}
 		}
	}
}
