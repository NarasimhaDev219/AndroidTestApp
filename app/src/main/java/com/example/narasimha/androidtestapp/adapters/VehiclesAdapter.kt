package com.example.narasimha.androidtestapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.narasimha.androidtestapp.R
import com.example.narasimha.androidtestapp.model.VehicleType

class VehiclesAdapter(
    private val vehicleType: List<VehicleType>
) :
    RecyclerView.Adapter<VehiclesAdapter.VehiclesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiclesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false)
        return VehiclesViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehiclesViewHolder, position: Int) {
        val vehicle = vehicleType[position]
        holder.bind(vehicle)
    }

    override fun getItemCount(): Int {
        return vehicleType.size
    }

    inner class VehiclesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvVehicleName: TextView = itemView.findViewById(R.id.tvVehicleNameID)
        val tvIsPrimaryID: TextView = itemView.findViewById(R.id.tvIsPrimaryID)

        fun bind(vehicleType: VehicleType) {
            tvVehicleName.text = vehicleType.Name
            tvIsPrimaryID.text = vehicleType.IsPrimary.toString()
        }
    }
}
