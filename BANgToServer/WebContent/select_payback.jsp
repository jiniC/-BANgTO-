<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.*"%>

<%
	int n=0;
	Connection conn = null;
	Statement stmt = null;
	try {
		/*
		 * 연결
		 */
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/BANgTO", "root", "bangto");
		// "mysql URL/데이터베이스 이름", "ID", "PASSWORD"
		//int n = 0;

		if (conn == null)
			throw new Exception("데이터베이스에 연결할 수 없습니다.<BR> ");

		/*
		 * 선택
		 */
		
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from PayBack,GroupInvite where GroupInvite.groupName = "+ request.getParameter("groupName")+" and PayBack.groupName ="+request.getParameter("groupName")+"; ");

		JSONObject jsonMain = new JSONObject();
		JSONArray jArray = new JSONArray();

		while (rs.next()) {
			int id = rs.getInt("id");
			String groupName = rs.getString("groupName");
			String from = rs.getString("from");
			String to = rs.getString("to");
			int howMuch = rs.getInt("howMuch");

			//하나의 정보를 저장할 JSONObject를 설정
			JSONObject jObject = new JSONObject();
			
			//데이터를 삽입
			jObject.put("id", id);
			jObject.put("groupName", groupName);
			jObject.put("from", from);
			jObject.put("to", to);
			jObject.put("howMuch", howMuch);
			
			//JSONArray배열객체에 하나의 JSONObject를 저장
			jArray.add(n, jObject);
			//전체 JSONObject를저장할 메인JSONObjec객체를 설정하여 저장(키,벨류)
			
			// n++;
			// toUnicode(name)		
			n++;
			
		}
		jsonMain.put("PayBack", jArray);
		out.print(jsonMain.toJSONString());
		out.flush();
		

		

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