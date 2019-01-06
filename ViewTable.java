package mypkg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.*;

//class responsible for printing the table, as well as printing table based on conditions, viewing stats and viewing table sorted by a column.
public class ViewTable {
    String resp;
    Scanner s2 = new Scanner(System.in);

	public void viewTable() {

		if(Table.noTables()) {
			System.out.println("You need to first create a table. Exiting.");
			return;
		}

		Table myTable = Table.searchTable();
		if(myTable == null) {
			return;
		}
       boolean endcheck = true;

		while(endcheck) {

			getMenu();
			resp = s2.nextLine();

			if(resp.equals("1")){
			  printTable(myTable, myTable.createCopy1());
			} else if (resp.equals("2")){
				viewSortBy(myTable);
			} else if (resp.equals("3")){
				customViewTable(myTable);
			} else if (resp.equals("4")){
				 viewStats(myTable);
		    } else {
			   return;
		    }
	    }
   }
      //view table based on conditions
	  public void customViewTable(Table myTable) {
		  if(myTable.getRowNo() == 0) {
			  System.out.println("Please first add some rows to access this functionality");
			  return;
		  }
		  ArrayList<String> colList = new ArrayList<String>();
		  ArrayList<Integer> srcSet = new ArrayList<Integer>();
		  String colName = "", coltyp = "";
		  int maxI = 0;

		  maxI = myTable.getRowNo();
		  for(int i = 0; i < maxI; i++) {
			  srcSet.add(i);
		  }
		  colList = selectColumns(myTable); //user selects which columns he would like to view
		   if(colList.isEmpty()) {
			   System.out.println("No columns to view. Exiting");
			   return;
		   }
           System.out.println("would you like to enter a condition?[Y/any other key])");
		   resp = s2.nextLine();
		    if(!resp.equalsIgnoreCase("y")) { //if user doesnt want to enter conditions then the columns he selected are printed
				myTable.viewTable(myTable.createCopy1(), colList, srcSet);
			    return;
			}
			Predicate<Integer> d;
			String colComp="" , conVal=""; 
			boolean b = true;
			List<Integer> resSet = new ArrayList<Integer>(); // in this list there sre stored the indexes that must be printed
             while(b) {
			   colName = getColName(myTable); //get the name of the column of the condition
			   coltyp = myTable.getType(colName).substring(0, 1); //get the type of that column
		       colComp = getColComp(colName, myTable); //get the type of condition(=,#,<,>)
		       conVal = getColVal(colName, myTable); //get the condition value
			  if(createShortcut(coltyp,colName, conVal, myTable, resSet, colComp, srcSet)) { 
			    d = Predicates.getCorrectPredicate(colComp, coltyp, myTable.getList(colName),conVal); //get the correct predicate
			    resSet = srcSet.parallelStream().filter(d).collect(Collectors.toList()); //get the indexes that match the condition
			  }
			   if(resSet.isEmpty()) {
				  System.out.println("No data found matching the condition");
				  return;
			   }
			srcSet.retainAll(resSet); //retain the indexes matching the condition.
			myTable.viewTable(myTable.createCopy1(), colList, srcSet);
			resSet.clear();
			 if(srcSet.size() == 1) {
				System.out.println("Press any key to exit");
				resp = s2.nextLine();
				return;
			 }
			System.out.println("Would you like to add more conditions?[Y/N] ");
			resp = s2.nextLine();
			 if (!resp.equalsIgnoreCase("y")) {
				b = false;
			 }
     }
	}
    // this method returns the columns the user would like to view
	public ArrayList<String> selectColumns(Table myTable) {
		boolean a = true;
		int s = 0;
		ArrayList<String> colList = new ArrayList<String>();
		System.out.println("Select which columns you would like to view. Press enter to exit");
		 while(a && s < myTable.getMappings()) {
		   resp = s2.nextLine();
			 if(resp.equals("")) {
				a = false;
			 } else if(myTable.containsKey(resp)) {
				s++;
				colList.add(resp);
			 } else {
			    System.out.println("The column name does not exist");
			}
		 }
		 return colList;
    }
     // this method returns the colVal the condition value
	 public  String getColVal(String colName, Table myTable) {
		  String t = "";
		  String coltyp = myTable.getType(colName).substring(0, 1);
		  Add2Table.getTypes(coltyp, colName);
		  String conVal = s2.nextLine();
		   if(conVal.trim().isEmpty() || conVal.equalsIgnoreCase("NULL")) {
			  t = "null";
		   } else if(conVal.matches("^-?[0-9]+(\\,[0-9]+)?$")) {
			  t = conVal.replace(",",".");
		   } else {
			   t = conVal;
		   }
		   return t;
     }

