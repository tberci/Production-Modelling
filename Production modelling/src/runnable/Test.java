package runnable;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import products.Component;
import products.Product;
import resources.Job;
import resources.Machine;
import resources.Order;


public class Test  {


	public static void main(String[] args) throws IOException {
		
		

		List<Order> orders = new ArrayList<>();
		
		String file = "src\\resources\\csvtest.csv";
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(file));
		//br.readLine();
		while((line = br.readLine()) != null) {
			String[] values = line.split(";");
			
			int id = Integer.parseInt(values[0]);
			String product_name = values[1];
			int deadline = Integer.parseInt(values[2]);
			int order_number = Integer.parseInt(values[3]);
			String customer_name = values[4];
			
			orders.add(new Order(id,new Product(product_name,select_component(product_name) ),deadline,customer_name,order_number));
				
		}
		
		for(Order order : orders) {
			System.out.println("id :" + order.getId() + ", product :"+ order.getProduct().getName() +", procT :"+ order.getProduct().getProcessing_time() + ", deadline : " + order.getDeadline() + ", order number :" + order.getNumber() + ", customer : "+ order.getCustomer_name());
		}
		
		br.close();
		
		
		int NJ = orders.size();
			

	///////////////////////////////////////////////////////////////////////////////////////////////////////	
		
		  int NOBJF = 5;
		  int capacity =6;
		  int NR = 3; //eroforrasok szama
		  Job job = new Job();
		  int materials = 2000;
		  int[] s;  
		  long t_ref = 0; //nulla referencia inditasi idopont
		  double[] f = new double[NOBJF];
		  double[] w = {100,0,0,0,0}; //prioritas, suly
		  int K = NOBJF;
		  Machine[] res = null;
		  Job[] jobs;
		  Machine machine = new Machine();

		  //modellepites
		  res = new Machine[NR]; 
		  s = new int[NJ];
		  
		  jobs =job.create_input_data(NJ,orders, NR);
		  res = machine.create_res_data(NR);
		  //utemterv
		  
		  //job.operation_schedule( s, NJ, jobs); 
		  job.edd(jobs, s);   //edd szerint sorrend
		// s[0]= 3;
		// s[1]= 2;
		 //s[2]= 1;
		 //s[3]= 0;
		  for(int i= 0; i< s.length; i++) {
	    	   System.out.println("indulo" +s[i]);
	       }
		  //szimulacio
		  //job.Simulate_FSS(jobs, NJ, s, t_ref, NR, res, f, capacity);
		  job.Simulation_FS( jobs, NJ, NR, s, t_ref,res,capacity);
		  
		
		  job.Print_Gant_table_FS(jobs, NJ,  s, NR,res, materials,capacity);
		  //job.Gantt_FS(jobs, NJ, s, t_ref, NR,res,f, capacity);
		  //kiertekeles
		  //Print_Res_Gant_table( job, NJ, NR, s );
		  job.Evaluate(jobs, NJ,NR, s, f,res);
		 // job.resetStartAndEndTimes(jobs, NJ, NR);
		  //job.Print_Gant_table_FS(jobs, NJ,  s, NR,res);
		  job.print_f(f);
		
		//innen folytatjuk
		  //STEP, LOOP
		  
		   System.out.println("\n\n Fm esetre a Search-algoritmus");
		   
		   job.Local_Search_with_multi_objf( 100,100, jobs, NJ, NR, s, t_ref, w, K,NOBJF, res,f, capacity);
	       //job.Fm_Search_alg( 100,100, jobs, NJ, NR, s, t_ref, w, K,NOBJF, res,f, capacity);
		   job.Simulation_FS( jobs, NJ, NR, s, t_ref,res,capacity);
			
	       job.Evaluate(jobs, NJ,NR, s, f,res); 
	       
	       job.Print_Gant_table_FS(jobs, NJ,  s, NR,res,materials,capacity);
	       job.print_f(f);
	
	       
	       for(int i= 0; i< s.length; i++) {
	    	   System.out.println("szimututan" +s[i]);
	       }
	
	       
    }
	
	private static List<Component> select_component(String product_name) {
		
		List<Component> product_component = new ArrayList<>();
		Component neon = new Component("neoncso",1,5,1);
		Component alvany1 = new Component("alvany",1,10,2);
		Component alvany2 = new Component("alvany",1,15,2);
		Component alvany3 = new Component("alvany",1,30,2);
		
		Component bura = new Component("bura",1,30,1);
		product_component.add(neon);
		product_component.add(bura);
		
		switch(product_name) {
		case "lampa1" :product_component.add(alvany1); break;
		case "lampa2" :product_component.add(alvany2); break;
		case "lampa3" :product_component.add(alvany3); break;
		
		}
		
		return product_component;
	}

	
}
