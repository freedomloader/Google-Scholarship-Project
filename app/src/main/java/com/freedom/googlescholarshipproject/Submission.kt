package com.freedom.googlescholarshipproject

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class Submission : AppCompatActivity() {

    private var firstName: EditText? = null
    private var lastName: EditText? = null
    private var editEmail: EditText? = null
    private var projectLink: EditText? = null
    private var btnSubmit: Button? = null
    private var myDialog: Dialog? = null
    private var con: Context? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.submission_lay)
        con = applicationContext

        firstName = findViewById(R.id.edit_firstname)
        lastName = findViewById(R.id.edit_lastname)
        editEmail = findViewById(R.id.edit_email)
        projectLink = findViewById(R.id.github_project_link)
        btnSubmit = findViewById(R.id.btn_submit)

        btnSubmit!!.setOnClickListener { ShowConfirmPopup() }

        val btnBack = findViewById<ImageButton>(R.id.toolbar_back)
        btnBack!!.setOnClickListener { finish() }
    }

    private fun makePostRequest() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/e/")
                .build()
        val spreadsheetWebService =
                retrofit.create(
                        QuestionsSpreadsheetWebService::class.java
                )

        val fName: String = firstName?.text.toString()
        val lName: String = lastName?.text.toString()
        val email: String = editEmail?.text.toString()
        val pLink: String = projectLink?.text.toString()
//        showToast(fName)

        val completeQuestionnaireCall =
                spreadsheetWebService.completeQuestionnaire(fName, lName, email, pLink)

        showProgress()
        completeQuestionnaireCall!!.enqueue{

            onResponse = {
                ShowSuccessPopup()
                dismissProgress()
            }

            onFailure = {
                ShowErrorPopup()
                dismissProgress()
            }
        }
    }

    interface QuestionsSpreadsheetWebService {
        @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/for\n" +
                "mResponse")
        @FormUrlEncoded
        fun completeQuestionnaire(
                @Field("entry.1877115667") firstName: String?,
                @Field("entry.2006916086") lastName: String?,
                @Field("entry.1824927963") email: String?,
                @Field("entry.284483984") projectLink: String?
        ): Call<Void?>?
    }

    fun<T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
        val callBackKt = CallBackKt<T>()
        callback.invoke(callBackKt)
        this.enqueue(callBackKt)
    }

    class CallBackKt<T>: Callback<T> {

        var onResponse: ((Response<T>) -> Unit)? = null
        var onFailure: ((t: Throwable?) -> Unit)? = null

        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure?.invoke(t)
            Log.d("XXX", "Failed. $t")
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResponse?.invoke(response)
            Log.d("XXX", "Submitted. $response")
//            Toast.makeText(con,"Submitted. $s", Toast.LENGTH_SHORT).show()
        }

    }

    fun ShowConfirmPopup() {
        myDialog = Dialog(this)
        myDialog!!.setContentView(R.layout.confirm_dialog)
        var txtclose = myDialog!!.findViewById(R.id.txtclose) as TextView
        var btnYes = myDialog!!.findViewById(R.id.yes) as Button
        txtclose.text = "X"

        txtclose.setOnClickListener {myDialog!!.dismiss() }
        btnYes.setOnClickListener {
            myDialog!!.dismiss();
            makePostRequest()
        }
        myDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog!!.show()
    }

    fun ShowSuccessPopup() {
        myDialog = Dialog(this)
        myDialog!!.setContentView(R.layout.successful_dialog)

        myDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog!!.show()
    }

    fun ShowErrorPopup() {
        myDialog = Dialog(this)
        myDialog!!.setContentView(R.layout.error_dialog)

        myDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog!!.show()
    }

    fun showToast(s: String) {
        Toast.makeText(con,"Submitted. $s", Toast.LENGTH_SHORT).show()
    }

    fun showProgress() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Submission")
        progressDialog!!.setMessage("Loading...")
        progressDialog!!.show()
    }

    fun dismissProgress() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }
}