import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;

public class MyWindow 
{
	private JFrame mainframe,framecustomer,frameshift,framecashier;
	public static Connection connection = null;
	public static Statement statement = null,statement2 = null, statement3 = null, statement4 = null, statement5=null, statement6 = null, statement7 = null, statementtest = null;
	public PreparedStatement prepared = null;
	public static ResultSet rs = null, rs2 = null,rs3 = null, rstest = null;
	public static String sql = null,sql2 = null, sql3 = null, sql4 = null, sql5 = null, sql6 = null, sql7 = null;
	private JTextField tcode,tgender,tbirthday,tstatus,tsum;
	public int customer_card = 0;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
		public void run()
		{
				MyWindow window = new MyWindow();
				window.mainframe.setVisible(true);
		}});
}

public MyWindow()
{
	mainframe = new JFrame();
	mainframe.setTitle("APPLICATION WINDOW");
	mainframe.setBounds(500, 100, 543, 450);
	mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mainframe.getContentPane().setLayout(null);
    connect();         // function to connect to our wampp database
    customer();        // CUSTOMER function
    shiftmanager();    // SHIFT function
    cashier();         // CASHIER function
}

public void connect()
{
    try
    {  
    			String dbname = "baseis", username = "root", password = "";
    			int port = 3306;
    			String connecting = "Attempting to connect to database " + dbname + "...\nPort Used: " + port + "\nUserName = '" + username + "'    Password = '" + password + "' \n";
        		Class.forName("com.mysql.jdbc.Driver");                                                
        		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/baseis?autoReconnect=true&useSSL=false","root","");
        		connecting += "Connection to database: SUCCESSFUL";
        		JOptionPane.showMessageDialog(mainframe, connecting);
    }
    catch (ClassNotFoundException error)
    {
        		JOptionPane.showMessageDialog(mainframe, "Error: " + error.getMessage() + "\n                                                       Program will now terminate\n", "Error", JOptionPane.ERROR_MESSAGE);
        		System.exit(0);
    }
    catch (SQLException error)
    {
    			JOptionPane.showMessageDialog(mainframe, "Error: " + error.getMessage() + "\n                                                       Program will now terminate\n", "Error", JOptionPane.ERROR_MESSAGE);
    			System.exit(0);
    }
}// end connect

public void customer()
{
		 	JButton customer = new JButton("CUSTOMER");
		 	customer.setBounds(10, 11, 162, 38);
		 	mainframe.getContentPane().add(customer);
		 	customer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
    		 	framecustomer = new JFrame();    
    		 	framecustomer.setVisible(true);
    		 	framecustomer.setTitle("CUSTOMER WINDOW");
    		 	framecustomer.setBounds(1000, 100, 543, 470);
    		 	framecustomer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		 	framecustomer.getContentPane().setLayout(null);
    		 	try
    		 	{
			     		String response = JOptionPane.showInputDialog(framecustomer, "Please enter your Client Code to view your Account Information (e.g 101)");
			     		while ( (response == null) || (response.equals("")) )
			     		{
			    	 			JOptionPane.showMessageDialog(framecustomer, "You must enter a Client Code!!!", "Error", JOptionPane.ERROR_MESSAGE);    
			    	 			response = JOptionPane.showInputDialog(framecustomer, "Please enter your Client Code to view your Account Information"); 
			     		}
			        	int customer_card = Integer.parseInt(response);
			 			JTextArea textAreacustomer = new JTextArea();
			 			textAreacustomer.setBounds(10, 11, 507, 410);
			 			framecustomer.getContentPane().add(textAreacustomer);
			 			textAreacustomer.setEditable(false);
			 			textAreacustomer.setText(""); 
			 			textAreacustomer.setVisible(false);
			 			statement = connection.createStatement(); 
			 			statement2 = connection.createStatement();
			 			statement3 = connection.createStatement();
			 			statementtest = connection.createStatement();
			 			sql = "SELECT * FROM customer WHERE code=" + customer_card;
			 			sql2 = "SELECT wmovie,rating FROM custsees WHERE watcher=" + customer_card;
			 			sql3 = "SELECT points FROM card WHERE own_code =" + customer_card + " GROUP BY (points) LIMIT 1;";
			 			rs = statement.executeQuery(sql);
			 			rs2 = statement2.executeQuery(sql2);
			 			rs3 = statement3.executeQuery(sql3);
			 			rstest = statementtest.executeQuery(sql);
			 			boolean go = true;
			 			if (!rstest.next())
			 			{ 
			 				JOptionPane.showMessageDialog(framecustomer, "Customer does not exist!!!", "Error", JOptionPane.ERROR_MESSAGE);     
			 				framecustomer.dispose(); 
			 			}
			 			while (rs.next())
			 			{
			 						textAreacustomer.setVisible(true);
			 						if (go == true) textAreacustomer.append("CARD\tGENDER\tBIRTHDAY\t     STATUS\tPOINTS\t\n\n");
			 						go = false;
			 						int code  = rs.getInt("code");
			 						String gender = rs.getString("gender");
			 						String birthday = rs.getString("birthday");
			 						String status = rs.getString("status");
			 						textAreacustomer.append(code + "\t" + gender + "\t" + birthday + "\t     " + status + "\t");
			 			}
			 			while (rs3.next())
			 			{
			 						textAreacustomer.setVisible(true);
			 						Float points  = rs3.getFloat("points");
			 						StringBuilder sb = new StringBuilder();
			 						sb.append("");
			 						sb.append(points);
			 						String strI = sb.toString();
			 						textAreacustomer.append(strI + "\n");
			 			}
			 			go = true;
			 			while (rs2.next())
			 			{
			 						textAreacustomer.setVisible(true);
			 						if (go == true)
			 						{
			 							textAreacustomer.append("____________________________________________________________________________________________________________________________________\n");
			 							textAreacustomer.append("MOVIE\t\tRATING\t\n\n");
			 						}
			 						go = false;
			 						int rating  = rs2.getInt("rating");
			 						String wmovie = rs2.getString("wmovie");  
			 						textAreacustomer.append(wmovie + "\t\t" + rating + "\t\n");
			 			}         
    		 	}
    		 	catch(SQLException error)
    		 	{
    		 		JOptionPane.showMessageDialog(framecustomer, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
		        }
	}});
} // end customer()

