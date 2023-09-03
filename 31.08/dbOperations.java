import java.sql.*;
import java.util.Scanner;

class dbOperations{
    public static void main(String args[]){
        System.out.println("Hello, World!");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/cse2102040024JDBC";
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
            Statement stmt2 = con.createStatement();

            // ----------------- RUN1
            // ResultSet rs = stmt.executeQuery("SELECT * FROM student");
            /*while(rs.next()){
                System.out.println("ROLLNO:\t" + rs.getString(1) + "\n\tNAME:\t\t" + rs.getString(2) + "\n\tDEGREE:\t\t" + rs.getString(3) + "\n\tADMISSION YEAR:\t" + rs.getString(4) + "\n\tGENDER:\t\t" + rs.getString(5) + "\n\tDEPARTMENT NO.:\t" + rs.getString(6) + "\n\tMENTOR:\t\t" + rs.getString(7) + "\n");
            }*/

            // ----------------- RUN2
            // int n = stmt.executeUpdate("CREATE TABLE computer(c_id VARCHAR(15), memory VARCHAR(10), storage VARCHAR(10), os VARCHAR(15), PRIMARY KEY (c_id))");
            // System.out.println(n + " row(s) affected");

            // ----------------- RUN3
            // int n = stmt.executeUpdate("INSERT INTO computer VALUES (\"CS105/05\", \"4GB\", \"256GB\", \"Windows 10 Pro\"), (\"CS105/04\", \"8GB\", \"512GB\", \"Windows 11 Pro\")");
            // System.out.println(n + " row(s) affected");

            String tableName, temp, str;
            String[] Attr = new String[20];
            String[] headers = new String[10];
            int flag = 1, Oprn, n = 0, nAttr;
            int updOprn, insFlag = 1;
            boolean cacheFlag = false;
            String cache;

            Scanner input = new Scanner(System.in);

            while(flag == 1){
                try{
                    System.out.println("Choose an operation:\n\t1. Create a table\n\t2. Update existing table\n\t3. Show tables\n\t4. Delete a table\n\t5. Exit");
                    Oprn = input.nextInt();
                    switch(Oprn){
                        case 1:
                            // createTable(stmt);
                            System.out.print("Enter Table Name: ");
                            tableName = input.nextLine();
                            tableName = input.nextLine();
                            System.out.print("Enter number of attributes: ");
                            nAttr = input.nextInt();
                            System.out.println("Enter attribute name and type");
                            temp = input.nextLine();
                            for(int i = 0; i < nAttr; i++){
                                temp = input.nextLine();
                                Attr[n++] = temp.split(" ")[0];
                                Attr[n++] = temp.split(" ")[1];
                            }
                            temp = "";
                            for(int i = 0; i < n; i++)
                                temp += Attr[i++] + " " + Attr[i] + ", ";
                            temp += "PRIMARY KEY (" + Attr[0] + ")";
                            n = stmt.executeUpdate("CREATE TABLE " + tableName + "(" + temp + ")");
                            System.out.println(n + " row(s) affected\n");
                            break;
                        case 2:
                            // updateTable(stmt);
                            System.out.println("Choose an operation:\n\t1. INSERT\n\t2. SELECT *\n\t3. CLEAR DATA");
                            updOprn = input.nextInt();
                            System.out.print("Enter Table Name: ");
                            tableName = input.nextLine();
                            tableName = input.nextLine();
                            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
                            ResultSetMetaData rsMetaData = rs.getMetaData();
                            nAttr = rsMetaData.getColumnCount();
                            System.out.println("Column Count: " + nAttr);
                            for(int i = 0; i < nAttr; i++)
                                headers[i] = rsMetaData.getColumnName(i + 1);
                            switch(updOprn){
                                case 1:
                                    n = 0;
                                    while(insFlag == 1){
                                        if(cacheFlag)
                                            cache = input.nextLine();
                                        if(!cacheFlag)
                                            cacheFlag = true;
                                        for(int i = 0; i < nAttr; i++){
                                            str = headers[i];
                                            System.out.print("Enter " + str + ": ");
                                            Attr[i] = input.nextLine();
                                        }
                                        temp = "";
                                        for(int i = 0; i < nAttr - 1; i++)
                                            temp += "\"" + Attr[i] + "\",";
                                        temp += "\"" + Attr[nAttr - 1] + "\"";
                                        try{
                                            n += stmt2.executeUpdate("INSERT INTO " + tableName + " VALUES(" + temp + ")");
                                        }catch(Exception e){
                                            e.printStackTrace();
                                        }                      
                                        System.out.println("Enter 1 to continue insertion, 0 to finish insertion");
                                        insFlag = input.nextInt();
                                    }
                                    cacheFlag = false;
                                    if(n > 0)
                                        System.out.println(n + " row(s) Inserted");
                                    break;
                                case 2:
                                    ResultSet rs2 = stmt2.executeQuery("SELECT * FROM " + tableName);
                                    for(int i = 0; i < nAttr; i++){
                                        str = headers[i];
                                        System.out.print("\t" + str + "\t\t");
                                    }
                                    System.out.println();
                                    while(rs2.next()){
                                        // System.out.println(rs2);
                                        for(int i = 0; i < nAttr; i++)
                                            System.out.print("\t" + rs2.getString(headers[i]));
                                        System.out.println();
                                    }
                                    break;
                                case 3:
                                    n = stmt.executeUpdate("TRUNCATE TABLE " + tableName);
                                    System.out.println(n + " row(s) affected");
                                    break;
                                default:
                                    // do nothing
                            }
                            break;
                        case 3:
                            // showTables(stmt);
                            ResultSet rs3 = stmt.executeQuery("SHOW TABLES");
                            System.out.println("List of Tables:");
                            while(rs3.next())
                                System.out.println("\t" + rs3.getString(1));
                            break;
                        case 4:
                            // deleteTable(stmt);
                            System.out.print("Enter Existing Table Name to Delete: ");
                            tableName = input.nextLine();
                            tableName = input.nextLine();
                            n = stmt.executeUpdate("DROP TABLE " + tableName);
                            System.out.println(n + " row(s) affected\n");
                            break;
                        default:
                            flag = 0;
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

/* TERMINAL
PS D:\Junior\Study\DBMS LAB\31.08> javac dbOperations.java
PS D:\Junior\Study\DBMS LAB\31.08> java -cp ".;C:\Users\Jibesh\Downloads\mysql-connector-j-8.1.0\mysql-connector-j-8.1.0.jar" dbOperations
Hello, World!
Connection is made..
Choose an operation:
        1. Create a table
        2. Update existing table
        3. Show tables
        4. Delete a table
        5. Exit
1
Enter Table Name: Student
Enter number of attributes: 4
Enter attribute name and type
ROLLNO CHAR(10)
NAME VARCHAR(50)
BRANCH VARCHAR(50)
SECTION VARCHAR(5)
0 row(s) affected

Choose an operation:
        1. Create a table
        2. Update existing table
        3. Show tables
        4. Delete a table
        5. Exit
3
List of Tables:
        student
Choose an operation:
        1. Create a table
        2. Update existing table
        3. Show tables
        4. Delete a table
        5. Exit
2
Choose an operation:
        1. INSERT
        2. SELECT *
        3. CLEAR DATA
1
Enter Table Name: Student
Column Count: 4
Enter ROLLNO: 2102040001
Enter NAME: Prerit Agrawal
Enter BRANCH: Computer Science and Engineering
Enter SECTION: D1
Enter 1 to continue insertion, 0 to finish insertion
1
Enter ROLLNO: 2102041032
Enter NAME: Sourav Panda
Enter BRANCH: Computer Science and Engineering
Enter SECTION: D2
Enter 1 to continue insertion, 0 to finish insertion
0
2 row(s) Inserted
Choose an operation:
        1. Create a table
        2. Update existing table
        3. Show tables
        4. Delete a table
        5. Exit
2
Choose an operation:
        1. INSERT
        2. SELECT *
        3. CLEAR DATA
2
Enter Table Name: Student
Column Count: 4
        ROLLNO                  NAME                    BRANCH                  SECTION
        2102040001      Prerit Agrawal  Computer Science and Engineering        D1
        2102041032      Sourav Panda    Computer Science and Engineering        D2
Choose an operation:
        1. Create a table
        2. Update existing table
        3. Show tables
        4. Delete a table
        5. Exit
4
Enter Existing Table Name to Delete: Student
0 row(s) affected

Choose an operation:
        1. Create a table
        2. Update existing table
        3. Show tables
        4. Delete a table
        5. Exit
3
List of Tables:
Choose an operation:
        1. Create a table
        2. Update existing table
        3. Show tables
        4. Delete a table
        5. Exit
5
 */