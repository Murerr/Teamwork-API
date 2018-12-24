package murer.rudy.api.task

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_home.*
import murer.rudy.api.CustomAdapter
import murer.rudy.api.Item
import murer.rudy.api.Project
import murer.rudy.api.R
import murer.rudy.api.authentication.LoginActivity
import murer.rudy.api.authentication.LoginActivity.Companion.BASEAUTH
import murer.rudy.api.project.ItemAdapterListener
import murer.rudy.api.task.TaskFragmentModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class TaskFragment : Fragment(), ItemAdapterListener {

    private lateinit var activityContext: Context
    private lateinit var model: TaskFragmentModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView =  inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = rootView.findViewById(R.id.progressbar) as ProgressBar
        recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        setRecyclerView(recyclerView)
        return  rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!this::activityContext.isInitialized && isAdded) {
            activityContext = activity?.applicationContext!!
        }
        if (!this::model.isInitialized) {
            model = TaskFragmentModel.newInstance(activityContext)
        }
        showProgress(true)
        doAsync {
            val listOfActivities = model.getTasks(getBasicAuth())
            uiThread {
                if (weakRef.get() != null) {
                    displayItems(listOfActivities)
                    showProgress(false)
                }
            }
        }

    }

    private fun setRecyclerView(recyclerView:RecyclerView) {
        recyclerView.adapter = CustomAdapter(emptyList(), this)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
    }

    private fun getBasicAuth(): String {
        return BASEAUTH
    }

    private fun displayItems(items: List<Item>) {
        recyclerView.adapter = CustomAdapter(items, this)
        (recyclerView.adapter as CustomAdapter).notifyDataSetChanged()
    }

    private fun showProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showItem(item: Item) {
    }

    interface ItemAdapterListener {
        fun showItem(item: Item)
    }
}



