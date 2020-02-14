package com.example.lenovo.emptypro.Fragments

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance

import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.Activities.MainActivity
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.example.lenovo.emptypro.Utils.GPSTracker
import com.example.lenovo.emptypro.Utils.GlobalData
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.iww.classifiedolx.recyclerview.setUp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_searched_item.view.*
import kotlinx.android.synthetic.main.item_top_cate.view.*
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragKotlin : Fragment(), View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
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
   R.id.iv_editLoc->
   {
       if (cityArrayList != null) {
           chooseCityPopUp()
       } else {
           utilities.snackBar(tv_homeCurLoc, "check internet or try again")
       }
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
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Choose an City")
            var tempChecked = 0
            builder.setSingleChoiceItems(cityList, 0!!, DialogInterface.OnClickListener { dialog, which ->
                tempChecked = which
            })

            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

                try {
                    choosedCity = cityArrayList!![tempChecked!!].city
                    Log.e(TAG + "choosedCityID", "" + choosedCity)
                    tv_homeFilteredLoc.text = "" + cityArrayList!![tempChecked!!].city
                    getAllTypePets(""+choosedCity)
                } catch (exp: Exception) {

                }
            })
            builder.setNegativeButton("Cancel", null)
// create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var mainCatItemData: MutableList<AllApiResponse.CategoryResponse.CategoryMainListModel>? = null
    private var allPetsItemData: MutableList<AllApiResponse.AllTypePetsRes.AllTypePetsItem>? = null
    var ctx: Context? = null
    internal var service: GetDataService? = null
    var TAG = "HomeFragKotlin "
    internal var mGoogleApiClient: GoogleApiClient? = null
    internal var mLocationRequest: LocationRequest? = null
    internal val REQUEST_LOCATION = 199
    internal var gpsTracker: GPSTracker? = null
    internal var result: PendingResult<LocationSettingsResult>? = null
    var cityArrayList: List<AllApiResponse.CityResponse.CityModel>? = null
    internal var lattitude = 0.0
    internal var longitude = 0.0
    var choosedCity=""
    private var userCurCity = ""
