package com.example.onemillonwinner.util.extension

import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.example.onemillonwinner.R
import com.example.onemillonwinner.util.BalloonFactory
import com.example.onemillonwinner.util.PublicVoteChart
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.skydoves.balloon.Balloon
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun String.htmlText(): String{
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
}


fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun Balloon.buildBalloon(answers: List<String>, correctAnswer: String){
    this.getContentView().findViewById<AAChartView>(R.id.aa_chart_view)
        .aa_drawChartWithChartModel(
            PublicVoteChart().getPublicVoteChart(
                answers,
                correctAnswer
            )
        )

    this.getContentView()
        .findViewById<TextView>(R.id.text_view_title_dialog_public_vote)
}

fun Balloon.closeBalloon(){
    this.getContentView().findViewById<Button>(R.id.button_okay_dialog)
        .setOnClickListener {
            this.dismiss()
        }
}