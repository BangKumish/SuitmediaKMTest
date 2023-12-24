package id.bangkumis.suitmediakmtest.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.bangkumis.suitmediakmtest.databinding.FragmentFirstScreenBinding

class FirstScreen : AppCompatActivity() {
    private lateinit var binding: FragmentFirstScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction(){
        val palindrome = binding.edtPalindrome.text
        val name = binding.edtName.text

        binding.palindromeButton.setOnClickListener {
            if(palindrome.isEmpty()){
                binding.edtPalindrome.error = "Must be Filled"
            } else {
                val isPalindrome = checkPalindrome(palindrome)

                if (isPalindrome){
                    Toast.makeText(this, "Palindrome", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "Not Palindrome", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.nextButton.setOnClickListener {
            if(name.isEmpty()){
                binding.edtName.error = "Must be Filled"
            }else toSecondScreen(name.toString())
        }
    }

    private fun checkPalindrome(string: Editable):Boolean {
        val textLength = string.length
        var i = 0
        while(i<textLength/2){
            i++
            if(string[i] != string[textLength-1-i]){
                return false
            }
        }
        return true
    }

    private fun toSecondScreen(name: String) {
        val action = Intent(this, SecondScreen::class.java)
        action.putExtra(SecondScreen.EXTRA_NAME, name)
        startActivity(action)
    }

}