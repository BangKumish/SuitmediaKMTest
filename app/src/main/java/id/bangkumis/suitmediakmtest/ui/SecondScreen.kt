package id.bangkumis.suitmediakmtest.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import id.bangkumis.suitmediakmtest.R
import id.bangkumis.suitmediakmtest.databinding.FragmentSecondScreenBinding

class SecondScreen: AppCompatActivity() {
    private lateinit var binding: FragmentSecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Second Screen"

        val username = intent.getStringExtra(EXTRA_NAME)
        binding.usernameTV.text = username

        setupAction()
    }

    private fun setupAction(){
        var selectedUser = binding.tvSelectedUser.isVisible
        binding.choseUserButton.setOnClickListener {
            val thirdScreen = Intent(this, ThirdScreen::class.java)
            if(!selectedUser){
                startActivity(thirdScreen)
            } else {
                Toast.makeText(this, "Click a name!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.usernameTV.setOnClickListener {
            binding.tvSelectedUser.visibility = View.VISIBLE
            binding.tvSelectedUser.text = SELECTED
            binding.tvSelectedUser.setTextColor(ContextCompat.getColor(this, R.color.grey))
            selectedUser = false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val SELECTED = "Selected User Name"
    }
}