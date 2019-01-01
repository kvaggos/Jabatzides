package mypkg;


import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

//every instance of this class is a table in our database
public class Table  {
	
	private String name;
	static Scanner sc = new Scanner(System.in);
	private int rows = 0;

	private String Pkey;
	private boolean PKInc = false;
        private Map<String, String> ColTypes = new HashMap<String, String>(); // maps columns with their types/constraints
        private static HashMap<String, Table> listoftables  = new HashMap<String, Table>(); // maps the name and the object instance, effectively storing the instance.
	private Map<String, LinkedList<String> > table = new LinkedHashMap<String, LinkedList<String>>(); //TreeMap that contains the rows and the values of every table
        private LinkedList<String> list; //in the instances of the list the values of each column are stored.

	public Table(String name){ //constructor
		this.name = name;
        }
    // getters and setters for the most part
    public void setTable() {
	 listoftables.put(this.name, this);
    }

    public String getName() {
		return this.name;
    }

    public void setName(String name) {
		this.name = name;
    }

    public int getMappings() {
	    return this.table.size();
    }

    public void setRows(int row) {
		this.rows = row;
    }

    public void clearValues() {
	   for (String key : table.keySet()) {
		     table.get(key).clear();
          }
     }
    //loops through the table's columns and deletes a given row
	public void deleteRow(int i) {
	     Iterator<Map.Entry<String, LinkedList<String>>> itr1 = table.entrySet().iterator();
		  while(itr1.hasNext()) {
		   Map.Entry<String, LinkedList<String>> entry = itr1.next();
                   String key = entry.getKey();
                   table.get(key).remove(i);
	          }
        }
     //updates a value of a given column with the new given value
     public void updateRow(int i, String colName, String newValue) {
		       table.get(colName).set(i, newValue);
     }

      public void deleteWholeTable() {
	    	listoftables.remove(this.name);
	}
     //returns true if the colum is deleted and deltes the column or false otherwise(if the column doesnt exist)
	 public boolean isColumnDeleted(String colname) {
	    if (table.containsKey(colname)) {
			table.remove(colname);
		    return true;
	    } else {
	      return false;
	    }
	 }
    // rename the column and makes sure the order of the columns is kept. Coltypes also get updated and the case where the given column is PK is handled appropriately.
	public void renameColumn(String oldname, String newname) {
		Map<String, LinkedList<String> > table1 = new LinkedHashMap<String, LinkedList<String>>();
		boolean x = false;
		Iterator<String> itr2 = table.keySet().iterator();
		 while(itr2.hasNext()) {
			String key = itr2.next();
			if(key.equals(oldname)) {
				table1.put(newname, table.get(key));
				itr2.remove();
				x = true;
			} else if(x == true) {
				table1.put(key, table.get(key));
				itr2.remove();
		    }
		}
		 for(String s : table1.keySet()) {
			 table.put(s, table1.get(s));
		 }
		ColTypes.put(newname, ColTypes.remove(oldname));
		if(oldname.equals(getPk())) {
			setPK(newname);

		}
     }

    public void setPKInc(boolean s) {
		this.PKInc = s;
     }

    public boolean getPKInc() {
		return this.PKInc;
    }
    // returns a copy of the values of a column.
     public LinkedList<String> getCopy(String colname) {
		LinkedList<String> s = new LinkedList<String>(table.get(colname));
		return s;
     }

    public static boolean noTables() {
		return listoftables.isEmpty();
    }

    public void updateRowNo() {
		this.rows++;
    }
    public int getRowNo() {
		return this.rows;
    }
    //finds a value in a column that has the no duplicates constraint.
    public int findUnique(String col, String val, ArrayList<Integer> srcSet, String coltyp) {
		int a = -1;
		double val1=0.0,val2=0.0;
		for(int i : srcSet) {
			if(coltyp.equals("N")) {
			   try {
				val1 = Double.parseDouble(val);
				val2 = Double.parseDouble(table.get(col).get(i));
				 if(val2 == val1) {
					a = i;
				 }
			   } catch(NumberFormatException e) { // if column value or given val is null then compare them as strings.
					if(val.equals(table.get(col).get(i))) {
					    a = i;
				    }
			    }
			}else {
				if(table.get(col).get(i).equals(val)) {
					a = i;
				}
			}
			if(a != -1) break;
		}
		return a;
    }

    public  void putValues(String rowname) {
       list = new LinkedList<String>(); //list that contains the values of a column
	   this.table.put(rowname, list);
    }
    // after creating a new column. after the creation of the table, fill all the rows that exist in this column with null values.
    public void fillEmptyCols() {
	  for (String key : table.keySet()) {
		if( table.get(key).size() < rows) {
		     for(int i = 0; i<rows; i++) {
			table.get(key).add("null");
		     }
                }
	  }
    }
     // get the table instance demanded by the user
     public static Table searchTable() {
   			boolean workwithtable = true; // Work with a table
   			Table Tableobj = null;
   			String name;
   			System.out.println("Give the table's name to work with or press enter to exit :");
   			while(workwithtable){
   				name = sc.nextLine();	//read the table's name
   				if(name.trim().isEmpty()) {
   					workwithtable = false;
   				} else {
   					Tableobj = getTable(name);
   					if ( Tableobj == null) {
   						System.out.println("Table " + name + " does not exist");
   					} else {
   						workwithtable = false;
   					}
   				}
			}
			return Tableobj;
    }

// Encapsulate Collections
   public String getPk() {
	return this.Pkey;
   }

   public void setTypePK(String pk) {
	   ColTypes.put(pk, "N");
   }

   public void setType(String colname, String coltype) {
	this.ColTypes.put(colname, coltype.toUpperCase());
   }

   public void setPK(String key) {
	    this.Pkey = key;
   }

   public void add2Table(String key, String value) {
		 this.table.get(key).add(value);
   }

   public Set<String> getKeySet() {
         return Collections.unmodifiableSet(this.table.keySet());
   }

   public boolean keyContainsVal(String key, String val) {
	    if(!getType(key).contains("N")) {
		 return this.table.get(key).contains(val);
		 } else {
			 for(String k : table.get(key)) {
				 try {
				 if(Double.parseDouble(k) == Double.parseDouble(val)) {
					 return true;
				 }
				 }catch(NumberFormatException e) {
					 if(k.equals("null") && val.equals("null")) {
					     return true;
				     } else {
						 return false;
					 }
                 }
			 }
           return false;
	   }
   }

   public String getType(String key) {
	    return this.ColTypes.get(key);
   }

   public  boolean containsKey(String key) {
	    return this.table.containsKey(key);
   }

   public String getValue(String key, int i) {
        return this.table.get(key).get(i);
   }

   public LinkedList<String> getList(String key) {
	    LinkedList<String> l = new LinkedList<String>(table.get(key));
	    return l;
  }

   public static  Table getTable(String name) {
		if(listoftables.containsKey(name)) {
			return  listoftables.get(name);
        } else {
        	return null;
        }
   }
    // return a deep copy of the table.
    public LinkedHashMap<String, LinkedList<String>> createCopy1() {
	 LinkedList<String> list12;
	 LinkedHashMap<String,LinkedList<String>> b = new LinkedHashMap<String, LinkedList<String>>();

	  for (String key : getKeySet()) {
		 list12 = new LinkedList<String>(table.get(key));
         b.put(key, list12);
      }
         return b;
	 }
}


