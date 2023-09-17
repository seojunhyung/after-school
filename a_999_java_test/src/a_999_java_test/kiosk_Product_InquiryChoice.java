package a_999_java_test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class kiosk_Product_InquiryChoice {

	public static void main(String[] args) {
	       Scanner sc = new Scanner(System.in);
	
        System.out.print("주문 방법 입력 단품상품 : 1, 세트상품 : 2, 추가주문상품 :3, 전체 : 4, 종료 : 9: ");
        
		int choice = sc.nextInt();

        if (choice== 9) {
            System.out.println("프로그램을 종료합니다.");
            return;
        }

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "system";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("===================출력============================");
            System.out.println("  NO  상품코드   상품명      단가      주문 방법");
            System.out.println("=================================================");
String selectQuery;
            if (choice == 4) {
                selectQuery = "select pdt_id, pdt_id_name, pdt_unit_price, pdt_order_method from tbl_product_master";
            } else {
                selectQuery = "select pdt_id, pdt_id_name, pdt_unit_price, pdt_order_method from tbl_product_master where pdt_order_method = ?";
            }
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            if (choice != 4) {
                selectStatement.setInt(1, choice);
            }
            ResultSet resultSet = selectStatement.executeQuery();

            int n = 1;
            while (resultSet.next()) {
                int pdt_id = resultSet.getInt("pdt_id");
                String pdt_id_name = resultSet.getString("pdt_id_name");
                int pdt_unit_price = resultSet.getInt("pdt_unit_price");
                int order_method = resultSet.getInt("pdt_order_method");

                System.out.printf("%4d %7d  %-9s %7d %7d %n",  n, pdt_id, pdt_id_name, pdt_unit_price, order_method);
                n++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}