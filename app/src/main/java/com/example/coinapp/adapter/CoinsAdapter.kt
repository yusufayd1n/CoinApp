package com.example.coinapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.R
import com.example.coinapp.databinding.ItemCoinBinding
import com.example.coinapp.model.Coins
import com.example.coinapp.util.formatCurrency
import com.example.coinapp.util.loadUrl
import com.example.coinapp.view.CoinsFragmentDirections

class CoinsAdapter(val coinArrayList: ArrayList<Coins.Data.Coin>) :
    RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder>() {

    class CoinsViewHolder(val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinBinding.inflate(inflater, parent, false)
        return CoinsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return coinArrayList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        holder.binding.apply {
            tvShortCoinName.text = coinArrayList[position].symbol
            tvCoinName.text = coinArrayList[position].name
            tvCoinPrice.text = formatCurrency(coinArrayList[position].price)
            ivCoin.loadUrl(coinArrayList[position].iconUrl)
            tvChange.text = "${coinArrayList[position].change ?: "0"}%"

            if (coinArrayList[position].change?.startsWith("-") == true) {
                tvChange.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            } else {
                tvChange.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
            }

            if (coinArrayList[position].isFavorite) {
                ivFavorite.visibility = View.VISIBLE
            } else {
                ivFavorite.visibility = View.GONE
            }

            holder.binding.root.setOnClickListener {
                val action =
                    CoinsFragmentDirections.actionCoinsFragmentToCoinDetailFragment(coinArrayList[position])
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    fun updateCoinsList(newCoinsList: List<Coins.Data.Coin>) {
        coinArrayList.clear()
        coinArrayList.addAll(newCoinsList)
        notifyDataSetChanged()
    }

}