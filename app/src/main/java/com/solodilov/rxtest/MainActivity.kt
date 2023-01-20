package com.solodilov.rxtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testObservable()
        test1Subject()
        test2Subject()

    }

    private fun testObservable() {
        val d = Observable.timer(10, TimeUnit.MILLISECONDS, Schedulers.newThread())
            .subscribeOn(Schedulers.io())
            .map {
                Log.d("HAHAHA", "mapThread = ${Thread.currentThread().name}")
            }
            .doOnSubscribe {
                Log.d("HAHAHA", "onSubscribeThread = ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.single())
            .flatMap {
                Log.d("HAHAHA", "flatMapThread = ${Thread.currentThread().name}")
                Observable.just(it)
                    .subscribeOn(Schedulers.io())
            }
            .subscribe {
                Log.d("HAHAHA", "subscribeThread = ${Thread.currentThread().name}")
            }
        d.dispose()
    }

    private fun test1Subject() {
        Log.d("Subject", "test1Subject, cold Observable")
        val subject = PublishSubject.create<String> { e ->
            e.onNext("1")
            e.onNext("2")
            e.onNext("3")
        }
        subject.subscribe { Log.d("Subject", it) }
    }

    private fun test2Subject() {
        Log.d("Subject", "test2Subject, hot Subject")
        val subject = PublishSubject.create<String>()
        subject.subscribe { Log.d("Subject", it) }
        subject.onNext("1")
        subject.onNext("2")
        subject.onNext("3")
    }

}