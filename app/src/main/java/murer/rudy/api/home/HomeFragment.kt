package murer.rudy.api.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import murer.rudy.api.Project
import murer.rudy.api.R


class HomeFragment : Fragment() {

    private lateinit var activityContext: Context
    private lateinit var model: HomeFragmentModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!this::activityContext.isInitialized && isAdded) {
            activityContext = activity?.applicationContext!!
        }
        if (!this::model.isInitialized) {
            model = HomeFragmentModel.newInstance(activityContext)
        }

    }

    private fun setRecyclerView() {
        /*recycler_view.layoutManager = LinearLayoutManager(activityContext)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = null
        recycler_view.addItemDecoration(DividerItemDecoration(recycler_view.context, DividerItemDecoration.VERTICAL))*/
    }

    private fun getBasicAuth(): String? {
        val sharedPref = activityContext.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPref.getString("user", "")
    }

    private fun displayItems(items: List<Project>) {

    }

    private fun showProgress(show: Boolean) {
        progressbar.visibility = if (show) View.VISIBLE else View.GONE
        //mscrollview.visibility = if (show) View.GONE else View.VISIBLE
    }
}



