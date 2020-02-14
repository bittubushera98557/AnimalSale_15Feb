package com.iww.classifiedolx.Fragments.AddAdvertise

import ImageAndNumberForAdd
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lenovo.emptypro.Activities.MainActivity
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance
import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_choose_location_for_addver.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChooseLocationForAdver : Fragment(), View.OnClickListener {
    var addNewAdvertiseRequestParam: AllApiResponse.AddAdvertiseRequest? = null
     var choosedCityID = 0
    var choosedAreaID = 0
    var TAG = "FieldForAddNewAdvertise "
    var dialog: Dialog? = null
    var cityArrayList: List<AllApiResponse.CityResponse.CityModel>? = null
    internal var service: GetDataService? = null

    // var areaArrayList: List<AllApiResponse.AreaResp.AreaItemData>? = null
    var  utilities= Utilities()
    // TODO: Rename and change types of parameters
    // private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var ctx: Context? = null

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_citySet -> {
                if (cityArrayList != null)
                    showCityPopUp()
                else
                    utilities.snackBar(tv_citySet, "Sorry ! No City Record")
            }
           /* R.id.tv_areaSet -> {
                if (areaArrayList != null)
                    showAreaPopUp()
                else
                    utilities.snackBar(tv_citySet, "try to choose another city")
            }*/
            R.id.tv_next -> {
                if (choosedCityID == 0)
                    utilities.snackBar(tv_citySet, "Choose a city")
                else if (choosedAreaID == 0)
                    utilities.snackBar(tv_citySet, "Choose an area")
                else {
                    addNewAdvertiseRequestParam!!.cityName = "" + tv_citySet.text.toString()
               //     addNewAdvertiseRequestParam!!.cityId = "" + choosedCityID
                    addNewAdvertiseRequestParam!!.areaName = "" + tv_areaSet.text.toString()
                 //   addNewAdvertiseRequestParam!!.areaId = "" + choosedAreaID

                    showHideDialog(false)
                    utilities.enterNextReplaceFragment(
                        R.id.fl_chooseLocation,
                        ImageAndNumberForAdd.newInstance(addNewAdvertiseRequestParam!!, ""),
                        (ctx as MainActivity).supportFragmentManager
                    )
                }
            }
            R.id.fl_chooseLocation->{

            }
        }
    }

    /*private fun showAreaPopUp() {

        val areaList = arrayOfNulls<String>(areaArrayList!!.size)
        for (i in 0..(areaArrayList!!.size - 1)) {
            var areaName: String? = areaArrayList!![i].cityarea
            areaList[i] = "" + areaName
        }
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose an Area")
        if (choosedAreaID == null)
            choosedAreaID = 0
        var tempChecked = choosedAreaID
        builder.setSingleChoiceItems(areaList, 0!!, DialogInterface.OnClickListener { dialog, which ->
            tempChecked = which
        })

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

            try {
                choosedAreaID = areaArrayList!![tempChecked!!].id.toInt()
                tv_areaSet.text = "" + areaArrayList!![tempChecked!!].cityarea
                Log.e(TAG + "choosedAreaID", "" + choosedAreaID)
            } catch (exp: Exception) {

            }
        })
        builder.setNegativeButton("Cancel", null)
// create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }*/

    private fun showCityPopUp() {
        val cityList = arrayOfNulls<String>(cityArrayList!!.size)
        for (i in 0..(cityArrayList!!.size - 1)) {
            var stateName: String? = cityArrayList!![i].city
            cityList[i] = "" + stateName
        }
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose an City")
        if (choosedCityID == null)
            choosedCityID = 0
        var tempChecked = choosedCityID
        builder.setSingleChoiceItems(cityList, 0!!, DialogInterface.OnClickListener { dialog, which ->
            tempChecked = which
        })

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

            try {

                tv_citySet.text = "" + cityArrayList!![tempChecked!!].city
                Log.e(TAG + "choosedCityID", "" + choosedCityID)
             //   fetchAreaListApi("" + choosedCityID)
                choosedAreaID = 0
                tv_areaSet.text = ""
            } catch (exp: Exception) {

            }
        })
        builder.setNegativeButton("Cancel", null)
// create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //      param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_location_for_addver, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = utilities.dialog(ctx!!)
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        tv_citySet.setOnClickListener(this)
        tv_areaSet.setOnClickListener(this)
        tv_next.setOnClickListener(this)
        fl_chooseLocation.setOnClickListener(this)
        fetchCityListApi()
    }

    private fun fetchCityListApi() {
        showHideDialog(true)
        service!!.allCityListApi().enqueue(object : Callback<AllApiResponse.CityResponse> {
            override fun onResponse(
                call: Call<AllApiResponse.CityResponse>,
                response: Response<AllApiResponse.CityResponse>
            ) {
                dialog!!.hide()
                Log.e(TAG + " CityList res", "" + Gson().toJson(response.body()))
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {

                    cityArrayList = response.body()!!.data
               //     fetchAreaListApi(response.body()!!.data[0].cityid)


                } else {
                    utilities.snackBar(tv_next, "" + response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<AllApiResponse.CityResponse>, t: Throwable) {
                t.printStackTrace()
                dialog!!.hide()

            }
        })
    }

    private fun showHideDialog(dialogStatus: Boolean) {
        if (dialogStatus == true && dialog != null) {
            dialog!!.hide()
            dialog!!.show()
        }
        if (dialogStatus == false && dialog != null) {
            dialog!!.hide()
        }
    }

   /* private fun fetchAreaListApi(strCityId: String) {
        showHideDialog(true)
        apiService!!.fetchAreaData("get_area", "" + strCityId).enqueue(object : Callback<AllApiResponse.AreaResp> {
            override fun onResponse(
                call: Call<AllApiResponse.AreaResp>,
                response: Response<AllApiResponse.AreaResp>
            ) {
                dialog!!.hide()
                Log.e(TAG + " fetchArea res", "" + Gson().toJson(response.body()))
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    Log.e(
                        TAG + "fetchArea",
                        "fetchAreaForCity=" + choosedCityID + "  response:-" + Gson().toJson(response.body())
                    )
                    areaArrayList = response.body()!!.data


                } else {
                    Utility.snackBar(tv_next, "" + response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<AllApiResponse.AreaResp>, t: Throwable) {
                t.printStackTrace()
                dialog!!.hide()

            }
        })
    }*/

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
        fun newInstance(param1: AllApiResponse.AddAdvertiseRequest, param2: String) =
            ChooseLocationForAdver().apply {
                arguments = Bundle().apply {
                    addNewAdvertiseRequestParam = param1
                    //        putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
