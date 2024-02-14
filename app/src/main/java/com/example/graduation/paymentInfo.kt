package com.example.graduation

//결제정보 확인, 결제승인 확인에 쓰일 데이터 클래스
data class PaymentInfo(
    val storeName: String="", //결제업체명
    val productName: String="", //상품이름
    val amount: Double=0.0 //결제금액
)

//나중에 다른 곳에 옮길 결제정보 더미리스트
/*
private fun inputDummyMenu(storeId: Int) {
//        var storeId = arguments?.getInt("storeId", -1)!!
    //var storeId = 1
    menuDatas.apply {
        add(Menu(1, storeId, "메뉴 1" ,"고기 토마토 소스와 함께 얇은 면의 스파게티를 곁들인 파스타.\n마늘, 양파, 토마토, 베이컨.","270 * 130 mm", 22000,0,0, "더운 날씨에 상할 수 있으므로 바로 드시는 것을\n" +
                "권장드립니다.",1, 1,R.drawable.store_img_1))
        add(Menu(2, storeId, "메뉴 2", "풍부한 크림과 파마산 치즈의 풍미가 일품.\n양파, 치즈.","250 * 130 mm", 11000,0,0, "알레르기 관련 요청사항은 주문 시에 명시해주세요." +
                "주문 시에 명시해주세요.",1, 1, R.drawable.store_img_2))
        add(Menu(3, storeId, "메뉴 3", "스파게티에 계란, 파마산 치즈, 페이콘, 후추 등을 사용하여 만든 고소하고 크리미한 파스타.\n계란,치즈,페이콘,후추.","270 * 150 mm", 13000, 0,0, "주문 시 주문 항목, 수량, 옵션 등을 정확하게 기재해주세요.\n" +
                "",1,1, R.drawable.store_img_3))
        add(Menu(4, storeId, "메뉴 4", "올리브 오일, 마늘, 후추로 만든 클래식 알리오 올리오 파스타.\n올리브 오일, 마늘, 후추.","권장용량/크기", 6000, 0,0, "음식 수령 시간을 정확하게 확인하고 주문을 진행해주세요.\n" +
                "",1,2, R.drawable.store_img_1))
        add(Menu(5, storeId, "메뉴 5", "라자냐 시트, 고기 소스, 토마토 소스, 치즈로 만든 라자냐.\n고기, 토마토.","권장용량/크기", 4000, 0,0, "공동 현관 비밀번호가 있을 경우 주문 메시지란에 적어주세요.\n" +
                "",1,2, R.drawable.store_img_2))
        add(Menu(6, storeId, "메뉴 6", "올리브, 토마토, 마늘로 누구나 좋아하는 맛.\n올리브, 토마토, 마늘.","권장용량/크기", 3000, 0,0, "주문 전 정확한 건물명, 동, 호 등 상세 주소를 확인해주세요.\n" +
                "",1,2, R.drawable.store_img_3))
    }
}*/
