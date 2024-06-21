package com.github.slznvk.todoapp.presentation.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.slznvk.todoapp.R
import com.github.slznvk.todoapp.databinding.CardTodoItemBinding
import com.github.slznvk.todoapp.domain.dto.Importance
import com.github.slznvk.todoapp.domain.dto.TodoItem

interface OnInteractionListener {
    fun onInfoButtonClick(item: TodoItem)
    fun onDoneButtonClick(item: TodoItem)
}

class ToDoAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<TodoItem, ItemViewHolder>(ItemDiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = CardTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view, onInteractionListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val todoItem = getItem(position)
        holder.bind(todoItem)
    }
}

class ItemViewHolder(
    private val binding: CardTodoItemBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TodoItem) {
        with(binding) {
            content.text = item.content

            if (item.deadline.isNullOrBlank()) {
                deadline.visibility = View.GONE
            } else {
                deadline.text = item.deadline
                deadline.visibility = View.VISIBLE
            }

            doneButton.isChecked = item.isDone
            if (item.isDone) {
                content.setTextColor(Color.GRAY)
                content.paintFlags = content.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                content.setTextColor(Color.BLACK)
                content.paintFlags = content.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            infoButton.setOnClickListener { onInteractionListener.onInfoButtonClick(item) }

            doneButton.setOnCheckedChangeListener { _, isChecked ->
                item.isDone = isChecked
                onInteractionListener.onDoneButtonClick(item)
            }


            when (item.importance) {
                Importance.LOW -> importanceIcon.apply {
                    icon = ContextCompat.getDrawable(context, R.drawable.ic_low_importance)
                    visibility = View.VISIBLE
                }

                Importance.REGULAR -> importanceIcon.visibility = View.GONE

                Importance.URGENT -> importanceIcon.apply {
                    icon = ContextCompat.getDrawable(context, R.drawable.ic_urgent_importance)
                    visibility = View.VISIBLE
                }
            }
        }
    }
}

object ItemDiffCallBack : DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem) = oldItem == newItem
}