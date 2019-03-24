package com.aayush.biowear.view

import android.view.View
import android.text.TextPaint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.aayush.biowear.R
import com.aayush.biowear.util.TEMP_FEVER
import com.aayush.biowear.util.TEMP_HYPOTHERMIA
import com.aayush.biowear.util.TEMP_MAX
import com.aayush.biowear.util.TEMP_MIN


class ThermometerView: View {
    private lateinit var progressPaint: Paint
    private lateinit var fillPaint: Paint
    private val barHeight = 10
    private val circleRadius = 25
    private var widthWithoutPadding = 0
    private var barWidth = 0f
    private var pxBarHeight = 0f
    private var pxBarLength = 0f
    private var pxCircleRadius = 0f
    private var progress = 0f
    private var textSize = 0f

    private val textPaint = TextPaint()
    private val linePaint = Paint()
    private val segment = RectF()
    private val path = Path()

    constructor(context: Context): super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val deviceDensity = resources.displayMetrics.density
        pxBarHeight = barHeight * deviceDensity
        pxCircleRadius = circleRadius * deviceDensity
        textSize = 13 * deviceDensity

        progressPaint = Paint()
        progressPaint.strokeWidth = 2f
        progressPaint.color = ContextCompat.getColor(context, R.color.colorAccent)

        fillPaint = Paint()
        fillPaint.style = Paint.Style.FILL
        fillPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
    }

    override fun onDraw(canvas: Canvas) {
        widthWithoutPadding = width - paddingRight
        barWidth = widthWithoutPadding.toFloat() - pxBarHeight - pxCircleRadius - Math.sqrt(
            Math.pow(
                pxCircleRadius.toDouble(),
                2.0
            ) - Math.pow(pxBarHeight.toDouble(), 2.0)
        ).toFloat()

        if (progress >= 0) {
            progressPaint.style = Paint.Style.FILL
            progressPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        }

        when {
            progress <= TEMP_HYPOTHERMIA -> fillPaint.color = ContextCompat.getColor(context, R.color.icyBlue)
            progress >= TEMP_FEVER -> fillPaint.color = ContextCompat.getColor(context, R.color.feverRed)
            else -> fillPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        }

        segment.set(
            paddingLeft.toFloat(),
            height / 2 - pxBarHeight,
            paddingLeft + 2 * pxBarHeight,
            height / 2 + pxBarHeight
        )
        path.addArc(segment, 90F, 180F)
        canvas.drawPath(path, fillPaint)

        linePaint.style = Paint.Style.FILL
        linePaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        linePaint.strokeWidth = 2f
        val endLineX =
            (widthWithoutPadding.toDouble() - pxCircleRadius.toDouble() - 2 * Math.sqrt(2.0) / 3 * pxCircleRadius).toFloat()
        val startLineX = paddingLeft + pxBarHeight
        val aboveLineY = height / 2 - pxBarHeight
        val belowLineY = height / 2 + pxBarHeight
        pxBarLength = endLineX - startLineX
        canvas.drawLine(startLineX, aboveLineY, endLineX, aboveLineY, linePaint)
        canvas.drawLine(startLineX, belowLineY, endLineX, belowLineY, linePaint)

        segment.set(
            widthWithoutPadding - 2 * pxCircleRadius,
            height / 2 - pxCircleRadius,
            widthWithoutPadding.toFloat(),
            height / 2 + pxCircleRadius
        )
        path.rewind()
        val angle = Math.toDegrees(Math.atan(1 / (2 * Math.sqrt(2.0)))).toFloat() // Why ? because I said that
        path.addArc(segment, 180 + angle, 360 - 2 * angle)
        progressPaint.style = Paint.Style.STROKE
        progressPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        canvas.drawPath(path, progressPaint)

        val totalLength = (width - paddingRight - paddingLeft).toFloat()
        val firstThreshold = (TEMP_MAX * pxBarHeight / totalLength)
        val secondThreshold = (TEMP_MAX * (pxBarHeight + pxBarLength) / totalLength)
        val thirdThreshold = (TEMP_MAX * (totalLength - pxCircleRadius) / totalLength)
        val lengthOfProgress = totalLength * progress / TEMP_MAX
        if (progress > firstThreshold) {
            if (progress <= secondThreshold) {
                canvas.drawRect(startLineX, aboveLineY, paddingLeft + lengthOfProgress, belowLineY, fillPaint)
            }
            else {
                 canvas.drawRect(
                    startLineX,
                    aboveLineY,
                    paddingLeft.toFloat() + pxBarHeight + pxBarLength,
                    belowLineY,
                    fillPaint
                )
                path.rewind()
                val angle2: Float
                if (progress <= thirdThreshold) {
                    angle2 =
                        Math.toDegrees(Math.acos(((totalLength - lengthOfProgress - pxCircleRadius) / pxCircleRadius).toDouble()))
                            .toFloat()
                    path.addArc(segment, 180 - angle2, 2 * angle2)
                }
                else {
                    angle2 =
                        Math.toDegrees(Math.acos(((lengthOfProgress - totalLength + pxCircleRadius) / pxCircleRadius).toDouble()))
                            .toFloat()
                    path.addArc(segment, angle2, 360 - 2 * angle2)
                }
                canvas.drawPath(path, fillPaint)
            }
        }

        textPaint.typeface = Typeface.create(
            Typeface.createFromAsset(context.assets, "fonts/roboto/Roboto-Regular.ttf"),
            Typeface.BOLD
        )
        textPaint.textSize = textSize
        textPaint.color = ContextCompat.getColor(context, R.color.colorSecondaryText)
        textPaint.textAlign = Paint.Align.CENTER
        val textHeight = textPaint.descent() - textPaint.ascent()
        val textOffset = textHeight / 2 - textPaint.descent()
        canvas.drawText("$progress ${0x00B0.toChar()}C", segment.centerX(), segment.centerY() + textOffset, textPaint)
    }

    fun setProgress(progress: Float) {
        when {
            progress > TEMP_MAX -> this.progress = 47f
            progress < TEMP_MIN -> this.progress = 13f
            else -> this.progress = progress
        }
        invalidate()
    }
}