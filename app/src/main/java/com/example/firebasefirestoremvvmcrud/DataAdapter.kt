package com.example.firebasefirestoremvvmcrud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(
    private var data: List<Data>,
    private var itemClickListener: ItemClickListener
) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onEditItemClick(data: Data)
        fun onDeleteItem(data: Data)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studId: TextView = itemView.findViewById(R.id.idTxt)
        val name: TextView = itemView.findViewById(R.id.nameTxt)
        val email: TextView = itemView.findViewById(R.id.emailEtxt)
        val subject: TextView = itemView.findViewById(R.id.subjectEtxt)
        val birthDate: TextView = itemView.findViewById(R.id.birthDateTxt)
        val edit: ImageButton = itemView.findViewById(R.id.editBtn)
        val delete: ImageButton = itemView.findViewById(R.id.deleteBtn)
    }

    // Update data and notify RecyclerView of change
    fun updateData(newData: List<Data>) {
        this.data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.studId.text = item.studid
        holder.name.text = item.name
        holder.email.text = item.email
        holder.subject.text = item.subject
        holder.birthDate.text = item.birthdate

        holder.edit.setOnClickListener {
            itemClickListener.onEditItemClick(item)
        }

        holder.delete.setOnClickListener {
            itemClickListener.onDeleteItem(item)
        }
    }
}
