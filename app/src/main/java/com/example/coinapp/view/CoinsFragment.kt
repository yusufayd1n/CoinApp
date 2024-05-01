package com.example.coinapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinapp.adapter.CoinsAdapter
import com.example.coinapp.databinding.FragmentCoinsBinding
import com.example.coinapp.viewmodel.CoinsViewModel

class CoinsFragment : Fragment() {

    private lateinit var binding: FragmentCoinsBinding
    private lateinit var viewModel: CoinsViewModel
    private val coinsAdapter = CoinsAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCoinsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this)[CoinsViewModel::class.java]
        viewModel.getCoinsFromAPI()
        binding.rvCoins.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coinsAdapter
        }

        observeCoins()

    }

    private fun observeCoins() {
        viewModel.coins.observe(viewLifecycleOwner, Observer { coins ->
            coins?.let {
                binding.rvCoins.visibility = View.VISIBLE
                coinsAdapter.updateCoinsList(coins)
            } ?: run {

            }
        })

        viewModel.coinError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    binding.tvError.visibility = View.VISIBLE
                } else {
                    binding.tvError.visibility = View.GONE
                }
            }
        })

        viewModel.coinsLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if(loading){
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvCoins.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }else{
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

}