package com.example.criminal

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.criminal.databinding.FragmentCrimeListBinding

class CrimeListFragment : Fragment(R.layout.fragment_crime_list) {

    private val binding by viewBinding(FragmentCrimeListBinding::bind)

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }

    private var adapter = CrimeListAdapter{
        Toast.makeText(context, "$it pressed!", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.crimeRecyclerView.adapter = adapter
        crimeListViewModel.crimesLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.updateUi(it)
            }
        }
    }


    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}