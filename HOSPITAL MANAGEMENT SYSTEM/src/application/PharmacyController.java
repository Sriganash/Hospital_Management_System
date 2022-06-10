package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PharmacyController {
    @FXML
    private Button addmed_btn;
    @FXML
    private Button remmed_btn;
    @FXML
    private Button status_btn;
    @FXML
    private Button logout;
    @FXML
    private AnchorPane add_pane;
    @FXML
    private TextField add_name;
    @FXML
    private TextField add_id;
    @FXML
    private TextField add_price;
    @FXML
    private TextField add_stock;
    @FXML
    private ChoiceBox<String> add_type;
    @FXML
    private Button add_btn;
    @FXML
    private AnchorPane remove_pane;
    @FXML
    private TextField remove_id;
    @FXML
    private Label remove_label;
    @FXML
    private Button remove_btn;
    @FXML
    private TextField remove_name;
    @FXML
    private AnchorPane status_pane;
    @FXML
    private ChoiceBox<String> medname_avail_choice;
    @FXML
    private Label med_stock_label;
    @FXML
    private Button chkstock_btn;

	int ID;
	public void getID(int id)
	{
		ID=id;
	}
    // Event Listener on Button[#addmed_btn].onAction
    @FXML
    public void addmed_onclick(ActionEvent event) {
        add_pane.setVisible(true);
        remove_pane.setVisible(false);
        status_pane.setVisible(false);
    }

    // Event Listener on Button[#remmed_btn].onAction
    @FXML
    public void remmed_onclick(ActionEvent event) {
        add_pane.setVisible(false);
        remove_pane.setVisible(true);
        status_pane.setVisible(false);
        remove_label.setText("");
        remove_id.setText("");
        remove_name.setText("");
    }

    // Event Listener on Button[#status_btn].onAction
    @FXML
    public void status_onclick(ActionEvent event) {
        add_pane.setVisible(false);
        remove_pane.setVisible(false);
        status_pane.setVisible(true);
        try{ 
    		
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			
			ResultSet rs1=stmt.executeQuery("select * from medicine;");
			while(rs1.next())
			{
				items.add(rs1.getString(2));
				medname_avail_choice.setItems(items);
				
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

    // Event Listener on Button[#add_btn].onAction
    @FXML
    public void add_onclick(ActionEvent event) throws SQLException, ClassNotFoundException {
        String name = add_name.getText();
        int id = Integer.parseInt(add_id.getText());
        Integer price = Integer.parseInt(add_price.getText());
        int stock = Integer.parseInt(add_stock.getText());
        JdbcController jdbcController = new JdbcController();
        if (jdbcController.insertIntoTable(id, name, "BATCH", price, stock) != 0) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Successful");
            a.show();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Already exist  !!");
            a.show();
        }

    }

    // Event Listener on Button[#remove_btn].onAction
    @FXML
    public void remove_onclick(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (remove_id.getText().compareTo("000") == 0) {
            remove_label.setText("Medicine Record Not Found");

        } else {
            int medId =Integer.parseInt(remove_id.getText());
            JdbcController jdbcController = new JdbcController();
            if(jdbcController.deleteRecord(medId)==-1){
                remove_label.setText("CANT DELETE");
            }
            else{
                remove_label.setText("Deleted");
            }
        }


    }
    ObservableList<String> items = FXCollections.observableArrayList();
    // Event Listener on Button[#chkstock_btn].onAction
    @FXML
    public void chkstock_onclick(ActionEvent event) {
    	try{ 
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Sriganash@27");
			Statement stmt=con.createStatement(); 
			
			String med_name=(String) medname_avail_choice.getSelectionModel().getSelectedItem();
			
			ResultSet rs=stmt.executeQuery("select stock,m_id from medicine where name='"+med_name+"';");
			if(rs.next())
			{
				med_stock_label.setText(""+rs.getInt(1)+"   [ medicine id = "+rs.getInt(2)+" ]");
			}
			else
				med_stock_label.setText("No medicine available");
			
			con.close();
			
	}
		catch(Exception e){ System.out.println(e);} 
    }

    public void initialize() {
        add_pane.setVisible(false);
        remove_pane.setVisible(false);
        status_pane.setVisible(false);
        add_type.getItems().add("antidote");
        add_type.getItems().add("antitherapeutic");
        add_type.getItems().add("antiacid");
      }
}
