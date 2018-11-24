 import java.util.*;
 import java.io.*;
 import java.util.Scanner;


  public class Database{

 	  static Scanner sc = new Scanner(System.in);

 	public static void main(String[]args){

 	boolean endcheck = true; // if false programm ends


    System.out.println("****Welcome to our DATABASE****");
 	while(endcheck){

 	getMenu();	// call menu method
 	int x = sc.nextInt();


 	if 	(x == 1){
 		createTable();
 		 }else if (x == 2){
 			addAtt2Table();
 		}else if (x == 3){
 			viewTable();
 		}else if (x == 4){
 			//deleteAttr();
 		}else if (x == 5){
 			//modifyAttr();
 		}else if (x == 6){
 			endcheck = false;
 			System.out.println("Thank you for using our Database");
 		} else {
		 System.out.println("Invalid entry. Please select a number from 1-5 or input 6 to end the program.");
 	}
 	}
 }

 		public static void getMenu(){

 			System.out.println("    Make a choice: ");
 			System.out.println("1. Create new table ");
 			System.out.println("2. Add attributes to a table ");
 			System.out.println("3. View from a table ");
 			System.out.println("4. Delete attributes from a table ");
 			System.out.println("5. Modify attributes of a table ");
 			System.out.println("6. End programm");
 			}

 		public static void createTable(){

 			boolean checkexistingtable = true; // checks if the table name is available
 			Scanner sc = new Scanner(System.in);
 			Table tableobj = new Table(); //creating Table type object
 			String name;

 			while(checkexistingtable){ //while checkeistingtable = true

 				System.out.println("Give table name");
 				name = sc.nextLine();	//read the table's name

 				if (tableobj.getTable(name) != null){ //checks if table names exists already
 					checkexistingtable = true; // make again the loop
 					System.out.println("Table already exists");
 					}else{
 						 tableobj = new Table(name); //the given name becomes a table name
 						 tableobj.setTable(name); //add the table to the list of tables

 						 checkexistingtable = false; // end the loop
 					}
 			}

        tableobj.createTableCols();


	}


	public static void addAtt2Table() {

		Table myTable = new Table(); //creating Table type object

		myTable = myTable.searchTable();

		if (myTable == null) {
			return;
		}

		Scanner s2 = new Scanner(System.in);
		String resp="o";
		while(!(resp.equals("x"))) {
			System.out.println("Add rows (h) or new Columns (c). Enter (x) to exit");
			resp = s2.nextLine();
			if (resp.equals("c")) {
				myTable.createTableCols();
			} else if (resp.equals("h")) {
				myTable.addRows();
			} else {
				System.out.println("Invalid entry: Enter (h), (c) or (x) to exit");
			}
		}

	}

	public static void viewTable() {
			        Scanner s2 = new Scanner(System.in);
	                String resp="";
					Table myTable = new Table(); //creating Table type object

					myTable = myTable.searchTable();

					if (myTable == null) {
							return;
					}
					System.out.println("What would you like to do?");
					System.out.println("a.View whole Table");
					System.out.println("b.Custom view Table");

					resp = s2.nextLine();
					if(resp.equals("a")) {
						myTable.viewWholeTable();
				}




	}
}

 
