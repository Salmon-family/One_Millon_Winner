package com.example.onemillonwinner.util

import android.content.Context
import android.text.Layout
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.onemillonwinner.R
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.android.material.internal.ViewUtils.getContentView
import com.skydoves.balloon.*
import com.skydoves.balloon.overlay.BalloonOverlayAnimation
import com.skydoves.balloon.overlay.BalloonOverlayCircle

class BalloonFactory : Balloon.Factory() {
    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return createBalloon(context) {
            setLayout(R.layout.balloon_audience_vote)
            setIsVisibleOverlay(true)
            setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE)
            setOverlayColorResource(R.color.transparent_blue_700)
            setArrowOrientation(ArrowOrientation.START)
            setOverlayShape(BalloonOverlayCircle(radius = 0f))
            setHeight(BalloonSizeSpec.WRAP)
            setWidth(BalloonSizeSpec.WRAP)
            setWidthRatio(1f)
            setCornerRadius(10f)
            setBalloonAnimation(BalloonAnimation.CIRCULAR)
            setLifecycleOwner(lifecycle)
        }
    }
}
