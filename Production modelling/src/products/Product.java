package products;

import java.util.ArrayList;
import java.util.List;

public class Product {

	private int id;
	private String name;
	private List<Component> components;
	private int processing_time;
	private int operation_number; 
	private int material;
	
	public Product() {}

	public Product( String name, List<Component> components) {
		this.id = (int) (Math.random()*100);
		this.name = name;
		this.components = components;
		this.operation_number = 2;
		this.setMaterial(30);
		
		int sum = 0;
		for(Component component : components) {
			sum += component.getProcT();
			this.processing_time = sum;
		}
		
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

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public int getProcessing_time() {
		return processing_time;
	}

	public void setProcessing_time(int processing_time) {
		this.processing_time = processing_time;
	}

	public int getOperation_number() {
		return operation_number;
	}

	public void setOperation_number(int operation_number) {
		this.operation_number = operation_number;
	}
	
	public String getComponenetName() {
		return "Component [ name=" + name + "]";
	}
	
	public  List<Component> select_component(String product_name) {
		
		List<Component> product_component = new ArrayList<>();
		Component neon = new Component("neoncso",1,5,1);
		Component alvany1 = new Component("alvany",1,10,2);
		Component alvany2 = new Component("alvany",1,25,2);
		Component alvany3 = new Component("alvany",1,36,2);
		
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

	public int getMaterial() {
		return material;
	}

	public void setMaterial(int material) {
		this.material = material;
	}
	
}
