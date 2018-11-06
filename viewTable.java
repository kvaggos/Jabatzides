public void viewTable(){

    String name;
	boolean checkexistingtable = true;
	boolean cancel = true;
	Scanner input = new Scanner(System.in);
	Table tview = null;

	System.out.println("Give table name that you want to see");

	while(checkexistingtable && cancel) {

		System.out.println("Or give exit if you want to quit");
		name = input.nextLine();


		if(name.equals("exit")) {
			cancel = false ;
		} else {
			if (tview.listoftables.contains(new Table(name))) {
				checkexistingtable = false;
			}else {
				System.out.println("This table not exists.Please give another name.");
			}
		}
	}

	 if(cancel == true){
		 for ( Map.Entry<String, ArrayList<Object>> entry : tableobj.table.entrySet()) {
			 System.out.print(entry.getKey()+" | ");

		     for(Object rowNo : entry.getValue() {
				 System.out.print(rowNo+" ");
			 }

			 ArrayList<Object> keys = new ArrayList<Object>();
		 	 keys = tableobj.table.values();

		     System.out.println(keys[0]);
		 }
	 }
 }
