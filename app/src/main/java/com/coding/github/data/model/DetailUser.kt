package com.coding.github.data.model

data class DetailUser(
    val login:String,
    val avatar_url:String,
    val followers:Int,
    val following:Int,
    val company:String,
    val location:String,
    val public_repos:Int,
    val name:String
)
