package com.example.criminalintent2



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.criminalintent2.databinding.FragmentCrimeListBinding
import kotlinx.coroutines.launch

//const val TAG = "CrimeListFragment"

abstract class CrimeListFragment :Fragment(){

    private var _binding: FragmentCrimeListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. is the view visible?"
        }

    private val crimeListViewModel :CrimeListViewModel by viewModels()

    //override fun onCreate(savedInstanceState: Bundle?) {
        //super.onCreate(savedInstanceState)
        //Log.d(TAG,"total Crimes ${crimeListViewModel.crimes.size}")


    //}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeListBinding.inflate(inflater,container,false)

        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)

       /* val crimes = crimeListViewModel.crimes
        val adapter = CrimeListAdapter(crimes)
        binding.crimeRecyclerView.adapter = adapter*/

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                //val crimes = crimeListViewModel.loadCrimes()
                crimeListViewModel.crimes.collect(){ crimes ->
                    binding.crimeRecyclerView.adapter=
                     CrimeListAdapter(crimes){ crimeId->
                         findNavController().navigate(
                             //R.id.show_crime_detail
                         CrimeListFragmentDirections.showCrimeDetail(crimeId)
                         )


                     }
            }
        }

        }
    }}

