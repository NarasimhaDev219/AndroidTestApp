package com.example.narasimha.androidtestapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.narasimha.androidtestapp.R
import com.example.narasimha.androidtestapp.model.Manufacturer
import com.example.narasimha.androidtestapp.ui.ManufacturerDetailsActivity

class ManufacturerAdapter(
    private val manufacturers: List<Manufacturer>,
    private val context: Context,
) :
    RecyclerView.Adapter<ManufacturerAdapter.ManufacturerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManufacturerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_manufacturer, parent, false)
        return ManufacturerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ManufacturerViewHolder, position: Int) {
        val manufacturer = manufacturers[position]
        holder.itemView.rootView.setOnClickListener {

            val intent = Intent(context, ManufacturerDetailsActivity::class.java).apply {
                putExtra("MANUFACTURER", manufacturer)
            }
            context.startActivity(intent)
        }
        holder.bind(manufacturer)
    }

    override fun getItemCount(): Int {
        return manufacturers.size
    }

    inner class ManufacturerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mfrNameTextView: TextView = itemView.findViewById(R.id.tvMfrName)
        val countryTextView: TextView = itemView.findViewById(R.id.tvCountry)
        val vehicleTypesTextView: TextView = itemView.findViewById(R.id.tvVehicleTypes)

        fun bind(manufacturer: Manufacturer) {
            mfrNameTextView.text = manufacturer.Mfr_Name
            countryTextView.text = manufacturer.Country

            // Display Vehicle Types
            val vehicleTypes = manufacturer.VehicleTypes.joinToString(", ") { it.Name }
            vehicleTypesTextView.text = "Vehicle Types: $vehicleTypes"
            // Set click listener
        }
    }
}
