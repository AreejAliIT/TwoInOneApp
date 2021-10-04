package com.example.twoinoneapp


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Message(private val context: Context, private val messages:ArrayList<String>) :
    RecyclerView.Adapter<Message.ViewHolder>(){

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)
    // for binding
    private lateinit var guessText : TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row_rv,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var msg = messages[position]
        holder.itemView.apply {
            // for binding
            guessText = findViewById(R.id.guessText)
            guessText.text = msg
        }
    }
    override fun getItemCount() = messages.size
}