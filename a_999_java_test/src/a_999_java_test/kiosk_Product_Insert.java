package a_999_java_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class ProductChoice9 {
	int    pdt_id;
	String    pdt_id_name;
	int    pdt_unit_price;
	int    pdt_order_method;
	
	public int    cnt;
	public String    method;
	
	
	void printScore() {
		System.out.printf("%3d %5d %5d %2d %s %2s \n",
				cnt, pdt_id, pdt_unit_price, pdt_order_method, method, pdt_id_name);
	
		
	}
}

public class kiosk_Product_Insert {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);

		System.out.println("단품상품 - 1 세트상품 -2 추가주문상품 -3 전체 -4 종료 -9 :");
		int i_order_method = sc.nextInt();
		int pom=0; 
		
		Connection conn = null;
        PreparedStatement pstmt = null;
        String sql;
        int num_count=0; //등록된 데이터베이스 자료 건수
       
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "system";
        String pw = "1234";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("클래스 로딩 성공");
            conn = DriverManager.getConnection(url, id, pw);
            System.out.println("DB 접속 성공");
            
            sql="select count(*) num from tbl_product_master";
            if (i_order_method > 0 && i_order_method <=3) {
            	sql="select count(*) num from tbl_product_master where pdt_order_method="+ i_order_method;
            }
            pstmt=conn.prepareStatement(sql);
            ResultSet rs=pstmt.executeQuery();
            rs.next();
            //num_count=rs.getInt(1);
            num_count = rs.getInt("num");
            System.out.println("등록된 코드 : "+ num_count + "건");
            
            System.out.println("===============상품코드 등록 내용===============");
            System.out.println("   NO   상품코드   단가      주문방법      상품명   \n");
            System.out.println("============================================");
		
            if (i_order_method > 0 && i_order_method <= 3) {
            	sql = "select * from tbl_product_master where pdt_order_method="+ i_order_method
					+  " order by pdt_id";
            }else {
            	sql="select * from tbl_product_master order by pdt_order_method, pdt_id";
            }
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
		
            num_count = 0;
            ProductChoice9 p = new ProductChoice9();
            while(rs.next()) {
        
            	p.cnt=num_count+1;
            	num_count++;
            	p.pdt_id = rs.getInt("pdt_id");
            	p.pdt_unit_price = rs.getInt("pdt_unit_price");
            	p.pdt_order_method = rs.getInt("pdt_order_method");
            	if(rs.getInt("pdt_order_method") ==1) {
            		p.method = "단품";
            	} else if(rs.getInt("pdt_order_method") ==2) {
            		p.method = "세트";
            	} else if(rs.getInt("pdt_order_method") ==3) {
            		p.method = "추가";
            	}
             
            	p.pdt_id_name = rs.getString("pdt_id_name");
            	p.printScore();
            }
            System.out.println("==============================================");
        } catch(Exception e) {
        	e.printStackTrace();
        }
     }
}
    