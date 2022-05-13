package com.example.utt.hnccomputer.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("customerID")
	val customerId: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("avatarUrl")
	val avatarUrl: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)