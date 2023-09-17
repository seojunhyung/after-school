package a_999_java_test;

import java.util.Scanner;

public class S21209_Kiosk_MainMenu {

	public static void main(String[] args) {
		
		Scanner stdIn=new Scanner(System.in);
		
		while(true) {
		System.out.println("========================================");
		System.out.println("***** KIOSK 상품등록 및 매장 운영프로그램 *****");
		System.out.println("========================================");
		
		System.out.println("   1. 상품코드 등록");
		System.out.println("   2. 등록된 상품코드 조회");
		System.out.println("   3. KIOSK 매장 운영 프로그램(주문입력)");
		System.out.println("   4. 매장 매출현황(주문서별 합계)");
		System.out.println("   5. 매장 무준서별 LIST");
		System.out.println("   6. 상품별 매출 수량 및 금액 총합");
		System.out.println("   7. 상품별 일차별 매출 리스트");
		System.out.println("   0. 작업 종료");
		System.out.println("========================================");
		System.out.print("\n작업 번호를 선택하세요 > ");
		int num=stdIn.nextInt();
		
		if(num==1) {
			kiosk_Product_Insert.main(args);
			continue;
		}else if(num==2) {
			kiosk_Product_InquiryChoice.main(args);
			continue;
		}else if(num==3) {
		  Kiosk_Prduct_BuyChoice.main(args);
			continue;
		}else if(num==4) {
			Kiosk_Product_BuyTotal.main(args);
			continue;
		}else if(num==5) {
			kiosk_product_buylist.main(args);
			continue;
		}else if(num==6) {
			kiosk_product_saleslistTotal.main(args);
			continue;
		}else if(num==7) {
			kiosk_product_saleslist.main(args);
			continue;
		}else if(num==0) {
			System.out.println("\n작업을 종료합니다.");
			break;
		}else {
			System.err.println("\n제대로 다시 입력하세요.\n");
			continue;
		}
		}
	}

}