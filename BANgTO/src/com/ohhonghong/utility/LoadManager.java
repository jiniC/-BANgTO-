package com.ohhonghong.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadManager {
	URL url;				//���Ӵ�� �����ּҸ� ���� ��ü
	HttpURLConnection conn;	//����� ����ϴ� ��ü
	BufferedReader buffer=null;

	//�ʿ��� ��ü �ʱ�ȭ
	public LoadManager(String jsp,String group) {
		try {
			url = new URL("http://119.205.252.231:8080/BANgToServer/"+jsp+".jsp?groupName='"+group+"'");
			conn = (HttpURLConnection)url.openConnection();
			
/*			conn.setRequestMethod("GET");
			conn.setRequestProperty("groupName", group);*/
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String request(){
		String data="";
		try {
			conn.connect();			//�������� ��û�ϴ� ����
			InputStream is = conn.getInputStream();	//�������κ��� ���۹��� �����Ϳ� ���� ��Ʈ�� ���
			
			//1byte����� ����Ʈ��Ʈ���̹Ƿ� �ѱ��� ������.
			//���� ����ó���� ���ڱ���� ��Ʈ������ ���׷��̵� �ؾ� �ȴ�.
			buffer = new BufferedReader(new InputStreamReader(is));
			
			//��Ʈ���� �������Ƿ�, ���ڿ��� ��ȯ
			StringBuffer str = new StringBuffer();
			String d=null;
			while( (d=buffer.readLine()) != null){
				str.append(d);
			}
			
			data = str.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(buffer!=null){
				try {
					buffer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return data;
		
	}
	
}