     //this method returns the name of the condition column
	 public  String getColName(Table myTable) {
			System.out.println("Select condition column");
			String colName = "";
		    boolean a = true;
		    while(a) {
			 resp = s2.nextLine();
			  if(myTable.containsKey(resp)) {
				a = false;
				colName = resp;
			   } else {
			    System.out.println("The column name does not exist");
			  }
		     }
		     return colName;
		}
      //this method returns the chosen condition type
	  public  String getColComp(String colName, Table myTable) {
		 String coltyp = myTable.getType(colName).substring(0, 1);
		 String colComp = "";
		      if(coltyp.equals("S")) {
				System.out.println("Enter a contition. Choose one of the symbols(= or #)");
			  } else {
			    System.out.println("Enter a contition. Choose one of the symbols(<,=,>,#)");
			   }
		  boolean a = true;
		   while(a) {
			String resp = s2.nextLine();
			 if((resp.length() == 1) && (("<=>#".contains(resp)) && (!coltyp.equals("S"))) ||
			   (("=#".contains(resp)) && (coltyp.equals("S")))) {
					a = false;
				    colComp = resp;
				} else {
			     System.out.println("Invalid condition. Please try again");
				}
		    }
		    return colComp;
		}
    //this method takes advantage of the constraints of each column to create ashortcut that speeds up the program
	public boolean createShortcut(String coltyp, String colName, String conVal, Table myTable, List<Integer> resSet, String colComp, ArrayList<Integer> srcSet ) {

		boolean whiteS = false;
		  if(coltyp.equalsIgnoreCase("n")) {
		       for(char c : conVal.toCharArray()) {
				  if(Character.isWhitespace(c)) {
					   whiteS = true;
				  }
		        }
		  }
		 if(conVal.equals("null") && (colComp.equals("<") || colComp.equals(">"))) {
			 System.out.println("Cannot perform this action with null values");
			 return false;
		  }

         if(coltyp.equalsIgnoreCase("N") && !conVal.matches("-?[0-9]*(.)?[0-9]+?$") || whiteS) {
		    if(!conVal.equals("null")) {
		      System.out.println("Incompatible Types");
		      return false;
	        }
		 } else if(coltyp.equalsIgnoreCase("C") && !conVal.replaceAll("\\s+","").matches("[a-zA-Z]+$")) {
			 if(!conVal.equals("null")) {
			   System.out.println("Incompatible Types");
			   return false;
		     }
	     }
		 if(myTable.getType(colName).contains("2") || colName.equals(myTable.getPk())) {
			int i = myTable.findUnique(colName, conVal, srcSet, coltyp);
			 if(colComp.equals("=")) {
			  if(i >= 0) {
				 resSet.add(i);
				 return false;
			   } else {
				 return false;
		       }
	      }else if(colComp.equals("#")) {
	        	if(i >= 0) {
	        		resSet.addAll(srcSet);
	        		resSet.remove(i);
	        	} else {
					resSet.addAll(srcSet);
				}
				return false;
		      }
	       }
		  return true;
	 }
     //this method sorts a chosen column and then prints the table with all the other column sorted by the soring done to the chosen column.
	 public void viewSortBy(Table myTable) {

         if(myTable.getRowNo() == 0) {
		    System.out.println("Please first add some rows to access this functionality");
		    return;
		 }

		Comparators c = new Comparators();
        String colby;
         LinkedHashMap<String, LinkedList<String>>  ss = myTable.createCopy1();
         //ss = myTable.createCopy1();
		 System.out.println("Input the name of a numeric or a character Column to sort the table by. Press enter to exit.");
	     colby = checkColumn(myTable);
	     if(colby.equals("")) {return;}
	     System.out.println("Would you like the sorting to be done in (A)scending or (D)escending order?");
	     resp = s2.nextLine();
	       while(!resp.equalsIgnoreCase("a") && !resp.equalsIgnoreCase("d")) {
	        System.out.println("Invalid input. Please try again");
	        resp = s2.nextLine();
		   }
        if(myTable.getType(colby).equals("N")) {
		   if(!resp.equalsIgnoreCase("d")) {
		     Collections.sort(ss.get(colby), c.getAttribute1Comparator());
		   } else {
		        Collections.sort(ss.get(colby), Collections.reverseOrder(c.getAttribute1Comparator()));
		   }
		} else {
			    if(resp.equalsIgnoreCase("d")) {
				  Collections.sort(ss.get(colby), c.getAttribute2Comparator());
			    } else {
		          Collections.sort(ss.get(colby), Collections.reverseOrder(c.getAttribute2Comparator()));
	            }
		 }
          for(String key : myTable.getKeySet()) {
			if (!key.equals(colby)) {
			 Collections.sort(ss.get(key), c.getAttribute3Comparator());
			 c.setJ();
	   		}
		  }
		printTable(myTable, ss);
   }
   //this method is called to output the whole table, creating the necessary arguments to give to the viewTable method.
   public void printTable(Table myTable, LinkedHashMap<String, LinkedList<String>> a) {
	    int s = myTable.getRowNo();
	    List<Integer> l = IntStream.rangeClosed(0, s - 1).boxed().collect(Collectors.toList());
	    ArrayList<String> mainList = new ArrayList<String>();
	   	mainList.addAll(myTable.getKeySet());
	    myTable.viewTable(a,mainList, l);
   }
   //this method outputs the stats of a given numeric column.
   public void viewStats(Table myTable) {
     if(myTable.getRowNo() == 0) {
	 	 System.out.println("Please first add some rows to access this functionality");
	 	  return;
	  }
     String max, min;
     boolean s = true;
     String exit = "or press enter to exit";
     Comparators c = new Comparators();
      while(s) {
       System.out.println("Press 1 to view max, 2 to view min, 3 to view sum, 4 to view average, any other key to exit");
       resp = s2.nextLine();
         if(resp.equals("1")) {
	       getMax(exit, myTable);
	     }else if(resp.equals("2")) {
		    getMin(exit, myTable);
         }else if(resp.equals("3")) {
			printSum(exit, myTable);
         }else if(resp.equals("4")) {
			getAvg(exit, myTable);
		 } else {
			s = false;
		  }
     }
   }


