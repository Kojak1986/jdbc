/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;


/**
 *
 * @author Kojak
 */
public class JdbcTest
{
    static private String userName, password;
    public static void main (String[] args)
    {
        //compinants to display the results
        JFrame frame = new JFrame("Java Database Tables");
        frame.setLayout(new BorderLayout());
        
        JPanel tablePanel = new JPanel(), southPanel = new JPanel();
        
        //items to hold the selected data
        final JLabel lblSelectedItem = new JLabel();
        final JTextField txtSelectedItem = new JTextField();
        
        final String DB_URL = 
                "jdbc:mtsql://sql.computerstudi.es:3306/gc200327966";
        
        final String QRY = "SELECT * FROM Employees";
        
        //DB connection objects
        Statement stat = null;
        ResultSet rs = null;
        Connection conn = null;
        
        //Login window ************************************************************
        //components
        
        JPanel loginPanel = new JPanel();
        JLabel lblUserName = new JLabel("User Name:"),
                lblPassword = new JLabel("Password");
        JTextField txtUserName = new JTextField(10);
        JPasswordField txtPassword = new JPasswordField(10);
        
               
        
        //add components to the panel
        loginPanel.add(lblUserName);
        loginPanel.add(txtUserName);
        loginPanel.add(lblPassword);
        loginPanel.add(txtPassword);
        
        //boolean variable for loop control
        boolean dbCredCheck = false;
        int dbLoopCounter = 0;
        do
        {
            //create login window
            String[] buttonOptions = new String[] {"OK","Cancel"};
            
            JOptionPane.showOptionDialog(null, loginPanel, "Enter Password",
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, buttonOptions, buttonOptions[0]);
            
            //Try catch to try and connect
            try
            {
                conn = DriverManager.getConnection(DB_URL, userName = txtUserName.getText(), password = new String(txtPassword.getPassword()));
                
                dbCredCheck = true;
            }
            catch(SQLException error)
            {
                JOptionPane.showMessageDialog(null, "Your database credentials are invalid, please try again");
                dbCredCheck = false;
                dbLoopCounter++;
                //if 3 invalid attempts, exit system
                if(dbLoopCounter < 2)
                {
                    JOptionPane.showMessageDialog(null, "Failure to authenticate to database. System will exit");
                    System.exit(0);
                }
            }
            catch (Exception error)
            {
                
            }
            
        }
        while(!dbCredCheck && dbLoopCounter <= 3);
        
        try
        {
        if (conn!=null)
        {
            //Create the statement object that will manipulate the database
            stat = conn.createStatement();
            rs = stat.executeQuery(QRY);
            
            //extract the meta data for later use
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            DefaultTableModel tblModel = buildTBModel(rs);
            //display the data into JTable
            final JTable table = new JTable(tblModel);
        }
        }
        catch(SQLException error)
                {
                    
                }
        
        
        
    }


    public static DefaultTableModel buildTBModel(ResultSet rs) throws SQLException
    {
        ResultSetMetaData metaData = rs.getMetaData();
        
        //get the column names in a vector
        Vector<String> columnNames = new Vector<String>();
        //column count
        int columnCount = metaData.getColumnCount();
        
        //loop to build the column names
        for (int i=1; i<=columnCount; i++)
        {
            columnNames.add(metaData.getColumnName(i));
        }
        
        //create the vector to hold the data(vector of vectors)
        //this stores all rows
        Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
        
        //go through the result set
        while (rs.next())
        {
            //this will store all data from each row
            Vector<Object> rowVector = new Vector<Object>();
            //loop through the result and get each object
            for(int colIndex = 1; colIndex <= columnCount; colIndex++)
            {
                rowVector.add(rs.getObject(colIndex));
            }
            tableData.add(rowVector);
        }
        //return
        return new DefaultTableModel(tableData, columnNames);
    }
    
    public static void insert()
    {
        Statement stat = null;
        Connection conn = null;
        
        final String DB_URL = 
                "jdbc:mtsql://sql.computerstudi.es:3306/gc200327966";
        
        try 
        {
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            
            String sql = "INSERT INTO '200327966'. ('firstName', 'lastName', 'age')"
                    + "VALUES ('Karl', 'Kovacs', '30'";
            
            //execute the query to the database
            stat.executeUpdate(sql);
        }
        catch(SQLException error)
        {
             error.printStackTrace();
        }
    }
}
