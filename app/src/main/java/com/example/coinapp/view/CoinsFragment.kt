package com.example.coinapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.R
import com.example.coinapp.adapter.CoinsAdapter
import com.example.coinapp.databinding.FragmentCoinsBinding
import com.example.coinapp.viewmodel.CoinsViewModel

class CoinsFragment : Fragment() {

    private lateinit var binding: FragmentCoinsBinding
    private lateinit var viewModel: CoinsViewModel
    private val coinsAdapter = CoinsAdapter(arrayListOf())
    var selectedItem = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCoinsBinding.inflate(layoutInflater)
        viewModel = ViewModelProviders.of(this)[CoinsViewModel::class.java]
        viewModel.getCoinsFromAPI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllFavoritesCoins()
        setAdapter()

        observeCoins()
        observeRecyclerview()
        setFilterSpinner()
        initListeners()
    }

    private fun initListeners() {
        binding.btnFilter.setOnClickListener {
            if (selectedItem != binding.spFilter.selectedItem.toString()) {
                viewModel.filteredCoins.value = emptyList()
                viewModel.filteredOffset = 0
            }
            selectedItem = binding.spFilter.selectedItem.toString()
            viewModel.getCoinsFromAPIByOrder(selectedItem)

        }

        binding.btnClearFilter.setOnClickListener {
            viewModel.getCoinsFromAPI()
        }
    }

    private fun setFilterSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filter_items,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spFilter.adapter = adapter
        }
    }

    private fun setAdapter() {
        binding.rvCoins.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coinsAdapter
        }
    }

    private fun observeRecyclerview() {
        binding.rvCoins.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    if (viewModel.coins.value.isNullOrEmpty()) {
                        viewModel.getCoinsFromAPIByOrder(binding.spFilter.selectedItem.toString())
                    } else if (viewModel.filteredCoins.value.isNullOrEmpty()) {
                        viewModel.getCoinsFromAPI()
                    }
                }
            }
        })
    }

    private fun observeCoins() {
        viewModel.coins.observe(viewLifecycleOwner) { coins ->
            if (!coins.isNullOrEmpty()) {
                binding.rvCoins.visibility = View.VISIBLE
                coinsAdapter.updateCoinsList(coins)
            }
        }

        viewModel.filteredCoins.observe(viewLifecycleOwner) { coins ->
            if (!coins.isNullOrEmpty()) {
                binding.rvCoins.visibility = View.VISIBLE
                coinsAdapter.updateCoinsList(coins)
            }
        }

        viewModel.coinError.observe(viewLifecycleOwner) { error ->
            error?.let {
                if (error) {
                    binding.tvError.visibility = View.VISIBLE
                } else {
                    binding.tvError.visibility = View.GONE
                }
            }
        }

        viewModel.coinsLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                if (loading) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvCoins.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

}