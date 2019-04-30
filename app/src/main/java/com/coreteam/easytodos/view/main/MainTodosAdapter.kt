package com.coreteam.easytodos.view.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coreteam.easytodos.R
import com.coreteam.easytodos.model.Todo
import com.coreteam.easytodos.view.update.UpdateTodoActivity
import kotlinx.android.synthetic.main.todo_item.view.*

class MainTodosAdapter(private val todos: List<Todo>,
                       private val onTodoClicked: (Todo, View) -> Unit) : RecyclerView.Adapter<MainTodosAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todos[position]
        holder.bind(todo, onTodoClicked)
    }

    fun removeItem(position: Int) {
        todos.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItemAtPosition(position: Int) : Todo{
        return todos[position]
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(todo: Todo, listener: (Todo, View) -> Unit) =  with(itemView){
            todoItem.text = todo.description
            todoItem.isChecked = todo.completed
            todoItem.setOnClickListener { listener(todo, itemView) }
            todoItem.setOnLongClickListener {
                val updateDescriptionIntent = Intent(itemView.context, UpdateTodoActivity::class.java)
                updateDescriptionIntent.putExtra("TODO_ID", todo.todoId)
                updateDescriptionIntent.putExtra("ACTUAL_DESCRIPTION", todo.description)
                itemView.context.startActivity(updateDescriptionIntent)
                return@setOnLongClickListener true
            }
        }
    }
}