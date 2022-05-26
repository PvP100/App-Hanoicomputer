package com.example.utt.hnccomputer.extension

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*

import androidx.core.content.ContextCompat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Collection<*>?.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}

@ColorInt
fun Context.color(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

fun Context.drawable(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

fun Context.string(@StringRes res: Int): String {
    return getString(res)
}

fun Context.inflate(@LayoutRes res: Int, parent: ViewGroup, attachView: Boolean = true) : View {
    return LayoutInflater.from(this).inflate(res, parent, attachView)
}

fun Context.statusBarHeight(restrictToLollipop: Boolean = true): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0 && (!restrictToLollipop || (restrictToLollipop && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP))) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun <T> Context.openActivityWithoutAddtoBackstack(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun String.toUpperCaseString() : String{
    return this.toUpperCase()
}

fun <T> Context.openActivityForResult(it: Class<T>, extras: Bundle.() -> Unit = {}): Intent {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))

    return intent
}

fun Long.convertToVnd(): String {
    return NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(this)
}

fun Long.convertToDate(): String {
    val dateFormat = SimpleDateFormat("HH:mm:ss, dd/MM/yyyy")
    val date = Date(this)

    return dateFormat.format(date)
}


