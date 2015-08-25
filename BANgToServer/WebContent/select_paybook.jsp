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
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/BANgTO?useUnicode=true&characterEncoding=UTF8", "root", "bangto");
		// "mysql URL/데이터베이스 이름", "ID", "PASSWORD"
		//int n = 0;

		if (conn == null)
			throw new Exception("데이터베이스에 연결할 수 없습니다.<BR> ");

		/*
		 * 선택
		 */
		
		stmt = conn.createStatement();
		request.setCharacterEncoding("UTF-8");
		String group = request.getParameter("groupName");
		group = new String(group.getBytes("ISO-8859-1"), "UTF-8");  
		
		ResultSet rs = stmt.executeQuery("select * from PayBook,GroupInvite where GroupInvite.groupName = "+ group+" and PayBook.groupName ="+group+"; ");

		JSONObject jsonMain = new JSONObject();
		JSONArray jArray = new JSONArray();

		while (rs.next()) {
			int id = rs.getInt("id");
			String groupName = rs.getString("groupName");
			Date date = rs.getDate("date");
			int plus = rs.getInt("plus");
			int minus = rs.getInt("minus");
			int balance = rs.getInt("balance");
			String content = rs.getString("content");

			//하나의 정보를 저장할 JSONObject를 설정
			JSONObject jObject = new JSONObject();
			
			//데이터를 삽입
			jObject.put("id", id);
			jObject.put("groupName", groupName);
			jObject.put("date", date);
			jObject.put("plus", plus);
			jObject.put("minus", minus);
			jObject.put("balance", balance);
			jObject.put("content", content);

			
			//JSONArray배열객체에 하나의 JSONObject를 저장
			jArray.add(n, jObject);
			//전체 JSONObject를저장할 메인JSONObjec객체를 설정하여 저장(키,벨류)
			
			// n++;
			// toUnicode(name)		
			n++;
			
		}
		jsonMain.put("PayBook", jArray);
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