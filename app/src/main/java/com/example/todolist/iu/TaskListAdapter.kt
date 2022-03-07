package com.example.todolist.iu

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.Task
import com.example.todolist.databinding.ItemTaskBinding


class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallBack()){

    var listenerEdit: (Task) -> Unit ={}
    var listenerDelete: (Task) -> Unit ={}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding:ItemTaskBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: Task) {
            binding.tvTitleList.text =item.title
            binding.tvDateHour.text = "${item.date} -- ${item.hour}"
            binding.tvDescriptions.text = item.description
            binding.ivMore.setOnClickListener{
                showPopUp(item)
            }

        }

        private fun showPopUp(item: Task) {
            val ivMore = binding.ivMore
            val popupmenu = PopupMenu(ivMore.context,ivMore)
            popupmenu.menuInflater.inflate(R.menu.iv_menu, popupmenu.menu)
            popupmenu.setOnMenuItemClickListener {

                when (it.itemId){

                    R.id.action_edit -> listenerEdit(item)

                    R.id.action_delete -> listenerDelete(item)
                }


                return@setOnMenuItemClickListener true
            }
            popupmenu.show()
        }


    }
    class DiffCallBack: DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
    }


}