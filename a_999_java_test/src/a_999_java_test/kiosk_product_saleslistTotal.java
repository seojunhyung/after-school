package a_999_java_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class kiosk_product_saleslistTotal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql;

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "system";
        String pw = "1234";

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(url, id, pw);

            while (true) {
                System.out.print("주문번호를 입력하세요. 전체내역은: 1, 종료는 9: ");
                int orderNumber = sc.nextInt();

                if (orderNumber == 1) {
                    sql = "SELECT b.ord_pdt_id, SUM(b.ord_buying_count) AS ord_buying_count, SUM(b.ord_price) AS ord_price, a.pdt_id_name "
                            + "FROM tbl_product_master a, tbl_order_list b, tbl_order_total c "
                            + "WHERE a.pdt_id = b.ord_pdt_id AND b.ord_no = c.tot_ord_no "
                            + "GROUP BY a.pdt_id_name, b.ord_pdt_id "
                            + "ORDER BY b.ord_pdt_id";

                    pstmt = conn.prepareStatement(sql);
                    ResultSet rs = pstmt.executeQuery();

                    System.out.println("=====================================================");
                    System.out.println(" NO  상품코드     수량        금액      상품명       ");
                    System.out.println("=====================================================");
                    int count = 0;
                    int num_count = 0;
                    int totalSales = 0;

                    while (rs.next()) {
                        int ordPdtId = rs.getInt("ord_pdt_id");
                        int ordBuyingCount = rs.getInt("ord_buying_count");
                        int ordPrice = rs.getInt("ord_price");
                        String pdtName = rs.getString("pdt_id_name");
                        System.out.printf("  %2d   %5d   %3d   %9d   %5s \n",
                                ++num_count, ordPdtId, ordBuyingCount, ordPrice, pdtName);
                        totalSales += ordPrice;
                        count += ordBuyingCount;
                    }

                    System.out.println("==============================================");
                    System.out.println("        총 수량: " + count + " 총 금액 " + totalSales);
                    System.out.println("==============================================");
                } else if (orderNumber == 9) {
                	System.out.println("키오스크 메인 화면으로 돌아갑니다.");
                    break;
                } else {
                    sql = "SELECT b.ord_pdt_id, SUM(b.ord_buying_count) AS ord_buying_count, SUM(b.ord_price) AS ord_price, a.pdt_id_name "
                            + "FROM tbl_product_master a, tbl_order_list b, tbl_order_total c "
                            + "WHERE b.ord_pdt_id = ? AND a.pdt_id = b.ord_pdt_id AND b.ord_no = c.tot_ord_no "
                            + "GROUP BY a.pdt_id_name, b.ord_pdt_id "
                            + "ORDER BY b.ord_pdt_id";

                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, orderNumber);
                    ResultSet rs = pstmt.executeQuery();

                    System.out.println("===========================================================");
                    System.out.println(" NO    상품코드  수량        금액      상품명       ");
                    System.out.println("===========================================================");
                    int count = 0;
                    int num_count = 0;
                    int totalSales = 0;

                    while (rs.next()) {
                        int ordPdtId = rs.getInt("ord_pdt_id");
                        int ordBuyingCount = rs.getInt("ord_buying_count");
                        int ordPrice = rs.getInt("ord_price");
                        String pdtName = rs.getString("pdt_id_name");
                        System.out.printf("  %2d   %5d   %3d   %9d   %5s \n",
                                ++num_count, ordPdtId, ordBuyingCount, ordPrice, pdtName);
                        totalSales += ordPrice;
                        count += ordBuyingCount;
                    }

                    System.out.println("==============================================");
                    System.out.println("         총 주문금액: " + totalSales+ "총 수량 : "+ count);
                    System.out.println("==============================================");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
