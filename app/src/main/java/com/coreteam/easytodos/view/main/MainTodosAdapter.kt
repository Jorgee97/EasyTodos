package com.coreteam.easytodos.view.main

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coreteam.easytodos.R
import com.coreteam.easytodos.model.Todo
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

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(todo: Todo, listener: (Todo, View) -> Unit) =  with(itemView){
            todoItem.text = todo.description
            todoItem.isChecked = todo.completed
            todoItem.setOnClickListener { listener(todo, itemView) }
        }
    }
}