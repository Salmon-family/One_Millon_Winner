package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.TriviaResponse
import com.example.onemillonwinner.util.NetworkConstants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class Repository {

    fun getEasyQuestion(numberOfQuestions: Int): Single<TriviaResponse> {
        return Network.triviaService.getQuestions(
            questionNumbers = numberOfQuestions,
            QuestionDifficulty = NetworkConstants.DIFFICULTY_LEVEL_EASY
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}