package com.herald.mazaadyapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.herald.mazaadyapp.R
import com.herald.mazaadyapp.common.Constants
import com.herald.mazaadyapp.databinding.ResultsFragmentBinding
import com.herald.mazaadyapp.presentation.categories.ResultsAdapter

class ResultsFragment :Fragment(R.layout.results_fragment){

    private val binding: ResultsFragmentBinding by lazy { ResultsFragmentBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.resultsRecycler.adapter = ResultsAdapter(Constants.arrAllItems)

        return binding.root
    }
}