   public void getAvg(String exit, Table myTable) {
	  System.out.println("Select a numeric column to view the average " + exit);
	  resp = checkColumn(myTable);
	   if(!resp.trim().isEmpty()) {
	       double avg =  getSum(myTable.getList(resp)) / myTable.getRowNo();
		   System.out.printf("The average of column " + resp + " is %.2f\n",avg);
		   System.out.println();
	   }
  }

   public void printSum(String exit, Table myTable) {
	     System.out.println("Select a numeric column to view the sum " + exit);
	   	 resp = checkColumn(myTable);
	      if(!resp.trim().isEmpty()) {
	         double sum = getSum(myTable.getList(resp));
	   		   if(sum % 1 == 0) {
	   		     sum = (int)sum;
	   	       }
	         System.out.println("The sum of column " + resp + " is " + sum);
          }
   }

   public void getMin(String exit, Table myTable) {
	   String min;
	   Comparators c = new Comparators();
	   	System.out.println("Select a numeric column to view the min value " + exit);
	    resp = checkColumn(myTable);
	   	  if(!resp.trim().isEmpty()) {
			 if(checkNulls(myTable.getList(resp))) {
			  LinkedList<String> l = myTable.getList(resp);
			  l.removeAll(Collections.singleton("null"));
	   		  min = Collections.min(l,c.getAttribute1Comparator());
		      System.out.println("The min value of column " + resp + " is " + min);
	         } else {
			  System.out.println("Cannot perform this action. Null values only.");
		     }
		 }
   }
   

   public void getMax(String exit, Table myTable) {
	  String max;
	  Comparators c = new Comparators();
	  System.out.println("Select a numeric column to view the max value " + exit);
	  resp = checkColumn(myTable);
	   	 if(!resp.equals("")) {
		   if(checkNulls(myTable.getList(resp))) { 
			 LinkedList<String> l = myTable.getList(resp);
			 l.removeAll(Collections.singleton("null"));
	   	     max = Collections.max(l,c.getAttribute1Comparator());
		     System.out.println("The max value of column " + resp + " is " + max);
	       } else {
			 System.out.println("Cannot perform this action. Null values only.");
		   }
	     }
   }
   //this method checks to see if a column is consisted only from null values. If so then the above functionalities are meaningless.
   public boolean checkNulls(LinkedList<String> l) {
	   boolean ss = false;
	   for(String s : l) {
		   if(!s.equals("null")) {
		     ss = true;
		   }
	   }
	   return ss;
   }
   //this method is used to return a val column name so to begin the above functionalities
   public String checkColumn(Table myTable) {
		   boolean a = true;
		   while(a) {
			   resp = s2.nextLine();
			   if(resp.trim().isEmpty()) {
				   a = false;
			   }else if(!myTable.containsKey(resp)) {
			       System.out.println("Column " + resp + " doesnt exist");
			   } else if(!myTable.getType(resp).contains("N") && !myTable.getType(resp).contains("C")) {
				   System.out.println("Column " + resp + " is not numeric or character");
		       } else {
				  a = false;
			    }
	      }
	      return resp;
	 }


      public double getSum(LinkedList<String> l) {
		 double sum = 0.0;
		 for(String s : l) {
			 try {
			 sum += Double.parseDouble(s);
		    }catch(NumberFormatException e) {
			}
			 }
		 return sum;
	   }

	  public void getMenu() {

		   System.out.println("Make a choice from 1-4. Press any other key to exit to main menu");
		   System.out.println("1. View Whole table ");
		   System.out.println("2. View table sorted by a column(Must be character or numeric column)");
		   System.out.println("3. Custom View Table ");
		   System.out.println("4. View Stats of a column.(Must be numeric)");
      }

}
