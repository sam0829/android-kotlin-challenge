package com.test.login.data.api.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("errorCode")
    var errorCode: String,
    @SerializedName("data")
    var data: Data,
    @SerializedName("errorMessage")
    var errorMessage: String,

) {
    data class Data(
        @SerializedName("userId")
        var userId: String,
        @SerializedName("userName")
        var userName: String,
        @SerializedName("isDeleted")
        var isDeleted: Boolean,
    )
}