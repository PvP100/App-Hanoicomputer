package com.example.utt.hnccomputer.entity.model

data class FilterOption(
    val filterId: Int,
    var title: String,
    var isChecked: Boolean = false,
)