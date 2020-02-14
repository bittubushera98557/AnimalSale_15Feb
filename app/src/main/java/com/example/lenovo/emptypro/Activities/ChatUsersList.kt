package com.example.lenovo.emptypro.Activities

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.example.lenovo.emptypro.Utils.GlobalData
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat_users_list.*

class ChatUsersList : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
         when(v!!.id)
         {
             R.id.tv_recievedQueries->
             {
                 strQueries="recieved"
                 tv_recievedQueries.setBackgroundColor(Color.parseColor("#FF535353"))
                 tv_sentQueries.setBackgroundColor(Color.parseColor("#FFFC9576"))
             }

             R.id.tv_sentQueries->
             {
                 strQueries="sent"
                 tv_sentQueries.setBackgroundColor(Color.parseColor("#FF535353"))
                 tv_recievedQueries.setBackgroundColor(Color.parseColor("#FFFC9576"))

             }
         }
    }
var strQueries="recieved"
    var dbReference = FirebaseDatabase.getInstance().reference
    var TAG = "ChatUsersList "
    internal var utilities = Utilities()
var loggedUserId=""
    private var firebaseQueriesData: MutableList<AllApiResponse.FirebaseQueryModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_users_list)
        tv_sentQueries.setOnClickListener(this)
     loggedUserId=SharedPrefUtil.getUserId(this@ChatUsersList)
        if (GlobalData.isConnectedToInternet(this@ChatUsersList)) {
            getAllFireBaseQueries()
        } else {
            swipe_refresh.isRefreshing=false
            GlobalData.showSnackbar(this@ChatUsersList, getString(R.string.please_check_your_internet_connection))
        }
    }

    private fun getAllFireBaseQueries() {

        firebaseQueriesData=mutableListOf()

        Log.e(TAG + " ", "function searching  FireBaseQueries()")

        var allUsersListener: ValueEventListener? = null
        var loggeInRef = dbReference.child("/AllChat")


        allUsersListener = object : ValueEventListener {
            @SuppressLint("WrongConstant")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e(TAG + " ", "function Calling  FireBaseQueries()")

                val children = dataSnapshot!!.children

                var txtTest = "FireBaseQueries data"
                children.forEach {

                    //    var strImg= it.child("profileImg").value.toString()
                    var strDB_OwnerId = it.child("ownerId").value.toString()
                    var strDB_intersterId= it.child("intersterId").value.toString()


                    if (strDB_OwnerId == loggedUserId && strQueries=="recieved") {
                        var firebaseQueryItem = AllApiResponse.FirebaseQueryModel()
                        firebaseQueryItem.interesterId = "" + it.child("intersterId").value
                        firebaseQueryItem.petId = "" + it.child("petId").value
                        firebaseQueryItem.petName = "" + it.child("petName").value
                        firebaseQueryItem.ownerId = "" + it.child("ownerId").value
                        firebaseQueryItem.queryId = "" + it.key
                        firebaseQueryItem.queryType= "recieved"

                        firebaseQueriesData!!.add(firebaseQueryItem)
                        Log.e(
                                TAG + " getOldChat",
                                "  ownerId=" + it.child("ownerId").value.toString() + "    petId=" + it.child("petId").value.toString() + "   " + "intersterId=" + it.child("intersterId").value.toString()
                        )
                    }


                    if (strDB_intersterId == loggedUserId && strQueries=="sent") {
                        var firebaseQueryItem = AllApiResponse.FirebaseQueryModel()
                        firebaseQueryItem.interesterId = "" + it.child("intersterId").value
                        firebaseQueryItem.petId = "" + it.child("petId").value
                        firebaseQueryItem.petName = "" + it.child("petName").value
                        firebaseQueryItem.ownerId = "" + it.child("ownerId").value
                        firebaseQueryItem.queryId = "" + it.key
                        firebaseQueryItem.queryType= "sent"
                        firebaseQueriesData!!.add(firebaseQueryItem)
                        Log.e(
                                TAG + " getOldChat",
                                "  ownerId=" + it.child("ownerId").value.toString() + "    petId=" + it.child("petId").value.toString() + "   " + "intersterId=" + it.child("intersterId").value.toString()
                        )
                    }

                    Log.e(TAG, "getOldChat  :  userLoginMsg")


                    txtTest = "matched data "

                }
                setChatRecyleData()
                Toast.makeText(applicationContext, "" + txtTest, Toast.LENGTH_LONG).show()
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.e(TAG, "getOldChat  :onCancelled", databaseError.toException())

            }
        }
            loggeInRef.addValueEventListener(allUsersListener!!)

    }

    private fun setChatRecyleData() {
        rv_petQueries.adapter!!.notifyDataSetChanged()
        if(firebaseQueriesData!!.size>0)
        {
            tv_noData.visibility=View.GONE
        }

    }
}
