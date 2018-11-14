import java.util.*;

  public class Database{

 	static Scanner sc = new Scanner(System.in);

 	public static void main(String[]args){

 	boolean endcheck = true; // if false programm ends

 	while(endcheck){

 	getMenu();	// call menu method
 	int x = sc.nextInt();


 	if 	(x == 1){
 		createTable();
 		 }else if (x == 2){
 			//addAttr();
 		}else if (x == 3){
 			viewTable();
 		}else if (x == 4){
 			deleteAttr();
 		}else if (x == 5){
 			modifyAttr();
 		}else if (x == 6){
 			endcheck = false;
 		//}
 	}
 	}
 }

 		public static void getMenu(){
 			System.out.println("****Welcome to our DATABASE****");
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
 			Table tableobj = null; //creating Table type object
 			String name;

 			while(checkexistingtable){ //while checkeistingtable = true

 				System.out.println("Give table name");
 				name = sc.nextLine();	//read the table's name

 				if (tableobj.getTable(name) == null){ //checks if table names exists already
 					checkexistingtable = true; // make again the loop
 					}else{
 						 tableobj = new Table(name); //the given name becomes a table name
 						 tableobj.setTable(name);	//add the table to the list of tables
 						 checkexistingtable = false; // end the loop
 					}
 			}


 				boolean check1 = true; // checks if you want to stop adding rows and values
 				boolean check2 = true; // checks if you want to change row


 				while(check1){

 				System.out.println("Give row name");
 				String rowname = sc.nextLine();
 				Object value; // takes every kind of a variable's type

 				LinkedList <Object> list = new LinkedList <Object>(); //list that contains the values of a row
 					check2 = true;
 					while(check2){ // while in this loop, values are added to a row

 					System.out.println("Give input");
 					System.out.println("To end type: stop");
 					System.out.println("To change row type: change");
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
 					tableobj.putValues(rowname, list); // save the rows and the lists(value's of every row) to TreeMap
 				}
 		}
  }
