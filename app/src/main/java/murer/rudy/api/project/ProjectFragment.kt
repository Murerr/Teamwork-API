package murer.rudy.api.project

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_projects.*
import murer.rudy.api.CustomAdapter
import murer.rudy.api.Item
import murer.rudy.api.R
import murer.rudy.api.authentication.LoginActivity.Companion.BASEAUTH

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ProjectFragment : Fragment(), ItemAdapterListener {

    private lateinit var activityContext: Context
    private lateinit var model: ProjectFragmentModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView =  inflater.inflate(R.layout.fragment_projects, container, false)
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
            model = ProjectFragmentModel.newInstance(activityContext)
        }

        showProgress(true)
        doAsync {
            val projectsList = model.getProjectInfos(getBasicAuth())
            Log.d("ProjectList",projectsList.toString())
            uiThread {
                if (weakRef.get() != null){
                    displayItems(projectsList)
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

}
interface ItemAdapterListener {
    fun showItem(item: Item)

}


