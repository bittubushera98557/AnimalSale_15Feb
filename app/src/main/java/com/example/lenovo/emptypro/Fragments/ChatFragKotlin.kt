package com.example.lenovo.emptypro.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.lenovo.emptypro.Activities.MainActivity
import com.example.lenovo.emptypro.Activities.P2PChatView

import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.example.lenovo.emptypro.Utils.GlobalData
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.iww.classifiedolx.recyclerview.setUp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_chat_frag_kotlin.*
import kotlinx.android.synthetic.main.qury_item.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChatFragKotlin : Fragment(), View.OnClickListener {
     override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_recievedQueries -> {
                tv_recievedQueries.setTextColor(Color.parseColor("#EC0B63"))
                tv_recievedQueries.setBackgroundColor(Color.parseColor("#FFFC9576"))

                tv_sentQueries .setTextColor(Color.parseColor("#FF6B6969"))
                tv_sentQueries .setBackgroundColor(Color.parseColor("#FFFC9576"))

                strQueries = "recieved"
                getAllFireBaseQueries()
             }

            R.id.tv_sentQueries -> {
                tv_recievedQueries.setTextColor(Color.parseColor("#FF6B6969"))
                tv_recievedQueries.setBackgroundColor(Color.parseColor("#FFFC9576"))

                tv_sentQueries .setTextColor(Color.parseColor("#EC0B63"))
                tv_sentQueries .setBackgroundColor(Color.parseColor("#FFFC9576"))

                strQueries = "sent"
                getAllFireBaseQueries()
            }
        }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var strQueries = "recieved"
    var dbReference = FirebaseDatabase.getInstance().reference
    var allUsersListener: ValueEventListener? = null
    var loggeInRef = dbReference.child("/AllChat")

    var TAG = "ChatUsersList "
    internal var utilities = Utilities()
    var loggedUserId = ""
    private var firebaseQueriesData: MutableList<AllApiResponse.FirebaseQueryModel>? = null
    var ctx: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loggedUserId= SharedPrefUtil.getUserId(ctx!!)
        firebaseQueriesData = mutableListOf()

        rv_petQueries.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_petQueries.layoutManager = mLayoutManager



        if (GlobalData.isConnectedToInternet(ctx!!)) {
            getAllFireBaseQueries()
        } else {
            swipe_refresh.isRefreshing=false
            utilities.snackBar(rv_petQueries, "Please check internet ")
        }
        rv_petQueries.layoutManager = mLayoutManager

        tv_sentQueries.setOnClickListener(this)
        tv_recievedQueries.setOnClickListener(this)

        tv_sentQueries.callOnClick()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_frag_kotlin, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    private fun getAllFireBaseQueries() {
        swipe_refresh.isRefreshing=false
        firebaseQueriesData=mutableListOf()

        Log.e(TAG + " ", "function searching  FireBaseQueries()")



        allUsersListener = object : ValueEventListener {
            @SuppressLint("WrongConstant")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e(TAG + " ", "function Calling  FireBaseQueries()")

                val children = dataSnapshot!!.children
                firebaseQueriesData!!.clear()

                 children.forEach {
                    Log.e(         TAG + " FireBaseQueries",
                            "  ownerId=" + it.child("ownerId").value.toString() + "    petId=" + it.child("petId").value.toString() + "   " + "intersterId=" + it.child("intersterId").value.toString()
                    )
                    //    var strImg= it.child("profileImg").value.toString()
                    var strDB_OwnerId = it.child("ownerId").value.toString()
                    var strDB_intersterId= it.child("intersterId").value.toString()

                    if (strDB_OwnerId == loggedUserId && strQueries=="recieved") {
                        var firebaseQueryItem = AllApiResponse.FirebaseQueryModel()
                        firebaseQueryItem.interesterId = "" + it.child("intersterId").value
                        firebaseQueryItem.petId = "" + it.child("petId").value
                        firebaseQueryItem.petName = "" + it.child("petName").value
                        firebaseQueryItem.ownerId = "" + it.child("ownerId").value
                        firebaseQueryItem.ownerName= "" + it.child("ownerName").value
                        firebaseQueryItem.interesterName= "" + it.child("intersterName").value
                        firebaseQueryItem.queryId = "" + it.key
                        firebaseQueryItem.queryType= "recieved"
                        firebaseQueriesData!!.add(firebaseQueryItem)
                        Log.e(
                                TAG + " getOldChat",
                                "  ownerId=" + it.child("ownerId").value.toString() + "    petId=" + it.child("petId").value.toString() + "    intersterId=" + it.child("intersterId").value.toString()
                        )
                    }


                    if (strDB_intersterId == loggedUserId && strQueries=="sent") {
                        var firebaseQueryItem = AllApiResponse.FirebaseQueryModel()
                        firebaseQueryItem.interesterId = "" + it.child("intersterId").value
                        firebaseQueryItem.petId = "" + it.child("petId").value
                        firebaseQueryItem.petName = "" + it.child("petName").value
                        firebaseQueryItem.ownerId = "" + it.child("ownerId").value
                        firebaseQueryItem.ownerName= "" + it.child("ownerName").value
                        firebaseQueryItem.interesterName= "" + it.child("intersterName").value
                        firebaseQueryItem.queryId = "" + it.key
                        firebaseQueryItem.queryType= "sent"
                        firebaseQueriesData!!.add(firebaseQueryItem)
                        Log.e(
                                TAG + " getOldChat",
                                "  ownerId=" + it.child("ownerId").value.toString() + "    petId=" + it.child("petId").value.toString() + "  intersterId=" + it.child("intersterId").value.toString()
                        )
                    }
                 }
          try{
              setChatRecyleData()
          }
          catch(exp:Exception)
          {
              Log.e(TAG+"","calling setChatRecyleData excep="+exp.toString())
          }
                   }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.e(TAG, "getOldChat  :onCancelled", databaseError.toException())
            }
        }
        loggeInRef.addValueEventListener(allUsersListener!!)
    }

    private fun setChatRecyleData() {
        rv_petQueries.setUp(firebaseQueriesData!!, R.layout.qury_item, { it1 ->
            var str="interesterId="+it1.interesterId+"\n  interesterName="+it1.interesterName +"\n ownerId="+it1.ownerId+"\n petId="+it1.petId
           tv_ownerName.text="Owner:  "/*+ it1.ownerId+" <> "*/+it1.ownerName
             tv_InteresterName.text= "Interester:  "+ /*it1.interesterId+" <> "+*/it1.interesterName
             tv_queryId.text= it1.queryId
            this.tv_petName.text= "Pet Info:  "+ /*it1.petId+" <> "+*/it1.petName
            Log.e(TAG+"Query Response Data","interesterId="+it1.interesterId+"  <<>>  interesterName="+it1.interesterName +"  <<>>  ownerId="+it1.ownerId+"  <<>>  petId="+it1.petId+"  <<>> QueryId="+it1.queryId +"  <<>> queryType="+it1.queryType)
if(strQueries.equals("recieved"))
{
    tv_ownerName.visibility=View.GONE
}else{
    tv_InteresterName.visibility=View.GONE

}


            ll_query_item.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, P2PChatView::class.java)
                intent.putExtra("oldPetId", "" + it1.petId)
                startActivity(intent)
            })

        }, { view1: View, i: Int -> })

        rv_petQueries.adapter!!.notifyDataSetChanged()
        tv_noData.visibility=View.VISIBLE
        rv_petQueries.visibility=View.GONE
        if(firebaseQueriesData!!.size>0)
        {
            tv_noData.visibility=View.GONE
            rv_petQueries.visibility=View.VISIBLE








        }
        Log.e(TAG+"","type="+strQueries+"   <>  size="+firebaseQueriesData!!.size)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        if(allUsersListener!=null)
        {
            loggeInRef.removeEventListener(allUsersListener!!)

        }
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ChatFragKotlin().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