var utilities:Utilities= Utilities()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_editLoc.setOnClickListener(this)
        tv_map_cancel.setOnClickListener(this)
        tv_map_changedLoc.setOnClickListener(this)
        tv_map_confirm.setOnClickListener(this)
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        mainCatItemData = mutableListOf()
        allPetsItemData = mutableListOf()
        gpsTracker = GPSTracker(context)

        gpsTracker!!.getLocation()
        TurnOnGps()

        rv_topMainCate.setHasFixedSize(true);
        rv_topMainCate.layoutManager = GridLayoutManager(context, 2)
        rv_searchedCate.setHasFixedSize(true);
        rv_searchedCate.layoutManager = GridLayoutManager(context, 2)

        rv_topMainCate.setUp(mainCatItemData!!, R.layout.item_top_cate, { it1 ->
            this.tv_mainCatTxt.text = it1.title
            val circularProgressDrawable = CircularProgressDrawable(ctx as Activity)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Log.e(TAG + "",  "rv_topMainCate   title="+it1.title+ "    img=" + it1.imgUrl)
            Picasso.with(context).load("" + it1.imgUrl).placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(this.iv_mainCatImg)


            if (ll_mainCat != null) {
                val display = (context as Activity).windowManager.defaultDisplay
                ll_mainCat.getLayoutParams().width = Math.round((display.width / 3).toFloat())
            }
            this.ll_mainCat.setOnClickListener {
              /*  if (SharedPrefUtil.getUserFirstName(ctx!!).replace(" ".toRegex(),"").equals(""))
                {
                    utilities.snackBar(rv_topMainCate,"Please Firstly update your profile")
                }
                else {*/
                    var utilities = Utilities()
                    utilities.enterNextReplaceFragment(R.id.ll_homeFragment, SubCatFrag.newInstance(it1.subCat, "" + it1.categoryID), (ctx as MainActivity).supportFragmentManager)
               // }
            }
        }, { view1: View, i: Int -> })
        rv_searchedCate.setUp(allPetsItemData!!, R.layout.home_searched_item, { it1 ->
            this.tv_searchedItemShortInfo.text = "Rs. " + it1.petPrice

var strInfo=""
            if(!it1.petTitle.equals("") && !it1.petTitle.equals("null")  )
                strInfo=strInfo+" | "+it1.petTitle
            if(!it1.category.equals("") && !it1.category.equals("null")  )
                strInfo=strInfo+" | "+it1.category
            if(!it1.subCategory.equals("") && !it1.subCategory.equals("null")  )
                strInfo=strInfo+" | "+it1.subCategory

            this.tv_searchedItemLongInfo.text =  strInfo.substring(2)
            tv_searchedItemLoc.text=it1.userCity+", "+it1.userState
            tv_searchedItemDate.text=GlobalData.dateSplit(it1.createdOn)

            val circularProgressDrawable = CircularProgressDrawable(ctx as Activity)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
   if( it1.images.size>0) {
       Picasso.with(context).load("" + it1.images[0].img).fit().placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(iv_searchedItemImg)
       Log.e(TAG + "", "rv_searchedCate=" + it1.images[0].img)

   }
            if (it1!!.ownerID.toString().equals("" + SharedPrefUtil.getUserId(context))) {
                iv_favImg.visibility=View.GONE

            }
            if(it1.favourite.equals("yes"))
                iv_favImg.setImageResource(R.drawable.ic_favorite_filled)

            if(it1.favourite.equals("no"))
                iv_favImg.setImageResource(R.drawable.ic_favorite_empty)

            iv_favImg.setOnClickListener{
/*if (SharedPrefUtil.getUserFirstName(ctx!!).replace(" ".toRegex(),"").equals(""))
{
 utilities.snackBar(rv_topMainCate,"Please Firstly update your profile")
}
    else {*/
    if (it1.favourite.equals("yes")) {
        if (utilities.isConnected(ctx!!))
            callChangeFavApi("remove", it1)
        else
            utilities.snackBar(tv_homeCurLoc, "Please check internet ")
    } else {
        if (utilities.isConnected(ctx!!))
            callChangeFavApi("add", it1)
        else
            utilities.snackBar(tv_homeCurLoc, "Please check internet ")

   // }
}  }
            if (ll_searchedItem != null) {
                val display = (context as Activity).windowManager.defaultDisplay
                ll_searchedItem.getLayoutParams().width = Math.round((display.width / 2).toFloat())
            }
            this.ll_searchedItem.setOnClickListener {
             /*   if (SharedPrefUtil.getUserFirstName(ctx!!).replace(" ".toRegex(),"").equals(""))
                {
                    utilities.snackBar(rv_topMainCate,"Please Firstly update your profile")
                }
                else {*/
                    utilities.enterNextReplaceFragment(
                            R.id.ll_homeFragment,
                            AdvertisementDetailsFrag.newInstance("" + it1.petID, ""),
                            (ctx as MainActivity).supportFragmentManager
                    )
                //}
             }
        }, { view1: View, i: Int -> })


        if (GlobalData.isConnectedToInternet(activity)) {
            getMainCateApi()
            getAllTypePets("")
        } else {
            GlobalData.showSnackbar(activity!!, getString(R.string.please_check_your_internet_connection))
        }
        getAllCities()
       // testAddPetApi()
        rv_topMainCate.layoutManager = GridLayoutManager(context, 3)
        rv_searchedCate.layoutManager = GridLayoutManager(context, 2)
        ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

