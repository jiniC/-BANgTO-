<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert Memo</title>
</head>
<body>
<%
		Connection conn = null;
		Statement stmt = null;
		try {
			/*
			 * ����
			 */
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/BANgTO", "root", "bangto");


			if (conn == null)
				throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.<BR> ");

			/*
			 * �Է�
			 */
			request.setCharacterEncoding("euc-kr");
			String groupName = request.getParameter("groupName");
			String who = request.getParameter("who");
			String date = request.getParameter("date");
			String memo = request.getParameter("memo");
			
			stmt = conn.createStatement();
			String command = String.format(
					"INSERT INTO `Memo`(`groupName`, `who`, `date`, `memo`) VALUES ('"+groupName+"','"+who+"',"+date+",'"+memo+"'); ");
			int rowNum = stmt.executeUpdate(command);
			if (rowNum < 1)
				throw new Exception("�����͸� DB�� �Է��� �� �����ϴ�. ");

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