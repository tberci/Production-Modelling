package resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

enum Status{
	ONE,
	TWO,
	THREE,

}

public class Machine {

	private int id;
	private int nCal;
	private T_TW[] cal;
	
	private List<Integer> jobs;
	private  int[][] setup ;
	
	private int timeSlice;
	private int completedJob;
	private List<T_TW> intervals;
	// this generates random numbers
	private static final Random random = new Random();
	private Status operation;
	private static final Status[] operations = Status.values();
	
	// choose a card at random
	
	
	public Machine(int id, int nCal, T_TW[] cal,int operation_capacity ) {
		
		this.id = id;
		this.nCal = nCal;
		this.cal = cal;
		
		this.completedJob = 0;
		
		
		
	}
	public Machine() {
		// TODO Auto-generated constructor stub
		this.jobs = new ArrayList<>();
		this.intervals = new ArrayList<>();
		
		
		this.setSetup(new int[][] {
            {10, 20, 30},
            {5, 15, 10},
            {10, 15, 5}
        });
        
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getnCal() {
		return nCal;
	}
	public void setnCal(int nCal) {
		this.nCal = nCal;
	}
	
	public T_TW[] getCal() {
		return cal;
	}
	public void setCal(T_TW[] cal) {
		this.cal = cal;
	}	

	public int getTimeSlice() {
		return timeSlice;
	}
	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}
	
	 public List<Integer> getJobs() {
		return jobs;
	}
	public void setJobs(List<Integer> jobs) {
		this.jobs = jobs;
	}
	public void addJobIndex(int index) {
	        this.jobs.add(index);
	    }
	
	public List<T_TW> getIntervals() {
		return intervals;
	}
	public void setIntervals(List<T_TW> intervals) {
		this.intervals = intervals;
	}  
	     
	
	
	/*switch (r % 3) {
	        case 0:
	            res[r].setOperation(Status.ONE);
	            break;
	        case 1:
	        	res[r].setOperation(Status.TWO);
	            break;
	        case 2:
	        	res[r].setOperation(Status.THREE);
	            break;
	        // további case-ek a többi enum értékhez
	        default:
	            throw new IllegalArgumentException("Nem definiált enum érték a gép számához: " + r);
	    }*/
	public Machine[] create_res_data( int NR )
	{
	  Machine[] res;
	  
	  int r ;
	  int c; //idoablakok indexe

	  res = new Machine[NR];  //struktura vektor
	  
	  for ( r=0; r<NR; r++ )
	    { 
		  res[r] = new Machine();
		 
		  
	      res[r].id = r;
	      res[r].nCal = 2;
	    
	      res[r].cal  = new T_TW[res[r].nCal];
	      //res[r].setOperation_capacity(4);
	     
	      
	      res[r].setOperation(Status.ONE);
	      
	      System.out.println(res[r].getOperation());
	     // 
	      
	      
	      for ( c=0; c<res[r].nCal; c++) {
	      res[r].cal[c] = new T_TW(); 
	     
	      }
	      
	      res[r].cal[0].ST = 360;
	      res[r].cal[0].ET = 3000;
	      res[r].cal[1].ST = 5000;
	      res[r].cal[1].ET = 10000;
	    }
	  return res;
	}

	

	
	
	public int Load_operation_to_calendar(long st, long et, Machine[] res, int r) {
	    int shift_number = -1;

	    long size = et - st;
	    long new_st = st;
	    long new_et = et;

	    int c = 0;
	    while (c < res[r].nCal) {
	        if (res[r].cal[c].ET > new_st) {
	            new_st = Math.max(new_st, res[r].cal[c].ST);
	            new_et = new_st + size;

	            if (new_et <= res[r].cal[c].ET) { //belefer
	                shift_number = c;
	                break;
	            } else {
	                c++;
	                if (c >= res[r].nCal) {
	                    new_st = res[r].cal[c - 1].ET; //vegere illeszt
	                    new_et = new_st + size;
	                    break;
	                }
	                continue;
	            }
	        }
	        c++;
	    }
	    
	    st = new_st;
	    et = new_et;

	    return shift_number;
	}
	
	public double max_l(long a, long b){
	    return a>b ? a:b;
	}
	public int getCompletedJob() {
		return completedJob;
	}
	public void setCompletedJob(int completedJob) {
		this.completedJob = completedJob;
	}
	public Status getOperation() {
		return operation;
	}
	public void setOperation(Status operation) {
		this.operation = operation;
	}
	public static Status getRandomStatus() {
        return operations[random.nextInt(operations.length)];
    }
	public int[][] getSetup() {
		return setup;
	}
	public void setSetup(int[][] setup) {
		this.setup = setup;
	}

	
	
	
}
