<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*, javax.sql.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="com.material.model.*"%>
<%@ page trimDirectiveWhitespaces="true" %>

<%
/*有空再解決這白癡的問題...*/
String MA_NO = request.getParameter("MA_NO");

switch (MA_NO){
case "L0001" :
	MA_NO="'L' || '0001'";
	break;
case "L0002" :
	MA_NO="'L' || '0002'";
	break;
case "L0003" :
	MA_NO="'L' || '0003'";
	break;
case "L0004" :
	MA_NO="'L' || '0004'";
	break;
case "L0005" :
	MA_NO="'L' || '0005'";
	break;
case "L0006" :
	MA_NO="'L' || '0006'";
	break;
case "L0007" :
	MA_NO="'L' || '0007'";
	break;
case "L0008" :
	MA_NO="'L' || '0008'";
	break;
case "L0009" :
	MA_NO="'L' || '0009'";
	break;
case "L0010" :
	MA_NO="'L' || '0010'";
	break;
}


Class.forName("oracle.jdbc.driver.OracleDriver");
String URL="jdbc:oracle:thin:@localhost:1521:XE";
String userid = "G1";
String passwd = "123456";
Connection con = DriverManager.getConnection(URL,userid,passwd);


try{
	// 準備語句執行物件
	Statement stmt = con.createStatement();
	String sql = " SELECT * FROM MATERIAL_DATA WHERE MA_NO = "+MA_NO;
	ResultSet rs = stmt.executeQuery(sql);
	if (rs.next()) {
	Blob b = rs.getBlob("MA_PHOTO");
	long size = b.length();
	//out.print(size);
	byte[] bs = b.getBytes(1, (int)size);
	response.setContentType("image/jpeg");
	OutputStream outs = response.getOutputStream();
	outs.write(bs);
	outs.flush();
	rs.close();
	}
	else {
	rs.close();
	response.sendRedirect("./error.jpg");
	}
	}
	finally{
	con.close();
	}
	%>
	