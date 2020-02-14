package com.iww.classifiedolx.Fragments.AddAdvertise

import ImageAndNumberForAdd
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.lenovo.emptypro.Activities.MainActivity
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance
import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_field_for_add_new_advertise.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FieldForAddNewAdvertise : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
if(position>0)
{
    strChoosedCityName=arrayList_CityName!!.get(position)
}
        else{
    utilities.snackBar(txtInptLyt_Title,"Please choose City Name")
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_next -> {

                checkEditFieldValues()
                /*    utilities.enterNextReplaceFragment(
                            R.id.fl_fieldsFrag,
                            ChooseLocationForAdver.newInstance(addNewAdvertiseRequestParam!!,""),
                            (ctx as MainActivity).supportFragmentManager)*/
            }
            R.id.fl_fieldsFrag -> {


            }
            R.id.chkBox_dollar -> {
                chkBox_dollar.isChecked = true
                chkBox_dollar.setTextColor(R.color.colorAccent)
                chkBox_ruppess.isChecked = false
                chkBox_ruppess.setTextColor(R.color.colorBlack)
                strCurrency="yes"
  }
            R.id.chkBox_ruppess-> {
                chkBox_ruppess.isChecked = true
                chkBox_ruppess.setTextColor(R.color.colorAccent)
                chkBox_dollar.isChecked = false
                chkBox_dollar.setTextColor(R.color.colorBlack)
                strCurrency="no"

            }
            R.id.fl_city->
            {
                if (cityArrayList != null) {
                   // chooseCityPopUp()


                } else {
                    utilities.snackBar(txtInptLyt_Title, "check internet or try again")
fetchCityListApi()
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
                    strChoosedCityName = cityArrayList!![tempChecked!!].city

                    Log.e(TAG + "strChoosedCityName", "" + strChoosedCityName)
        //            tv_citySet.text = "" + cityArrayList!![tempChecked!!].city

                } catch (exp: Exception) {

                }
            })
            builder.setNegativeButton("Cancel", null)
