package dear.melbet.soor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.SharedPreferences
import dear.melbet.soor.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tv1.visibility = View.INVISIBLE
        binding.eT1.text = null
        binding.eT2.text = null
        binding.eT1.hint = "Your height"
        binding.eT2.hint = "Your weight"

        binding.bt1.setOnClickListener {
            val height = binding.eT1.text.toString()
            val weight = binding.eT2.text.toString()
            val a = height.toInt()
            val b = weight.toInt()
            val res = a - b
            when (res) {
                in 90..110 -> binding.tv1.text = "Your health is normal!"
                else -> {
                    binding.tv1.text = "Watch yourself harder!"
                }
            }
            binding.tv1.visibility = View.VISIBLE
        }
    }
}
