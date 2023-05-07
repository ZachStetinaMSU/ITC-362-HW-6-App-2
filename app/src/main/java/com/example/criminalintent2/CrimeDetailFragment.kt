package com.example.criminalintent2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.criminalintent2.databinding.FragmentCrimeDetailBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "CrimeDetailFragment"

class CrimeDetailFragment :Fragment(){

//private lateinit var crime:Crime
private val args: CrimeDetailFragmentArgs by navArgs()
    private val crimeDetailViewModel: CrimeDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.crimeid)
    }

    //private lateinit var binding: FragmentCrimeDetailBinding
private  var _binding :FragmentCrimeDetailBinding? = null

        private val binding
            get() = checkNotNull(_binding){
                "Cannot access binding because it is null. is the view visible"
            }



   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        crime = Crime(

            UUID.randomUUID(),
            title = "",
            date = Date(),
            isSolved = false

        )
        Log.d(TAG, "the crime ID is :${args.crimeid}")
    }*/


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //binding = FragmentCrimeDetailBinding.inflate(layoutInflater,container, false)
        _binding = FragmentCrimeDetailBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            crimeTitle.doOnTextChanged{text,_,_,_ ->
                crimeDetailViewModel.updateCrime { oldCrime ->
                    oldCrime.copy(title = text.toString())
                }
                //crime = crime.copy(title = text.toString())

            }

            crimeDate.apply {
                //text = crime.date.toString()
                isEnabled = false
            }

            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crimeDetailViewModel.updateCrime { oldCrime -> oldCrime.copy(isSolved = isChecked) }
            }

            viewLifecycleOwner.lifecycleScope.launch{
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    crimeDetailViewModel.crime.collect { crime ->
                        crime?.let { updateUI(it) }
                    }
                }
            }
        }
    }

    private fun updateUI(crime:Crime) {

        binding.apply {
            if (crimeTitle.text.toString() != crime.title) {
                crimeTitle.setText(crime.title)
            }
            crimeDate.text = crime.date.toString()
            crimeSolved.isChecked = crime.isSolved
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //binding = null
        _binding = null
    }

}