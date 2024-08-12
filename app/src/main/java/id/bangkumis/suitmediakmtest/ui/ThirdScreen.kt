package id.bangkumis.suitmediakmtest.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.bangkumis.suitmediakmtest.adapter.Adapter
import id.bangkumis.suitmediakmtest.databinding.FragmentThirdScreenBinding
import id.bangkumis.suitmediakmtest.model.DataItem
import id.bangkumis.suitmediakmtest.model.Response
import id.bangkumis.suitmediakmtest.network.ApiConfig
import retrofit2.Call

class ThirdScreen: AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentThirdScreenBinding
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = FragmentThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Third Screen"

        binding.refresh.setOnRefreshListener(this)

        adapter = Adapter()
        binding.userRV.layoutManager = LinearLayoutManager(this)
        binding.userRV.adapter = adapter
        binding.userRV.setHasFixedSize(true)
        adapter.setClickCallback(object : Adapter.OnItemClickCallback{
            override fun onItemClicked(data: DataItem) {
                val resultIntent = Intent()
                resultIntent.putExtra(SecondScreen.SELECTED, data.firstName + " " + data.lastName)
                resultIntent.putExtra(SecondScreen.SELECTED_AVATAR, data.avatar)
                setResult(RESULT_OK, resultIntent)
                finish()

//                Intent(this@ThirdScreen, SecondScreen::class.java).also{
//                    it.putExtra(SecondScreen.SELECTED, data.firstName)
//                    it.putExtra(SecondScreen.SELECTED_AVATAR, data.avatar)
//                    startActivity(it)
//                    page = 1
//                    finish()
//                }
            }
        })

        getAllUsers(false)
    }

    private fun getAllUsers(isRefresh: Boolean){
        isLoading = true
        if(!isRefresh){
            binding.progressbar.visibility = View.VISIBLE
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val params = HashMap<String, String>()
            params["page"] = page.toString()
            ApiConfig.getApiService()
                .getAllUser(params)
                .enqueue(object : retrofit2.Callback<Response>{
                    override fun onResponse(
                        call: Call<Response>,
                        response: retrofit2.Response<Response>
                    ) {
                        if(response.isSuccessful){
                            totalPage = response.body()?.totalPages!!
                            val listUsers = response.body()?.data
                            Log.d("Third Screen", "onResponse: $listUsers")

                            if(listUsers!!.isNotEmpty()){
                                adapter.setList(listUsers as ArrayList<DataItem>)
                            }

                            binding.progressbar.visibility = View.GONE
                            isLoading = false
                            binding.refresh.isRefreshing = false
                        }
                    }

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Log.d("onFailure", t.message.toString())
                        Toast.makeText(this@ThirdScreen, "Connection Failed..", Toast.LENGTH_SHORT).show()
                        binding.progressbar.visibility = View.GONE
                        isLoading = false
                        binding.refresh.isRefreshing = false
                    }
                })
        }, 3000)
    }

    override fun onRefresh() {
        adapter.clearUsers()
        page = 2
        getAllUsers(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object{
        private var isLoading = false
        private var page: Int = 1
        private var totalPage: Int = 1
    }

}