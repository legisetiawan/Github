package com.coding.github.data.model

import com.google.gson.annotations.SerializedName

data class ResponUser (
    @field:SerializedName("items")
    val items:ArrayList<User>
)