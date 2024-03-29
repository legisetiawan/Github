package com.coding.github.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coding.github.data.model.User
import com.coding.github.data.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel  : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setFollowing(username:String){
        ApiConfig.api.getFollowing(username).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful){
                    listFollowing.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                t.message?.let { Log.d("failure", it) }
            }

        })

    }

    fun getFollowing(): LiveData<ArrayList<User>> {
        return listFollowing
    }
}