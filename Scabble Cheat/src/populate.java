import java.sql.*;
import java.util.*;
import java.io.*;

public class populate {
	
	
	public static void main(String args[]){
		DatabaseConnection conn = new DatabaseConnection();
		 Connection c = null;
		c = conn.dbConnector();
		String path = "C:\\Users\\Owner\\Desktop\\Words.txt";
		
		//Delete
		/*try{
			PreparedStatement pst = c.prepareStatement("delete from words;");
			  pst.executeUpdate();
		}catch(Exception e){}*/
		
		try{
		Scanner x = new Scanner(new File(path));
		
		while(x.hasNext()){
		
			String a = x.next();
			String b = "";
		    char [] letters = a.toCharArray();
		    Arrays.sort(letters);
		    for(char letter: letters){
		    	b = b + "" + letter;
		    }
			int length = a.length();
			

			
		
			
		try{
			      PreparedStatement pst = c.prepareStatement("insert into words values(?,?,?);");
			      pst.setString(1,a);
			      pst.setString(2, b);
			      pst.setInt(3, length);
			      pst.executeUpdate();
		   }catch(Exception e){}  
	
		/*  try{
			      String query = "select * from words;";
			      PreparedStatement pst = c.prepareStatement(query);
			      ResultSet rs = pst.executeQuery();
			      
			      while(rs.next()){
			    	  String result = rs.getString(1);
			    	  System.out.println(result);
			      }
			      
			      }catch(Exception e){System.out.println("catch");} */
		
		} 
		}catch(Exception e){}
		
		
		System.out.println("done");
		}
	
}
