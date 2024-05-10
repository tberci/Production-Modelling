package resources;

import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Job {

	private int id; 
	private long[] ProcT; // muveleti ido
	private long DueDate; // hatar ido
	private long[] StartT; // inditas idõpontja
	private long[] EndT; // befejezes idõpontja
	private long L; // szamitott ertek
	private int hops; //folyamatok száma
	private int timeSlice;
	public int machine;
	private resources.Status status;
	private int count;
	private int order_count;
	private String pname;
	private int[] t;

	public Job() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long[] getProcT() {
		return ProcT;
	}

	public void setProcT(long[] procT) {
		ProcT = procT;
	}

	public long getDueDate() {
		return DueDate;
	}

	public void setDueDate(long dueDate) {
		DueDate = dueDate;
	}

	public long[] getStartT() {
		return StartT;
	}

	public void setStartT(long[] startT) {
		StartT = startT;
	}

	public long[] getEndT() {
		return EndT;
	}

	public void setEndT(long[] endT) {
		EndT = endT;
	}

	public long getL() {
		return L;
	}

	public void setL(long l) {
		L = l;
	}

	public int getMachine() {
		return machine;
	}

	public void setMachine(int i) {
		this.machine = i;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTimeSlice() {
		return timeSlice;
	}

	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}

	public resources.Status getStatus() {
		return status;
	}

	public void setStatus(resources.Status status) {
		this.status = status;
	}

	public int getOrder_count() {
		return order_count;
	}

	public void setOrder_count(int order_count) {
		this.order_count = order_count;
	}

	public int getHops() {
		return hops;
	}

	public void setHops(int hops) {
		this.hops = hops;
	}

	public int[] getT() {
		return t;
	}

	public void setT(int[] t) {
		this.t = t;
	}

	public Job[] create_input_data(int NJ, List<Order> orders, int NR) {

		Job[] jobs = new Job[NJ];

		for (int i = 0; i < NJ; i++) {
			jobs[i] = new Job();

			jobs[i].id = i;
			jobs[i].ProcT = new long[NR];
			jobs[i].StartT = new long[NR];
			jobs[i].EndT = new long[NR];
			
			jobs[i].order_count = orders.get(i).getNumber();
			jobs[i].pname = orders.get(i).getProduct().getName();
			switch (jobs[i].pname) {
				case "lampa1":

					jobs[i].setStatus(Status.ONE);
					jobs[i].t = new int[] { 0, 1 };
					jobs[i].setHops(jobs[i].t.length);
					break;
				case "lampa2":

					jobs[i].setStatus(Status.TWO);
					jobs[i].t = new int[] { 0, 1 };
					jobs[i].setHops(jobs[i].t.length);
					break;
				case "lampa3":

					jobs[i].setStatus(Status.THREE);
					jobs[i].t = new int[] { 0,2 };
					jobs[i].setHops(jobs[i].t.length);
					break;
			}

			for (int r = 0; r < NR; r++) {
				switch (r % 3) {
		        case 0:
		        	jobs[i].ProcT[r] = orders.get(i).getProduct_processing_time() * orders.get(i).getNumber();
		            break;
		        case 1:
		        	jobs[i].ProcT[r] = orders.get(i).getProduct_processing_time() * orders.get(i).getNumber()/2;
		            break;
		        case 2:
		        	jobs[i].ProcT[r] = orders.get(i).getProduct_processing_time() * orders.get(i).getNumber()/3;
		            break;
		        // további case-ek a többi enum értékhez
		        default:
		            throw new IllegalArgumentException("Nem definiált enum érték a gép számához: " + r);
		    }
				
				
			}
			jobs[i].setDueDate(orders.get(i).getDeadline());
			System.out.println(jobs[i].toString());
		}

		return jobs;

	}

	public int[] operation_schedule(int[] sch, int NJ, Job[] jobs) {
		// statusz alapú munka rendezés
		Arrays.sort(jobs, new Comparator<Job>() {
			@Override
			public int compare(Job j1, Job j2) {
				return j1.status.compareTo(j2.status);
			}
		});

	
		/*for (Status status : Status.values()) {
			for (Job job : jobs) {
				if (job.status == status) {
					System.out.println(job.id + " - " + job.status);
				}
			}
		}*/

		
		for (int i = 0; i < NJ; i++) {
			sch[i] = i;
		}
		return sch;
	}

	public void edd(Job[] jobs, int[] schedule) {
		int n = jobs.length;
		boolean[] assigned = new boolean[n];
		Arrays.fill(assigned, false);

		for (int i = 0; i < n; i++) {
			int earliestDueDate = Integer.MAX_VALUE;
			int jobIndex = -1;
			for (int j = 0; j < n; j++) {
				if (!assigned[j] && jobs[j].DueDate < earliestDueDate) {
					earliestDueDate = (int) jobs[j].DueDate;
					jobIndex = j;
				}
			}
			assigned[jobIndex] = true;
			schedule[i] = jobIndex;
		}
	}

	

	public void Evaluate(Job[] job, int NJ, int NR, int[] sch, double[] f, Machine[] machines) { 
																										
		double Cmax = 0;
		double Csum = 0;
		double L = 0, Lmax = 0;
		double Tsum = 0, T;
		double Usum = 0;
		long lastJobEndTime = -1; 
	
		for (int i = 0; i < NJ; i++) {
	
			long lastEndT = 0;
			
			for (int r = 0; r < machines.length; r++) {
			
				if (job[i].EndT[r] > lastEndT) {
					lastEndT = job[i].EndT[r];
				}
				if (job[sch[i]].EndT[r] > lastJobEndTime) {
					lastJobEndTime = job[sch[i]].EndT[r];
				}
				L = job[sch[i]].EndT[r] - job[sch[i]].DueDate;
		        if (L > Lmax) {
		        	Lmax = L;
		        }
				
			}
			// Tsum += lastEndT + Lmax;
			Tsum +=  Lmax;
			

			T = Math.max(0.0, L);

			if (T > 0)
				Usum++;

			Cmax = lastJobEndTime;
			Csum += lastEndT;
			
			
		}

		f[0] = Cmax;
		f[1] = Csum;
		f[2] = Lmax;
		f[3] = Tsum;
		f[4] = Usum;
		

	}

	
	public void resetStartAndEndTimes(Job[] job, int NJ, int NR) {
	    for (int i = 0; i < NJ; i++) {
	        for (int r = 0; r < NR; r++) {
	            job[i].StartT[r] = 0;
	            job[i].EndT[r] = 0;
	        }
	    }
	}
	public Job[] Local_Search_with_multi_objf(int STEP, int LOOP, Job[] job, int NJ, int NR, int[] sch, long t_ref,
			double[] w, int K, int NOBJ, Machine[] machines, double[] f, int capacity) { // step : keresõ lépések száma
																							// //LOOP: Max szomszed szama
		
		int step; 
		int loop; 

		int[] s_0; // mindenkori kiindulasi bazis.
		int[] s_act; // aktualisan vizsgalt szomszed
		int[] s_ext; // legjobb szomszed
		int[] s_best; // kereses legjobb megoldasa.

		double[] f_act;
		double[] f_ext;
		double[] f_best;

		s_0 = new int[NJ];
		s_act = new int[NJ];
		s_best = new int[NJ];
		s_ext = new int[NJ];

		f_act = new double[NOBJ]; // megoldáshoz tartozó célfüggvények az f_act , f_ext
		f_ext = new double[NOBJ];
		f_best = new double[NOBJ];
		
		copy_s(s_0, sch, NJ);
		copy_s(s_best, sch, NJ); 
		
		Simulation_FS(job, NJ, NR, s_best, t_ref, machines, capacity); // s_bestet szimul referencia kezdo idovel.
		
		Evaluate(job, NJ, NR, s_best, f_best, machines); // S_bestet akarom szimul, és F_bestbe akarom eltarolni.
		// print_f(f_best);
		
		//resetStartAndEndTimes(job, NJ, NR);
		// ALGORITMUS
		for (step = 1; step <= STEP; step++) { // van bázis abból lehet gyártani a szomszedokat.
			for (loop = 1; loop <= LOOP; loop++) {
				
				Neighbour(s_0, s_act, NJ); // kell kiindulás.
				
				Simulation_FS(job, NJ, NR, s_act, t_ref, machines, capacity); // s_actott szimul
				Evaluate(job, NJ, NR, s_act, f_act, machines); // itt megjelenik az f_act .
				//System.out.println("aaaa" +s_best[0]);
				
				// print_f(f_act);
				if (loop == 1) { // elso vizsgált szomszédoim van akkor megkell jegyezni copyval.
					copy_s(s_ext, s_act, NJ);
					copy_f(f_ext, f_act, K);
				} else { // ha van több szomszéd
							// if(f_ext[0] > f_act[0]){ //csak a Cmaxra megy
					if (0 > F(f_ext, f_act, w, K)) { // ha kisebb mint 0 akkor az f_act jobb mint az f_ext
						copy_s(s_ext, s_act, NJ);
						copy_f(f_ext, f_act, K);
					}
				}
				// legjobb szomszéd lesz a következõ bázis.
				copy_s(s_0, s_ext, NJ);

				// if(f_best[0] > f_ext[0]){} //csak a Cmaxra megy
				if (0 > F(f_best, f_ext, w, K)) {
					copy_s(s_best, s_ext, NJ);
					copy_f(f_best, f_ext, K);
				}
				
				resetStartAndEndTimes(job, NJ, NR);  //kell ,hogy ne akkumulálódjon a job[s[i]].EndT[r] és Start[r] .
			} // step
		}

		// ALGORITMUS

		copy_s(sch, s_best, NJ); // bejovobe visszakell másolni. Itt generálom az eredményt.
		return job;

	}

	private double F(double[] f_x, double[] f_y, double[] w, int K) { // K : célfüggvény komponenseinek száma.
		double a, b;
		int k;
		double D, F;
		F = 0;
		for (k = 0; k < K; k++) {

			a = f_x[k];
			b = f_y[k];

			if (Math.max(a, b) == 0) {
				D = 0;

			} else {
				D = (b - a) / Math.max(a, b);
			}

			F += w[k] * D;
		}

		return F;

	}

	private void copy_s(int[] t, int[] s, int NJ) { // t: target, //s: source . //Hova, Mit, Mennyit.
		int i;
		for (i = 0; i < NJ; i++) {
			t[i] = s[i];
		}
	}

	private void copy_f(double[] t, double[] s, int K) { // t: target, //s: source . //Hova, Mit, Mennyit.
		int i;
		for (i = 0; i < K; i++) {
			t[i] = s[i];
		}
	}

	private void Neighbour(int[] s_0, int[] s_act, int NJ) {
		/* Szomszed kepzese */
		int x;
		copy_s(s_act, s_0, NJ); // s_actba átmás az s_0-t
		x = (int) (Math.random() % NJ); // kiválasztunk egy random elemet. De lehet 1.elem is viszont a 0-nak nincs
										// szomszédja.
		if (x == 0) {
			s_act[NJ - 1] = s_0[x]; // NJ-1 a jobb szélso.
			s_act[x] = s_0[NJ - 1];
		} else { // ha x nek van baloldali szomszédja akkor
			s_act[x - 1] = s_0[x];
			s_act[x] = s_0[x - 1]; // sact másolata x0 nak csakbehoz.
		}

	}

	public void Print_Gant_table_FS(Job[] job, int NJ, int[] sch, int NR, Machine[] machines, int materials,	int capacity) {
		
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		materials = 2000;

		System.out.println("\n Gep-orientalt Gantt diagram");

		for (int r = 0; r < NR; r++) {
			
		
			System.out.println("\n gep : " + r + "setup" + machines[r].getOperation());
			System.out.println(
					"\n\t munka \t indul \t muv \t kesz \t hat \t keses \t hops \t count \t setup \t m.setup ");

			/* for (Integer element : machines[r].getJobs()) {
		            System.out.println(element);
		            
		        }*/
			for (int i = 0; i < NJ; i++) {
				/*for (int j = 1; j < job[sch[i]].t.length; j++) {
					System.out.println(job[sch[0]].EndT[r] );
				}*/
				if (machines[r].getOperation() != job[sch[i]].getStatus()) {

					machines[r].setOperation(job[sch[i]].getStatus());
				}
				int[] machineIndexes = job[i].t;
				

				if (containsIndex(machineIndexes, r)) {

					// machines[r].setOperation_capacity(machines[r].getOperation_capacity() - 1);
					job[sch[i]].count++;
					System.out.println("\n \t" + job[sch[i]].getId() + "\t" + job[sch[i]].StartT[r] + "\t"
							+ job[sch[i]].ProcT[r] + "\t" + job[sch[i]].EndT[r] + "\t" + job[sch[i]].DueDate + "\t"
							+ job[sch[i]].L + "\t" + job[sch[i]].hops + "\t" + job[sch[i]].count + "\t"
							+ job[sch[i]].status + "\t" + machines[r].getOperation());
				}
				materials -= 20 * job[sch[i]].getOrder_count();
				dataset.addValue(materials, "Készletszínt", Long.toString(job[sch[i]].StartT[r]));
				//dataset.addValue(null, "Készletszínt", Long.toString(job[s[i]].EndT[r]));
				while (materials <= 500) {
				
					materials += 1500;
					
					// job[sch[i]].ProcT[r] += (long) 50;

					materials -= 20 * job[sch[i]].getOrder_count();
					dataset.addValue(materials, "Készletszínt", Long.toString(job[sch[i]].StartT[r]));
					//dataset.addValue(null, "Készletszínt", Long.toString(job[s[i]].EndT[r]));

					break;
				}

			}
		
		}
		
		JFreeChart chart = ChartFactory.createLineChart("Beépülõ komponensek és készletszínt idõbeli alakulása",
				"ProcT", "Anyag", dataset, PlotOrientation.VERTICAL, true, true, false);
	
		ChartPanel chartPanel = new ChartPanel(chart);

		JFrame frame = new JFrame("Készlet változás diagramm");
		frame.setContentPane(chartPanel);
		frame.setSize(800, 600);
		frame.setVisible(true);

	}

	public void print_f(double[] f) {
		System.out.println("\n Teljesitmenymutatok erteke:");
		System.out.println("\n Cmax = " + f[0]);
		System.out.println("\n Csum =" + f[1]);
		System.out.println("\n Lmax =" + f[2]);
		System.out.println("\n Tmax = " + f[3]);
		System.out.println("\n Usum = " + f[4]);
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", ProcT=" + ProcT + ", DueDate=" + DueDate + ", StartT=" + StartT + ", EndT=" + EndT
				+ ", L=" + L + "]";
	}

	private boolean containsIndex(int[] array, int index) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == index) {
				return true;
			}
		}
		return false;
	}

	public void Simulation_FS(Job[] job, int NJ, int NR, int[] s, long t0, Machine[] machines, int capacity) {
		//resetStartAndEndTimes(job, NJ, NR);
		int muszak = -1;
		//átállás mátrix.
		int[][] setup = new int[][] {
					/*r1, r2, r3/*
            /*ONE*/  {10, 20, 30},
            /*TWO*/  {5, 15, 10},
            /*THREE*/{10, 15, 5}
        };
     
       int[] move = new int[] {2,3,4}; //anyagmozgatási idõ gépenként.
       
       
		for (int i = 0; i < NJ; i++) {
			// i az inditasi sorrend indexe
			// Arrays.fill(job[s[i]].StartT, 0);
			// Arrays.fill(job[s[i]].EndT, 0);

			int prevT = -1;
			for (int j = 0; j < job[s[i]].t.length; j++) {
				if (j > 0) {

					prevT = j - 1;
				}
			}
			
			int[] machineIndexes = job[i].t;
			for (int r = 0; r < NR; r++) {

				if (job[s[i]].count == job[s[i]].hops) { // lejön a géprõl ha a folyamatok száma elérte a maximumot.
					continue;
				} else if (containsIndex(machineIndexes, r)) {
					
					machines[r].addJobIndex(s[i]);
					int prevjob = -1;
					for (int k = 0; k < machines[r].getJobs().size(); k++) {
						if (k > 0)
							prevjob = k - 1;
						// System.out.println("a" +prevjob);

					}
					//job[s[i]].StartT[r] = 0;
					if (s[i] == machines[r].getJobs().get(0)) { // azt vizsgalom ,hogy kezdo gepen elso munka
																// megegyezik e az i-edik soron következõvel.
						// kezdo munka
						if (r == 0 && i >0) {
							// kezdo munkahely
							
							job[s[i]].StartT[r] =  Math.max(job[s[i-1]].EndT[r],  t0 + machines[r].getCal()[0].ST);

							// ref. idoben indul
						} else {
							// nem kezdo munkahely
							// job[s[i]].StartT[r] = 100;
							if (machines[r].getJobs().get(0) == s[0] && job[s[i]].count == 0) {
								job[s[i]].StartT[r] = t0 + machines[r].getCal()[0].ST;

							} else {
								if (job[s[i]].count == 0 && machines[r].getJobs().get(0) == s[i]) {

									job[s[i]].StartT[r] = Math.max(job[machines[r].getJobs().get(prevjob)].EndT[r],job[s[i - 1]].EndT[r - 1]); //megkell nézni az elõzõ munkát is .
								} else {

									job[s[i]].StartT[r] = Math.max(job[s[i]].EndT[job[s[i]].t[prevT]],job[s[i]].EndT[r - 1]); // elozo munkahelyen a bef.
											
								}
							}
						}
					} else {
						// nem kezdo munka
						if (r == 0) {
							// kezdo munkahely
							
								job[s[i]].StartT[r] = Math.max(job[s[i]].EndT[job[s[i]].t[prevT]],job[machines[r].getJobs().get(prevjob)].EndT[r]); // elozo munka bef.
							
						} else {
							// nem kezdo munkahely
							// job[s[i]].StartT[r]

							if (job[s[i]].count == 0 && i > 0) {

								job[s[i]].StartT[r] = Math.max(job[s[i - 1]].EndT[r - 1],	job[machines[r].getJobs().get(prevjob)].EndT[r]);
									
								// job[s[i]].StartT[r] = job[s[i]].EndT[r-1];

							} else {

								job[s[i]].StartT[r] = Math.max(job[s[i]].EndT[job[s[i]].t[prevT]],job[machines[r].getJobs().get(prevjob)].EndT[r]);
										
							}

						}

						job[s[i]].count++;
					}
					
					// ha a gép setup != job[sch[i]] setup akkor átállítás történik.
					
					muszak = machines[r].Load_operation_to_calendar( job[s[i]].StartT[r], job[s[i]].EndT[r], machines, r );
					if(muszak == 1 &&i>0 ) {
					
				    	 job[s[i]].StartT[r] =   Math.max(job[s[i-1]].EndT[r],Math.max(machines[r].getCal()[1].ST,job[s[i]].EndT[job[s[i]].t[prevT]]));
				    	
				     }else if(muszak == 0 && i==0) {
				    	
				    	 job[s[i]].StartT[r] =  Math.max(machines[r].getCal()[0].ST,job[s[i]].EndT[job[s[i]].t[prevT]]); //elsõ munka van hogy a következõ gépen kezd 
				    	 																								//valószínû ,hogy az elõzõ befejezésével kellene kezdenie.
				     }
					
					if (machines[r].getOperation() != job[s[i]].getStatus()) {
						machines[r].setOperation(job[s[i]].getStatus());
						
							//nem munkák szerint hanem rendelés tipusa szerint történik a vizsgálat.
						      switch(job[s[i]].getStatus()) {
						      	case ONE : 	job[s[i]].StartT[r] += setup[0][r];break;
						      	case TWO : 	job[s[i]].StartT[r] += setup[1][r];break;
						      	case THREE: job[s[i]].StartT[r] += setup[2][r];break;
						}
						
					}
					
					//anyagmozgatás: hasonló mint az átállás.
					switch(job[s[i]].getStatus()) {
			      		case ONE : 	job[s[i]].StartT[r] += move[0];break;
			      		case TWO : 	job[s[i]].StartT[r] += move[1];break;
			      		case THREE: job[s[i]].StartT[r] += move[2];break;
					}
					
					job[s[i]].EndT[r] = job[s[i]].StartT[r] + job[s[i]].ProcT[r];
					
					//System.out.println(machines[r].Load_operation_to_calendar( job[s[i]].StartT[r], job[s[i]].EndT[r], machines, r  )+ "job id : "+ s[i]);
					
					L = job[s[i]].EndT[r] - job[s[i]].DueDate;

					job[s[i]].setL(L);
				}

			}
			job[s[i]].count = 0;
			L = 0;
			
		}
	
		// minden szimuláció végén státusz reset.
		for (int m = 0; m < NR; m++) {
			machines[m].setOperation(Status.ONE);
		
		}
		
	}

}
