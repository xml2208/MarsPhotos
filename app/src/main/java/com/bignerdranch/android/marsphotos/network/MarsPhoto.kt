package com.bignerdranch.android.marsphotos.network

import com.google.gson.annotations.SerializedName

data class MarsPhoto(val id: String, @SerializedName("img_src") val imgSrcUrl: String)