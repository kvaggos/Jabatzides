package mypkg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Predicate;
//class that contains the predicates used for filtering done when searching(Conditions)
public class Predicates {
    //predicate used for string and character columns when the condition is "="
	public static Predicate<Integer> isEquals(LinkedList<String> l1, String colVal) {
		    return  (t) -> l1.get(t).equals(colVal);
	}
    //predicate used for character columns when the condition is ">"
	public static Predicate<Integer> isGreater(LinkedList<String> l1, String colVal) {
			return  (t) -> l1.get(t).compareTo(colVal) < 0;
	}
    //predicate used for character columns when the condition is "<"
	public static Predicate<Integer> isLesser(LinkedList<String> l1, String colVal) {
			return  (t) -> l1.get(t).compareTo(colVal) > 0;
	}
    //predicate used for numeric columns when the condition is "="
	public static Predicate<Integer> isEqualsN(ArrayList<Double> l1, double colVal) {
			return  (t) -> l1.get(t) == colVal;
	}
    //predicate used for numeric columns when the condition is ">"
	public static Predicate<Integer> isGreaterN(ArrayList<Double> l1, double colVal) {
	        return  (t) -> l1.get(t) > colVal;
	}
    //predicate used for numeric columns when the condition is "<"
	public static Predicate<Integer> isLesserN(ArrayList<Double> l1, double colVal) {
		    return  (t) -> l1.get(t) < colVal;
	}

    //method used in order to get the approriate predicate considering condition type and column type
	public static Predicate<Integer> getCorrectPredicate(String condition, String Type, LinkedList<String> l1, String colVal) {
		if(Type.equals("N")) {
			double s = 0.0;
			try {
			   s = Double.parseDouble(colVal);
			 }catch(NumberFormatException e) {
				 s = 0.0;
			 }
			 ArrayList<Double> l = getRightArrayList(l1, colVal, condition);
			if(condition.equals("=")) {
				return isEqualsN(l, s);
            } else if(condition.equals("#")) {
				   return isEqualsN(l, s).negate();
		    } else if(condition.equals(">")) {
				   return isGreaterN(l, s);
		    } else {
				   return isLesserN(l, s);
		    }
	     } else {
			 if(condition.equals("=")) {
			 	 return isEquals(l1, colVal);
			  } else if(condition.equals("#")) {
			 	  return isEquals(l1, colVal).negate();
			  } else if(condition.equals(">")) {
			 	  return isGreater(l1, colVal);
			  } else  {
				 return isLesser(l1, colVal);
		     }
	   }
   }
   // converts the string list to a double type list, while also handling the various "null" cases
   public static ArrayList<Double> getRightArrayList(LinkedList<String> l1, String k, String condition) {
	   double w = 0.0;
	   ArrayList<Double> l = new ArrayList<Double>();
	    for(String s : l1) {
			try {
			   w = Double.parseDouble(s);
			   l.add(w);
		   } catch(NumberFormatException e) { // handle null cases
			   if(condition.equals("=") || condition.equals("#")) {
				   if(k.equals("null")) {
					   l.add(0.0);
				   } else {
					   l.add(Double.parseDouble(k) + 1);
				   }
			   } else if(condition.equals(">")){
				   double ss = Double.parseDouble(k);
				   double s1 = ss >= 0 ? ss - 1 : ss + 1;
				   l.add(s1);
			   } else if(condition.equals("<")) {
				   double ss = Double.parseDouble(k);
				   double s1 = ss >= 0 ? ss + 1 : ss - 1;
				   l.add(s1);
			   }
		    }

        }
       return l;
	 }

}

