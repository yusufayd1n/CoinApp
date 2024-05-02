package com.example.coinapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
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

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        holder.binding.apply {
            tvShortCoinName.text = coinArrayList[position].symbol
            tvCoinName.text = coinArrayList[position].name
            tvCoinPrice.text = formatCurrency(coinArrayList[position].price)
            tvChange.text = coinArrayList[position].change + "%"
            ivCoin.loadUrl(coinArrayList[position].iconUrl)

            if (coinArrayList[position].change.startsWith("-")) {
                tvChange.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            } else {
                tvChange.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.green
                    )
                )
            }

            if (coinArrayList[position].isFavorite) {
                clCoin.background = R.drawable.bg_favorite_constraint_layout.toDrawable()
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