/*
    private fun testAddPetApi() {
      val call = service!!.addNewPetWithOutImgApi("Dog" ,"desi","jacky","Black white Dog","German", "3.5","300","test from app","yes","","","Mohali","Punjab",""+SharedPrefUtil.getUserId(context)).enqueue(object : Callback<AllApiResponse.CommonRes> {
            override fun onResponse(
                    call: Call<AllApiResponse.CommonRes>,
                    response: Response<AllApiResponse.CommonRes>
            ) {
                Log.e("testAddPetApi res", "" + Gson().toJson(response.body()))

            }

            override fun onFailure(call: Call<AllApiResponse.CommonRes>, t: Throwable) {
                t.printStackTrace()
                 }
        })

    }
*/

    private fun getAllCities() {
        Log.e(TAG,"getAllCities   city" )
        var dialogBar=utilities.dialog(context!!)

          service!!.allCityListApi( ).enqueue(object : Callback<AllApiResponse.CityResponse> {
            override fun onResponse(
                    call: Call<AllApiResponse.CityResponse>,
                    response: Response<AllApiResponse.CityResponse>
            ) {
                dialogBar.cancel()
                Log.e("getAllCities res", "" + Gson().toJson(response.body()))
                if (response.isSuccessful && (response.body()!!.status.equals("200"))) {

                    cityArrayList = response.body()!!.data

                } else {
                    //swipe_refresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<AllApiResponse.CityResponse>, t: Throwable) {
                t.printStackTrace()
                dialogBar.cancel()

                //swipe_refresh.isRefreshing = false
            }
        })



    }

    private fun callChangeFavApi(strAction: String, dataItem: AllApiResponse.AllTypePetsRes.AllTypePetsItem) {
        Log.e(TAG,"callChangeFavApi   adsId="+dataItem!!.petID+"   action="+strAction)
        var dialogBar=utilities.dialog(context!!)

        val call = service!!.addInFav(""+strAction ,""+ SharedPrefUtil.getUserId(ctx),""+ dataItem!!.petID).enqueue(object : Callback<AllApiResponse.CommonRes> {
            override fun onResponse(
                    call: Call<AllApiResponse.CommonRes>,
                    response: Response<AllApiResponse.CommonRes>
            ) {
                Log.e("callAddInFavApi res", "" + Gson().toJson(response.body()))
                dialogBar.cancel()
                if (response.isSuccessful && (response.body()!!.status.equals("200"))) {
                    if(strAction.equals("add"))
                        dataItem!!.favourite="yes"
                    if(strAction.equals("remove"))
                        dataItem!!.favourite="no"

                     rv_searchedCate.adapter!!.notifyDataSetChanged()
                } else {
                    //swipe_refresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<AllApiResponse.CommonRes>, t: Throwable) {
                t.printStackTrace()
                dialogBar.cancel()

                //swipe_refresh.isRefreshing = false
            }
        })


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
                        userCurCity = city
                        tv_homeCurLoc.setText("Current Location : $userCurCity")
                        tv_homeFilteredLoc.setText(" $userCurCity")

                        Log.e(TAG + "userCurCity", userCurCity)
                        Log.e(TAG + "current_location ", "-LAT $stringLatitude   LONG $stringLongitude  CITY=$userCurCity  ADDRESS=$address")

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


    private fun getMainCateApi() {
        val call = service!!.allCateListApi()

        call.enqueue(object : Callback<AllApiResponse.CategoryResponse> {
            override fun onResponse(call: Call<AllApiResponse.CategoryResponse>, response: Response<AllApiResponse.CategoryResponse>) {
                Log.e(TAG + " getMainCateApi", "" + Gson().toJson(response.body()))
                mainCatItemData!!.clear()
                mainCatItemData!!.addAll(response.body()!!.data)

                rv_topMainCate.adapter!!.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<AllApiResponse.CategoryResponse>, t: Throwable) {
                // progress_bar.setVisibility(View.GONE);
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun getAllTypePets(strCity:String) {
        Log.e(TAG + " getAllTypePets", "all-pets/?cityName="+strCity+" & userID="+SharedPrefUtil.getUserId(context))
        allPetsItemData!!.clear()

        val call = service!!.allTypePetsListApi(""+strCity,""+SharedPrefUtil.getUserId(context))

        call.enqueue(object : Callback<AllApiResponse.AllTypePetsRes> {
            override fun onResponse(call: Call<AllApiResponse.AllTypePetsRes>, response: Response<AllApiResponse.AllTypePetsRes>) {
                Log.e(TAG + " getAllTypePets", "response "+ Gson().toJson(response.body()));
                if (response.body()!!.status.equals("200")) {
                    allPetsItemData!!.addAll(response.body()!!.data)
                    Log.e(TAG + " getAllTypePets", "size=" + response.body()!!.data.size)

                }
                rv_searchedCate.adapter!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<AllApiResponse.AllTypePetsRes>, t: Throwable) {
                // progress_bar.setVisibility(View.GONE);
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
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
        fun newInstance(param1: String, param2: String) =
                HomeFragKotlin().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
