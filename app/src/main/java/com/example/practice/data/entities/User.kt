package com.example.practice.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTable")
data class User(
    @SerializedName(value="email") var email: String,
    @SerializedName(value="email")var password: String,
    @SerializedName(value="email")var name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}