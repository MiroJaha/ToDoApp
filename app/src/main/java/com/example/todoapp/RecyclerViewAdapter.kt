package com.example.todoapp

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemsViewBinding

class RecyclerViewAdapter (private val toDoList : ArrayList<Group>) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){

    private lateinit var myListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:OnItemClickListener ){

        myListener=listener
    }

    class ItemViewHolder (val binding: ItemsViewBinding, listener: OnItemClickListener): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
            ItemsViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ,myListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val group=toDoList[position]

        holder.binding.apply {
            textViewList.text = group.task
            textViewList.setTextColor(Color.BLACK)
            checkBoxList.isChecked = group.checkBox
            checkBoxList.setOnCheckedChangeListener{ _,checked ->
                when (checked){
                    true -> {
                        textViewList.setTextColor(Color.GRAY)
                        group.checkBox = true
                    }
                    else ->{
                        textViewList.setTextColor(Color.BLACK)
                        group.checkBox = false
                    }
                }
            }
        }
    }

    fun DeleteChecked (){
        toDoList.removeAll { list -> list.checkBox }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }
}