Steps to Develop a JSP-Based Student Portal with Attendance Submission
  
1. Set Up the Database (MySQL)
Create a database named StudentDB.
Create a table attendance with columns: id, student_name, date, status.
Insert sample records for testing.

2. Configure Your Java Project
Add MySQL JDBC Driver (via Maven or manually).
Set up Apache Tomcat in your IDE (Eclipse/VScode).

3. Create a Database Connection Class (DBConnection.java)
This utility class connects to MySQL.

4. Create the JSP Form (attendance.jsp)
A form where users enter student name, date, and attendance status (Present/Absent).

5. Develop the Servlet (AttendanceServlet.java)
Handles form submission and saves attendance to the database.
Uses JDBC to insert data into MySQL.

6. Configure web.xml (If Needed)
Map AttendanceServlet to handle form submissions.

7. Deploy & Test
Run on Tomcat.
Access via http://localhost:8080/YourAppName/attendance.jsp.
Submit attendance, check database for saved records.

Enhancements (Optional)
- Display submitted attendance records in attendance.jsp.
- Add CSS/Bootstrap for better UI.
- Implement session handling for authentication.
code 

CREATE DATABASE StudentDB;
USE StudentDB;
CREATE TABLE attendance (
id INT AUTO_INCREMENT PRIMARY KEY,
student_name VARCHAR(100),
subject VARCHAR(100),
date DATE
);
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Student Attendance</title>
</head>
<body>
<form action="SaveAttendanceServlet" method="post">
Name: <input type="text" name="student_name" required><br>
Subject: <input type="text" name="subject" required><br>
Date: <input type="date" name="date" required><br>
<input type="submit" value="Submit">
</form>
</body>
</html> import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/SaveAttendanceServlet")
public class SaveAttendanceServlet extends HttpServlet {
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
response.setContentType("text/html");
PrintWriter out = response.getWriter();
String name = request.getParameter("student_name");
String subject = request.getParameter("subject");
String date = request.getParameter("date");
try {
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentDB", "root", "password");
String query = "INSERT INTO attendance (student_name, subject, date) VALUES (?, ?, ?)";
PreparedStatement ps = con.prepareStatement(query);
ps.setString(1, name);
ps.setString(2, subject);
ps.setString(3, date);
int result = ps.executeUpdate();
if (result > 0) {
out.println("<h3>Attendance recorded successfully.</h3>");
} else {
out.println("<h3>Failed to record attendance.</h3>");
}
con.close();
} catch (Exception e) {
out.println("<h3>Error: " + e.getMessage() + "</h3>");
}
out.close();
}
}
