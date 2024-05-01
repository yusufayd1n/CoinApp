package com.example.coinapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.coinapp.CoinsFragmentDirections
import com.example.coinapp.databinding.FragmentCoinsBinding
import com.example.coinapp.model.Coin

class CoinsFragment : Fragment() {

    private lateinit var binding : FragmentCoinsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCoinsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val action =
                CoinsFragmentDirections.actionCoinsFragmentToCoinDetailFragment(Coin("6161"))
            findNavController().navigate(action)
        }
    }

}