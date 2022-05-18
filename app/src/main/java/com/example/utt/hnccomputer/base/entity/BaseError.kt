package com.example.utt.hnccomputer.base.entity

data class BaseError(var error: String, var code: Int = 1) : Exception(error)