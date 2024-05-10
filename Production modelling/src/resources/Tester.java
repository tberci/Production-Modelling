package resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import products.Product;

public class Tester {

	public static void main(String[] args) throws IOException {
		
		List<Order> orders = new ArrayList<>();
		Product product = new Product();
		String file = "src\\resources\\csvtest.csv";
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.readLine();
		while((line = br.readLine()) != null) {
			String[] values = line.split(";");
			
			int id = Integer.parseInt(values[0]);
			product.setName(values[1]);
			int deadline = Integer.parseInt(values[2]);
			int order_number = Integer.parseInt(values[3]);
			String customer_name = values[4];
			
			orders.add(new Order(id,product,deadline,customer_name,order_number));
		}
		
		for(Order order : orders) {
			System.out.println("id :" + order.getId() + ", product :"+ order.getProduct() + ", deadline : " + order.getDeadline() + ", order number :" + order.getNumber() + ", customer : "+ order.getCustomer_name());
		}
		
		br.close();
		

	}

}