// create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
  }


    private fun checkEditFieldValues() {

        var strPetName = et_PetName.text.toString()
        var strTitle = et_Title.text.toString()
        var strPrice = et_Price.text.toString()
        var strPetType = et_petType.text.toString()
        var strBreed = et_breed.text.toString()
        var strAge = et_age.text.toString()
        var strDesc = et_desc.text.toString()
        var strArea= et_area.text.toString()

       /* if (txtInptLyt_PetName.visibility == View.VISIBLE && (TextUtils.isEmpty(et_PetName.text.toString().replace(" ".toRegex(), "")))) {
            focusOnEditTxtView(et_PetName, "Enter Pet Name")
            fiedlValueChk = false
        } else*/ if (txtInptLyt_Title.visibility == View.VISIBLE && (TextUtils.isEmpty(et_Title.text.toString().replace(" ".toRegex(), "")))) {
             focusOnEditTxtView(et_Title, "Enter Title")
        } else if (ll_Price.visibility == View.VISIBLE && (TextUtils.isEmpty(et_Price.text.toString().replace(" ".toRegex(), "")))) {
             focusOnEditTxtView(et_Price, "Enter Price")
        }
        else if (ll_Price.visibility == View.VISIBLE && strCurrency.equals("")) {
      utilities.snackBar(fl_fieldsFrag,"Choose a currency type")
        }
            else  if ((addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals("others")) && txtInptLyt_petType.visibility == View.VISIBLE && (TextUtils.isEmpty(strPetType.replace(" ".toRegex(), "")))) {

            focusOnEditTxtView(et_petType, "Enter Pet Type")

        } else if ( (addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals("dog")  || addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals("cat")  ||addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals("bird")     )&&  (txtInptLyt_breed.visibility == View.VISIBLE) && (TextUtils.isEmpty(
                        strBreed.replace(
                                " ".toRegex(),
                                ""
                        )
                ))
        ) {
            focusOnEditTxtView(et_breed, "Enter Pet Breed")
        }
        /*else
            if (txtInptLyt_age.visibility == View.VISIBLE && (TextUtils.isEmpty(
                        strAge.replace(
                                " ".toRegex(),
                                ""
                        )
                ))
        ) {
            focusOnEditTxtView(et_age, "Enter Age value")
 }*/
            else
                if (txtInptLyt_desc.visibility == View.VISIBLE && (TextUtils.isEmpty(
                        strDesc.replace(
                                " ".toRegex(),
                                ""
                        )
                ))
        ) {
            focusOnEditTxtView(et_desc, "Enter Desc value")
         }
                else
                    if (TextUtils.isEmpty(
                                    strArea.replace(
                                            " ".toRegex(),
                                            ""
                                    )
                            )
                    ) {
                        focusOnEditTxtView(et_area, "Enter Location State")
                     }
                else if(strChoosedCityName.equals("") || strChoosedCityName.equals("Choose a City"))
                {
                 //   tv_citySet.setTextColor(Color.RED)
                    utilities.snackBar(fl_fieldsFrag,"Choose city")
                }
else  {
            addNewAdvertiseRequestParam!!.petName = strPetName
            addNewAdvertiseRequestParam!!.petTitle = strTitle
            addNewAdvertiseRequestParam!!.petPrice = strPrice
            addNewAdvertiseRequestParam!!.petType = strPetType
            addNewAdvertiseRequestParam!!.petBreed = strBreed
            addNewAdvertiseRequestParam!!.petAge = strAge
            addNewAdvertiseRequestParam!!.petDesc = strDesc
                        addNewAdvertiseRequestParam!!.priceCurrency= strCurrency
                        addNewAdvertiseRequestParam!!.cityName= strChoosedCityName
                        addNewAdvertiseRequestParam!!.areaName= strArea


                        utilities.enterNextReplaceFragment(
                    R.id.fl_fieldsFrag,
                    ImageAndNumberForAdd.newInstance(addNewAdvertiseRequestParam!!, ""),
                    (ctx as MainActivity).supportFragmentManager
            )
        }


    }

    private fun focusOnEditTxtView(etView: EditText, strMsg: String) {
        scrVw_fields.post(Runnable {
            scrVw_fields.scrollTo(0, etView.top)
            etView.isFocusable = true
            etView.error = "" + strMsg

            //  txtInptLyt_email.error=" txtInptLyt Field Required"

        })
    }


    private var mainCatId: String? = null
    private var subCatId: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var ctx: Context? = null
    var TAG = "FieldForAddNewAdvertise "
    var utilities = Utilities()
    internal var service: GetDataService? = null
    var cityArrayList: List<AllApiResponse.CityResponse.CityModel>? = null
    var strChoosedCityName=""
