package murer.rudy.api

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ProjectFragment : Fragment() {

    private lateinit var activityContext: Context
    private lateinit var model: ProjectFragmentModel

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
            model = ProjectFragmentModel.newInstance(activityContext)
        }

    }

}


