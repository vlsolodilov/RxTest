package com.solodilov.rxtest.request

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.solodilov.rxtest.R
import com.solodilov.rxtest.databinding.FragmentRequestBinding
import com.solodilov.rxtest.viewBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RequestFragment : Fragment(R.layout.fragment_request) {

    private val binding by viewBinding(FragmentRequestBinding::bind)

    var d: Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val catFact = CatFactApiClient.apiClient.getRandomFact()

        binding.loadCatFact.setOnClickListener {
            d = catFact
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> binding.catFactText.text = result.fact },
                    { error -> Log.d(TAG, error.message.toString()) }
                )
        }
    }

    override fun onDestroyView() {
        d?.dispose()
        super.onDestroyView()
    }

    companion object {
        const val TAG = "Request"
    }
}