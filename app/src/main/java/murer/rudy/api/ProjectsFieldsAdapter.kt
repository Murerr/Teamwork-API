package murer.rudy.api


import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.projects_list_row.view.*

/**
 * In charge of correctly displaying the Items
 */
class ProjectsFieldsAdapter(private val results: List<ProjectsFields>, private val listener: ProjectsFieldsAdapterListener) :
    RecyclerView.Adapter<ProjectsFieldsAdapter.ViewHolder>() {

    private var filteredResult: MutableList<ProjectsFields> = results.toMutableList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredResult[position], listener)
    }

    override fun getItemCount() = results.size

    fun removeItemAtIndex(index: Int) {
        filteredResult.removeAt(index)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.projects_list_row, parent, false))


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ProjectsFields, listener: ProjectsFieldsAdapterListener) = with(itemView) {
            if(item.logo !=""){
                glidewith(item_picture,item.logo)
            } else {
                glidewith(item_picture,R.drawable.ic_round_folder_open_24px)
            }

            if (!item.starred) {
                glidewith(icon_star,R.drawable.ic_round_star_border_24px)
            } else {
                glidewith(icon_star,R.drawable.ic_baseline_star_24px)
            }

            item_name.text = item.name
            item_description.text = item.description

            listener.showProject(item)

        }
        private fun glidewith(imageView: ImageView,drawablePath:Any){
            Glide.with(imageView)
                .load(drawablePath)
                .apply(RequestOptions().circleCrop().centerCrop().centerInside())
                .into(imageView)
        }
    }

}
