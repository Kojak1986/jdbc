/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;
import java.io.*;
import java.util.*;
/**
 *
 * @author Kojak
 */
public class JavaIO 
{
    private Scanner read = new Scanner(System.in);
    BufferedWriter bufferWriter = null;
    
    //Method to check for the used directory
    public void checkDir()
    {
        try
        {
            // define directory name in String
            String dirName = "C:/JavaIO/Error/";
            File directory = new File("C:/JavaIO/Error/");
            
            
            //if the directory doesnt exist, create it
            if(!directory.exists())
            {
                System.out.print("Creating Directory: " + dirName);
                boolean exists = false;
                
                try
                {
                    directory.mkdir();
                    exists = true;
                    System.out.println("Directory Created");
                }
                catch(SecurityException error)
                {
                    
                }
                       
            }
            else
            {
                System.out.print("Exists!");
            }
        }
        catch(SecurityException error)
        {
            
        }
        catch(Exception error)
        {
            
        }
    }
   
    
    
    
    //method to append data to a file
    public void appendToFile()
    {
        //check directory
        checkDir();
        
        try
        {
            File file = new File ("C:/JavaIO/Error/error.txt");
            String message;
            
            //if file doesnt exists create it
            if(!file.exists())
            {
                System.out.println("No such file");
                file.createNewFile();
            }
            //ask for additional input
            System.out.print("Enter new text to append: ");
            message = read.nextLine();
            
            FileWriter fileWriter = new FileWriter("C:/JavaIO/Error/error.txt", true);
            
            bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.newLine();
            bufferWriter.write(message);
            
            
            //close connection to the file
            bufferWriter.close();
        
        }
        catch(IOException error)
        {
            //handle exception
        }
        catch(SecurityException error)
        {
            //handle exception
        }
    }
    
    //main method
    public static void main(String[] args)
    {
        JavaIO io = new JavaIO();
        //call method to create the directory
        //io.checkDir();
        io.appendToFile();
    }
    
}
