	import java.sql.*;
	import java.util.*;

	//to use org.sqlite.JDBC you must add jar files
	//jar files are located in MyJavaFiles -> SQLiteFiles
	//Right click JRE System Library in current package and add build path and add jar files

	public class DatabaseConnection{
		
		ArrayList<String> words = new ArrayList<String>();
		int blank = 0;

	    public static Connection dbConnector(){//creates connection to database method
	    	
	    	String path = System.getProperty("user.dir") + "\\src\\words.sqlite";

			      try{
			          Class.forName("org.sqlite.JDBC");
			          Connection conn=DriverManager.getConnection("jdbc:sqlite:" + path);

			          return conn;
			      }catch(Exception e){System.out.println("no connection");return null;}
			   }
	       


		public ArrayList<String> LookUpWords(String word, boolean first, boolean size){
			Connection c = dbConnector();
			System.out.println(size);
///////////////////////////////////////////////////////////////////////////////Sort word in alphabetical order and add to ArrayList			
			char [] letters = word.toCharArray();
			Arrays.sort(letters);
			ArrayList<Character> sorted = new ArrayList<Character>();	

			for(char x : letters){
				if(x=='*'){blank++;}
				sorted.add(x);
			}

///////////////////////////////////////////////////////////////////////////////// Execute Query with database			
			 try{
				 
				 String query = "select * from words;";
				 
				 if(size){
			      query = "select * from words where length =" + word.length() + ";";
				 }
				 
				 
				 
			      PreparedStatement pst = c.prepareStatement(query);
			      ResultSet rs = pst.executeQuery();
			      
			      
			      
///////////////////////////////////////////////////////////////////////////////// ArrayList holds sorted word from database
			      
			      ArrayList<Character> sort = new ArrayList<Character>();
			      
			      while(rs.next()){
////////////////////////////////////////////////////////////////////////////////gets query results			    	  
			    	  String wordDB = rs.getString(1);
			    	  
			    	  if(first){
			    	  	   if(wordDB.charAt(0)!=word.charAt(0)){continue;}///for first letter checkbox
			    	  }
			    	  
			    	  String sortedDB = rs.getString(2);
			    	  for(char l : sortedDB.toCharArray()){
			    		  sort.add(l);
			    	  }
			    	  
/////////////////////////////////////////////////////////////////////////////////subtracts original word from database word			    	  
			    	  
			    	  for(Character l : sorted){
			    		  sort.remove(l);
			    	  }
///////////////////////////////////////////////////////////////////////////////////////////////////////////	
			    	  
			    	  if(sort.size()==(0+blank)){words.add(wordDB);}//blank give extra size for *
			    	  
			    	  
			    	  sort.clear();
			      }
			      
			      
			      }catch(Exception e){System.out.println("no go");}			 
			 
			 return words;
		}
		
	}

