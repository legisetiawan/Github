package com.coding.github.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coding.github.data.model.ResponUser
import com.coding.github.data.model.User
import com.coding.github.data.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    val list = MutableLiveData<ArrayList<User>>()

    fun setSearch(query:String) {
        ApiConfig.api
            .getUser(query)
            .enqueue(object : Callback<ResponUser> {
                override fun onResponse(call: Call<ResponUser>, response: Response<ResponUser>) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<ResponUser>, t: Throwable) {
                    t.message?.let { Log.d("failure", it) }
                }

            })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return list
    }
}