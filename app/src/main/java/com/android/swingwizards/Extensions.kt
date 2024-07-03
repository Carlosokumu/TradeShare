package com.android.swingwizards

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.swingwizards.models.DataPoint
import com.tinder.scarlet.Message
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.math.RoundingMode
import java.text.DecimalFormat


internal fun mapCoinWithMarketDataUiItemsList(tradeshistory: List<DataPoint>): ImmutableList<DataPoint> {
    return tradeshistory.toImmutableList()
}


object MonetaryConverter {
    fun fromMonetary(monetary: Double): Double {
        return monetary / 100.0
    }

    fun toMonetary(amount: Double): Long {
        return (amount * 100).toLong()
    }
}


fun Message.getType(): ByteArray? {
    return when (this) {
        is Message.Text -> {
            null
        }

        is Message.Bytes -> {
            value
        }

    }
}


fun roundOffDecimal(number: Double?): Double? {
    if (number == null) return null
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number).toDouble()
}

fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = this.drawBehind {
//    Color.
    val transparentColor =  color.copy(alpha = 0.0f).toArgb()//android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
    val shadowColor = color.copy(alpha = alpha).toArgb()//android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
    }
}





