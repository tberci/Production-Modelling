package products;

public class Component {

	private int id;
	private String name;
	private int number;
	private int procT;
	private int operation_num;
	
	public Component(String name,int number, int procT, int operation_num) {
	
		this.id = (int) (Math.random()*100);
		this.name = name;
		this.number = number;
		this.procT = procT;
		this.operation_num = operation_num;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getProcT() {
		return procT;
	}

	public void setProcT(int procT) {
		this.procT = procT;
	}	
	
	public int getOperation_num() {
		return operation_num;
	}

	public void setOperation_num(int operation_num) {
		this.operation_num = operation_num;
	}

	@Override
	public String toString() {
		return "Component [ name=" + name + "]";
	}
	
	public String getComponenetName() {
		return "Component [ name=" + name + "]";
	}

}	
