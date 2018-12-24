package murer.rudy.api.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import murer.rudy.api.AccountData
import murer.rudy.api.MainActivity
import murer.rudy.api.R
import murer.rudy.api.authentication.LoginActivityModelStatus.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class LoginActivity : AppCompatActivity() {

    private lateinit var model: LoginActivityModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_login)
        if (!this::model.isInitialized) {
            model = LoginActivityModel.newInstance(this)
        }

        login_button.setOnClickListener {
            showProgress(true)
            doAsync {
                val response = model.login(getUserData())
                uiThread {
                    if (weakRef.get() != null) {
                        onLogin(response)
                    }
                }
            }
        }
    }

    private fun getUserData(): String {
        val username = "twp_s0ZgGXeXvZ0AUcU1HFnF5uLYdeAe" //text_username.text.toString()
        val password = "7417RudyM"//text_password.text.toString()
        return "Basic " + Base64.encodeToString(("$username:$password").toByteArray(), Base64.NO_WRAP)
    }

    private fun showProgress(show: Boolean) {
        progressbar.visibility = if (show) View.VISIBLE else View.GONE
        mscrollview.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun onLogin(response: LoginActivityModelResponse) {
        when (response.status) {
            OK -> {
                savePreferences(response.accountData!!.URL);redirectToMainActivity()
            }
            AUTH_FAILED -> {
                showErrorMessage(response.errorMessage)
            }
            NO_NETWORK -> {
                showErrorMessage(response.errorMessage)
            }
            FAILED -> {
                showErrorMessage(response.errorMessage)
            }
        }
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun savePreferences(url: String) {
        BASEURL = url
        BASEAUTH = getUserData()
        val sharedPref = getSharedPreferences("url", Context.MODE_PRIVATE) ?: return
        sharedPref.edit().putString("url", url).apply()
        sharedPref.edit().putString("user", getUserData()).apply()
        Log.d("tag",getUserData())
    }

    private fun showErrorMessage(errorMessage: String) {
        toast(errorMessage)
        showProgress(false)
    }

    private fun debugDisplayUserData(accountData: AccountData) {
        val result =
            "Firstname: ${accountData.firstname}\n" +
                    "LastName: ${accountData.lastname}\n" +
                    "ID: ${accountData.id}\n" +
                    "UserID: ${accountData.userId}\n" +
                    "URL: ${accountData.URL}\n"
        textview_result.text = result
    }

    companion object {
        lateinit var BASEURL:String
        lateinit var BASEAUTH:String
    }


}


