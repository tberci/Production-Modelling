package runnable;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import products.Component;
import products.Product;
import resources.Order;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;

public class OrderPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private String product_name;
	private JComboBox comboBox ;
	private DefaultListModel<String> model;
	private JList<String> list;
	private int index = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderPanel frame = new OrderPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OrderPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("product");
		lblNewLabel_1.setBounds(10, 42, 72, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("deadline");
		lblNewLabel_2.setBounds(10, 98, 72, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("number");
		lblNewLabel_3.setBounds(10, 150, 72, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("customer");
		lblNewLabel_4.setBounds(10, 199, 72, 14);
		contentPane.add(lblNewLabel_4);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(128, 42, -34, 20);
		contentPane.add(textPane_1);
		
		
		
		JButton btnNewButton = new JButton("Kiir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "Succesfully written to file");
				
				int id = index;
				product_name = (String) comboBox.getSelectedItem();
				int deadline = Integer.parseInt(textField_2.getText());
				int order_number = Integer.parseInt(textField_3.getText());
				String customer_name = textField_4.getText();
				index++;
				try {
					Kiir(id, product_name, deadline, order_number, customer_name);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(265, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		textField_2 = new JTextField();
		textField_2.setBounds(92, 95, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(92, 147, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(92, 196, 86, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		String[] products = {"lampa1", "lampa2","lampa3"};
		List<Product> product_components = new ArrayList<>();
		Product lampa1 = new Product("lapma1",select_component("lampa1") );
		Product lampa2 = new Product("lapma2",select_component("lampa2") );
		Product lampa3 = new Product("lapma3",select_component("lampa3") );
		
		product_components.add(lampa1);
		product_components.add(lampa2);
		product_components.add(lampa3);
		model = new DefaultListModel<>();
		
		comboBox = new JComboBox(products);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 String selectedItem = (String)comboBox.getSelectedItem();
				 List<String> components = new ArrayList<>();
				 model.clear();
				 
				 for(int i = 0; i< product_components.size(); i++) {
					 if(selectedItem.equals(products[i])) { 
						 List<Component> componentList = product_components.get(i).getComponents();
						 for(Component component : componentList) {
							 components.add(component.getName());
						 }
					 }
					
				 } for(int j = 0; j<components.size() ; j++) {
							 	model.addElement(components.get(j));
						 	}
			 
			}
		});
		comboBox.setBounds(92, 42, 86, 22);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("components");
		lblNewLabel.setBounds(188, 42, 79, 14);
		contentPane.add(lblNewLabel);
		
	    list = new JList(model);
		list.setBounds(265, 42, 159, 173);
		contentPane.add(list);
		
	}
	
	public void Kiir(int id, String product, int deadline, int order_num,String customer_name) throws IOException {
	
		String file = "src\\resources\\csvtest.csv";
		
		try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
			
			pw.write(id + ";");
			pw.write(product + ";");
			pw.write(deadline + ";");
			pw.write(order_num + ";");
			pw.write(customer_name + ";" + System.lineSeparator());
			
		}
	}
	
	public  List<Component> select_component(String product_name) {
		
		List<Component> product_component = new ArrayList<>();
		Component neon = new Component("neoncso",1,5,1);
		Component alvany1 = new Component("alvany1",1,10,2);
		Component alvany2 = new Component("alvany2",1,25,2);
		Component alvany3 = new Component("alvany3",1,36,2);
		
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