public void shiftmanager()
{
	    JButton shiftManager = new JButton("SHIFT MANAGER");
     	shiftManager.setBounds(182, 11, 162, 38);
  		mainframe.getContentPane().add(shiftManager);
  		shiftManager.addActionListener(new ActionListener() {
  		public void actionPerformed(ActionEvent e)
  		{
  				frameshift = new JFrame();          
  				frameshift.setVisible(true);
  				frameshift.setTitle("SHIFT MANAGER WINDOW");
  				frameshift.setBounds(1000, 100, 543, 470);
  				frameshift.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
  				frameshift.getContentPane().setLayout(null);
  				JTextArea textAreashift = new JTextArea();
  				textAreashift.setBounds(10, 104, 507, 320);
  				frameshift.getContentPane().add(textAreashift);
  				textAreashift.setEditable(false);
  				textAreashift.setText("");    
  				textAreashift.setVisible(false);
  				String option1 = "1. Ποιες ταινίες παρακολούθησαν οι άντρες πελάτες μέσα σε ένα συγκεκριμένο χρονικό διάστημα και τις βαθμολόγησαν με 4 ή 5 και ποιο ήταν το είδος των ταινιών αυτών;";
  				String option2 = "2. Ποια είναι η ταινία (ή οι ταινίες) με τα περισσότερα εισιτήρια σε συγκεκριμένο χρονικό διάστημα (ορίστε το)";
  				String option3 = "3. Ποιος πελάτης παρακολούθησε τις περισσότερες ταινίες 3D μέσα σε συγκεκριμένο χρονικό διάστημα (ορίστε το).";
  				String option4 = "4. Τι είδους ταινίες επιλέγουν να δουν οι γυναίκες πελάτες με ηλικία 20-30 ετών (σε φθίνουσα σειρά);";
  				String option5 = "5. Ποιες κάρτες πελάτη (και πόσες φορές) κέρδισαν τα δωρεάν εισιτήρια, μέσα σε συγκεκριμένο διάστημα (ορίστε το) και αν τα έχουν εξαργυρώσει.";
  				String option6 = "6. Πόσες ώρες εργάστηκε κάθε εργαζόμενος σε ένα συγκεκριμένο χρονικό διάστημα (ορίστε το) και πόσο κόστισε στην επιχείρηση;";
  				String option7 = "7. Tην τρέχουσα χρονική στιγμή ποιοι εργαζόμενοι εργάζονται και σε ποιο πόστο";
  				String option8 = "8. Θα μπορεί καθορίζοντας την ημερομηνία και τη βάρδια να βλέπει σε παρελθόν και μέλλον τους υπάλληλους ανά πόστο";
  				String[] options = new String[] {"",option1, option2, option3, option4, option5, option6, option7, option8};
  				JComboBox <String> combobox = new JComboBox<>(options);
  				combobox.setBounds(10, 11, 507, 85);
  				frameshift.getContentPane().add(combobox);
  				combobox.setEditable(false);
  				combobox.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent e) 
      			{
      				textAreashift.setVisible(true);
      				String selectedItem = combobox.getSelectedItem().toString();
      				if ( selectedItem.equals("") ) textAreashift.setText("");
      				if ( selectedItem.equals(option1) ) 
	         		{
      					textAreashift.setText(""); 
	         			try
	         			{
	         				statement = connection.createStatement();
	         				statementtest = connection.createStatement();
				 			sql = "SELECT DISTINCT(title),cat FROM movie INNER JOIN custsees ON wmovie=title INNER JOIN customer ON code=watcher INNER JOIN category ON cmovie=title WHERE rating = 4 OR rating = 5 AND customer.gender='Male' AND wdate > '2010-01-01' and wdate < '2015-01-01';";
				 			rs = statement.executeQuery(sql);
				 			rstest = statementtest.executeQuery(sql);
				 			boolean go = true;
				 			if (!rstest.next())
				 			{
				 				JOptionPane.showMessageDialog(frameshift, "No such movies!!!", "Error", JOptionPane.ERROR_MESSAGE);   
				 			}
				 			while (rs.next())
				 			{
				 				if ( go == true) textAreashift.append("TITLE\t\tCATEGORY\t\n\n");
				 				go = false;
				 				String title = rs.getString("title");
				 				String cat = rs.getString("cat");
				 				textAreashift.append(title + "\t\t" + cat + "\n");
				 			}
	         			}
	         			catch(SQLException error)
         		 	{
	         				JOptionPane.showMessageDialog(frameshift, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
         		 	}
	         			
	         		}
	         		if ( selectedItem.equals(option2) ) 
	         		{
	         			textAreashift.setText("");
	         			try
	         			{
	         				statement = connection.createStatement();
	         				statementtest = connection.createStatement();
				 			sql = "SELECT wmovie FROM custsees WHERE wdate > '1999-07-24' AND wdate < '2020-08-17' GROUP BY wmovie ORDER BY COUNT(wmovie) DESC LIMIT 1;";
				 			rs = statement.executeQuery(sql);
				 			rstest = statementtest.executeQuery(sql);
				 			boolean go = true;
				 			if (!rstest.next())
				 			{ 
				 				JOptionPane.showMessageDialog(frameshift, "No such movie!!!", "Error", JOptionPane.ERROR_MESSAGE);    
				 			}
				 			while (rs.next())
				 			{
				 				if ( go == true) textAreashift.append("MOST WATCHED MOVIES\n\n");
				 				go = false;
				 				String wmovie = rs.getString("wmovie");
				 				textAreashift.append(wmovie + "\n");
				 			}
	         			}
	         			catch(SQLException error)
	         			{
	         				JOptionPane.showMessageDialog(frameshift, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
	         			}
	         		}
	         		if ( selectedItem.equals(option3) ) 
	         		{
	         			textAreashift.setText("");
	         			try
	         			{
	         				statement = connection.createStatement();
	         				statementtest = connection.createStatement();
				 			sql = "SELECT watcher FROM custsees INNER JOIN customer ON custsees.watcher=customer.code INNER JOIN movie ON title= wmovie INNER JOIN ticket ON start_time = wdate WHERE ticket.sep = '3D' AND wdate > '1999-07-14' AND wdate < '2020-08-17' GROUP BY wmovie ORDER BY COUNT(wmovie) DESC LIMIT 1;";
				 			rs = statement.executeQuery(sql);
				 			rstest = statementtest.executeQuery(sql);
				 			boolean go = true;
				 			if (!rstest.next())
				 			{
				 				JOptionPane.showMessageDialog(frameshift, "No such client!!!", "Error", JOptionPane.ERROR_MESSAGE);    
				 			}
				 			while (rs.next())
				 			{
				 				if ( go == true) textAreashift.append("CLIENT\n\n");
				 				go = false;
				 				String watcher = rs.getString("watcher");
				 				textAreashift.append(watcher + "\n");
				 			}
	         			}
	         			catch(SQLException error)
	         			{
	         				JOptionPane.showMessageDialog(frameshift, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
	         			}
	         		}
	         		if ( selectedItem.equals(option4) ) 
	         		{
	         			textAreashift.setText("");
	         			try
	         			{
	         				statement = connection.createStatement();
	         				statementtest = connection.createStatement();
				 			sql = "SELECT DISTINCT(cat) FROM category INNER JOIN movie ON cmovie = title INNER JOIN custsees ON wmovie = title INNER JOIN customer ON code = watcher WHERE customer.gender = 'female' AND birthday > '1986-01-01' AND birthday < '1996-01-01' ORDER BY birthday ASC;";
				 			rs = statement.executeQuery(sql);
				 			rstest = statementtest.executeQuery(sql);
				 			boolean go = true;
				 			if (!rstest.next())
				 			{ 
				 				JOptionPane.showMessageDialog(frameshift, "No such category!!", "Error", JOptionPane.ERROR_MESSAGE);     
				 			}
				 			while (rs.next())
				 			{
				 				if ( go == true) textAreashift.append("CATEGORY\n\n");
				 				go = false;
				 				String cat = rs.getString("cat");
				 				textAreashift.append(cat  + "\n");
				 			}
	         			}
	         			catch(SQLException error)
	         			{
	         				JOptionPane.showMessageDialog(frameshift, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
	         			}
	         		}
	         		if ( selectedItem.equals(option5) ) 	
	         		{
	         			textAreashift.setText("");
	         			try
	         			{
	         				statement = connection.createStatement();
	         				statementtest = connection.createStatement();
				 			sql = "SELECT DISTINCT(card_id),free_tickets,took FROM card INNER JOIN customer ON card.user_code = customer.code INNER JOIN custsees ON watcher = code WHERE free_tickets > 0 AND wdate > '2016-07-19' AND wdate < '2016-07-21';";
				 			rs = statement.executeQuery(sql);
				 			rstest = statementtest.executeQuery(sql);
				 			boolean go = true;
				 			if (!rstest.next())
				 			{ 
				 				JOptionPane.showMessageDialog(frameshift, "No such client cards!!", "Error", JOptionPane.ERROR_MESSAGE);   
				 			}
				 			while (rs.next())
				 			{
				 				if ( go == true) textAreashift.append("CARD\t FREE TICKETS\t ENCASHMENT\n\n");
				 				go = false;
				 				String card_id = rs.getString("card_id");
				 				int free_tickets = rs.getInt("free_tickets");
				 				String took = rs.getString("took");
				 				textAreashift.append(card_id + "\t" + free_tickets + "\t\t "  + took + "\n");
				 			}
	         			}
	         			catch(SQLException error)
	         			{
	         				JOptionPane.showMessageDialog(frameshift, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);   
	         			}
	         		}
	         		if ( selectedItem.equals(option6) )  
	         		{
	         			textAreashift.setText("");
	         			try
	         			{
	         				statement = connection.createStatement();
	         				statementtest = connection.createStatement();
				 			sql = "SELECT DISTINCT(wid),hours,hours*10*kostos_h as cost FROM worker INNER JOIN wworks ON wguy = wid INNER JOIN shift ON shid = wshift WHERE hmnia > '2016-07-17' AND hmnia < '2017-07-26';";
				 			rs = statement.executeQuery(sql);
				 			rstest = statementtest.executeQuery(sql);
				 			boolean go = true;
				 			if (!rstest.next())
				 			{ 
				 				JOptionPane.showMessageDialog(frameshift, "No such workers!!", "Error", JOptionPane.ERROR_MESSAGE);  
				 			}
				 			while (rs.next())
				 			{
				 				if ( go == true) textAreashift.append("WORKER ID\tHOURS\tCOST\t\n\n");
				 				go = false;
				 				int hours = rs.getInt("hours");
				 				int cost = rs.getInt("cost");
				 				int wid = rs.getInt("wid");
				 				textAreashift.append(wid + "\t" + hours + "\t"  + cost + "\n");
				 			}
	         			}
	         			catch(SQLException error)
	         			{
	         				JOptionPane.showMessageDialog(frameshift, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	         			}
	         		}
	         		if ( selectedItem.equals(option7) )
	         		{
	         			textAreashift.setText("");
	         			try
	         			{
	         				String time_of_day = "afternoon";         //default value
	         				Calendar cal = Calendar.getInstance();
	         				int currentHour = cal.get(Calendar.HOUR_OF_DAY);
	         				if (currentHour >= 8 && currentHour <= 18)   time_of_day = "morning";
	         				if (currentHour > 18 && currentHour <= 4)   time_of_day = "afternoon";
	         				statement = connection.createStatement();
	         				statementtest = connection.createStatement();
	         				sql = "SELECT DISTINCT(wguy),idiotita FROM wworks INNER JOIN worker ON wguy=wid INNER JOIN shift ON shid=wshift WHERE time_of_day = '" + time_of_day + "';"; 
	         				rs = statement.executeQuery(sql);
	         				rstest = statementtest.executeQuery(sql);
	         				boolean go = true;
	         				if (!rstest.next()) 
	         				{ 
	         					JOptionPane.showMessageDialog(frameshift, "No workers!!!", "Error", JOptionPane.ERROR_MESSAGE);    
	         				}
	         				while (rs.next())
	         				{
	         					if ( go == true) textAreashift.append("WORKER\tIDIOTITA\n\n");
			 					go = false;
			 			    	int wguy = rs.getInt("wguy");
			 					String idiotita = rs.getString("idiotita");
			 					textAreashift.append(wguy + "\t" + idiotita + "\n");
	         				}
	         			}
	         			catch(SQLException error)
	         			{
	         					JOptionPane.showMessageDialog(frameshift, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
	         			}
	         		}
	         		if ( selectedItem.equals(option8) ) 
	         		{
	         			textAreashift.setText("");
	         			String date = JOptionPane.showInputDialog(frameshift, "Enter The Date (like 2016-07-27)");
			     		while ( (date == null) || (date.equals("")) )   
			     		{
			    	 			JOptionPane.showMessageDialog(frameshift, "You must enter a Date!!!", "Error", JOptionPane.ERROR_MESSAGE);     
			    	 			date = JOptionPane.showInputDialog(frameshift, "Enter The Date (like 2016-07-27)");
			     		}
			     		String shift = JOptionPane.showInputDialog(frameshift, "Enter The Shift (like morning or afternoon)");
			     		while ( (shift == null) || (shift.equals("")) )   
			     		{
			    	 			JOptionPane.showMessageDialog(frameshift, "You must enter a Shift!!!", "Error", JOptionPane.ERROR_MESSAGE);     
			    	 			shift = JOptionPane.showInputDialog(frameshift, "Enter The Date (like morning or afternoon)");
			     		}
			     		try
	         			{
	         				statement = connection.createStatement();
	         				statementtest = connection.createStatement();
				 			sql = "SELECT wguy,idiotita FROM wworks INNER JOIN worker ON wid = wguy INNER JOIN shift ON shid = wshift WHERE time_of_day = '" + shift + "' AND hmnia = '" + date + "' ;";
	         				rs = statement.executeQuery(sql);
				 			rstest = statementtest.executeQuery(sql);
				 			boolean go = true;
				 			if (!rstest.next())
				 			{ 
				 				JOptionPane.showMessageDialog(frameshift, "No such worker!!", "Error", JOptionPane.ERROR_MESSAGE);    
				 			}
				 			while (rs.next())
				 			{
				 				if ( go == true) textAreashift.append("WORKER\tIDIOTITA\n\n");
				 				go = false;
				 				String wguy = rs.getString("wguy");
				 				String idiotita = rs.getString("idiotita");
				 		        textAreashift.append(wguy + "\t" + idiotita + "\n");
				 			}
	         			}
	         			catch(SQLException error)
			     		{
	         				JOptionPane.showMessageDialog(frameshift, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
			     		}
	         		}
      			}});
  		}});
} // end shiftmanager()

public void cashier() 
{
		 	JButton cashier = new JButton("CASHIER");
		 	cashier.setBounds(354, 11, 163, 38);
		 	mainframe.getContentPane().add(cashier);
		 	cashier.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent arg0) 
 			{
 				framecashier = new JFrame();            
 				framecashier.setVisible(true);
 				framecashier.setTitle("CASHIER WINDOW");
 				framecashier.setBounds(1000, 100, 543, 470);
 				framecashier.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
 				framecashier.getContentPane().setLayout(null);
      			JTextArea textAreacashier = new JTextArea();
      			textAreacashier.setBounds(10, 104, 507, 320);
		 		framecashier.getContentPane().add(textAreacashier);
		 		textAreacashier.setEditable(false);
		 		textAreacashier.setText(""); 
		 		textAreacashier.setVisible(false);
		 		tcode = new JTextField();
		 		tcode.setColumns(10);
		 		tcode.setBounds(100, 150, 178, 30);
		 		tcode.setEditable(false);
		 		framecashier.getContentPane().add(tcode);
     		 	tgender= new JTextField();
		  		tgender.setColumns(10);
		  		tgender.setBounds(100, 200, 178, 30);
		  		framecashier.getContentPane().add(tgender);
		  		tbirthday = new JTextField();
		  		tbirthday.setColumns(10);
		  		tbirthday.setBounds(100, 250, 178, 30);
		  		framecashier.getContentPane().add(tbirthday);
		  		tstatus = new JTextField();
		  		tstatus.setColumns(10);
		  		tstatus.setBounds(100, 300, 178, 30);
		  		framecashier.getContentPane().add(tstatus);
		  		tsum = new JTextField();
		  		tsum.setBounds(100, 350, 178, 30);
		  		framecashier.getContentPane().add(tsum);
		  		tsum.setColumns(10);
				tgender.setText("");
				tbirthday.setText("");
				tstatus.setText("");
				tsum.setText("");
				tgender.setEditable(false);
				tbirthday.setEditable(false);
				tstatus.setEditable(false);
				tsum.setEditable(false);
				JLabel lcode = new JLabel("CODE");
		  		lcode.setBounds(10, 140, 58, 46);
		  		framecashier.getContentPane().add(lcode);
				JLabel lgender = new JLabel("GENDER");
		  		lgender.setBounds(10, 190, 58, 46);
		  		framecashier.getContentPane().add(lgender);
		  		JLabel lbirthday = new JLabel("BIRTHDAY");
		  		lbirthday.setBounds(10, 240, 58, 46);
		  		framecashier.getContentPane().add(lbirthday);
		  		JLabel lstatus = new JLabel("STATUS");
		  		lstatus.setBounds(10, 290, 58, 46);
		  		framecashier.getContentPane().add(lstatus);
		  		JLabel lsum = new JLabel("SUM");
		  		lsum.setBounds(10, 340, 58, 46);
		  		framecashier.getContentPane().add(lsum);
		  		JButton updategender = new JButton("UPDATE FIELD");
		  		updategender.setBounds(300, 200, 178, 30);
		  		framecashier.getContentPane().add(updategender);
		  		JButton updatebirthday = new JButton("UPDATE FIELD");
		  		updatebirthday.setBounds(300, 250, 178, 30);
		  		framecashier.getContentPane().add(updatebirthday);
		  		JButton updatestatus = new JButton("UPDATE FIELD");
		  		updatestatus.setBounds(300, 300, 178, 30);
		  		framecashier.getContentPane().add(updatestatus);
		  		JButton updatesum = new JButton("UPDATE FIELD");
		  		updatesum.setBounds(300, 350, 178, 30);
		  		framecashier.getContentPane().add(updatesum);
		  		tcode.setVisible(false);
		  		tgender.setVisible(false);
				tbirthday.setVisible(false);
				tstatus.setVisible(false);
				tsum.setVisible(false);
				lcode.setVisible(false);
		  		lgender.setVisible(false);
		  		lbirthday.setVisible(false);
		  		lstatus.setVisible(false);
		  		lsum.setVisible(false);
		  		updategender.setVisible(false);
		  		updatebirthday.setVisible(false);
		  		updatestatus.setVisible(false);
		  		updatesum.setVisible(false);
		  		String option1 = "1. Εμφάνιση προβολών ανα ημέρα και διαθέσιμων θέσεων";
		 		String option2 = "2. Επικυρωση Εισητηριου";
		 		String option3 = "3. Λειτουργια Δωρεαν Εισητηριων";
		 		String option4 = "4. Διαχειριση Προσωπικων Στοιχειων Πελατη";
		 		String option5 = "5. Συνολικο Ποσο Χρεωσης Πελατη";
		 		String[] options = new String[] {"",option1, option2, option3, option4, option5};
	         	JComboBox<String> combobox = new JComboBox<>(options);
	         	combobox.setBounds(10, 11, 507, 85);
	         	framecashier.getContentPane().add(combobox);
	         	combobox.setEditable(false);
	         	combobox.addActionListener(new ActionListener() {
         		public void actionPerformed(ActionEvent e)
         		{
         		 		String selectedItem = combobox.getSelectedItem().toString();
         		 		if ( selectedItem.equals("") ) textAreacashier.setText("");
         		 		if ( selectedItem.equals(option1) ) 
 		         		{
 	        				textAreacashier.setText("");
		         			textAreacashier.setVisible(true);
		         			tcode.setVisible(false);
 	        				tgender.setVisible(false);
 	        				tbirthday.setVisible(false);
 	        				tstatus.setVisible(false);
 	        				tsum.setVisible(false);
 	        				lcode.setVisible(false);
 	        				lgender.setVisible(false);
 	       		  			lbirthday.setVisible(false);
 	       		  			lstatus.setVisible(false);
 	       		  			lsum.setVisible(false);
 	       		  			updategender.setVisible(false);
 	       		  			updatebirthday.setVisible(false);
 	       		  			updatestatus.setVisible(false);
 	       		  			updatesum.setVisible(false);
 		         			try
 		         			{
 		         				String response = JOptionPane.showInputDialog(framecashier, "Enter DateTime for the movies (e.g 2015-08-05 15:00:00)");
 		         				while ( (response == null) || (response.equals("")) )   
 					     		{
 					    	 			JOptionPane.showMessageDialog(framecashier, "You must enter a DateTime!!!", "Error", JOptionPane.ERROR_MESSAGE);     
 					    	 			response = JOptionPane.showInputDialog(framecashier, "Enter DateTime for the movies (e.g 2015-08-05 15:00:00)");
 					     		}
 		         				statement = connection.createStatement();
 		         				statement2 = connection.createStatement();
 		         				statementtest = connection.createStatement();
 					 			sql = "SELECT DISTINCT(title),available_seats,rname FROM movie INNER JOIN room ON title=mshown INNER JOIN ticket ON foroom=rname WHERE start_time >'" + response + "' AND end_time <= NOW();" ;
 					 			sql2 = "UPDATE room SET available_seats = 0 WHERE available_seats<0;";															
 					 			statement2.executeUpdate(sql2);
 					 			rs = statement.executeQuery(sql);                                    
 					 			rstest = statementtest.executeQuery(sql);
 					 			boolean go = true;
 					 			if (!rstest.next())   JOptionPane.showMessageDialog(framecashier, "No movies are shown that day!!!", "Error", JOptionPane.ERROR_MESSAGE);  
 					 			while (rs.next())
 					 			{
 					 				if ( go == true) textAreacashier.append("MOVIES SHOWN\tMOVIE ROOM\t\tAVAILABLE SEATS\n\n");
 					 				go = false;
 					 				String title = rs.getString("title"); 
 					 				String rname = rs.getString("rname");
 					 				String available_seats = rs.getString("available_seats");
 					 				textAreacashier.append(title + "\t\t" + rname + "\t\t" + available_seats + "\n");
 					 			}
 					 			
 		         			}
 		         			catch(SQLException error)
 	            		 	{
 		         				JOptionPane.showMessageDialog(framecashier, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
 	            		 	}
 		         			
 		         		}
 	        			if ( selectedItem.equals(option2) )
 	        			{
 	        				textAreacashier.setVisible(false);
 	        				textAreacashier.setText("");
		         			textAreacashier.setVisible(true);
		         			tcode.setVisible(false);
 	        				tgender.setVisible(false);
 	        				tbirthday.setVisible(false);
 	        				tstatus.setVisible(false);
 	        				tsum.setVisible(false);
 	        				lcode.setVisible(false);
 	       		  			lgender.setVisible(false);
 	       		  			lbirthday.setVisible(false);
 	       		  			lstatus.setVisible(false);
 	       		  			lsum.setVisible(false);
 	       		  			updategender.setVisible(false);
 	       		  			updatebirthday.setVisible(false);
 	       		  			updatestatus.setVisible(false);
 	       		  			updatesum.setVisible(false);
 	       		  			String response = JOptionPane.showInputDialog(framecashier, "How many tickets do you have?");
 	        				while ( (response == null) || (response.equals("")) )   
 				     		{
 				    	 			JOptionPane.showMessageDialog(framecashier, "You must enter number of tickets!!!", "Error", JOptionPane.ERROR_MESSAGE);   
 				    	 			response = JOptionPane.showInputDialog(framecashier, "How many tickets do you have?");
 				     		}
 	        				int tickets = Integer.parseInt(response);
 	        				int counter = 0,counter2 = 0;
 	        				while (counter < tickets)
 	        				{
 	        					String card = JOptionPane.showInputDialog(framecashier, "What is the card number for the current person? (e.g 205)");
 	        					while ( (card == null) || (card.equals("")) )  
     				     		{
     				    	 			JOptionPane.showMessageDialog(framecashier, "You must enter card number!!!", "Error", JOptionPane.ERROR_MESSAGE);    
     				    	 			card = JOptionPane.showInputDialog(framecashier, "What is the card number for the current person? (e.g 205)"); 
     				     		}
 	        					int card_number = Integer.parseInt(card);
 	        					JTextField start = new JTextField();
 	        					JTextField end =   new JTextField();
 	        					JTextField seat =  new JTextField();
 	        					JTextField sep =   new JTextField();
 	        					JTextField price = new JTextField();
 	        					JTextField foroom =  new JTextField();
 	        					JTextField printer = new JTextField();
 	        					Object[] message = {"START TIME (e.g 2017-04-18 20:00:00)", start, "END TIME (e.g 2017-04-18 22:30:00)", end, "SEAT (e.g C20)", seat, "SIMPLE / 3D", sep, "TICKET PRICE (e.g 10 or 11.5)", price, "ROOM (e.g C)", foroom, "CASHIER", printer};
 	        					JOptionPane.showConfirmDialog(null, message, "TICKET DETAILS", JOptionPane.OK_CANCEL_OPTION);
 	        					try
 	        					{
 	        						String sql = "INSERT INTO ticket (start_time,end_time,seat,sep,price,foroom,printer) VALUES (?,?,?,?,?,?,?)";
 	        						prepared = connection.prepareStatement(sql);
 	        						prepared.setString(1,start.getText());
 			         				prepared.setString(2,end.getText());
 			         				prepared.setString(3,seat.getText());
 			         				prepared.setString(4,sep.getText());
 			         				prepared.setString(5,price.getText());
 			         				prepared.setString(6,foroom.getText());
 			         				prepared.setString(7,printer.getText());
 	        						prepared.executeUpdate();
 	        						statement2 = connection.createStatement();
 	        						statement3 = connection.createStatement();
 	        						statement4 = connection.createStatement();
 	        						statement5 = connection.createStatement();
 	        						statement6 = connection.createStatement();
 	        						statement7 = connection.createStatement();
 	        						sql2 = "UPDATE room SET available_seats = available_seats - 1 WHERE rname = '" + foroom.getText() + "';";
 	        						sql3 = "UPDATE card SET points = points + 10/100 * " + price.getText() + " WHERE card_id = " + card_number + ";";
 	        						sql4 = "UPDATE room SET available_seats = 0 WHERE available_seats<0;";
 	        						sql5 = "UPDATE card SET free_tickets = free_tickets+2 WHERE points>=200";
 	        						sql6 = "UPDATE card SET points = points-200 WHERE points>=200";
 	        						sql7 = "SELECT points FROM card;";
 	 					 			statement2.executeUpdate(sql2);
 	        						statement3.executeUpdate(sql3);
 	        						statement4.executeUpdate(sql4);
 	        						statement5.executeUpdate(sql5);
 	        						rs = statement7.executeQuery(sql7);
 	        						while (rs.next())
 	        						{
 	        							Float points = rs.getFloat("points");
 	        							while (counter2<1)
 	        							{
 	        								if (points >= 200) JOptionPane.showMessageDialog(framecashier, "User with card number " + card_number + " has won 2 free tickets!!!");
 	        								counter2++;
 	        							}
 	        						}
 	        						statement6.executeUpdate(sql6);
 	        						JOptionPane.showMessageDialog(framecashier, "INSERT SUCCESSFUL!!!");
 	        					}catch(SQLException error)
 	        					{
 	        						JOptionPane.showMessageDialog(framecashier, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
 	        					}
 	        					counter++;
 	        				}
 	        			}
 	        			if ( selectedItem.equals(option3) ) 
 	        			{
 	        				textAreacashier.setText("");
 	        				textAreacashier.setVisible(false);
 	        				tcode.setVisible(false);
 	        				tgender.setVisible(false);
	 	        			tbirthday.setVisible(false);
	 	        			tstatus.setVisible(false);
	 	        			tsum.setVisible(false);
	 	        			lcode.setVisible(false);
	 	       		  		lgender.setVisible(false);
	 	       		  		lbirthday.setVisible(false);
	 	       		  		lstatus.setVisible(false);
	 	       		  		lsum.setVisible(false);
	 	       		  		updategender.setVisible(false);
	 	       		  		updatebirthday.setVisible(false);
	 	       		  		updatestatus.setVisible(false);
	 	       		  		updatesum.setVisible(false);
 	        				try
 		         			{
 	        					int counter = 0,counter2=0,counter3=0,counter4=0,counter5=0;
		         				String response = JOptionPane.showInputDialog(framecashier, "Please enter your Customer Card Code to view your Account Information (e.g 201)");
					     		while ( (response == null) || (response.equals("")) )    
					     		{
					    	 			JOptionPane.showMessageDialog(framecashier, "You must enter a Customer Card Code!!!", "Error", JOptionPane.ERROR_MESSAGE);    
					    	 			response = JOptionPane.showInputDialog(framecashier, "Please enter your Customer Card Code to view your Account Information (e.g 201)"); 
					     		}
					        	int customer_card = Integer.parseInt(response);
					        	statement = connection.createStatement();
 		         				statementtest = connection.createStatement();
 					 			sql = "SELECT free_tickets FROM card WHERE card_id = " + customer_card + ";";
 					 			rs = statement.executeQuery(sql);
 					 			rstest = statementtest.executeQuery(sql);
 					 			if (!rstest.next())	   JOptionPane.showMessageDialog(framecashier, "Card id does not exist in database!!!", "Error", JOptionPane.ERROR_MESSAGE);    
 					 			while (rs.next())
 					 			{
 					 				int free_tickets = rs.getInt("free_tickets");
 					 				int validation = 1;     
 					 				if (free_tickets > 0) 
 					 				{
 					 						if (counter<1) validation = JOptionPane.showConfirmDialog(framecashier,"You have " + free_tickets + " tickets available.\nWould you like to encash these tickets?","TICKET ENCASHMENT",JOptionPane.YES_NO_OPTION);
 					 						counter++;
 					 				}
 					 				else
 					 				{
 					 						if (counter2<1) JOptionPane.showMessageDialog(framecashier, "No Free Tickets Allowed!!!", "Error", JOptionPane.ERROR_MESSAGE);    
 					 						counter2++;
 					 				}
 					 				if (validation == 0) 
 					 				{
 					 					if (free_tickets>0)
 					 					{
 					 						try
 					 						{
 					 							int movie_count = 0;
 					 							statement = connection.createStatement();
 					 							sql = "UPDATE card SET free_tickets = 0, took = 'YES' WHERE card_id = " + customer_card + ";";
 					 							statement2 = connection.createStatement();
 					 							sql2 = "SELECT mshown FROM room WHERE available_seats > 0;";
 					 							statement3 = connection.createStatement();
 					 							sql3 = "SELECT COUNT(rname) AS room_count FROM room WHERE available_seats > 0;";
 					 							statement.executeUpdate(sql);
 					 							rs2 = statement2.executeQuery(sql2);
 					 							rs3 = statement3.executeQuery(sql3);
 					 							int number_of_rooms=0;
 					 							while (rs3.next())
 					 							{
 					 								number_of_rooms = rs3.getInt("room_count");
 					 							}
 					 							String movies[] = new String[number_of_rooms];
 					 							while (rs2.next())
 					 							{
 					 								movies[movie_count] = rs2.getString("mshown");
 					 								movie_count++;
 			 					 				}
 					 							String response2 = null;
 					 							if (counter4 < 1)
 					 							{
 					 								response2 = JOptionPane.showInputDialog(framecashier,"Select A Movie: " + Arrays.toString(movies));
 					 								while ( (response2 == null) || (response2.equals("")) )    
 										     		{
 										    	 			JOptionPane.showMessageDialog(framecashier, "You must enter a Movie!!!", "Error", JOptionPane.ERROR_MESSAGE);    
 										    	 			response2 = JOptionPane.showInputDialog(framecashier,"Select A Movie: " + Arrays.toString(movies));
 										     		}
 					 								counter4++;
 					 							}
 					 							statement4 = connection.createStatement();
 					 							sql4 = "UPDATE room SET available_seats = available_seats - " + free_tickets + "  WHERE mshown = '" + response2 + "';";
 					 							statement4.executeUpdate(sql4);
 					 							statement5 = connection.createStatement();
 					 							sql5 = "UPDATE room SET available_seats = 0 WHERE available_seats<0;";														
 			 	 					 			statement5.executeUpdate(sql5);
 					 							if (free_tickets > 0)
 					 							{
 					 								if (counter3 < 1) JOptionPane.showMessageDialog(framecashier, "UPDATE SUCCESSFUL!!!");  
 		 	        								counter3++;
 		 	        								validation = 0;
 					 							}
 			 					 		}
 		 	        					catch(SQLException error)
 		 	        					{
 		 	        						JOptionPane.showMessageDialog(framecashier, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
 		 	        					}
 					 				}
 					 				}
 					 				if (validation == 1)  	
 					 				{
 					 						if (counter5 < 1) 
 					 							{
 					 								JOptionPane.showMessageDialog(framecashier, "Free Ticket Encashment ABORTED!!!\nDatabase unchanged.", "Error", JOptionPane.ERROR_MESSAGE); 
 					 								counter5++;
 					 							}
 					 				}
 					 			}
 		         			}
 		         			catch(SQLException error)
 	            		 	{
 		         				JOptionPane.showMessageDialog(framecashier, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);     
 	            		 	}
 	        				
 	        			}
 	        			if ( selectedItem.equals(option4) )  
 	        			{
 	        				textAreacashier.setVisible(false);
		         			textAreacashier.setText("");
		         			tcode.setVisible(true);
 	        				tgender.setVisible(true);
 	       		  			tbirthday.setVisible(true);
 	       		  			tstatus.setVisible(true);
 	       		  			tsum.setVisible(true);
 	       		  			lcode.setVisible(true);
 	       		  			lgender.setVisible(true);
 	       		  			lbirthday.setVisible(true);
 	       		  			lstatus.setVisible(true);
 	       		  			lsum.setVisible(true);
 	       		  			updategender.setVisible(true);
 	       		  			updatebirthday.setVisible(true);
 	       		  			updatestatus.setVisible(true);
 	       		  			updatesum.setVisible(true);
        			  		try
 		         			{
        			  			String response = JOptionPane.showInputDialog(framecashier, "Please enter your Client Code to view Account Information (e.g 101) ");
 					     		while ( (response == null) || (response.equals("")) ) 
 					     		{
 					    	 			JOptionPane.showMessageDialog(framecashier, "You must enter a Client Code!!!", "Error", JOptionPane.ERROR_MESSAGE);    
 					    	 			response = JOptionPane.showInputDialog(framecashier, "Please enter your Client Card Code to view Account Information (e.g 101)"); 
 					     		}
 					        	customer_card = Integer.parseInt(response);
 		         				statement = connection.createStatement();
 		         				statementtest = connection.createStatement();
 					 			sql = "SELECT * FROM customer WHERE code = " + customer_card + ";";
 					 			rs = statement.executeQuery(sql);
 					 			rstest = statementtest.executeQuery(sql);
 					 			if (!rstest.next())    JOptionPane.showMessageDialog(framecashier, "No such customer!!!", "Error", JOptionPane.ERROR_MESSAGE);     
 					 			while (rs.next())
 					 			{
 					 				String code = rs.getString("code");
 					 				String gender = rs.getString("gender");
 					 				String birthday = rs.getString("birthday");
 					 				String status = rs.getString("status");
 					 				float sum = rs.getFloat("sum");
 					 				tcode.setText(code);
 					 				tgender.setText(gender);
 					 				tbirthday.setText(birthday);
 					 				tstatus.setText(status);
 					 				String string_sum = Float.toString(sum);
 					 				tsum.setText(string_sum);
 					 			}
 					 			updategender.addActionListener(new ActionListener() {
 	        			  			public void actionPerformed(ActionEvent arg0) 
 	        			  			{
 	        			  				String gender = JOptionPane.showInputDialog(framecashier, "Enter new value (e.g male or female)");
 	    					     		while ( (gender == null) || (gender.equals("")) )    
 	    					     		{
 	    					    	 			JOptionPane.showMessageDialog(framecashier, "You must enter a gender for update", "Error", JOptionPane.ERROR_MESSAGE);     
 	    					    	 			gender = JOptionPane.showInputDialog(framecashier, "Enter new value (e.g male or female)"); 
 	    					     		}
 	    					     		tgender.setText(gender);
 	    					     		try
 			 	        				{
 						 						statement = connection.createStatement();
 				 					 			sql = "UPDATE customer SET gender = '" + gender +  "' WHERE code = " + customer_card + ";";
 				 					 			statement.executeUpdate(sql);
 				 					 			JOptionPane.showMessageDialog(framecashier, "Update Complete!!!");     
 				 					 	}
 			 	        				catch(SQLException error)
 			 	        				{
 			 	        						JOptionPane.showMessageDialog(framecashier, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
 			 	        				}
 	        			  		}});
 					 			updatebirthday.addActionListener(new ActionListener() {
 	        			  			public void actionPerformed(ActionEvent arg0) {
 	        			  				String birthday = JOptionPane.showInputDialog(framecashier, "Enter new value (e.g 1995-12-20)");
 	    					     		while ( (birthday == null) || (birthday.equals("")) )   
 	    					     		{
 	    					    	 			JOptionPane.showMessageDialog(framecashier, "You must enter a birthday for update", "Error", JOptionPane.ERROR_MESSAGE);    
 	    					    	 			birthday = JOptionPane.showInputDialog(framecashier, "Enter new value (e.g 1995-12-20)"); 
 	    					     		}
 	    					     		tbirthday.setText(birthday);
 	    					     		try
 			 	        				{
 						 						statement = connection.createStatement();
 				 					 			sql = "UPDATE customer SET birthday = '" + birthday +  "' WHERE code = " + customer_card + ";";
 				 					 			statement.executeUpdate(sql);
 				 					 			JOptionPane.showMessageDialog(framecashier, "Update Complete!!!");     
 				 					 	}
 			 	        				catch(SQLException error)
 			 	        				{
 			 	        						JOptionPane.showMessageDialog(framecashier, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
 			 	        				}
 	        			  			}
 	        			  		});
 	 	        				updatestatus.addActionListener(new ActionListener() {
 	        			  			public void actionPerformed(ActionEvent arg0) {
 	        			  				String status = JOptionPane.showInputDialog(framecashier, "Enter new value (e.g single or married)");
 	    					     		while ( (status == null) || (status.equals("")) )   
 	    					     		{
 	    					    	 			JOptionPane.showMessageDialog(framecashier, "You must enter a status for update", "Error", JOptionPane.ERROR_MESSAGE);   
 	    					    	 			status = JOptionPane.showInputDialog(framecashier, "Enter new value (e.g single or married)"); 
 	    					     		}
 	    					     		tstatus.setText(status);
 	    					     		try
 			 	        				{
 						 						statement = connection.createStatement();
 				 					 			sql = "UPDATE customer SET status = '" + status +  "' WHERE code = " + customer_card + ";";
 				 					 			statement.executeUpdate(sql);
 				 					 			JOptionPane.showMessageDialog(framecashier, "Update Complete!!!");     
 				 					 	}
 			 	        				catch(SQLException error)
 			 	        				{
 			 	        						JOptionPane.showMessageDialog(framecashier, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
 			 	        				}
 	        			  			}
 	        			  		});
 	 	        				updatesum.addActionListener(new ActionListener() {
 	        			  			public void actionPerformed(ActionEvent arg0) {
 	        			  				String sum = JOptionPane.showInputDialog(framecashier, "Enter new value (e.g 19.5 or 23.0)");
 	    					     		while ( (sum == null) || (sum.equals("")) )   
 	    					     		{
 	    					    	 			JOptionPane.showMessageDialog(framecashier, "You must enter a sum for update", "Error", JOptionPane.ERROR_MESSAGE);    
 	    					    	 			sum = JOptionPane.showInputDialog(framecashier, "Enter new value (e.g 19.5 or 23.0)");
 	    					     		}
 	    					     		tsum.setText(sum);
 	    					     		try
 			 	        				{
 						 						statement = connection.createStatement();
 						 						float fsum = Float.parseFloat(sum);
 				 					 			sql = "UPDATE customer SET sum = '" + fsum +  "' WHERE code = " + customer_card + ";";
 				 					 			statement.executeUpdate(sql);
 				 					 			JOptionPane.showMessageDialog(framecashier, "Update Complete!!!");     
 				 					 	}
 			 	        				catch(SQLException error)
 			 	        				{
 			 	        						JOptionPane.showMessageDialog(framecashier, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);   
 			 	        				}
 	        			  			}
 	        			  		});
 					 			
 		         			}
 		         			catch(SQLException error)
 	            		 	{
 		         				JOptionPane.showMessageDialog(framecashier, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
 	            		 	}
 	        				
 	        			}
 	        			if ( selectedItem.equals(option5) )   
 	        			{
 	        				textAreacashier.setText("");
		         			textAreacashier.setVisible(true);
 	        				tgender.setVisible(false);
 	        				tbirthday.setVisible(false);
 	        				tstatus.setVisible(false);
 	        				tsum.setVisible(false);
 	       		  			lgender.setVisible(false);
 	       		  			lbirthday.setVisible(false);
 	       		  			lstatus.setVisible(false);
 	       		  			lsum.setVisible(false);
 	       		  			updategender.setVisible(false);
 	       		  			updatebirthday.setVisible(false);
 	       		  			updatestatus.setVisible(false);
 	       		  			updatesum.setVisible(false);
 	       		  			try
 		         			{
 		         				textAreacashier.setText(""); 
 		         				String response = JOptionPane.showInputDialog(framecashier, "Please enter your Client Code (e.g 101)");
 					     		while ( (response == null) || (response.equals("")) )   
 					     		{
 					    	 			JOptionPane.showMessageDialog(framecashier, "You must enter a Client Code!!!", "Error", JOptionPane.ERROR_MESSAGE);   
 					    	 			response = JOptionPane.showInputDialog(framecashier, "Please enter your Client Code (e.g 101)"); 
 					     		}
 					        	int customer_card = Integer.parseInt(response);
 		         				statement = connection.createStatement();
 		         				statementtest = connection.createStatement();
 					 			sql = "SELECT sum FROM customer WHERE code = " + customer_card;
 					 			rs = statement.executeQuery(sql);
 					 			rstest = statementtest.executeQuery(sql);
 					 			boolean go = true;
 					 			if (!rstest.next())    JOptionPane.showMessageDialog(framecashier, "No such customer!!!", "Error", JOptionPane.ERROR_MESSAGE);   
 					 			while (rs.next())
 					 			{
 					 				if ( go == true) textAreacashier.append("CUSTOMER COST\n\n");
 					 				go = false;
 					 				float sum = rs.getFloat("sum"); 
 					 				textAreacashier.append(sum + "\n");
 					 			}
 					 			
 		         			}
 		         			catch(SQLException error)
 	            		 	{
 		         				JOptionPane.showMessageDialog(framecashier, "Error: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
 	            		 	}
 	        				
 	        			}
 	               }});
              }});
} // end cashier()
}// end class MyWindow