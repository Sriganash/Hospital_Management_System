package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.sql.*;
public class AdminController {
	@FXML
	private Button addemp_btn;
	@FXML
	private Button rememp_btn;
	@FXML
	private Button editemp_btn;
	@FXML
	private Button logout;
	@FXML
	private AnchorPane addemp_pane;
	@FXML
	private TextField add_username;
	@FXML
	private TextField add_id;
	@FXML
	private TextField add_name;
	@FXML
	private TextField add_gender;
	@FXML
	private TextField add_age;
	@FXML
	private TextField add_contact;
	@FXML
	private ChoiceBox<String> add_type;
	@FXML
	private ChoiceBox<String> add_dept;
	@FXML
	private TextArea add_qualif;
	@FXML
	private TextField add_password;
	@FXML
	private Button add_btn;
	@FXML
	private AnchorPane remove_pane;
	@FXML
	private ChoiceBox<String> remove_type;
	@FXML
	private TextField remove_id;
	@FXML
	private Label remove_label;
	@FXML
	private Button remove_btn;
	@FXML
	private AnchorPane search_pane;
	@FXML
	private ChoiceBox<String> search_type;
	@FXML
	private TextField search_id;
	@FXML
	private Label search_label;
	@FXML
	private Button search_btn;
	@FXML
	private AnchorPane edit_pane;
	@FXML
	private TextField edit_username;
	@FXML
	private TextField edit_name;
	@FXML
	private TextField edit_gender;
	@FXML
	private TextField edit_age;
	@FXML
	private TextField edit_contact;
	@FXML
	private TextArea edit_qualif;
	@FXML
	private TextField edit_password;
	@FXML
	private Button edit_btn;
	
	int ID;
	public void getID(int id)
	{
		ID=id;
	}

	// Event Listener on Button[#addemp_btn].onAction
	Alert a=new Alert(AlertType.ERROR);
	@FXML
	public void addemp_onclick(ActionEvent event) {
		addemp_pane.setVisible(true);
		remove_pane.setVisible(false);
		search_pane.setVisible(false);
		edit_pane.setVisible(false);
		
	}
	// Event Listener on Button[#rememp_btn].onAction
	@FXML
	public void rememp_btn(ActionEvent event) {
		addemp_pane.setVisible(false);
		remove_pane.setVisible(true);
		search_pane.setVisible(false);
		edit_pane.setVisible(false);
		remove_label.setText("");
		remove_id.setText("");
	}
	// Event Listener on Button[#editemp_btn].onAction
	@FXML
	public void editemp_onclick(ActionEvent event) {
		addemp_pane.setVisible(false);
		remove_pane.setVisible(false);
		search_pane.setVisible(true);
		edit_pane.setVisible(false);
		search_label.setText("");
		search_id.setText("");
	}
	// Event Listener on Button[#logout].onAction
	@FXML
	public void l_onclick(ActionEvent event) throws IOException {
		Parent login_parent = FXMLLoader.load(getClass().getResource("Sample.fxml"));
		Scene login_scene = new Scene(login_parent);
		login_scene.getStylesheets().add(getClass().getResource("loginapplication.css").toExternalForm());
		Stage login_stage=(Stage)((Node)event.getSource()).getScene().getWindow();
		login_stage.setScene(login_scene);
		login_stage.show();
	}
	// Event Listener on Button[#add_btn].onAction
	@FXML
	public void add_onclick(ActionEvent event) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			String username=add_username.getText();
			String password=add_password.getText();
			int id=Integer.parseInt(add_id.getText());
			String name=add_name.getText();
			String gender=add_gender.getText();
			int age=Integer.parseInt(add_age.getText());
			String dept=(String)add_dept.getSelectionModel().getSelectedItem();
			String type=(String)add_type.getSelectionModel().getSelectedItem();
			String contact=(String)add_contact.getText();
			String qualification=(String)add_qualif.getText();
			
