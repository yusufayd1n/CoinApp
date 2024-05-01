package com.example.coinapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coinapp.databinding.FragmentCoinDetailBinding
import com.example.coinapp.model.Coin

class CoinDetailFragment : Fragment() {
    private lateinit var coin : Coin
    private lateinit var binding : FragmentCoinDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            coin = CoinDetailFragmentArgs.fromBundle(it).CoinData
        }

    }

}