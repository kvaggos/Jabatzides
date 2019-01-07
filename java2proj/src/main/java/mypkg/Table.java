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
	private Map<String, LinkedList<String> > table = new LinkedHashMap<String, LinkedList<String>>(); //Map that contains the columns and the values of every table
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
	setRows(0); 
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
      //method that returns a list with the correct format for every column that is requested.
     public ArrayList<String> findMaxLength(ArrayList<String> colList , int size, List<Integer> l ) {
        //first find the max length of a value of a column.
		int[] maxLengths = new int[size];
		int s = -1;
		   for(String col : colList) {
	                 s++;
		         maxLengths[s] = col.length();
			    for(int i : l) {
				maxLengths[s] = Math.max(maxLengths[s], table.get(col).get(i).length());
			    }
		    }
         //then knowing the max length, create a list containing the correct format for each column.
		 StringBuilder formatBuilder = new StringBuilder();
		 ArrayList<String> maxF = new ArrayList<String>();
		    for (int maxLength : maxLengths) {
		       maxF.add(formatBuilder.append("%-").append(maxLength).append("s").toString());
		       formatBuilder.setLength(0);
                    }
		 return maxF;
    }

   // method that dynamically outputs the table.
   public void viewTable(LinkedHashMap<String, LinkedList<String>> a, ArrayList<String> colList, List<Integer> l) {
	              if(a.keySet() == null) {
			   System.out.println("No Data");
			   return;
		      }
		    System.out.println();
		    ArrayList<String> maxLengths = findMaxLength(colList, colList.size(), l); 
		    int sum = 0;
		      for (String s : maxLengths) {
				int k = Integer.parseInt(s.substring(2,s.length() - 1)); // get the maxLength from the format.
				sum += k; //sum of total lengths. Used to print the correct amount of --- later.
		      }
		     sum += (colList.size() - 1) * 3 + 1; //(colList.size() - 1) * 3  -> accounting for " | ". Plus 1 because at last column we only print "|" as seen below.
		     int i = -1;
			   for(String key : colList) { //printing the column names
				 i++;
			         System.out.format(maxLengths.get(i), key);
			      if(i != colList.size() - 1) {
			        System.out.print(" | ");
			      } else {
				System.out.print("|");
		              }
			   }
			System.out.println();
			ArrayList<Integer> crossPosition = new ArrayList<Integer>();
			int h = -1;
			int sum2 = 0;
			  for(String f : maxLengths) {
				h++;
				 if(h != colList.size() - 1) { //finding the position where the crosses must be placed, where the | intersect.
					 int kk = Integer.parseInt(f.substring(2,f.length() - 1)) + 2;
					 sum2 += kk;
				         crossPosition.add(sum2);
				         sum2++;
				 } else {
				        crossPosition.add(sum2 + Integer.parseInt(f.substring(2,f.length() - 1)) + 1);
			         } 
			  }
			 int d = 0;
			 for(int ii = 1;ii<= sum;ii++) {
				  if(ii != crossPosition.get(d)) {
				    System.out.print("-");
				   } else {
				    System.out.print("+");
				    d++;
			           }
			 }
		      System.out.println();
                        for(int k : l){
			    int p = -1;
 			     for(String key2 : colList){ //printing the values row by row.
					 p++;
 					 System.out.format(maxLengths.get(p),  a.get(key2).get(k));
 					  if(p != colList.size() - 1) {
					     System.out.print(" | ");
					  } else {
					    System.out.print("|");
		                          }
 			     }
 		           System.out.println();
 		           d = 0;
		             for(int ii = 1;ii<= sum;ii++) {
				 if(ii != crossPosition.get(d)) {
				      System.out.print("-");
				 } else {
				    System.out.print("+");
				    d++;
				 }
			     }
		     System.out.println();
		   }
            System.out.println();
    }

	
}


