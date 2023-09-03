import java.sql.*;
import java.util.Scanner;

class makeConnection{
    public static void main(String args[]){
        System.out.println("Hello, World!");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/cse2102040024";
            String username = "root";
            String password = "Jaggu@2004";
            Connection con = DriverManager.getConnection(url, username, password);
            if(con.isClosed()){
                System.out.println("Connection is closed!");
            }
            else{
                System.out.println("Connection is made..");
            }
            Statement stmt = con.createStatement();

            // ----------------- RUN1
            // ResultSet rs = stmt.executeQuery("SELECT * FROM student");
            /*while(rs.next()){
                System.out.println("ROLLNO:\t" + rs.getString(1) + "\n\tNAME:\t\t" + rs.getString(2) + "\n\tDEGREE:\t\t" + rs.getString(3) + "\n\tADMISSION YEAR:\t" + rs.getString(4) + "\n\tGENDER:\t\t" + rs.getString(5) + "\n\tDEPARTMENT NO.:\t" + rs.getString(6) + "\n\tMENTOR:\t\t" + rs.getString(7) + "\n");
            }*/

            // ----------------- RUN2
            // int n = stmt.executeUpdate("CREATE TABLE computer(c_id VARCHAR(15), memory VARCHAR(10), storage VARCHAR(10), os VARCHAR(15), PRIMARY KEY (c_id))");
            // System.out.println(n + " rows affected");

            // ----------------- RUN3
            // int n = stmt.executeUpdate("INSERT INTO computer VALUES (\"CS105/05\", \"4GB\", \"256GB\", \"Windows 10 Pro\"), (\"CS105/04\", \"8GB\", \"512GB\", \"Windows 11 Pro\")");
            // System.out.println(n + " rows affected");
            System.out.println("CREATING TABLE STUDENTS...");
	        int n = stmt.executeUpdate("CREATE TABLE students(regd CHAR(10), name VARCHAR(30), branch VARCHAR(40), section VARCHAR(5), PRIMARY KEY(regd))");
            System.out.println(n + " rows affected");
	    

            String regd, name, branch, section;
            int flag = 1;
            n = 0;

            Scanner input = new Scanner(System.in);

            while(flag == 1){
                try{
                    System.out.print("Enter Roll Number: ");
                    if(n > 0)
                        regd = input.nextLine();
                    regd = input.nextLine();
                    System.out.print("Enter Name: ");
                    name = input.nextLine();
                    System.out.print("Enter Branch: ");
                    branch = input.nextLine();
                    System.out.print("Enter Section: ");
                    section = input.nextLine();
                    n += stmt.executeUpdate("INSERT INTO students VALUES (\"" + regd  + "\", \"" + name + "\", \"" + branch + "\", \"" + section + "\")");
                    System.out.println("Press \t1 to insert another row\n\t0 if insertion is completed");
                    flag = input.nextInt();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            if (n > 0){
                System.out.println(n + " rows inserted");
            }

            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while(rs.next()){
                System.out.println("ROLLNO:\t" + rs.getString(1) + "\n\tNAME:\t\t" + rs.getString(2) + "\n\tBRANCH:\t\t" + rs.getString(3) + "\n\tSECTION:\t" + rs.getString(4));
            }

            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}