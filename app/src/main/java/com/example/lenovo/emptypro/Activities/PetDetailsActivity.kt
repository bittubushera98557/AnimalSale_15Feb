package com.example.lenovo.emptypro.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import com.glide.slider.library.SliderTypes.TextSliderView
import com.google.android.gms.maps.SupportMapFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lenovo.emptypro.Utilities.Utilities
import com.example.lenovo.emptypro.Utils.GlobalData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.iww.classifiedolx.recyclerview.setUp
import kotlinx.android.synthetic.main.fragment_advertisement_details.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.qury_item.view.*
import java.lang.Exception

class PetDetailsActivity : AppCompatActivity(), View.OnClickListener, OnMapReadyCallback {
    override fun onMapReady(googleMap: GoogleMap?) {

        googleMap!!.uiSettings.isZoomControlsEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        gMap = googleMap
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {
                onBackPressed()
            }
            R.id.tv_call -> {

                try {
                    val callIntent = Intent(Intent.ACTION_DIAL)
                    callIntent.data = Uri.parse("tel:" + petDetailModel!!.ownerNumber)
                    startActivity(callIntent)
                } catch (exp: Exception) {
                    Toast.makeText(this@PetDetailsActivity, "Owner Number is not available", Toast.LENGTH_LONG).show()
                }
            }
            R.id.fl_AdvertisementDetailsFrag -> {

            }
            R.id.tv_bottomChat -> {
                val intent = Intent(this@PetDetailsActivity, P2PChatView::class.java)
                intent.putExtra("oldPetId", "" + petId)
                startActivity(intent)
            }
            R.id.tv_petQueries -> {
                try{
                    getAllFireBaseQueries()
                }
                catch (exp:java.lang.Exception)
                {
                    Log.e(TAG+"","call getAllFireBaseQueries excep="+exp.toString())
                } }

        }
    }

    // TODO: Rename and change types of parameters
    private var petId: String? = ""
    var petDetailModel: AllApiResponse.PetDetailRes.PetDetail? = null
    var lati = "0"
    var longi = "0"
    internal var service: GetDataService? = null
    var gMap: GoogleMap? = null
    var TAG = "AdvertisementDetailsFrag "
    internal var utilities = Utilities()
    var quriesDialog :Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_advertisement_details)
        ll_topHeaderLayout.visibility = View.VISIBLE
        img_back.setOnClickListener(this)
        tv_title.text = "Pet Detail"
        tv_bottomChat.setOnClickListener(this)
        tv_call.setOnClickListener(this)
        tv_petQueries.setOnClickListener(this)
        fl_AdvertisementDetailsFrag.setOnClickListener(this)
        quriesDialog = Dialog(this@PetDetailsActivity)
        quriesDialog!! .requestWindowFeature(Window.FEATURE_NO_TITLE)
        quriesDialog!! .setCancelable(true)
        quriesDialog!! .setContentView(R.layout.popup_queries)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)

        try {
            getOldIntentData()
        } catch (exp: Exception) {

        }

        swipe_refresh.setOnRefreshListener { }
        try {
            getOldIntentData()
        } catch (exp: Exception) {

        }
    }

    private fun getOldIntentData() {

        petId = intent.extras!!.getString("oldPetId")
        if (!petId.equals("")) {
            Log.e(TAG + " oldIntent", "petID=" + petId)
            getPetDetail()
        }
    }

    private fun getPetDetail() {
        Log.e(TAG + " getPetDetail", "single-pet/?userID=" + SharedPrefUtil.getUserId(this@PetDetailsActivity) + "&petID=" + petId)
        var call = service!!.getPetDetailsApi("" + SharedPrefUtil.getUserId(this@PetDetailsActivity), "" + petId)
        nsView.visibility = View.GONE
         fl_bottom.visibility = View.GONE

        call.enqueue(object : Callback<AllApiResponse.PetDetailRes> {
            override fun onResponse(call: Call<AllApiResponse.PetDetailRes>, response: Response<AllApiResponse.PetDetailRes>) {
                Log.e("getPetDetail res", "" + Gson().toJson(response.body()))
                swipe_refresh.isRefreshing = false
                if (response.body()!!.status.equals("200") && response.body()!!.data.size > 0) {
                    nsView.visibility = View.VISIBLE
                    fl_bottom.visibility = View.VISIBLE

                    petDetailModel = response.body()!!.data[0]
                    for (item in petDetailModel!!.images) {
                        val sliderView = TextSliderView(this@PetDetailsActivity)
                        sliderView.image(item.img).setProgressBarVisible(true)
                        slider.addSlider(sliderView)
                    }

                    if (petDetailModel!!.images.size == 0) {
                        val sliderView = TextSliderView(this@PetDetailsActivity)
                        sliderView.image(R.drawable.app_logo).setProgressBarVisible(true)
                        slider.addSlider(sliderView)
                    }
                    tv_advertiseLoc.text = "" + petDetailModel!!.userCity + ", " + petDetailModel!!.userState
                    tv_advertiseDate.text = "" + GlobalData.dateSplit(petDetailModel!!.createdOn)
                    tv_petTitle.text = petDetailModel!!.petTitle
                    tv_advertiseDesc.text = petDetailModel!!.petDescription
                    tv_advertisePrice.text = petDetailModel!!.petPrice
                    tv_owmerName.text = petDetailModel!!.ownerNamer
                    tv_adsId.text = "Pet ID:  " + petDetailModel!!.petID

                    ll_bottomChatCall.visibility = View.VISIBLE

                    if (petDetailModel!!.ownerID.toString().equals("" + SharedPrefUtil.getUserId(this@PetDetailsActivity))) {
                        ll_bottomChatCall.visibility = View.GONE
                        iv_favImg.visibility = View.GONE
                        tv_petQueries.visibility = View.VISIBLE
                    }
                    var strDetails = ""
                    try {
                        if (!petDetailModel!!.petTitle.equals("")) {
                            strDetails = strDetails + "Title: " + petDetailModel!!.petTitle + "\n"
                        }
                        if (!petDetailModel!!.category.equals("")) {
                            strDetails = strDetails + "Category: " + petDetailModel!!.category + "\n"
                        }
                        if (!petDetailModel!!.subCategory.equals("")) {
                            strDetails = strDetails + "SubCategory: " + petDetailModel!!.subCategory + "\n"
                        }
                        if (!petDetailModel!!.petAge.equals("")) {
                            strDetails = strDetails + "Pet Age: " + petDetailModel!!.petAge + "\n"
                        }
                        if (!petDetailModel!!.petBreed.equals("")) {
                            strDetails = strDetails + "Pet Breed: " + petDetailModel!!.petBreed + "\n"
                        }
                        if (!petDetailModel!!.petName.equals("")) {
                            strDetails = strDetails + "Pet Name: " + petDetailModel!!.petName + "\n"
                        }
                        if (!petDetailModel!!.petDescription.equals("")) {
                            strDetails = strDetails + "Pet Description: " + petDetailModel!!.petDescription + "\n"
                        }
                        if (!petDetailModel!!.ownerNamer.equals("")) {
                            strDetails = strDetails + "Owner Name : " + petDetailModel!!.ownerNamer + "\n"
                        }
                        if (petDetailModel!!.isPrivate.toString().toLowerCase().equals("no")) {
                            strDetails = strDetails + "Owner Number: " + petDetailModel!!.ownerNumber + "\n"
                        }
                    } catch (exp: Exception) {
                        Log.e(TAG, "filed values excep=" + exp.toString())
                    }

                    tv_moreFieldsValues.text = "" + strDetails
                    if (petDetailModel!!.favourite.equals("yes"))
                        iv_favImg.setImageResource(R.drawable.ic_favorite_filled)

                    if (petDetailModel!!.favourite.equals("no"))
                        iv_favImg.setImageResource(R.drawable.ic_favorite_empty)
                    iv_favImg.setOnClickListener {

                        if (petDetailModel!!.favourite.equals("yes")) {
                            if (utilities.isConnected(this@PetDetailsActivity))
                                callChangeFavApi("remove")
                            else
                                utilities.snackBar(slider, "Please check internet ")
                        } else {
                            if (utilities.isConnected(this@PetDetailsActivity))
                                callChangeFavApi("add")
                            else
                                utilities.snackBar(slider, "Please check internet ")

                        }
                    }


                } else {
                    GlobalData.showSnackbar(this@PetDetailsActivity as Activity, response.body()!!.messageType)
                }
            }

            override fun onFailure(call: Call<AllApiResponse.PetDetailRes>, t: Throwable) {
                // progress_bar.setVisibility(View.GONE);
                Toast.makeText(this@PetDetailsActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                swipe_refresh.isRefreshing = false
            }
        })

    }

    private fun callChangeFavApi(strAction: String) {
        Log.e(TAG, "callChangeFavApi   adsId=" + petId + "   action=" + strAction)
        var dialogBar = utilities.dialog(this@PetDetailsActivity)

        val call = service!!.addInFav("" + strAction, "" + SharedPrefUtil.getUserId(this@PetDetailsActivity), "" + petId).enqueue(object : Callback<AllApiResponse.CommonRes> {
            override fun onResponse(
                    call: Call<AllApiResponse.CommonRes>,
                    response: Response<AllApiResponse.CommonRes>
            ) {
                Log.e("callAddInFavApi res", "" + Gson().toJson(response.body()))
                dialogBar.cancel()
                if (response.isSuccessful && (response.body()!!.status.equals("200"))) {
                    if (strAction.equals("add"))
                        iv_favImg.setImageResource(R.drawable.ic_favorite_filled)

                    if (strAction.equals("remove"))
                        iv_favImg.setImageResource(R.drawable.ic_favorite_empty)

                } else {
                    //swipe_refresh.isRefreshing = false
                }
                getPetDetail()
            }

            override fun onFailure(call: Call<AllApiResponse.CommonRes>, t: Throwable) {
                t.printStackTrace()
                dialogBar.cancel()

                //swipe_refresh.isRefreshing = false
            }
        })


    }

    private fun getAllFireBaseQueries() {
        var firebaseQueriesData: MutableList<AllApiResponse.FirebaseQueryModel>? = null
        var dbReference = FirebaseDatabase.getInstance().reference

        firebaseQueriesData = mutableListOf()

        Log.e(TAG + " ", "function searching  FireBaseQueries()")

        var allUsersListener: ValueEventListener? = null
        var loggeInRef = dbReference.child("/AllChat")


        allUsersListener = object : ValueEventListener {
            @SuppressLint("WrongConstant")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e(TAG + " ", "function Calling  FireBaseQueries()")

                val children = dataSnapshot!!.children
                firebaseQueriesData!!.clear()
                var txtTest = "FireBaseQueries data"
                children.forEach {

                    //    var strImg= it.child("profileImg").value.toString()
                    var strDB_OwnerId = it.child("ownerId").value.toString()
                    var strDB_intersterId = it.child("intersterId").value.toString()

                    var strDB_petId = "" + it.child("petId").value.toString()

                    if ((strDB_OwnerId == SharedPrefUtil.getUserId(this@PetDetailsActivity)) && (strDB_petId == petId)) {
                        var firebaseQueryItem = AllApiResponse.FirebaseQueryModel()
                        firebaseQueryItem.interesterId = "" + it.child("intersterId").value.toString()
                        firebaseQueryItem.petId = "" + it.child("petId").value.toString()
                        firebaseQueryItem.petName = "" + it.child("petName").value.toString()
                        firebaseQueryItem.ownerId = "" + it.child("ownerId").value.toString()
                        firebaseQueryItem.queryId = "" + it.key
                        firebaseQueryItem.queryType = "recieved"
                        firebaseQueryItem.ownerName = "" + it.child("ownerName").value.toString()
                        firebaseQueryItem.interesterName = "" + it.child("intersterName").value.toString()

                        firebaseQueriesData!!.add(firebaseQueryItem)
                        Log.e(
                                TAG + " getOldChat",
                                "  ownerId=" + it.child("ownerId").value.toString() + "    petId=" + it.child("petId").value.toString() + "   " + "intersterId=" + it.child("intersterId").value.toString()
                        )
                    }



                    Log.e(TAG, "getOldChat  :  userLoginMsg")


                }
         try {
             setChatRecylePopUp(firebaseQueriesData)
         }
         catch (exp : Exception)
         {
             Log.e(TAG,"call setChatRecylePopUp excep="+exp.toString())
         }

              }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.e(TAG, "getOldChat  :onCancelled", databaseError.toException())

            }
        }
        loggeInRef.addValueEventListener(allUsersListener!!)

    }

    private fun setChatRecylePopUp(firebaseQueriesData: MutableList<AllApiResponse.FirebaseQueryModel>) {


        var rv_queries = quriesDialog!! .findViewById(R.id.rv_queries) as RecyclerView
        var iv_close = quriesDialog!! .findViewById(R.id.iv_close) as ImageView
        var tv_noData = quriesDialog!! .findViewById(R.id.tv_noData) as TextView

        rv_queries.setLayoutManager(LinearLayoutManager(this))
        rv_queries.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rv_queries.setUp(firebaseQueriesData!!, R.layout.qury_item, { it1 ->
            var str = "interesterId=" + it1.interesterId + "\n  interesterName=" + it1.interesterName + "\n ownerId=" + it1.ownerId + "\n petId=" + it1.petId
            tv_ownerName.text = it1.ownerId + " <> " + it1.ownerName
            tv_ownerName.visibility=View.GONE
            tv_InteresterName.text = /*it1.interesterId +*/ "Interester: " + it1.interesterName
            tv_queryId.text = it1.queryId
            this.tv_petName.text = /*it1.petId + */"Pet: " + it1.petName

            Log.e(TAG + "Query Response Data", "interesterId=" + it1.interesterId + "  <<>>  interesterName=" + it1.interesterName + "  <<>>  ownerId=" + it1.ownerId + "  <<>>  petId=" + it1.petId + "  <<>> QueryId=" + it1.queryId + "  <<>> queryType=" + it1.queryType)
            ll_query_item.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, P2PChatView::class.java)
                intent.putExtra("oldPetId", "" + it1.petId)
                startActivity(intent)
            })

        }, { view1: View, i: Int -> })

        rv_queries.adapter!!.notifyDataSetChanged()
        tv_noData.visibility = View.VISIBLE
        rv_queries.visibility = View.GONE

        if (firebaseQueriesData.size > 0) {
            tv_noData.visibility = View.GONE
            rv_queries.visibility = View.VISIBLE
        }

        rv_queries.adapter!!.notifyDataSetChanged()
        iv_close.setOnClickListener(View.OnClickListener {
            quriesDialog!! .cancel()
        })

        var lWindowParams = WindowManager.LayoutParams();
        lWindowParams.copyFrom(quriesDialog!! .getWindow().getAttributes());
        lWindowParams.width = WindowManager.LayoutParams.FILL_PARENT; // this is where the magic happens
        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        quriesDialog!! .getWindow().setAttributes(lWindowParams);

if(quriesDialog!!.isShowing)
{
    quriesDialog!! .cancel()
}

        try {
            quriesDialog!! .show()
        }
        catch (exp : Exception)
        {
            Log.e(TAG,"show queries dialog excep="+exp.toString())
        }

    }

    override fun onBackPressed() {

        finish()
    }
}
