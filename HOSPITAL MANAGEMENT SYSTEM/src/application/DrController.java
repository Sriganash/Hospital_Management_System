package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.TextArea;



public class DrController {
	@FXML
	private Button updatepRecord;
	@FXML
	private Button lab_records;
	@FXML
	private Button presription;
	@FXML
	private Button appointments;
	@FXML
	private Button logout;
	@FXML
	private Button profile;
	@FXML
	private AnchorPane patient_rno_pane;
	@FXML
	private TextField recno_textf;
	@FXML
	private Button recordprescription_select;
	@FXML
	private AnchorPane update_patient_pane;
	@FXML
	private Label prno_medhis_label;
	@FXML
	private Label name_medhis_label;
	@FXML
	private Label age_medhis_label;
	@FXML
	private Label prevhis_medhis_label;
	@FXML
	private TextArea add_history;
	@FXML
	private Button submit_medhis_btn;
	@FXML
	private AnchorPane add_test_pane;
	@FXML
	private ChoiceBox<String> test_choicebox;
	@FXML
	private Button addtest_btn;
	@FXML
	private Label test_label;
	@FXML
	private Button submit_test_btn;
	@FXML
	private AnchorPane testpatient_rno_pane;
	@FXML
	private TextField testrno_textf;
	@FXML
	private Button checktest_btn;
	@FXML
	private Button add_testbtn;
	@FXML
	private AnchorPane prescription_pane;
	@FXML
	private ChoiceBox<String> med_choiceb;
	@FXML
	private ChoiceBox<String> med_day_choice;
	@FXML
	private ChoiceBox<String> med_times_choice;
	@FXML
	private Button add_to_presc_btn;
	@FXML
	private Label presc_label;
	@FXML
	private Button submit_presc_btn;
	@FXML
	private AnchorPane appointment_pane;
	@FXML
	private Label date_label;
	@FXML
	private Label time_label;
	@FXML
	private Label pname_label;
	@FXML
	private Label pid_label;
	@FXML
	private Label profile_label;
	@FXML
	private Label test_report_label;
	@FXML
	private Label name;
	
	ObservableList<String> items = FXCollections.observableArrayList();
	int D_ID;
	public void getID(int id)
	{
		D_ID=id;
		
	}

	int recordPresc=-1;
	StringBuilder testmsg=new StringBuilder(); 	
	StringBuilder prescmsg=new StringBuilder();
	StringBuilder profilemsg=new StringBuilder();
	String appointdate;
	String appointtime;
	String appointpid;
	String appointname;
	Alert a=new Alert(AlertType.ERROR);
	
	// Event Listener on Button[#updatepRecord].onAction
	@FXML


	public void update_onclick(ActionEvent event) {
		patient_rno_pane.setVisible(true);
		update_patient_pane.setVisible(false);
		add_test_pane.setVisible(false);
		testpatient_rno_pane.setVisible(false);
		prescription_pane.setVisible(false);
		appointment_pane.setVisible(false);
		profile_label.setVisible(false);
		test_report_label.setVisible(false);
		recordPresc=0;
		recno_textf.clear();
	}
	
	// Event Listener on Button[#lab_records].onAction
	@FXML
	public void lab_onclick(ActionEvent event) {
		patient_rno_pane.setVisible(false);
		update_patient_pane.setVisible(false);
		add_test_pane.setVisible(false);
		testpatient_rno_pane.setVisible(true);
		prescription_pane.setVisible(false);
		appointment_pane.setVisible(false);
		profile_label.setVisible(false);
		testrno_textf.clear();
		test_report_label.setVisible(false);
	}
	
