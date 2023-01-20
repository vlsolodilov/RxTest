package com.solodilov.rxtest.card

import android.util.Log
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class CardsFragment : Fragment() {

   init {
       getDiscountCards()
   }

    private fun getDiscountCards() {
        Log.d(TAG, "Zip")
        val firstDiscountCards: Single<List<String>> =
            Single.just(listOf("Card 1", "Card 2", "Card 3"))
        val secondDiscountCards: Single<List<String>> = Single.just(listOf("Card 4", "Card 5"))
        val errorDiscountCards: Single<List<String>> = Single.error(Exception("Error!"))
        Single
            .zip(firstDiscountCards, secondDiscountCards) { first, second -> first + second }
            .subscribe(
                { results -> Log.d(TAG, results.joinToString()) },
                { error -> Log.d(TAG, error.message.toString()) }
            )
        Log.d(TAG, "mergeDelayError")
        Single
            .mergeDelayError(firstDiscountCards, secondDiscountCards)
            .onErrorComplete()
            .flatMap { list -> Flowable.fromIterable(list) }
            .toList()
            .subscribe(
                { results -> Log.d(TAG, results.joinToString()) },
                { error -> Log.d(TAG, error.message.toString()) }
            )
    }

    companion object {
        const val TAG = "Cards"
    }
}