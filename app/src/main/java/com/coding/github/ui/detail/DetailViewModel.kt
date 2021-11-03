package com.coding.github.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coding.github.data.local.FavoriteDao
import com.coding.github.data.local.FavoriteDatabase
import com.coding.github.data.local.FavoriteUser
import com.coding.github.data.model.DetailUser
import com.coding.github.data.network.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application) : AndroidViewModel(application) {
    val userDetail = MutableLiveData<DetailUser>()
    private var userDao: FavoriteDao?
    private var userDb: FavoriteDatabase?

    init {
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }

    fun setDetail(username: String) {
        ApiConfig.api.getDetail(username).enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                if (response.isSuccessful) {
                    userDetail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                t.message?.let { Log.d("failure", it) }
            }

        })

    }

    fun getDetail(): LiveData<DetailUser> {
        return userDetail
    }

    fun addFavorite(username: String, id: Int, avatarUrl: String, htmlUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                id,
                avatarUrl,
                htmlUrl
            )
            userDao?.addFavoriteUser(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavorite(id)
        }
    }
}