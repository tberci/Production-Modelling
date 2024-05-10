package resources;

import products.Product;

public class Order {

	private int id;
	private Product product;
	private int number;
	private String customer_name; 
	private int deadline;
	private int product_processing_time;
	private int operation_number;
	private int materials;
	
	public Order(){}
	
	public Order(int id, Product product,int deadline,  String customer_name, int number) {
		this.id = id;
		this.product = product;
		this.customer_name = customer_name;
		this.deadline = deadline;
		this.number = number;
		this.product_processing_time = product.getProcessing_time();
		this.operation_number = product.getOperation_number();
		this.materials = this.number * 30;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProduct_processing_time() {
		return product_processing_time;
	}

	public void setProduct_processing_time(int product_processing_time) {
		this.product_processing_time = product_processing_time;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	public int getOperation_number() {
		return operation_number;
	}

	public void setOperation_number(int operation_number) {
		this.operation_number = operation_number;
	}

	public int getMaterials() {
		return materials ;
	}

	public void setMaterials(int materials) {
		this.materials = materials;
	}

}


