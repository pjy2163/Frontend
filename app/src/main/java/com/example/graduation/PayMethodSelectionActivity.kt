package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.graduation.databinding.ActivityPayMethodSelectionBinding

class PayMethodSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayMethodSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayMethodSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPagerCard.adapter = ViewPagerAdapter(getCardList()) // Create the adapter
        binding.viewPagerCard.orientation = ViewPager2.ORIENTATION_HORIZONTAL // Set the orientation

        binding.viewPagerCard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.indicator0IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_grey))
                binding.indicator1IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_grey))
                binding.indicator2IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_grey))
                binding.indicator3IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_grey))

                when (position) {
                    0 -> binding.indicator0IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_purple))
                    1 -> binding.indicator1IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_purple))
                    2 -> binding.indicator2IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_purple))
                    3 -> binding.indicator3IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_purple))

                }
            }
        })

        binding.prevBtn.setOnClickListener {
            val intent = Intent(this, CheckPayInfoActivity::class.java)
            startActivity(intent)
        }

        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, AuthWayActivity::class.java)
            startActivity(intent)
        }
    }

    // Items for the ViewPager
    private fun getCardList(): ArrayList<Int> {
        return arrayListOf(
            R.drawable.img_card_kookmin,
            R.drawable.img_card_shinhan,
            R.drawable.img_card_hana,
            R.drawable.img_card_woori
        )
    }
}