var    arrayList_CityName : ArrayList<String>?=null;
    var strCurrency=""
    var addNewAdvertiseRequestParam: AllApiResponse.AddAdvertiseRequest? = null
   var cityadp:ArrayAdapter<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mainCatId = addNewAdvertiseRequestParam!!.CatId
            subCatId = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //   dialog = utilities.dialog(ctx!!)
        //    fetchAllSubCat("get_subcategory")
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        tv_next.setOnClickListener(this)
        fl_fieldsFrag.setOnClickListener(this)
        chkBox_dollar.setOnClickListener(this)
        chkBox_ruppess.setOnClickListener(this)
        fl_city.setOnClickListener(this)

        tv_catName.text = addNewAdvertiseRequestParam!!.CatName + "    >    " + addNewAdvertiseRequestParam!!.SubCatName
        et_breed.setText(""+addNewAdvertiseRequestParam!!.SubCatName)
        if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals("dog"))
        {
             txtInptLyt_PetName.visibility=View.VISIBLE
            txtInptLyt_Title.visibility=View.VISIBLE
            ll_Price.visibility=View.VISIBLE
            txtInptLyt_breed.visibility=View.VISIBLE
            txtInptLyt_desc.visibility=View.VISIBLE
            txtInptLyt_age.visibility=View.VISIBLE
        }
        else
            if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals("cat"))
            {
                txtInptLyt_PetName.visibility=View.VISIBLE
                txtInptLyt_Title.visibility=View.VISIBLE
                 ll_Price.visibility=View.VISIBLE
                txtInptLyt_breed.visibility=View.VISIBLE
                txtInptLyt_desc.visibility=View.VISIBLE
                    txtInptLyt_age.visibility=View.VISIBLE
            } else
                if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals("bird"))
                {
                    txtInptLyt_PetName.visibility=View.VISIBLE
                    txtInptLyt_Title.visibility=View.VISIBLE
                     ll_Price.visibility=View.VISIBLE
                    txtInptLyt_desc.visibility=View.VISIBLE
                     txtInptLyt_breed.visibility=View.VISIBLE
                   //  txtInptLyt_age.visibility=View.VISIBLE
                } else
                    if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals("cow"))
                    {
                    //    txtInptLyt_PetName.visibility=View.VISIBLE
                        txtInptLyt_Title.visibility=View.VISIBLE
                         ll_Price.visibility=View.VISIBLE
                        txtInptLyt_desc.visibility=View.VISIBLE
                            txtInptLyt_breed.visibility=View.VISIBLE
                         txtInptLyt_age.visibility=View.VISIBLE
                    } else
                        if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals("buffalo"))
                        {
                         //   txtInptLyt_PetName.visibility=View.VISIBLE
                            txtInptLyt_Title.visibility=View.VISIBLE
                             ll_Price.visibility=View.VISIBLE
                            txtInptLyt_desc.visibility=View.VISIBLE
                             txtInptLyt_petType.visibility=View.VISIBLE
                              txtInptLyt_breed.visibility=View.VISIBLE
                            txtInptLyt_age.visibility=View.VISIBLE
                        }
                        else
                            if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals("others"))
                            {
                                txtInptLyt_PetName.visibility=View.VISIBLE
                                txtInptLyt_Title.visibility=View.VISIBLE
                                ll_Price.visibility=View.VISIBLE
                               txtInptLyt_petType.visibility=View.VISIBLE
                                txtInptLyt_breed.visibility=View.VISIBLE
                                txtInptLyt_age.visibility=View.VISIBLE
                                txtInptLyt_desc.visibility=View.VISIBLE
                            }
        fetchCityListApi()
    }
    private fun fetchCityListApi() {
        var dialog: Dialog? = utilities.dialog(ctx!!)
        arrayList_CityName= ArrayList()
        arrayList_CityName!!.clear()
        service!!.allCityListApi().enqueue(object : Callback<AllApiResponse.CityResponse> {
            override fun onResponse(
                    call: Call<AllApiResponse.CityResponse>,
                    response: Response<AllApiResponse.CityResponse>
            ) {
                dialog!!.cancel()
                Log.e(TAG + " CityList res", "" + Gson().toJson(response.body()))
                if (response.isSuccessful && (response.body()!!.status.equals("200"))) {

                    cityArrayList = response.body()!!.data
                    arrayList_CityName!!.add("Choose a City")
                    for (i in 0..(cityArrayList!!.size- 1)) {
                        arrayList_CityName!!.add(""+ response.body()!!.data[i].city)
                    }

try{
    cityadp = ArrayAdapter<String>(ctx!!, R.layout.textview, arrayList_CityName)


    spinner_city.setOnItemSelectedListener(this@FieldForAddNewAdvertise);
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
                dialog!!.cancel()

            }
        })
    }




    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_field_for_add_new_advertise, container, false)


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
        fun newInstance(param1: AllApiResponse.AddAdvertiseRequest, param2: String) =
                FieldForAddNewAdvertise().apply {
                    arguments = Bundle().apply {
                        addNewAdvertiseRequestParam = param1;
                        //    putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}

