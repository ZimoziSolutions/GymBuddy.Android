package com.zimozi.assessment.base


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import java.util.concurrent.TimeUnit


open class BaseAdapter<T>() : RecyclerView.Adapter<BaseAdapter<T>.ViewHolder>() {
    private var layoutId: Int = 0
    private var list: List<T>? = null
    private var viewHolder: ((RecyclerView.ViewHolder, T) -> Unit)? = null
    private val clickableViews = ArrayList<Int>()
    private var clickListener: ((View, Int) -> Unit)? = null
    private var likeButton: Int? = null
    private var button: ((View, Int) -> Unit)? = null

    constructor(
        layoutId: Int,
        list: List<T>?,
        viewHolder: ((RecyclerView.ViewHolder, T) -> Unit)
    ) : this(
        layoutId,
        list,
        viewHolder,
        listOf(),
        null
    )

    constructor(
        layoutId: Int,
        list: List<T>?,
        viewHolder: ((RecyclerView.ViewHolder, T) -> Unit),
        clickableViews: List<Int>,
        clickListener: ((View, Int) -> Unit)?,
        likeButton: Int? = null,
        button: ((View, Int) -> Unit)? = null

    ) : this() {
        this.layoutId = layoutId
        this.list = list
        this.viewHolder = viewHolder
        this.clickableViews.addAll(clickableViews)
        this.clickListener = clickListener
        this.button = button
        this.likeButton = likeButton

    }

    constructor(
        layoutId: Int,
        list: List<T>?,
        viewHolder: ((RecyclerView.ViewHolder, T) -> Unit),
        clickableView: Int,
        clickListener: ((View, Int) -> Unit)? = null,
        likeButton: Int? = null,
        button: ((View, Int) -> Unit)? = null

    ) : this(layoutId, list, viewHolder, listOf(clickableView), clickListener, likeButton, button)

    override fun getItemCount() = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    fun notifySearchData(searchList: List<T>) {
        list = searchList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewHolder?.apply { this(holder, list!![position])
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {

            for (clickableView in clickableViews)
                view.findViewById<View>(clickableView).setOnClickListener { v ->
                    clickListener?.invoke(
                        v,
                        adapterPosition
                    )
                }


            likeButton?.let {
                view.findViewById<LikeButton>(it).setOnLikeListener(object : OnLikeListener {
                    override fun liked(likeButton: LikeButton?) {
                        button?.invoke(
                            likeButton!!, adapterPosition
                        )
                    }

                    override fun unLiked(likeButton: LikeButton?) {
                        button?.invoke(
                            likeButton!!, adapterPosition
                        )
                    }

                })
            }


        }

    }

}