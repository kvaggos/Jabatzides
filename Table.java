import java.util.*;

public class Table{

	String name;

	static ArrayList <Table> listoftables = new ArrayList <Table>();
	static TreeMap<String, Object> table = new TreeMap<String, Object>();


	public Table(String name){
		this.name = name;


		}

	public void addTolistoftables() {

		listoftables.add(this);
}


      public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Table)) {
            return false;
        }

        Table table = (Table) o;

        return table.name.equals(name);


	}

}