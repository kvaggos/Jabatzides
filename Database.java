 import java.util.TreeMap;
 import java.util.*;

 public class Database{

	static Scanner sc = new Scanner(System.in);

	public static void main(String[]args){

	boolean endcheck = true; // if false programm ends

	while(endcheck){

	getMenu();
	int x = sc.nextInt();


	if 	(x == 1){
		createTable();
		// }else if (x == 2){
			//addAttr();
		//}else if (x == 3){
		//	viewTable();
		//}else if (x == 4){
		//	deleteAttr();
		//}else if (x == 5){
		//	modifyAttr();
	//	}else if (x == 6){
	//		endcheck = false;
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
			Table tableobj = null;
			String name;

			while(checkexistingtable){

				System.out.println("Give table name");
				name = sc.nextLine();

				if (tableobj.listoftables.contains(new Table(name))){
					checkexistingtable = true;
					}else{
						 tableobj = new Table(name);
						 tableobj.addTolistoftables();
						 checkexistingtable = false;
					}
			}


				boolean check1 = true;
				boolean check2 = true;


				while(check1){

				System.out.println("Give row name");
				String rowname = sc.nextLine();
				Object value;

				ArrayList <Object> list = new ArrayList <Object>();
					check2 = true;
					while(check2){

					System.out.println("Give input");
					System.out.println("To end type: stop");
					System.out.println("To change row type: change");
					value = sc.nextLine();
					if( value.equals("change")){
						check2 = false;
					}else if( value.equals("stop")){
								check2 = false;
								check1 = false;
						}else{
					 list.add(value);
						}

					}
					tableobj.table.put(rowname, list);
				}
		}
 }