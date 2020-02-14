package com.example.lenovo.emptypro.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.*
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.lenovo.emptypro.Activities.MainActivity
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance

import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.example.lenovo.emptypro.Utils.GPSTracker
import com.example.lenovo.emptypro.Utils.GlobalData
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import com.facebook.internal.Utility
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.iww.classifiedolx.recyclerview.setUp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_field_for_add_new_advertise.*
import kotlinx.android.synthetic.main.fragment_sub_cate_base_add.*
import kotlinx.android.synthetic.main.fragment_sub_cate_base_add.spinner_city
import kotlinx.android.synthetic.main.item_advertisement.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class SubCateBaseAdd : Fragment() , View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

      if(position>0)
      {

          choosedCity=strArrayList_CityName!!.get(position)
          Log.e(TAG + "choosedCityID", "" + choosedCity)
          tv_subCatFilteredLoc.text = "Choosed City:  " + choosedCity
          fetchAdvertiseList()
      ll_filters.visibility=View.GONE
      }


    }

    override fun onConnectionFailed(p0: ConnectionResult) {
          }

        override fun onConnectionSuspended(p0: Int) {
          }


        override fun onConnected(p0: Bundle?) {
            mLocationRequest = LocationRequest.create()
            mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest!!.interval = (30 * 1000).toLong()
            mLocationRequest!!.fastestInterval = (5 * 1000).toLong()

            val builder = LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest!!)
            builder.setAlwaysShow(true)

            result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build())

            result!!.setResultCallback(ResultCallback { result ->
                val status = result.status
                //final LocationSettingsStates state = result.getLocationSettingsStates();
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS ->
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        //...
                        Log.e(TAG + "aaaaaaaaaa ", "----DONE----")
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        Log.e(TAG + "aaaaaaaaaa ", "----PENDING----")


                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    context as Activity,
                                    REQUEST_LOCATION)
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        }

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.e(TAG + "aaaaaaaaaa ", "----CHANGE----")
                }// Location settings are not satisfied. However, we have no way to fix the
                // settings so we won't show the dialog.
                //...
            })

        }
        override fun onClick(v: View?) {
            when(v!!.id)
            {
                R.id.ll_editLoc->
                {
                    ll_filters.visibility=View.VISIBLE

                }
                R.id.iv_close_filter-> {
                    ll_filters.visibility=View.GONE

                }
                R.id.tv_filterByOtherCity-> {
                   // ll_filters.visibility=View.GONE
                    if (cityArrayList != null) {
                     //   chooseCityPopUp()
                spinner_city.callOnClick()
                    } else {
                        utilities.snackBar(fl_subCateBaseAdd, "check internet or try again")
                    }
                }
                R.id.tv_filterByCurLoc-> {
                    ll_filters.visibility=View.GONE
                    TurnOnGps()
                }

                R.id.ll_filters->
                {

                }
                R.id.tv_resetLoc->
                {
choosedCity=""
                    tv_subCatFilteredLoc.setText("No Choosed Location")

                    fetchAdvertiseList()
                }



            }
        }

        private fun chooseCityPopUp()
        {
            val cityList = arrayOfNulls<String>(cityArrayList!!.size)

            for (i in 0..(cityArrayList!!.size - 1)) {
                var stateName: String? = cityArrayList!![i].city
                cityList[i] = "" + stateName
            }
            val builder = androidx.appcompat.app.AlertDialog.Builder(context!!)
            builder.setTitle("Choose an City")

            var tempChecked = 0
            builder.setSingleChoiceItems(cityList, 0!!, DialogInterface.OnClickListener { dialog, which ->
                tempChecked = which
            })

            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

                try {
                    choosedCity = cityArrayList!![tempChecked!!].city
                    Log.e(TAG + "choosedCityID", "" + choosedCity)
                    tv_subCatFilteredLoc.text = "Choosed City:  " + cityArrayList!![tempChecked!!].city
                    fetchAdvertiseList()
                } catch (exp: Exception) {

                }
            })
            builder.setNegativeButton("Cancel", null)
