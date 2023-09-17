package a_999_java_test;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class buy_list {
    
}

public class kiosk_product_buylist {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql;

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "system";
        String pw = "1234";

        while (true) {
            System.out.print("주문번호를 입력하세요. 전체내역은: 1, 종료는 9: ");
            int orderNumber = sc.nextInt();

            if (orderNumber == 1) {
                try {
                    Class.forName("oracle.jdbc.OracleDriver");
                    conn = DriverManager.getConnection(url, id, pw);

                 
                    sql = "SELECT ord_no, ord_count, ORD_BUYING_COUNT, ORD_PDT_UNIT_PRICE, ORD_PRICE FROM tbl_order_list order by ord_no, ord_count asc";
                    pstmt = conn.prepareStatement(sql);
                    ResultSet rs = pstmt.executeQuery();

                    int num_count = 0;
                    int totalSales = 0;

                    System.out.println("==========================================================");
                    System.out.println(" NO 주문번호       카운트        구매수량      단위가격       금액");
                    System.out.println("==========================================================");

                    while (rs.next()) {
                        int ordNo = rs.getInt("ord_no");
                        int ordcount = rs.getInt("ord_count");
                        int ordbuycnt = rs.getInt("ORD_BUYING_COUNT");
                        int ordunitprice = rs.getInt("ORD_PDT_UNIT_PRICE");
                        int ordprice = rs.getInt("ORD_PRICE");
                        System.out.printf("%2d %7d %10d %10d %10d %10d%n", ++num_count, ordNo, ordcount, ordbuycnt, ordunitprice, ordprice);
                        totalSales += ordprice;
                    }

                    System.out.println("==========================================================");
                    System.out.println("         총 주문금액: " + totalSales);
                    System.out.println("==========================================================");

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
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            } else if (orderNumber == 9) {
                System.out.println("종료합니다.");
                break; 
            } else {
                try {
                    Class.forName("oracle.jdbc.OracleDriver");
                    conn = DriverManager.getConnection(url, id, pw);

                   
                    sql = "SELECT ord_no, ord_count, ORD_BUYING_COUNT, ORD_PDT_UNIT_PRICE, ORD_PRICE FROM tbl_order_list  WHERE ord_no = ? order by ord_count asc";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, orderNumber);
                    ResultSet rs = pstmt.executeQuery();

                    int num_count = 0;
                    int totalSales = 0;

                    System.out.println("==========================================================");
                    System.out.println(" NO 주문번호      카운트        구매수량      단위가격        금액");
                    System.out.println("==========================================================");

                    while (rs.next()) {
                        int ordNo = rs.getInt("ord_no");
                        int ordcount = rs.getInt("ord_count");
                        int ordbuycnt = rs.getInt("ORD_BUYING_COUNT");
                        int ordunitprice = rs.getInt("ORD_PDT_UNIT_PRICE");
                        int ordprice = rs.getInt("ORD_PRICE");
                        System.out.printf("%2d %7d %10d %10d %10d %10d%n", ++num_count, ordNo, ordcount, ordbuycnt, ordunitprice, ordprice);
                        totalSales += ordprice;
                    }

                    System.out.println("==========================================================");
                    System.out.println("         총 주문금액: " + totalSales);
                    System.out.println("==========================================================");

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
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        }

        sc.close();
    }
}