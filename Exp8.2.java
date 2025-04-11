Steps to Create a JDBC-Integrated Servlet for Employee Management
1. Set Up Database
Create a MySQL database (EmployeeDB).
Create an employees table with columns (id, name, department, salary).
Insert sample employee data.

2. Set Up Your Java Project
Add MySQL JDBC Driver (via Maven or manually).
Configure Apache Tomcat in your IDE.

3. Create Database Connection Class
Write a utility class (DBConnection.java) to establish a connection with the MySQL database.

4. Develop the Servlet (EmployeeServlet.java)
- Handle GET requests to fetch all employees or search by Employee ID.
- Use JDBC to query data and display it in HTML format.
- Implement a search form for filtering employees by ID.

5. Deploy and Run
Deploy the servlet on Tomcat.
Access it via http://localhost:8080/YourAppName/EmployeeServlet.
(The page displays employee records and allows searching by ID.)

Note : Enhancements (Optional)
Improve UI with CSS & Bootstrap.

code 
Database Schema CREATE DATABASE EmployeeDB;
USE EmployeeDB;
CREATE TABLE employees (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100),
department VARCHAR(100)
);
INSERT INTO employees (name, department) VALUES
('John Doe', 'IT'),
('Jane Smith', 'HR');
ii) Employee Servlet: import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
response.setContentType("text/html");
PrintWriter out = response.getWriter();
try {
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EmployeeDB", "root", "password");
String query = "SELECT * FROM employees";
PreparedStatement ps = con.prepareStatement(query);
ResultSet rs = ps.executeQuery();
out.println("<h2>Employee List</h2><ul>");
while (rs.next()) {
out.println("<li>" + rs.getInt("id") + " - " + rs.getString("name") + " (" + rs.getString("department") + ")</li>");
}
out.println("</ul>");
con.close();
} catch (Exception e) {
out.println("<h3>Error: " + e.getMessage() + "</h3>");
}
out.close();
}
}
Web Form: <!DOCTYPE html>
<html>
<head>
<title>Search Employee</title>
</head>
<body>
<form action="EmployeeServlet" method="get">
Employee ID: <input type="text" name="id" required>
<input type="submit" value="Search">
</form>
</body>
</html>
