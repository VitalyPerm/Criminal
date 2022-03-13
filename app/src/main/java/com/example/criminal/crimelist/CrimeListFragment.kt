package com.example.criminal.crimelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.criminal.R
import com.example.criminal.crime.CrimeDetailFragment
import com.example.criminal.databinding.FragmentCrimeListBinding

class CrimeListFragment : Fragment(R.layout.fragment_crime_list) {


    private val binding by viewBinding(FragmentCrimeListBinding::bind)

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    private var adapter = CrimeListAdapter {
        findNavController().navigate(
            R.id.action_crimeListFragment_to_crimeFragment,
            Bundle().apply {
                putSerializable(CrimeDetailFragment.ARG_CRIME_ID, it)
            })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.crimeRecyclerView.adapter = adapter
        crimeListViewModel.crimesLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
}