package id.bangkumis.suitmediakmtest.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import id.bangkumis.suitmediakmtest.R
import id.bangkumis.suitmediakmtest.databinding.FragmentSecondScreenBinding

@Suppress("DEPRECATION")
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

        setupAction(username!!)
    }

    private fun setupAction(username: String){
        binding.choseUserButton.setOnClickListener {
            val thirdScreen = Intent(this, ThirdScreen::class.java)
            startActivityForResult(thirdScreen, REQUEST_CODE)
        }
        binding.usernameTV.setOnClickListener {
            binding.tvSelectedUser.visibility = View.VISIBLE
            binding.tvSelectedUser.text = username
            binding.tvSelectedUser.setTextColor(ContextCompat.getColor(this, R.color.grey))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedUsername = data?.getStringExtra(SELECTED)
            val selectedAvatarUrl = data?.getStringExtra(SELECTED_AVATAR)

            selectedUsername?.let {
                binding.usernameTV.text = it
                binding.tvSelectedUser.visibility = View.GONE // Hide the temporary text view
                binding.imageViewSelectedUser.visibility = View.VISIBLE
                Glide.with(this)
                    .load(selectedAvatarUrl)
                    .circleCrop()
                    .into(binding.imageViewSelectedUser)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val SELECTED = "selected"
        const val SELECTED_AVATAR = "selected_avatar"
        private const val REQUEST_CODE = 100
    }
}