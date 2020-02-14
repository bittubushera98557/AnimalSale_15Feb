package com.example.lenovo.emptypro.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lenovo.emptypro.Activities.P2PChatView
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance

import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.example.lenovo.emptypro.Utils.GlobalData
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import com.glide.slider.library.SliderTypes.TextSliderView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.iww.classifiedolx.recyclerview.setUp
import kotlinx.android.synthetic.main.fragment_advertisement_details.*
import kotlinx.android.synthetic.main.qury_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdvertisementDetailsFrag : Fragment(), View.OnClickListener, OnMapReadyCallback {
    override fun onMapReady(googleMap: GoogleMap?) {

        googleMap!!.uiSettings.isZoomControlsEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        gMap = googleMap
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_bottomChat -> {
                var intentMain = Intent((ctx!! as Activity), P2PChatView::class.java)
                //    intentMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                intentMain.putExtra("oldPetId", petId)
                startActivity(intentMain)

            }

            R.id.tv_petQueries -> {
                try {
                    getAllFireBaseQueries()
                } catch (exp: java.lang.Exception) {
                    Log.e(TAG + "", "call getAllFireBaseQueries excep=" + exp.toString())
                }
            }

            R.id.tv_call -> {

                try {
                    val callIntent = Intent(Intent.ACTION_DIAL)
                    callIntent.data = Uri.parse("tel:" + petDetailModel!!.ownerNumber)
                    startActivity(callIntent)
                } catch (exp: java.lang.Exception) {
                    Toast.makeText(ctx!!, "Owner Number is not available", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun getAllFireBaseQueries() {


        Log.e(TAG + " ", "function searching  FireBaseQueries()")



        allUsersListener = object : ValueEventListener {
            @SuppressLint("WrongConstant")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e(TAG + " ", "function Calling  FireBaseQueries()")
                firebaseQueriesData!!.clear()
                val children = dataSnapshot!!.children

                children.forEach {

                    //    var strImg= it.child("profileImg").value.toString()
                    var strDB_OwnerId = it.child("ownerId").value.toString()
                    var strDB_intersterId = it.child("intersterId").value.toString()

                    var strDB_petId = "" + it.child("petId").value

                    if ((strDB_OwnerId == SharedPrefUtil.getUserId(ctx!!)) && (strDB_petId == petId)) {
                        /*
                         firebaseQueryItem.interesterId = "" + it.child("intersterId").value
                         firebaseQueryItem.petId = "" + it.child("petId").value
                         firebaseQueryItem.petName = "" + it.child("petName").value
                         firebaseQueryItem.ownerId = "" + it.child("ownerId").value
                         firebaseQueryItem.queryId = "" + it.key
                         firebaseQueryItem.queryType = "recieved"

                         firebaseQueriesData!!.add(firebaseQueryItem)
 */
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
                }
                try {
                    setChatRecylePopUp()
                } catch (exp: Exception) {
                    Log.e(TAG + "", "call setChatRecyclePopUp excep=" + exp)
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.e(TAG, "getOldChat  :onCancelled", databaseError.toException())

            }
        }
        loggeInRef.addValueEventListener(allUsersListener!!)

    }

    override fun onDestroy() {
        super.onDestroy()
        if(allUsersListener!=null)
        loggeInRef.removeEventListener(allUsersListener!!)

    }

    private fun setChatRecylePopUp() {

        var rv_queries = quriesDialog!!.findViewById(R.id.rv_queries) as RecyclerView
        var iv_close = quriesDialog!!.findViewById(R.id.iv_close) as ImageView
        var tv_noData = quriesDialog!!.findViewById(R.id.tv_noData) as TextView
        //   rv_skills.layoutManager = StickyHeaderLayoutManager()
        rv_queries.setLayoutManager(LinearLayoutManager(ctx!!))
        rv_queries.addItemDecoration(DividerItemDecoration(ctx!!, LinearLayoutManager.VERTICAL))
        rv_queries.setUp(firebaseQueriesData!!, R.layout.qury_item, { it1 ->
            var str = "interesterId=" + it1.interesterId + "\n  interesterName=" + it1.interesterName + "\n ownerId=" + it1.ownerId + "\n petId=" + it1.petId
            tv_ownerName.text = it1.ownerId + " <> " + it1.ownerName
            tv_ownerName.visibility = View.GONE
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

        if (firebaseQueriesData!!.size > 0) {
            tv_noData.visibility = View.GONE
            rv_queries.visibility = View.VISIBLE
        }

        iv_close.setOnClickListener(View.OnClickListener {
            quriesDialog!!.cancel()
        })

        var lWindowParams = WindowManager.LayoutParams();
        lWindowParams.copyFrom(quriesDialog!!.getWindow().getAttributes());
        lWindowParams.width = WindowManager.LayoutParams.FILL_PARENT; // this is where the magic happens
        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        quriesDialog!!.getWindow().setAttributes(lWindowParams);

        if (quriesDialog!!.isShowing) {
            quriesDialog!!.cancel()
        }
        try {
            quriesDialog!!.show()
        } catch (exp: java.lang.Exception) {
            Log.e(TAG, "show queries dialog excep=" + exp.toString())
        }

    }

    // TODO: Rename and change types of parameters
    private var petId: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var petDetailModel: AllApiResponse.PetDetailRes.PetDetail? = null
    var ctx: Context? = null
    var lati = "0"
    var utilities: Utilities? = Utilities()
    var longi = "0"
    internal var service: GetDataService? = null
    var gMap: GoogleMap? = null
    var TAG = "AdvertisementDetailsFrag "
    var quriesDialog: Dialog? = null

    var dbReference = FirebaseDatabase.getInstance().reference
     var allUsersListener: ValueEventListener? = null
    var loggeInRef = dbReference.child("/AllChat")

    var firebaseQueriesData: MutableList<AllApiResponse.FirebaseQueryModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_call.setOnClickListener(this)
        tv_bottomChat.setOnClickListener(this)
        tv_petQueries.setOnClickListener(this)
        fl_AdvertisementDetailsFrag.setOnClickListener(this)
        quriesDialog = Dialog(ctx!!)
        quriesDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        quriesDialog!!.setCancelable(true)
        quriesDialog!!.setContentView(R.layout.popup_queries)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        firebaseQueriesData = mutableListOf()
        utilities = Utilities()
        getPetDetail()
        swipe_refresh.setOnRefreshListener {
            getPetDetail()
        }
    }

    private fun getPetDetail() {
        swipe_refresh.isRefreshing = true
        fl_bottom.visibility = View.GONE
        nsView.visibility = View.GONE

        Log.e(TAG + " getPetDetail", "single-pet/?userID=" + SharedPrefUtil.getUserId(context) + "&petID=" + petId)
        val call = service!!.getPetDetailsApi(SharedPrefUtil.getUserId(context), "" + petId)

        call.enqueue(object : Callback<AllApiResponse.PetDetailRes> {
            override fun onResponse(call: Call<AllApiResponse.PetDetailRes>, response: Response<AllApiResponse.PetDetailRes>) {
                Log.e("testAddPetApi res", "" + Gson().toJson(response.body()))
                swipe_refresh.isRefreshing = false

                if (response.body()!!.status.equals("200") && response.body()!!.data.size > 0) {
                    fl_bottom.visibility = View.VISIBLE
                    nsView.visibility = View.VISIBLE

                    petDetailModel = response.body()!!.data[0]
                    for (item in petDetailModel!!.images) {
                        val sliderView = TextSliderView(context)
                        sliderView.image(item.img).setProgressBarVisible(true)
                        slider.addSlider(sliderView)
                    }

                    if (petDetailModel!!.images.size == 0) {
                        val sliderView = TextSliderView(context)
                        sliderView.image(R.drawable.app_logo).setProgressBarVisible(true)
                        slider.addSlider(sliderView)
                    }
                    tv_advertiseLoc.text = "" + petDetailModel!!.userCity + ", " + petDetailModel!!.userState
                    tv_advertiseDate.text = "" + GlobalData.dateSplit(petDetailModel!!.createdOn)
                    tv_petTitle.text = petDetailModel!!.petTitle
                    tv_advertiseDesc.text = petDetailModel!!.petDescription
                    tv_advertisePrice.text = petDetailModel!!.petPrice
                    tv_owmerName.text = petDetailModel!!.ownerNamer
                    tv_adsId.text = "PetID: " + petDetailModel!!.petID

                    iv_favImg.visibility = View.VISIBLE
                    if (petDetailModel!!.favourite.equals("yes"))
                        iv_favImg.setImageResource(R.drawable.ic_favorite_filled)

                    if (petDetailModel!!.favourite.equals("no"))
                        iv_favImg.setImageResource(R.drawable.ic_favorite_empty)

                    iv_favImg.setOnClickListener {

                        if (petDetailModel!!.favourite.equals("yes")) {
                            if (utilities!!.isConnected(ctx!!))
                                callChangeFavApi("remove")
                            else
                                utilities!!.snackBar(tv_advertiseLoc, "Please check internet ")
                        } else {
                            if (utilities!!.isConnected(ctx!!))
                                callChangeFavApi("add")
                            else
                                utilities!!.snackBar(tv_advertiseLoc, "Please check internet ")
                        }
                    }
                    ll_bottomChatCall.visibility = View.VISIBLE

                    if (petDetailModel!!.ownerID.toString().equals("" + SharedPrefUtil.getUserId(context))) {
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
                        if (!petDetailModel!!.isPrivate.equals(null) && !petDetailModel!!.isPrivate.toLowerCase().equals("yes")) {
                            strDetails = strDetails + "Owner Number: " + petDetailModel!!.ownerNumber + "\n"
                        }
                    } catch (exp: Exception) {
                        Log.e(TAG, "filed values excep=" + exp.toString())
                    }
                    tv_moreFieldsValues.text = "" + strDetails

                } else {
                    GlobalData.showSnackbar(context as Activity, response.body()!!.messageType)

                }
                /*  try {
                      getAllFireBaseQueries()
                  }
                  catch (exp:Exception)
                  {
                      Log.e(TAG," calling getAllFireBaseQueries excep="+exp.toString())
                  }*/
            }

            override fun onFailure(call: Call<AllApiResponse.PetDetailRes>, t: Throwable) {
                // progress_bar.setVisibility(View.GONE);
                swipe_refresh.isRefreshing = false
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun callChangeFavApi(strAction: String) {
        Log.e(TAG, "callChangeFavApi   adsId=" + petId + "   action=" + strAction)
        var dialogBar = utilities!!.dialog(ctx!!)

        val call = service!!.addInFav("" + strAction, "" + SharedPrefUtil.getUserId(ctx!!), "" + petId).enqueue(object : Callback<AllApiResponse.CommonRes> {
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advertisement_details, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
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
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(petOldId: String, param2: String) =
                AdvertisementDetailsFrag().apply {
                    arguments = Bundle().apply {
                        //    petDetailModel=param1
                        putString(ARG_PARAM1, petOldId)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