			switch(type)
			{
			case "Doctor":
				{
					ResultSet rs=stmt.executeQuery("select * from doctor where D_ID="+id);
					if(rs.next())
					{
						
						a.setContentText("This doctor id already exist");
						a.show();
					}
					else
					{
						a.setAlertType(AlertType.INFORMATION);
						stmt.executeUpdate("insert into doctor values("+id+",'"+username+"','"+password+"','"+name+"',"+age+",'"+qualification+"','"+dept+"','"+gender+"','"+contact+"');");
						a.setContentText("Successfully inserted.....");
						a.show();
					}
				}
				break;
			case "Lab Technician":
				{
					ResultSet rs=stmt.executeQuery("select * from labtech where LT_ID="+id);
					if(rs.next())
					{
						
						a.setContentText("This lab tech id already exist");
						a.show();
					}
					else
					{
						a.setAlertType(AlertType.INFORMATION);
						stmt.executeUpdate("insert into labtech values("+id+","+age+",'"+name+"','"+gender+"','"+password+"','"+username+"','"+contact+"','"+qualification+"');");
						a.setContentText("Successfully inserted.....");
						a.show();
					}
				}
				break;
			case "Pharmacist":
				{
					ResultSet rs=stmt.executeQuery("select * from pharmacist where PH_ID="+id);
					if(rs.next())
					{
						a.setContentText("This pharmacist id already exist");
						a.show();
					}
					else
					{
						a.setAlertType(AlertType.INFORMATION);
						stmt.executeUpdate("insert into pharmacist values("+id+","+age+",'"+name+"','"+gender+"','"+password+"','"+username+"','"+contact+"','"+qualification+"');");
						a.setContentText("Successfully inserted.....");
						a.show();
					}
				}
				break;
			case "Admin":
				{	
					ResultSet rs=stmt.executeQuery("select * from admin where A_ID="+id);
					if(rs.next())
					{
						a.setContentText("This admin id already exist");
						a.show();
					}
					else
					{
						a.setAlertType(AlertType.INFORMATION);
						stmt.executeUpdate("insert into admin values("+id+","+age+",'"+name+"','"+gender+"','"+password+"','"+username+"','"+contact+"','"+qualification+"');");
						a.setContentText("Successfully inserted.....");
						a.show();
					}
				}
				break;
			}
			
		
			con.close();
			
	}
		catch(Exception e){ System.out.println(e);} 
	}
	// Event Listener on Button[#remove_btn].onAction
	@FXML
	public void remove_onclick(ActionEvent event) {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			
			String type=(String) remove_type.getSelectionModel().getSelectedItem();
			int id=Integer.parseInt(remove_id.getText());
			
			if(type=="Doctor")
				{
					ResultSet rs=stmt.executeQuery("select * from doctor where D_ID="+id+";");
					if(rs.next())
					{
						stmt.executeUpdate("Delete from doctor where D_ID="+id+";");
						remove_label.setText("Doctor Record Deleted");
					}
					else
						remove_label.setText("Doctor Record Not Found");
				}
				else if(type=="Lab Technician")
				{
					ResultSet rs=stmt.executeQuery("select * from labtech where LT_ID="+id+";");
						if(rs.next())
						{
							stmt.executeUpdate("Delete from labtech where LT_ID="+id+";");
							remove_label.setText("Lab technician Record Deleted");
						}
						else
							remove_label.setText("Lab technician Record Not Found");
				}
				else if(type=="Pharmacist")
				{
					ResultSet rs=stmt.executeQuery("select * from pharmacist where PH_ID="+id+";");
					if(rs.next())
					{
						stmt.executeUpdate("Delete from pharmacist where PH_ID="+id+";");
						remove_label.setText("Pharmacist Record Deleted");
					}
					else
						remove_label.setText("Pharmacist Record Not Found");
				}
				else if(type=="Admin")
				{
					if(ID!=id)
					{
						ResultSet rs=stmt.executeQuery("select * from admin where A_ID="+id+";");
						if(rs.next())
						{
							stmt.executeUpdate("Delete from admin where A_ID="+id+";");
							remove_label.setText("Admin Record Deleted");
						}
						else
							remove_label.setText("Admin Record Not Found");
					}
					else
						remove_label.setText("You cannot delete your own record...");
				}
			
			
			con.close();  
	}
		catch(Exception e){ System.out.println(e);} 
		
	}
	// Event Listener on Button[#search_btn].onAction
	@FXML
	String type_1;
	int id_1;
	public void search_onclick(ActionEvent event) {
		try{  
				Class.forName("com.mysql.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
				Statement stmt=con.createStatement(); 
				
				String type=(String) search_type.getSelectionModel().getSelectedItem();
				type_1=type;
				int id=Integer.parseInt(search_id.getText());
				id_1=id;
				
				if(type=="Doctor")
					{
						ResultSet rs=stmt.executeQuery("select * from doctor where D_ID="+id+";");
						if(rs.next())
						{
							search_label.setText("");
							search_pane.setVisible(false);
							edit_pane.setVisible(true);
							edit_username.setText(rs.getString(2));
							edit_password.setText(rs.getString(3));
							edit_name.setText(rs.getString(4));
							edit_gender.setText(rs.getString(8));
							edit_age.setText(""+rs.getInt(5));
							edit_contact.setText(rs.getString(9));
							edit_qualif.setText(rs.getString(6));
						}
						else
							search_label.setText("Doctor Record Not Found");
					}
					else if(type=="Lab Technician")
					{
						ResultSet rs=stmt.executeQuery("select * from labtech where LT_ID="+id+";");
							if(rs.next())
							{
								search_label.setText("");
								search_pane.setVisible(false);
								edit_pane.setVisible(true);
								edit_username.setText(rs.getString(6));
								edit_password.setText(rs.getString(5));
								edit_name.setText(rs.getString(3));
								edit_gender.setText(rs.getString(4));
								edit_age.setText(""+rs.getInt(2));
								edit_contact.setText(rs.getString(7));
								edit_qualif.setText(rs.getString(8));
							}
							else
								search_label.setText("Lab technician Record Not Found");
					}
					else if(type=="Pharmacist")
					{
						ResultSet rs=stmt.executeQuery("select * from Pharmacist where PH_ID="+id+";");
						if(rs.next())
						{
							search_label.setText("");
							search_pane.setVisible(false);
							edit_pane.setVisible(true);
							edit_username.setText(rs.getString(6));
							edit_password.setText(rs.getString(5));
							edit_name.setText(rs.getString(3));
							edit_gender.setText(rs.getString(4));
							edit_age.setText(""+rs.getInt(2));
							edit_contact.setText(rs.getString(7));
							edit_qualif.setText(rs.getString(8));
						}
						else
							search_label.setText("Pharmacist Record Not Found");
					}
					else if(type=="Admin")
					{
						ResultSet rs=stmt.executeQuery("select * from Admin where A_ID="+id+";");
						if(rs.next())
						{
							search_label.setText("");
							search_pane.setVisible(false);
							edit_pane.setVisible(true);
							edit_username.setText(rs.getString(6));
							edit_password.setText(rs.getString(5));
							edit_name.setText(rs.getString(3));
							edit_gender.setText(rs.getString(4));
							edit_age.setText(""+rs.getInt(2));
							edit_contact.setText(rs.getString(7));
							edit_qualif.setText(rs.getString(8));
						}
						else
							search_label.setText("Admin Record Not Found");
					}
				
				
				con.close();  
		}
			catch(Exception e){ System.out.println(e);} 

	}
	// Event Listener on Button[#edit_btn].onAction
	@FXML
	public void edit_onclick(ActionEvent event) {
		// TODO Autogenerated
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			int id=id_1;
			String type=type_1;
			
			
			switch(type)
			{
			case "Doctor":
				{
					ResultSet rs=stmt.executeQuery("select * from doctor where D_ID="+id);
					if(rs.next())
					{
						stmt.executeUpdate("update Doctor set username='"+edit_username.getText()+"' where D_ID="+id+";");
						stmt.executeUpdate("update Doctor set password='"+edit_password.getText()+"' where D_ID="+id+";");
						stmt.executeUpdate("update Doctor set name='"+edit_name.getText()+"' where D_ID="+id+";");
						stmt.executeUpdate("update Doctor set age="+Integer.parseInt(edit_age.getText())+" where D_ID="+id+";");
						stmt.executeUpdate("update Doctor set gender='"+edit_gender.getText()+"' where D_ID="+id+";");
						stmt.executeUpdate("update Doctor set contact_no='"+edit_contact.getText()+"' where D_ID="+id+";");
						stmt.executeUpdate("update Doctor set qualification='"+edit_qualif.getText()+"' where D_ID="+id+";");
						a.setAlertType(AlertType.INFORMATION);
						a.setContentText("Successfully Updated.....");
						a.show();
					}
				}
				break;
			case "Lab Technician":
				{
					ResultSet rs=stmt.executeQuery("select * from labtech where LT_ID="+id);
					if(rs.next())
					{
						stmt.executeUpdate("update labtech set username='"+edit_username.getText()+"' where LT_ID="+id+";");
						stmt.executeUpdate("update labtech set password='"+edit_password.getText()+"' where LT_ID="+id+";");
						stmt.executeUpdate("update labtech set name='"+edit_name.getText()+"' where LT_ID="+id+";");
						stmt.executeUpdate("update labtech set age="+Integer.parseInt(edit_age.getText())+" where LT_ID="+id+";");
						stmt.executeUpdate("update labtech set gender='"+edit_gender.getText()+"' where LT_ID="+id+";");
						stmt.executeUpdate("update labtech set contact_no='"+edit_contact.getText()+"' where LT_ID="+id+";");
						stmt.executeUpdate("update labtech set qualification='"+edit_qualif.getText()+"' where LT_ID="+id+";");
						a.setAlertType(AlertType.INFORMATION);
						a.setContentText("Successfully Updated.....");
						a.show();
					}
				}
				break;
			case "Pharmacist":
				{
					ResultSet rs=stmt.executeQuery("select * from pharmacist where PH_ID="+id);
					if(rs.next())
					{
						stmt.executeUpdate("update Pharmacist set username='"+edit_username.getText()+"' where PH_ID="+id+";");
						stmt.executeUpdate("update Pharmacist set password='"+edit_password.getText()+"' where PH_ID="+id+";");
						stmt.executeUpdate("update Pharmacist set name='"+edit_name.getText()+"' where PH_ID="+id+";");
						stmt.executeUpdate("update Pharmacist set age="+Integer.parseInt(edit_age.getText())+" where PH_ID="+id+";");
						stmt.executeUpdate("update Pharmacist set gender='"+edit_gender.getText()+"' where PH_ID="+id+";");
						stmt.executeUpdate("update Pharmacist set contact_no='"+edit_contact.getText()+"' where PH_ID="+id+";");
						stmt.executeUpdate("update Pharmacist set qualification='"+edit_qualif.getText()+"' where PH_ID="+id+";");
						a.setAlertType(AlertType.INFORMATION);
						a.setContentText("Successfully Updated.....");
						a.show();
				}
				}
				break;
				case "Admin":
				{
					ResultSet rs=stmt.executeQuery("select * from admin where A_ID="+id);
					if(rs.next())
					{
						stmt.executeUpdate("update admin set username='"+edit_username.getText()+"' where A_ID="+id+";");
						stmt.executeUpdate("update admin set password='"+edit_password.getText()+"' where A_ID="+id+";");
						stmt.executeUpdate("update admin set name='"+edit_name.getText()+"' where A_ID="+id+";");
						stmt.executeUpdate("update admin set age="+Integer.parseInt(edit_age.getText())+" where A_ID="+id+";");
						stmt.executeUpdate("update admin set gender='"+edit_gender.getText()+"' where A_ID="+id+";");
						stmt.executeUpdate("update admin set contact_no='"+edit_contact.getText()+"' where A_ID="+id+";");
						stmt.executeUpdate("update admin set qualification='"+edit_qualif.getText()+"' where A_ID="+id+";");
						a.setAlertType(AlertType.INFORMATION);
						a.setContentText("Successfully Updated.....");
						a.show();
				}
				}
				break;
			
				
			
			}
			
		
			con.close();
			id_1=0;
			type_1=null;
			
	}
		catch(Exception e){ System.out.println(e);} 
	}
	
	public void initialize()
	{
		addemp_pane.setVisible(false);
		remove_pane.setVisible(false);
		search_pane.setVisible(false);
		edit_pane.setVisible(false);	
		
		search_type.getItems().addAll("Doctor","Lab Technician","Pharmacist","Admin");
		remove_type.getItems().addAll("Doctor","Lab Technician","Pharmacist","Admin");
		add_type.getItems().addAll("Doctor","Lab Technician","Pharmacist","Admin");
		add_dept.getItems().addAll("Cardiology","Radiology","Immediate medicine","Pediatric","Orthopedics","Gynacology","Therapist","Psychiartist","Nuerology");
	}
}
