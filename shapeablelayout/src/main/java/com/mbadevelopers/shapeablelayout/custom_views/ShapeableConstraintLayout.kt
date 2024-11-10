package com.mbadevelopers.shapeablelayout.custom_views

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mbadevelopers.shapeablelayout.R

/**
 * Created by BalaVignesh on 10-11-2024.
 */


class ShapeableConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var strokeWidth: Int = 0
    private var strokeColor: Int = ContextCompat.getColor(context, android.R.color.black)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomConstraintLayout,
            0, 0
        ).apply {
            try {
                val shape = getInt(R.styleable.CustomConstraintLayout_shapeDrawable, GradientDrawable.RECTANGLE)
                val radius = getDimension(R.styleable.CustomConstraintLayout_cornerRadius, 0f)
                val solidColor = getColor(R.styleable.CustomConstraintLayout_solidColor, ContextCompat.getColor(context, android.R.color.transparent))
                strokeColor = getColor(R.styleable.CustomConstraintLayout_strokeColor, ContextCompat.getColor(context, android.R.color.black))
                strokeWidth = getDimensionPixelSize(R.styleable.CustomConstraintLayout_strokeWidth, 0)
                val elevation = getDimension(R.styleable.CustomConstraintLayout_elevation, 0f)

                background = createCustomDrawable(
                    shapeDrawable = shape,
                    radius = radius,
                    solidColor = solidColor,
                    strokeColor = strokeColor,
                    strokeWidth = strokeWidth
                )

                // Set elevation
                setElevation(elevation)
                // Ensure that the view casts a shadow properly with the elevation
                outlineProvider = ViewOutlineProvider.BACKGROUND
                clipToOutline = true

            } finally {
                recycle()
            }
        }
    }

    private fun createCustomDrawable(
        shapeDrawable: Int = GradientDrawable.RECTANGLE,
        radius: Float,
        solidColor: Int,
        strokeColor: Int,
        strokeWidth: Int
    ): GradientDrawable {
        return GradientDrawable().apply {
            shape = shapeDrawable
            cornerRadius = radius
            setColor(solidColor)
            setStroke(strokeWidth, strokeColor)
        }
    }

    fun setShapeDrawable(shape: Int) {
        val drawable = background as? GradientDrawable ?: GradientDrawable()
        drawable.shape = shape
        background = drawable
    }

    fun setCornerRadius(radius: Float) {
        val drawable = background as? GradientDrawable ?: GradientDrawable()
        drawable.cornerRadius = radius
        background = drawable
    }

    fun setSolidColor(color: Int) {
        val drawable = background as? GradientDrawable ?: GradientDrawable()
        drawable.setColor(color)
        background = drawable
    }

    fun setStrokeColor(color: Int) {
        val drawable = background as? GradientDrawable ?: GradientDrawable()
        drawable.setStroke(strokeWidth, color)
        background = drawable
        strokeColor = color
    }

    fun setStrokeWidth(width: Int) {
        val drawable = background as? GradientDrawable ?: GradientDrawable()
        drawable.setStroke(width, strokeColor)
        background = drawable
        strokeWidth = width
    }

    fun setLayoutElevation(elevation: Float) {
        this.elevation = elevation
        outlineProvider = ViewOutlineProvider.BACKGROUND
        clipToOutline = true
    }
}