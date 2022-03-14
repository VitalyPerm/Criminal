package com.example.criminal.crimelist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.criminal.R
import com.example.criminal.crime.CrimeDetailFragment
import com.example.criminal.databinding.FragmentCrimeListBinding
import com.example.criminal.db.Crime

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.title = "Puma best cat"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.crimeRecyclerView.adapter = adapter
        crimeListViewModel.crimesLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
                binding.tvEmpty.isVisible = it.isEmpty()
                binding.btnAdd.isVisible = it.isEmpty()
            }
        }
        binding.btnAdd.setOnClickListener { addCrime() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_crime_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                addCrime()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addCrime() {
        val crime = Crime()
        crimeListViewModel.addCrime(crime)
        findNavController().navigate(
            R.id.action_crimeListFragment_to_crimeFragment,
            Bundle().apply {
                putSerializable(CrimeDetailFragment.ARG_CRIME_ID, crime.id)
            })
    }
}