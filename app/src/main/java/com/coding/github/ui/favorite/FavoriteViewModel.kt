package com.coding.github.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.coding.github.data.local.FavoriteDao
import com.coding.github.data.local.FavoriteDatabase
import com.coding.github.data.local.FavoriteUser

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: FavoriteDao?
    private var userDb: FavoriteDatabase?

    init {
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }

    fun getFavorite(): LiveData<List<FavoriteUser>>? {
        return userDao?.getUser()
    }
}