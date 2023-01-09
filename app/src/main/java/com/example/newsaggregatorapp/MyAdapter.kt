package com.example.newsaggregatorapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter (private val imageModelArrayList: MutableList<MyModel>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    lateinit var myContext:Context
    /*
     * Inflate our views using the layout defined in row_layout.xml
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.row_layout, parent, false)

        myContext = v.context
        return ViewHolder(v)
    }

    /*
     * Bind the data to the child views of the ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]


        Picasso.get().load(info.getArticleImage()).into(holder.imgView)
        //holder.imgView.setImageResource(info.getImages())
        holder.titleView.text = info.getArticleTitle()
        holder.summaryView.text = info.getArticleSummary()
        holder.contentView.text = info.getArticleContent()
        holder.sourceNameView.text = info.getArticleSourceName()
        holder.publishedAtView.text = info.getArticlePublishedAt()
        //myContext = holder.sourceNameView.context
    }

    /*
     * Get the maximum size of the
     */
    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    /*
     * The parent class that handles layout inflation and child view use
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var imgView = itemView.findViewById<View>(R.id.article_image) as ImageView
        var titleView = itemView.findViewById<View>(R.id.article_title) as TextView
        var summaryView = itemView.findViewById<View>(R.id.summary_text) as TextView

        var contentView = itemView.findViewById<View>(R.id.content_text) as TextView
        var sourceNameView = itemView.findViewById<View>(R.id.sourceName) as TextView
        var publishedAtView = itemView.findViewById<View>(R.id.publishedAt) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            //Log.d("=======||||||||========", imageModelArrayList[getAdapterPosition()].getArticleURL())
            val www = Uri.parse(imageModelArrayList[adapterPosition].getArticleURL())
            val webIntent = Intent(Intent.ACTION_VIEW, www)
            myContext.startActivity(webIntent)
        }
    }
}