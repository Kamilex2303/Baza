import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;
import static java.util.Date.*;

public class Main {

    public static void main(String[] argv) throws SQLException, IOException {

        Connection c = null;
        try {
            int ip;
            String database_name;
            String login;
            int option;
            Scanner read = new Scanner(System.in);
            System.out.print("IP: ");
            ip = read.nextInt();
            System.out.print("Database name: ");
            database_name = read.next();
            System.out.print("Login: ");
            login = read.next();
            char[] password = System.console().readPassword("Password: ");
            String passwd = new String(password);
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:"+ip+"/"+database_name,login,passwd);
            c.setAutoCommit(false);
            System.out.println("Connected");
            boolean check=true;
            while (check){
                System.out.println("----------MENU----------");
                System.out.println("1.Add user");
                System.out.println("2.Moodify user");
                System.out.println("3.Create HTML file");
                System.out.println("4.Print all users");
                System.out.println("5.Exit");
                System.out.print("Which options? :");
                option = read.nextInt();
                switch (option){
                    case 1:
                        PreparedStatement st = c.prepareStatement("INSERT INTO person (name, surname, birthday_date, salary , job_position ) VALUES (?,?, ?, ?, ?)");
                        System.out.println("Please add all informations");
                        Person p = new Person();
                        System.out.print("Name: ");
                        p.setName(read.next());
                        System.out.print("Surname: ");
                        p.setSurname(read.next());
                        System.out.println("Birthday format : (year-month-day): ");
                        p.setBirthday_date(read.next());
                        System.out.println("Salary: ");
                        p.setSalary(read.nextFloat());
                        System.out.println("Job position: ");
                        p.setJob_position(read.next());

                        st.setString(1,p.getName());
                        st.setString(2, p.getSurname());
                        st.setObject(3, java.sql.Date.valueOf(p.getBirthday_date()));
                        st.setFloat(4, p.getSalary());
                        st.setString(5, p.getJob_position());
                        st.executeUpdate();
                        System.out.println("Success");
                        st.close();
                        c.commit();
                        break;
                    case 2:
                        System.out.println("Modyfi");
                        Statement stmt5;
                        stmt5 = c.createStatement();
                        ResultSet rs5 = stmt5.executeQuery("SELECT * FROM person ORDER BY id_person;");
                        while (rs5.next()) {
                            int id = rs5.getInt(1);
                            String name = rs5.getString("name");
                            String surname = rs5.getString("surname");
                            java.sql.Date birthday = rs5.getDate("birthday_date");
                            float salary = rs5.getFloat("salary");
                            String job = rs5.getString("job_position");
                            System.out.println(id+","+name +","+surname+","+birthday+","+salary+","+job);
                        }
                        System.out.println("Which user you want modify ? :");
                        System.out.println("Id: ");
                        int id=read.nextInt();
                        Statement stmt;
                        stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM person WHERE id_person = "+id+";");
                        while (rs.next()) {
                            String name2 = rs.getString("name");
                            String surname2 = rs.getString("surname");
                            java.sql.Date birthday2 = rs.getDate("birthday_date");
                            float salary2 = rs.getFloat("salary");
                            String job2 = rs.getString("job_position");
                            System.out.println(name2 + "," + surname2 + "," + birthday2 + "," + salary2 + "," + job2);
                        }
                        boolean check2 = true;
                        while (check2) {
                            System.out.print("Which date you want to change ? (name , surname , birthday , salary , job) or exit : ");
                            String checked = read.next();
                            switch (checked) {
                                case "name":
                                    System.out.print("New name: ");
                                    String newName = read.next();
                                    PreparedStatement s = c.prepareStatement("UPDATE person SET name='"+newName+"' WHERE id_person="+id+";");
                                    s.executeUpdate();
                                    s.close();
                                    c.commit();
                                    break;
                                case "surname":
                                    System.out.print("New surname: ");
                                    String newSurname = read.next();
                                    s = c.prepareStatement("UPDATE person SET surname='"+newSurname+"' WHERE id_person="+id+";");
                                    s.executeUpdate();
                                    s.close();
                                    c.commit();
                                    break;
                                case "birthday":
                                    System.out.print("New birthday day: ");
                                    String newBirthday = read.next();
                                    s = c.prepareStatement("UPDATE person SET birthday_date='"+newBirthday+"' WHERE id_person="+id+";");
                                    s.executeUpdate();
                                    s.close();
                                    c.commit();
                                    break;
                                case "salary":
                                    System.out.print("New salary: ");
                                    float newSalary = read.nextFloat();
                                    s = c.prepareStatement("UPDATE person SET salary='"+newSalary+"' WHERE id_person="+id+";");
                                    s.executeUpdate();
                                    c.commit();
                                    break;
                                case "job":
                                    System.out.print("New job position: ");
                                    String newJob = read.next();
                                    s = c.prepareStatement("UPDATE person SET job_position='"+newJob+"' WHERE id_person="+id+";");
                                    s.executeUpdate();
                                    s.close();
                                    c.commit();
                                    break;
                                case "exit":
                                    check2 = false;
                                    break;

                                    default:
                                        System.out.println("Wrong option. Try again");
                                        break;
                            }
                        }
                    case 3:
                        System.out.println("HTML");
                        FileWriter fWriter = null;
                        BufferedWriter bWriter = null;
                        fWriter = new FileWriter("users2.html");
                        bWriter = new BufferedWriter(fWriter);
                        Statement stmt3;
                        stmt3 = c.createStatement();
                        ResultSet rs3 = stmt3.executeQuery("SELECT * FROM person;");
                        String backgroundColor = "<body style='background-color:#9999ff;'>";
                        bWriter.write("" +"<!DOCTYPE html> \n"+
                                "<html><head><meta charset=\"utf-8\">\n"+
                                "<title>Database</title>"+
                                "<style>" +
                                "table {" +
                                "    width: 100%;" +
                                "    border: solid black 3px;"+
                                "    border-radius:10px;"+
                                "}" +
                                "" +
                                "th{" +
                                "    text-align: center;" +
                                "    "+
                                "}" +
                                ""
                                + "h1 {" +
                                "    font-weight: 900;"+
                                "}" +
                                "td {"+
                                "   background-color:white;"+
                                "   border-radius:10px;"+
                                "   transition-duration:4s;"+
                                "   text-align:center;"+
                                "}"+
                                "td:hover {"+
                                "   background-color:#ccccff;"+
                                "   cursor: pointer;"+
                                "}"+

                                "</style>\n" +
                                "</head>" + backgroundColor + "<h1>Users</h1><br><br>");
                        bWriter.write("<table><tr><th>ID</th>" + "<th>Name</th> " + "<th>Surname</th> "
                                + "<th>Birthday</th> " + "<th>Salary</th> " + "<th>Job position</th> "+ "</tr>");
                        while (rs3.next()) {
                            int id2 = rs3.getInt(1);
                            String name2 = rs3.getString("name");
                            String surname2 = rs3.getString("surname");
                            java.sql.Date birthday2 = rs3.getDate("birthday_date");
                            float salary2 = rs3.getFloat("salary");
                            String job2 = rs3.getString("job_position");
                            bWriter.write("<tr><td>"+id2+"</td><td>"+name2+"</td><td>"+surname2+"</td><td>"+birthday2+"</td><td>"+salary2+" PLN</td><td>"+job2+"</td></tr>");
                            bWriter.newLine();
                        }
                        bWriter.write("</table></body></html>");
                        bWriter.close();
                        fWriter.close();
                        break;
                    case 4:
                        System.out.println("All users : ");
                        Statement stmt2;
                        stmt2 = c.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("SELECT * FROM person ORDER BY id_person;");
                        while (rs2.next()) {
                            String name = rs2.getString("name");
                            String surname = rs2.getString("surname");
                            java.sql.Date birthday = rs2.getDate("birthday_date");
                            float salary = rs2.getFloat("salary");
                            String job = rs2.getString("job_position");
                            System.out.println(name +","+surname+","+birthday+","+salary+","+job);
                        }
                        break;
                    case 5:
                        c.close();
                        check = false;
                        break;
                        default:
                            System.out.println("Wrong option");


                }
            }



        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

}