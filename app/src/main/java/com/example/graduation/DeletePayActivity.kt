package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivityDeletePayBinding
import com.example.graduation.databinding.ActivityMainBinding

class DeletePayActivity : AppCompatActivity() {

    val binding by lazy { ActivityDeletePayBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_pay)


    }
}