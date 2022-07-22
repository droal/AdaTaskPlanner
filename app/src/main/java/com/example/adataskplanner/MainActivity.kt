package com.example.adataskplanner

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.adataskplanner.data.api.AdaTaskPlannerApi
import com.example.adataskplanner.data.model.request.AuthenticationBodyDto
import com.example.adataskplanner.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.adaschool.rest.storage.TokenStorage
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var adaTaskPlannerApi: AdaTaskPlannerApi

    @Inject
    lateinit var tokenStorage: TokenStorage

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticate()

        binding.button.setOnClickListener {
            getAllUsers(binding.textView)
        }

    }


    private fun authenticate(){
        GlobalScope.launch(Dispatchers.IO) {
            val response = adaTaskPlannerApi.authentication(AuthenticationBodyDto())
            response.body()?.let { tokenStorage.saveToken(it.accessToken) }
        }
    }

    private fun getAllUsers(textView: TextView){
        GlobalScope.launch(Dispatchers.IO) {
            val response = adaTaskPlannerApi.getAllUsers()

            if(response.isSuccessful){

                runOnUiThread {
                    response.body()?.forEach {
                        textView.text = textView.text.toString() + it.name.toString() + "\n"
                    }
                }

                }else{
                Log.d("getAllUsersErr", "Err: $response")
            }
        }
    }

}
