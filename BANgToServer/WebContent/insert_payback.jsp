<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert PayBack</title>
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
			request.setCharacterEncoding("euc-kr");
			String id = request.getParameter("id");
			String groupName = request.getParameter("groupName");
			String from = request.getParameter("from");
			String to = request.getParameter("to");
			String howMuch = request.getParameter("howMuch");
			
			stmt = conn.createStatement();
			String command = String.format(
					"INSERT INTO  `PayBack` (  `groupName` ,  `from` ,  `to` ,  `howMuch` ) VALUES ('"+groupName+"','"+from+"','"+to+"',"+howMuch+"); ");
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