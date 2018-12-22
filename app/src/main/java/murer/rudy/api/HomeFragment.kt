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

        login_button.setOnClickListener {
            showProgress(true)
            doAsync {
                val userdata = getUserData()
                uiThread {
                    if (weakRef.get()!= null && userdata != null) {
                        displayUserData(userdata)
                        model.setURLInPreferences(userdata.URL)
                        BASEURL = userdata.URL
                    }
                    showProgress(false)
                }
            }
        }
    }

    private fun displayUserData(accountData: AccountData) {
        val result =
            "Firstname: ${accountData.firstname}\n" +
            "LastName: ${accountData.lastname}\n" +
            "ID: ${accountData.id}\n" +
            "UserID: ${accountData.userId}\n" +
            "URL: ${accountData.URL}\n"
        textview_result.text = result
    }

    private fun getUserData(): AccountData? {
        val username = "twp_s0ZgGXeXvZ0AUcU1HFnF5uLYdeAe" //text_username.text.toString()
        val password = "7417RudyM"//text_password.text.toString()
        val basicAuth = "Basic " + Base64.encodeToString(("$username:$password").toByteArray(), Base64.NO_WRAP)
        model.setUserDataInPrefences(basicAuth)
        BASICAUTH = basicAuth
        return model.getUserData(basicAuth)

    }

    private fun showProgress(show: Boolean) {
        progressbar.visibility = if (show) View.VISIBLE else View.GONE
        mscrollview.visibility = if (show) View.GONE else View.VISIBLE
    }

    companion object {
        lateinit var BASEURL: String
        lateinit var BASICAUTH: String
    }

}


