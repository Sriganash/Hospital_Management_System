package application;
import java.io.IOException;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

import javafx.scene.control.ChoiceBox;

public class LoginController {
	@FXML
	private Button patient_btn;
	@FXML
	private Button emp_btn;
	@FXML
	private Label login_label;
	@FXML
	private AnchorPane pLogin;
	@FXML
	private TextField pusername_field;
	@FXML
	private PasswordField ppassword_field;
	@FXML
	private Button plogin_btn;
	@FXML
	private Button signup_btn;
	@FXML
	private AnchorPane empLogin;
	@FXML
	private TextField empuser_field;
	@FXML
	private PasswordField emppassword_field;
	@FXML
	private Button emplogin_btn;
	@FXML
	private ChoiceBox<String> emptype_choice;

	String username,password,type,query;
	
	Alert a=new Alert(AlertType.ERROR);
	
	// Event Listener on Button[#patient_btn].onAction
	@FXML
	public void patient_onclick(ActionEvent event) {
		pLogin.setVisible(true);
		empLogin.setVisible(false);
		login_label.setVisible(true);
	}
	// Event Listener on Button[#emp_btn].onAction
	@FXML
	public void emp_onclick(ActionEvent event) {
		pLogin.setVisible(false);
		empLogin.setVisible(true);
		login_label.setVisible(true);
	}
	// Event Listener on Button[#plogin_btn].onAction
	@FXML
	public void plogin_onclick(ActionEvent event) {

		username=pusername_field.getText();
		password=ppassword_field.getText();
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement();
			query="Select pid,username,password from patient where username='"+username+"' and password='"+password+"'";
			ResultSet rs=stmt.executeQuery(query);
			
			if(rs.next())
			{
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("PatientSample.fxml"));
		        Parent PatientParent = loader.load();
		        
		        Scene PatientScene = new Scene(PatientParent);
		        
		        
		        //access the controller and call a method
		        PatientController controller = loader.getController();
		        controller.getPID(rs.getInt(1));
		        
		        //This line gets the Stage information
		        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		        
		        window.setScene(PatientScene);
		        window.show();
		        
			}
			
			else
			{
				a.setContentText("Username or Password Do Not Match");
				a.show();	
			}
			
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	

	}
	// Event Listener on Button[#signup_btn].onAction
	@FXML
	public void signup_onclick(ActionEvent event) throws IOException {
		
		Parent signup_parent = FXMLLoader.load(getClass().getResource("SignUpSample.fxml"));
		Scene signup_scene = new Scene(signup_parent);
		Stage signup_stage=(Stage)((Node)event.getSource()).getScene().getWindow();
		signup_stage.setScene(signup_scene);
		signup_stage.show();
		
	}
	// Event Listener on Button[#emplogin_btn].onAction
	@FXML
	public void emplogin_onclick(ActionEvent event) {
		username=empuser_field.getText();
		password=emppassword_field.getText();
		type=emptype_choice.getSelectionModel().getSelectedItem();
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(type.compareTo("Doctor")==0)
			{
				query="Select d_id,username,password from doctor where username='"+username+"' and password='"+password+"'";
				ResultSet rs=stmt.executeQuery(query);
				if(rs.next())
				{
			        FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(getClass().getResource("DrSample.fxml"));
			        Parent DoctorParent = loader.load();
			        
			        Scene DoctorScene = new Scene(DoctorParent);
			        DoctorScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			        
			        
			        //access the controller and call a method
			        DrController controller = loader.getController();
			        controller.getID(rs.getInt(1));
			        
			        //This line gets the Stage information
			        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			        
			        window.setScene(DoctorScene);
			        window.show();
					
				}
				else
				{
					a.setContentText("Username or Password Do Not Match");
					a.show();	
				}
			}
			else if(type.compareTo("Admin")==0)
			{
				query="Select a_id,username,password from admin where username='"+username+"' and password='"+password+"'";
				ResultSet rs=stmt.executeQuery(query);
				if(rs.next())
				{
			        FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(getClass().getResource("AdminSample.fxml"));
			        Parent AdminParent = loader.load();
			        
			        Scene AdminScene = new Scene(AdminParent);
			        AdminScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			        
			        
			        //access the controller and call a method
			        AdminController controller = loader.getController();
			        controller.getID(rs.getInt(1));
			        
			        //This line gets the Stage information
			        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			        
			        window.setScene(AdminScene);
			        window.show();
					
				}
				else
				{
					a.setContentText("Username or Password Do Not Match");
					a.show();	
				}
			}
			else if(type.compareTo("Lab Technician")==0)
			{
				query="Select lt_id,username,password from labtech where username='"+username+"' and password='"+password+"'";
				ResultSet rs=stmt.executeQuery(query);
				if(rs.next())
				{
			        FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(getClass().getResource("LabSample.fxml"));
			        Parent LabParent = loader.load();
			        
			        Scene LabScene = new Scene(LabParent);
			       LabScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			        
			        
			        //access the controller and call a method
			        LabController controller = loader.getController();
			        controller.getID(rs.getInt(1));
			        
			        //This line gets the Stage information
			        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			        
			        window.setScene(LabScene);
			        window.show();
					
				}
				else
				{
					a.setContentText("Username or Password Do Not Match");
					a.show();	
				}
			}
			else if(type.compareTo("Pharmacist")==0)
			{
				query="Select ph_id,username,password from pharmacist where username='"+username+"' and password='"+password+"'";
				ResultSet rs=stmt.executeQuery(query);
				if(rs.next())
				{
			        FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(getClass().getResource("PharmacySample.fxml"));
			        Parent PharmaParent = loader.load();
			        
			        Scene PharmaScene = new Scene(PharmaParent);
			        PharmaScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			        
			        
			        //access the controller and call a method
			        PharmacyController controller = loader.getController();
			        controller.getID(rs.getInt(1));
			        
			        //This line gets the Stage information
			        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			        
			        window.setScene(PharmaScene);
			        window.show();
					
				}
				else
				{
					a.setContentText("Username or Password Do Not Match");
					a.show();	
				}
			}		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}
	public void initialize()
	{
		pLogin.setVisible(false);
		empLogin.setVisible(false);
		login_label.setVisible(false);
		
		emptype_choice.getItems().addAll("Doctor","Admin","Lab Technician","Pharmacist");
		
	}
}