	// Event Listener on Button[#presription].onAction
	@FXML
	public void presc_onclick(ActionEvent event) {
		patient_rno_pane.setVisible(true);
		update_patient_pane.setVisible(false);
		add_test_pane.setVisible(false);
		testpatient_rno_pane.setVisible(false);
		prescription_pane.setVisible(false);
		appointment_pane.setVisible(false);
		profile_label.setVisible(false);
		test_report_label.setVisible(false);
		recordPresc=1;
		recno_textf.clear();
		
		
	}
	// Event Listener on Button[#appointments].onAction
	@FXML
	public void viewappoint_onclick(ActionEvent event) {
		patient_rno_pane.setVisible(false);
		update_patient_pane.setVisible(false);
		add_test_pane.setVisible(false);
		testpatient_rno_pane.setVisible(false);
		prescription_pane.setVisible(false);
		appointment_pane.setVisible(true);
		profile_label.setVisible(false);
		test_report_label.setVisible(false);
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			
			date_label.setText("");
			time_label.setText("");
			pname_label.setText("");
			pid_label.setText("");
			ResultSet rs=stmt.executeQuery("select a.date,a.time,a.pid,p.name from appointment a,patient p where a.id="+D_ID+" and p.pid=a.pid");
			while(rs.next())
			{
				String appointdate=date_label.getText();
				String appointtime=time_label.getText();
				String appointpid=pid_label.getText();
				String appointname=pname_label.getText();
				date_label.setText(appointdate+"\n"+rs.getDate(1));
				time_label.setText(appointtime+"\n"+rs.getInt(2));
				pname_label.setText(appointname+"\n"+rs.getString(4));
				pid_label.setText(appointpid+"\n"+rs.getString(3));
			}
			
			con.close();  
	}
		catch(Exception e){ System.out.println(e);} 
		
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
	// Event Listener on Button[#profile].onAction
	@FXML
	public void viewprofile_onclick(ActionEvent event) {
		patient_rno_pane.setVisible(false);
		update_patient_pane.setVisible(false);
		add_test_pane.setVisible(false);
		testpatient_rno_pane.setVisible(false);
		prescription_pane.setVisible(false);
		appointment_pane.setVisible(false);
		profile_label.setVisible(true);
		test_report_label.setVisible(false);
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from doctor where D_ID="+D_ID+";");
			String h="PROFILE\n\n";
			while(rs.next())
			{
				h=h+("\tDoctor ID         :\t"+rs.getInt(1)+"\n");
				h=h+("\tusername          :\t"+rs.getString(2)+"\n");
				h=h+("\tName              :\t"+rs.getString(4)+"\n");
				h=h+("\tAge               :\t"+rs.getInt(5)+"\n");
				h=h+("\tQualification     :\t"+rs.getString(6)+"\n");
				h=h+("\tDepartment        :\t"+rs.getString(7)+"\n");
				h=h+("\tGender            :\t"+rs.getString(8)+"\n");
				h=h+("\tContact Number    :\t"+rs.getString(9)+"\n");
			}
			System.out.println(h);
			profile_label.setText(h);
			con.close();  
	}
		catch(Exception e){ System.out.println(e);} 
		
	}
	// Event Listener on Button[#recordprescription_select].onAction
	@FXML
	int pid;
	public void choose_recpresc_onclick(ActionEvent event) {
		if(recordPresc==0)
		{
			try{  
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
					Statement stmt=con.createStatement(); 
					pid=Integer.parseInt(recno_textf.getText());
					ResultSet rs=stmt.executeQuery("select * from patient where PID="+pid);
					if(rs.next())
					{
						patient_rno_pane.setVisible(false);
						update_patient_pane.setVisible(true);
						prno_medhis_label.setText(rs.getString(1));
						name_medhis_label.setText(rs.getString(4));
						age_medhis_label.setText(rs.getString(5));
						prevhis_medhis_label.setText(rs.getString(7));
						recordPresc=-1;
						add_history.setText(rs.getString(7));
						
					}
					else
					{
						a.setContentText("Enter the correct Patient Record No");
						a.show();
						recordPresc=0;
					}
					con.close();  
			}catch(Exception e){ System.out.println(e);}  

			
		}
		else if(recordPresc==1)
		{
			try{  
				Class.forName("com.mysql.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
				Statement stmt=con.createStatement(); 
				pid=Integer.parseInt(recno_textf.getText());
				ResultSet rs=stmt.executeQuery("select PID from patient where PID="+pid);
				if(rs.next())
				{
					testmsg.delete(0,testmsg.length());
					test_label.setText("");
					patient_rno_pane.setVisible(false);
					prescription_pane.setVisible(true);
					recordPresc=-1;
					ResultSet rs1=stmt.executeQuery("select * from medicine;");
					while(rs1.next())
					{
						items.add(rs1.getString(2));
						med_choiceb.setItems(items);
					}
				
				}
				else
				{
					a.setContentText("Enter the correct Patient Record No");
					a.show();
					recordPresc=0;
				}
				con.close();  
		}
			catch(Exception e){ System.out.println(e);}  
				
		}
	}
	
	
	// Event Listener on Button[#submit_medhis_btn].onAction
	@FXML
	public void submit_medhis_onclick(ActionEvent event) {
		// TODO Autogenerated
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			
			
			
			String his=add_history.getText();
			stmt.executeUpdate("update patient set history='"+his+"' where PID="+pid);
			add_history.setText("Successfully inserted....");
			
			con.close();  
	}
		catch(Exception e){ System.out.println(e);}  
	}
	
	// Event Listener on Button[#addtest_btn].onAction
	@FXML
	public void addtolabel_onclick(ActionEvent event) {

		test_label.setText((String) test_choicebox.getSelectionModel().getSelectedItem());
		 
	}
	
	// Event Listener on Button[#submit_test_btn].onAction
	@FXML
	public void submit_test_onclick(ActionEvent event) {
		// TODO Autogenerated
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			
			stmt.executeUpdate("Insert into lab (test_name,d_id,pid,status) values('"+test_label.getText()+"',"+D_ID+","+pid+",'Processing');");
			test_label.setText("Successfully inserted...");
			con.close();
			
	}
		catch(Exception e){ System.out.println(e);} 
	}
	
	// Event Listener on Button[#checktest_btn].onAction
	@FXML
	public void checktest_onclick(ActionEvent event) {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			pid=Integer.parseInt(testrno_textf.getText());
			ResultSet rs1=stmt.executeQuery("select * from patient where pid="+pid+";");
			if(rs1.next())
			{
				ResultSet rs=stmt.executeQuery("select test_name,test_report,status from lab where D_ID="+D_ID+" and PID="+pid+";");
				test_report_label.setText("TEST REPORT\n\n");
				if(rs.next())
				{
					rs.previous();
					patient_rno_pane.setVisible(false);
					update_patient_pane.setVisible(false);
					add_test_pane.setVisible(false);
					testpatient_rno_pane.setVisible(false);
					prescription_pane.setVisible(false);
					appointment_pane.setVisible(false);
					profile_label.setVisible(false);
					test_report_label.setVisible(true);
					while(rs.next())
					{
						String h=test_report_label.getText();
						if(rs.getString(3).compareTo("complete")==0)
							test_report_label.setText(h+"\n"+rs.getString(1)+" : "+rs.getString(2));
						else
							test_report_label.setText(h+"\n"+rs.getString(1)+" : **Still processing");
						
							
					}
				}
				else
				{
					String h=test_report_label.getText();
					test_report_label.setText(h+"\nNo tests yet........");
				}
			}
			else
			{
				a.setAlertType(AlertType.ERROR);
				a.setContentText("Enter the correct patient id...");
				a.show();
			}
			
			con.close();  
	}
		catch(Exception e){ System.out.println(e);} 
		
		
	}
	
	// Event Listener on Button[#add_testbtn].onAction
	@FXML
	public void test_onclick(ActionEvent event) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			pid=Integer.parseInt(testrno_textf.getText());
			ResultSet rs=stmt.executeQuery("select * from patient where PID="+pid);
			if(rs.next())
			{
				testpatient_rno_pane.setVisible(false);
				add_test_pane.setVisible(true);
			}
			else
			{
				a.setContentText("Enter the correct Patient Record No");
				a.show();
				recordPresc=0;
			}
			con.close();  
	}catch(Exception e){ System.out.println(e);}  

	}
	
	// Event Listener on Button[#add_to_presc_btn].onAction
	@FXML
	String medi_name,day_choice,time_choice;
	public void add_to_presc_onclick(ActionEvent event) {
		
		medi_name=(String)med_choiceb.getSelectionModel().getSelectedItem();
		day_choice=(String)med_times_choice.getSelectionModel().getSelectedItem();
		time_choice=(String)med_day_choice.getSelectionModel().getSelectedItem();
		
		presc_label.setText(medi_name+" "+day_choice+"For "+time_choice);

	}
	
	// Event Listener on Button[#submit_presc_btn].onAction
	@FXML
	public void submit_presc_onlick(ActionEvent event) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			
			ResultSet rs=stmt.executeQuery("select M_ID from medicine where name='"+medi_name+"';");
			int med_id;
			if(rs.next())
			{
				med_id=rs.getInt(1);
				stmt.executeUpdate("insert into buymed values("+pid+","+med_id+",'"+presc_label.getText()+"');");
				presc_label.setText("Successfully inserted...");
			}
			else
				
				presc_label.setText("Medicine is not available.......");
			
			con.close();
			
	}
		catch(Exception e){ System.out.println(e);} 
	}
	public void initialize()
	{
		patient_rno_pane.setVisible(false);
		update_patient_pane.setVisible(false);
		add_test_pane.setVisible(false);
		testpatient_rno_pane.setVisible(false);
		prescription_pane.setVisible(false);
		appointment_pane.setVisible(false);
		profile_label.setVisible(false);
		
		//Add test names into check box
		for(int i=1; i<=10; i++)
		{
			test_choicebox.getItems().add("Test_No_"+i);
		}

		//Add medicine names
	
		
		med_day_choice.getItems().addAll("1 Day","2 Days","3 Days","4 Days","5 Days","6 Days","7 Days","10 Days","1 Month");  
		med_times_choice.getItems().addAll("Once a Day","Twice a day", "Thrice a Day", "Night only");
	}
}
