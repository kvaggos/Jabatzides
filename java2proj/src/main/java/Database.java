 import java.util.Scanner;

import mypkg.Add2Table;
import mypkg.NewTable;
import mypkg.ViewTable;
import mypkg.RemoveFromTable;
import mypkg.ModifyTable;

public class DataBase {
	public static void main(String[] args) {
		String x;
		Scanner sc = new Scanner(System.in);
		boolean endcheck = true; // if false programm ends
		NewTable a = new NewTable();
		Add2Table b = new Add2Table();
		ViewTable c = new ViewTable();
		ModifyTable d = new ModifyTable();
		RemoveFromTable e = new RemoveFromTable();

		System.out.println("****Welcome to our DATABASE****");

		while (endcheck) {

			getMenu(); // call menu method

			x = sc.nextLine();

			if (x.equals("1")) {
				a.createTable();
			} else if (x.equals("2")) {
				b.addAtt2Table();
			} else if (x.equals("3")) {
				c.viewTable();
			} else if (x.equals("4")) {
			    e.removeTable();
			} else if (x.equals("5")) {
				 d.modifyTable();
			} else {
				endcheck = false;
				System.out.println("Thank you for using our Database");
			}
		}
		sc.close();
	}

	public static void getMenu() {

		System.out.println(
				"Make a choice from 1-5 to access database's functionalities." + "Press any other key to exit");
		System.out.println("1. Create new table ");
		System.out.println("2. Add attributes to a table ");
		System.out.println("3. View from a table ");
		System.out.println("4. Delete attributes from a table ");
		System.out.println("5. Modify attributes of a table ");

	}

}
