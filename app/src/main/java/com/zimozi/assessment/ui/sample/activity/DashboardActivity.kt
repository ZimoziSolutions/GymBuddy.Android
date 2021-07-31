package com.zimozi.assessment.ui.sample.activity

import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.like.LikeButton
import com.zimozi.assessment.GlideApp
import com.zimozi.assessment.R
import com.zimozi.assessment.base.BaseActivity
import com.zimozi.assessment.base.BaseAdapter
import com.zimozi.assessment.base.BaseViewModel
import com.zimozi.assessment.data.model.DrawableItem
import com.zimozi.assessment.data.model.GymDataResponse
import com.zimozi.assessment.data.model.PopularClasessItem
import com.zimozi.assessment.util.extension.loge
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.row_available_classes.view.*
import kotlinx.android.synthetic.main.row_populer_classes.view.*
import kotlinx.android.synthetic.main.row_recommended_gym.view.*
import kotlinx.android.synthetic.main.row_recommended_gym.view.tv_gym_cost
import kotlinx.android.synthetic.main.row_recommended_gym.view.tv_gym_name
import java.io.*


class DashboardActivity : BaseActivity<ViewDataBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_dashboard


    val viewModel: DashboardActivityViewModel by ViewModelProvider()
    override val setViewModel: BaseViewModel?
        get() = viewModel

    override fun setDataBinding() = false

    var gymDataResponse = GymDataResponse()
    var popularClasess = ArrayList<PopularClasessItem>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {

        initObservable()


//        gymDataResponse = getRawGymData()


        if (getUserData() != null) {
            gymDataResponse = getUserData()!!
            initRecycler()
        } else {
            viewModel.getGymData(this)
        }
    }


    private fun initObservable() {
        viewModel.gymDataResponseLiveData.observe(this, Observer { gymData ->

            gymDataResponse = gymData

            var iconList = arrayListOf<DrawableItem>(
                DrawableItem(R.drawable.ic_box, false),
                DrawableItem(R.drawable.ic_aerobics, false),
                DrawableItem(R.drawable.ic_dances, false),
                DrawableItem(R.drawable.ic_gym, false),
                DrawableItem(R.drawable.ic_run, false),
                DrawableItem(R.drawable.ic_swimming, false),
                DrawableItem(R.drawable.ic_wrestling, false),
                DrawableItem(R.drawable.ic_yoga, false)
            )
            gymDataResponse.drawables = iconList

            saveUserData(gymDataResponse)

            initRecycler()

        })
    }


    private fun initRecycler() {


        var selectedGym = 0;

        var imagelist =
            listOf<Int>(R.drawable.gym_rebel, R.drawable.gym_non_stop, R.drawable.gym_rebel)


        rv_gyms.adapter = BaseAdapter(

            R.layout.row_recommended_gym,
            gymDataResponse.gyms,
            viewHolder = { holder, data ->
                holder.itemView.tv_gym_name.text = data?.title
                holder.itemView.tv_gym_cost.text = "$" + data?.price.toString()
                holder.itemView.tv_gym_rating.text = data?.rating.toString()
                holder.itemView.tv_gym_time.text = data?.date
                holder.itemView.btn_like_gym.isLiked = data?.favorite!!
                GlideApp.with(this).load(imagelist[holder.adapterPosition])
                    .into(holder.itemView.iv_gym_image)


            }
            , clickableView = R.id.card_view,
            clickListener = { view, position ->

                when (view) {
                    view.findViewById<CardView>(R.id.card_view) -> {
                        selectedGym = position
                        gymDataResponse.gyms?.get(position)?.popularClasess?.let {
                            popularClasess.clear()
                            popularClasess.addAll(it)
                            rv_classes.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            }, likeButton = R.id.btn_like_gym,
            button = { view, i ->

                if (view.findViewById<LikeButton>(R.id.btn_like_gym).isLiked) {
                    gymDataResponse.gyms?.get(i)?.favorite = true
                    saveUserData(gymDataResponse)
                    rv_gyms.adapter?.notifyDataSetChanged()

                } else {
                    gymDataResponse.gyms?.get(i)?.favorite = false
                    saveUserData(gymDataResponse)
                    rv_gyms.adapter?.notifyDataSetChanged()
                }

            })


        var classImageList = listOf<Int>(
            R.drawable.fitness_class,
            R.drawable.fitness_with_some_friends,
            R.drawable.crossfit_class,
            R.drawable.yoga_class
        )


        rv_classes.adapter = BaseAdapter(
            R.layout.row_available_classes,
            popularClasess,
            viewHolder = { myholder, mydata ->
                myholder.itemView.tv_class_title.text = mydata.title
                myholder.itemView.tv_class_time.text = mydata.time
                myholder.itemView.tv_class_address.text = mydata.location
                myholder.itemView.tv_gym_cost.text = "$" + mydata.price.toString()
                myholder.itemView.btn_like.isLiked = mydata.favorite!!

                GlideApp.with(this).load(classImageList[myholder.adapterPosition])
                    .into(myholder.itemView.iv_class_image)


            },
            clickableView = R.id.card_view,
            likeButton = R.id.btn_like,
            button = { view: View, i: Int ->

                if (view.findViewById<LikeButton>(R.id.btn_like).isLiked) {
                    popularClasess[i].favorite = true
                    gymDataResponse.gyms!![selectedGym]?.popularClasess!![i].favorite = true


                } else {
                    popularClasess[i].favorite = false
                    gymDataResponse.gyms!![selectedGym]?.popularClasess!![i].favorite = false

                }
                saveUserData(gymDataResponse)
                rv_classes.adapter?.notifyDataSetChanged()

            })




        rv_popular_classes.adapter = BaseAdapter(
            R.layout.row_populer_classes, gymDataResponse.drawables
            , viewHolder = { holder, data ->
                GlideApp.with(this).load(data.id).into(holder.itemView.iv_icons)
                if (data.favorite == true) {
                    holder.itemView.card_view_populer.setCardBackgroundColor(
                        resources.getColor(
                            R.color.frenchBlue,
                            null
                        )
                    )
                    holder.itemView.iv_icons.setColorFilter(
                        resources.getColor(R.color.white, null)
                    )
                } else {
                    holder.itemView.card_view_populer.setCardBackgroundColor(
                        resources.getColor(
                            R.color.white,
                            null
                        )
                    )
                    holder.itemView.iv_icons.setColorFilter(
                        resources.getColor(
                            R.color.frenchBlue,
                            null
                        )
                    )
                }


            }, clickableView = R.id.card_view_populer, clickListener = { view, i ->
                when (view) {
                    view.findViewById<CardView>(R.id.card_view_populer) -> {

                        gymDataResponse.drawables!![i].favorite =
                            !gymDataResponse.drawables!![i].favorite!!
                        rv_popular_classes.adapter?.notifyDataSetChanged()

                        saveUserData(gymDataResponse)

                    }

                }
            })


    }

    override fun detachView() {

    }
}