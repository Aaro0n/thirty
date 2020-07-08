package com.github.thirty

import android.content.Context
import android.util.TypedValue
import android.widget.TextView


/**
 * @author cl
 */

fun Number.spToPx(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP, this.toFloat(), context.resources.displayMetrics
).toInt()


fun TextView.setSpSize(size: Float) {
    this.textSize = size
}