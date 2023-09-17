package a_999_java_test;

import java.sql.*;
import java.util.*;
import java.util.Date;

class Product_BuyTotal {
    public int tot_ord_no;           
    public int tot_ord_price;        
    public int tot_buying_method;    
    public int tot_in_money;         
    public int tot_out_money;        
    public Date tot_system_date;   

    public int cnt;                  
    public String method;

    void printScore() {
        System.out.printf("%3d   %5d    %5d     %2d       %1d        %1d \n",
                cnt, tot_ord_no, tot_ord_price, tot_buying_method, tot_in_money, tot_out_money);
    }
}

public class Kiosk_Product_BuyTotal {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int num_count = 0;
        int total = 0; 

        do {
            int in = 4;
            if (in == 9) {
                System.out.println("Kiosk Main Menu 화면으로 갑니다");
                S21209_Kiosk_MainMenu.main(args);
            } else if (in > 4 || in < 1) {
                System.out.println("프로그램 종료합니다");
                break;
            }

            Connection conn = null;
            PreparedStatement pstmt = null;
            String sql;

            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String id = "system";
            String pw = "1234";
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                System.out.println("클래스 로딩 성공");
                conn = DriverManager.getConnection(url, id, pw);
                System.out.println("DB 접속");
                sql = "select count(*) num from tbl_order_total";
                if (in > 0 && in <= 3) {
                    sql = "select count(*) num from tbl_order_total where tot_buying_method=" + in;
                }

                pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                rs.next();
                num_count = rs.getInt("num");
                System.out.println("등록된코드:" + num_count + "건");

                System.out.println("=========================================");
                System.out.println(" NO 주문번호    금액  지급방법  받은 돈  거스름 돈");
                System.out.println("=========================================");

                if (in > 0 && in <= 3) {
                    sql = "select * from tbl_order_total where tot_buying_method=" + in + " order by tot_ord_no";
                } else {
                    sql = "select * from tbl_order_total order by tot_buying_method, tot_ord_no";
                }
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                num_count = 0;

                Product_BuyTotal p = new Product_BuyTotal();
                while (rs.next()) {
                    p.cnt = num_count + 1;
                    num_count++;
                    p.tot_ord_no = rs.getInt("tot_ord_no");
                    p.tot_ord_price = rs.getInt("tot_ord_price");
                    p.tot_buying_method = rs.getInt("tot_buying_method");
                    if (rs.getInt("tot_buying_method") == 1) {
                        p.method = "카드";
                    } else if (rs.getInt("tot_buying_method") == 2) {
                        p.method = "현금";
                    }
                    p.tot_in_money = rs.getInt("tot_in_money");
                    p.tot_out_money = rs.getInt("tot_out_money");

                    p.printScore();
                    total += p.tot_ord_price;
                }
                System.out.println("======================================");
                System.out.println("***매출합계: " + total);

            } catch (Exception e) {
                e.printStackTrace();
            }

            break;
        } while (true);
    }
}