package com.example.coinapp.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.coinapp.R
import com.example.coinapp.databinding.FragmentCoinDetailBinding
import com.example.coinapp.model.Coins
import com.example.coinapp.model.DaoModel
import com.example.coinapp.util.formatCurrency
import com.example.coinapp.util.loadUrl
import com.example.coinapp.viewmodel.CoinDetailViewModel
import com.example.coinapp.viewmodel.CoinsViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class CoinDetailFragment : Fragment() {

    private lateinit var binding: FragmentCoinDetailBinding
    private lateinit var coinData: Coins.Data.Coin
    private lateinit var viewModel: CoinDetailViewModel
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
            coinData = CoinDetailFragmentArgs.fromBundle(it).coinData
        }
        viewModel = ViewModelProviders.of(this)[CoinDetailViewModel::class.java]
        initView()
        initListeners()
    }

    private fun initListeners() {
        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivFavorite.setOnClickListener {
            if (coinData.isFavorite) {
                coinData.isFavorite = false
                viewModel.removeCoinFromFavorites(DaoModel(coinData.uuid))
                Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT)
                    .show()
            } else {
                coinData.isFavorite = true
                viewModel.addCoinToFavorites(DaoModel(coinData.uuid))
                Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
            }
            setIsFavorite()
        }

    }

    private fun initView() {
        val formattedSparkline = coinData.sparkline.map { formatCurrency(it) }
        binding.apply {
            tvCoinShortName.text = coinData.symbol
            tvCoinName.text = coinData.name
            tvCoinPrice.text = formatCurrency(coinData.price)
            tvChange.text = coinData.change
            tvRank.text = getString(R.string.rank_text, coinData.rank.toString())
            tvCoinLowPriceValue.text = formattedSparkline.minOrNull()
            tvCoinHighPriceValue.text = formattedSparkline.maxOrNull()
            ivCoin.loadUrl(coinData.iconUrl)
            if (coinData.change.startsWith("-")) {
                tvChange.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            } else {
                tvChange.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
            }

        }
        setGraphicData()
        setIsFavorite()
    }

    private fun setGraphicData() {
        val sparklineData = coinData.sparkline

        val entries = ArrayList<Entry>()
        for ((index, value) in sparklineData.withIndex()) {
            entries.add(Entry(index.toFloat(), value.toFloat()))
        }

        val dataSet = LineDataSet(entries, "Price")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK

        val lineData = LineData(dataSet)
        binding.lineChart.data = lineData
        binding.lineChart.invalidate()
    }

    private fun setIsFavorite() {
        if (coinData.isFavorite) {
            binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.ivFavorite.setImageResource(R.drawable.ic_not_favorite)
        }
    }


}