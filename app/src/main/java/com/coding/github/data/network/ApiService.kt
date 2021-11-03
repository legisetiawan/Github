package com.coding.github.data.network

import com.coding.github.data.model.DetailUser
import com.coding.github.data.model.ResponUser
import com.coding.github.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization:token api")
    fun getUser(@Query("q") query:String): Call<ResponUser>

    @GET("users/{username}")
    @Headers("Authorization:token api")
    fun getDetail(@Path("username") username: String?):Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization:token api")
    fun getFollowers(@Path("username") username:String):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization:token api")
    fun getFollowing(@Path("username") username:String):Call<ArrayList<User>>
}
