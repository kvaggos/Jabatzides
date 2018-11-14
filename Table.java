import java.util.*;

public class Table{

	String name;

     static HashMap<String, Table> listoftables  = new HashMap<String, Table>();
	//static ArrayList <Table> listoftables = new ArrayList <Table>(); //arraylist which contains the tables
	 HashMap<String, LinkedList<Object> > table = new HashMap<String, LinkedList<Object>>(); //TreeMap that contains the rows and the values of every table


	public Table(String name){ //constructor
		this.name = name;
		}

  public void putValues(String rowname, LinkedList<Object> values) {

	   table.put(rowname, values);


	  }

	public void setTable(String name) {
		listoftables.put(name, this);
}

    public Table getTable(String name) {
		if(listoftables.containsKey(name)) {
			return  listoftables.get(name);
	 } else {
		 return null;
     }
}


	//public void addTolistoftables() { //add this table to the list of tables
		//listoftables.add(this);
//}

      public boolean equals(Object o) { // override the equals method

        if (o == this) return true;
        if (!(o instanceof Table)) {
            return false;
        }
        Table table = (Table) o;
        return table.name.equals(name);
	}
}
