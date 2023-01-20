package com.solodilov.rxtest.debounce

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.solodilov.rxtest.R
import com.solodilov.rxtest.databinding.FragmentDebounceBinding
import com.solodilov.rxtest.viewBinding
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class DebounceFragment : Fragment(R.layout.fragment_debounce) {
    private val binding by viewBinding(FragmentDebounceBinding::bind)

    private val disposables = CompositeDisposable()
    private val textInput = BehaviorSubject.create<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                textInput.onNext(p0.toString())
            }

        })

        disposables.add(textInput.toFlowable(BackpressureStrategy.LATEST)
            .debounce(3, TimeUnit.SECONDS)
            .subscribe { Log.d(TAG, it) }
        )
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }

    companion object {
        const val TAG = "Debounce"
    }
}