// create and show the alert dialog
            val dialog = builder.create()
            dialog.show()


        }

        // TODO: Rename and change types of parameters
        private var mainCatId: String? = null
        private var subCatId: String? = null
        private var listener: OnFragmentInteractionListener? = null
        var ctx: Context? = null
        var utilities = Utilities()
        private var advertiseItemData: MutableList<AllApiResponse.FilterBasePetsRes.FilterBasePetsModel>? = null
        var cityArrayList: List<AllApiResponse.CityResponse.CityModel>? = null
        internal var mGoogleApiClient: GoogleApiClient? = null
        internal var mLocationRequest: LocationRequest? = null
        internal val REQUEST_LOCATION = 199
        internal var gpsTracker: GPSTracker? = null
        internal var result: PendingResult<LocationSettingsResult>? = null
        internal var lattitude = 0.0
        internal var longitude = 0.0
        var choosedCity=""
    var cityadp: ArrayAdapter<String>?=null
    var    strArrayList_CityName : ArrayList<String>?=null;

        var TAG = "SubCateBaseAdd "
        internal var service: GetDataService? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {
                mainCatId = it.getString(ARG_PARAM1)
                subCatId = it.getString(ARG_PARAM2)
  choosedCity          = it.getString(ARG_PARAM3)}
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_sub_cate_base_add, container, false)
        }

        fun onButtonPressed(uri: Uri) {
            listener?.onFragmentInteraction(uri)
        }

        @SuppressLint("WrongConstant")
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            advertiseItemData = mutableListOf()



            service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
            rv_adverLst.setHasFixedSize(true)
            val mLayoutManager = LinearLayoutManager(context)
            mLayoutManager.orientation = VERTICAL
            rv_adverLst.layoutManager = mLayoutManager

            rv_adverLst.setUp(advertiseItemData!!, R.layout.item_advertisement, { it1 ->

                val circularProgressDrawable = CircularProgressDrawable(ctx as Activity)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()
                Log.e(TAG + "", "pet  name=" + it1.petName + "  " + it1.category)
                if (it1.images.size > 0)
                    Picasso.with(context).load(it1.images[0].img).fit().placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(iv_advertiseImg)



                tv_advertisePrice.text = "Rs. " + it1.petPrice

                var strInfo = ""
                if (!it1.petTitle.equals("") && !it1.petTitle.equals("null"))
                    strInfo = strInfo + " | " + it1.petTitle
                if (!it1.category.equals("") && !it1.category.equals("null"))
                    strInfo = strInfo + " | " + it1.category
                if (!it1.subCategory.equals("") && !it1.subCategory.equals("null"))
                    strInfo = strInfo + " | " + it1.subCategory

                this.tv_advertiseDesc.text = strInfo.substring(2)
                tv_advertiseLoc.text = it1.userCity + ", " + it1.userState
                tv_advertiseDate.text = GlobalData.dateSplit(it1.createdOn)
                iv_favImg.visibility=View.VISIBLE

                if (it1!!.ownerID.toString().equals("" + SharedPrefUtil.getUserId(context))) {
                    iv_favImg.visibility=View.GONE

                }

                iv_favImg.setOnClickListener {
                    if (it1.favourite.equals("no")) {

                        if (utilities.isConnected(ctx!!))
                            callChangeFavApi("add", it1)
                        else
                            utilities.snackBar(rv_adverLst, "Please check internet ")

                    } else {
                        if (utilities.isConnected(ctx!!))
                            callChangeFavApi("remove", it1)
                        else
                            utilities.snackBar(rv_adverLst, "Please check internet ")

                    }
                }

                if (it1.favourite.equals("yes")) {
                    iv_favImg.setImageResource(R.drawable.ic_favorite_filled)
                } else {
                    iv_favImg.setImageResource(R.drawable.ic_favorite_empty)

                }


                this.ll_advertItemLinear.setOnClickListener {
                    utilities.enterNextReplaceFragment(
                            R.id.fl_subCateBaseAdd,
                            AdvertisementDetailsFrag.newInstance("" + it1.petID, ""),
                            (ctx as MainActivity).supportFragmentManager
                    )
                }
            }, { view1: View, i: Int -> })

            if (utilities.isConnected(ctx!!)) {
                getAllCities()
                fetchAdvertiseList()
            }  else
                utilities.snackBar(rv_adverLst, "Please check internet ")


            rv_adverLst.layoutManager = LinearLayoutManager(context)

            swipe_refresh.setOnRefreshListener {
                swipe_refresh.isRefreshing=false
                if (utilities.isConnected(ctx!!)) {
                    fetchAdvertiseList()
                    getAllCities()
                }
                else
                    utilities.snackBar(rv_adverLst, "Please check internet ")
            }

            ll_editLoc.setOnClickListener(this)
            ll_filters.setOnClickListener(this)
            tv_filterByCurLoc.setOnClickListener(this)
            tv_filterByOtherCity.setOnClickListener(this)
            iv_close_filter.setOnClickListener(this)
            tv_resetLoc.setOnClickListener(this)
            gpsTracker = GPSTracker(context)

            gpsTracker!!.getLocation()
         //   TurnOnGps()
        }
        private fun TurnOnGps() {
            val gpsTracker = GPSTracker(context)

            if (gpsTracker.isGPSTrackingEnabled) {

                val loc = gpsTracker.location

                if (loc != null) {

                    lattitude = loc.latitude
                    longitude = loc.longitude

                    val stringLatitude = java.lang.Double.toString(loc.latitude)

                    val stringLongitude = java.lang.Double.toString(loc.longitude)

                    Log.e("current_locationQQ", "--\n LAT $stringLatitude\nLONG $stringLongitude")

                    if (stringLatitude.equals("0.0", ignoreCase = true) || stringLongitude.equals("0.0", ignoreCase = true)) {

                        if (checkLocationPermission()) {
                            Toast.makeText(context, "Turn ON GPS", Toast.LENGTH_LONG).show()
                        } else {
                            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)

                        }

                    } else {
                        val results = FloatArray(1)

                        lattitude = java.lang.Double.parseDouble(stringLatitude)
                        longitude = java.lang.Double.parseDouble(stringLongitude)
                        /*  Location.distanceBetween(30.740597, 76.782068, lattitude, longitude, results)
                          val distanceInMeters = results[0]
                          val isWithinRange = distanceInMeters < 20000
                          Log.e("isWithinRange ", "" + isWithinRange)
                          if (isWithinRange == true) {

                          } else {
                              utilities.snackBar(tv_homeCurLoc, "Services are available for chandigarh.")

                          }*/

                        val geocoder = Geocoder(
                                context, Locale
                                .getDefault())
                        val addresses: List<Address>
                        try {
                            Log.v("vvvvvvvvvvvvvv", "latitude$stringLatitude")
                            Log.v("vvvvvvvvvvvvvv", "longitude$stringLongitude")

                            addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                            Log.e("vvvvvvvvvvvvvv", "addresses+)_+++$addresses")

                            val address = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            val city = addresses[0].locality
                            val state = addresses[0].adminArea
                            val country = addresses[0].countryName
                            val postalCode = addresses[0].postalCode
                            val knownName = addresses[0].featureName
                            choosedCity = city
                            tv_subCatFilteredLoc.setText("Current Location : $choosedCity")
                            fetchAdvertiseList()
                            Log.e(TAG + "userCurCity", choosedCity)
                            Log.e(TAG + "current_location ", "-LAT $stringLatitude   LONG $stringLongitude  CITY=$choosedCity  ADDRESS=$address")

                            // handler.removeCallbacks(runnable);
                        } catch (e: IOException) {
                            // TODO Auto-generated catch block
                            e.printStackTrace()
                        }

                        //   Prob_auto.setVisibility(View.GONE);
                        //   clear.setVisibility(View.VISIBLE);
                    }
                } else {

                    // Toast.makeText(NavigationScreen.this,"Turn ON GPS", Toast.LENGTH_LONG).show();
                    //  load = 0;
                    //  GPS_FETCHING();
                }
            } else {

                mGoogleApiClient = GoogleApiClient.Builder(context!!)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this).build()
                mGoogleApiClient!!.connect()

            }
            //  Toast.makeText(NavigationScreen.this,"Turn ON GPS", Toast.LENGTH_LONG).show();

        }
        fun checkLocationPermission(): Boolean {
            val permission = "android.permission.ACCESS_FINE_LOCATION"
            val res = context!!.checkCallingOrSelfPermission(permission)
            return res == PackageManager.PERMISSION_GRANTED
        }

        private fun callChangeFavApi(strAction: String, dataModel: AllApiResponse.FilterBasePetsRes.FilterBasePetsModel?) {
            Log.e(TAG, "callChangeFavApi   adsId=" + dataModel!!.petID + "   action=" + strAction)
            var dialogBar=utilities.dialog(ctx!!)

            val call = service!!.addInFav("" + strAction, "" + SharedPrefUtil.getUserId(ctx), "" + dataModel!!.petID).enqueue(object : Callback<AllApiResponse.CommonRes> {
                override fun onResponse(
                        call: Call<AllApiResponse.CommonRes>,
                        response: Response<AllApiResponse.CommonRes>
                ) {
                    dialogBar.cancel()
                    Log.e("callAddInFavApi res", "" + Gson().toJson(response.body()))
                    if (response.isSuccessful && (response.body()!!.status.equals("200"))) {
                        if (strAction.equals("add"))
                            dataModel!!.favourite = "yes"
                        if (strAction.equals("remove"))
                            dataModel!!.favourite = "no"

                        rv_adverLst.adapter!!.notifyDataSetChanged()
                    } else {
                        //swipe_refresh.isRefreshing = false
                    }
                }

                override fun onFailure(call: Call<AllApiResponse.CommonRes>, t: Throwable) {
                    dialogBar.cancel()
                    t.printStackTrace()
                    //swipe_refresh.isRefreshing = false
                }
            })

        }

        private fun getAllCities() {
            Log.e(TAG,"getAllCities   city" )
            var dialogBar=utilities.dialog(context!!)
            strArrayList_CityName= ArrayList()
            strArrayList_CityName!!.clear()
            service!!.allCityListApi( ).enqueue(object : Callback<AllApiResponse.CityResponse> {
                override fun onResponse(
                        call: Call<AllApiResponse.CityResponse>,
                        response: Response<AllApiResponse.CityResponse>
                ) {
                    dialogBar.cancel()
                    Log.e("getAllCities res", "" + Gson().toJson(response.body()))
                    if (response.isSuccessful && (response.body()!!.status.equals("200"))) {

                        cityArrayList = response.body()!!.data
                        strArrayList_CityName!!.add("Choose a City")
                        for (i in 0..(cityArrayList!!.size- 1)) {
                            strArrayList_CityName!!.add(""+ response.body()!!.data[i].city)
                        }

                        try{
                            cityadp = ArrayAdapter<String>(ctx!!, R.layout.textview, strArrayList_CityName)


                            spinner_city.setOnItemSelectedListener(this@SubCateBaseAdd);
                            // Drop down layout style - list view with radio button
                            cityadp!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cityadp!!.setDropDownViewResource(R.layout.textview);
                            spinner_city.setAdapter(cityadp);

                        } catch ( e:Exception) {
                            Log.e(TAG,"searchable adapter excep="+e.toString())
                            e.printStackTrace();
                        }

                    } else {
                        //swipe_refresh.isRefreshing = false

                        utilities.snackBar(tv_next, "" + response.body()!!.message)

                    }
                }

                override fun onFailure(call: Call<AllApiResponse.CityResponse>, t: Throwable) {
                    t.printStackTrace()
                    dialogBar.cancel()

                    //swipe_refresh.isRefreshing = false
                }
            })



        }

        private fun fetchAdvertiseList() {
            swipe_refresh.isRefreshing = true
            service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
            Log.e(TAG + "fetchAdvertiseList res", "filter-data/?userID=" + SharedPrefUtil.getUserId(ctx) + "&cityName="+choosedCity+"&catId=" + mainCatId + "&subCat=" + subCatId)

            service!!.getFilterBaseApi("" + SharedPrefUtil.getUserId(ctx), ""+choosedCity, "" + mainCatId, "" + subCatId).enqueue(object : Callback<AllApiResponse.FilterBasePetsRes> {
                override fun onResponse(
                        call: Call<AllApiResponse.FilterBasePetsRes>,
                        response: Response<AllApiResponse.FilterBasePetsRes>
                ) {
                    Log.e("fetchAdvertiseList res", "" + Gson().toJson(response.body()))
                    if (response.isSuccessful && (response.body()!!.status.equals("200")) && (response.body()!!.data.size>0)) {
                        advertiseItemData!!.clear()
                        advertiseItemData!!.addAll(response.body()!!.data)
                        rv_adverLst.adapter!!.notifyDataSetChanged()
                        tvNoData.visibility = View.GONE
                        rv_adverLst.visibility = View.VISIBLE
                    } else {
                        tvNoData.visibility = View.VISIBLE
                        rv_adverLst.visibility = View.GONE
                    }
                    swipe_refresh.isRefreshing = false
                }

                override fun onFailure(call: Call<AllApiResponse.FilterBasePetsRes>, t: Throwable) {
                    t.printStackTrace()
                    tvNoData.visibility = View.VISIBLE
                    rv_adverLst.visibility = View.GONE
                    swipe_refresh.isRefreshing = false
                }
            })
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
        fun newInstance(param1: String, param2: String, param3: String) =
                SubCateBaseAdd().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                        putString(ARG_PARAM3, param3)
                    }
                }
    }
}
