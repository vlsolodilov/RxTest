package com.solodilov.rxtest.subject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.solodilov.rxtest.R
import com.solodilov.rxtest.databinding.FragmentSubjectBinding
import com.solodilov.rxtest.viewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SubjectFragment : Fragment(R.layout.fragment_subject) {
    private val binding by viewBinding(FragmentSubjectBinding::bind)

    private var listAdapter = ListAdapter()
    private val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvList.adapter = listAdapter
        disposables.add(listAdapter.clickEvent.subscribe {
            Toast.makeText(requireActivity(), "Clicked on $it", Toast.LENGTH_LONG).show()
        })
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}