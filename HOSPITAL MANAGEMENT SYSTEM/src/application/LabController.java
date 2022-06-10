package application;

import java.io.IOException;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LabController {
	@FXML
	private Button genrep_btn;
	@FXML
	private Button status_btn;
	@FXML
	private Button logout;
	@FXML
	private AnchorPane search_pane;
	@FXML
	private TextField search_id;
	@FXML
	private Button enter_btn;
	@FXML
	private AnchorPane status_pane;
	@FXML
	private AnchorPane test_pane;
	@FXML
	private TextArea test_report_area;
	@FXML
	private Label remaining_test_label;
	@FXML
	private Label test_label;
	@FXML
	private Label patient_id_label;
	@FXML
	private Button submittest_btn;
	@FXML
	private ChoiceBox<String> patientid_choice;
	@FXML
	private Label status_label;
	@FXML
	private Button chkstatus_btn;
	
	
	Alert a=new Alert(AlertType.ERROR);
	String[] str= new String[10];
	StringBuilder msg=new StringBuilder();
	int c,count;
	int pid;
	
	int ID;
	public void getID(int id)
	{
		ID=id;
	}
	
	public Connection db_connect() throws ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	

	// Event Listener on Button[#genrep_btn].onAction
	@FXML
	public void genrep_onclick(ActionEvent event) {
		search_id.setText("");
		search_pane.setVisible(true);
		test_pane.setVisible(false);
		status_pane.setVisible(false);
	}
	// Event Listener on Button[#status_btn].onAction
	@FXML
	public void status_onclick(ActionEvent event) {
		search_pane.setVisible(false);
		test_pane.setVisible(false);
		status_pane.setVisible(true);
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
	// Event Listener on Button[#enter_btn].onAction
	@FXML
	public void enter_onclick(ActionEvent event) {
		
		try
		{
			Connection con=db_connect();
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE); 
			pid=Integer.parseInt(search_id.getText());
			ResultSet rs=stmt.executeQuery("select * from lab where PID="+pid);
			if(rs.next())
			{
				ResultSet rs1=stmt.executeQuery("select * from lab where PID="+pid+" and status='Processing'");
				if(rs1.next())
				{
					search_pane.setVisible(false);
					test_pane.setVisible(true);	
					patient_id_label.setText(search_id.getText());
					rs1.previous();
					int i=0;
					while(rs1.next())
					{
						str[i]=rs1.getString(1);
						i++;
					}
					c=1;
					count=i+1;
					System.out.println(i);
					test_label.setText(str[0]);
					get_test();
					
				}
				else
				{
					a.setAlertType(AlertType.INFORMATION);
					a.setContentText("All Test Reports Generated");
					a.show();
				}
			}
			
			else
			{
				a.setAlertType(AlertType.ERROR);
				a.setContentText("Enter The Correct Patient ID");
				a.show();
			}
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	
	// Event Listener on Button[#submittest_btn].onAction
	@FXML
	public void submit_test_onclick(ActionEvent event) throws ClassNotFoundException, SQLException {

		if(c<count)
		{
			Connection con=db_connect();
			String query="update lab set test_report=?,status='complete' where pid=? and test_name=?";
			PreparedStatement stmt=con.prepareStatement(query); 
			stmt.setInt(2,pid);
			stmt.setString(1, test_report_area.getText());
			stmt.setString(3, str[c-1]);
			try
			{
				stmt.execute();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			test_report_area.setText("");
			test_label.setText(str[c]);
			c++;
			get_test();
			con.close();

		}
		

		else
		{
			a.setAlertType(AlertType.INFORMATION);
			a.setContentText("All Test Reports Generated");
			a.show();
		}

	}
	
	// Event Listener on Button[#chkstatus_btn].onAction
	@FXML
	public void chkstatus_onclick(ActionEvent event) {
		try
		{
			Connection con=db_connect();
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE); 
			int status_pid=Integer.parseInt((String)patientid_choice.getSelectionModel().getSelectedItem());
			ResultSet rs=stmt.executeQuery("select * from lab where pid="+status_pid+" and status='Processing';");
			if(rs.next())
			{
				status_label.setText("Reports Are To Be Generated");

			}
			else
			{
				status_label.setText("All Reports Are Generated");
			}
			con.close();
				
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		

	}
	
	public void get_test()
	{
		msg.delete(0, msg.length());
		for(int i=c;i<count-1;i++)
		{
			msg.append(str[i]+"\n");	
		}
		remaining_test_label.setText(msg.toString());
	
	}
	
	public void initialize()
	{
		search_pane.setVisible(false);
		test_pane.setVisible(false);
		status_pane.setVisible(false);
		status_label.setText("");
		patientid_choice.getItems().clear();
		try
		{
			Connection con=db_connect();
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE); 
			ResultSet rs=stmt.executeQuery("select distinct pid from lab");
			while(rs.next())
			{
				patientid_choice.getItems().add(rs.getString(1));

			}
			con.close();
				
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
}
