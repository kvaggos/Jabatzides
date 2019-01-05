package mypkg;

import java.util.ArrayList;
import java.util.Comparator;

public class Comparators {
    int j = 0;
    ArrayList<Integer> sortingMethodReturns = new ArrayList<Integer>();

	Comparator<String> getAttribute1Comparator() {
	       return new Comparator<String>() {
				@Override
			    public int compare(String lhs, String rhs) {
		           double x=0,z=0;
				    if(lhs.matches("-?\\d+")) {
				       x = Integer.parseInt(lhs);
		       	    }
				   if(lhs.matches("-?[0-9]*\\.?[0-9]*")) {
						 x = Double.parseDouble(lhs);
				   }
				   if(rhs.matches("-?\\d+")) {
					  z = Integer.parseInt(rhs);
					}
				   if(rhs.matches("-?[0-9]*\\.?[0-9]*")) {
					 z = Double.parseDouble(rhs);
				  }
				  int returning=0;
				   if (x  < z) {
					  returning = -1;
				  } else if (x > z) {
					  returning = 1;
				  } else {
				    returning =  0;
				  }
			    sortingMethodReturns.add(returning);
				return returning;
	          }
	     };
       }
	 public void setJ() {
		this.j = 0;
	 }
         Comparator<String> getAttribute2Comparator() {
		        return new Comparator<String>() {
					@Override
					public int compare(String lhs, String rhs) {
							int returning = lhs.compareTo(rhs);
							sortingMethodReturns.add(returning);
							return returning;
		             }

	            };
       }
          Comparator<String> getAttribute3Comparator() {
   		        return new Comparator<String>() {
   			    @Override
   		            public int compare(String lhs, String rhs) {
                                int returning = sortingMethodReturns.get(j);
                                j++;
                                return returning;
   		            }

   		        };
          }

}
