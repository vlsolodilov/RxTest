package com.solodilov.rxtest.timer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.solodilov.rxtest.R
import com.solodilov.rxtest.databinding.FragmentTimerBinding
import com.solodilov.rxtest.viewBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class TimerFragment : Fragment(R.layout.fragment_timer) {
    private val binding by viewBinding(FragmentTimerBinding::bind)

    var d: Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.timerButton.setOnClickListener {
            if (d == null) {
                binding.timerButton.text = "Stop"
                d = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { value -> binding.timerText.text = value.toString() },
                        { error -> Log.d(TAG, error.message.toString()) }
                    )
            } else {
                binding.timerButton.text = "Start"
                binding.timerText.text = "0"
                d?.dispose()
                d = null
            }
        }
    }

    override fun onDestroyView() {
        d?.dispose()
        super.onDestroyView()
    }

    companion object {
        const val TAG = "Timer"
    }
}