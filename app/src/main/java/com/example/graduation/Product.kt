package com.example.graduation

//결제하기 >>결제 정보 확인에서 결제할 물건
data class Product(
    val storeId:Int=0, //결제 아이디 (Ex. 1,2,3,4)
    val storeName: String="", //결제업체명
    val price: Int=0, //결제금액

)
