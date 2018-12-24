package murer.rudy.api.project

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!this::activityContext.isInitialized && isAdded) {
            activityContext = activity?.applicationContext!!
        }
        if (!this::model.isInitialized) {
            model = ProjectFragmentModel.newInstance(activityContext)
        }

        setRecyclerView()
        showProgress(true)
        doAsync {
            val projectsList = model.getProjectInfos(getBasicAuth())
            Log.d("ProjectList",projectsList.toString())
            uiThread {
                displayItems(projectsList)
                showProgress(false)
            }
        }
    }

    private fun setRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(activityContext)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = null
        recycler_view.addItemDecoration(DividerItemDecoration(recycler_view.context, DividerItemDecoration.VERTICAL))
    }

    private fun getBasicAuth(): String {
        return BASEAUTH
    }

    private fun displayItems(items: List<Item>) {
        recycler_view.adapter = CustomAdapter(items, this)
        recycler_view.adapter!!.notifyDataSetChanged()
        //(recycler_view.adapter as CustomAdapter).notifyDataSetChanged()
    }

    private fun showProgress(show: Boolean) {
        progressbar.visibility = if (show) View.VISIBLE else View.GONE
        mscrollview.visibility = if (show) View.GONE else View.VISIBLE
    }

    override fun showProject(item: Item) {

    }

}
interface ItemAdapterListener {
    fun showProject(item: Item)

}


