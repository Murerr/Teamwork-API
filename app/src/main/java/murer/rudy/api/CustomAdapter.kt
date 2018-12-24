package murer.rudy.api

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.projects_list_row.view.*
import kotlinx.android.synthetic.main.recent_activities_list_row.view.*
import murer.rudy.api.project.ItemAdapterListener


/**
 * In charge of correctly displaying the Items
 */
class CustomAdapter(private val results: List<Item>, private val listener: ItemAdapterListener) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var filteredResult: MutableList<Item> = results.toMutableList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredResult[position], listener)
    }

    override fun getItemCount() = results.size

    fun removeItemAtIndex(index: Int) {
        filteredResult.removeAt(index)
    }

    override fun getItemViewType(position: Int): Int {
        return filteredResult[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemType = ItemType.values()[viewType]
        when (itemType) {
            ItemType.RecentActivity -> { return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recent_activities_list_row, parent, false)) }
            ItemType.Project -> { return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.projects_list_row, parent, false)) }
            ItemType.Task -> { return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tasks_list_row, parent, false)) }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item, listener: ItemAdapterListener) = with(itemView) {
            when (item.type) {
                ItemType.Project -> {

                    if (item.project!!.logo != "") {
                        glidewith(item_picture_project, item.project!!.logo)
                    } else {
                        glidewith(item_picture_project,R.drawable.ic_round_folder_open_24px)
                    }
                    if (!item.project!!.starred) {
                        glidewith(icon_star_project, R.drawable.ic_round_star_border_24px)
                    } else {
                        glidewith(icon_star_project, R.drawable.ic_baseline_star_24px)
                    }
                    item_name_project.text = item.project!!.name
                    item_description_project.text = item.project!!.description
                    setOnClickListener { listener.showItem(item) }
                }
                ItemType.RecentActivity -> {
                    if (item.recentActivity!!.activitytype == "new"){
                        glidewith(icon_recentActivities,R.drawable.ic_round_fiber_new_24px)
                    } else {
                        glidewith(icon_recentActivities,R.drawable.ic_round_edit_24px)
                    }

                    item_name_recentActivities.text = item.recentActivity!!.description
                    item_from_recentActivities.text = item.recentActivity!!.fromusername
                    val datetime = item.recentActivity!!.datetime
                    item_datetime_recentActivities.text = datetime.removeRange(10,datetime.length)
                }
                ItemType.Task ->{

                }
            }


        }

        private fun glidewith(imageView: ImageView, drawablePath: Any) {
            Glide.with(imageView)
                .load(drawablePath)
                .apply(RequestOptions().circleCrop().centerCrop().centerInside())
                .into(imageView)
        }
    }

}
