import java.util.*;

public class Table{

	String name;

	static ArrayList <Table> listoftables = new ArrayList <Table>(); //arraylist which contains the tables
	static TreeMap<String, Object> table = new TreeMap<String, Object>(); //TreeMap that contains the rows and the values of every table


	public Table(String name){ //constructor
		this.name = name;
		}

	public void addTolistoftables() { //add this table to the list of tables
		listoftables.add(this);
}

      public boolean equals(Object o) { // override the equals method

        if (o == this) return true;
        if (!(o instanceof Table)) {
            return false;
        }
        Table table = (Table) o;
        return table.name.equals(name);
	}
}
