package com.example.onemillonwinner.util

import com.github.aachartmodel.aainfographics.aachartcreator.AAChartAnimationType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement

class PublicVoteChart {

    fun getPublicVoteChart(answers: List<String>, correctAnswer: String): AAChartModel {
        val listOfAASeriesElement =
            getArrayOFAASeriesElementIfSuccessChance(getRightAnswerIndex(answers, correctAnswer))
        return AAChartModel()
            .chartType(AAChartType.Column)
            .animationDuration(2000)
            .backgroundColor("#007CC5")
            .dataLabelsEnabled(true)
            .yAxisGridLineWidth(0)
            .gradientColorEnable(false)
            .xAxisGridLineWidth(0)
            .yAxisVisible(false)
            .animationType(AAChartAnimationType.EaseFromTo)
            .series(
                arrayOf(
                    listOfAASeriesElement[0],
                    listOfAASeriesElement[1],
                    listOfAASeriesElement[2],
                    listOfAASeriesElement[3],
                )
            )
    }

    private fun getArrayOFAASeriesElementIfSuccessChance(indexRightAnswer: Int): MutableList<AASeriesElement> {
        val gettingRandomSequence = gettingRandomPercentagesInSequenceFromTheSmallest()
        val seriesElement = mutableListOf<AASeriesElement>()
        for (i in 0..2) {
            seriesElement.add(
                AASeriesElement()
                    .name("")
                    .data(arrayOf(gettingRandomSequence[i]))
                    .allowPointSelect(false)
                    .colorByPoint(false)
            )
        }
        seriesElement.add(
            indexRightAnswer,
            AASeriesElement()
                .name("")
                .data(arrayOf(gettingRandomSequence[3]))
                .allowPointSelect(false)
                .colorByPoint(false)
        )
        return seriesElement
    }

    private fun gettingRandomPercentagesInSequenceFromTheSmallest(): List<Int> {
        var firstValue = (60..75).random()
        var secondValue = (0..(100 - firstValue) - 5).random()
        var thirdValue = (1..(100 - (firstValue + secondValue)) - 3).random()
        var fourthValue = (100 - (firstValue + secondValue + thirdValue))

        if (fourthValue < 0) {
            firstValue -= fourthValue;fourthValue = 0
        }

        if (secondValue < 0) {
            firstValue -= secondValue;secondValue = 0
        }

        if (thirdValue < 0) {
            firstValue -= thirdValue;thirdValue = 0
        }

        return listOf(fourthValue, thirdValue, secondValue, firstValue)
    }

    private fun getRightAnswerIndex(answers: List<String>, correctAnswer: String): Int {
        var correctAnswerIndex = 0
        answers.forEachIndexed { _correctAnswerIndex, answer ->
            if (answer == correctAnswer) correctAnswerIndex = _correctAnswerIndex
        }
        return correctAnswerIndex
    }

}


