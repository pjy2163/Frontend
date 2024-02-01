package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivityMainBinding
import com.example.graduation.databinding.ActivityPayPasswordBinding

class PayPasswordActivity : AppCompatActivity() {

    val binding by lazy { ActivityPayPasswordBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_password)

        val intent = Intent(this, DeletePayActivity::class.java)
        binding.nextBtn.setOnClickListener{startActivity(intent)}
    }
}