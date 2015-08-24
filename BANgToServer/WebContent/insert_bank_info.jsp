<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert Bank_Info</title>
</head>
<body>
<%
		Connection conn = null;
		Statement stmt = null;
		try {
			/*
			 * 연결
			 */
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/BANgTO", "root", "bangto");


			if (conn == null)
				throw new Exception("데이터베이스에 연결할 수 없습니다.<BR> ");

			/*
			 * 입력
			 */
			request.setCharacterEncoding("UTF-8");
			String email = request.getParameter("email");
			String bank = request.getParameter("bank");
			String account = request.getParameter("account");
			
			stmt = conn.createStatement();
			String command = String.format(
					"insert into Bank_Info (email,bank,account) values ('"+email+"','"+bank+"','"+account+"'); ");
			int rowNum = stmt.executeUpdate(command);
			if (rowNum < 1)
				throw new Exception("데이터를 DB에 입력할 수 없습니다. ");

		} finally {
			try {
				stmt.close();
			} catch (Exception ignored) {
			}

			try {
				conn.close();
			} catch (Exception ignored) {
			}
		}
	%>
</body>
</html>