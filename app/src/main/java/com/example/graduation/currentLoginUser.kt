package com.example.graduation

class LoginUser(uid: String)
{
    val uid:String
    var nickname:String?
    init
    {
        this.uid=uid
        this.nickname=null
    }
    companion object {
        const val logout = 0
        const val email = 1
    }
}

object LoginInformation {
    var currentLoginUser: LoginUser?
    var loginType: Int
    //private val db = "DB 경로"//
    init {
        currentLoginUser=null
        loginType=LoginUser.logout
    }

    fun setCurrentLoginUserWithUID(uid: String, loginType: Int)
    {
    /*  this.loginType=loginType
        currentLoginUser=LoginUser(uid)
        "DB 사용자 경로".whereEqualTo("DB uid 변수명", uid).get().addOnSuccessListener{
            for ("DB 각 document")
            {
                val nickname = document.data["nickname"].toString()
                currentLoginUser!!.nickname=nickname
            }
        }
    */
    }

    fun logout()
    {
        this.loginType=LoginUser.logout
        this.currentLoginUser=null
    }
}