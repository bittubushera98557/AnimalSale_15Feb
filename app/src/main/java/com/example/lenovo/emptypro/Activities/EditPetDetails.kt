package com.example.lenovo.emptypro.Activities

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.example.lenovo.emptypro.Utils.GlobalData
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import com.glide.slider.library.SliderTypes.TextSliderView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_edit_pet_details.*
import kotlinx.android.synthetic.main.header.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPetDetails : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position > 0) {
            strChoosedCityName = arrayList_CityName!!.get(position)
        } else {
            utilities.snackBar(txtInptLyt_Title, "Please choose City Name")
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_updatePet -> {
                checkEditFieldValues()
            }
            R.id.chkBox_dollar -> {
                chkBox_dollar.isChecked = true
                chkBox_dollar.setTextColor(R.color.colorAccent)
                chkBox_ruppess.isChecked = false
                chkBox_ruppess.setTextColor(R.color.colorBlack)
                strCurrency = "yes"
            }
            R.id.chkBox_ruppess -> {
                chkBox_ruppess.isChecked = true
                chkBox_ruppess.setTextColor(R.color.colorAccent)
                chkBox_dollar.isChecked = false
                chkBox_dollar.setTextColor(R.color.colorBlack)
                strCurrency = "no"

            }
            R.id.fl_city -> {
                if (cityArrayList != null) {
                    //       chooseCityPopUp()
                } else {
                    utilities.snackBar(txtInptLyt_Title, "check internet or try again")
                    fetchCityListApi()
                }
            }
            R.id.img_back -> {
                onBackPressed()
            }
            R.id.chkBox_no -> {
                chkBox_no.isChecked = true
                chkBox_no.setTextColor(R.color.colorAccent)
                chkBox_yes.isChecked = false
                chkBox_yes.setTextColor(R.color.colorBlack)
                showMobileChk = "yes"
            }
            R.id.chkBox_yes -> {
                chkBox_yes.isChecked = true
                chkBox_yes.setTextColor(R.color.colorAccent)
                chkBox_no.isChecked = false
                chkBox_no.setTextColor(R.color.colorBlack)
                showMobileChk = "no"
            }
        }
    }

    private fun chooseCityPopUp() {
        val cityList = arrayOfNulls<String>(cityArrayList!!.size)

        for (i in 0..(cityArrayList!!.size - 1)) {
            var stateName: String? = cityArrayList!![i].city
            cityList[i] = "" + stateName
        }
        val builder = AlertDialog.Builder(this@EditPetDetails)
        builder.setTitle("Choose an City")

        var tempChecked = 0
        builder.setSingleChoiceItems(cityList, 0!!, DialogInterface.OnClickListener { dialog, which ->
            tempChecked = which
        })

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

            try {
//                strChoosedCityName = cityArrayList!![tempChecked!!].city

                Log.e(TAG + "strChoosedCityName", "" + cityArrayList!![tempChecked!!].city)
                //  tv_citySet.text = "" + cityArrayList!![tempChecked!!].city

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
        var strState = et_state.text.toString()
        var strCity = strChoosedCityName

        if (txtInptLyt_PetName.visibility == View.VISIBLE && (TextUtils.isEmpty(strPetName.replace(" ".toRegex(), "")))) {
            focusOnEditTxtView(et_PetName, "Enter Pet Name")
        } else if (txtInptLyt_Title.visibility == View.VISIBLE && (TextUtils.isEmpty(strTitle.replace(" ".toRegex(), "")))) {
            focusOnEditTxtView(et_Title, "Enter Title")
        } else if (ll_Price.visibility == View.VISIBLE && (TextUtils.isEmpty(strPrice.replace(" ".toRegex(), "")))) {
            focusOnEditTxtView(et_Price, "Enter Price")
        } else if (ll_Price.visibility == View.VISIBLE && strCurrency.equals("")) {
            utilities.snackBar(ll_Price, "Choose a currency type")
        } else if (txtInptLyt_petType.visibility == View.VISIBLE && (TextUtils.isEmpty(strPetType.replace(" ".toRegex(), "")))) {

            focusOnEditTxtView(et_petType, "Enter Pet Type")
        } /*else if (txtInptLyt_breed.visibility == View.VISIBLE && (TextUtils.isEmpty(
                        strBreed.replace(
                                " ".toRegex(),
                                ""
                        )
                ))
        ) {
            focusOnEditTxtView(et_breed, "Enter Pet Breed")
         }*/ else
            if (txtInptLyt_age.visibility == View.VISIBLE && (TextUtils.isEmpty(
                            strAge.replace(
                                    " ".toRegex(),
                                    ""
                            )
                    ))
            ) {
                focusOnEditTxtView(et_age, "Enter Age value")

            } else
                if (txtInptLyt_desc.visibility == View.VISIBLE && (TextUtils.isEmpty(
                                strDesc.replace(
                                        " ".toRegex(),
                                        ""
                                )
                        ))
                ) {
                    focusOnEditTxtView(et_desc, "Enter Desc value")
                } else
                    if (TextUtils.isEmpty(
                                    strState.replace(
                                            " ".toRegex(),
                                            ""
                                    )
                            )
                    ) {
                        focusOnEditTxtView(et_state, "Enter Location State")
                    } else if (strCity.equals("")) {
                        //    tv_citySet.setTextColor(Color.RED)
                        utilities.snackBar(scrVw_updatePet, "Choose city")
                    } else if (!chkBox_yes.isChecked && !chkBox_no.isChecked) {
                        utilities.snackBar(scrVw_updatePet, "Please check yes or no (for show mobile on Ads) ")
                    } else {

                        /*                "&petName="+ addNewAdvertiseRequestParam!!.petName+
                "&petTitle=" + addNewAdvertiseRequestParam!!.petTitle+
                "&petBreed=" + addNewAdvertiseRequestParam!!.petBreed+
                "&petAge=" + addNewAdvertiseRequestParam!!.petAge+
                "&petPrice=" + addNewAdvertiseRequestParam!!.petPrice+
                "&petDescription=" + addNewAdvertiseRequestParam!!.petDesc+
                "&dollar=" + addNewAdvertiseRequestParam!!.priceCurrency+
                "&action="+"&petID="+"&cityName="+addNewAdvertiseRequestParam!!.cityName+
                "&state="+addNewAdvertiseRequestParam!!.areaName+
                "&isPrivate="+showMobileChk+
*/
                        updatePetDetailsApi(strPetName, strTitle, strBreed, strAge, strPrice, strDesc, strCurrency, strState)

                    }
    }

    private fun updatePetDetailsApi(strPetName: String, strTitle: String, strBreed: String, strAge: String, strPrice: String, strDesc: String, strCurrency: String, strState: String) {
        var dialogBar = utilities.dialog(this@EditPetDetails)

        Log.e(TAG + " addNewPetApi", "mainCat=" + petDetailModel!!.mainCatID +
                "&subCat=" + petDetailModel!!.subCategory +
                "&petName=" + strPetName +
                "&petTitle=" + strTitle +
                "&petBreed=" + strBreed +
                "&petAge=" + strAge +
                "&petPrice=" + strPrice +
                "&petDescription=" + strDesc +
                "&dollar=" + strCurrency +
                "&action=edit" + "&petID=" + oldPetId + "&cityName=" + strChoosedCityName +
                "&state=" + strState +
                "&isPrivate=" + showMobileChk +
                "&userID=" + SharedPrefUtil.getUserId(this@EditPetDetails))
        service!!.addNewPetWithOutImgApi(

                "" + petDetailModel!!.mainCatID,
                "" + petDetailModel!!.subCategory,
                "" + strPetName,
                "" + strTitle,
                "" + strBreed,
                "" + strAge,
                "" + strPrice,
                "" + strDesc,
                "" + strCurrency,

                "edit", "" + oldPetId, "" + strChoosedCityName,
                "" + strState,
                "" + SharedPrefUtil.getUserId(this@EditPetDetails),
                "" + showMobileChk

        )
                .enqueue(object : Callback<AllApiResponse.UploadSinglePetRes> {
                    override fun onResponse(
                            call: Call<AllApiResponse.UploadSinglePetRes>,
                            response: Response<AllApiResponse.UploadSinglePetRes>
                    ) {
                        Log.e("callAddInFavApi res", "" + Gson().toJson(response.body()))

                        //       utilities.snackBar(tv_mobile, "" + response.body()!!.message)
                        dialogBar.cancel()


                        if (response.isSuccessful && (response.body()!!.status.equals("200"))) {
                            onBackPressed()


                        } else {

                        }


                    }

                    override fun onFailure(call: Call<AllApiResponse.UploadSinglePetRes>, t: Throwable) {
                        t.printStackTrace()
                        dialogBar.cancel()
                    }
                })

    }

    private fun focusOnEditTxtView(etView: EditText, strMsg: String) {
        scrVw_updatePet.post(Runnable {
            scrVw_updatePet.scrollTo(0, etView.top)
            etView.isFocusable = true
            etView.error = "" + strMsg

            //  txtInptLyt_email.error=" txtInptLyt Field Required"

        })
    }

    internal var oldPetId: String? = ""
    internal var oldChoosedMainCatName: String? = ""
    internal var service: GetDataService? = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)

    internal var TAG = "EditPetDetails "
    internal var utilities = Utilities()
    var petDetailModel: AllApiResponse.PetDetailRes.PetDetail? = null
    var cityArrayList: List<AllApiResponse.CityResponse.CityModel>? = null
    var strCurrency = ""
    var showMobileChk = ""
    var strChoosedCityName = ""
    var arrayList_CityName: ArrayList<String>? = null
    var cityadp: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pet_details)
        getOldIntentData()
        img_back.setOnClickListener(this)
        tv_title.text = "Edit Pet Details"

        if (oldChoosedMainCatName!!.toLowerCase().equals("dog")) {
            txtInptLyt_PetName.visibility = View.VISIBLE
            txtInptLyt_Title.visibility = View.VISIBLE
            ll_Price.visibility = View.VISIBLE
            //   txtInptLyt_petType.visibility=View.VISIBLE
            txtInptLyt_breed.visibility = View.VISIBLE
            txtInptLyt_age.visibility = View.VISIBLE
            txtInptLyt_desc.visibility = View.VISIBLE
        } else
            if (oldChoosedMainCatName!!.toLowerCase().equals("cat")) {
                txtInptLyt_PetName.visibility = View.VISIBLE
                txtInptLyt_Title.visibility = View.VISIBLE
                //txtInptLyt_Price.visibility=View.VISIBLE
                ll_Price.visibility = View.VISIBLE
                //     txtInptLyt_petType.visibility=View.VISIBLE
                txtInptLyt_breed.visibility = View.VISIBLE
                //   txtInptLyt_age.visibility=View.VISIBLE
                txtInptLyt_desc.visibility = View.VISIBLE
            } else
                if (oldChoosedMainCatName!!.toLowerCase().equals("bird")) {
                    txtInptLyt_PetName.visibility = View.VISIBLE
                    txtInptLyt_Title.visibility = View.VISIBLE
                    ll_Price.visibility = View.VISIBLE
                    // txtInptLyt_petType.visibility=View.VISIBLE
                    //  txtInptLyt_breed.visibility=View.VISIBLE
                    //   txtInptLyt_age.visibility=View.VISIBLE
                    txtInptLyt_desc.visibility = View.VISIBLE
                } else
                    if (oldChoosedMainCatName!!.toLowerCase().equals("cow")) {
                        txtInptLyt_PetName.visibility = View.VISIBLE
                        txtInptLyt_Title.visibility = View.VISIBLE
                        //txtInptLyt_Price.visibility=View.VISIBLE
                        ll_Price.visibility = View.VISIBLE
                        //  txtInptLyt_petType.visibility=View.VISIBLE
                        //   txtInptLyt_breed.visibility=View.VISIBLE
                        //  txtInptLyt_age.visibility=View.VISIBLE
                        txtInptLyt_desc.visibility = View.VISIBLE
                    } else
                        if (oldChoosedMainCatName!!.toLowerCase().equals("buffalo")) {
                            //                      txtInptLyt_PetName.visibility= View.VISIBLE
                            txtInptLyt_Title.visibility = View.VISIBLE
                            ll_Price.visibility = View.VISIBLE
                            //  txtInptLyt_petType.visibility=View.VISIBLE
                            //                    txtInptLyt_breed.visibility= View.VISIBLE
                            txtInptLyt_age.visibility = View.VISIBLE
                            txtInptLyt_desc.visibility = View.VISIBLE
                        } else
                            if (oldChoosedMainCatName!!.toLowerCase().equals("others")) {
                                txtInptLyt_PetName.visibility = View.VISIBLE
                                txtInptLyt_Title.visibility = View.VISIBLE
                                ll_Price.visibility = View.VISIBLE
                                txtInptLyt_petType.visibility = View.VISIBLE
                                txtInptLyt_breed.visibility = View.VISIBLE
                                txtInptLyt_age.visibility = View.VISIBLE
                                txtInptLyt_desc.visibility = View.VISIBLE
                            }
        fetchCityListApi()
        btn_updatePet.setOnClickListener(this)
        chkBox_dollar.setOnClickListener(this)
        chkBox_ruppess.setOnClickListener(this)
        fl_city.setOnClickListener(this)
        chkBox_no.setOnClickListener(this)
        chkBox_yes.setOnClickListener(this)

    }

    private fun getOldIntentData() {

        oldPetId = intent.extras!!.getString("oldPetId")
        oldChoosedMainCatName = intent.extras!!.getString("petMainCat")
        tv_mainCategory.text = "" + oldChoosedMainCatName
        getPetDetail()
    }

    private fun getPetDetail() {
        Log.e(TAG + " getPetDetail", "single-pet/?userID=" + SharedPrefUtil.getUserId(this@EditPetDetails) + "&petID=" + oldPetId)
        var call = service!!.getPetDetailsApi("" + SharedPrefUtil.getUserId(this@EditPetDetails), "" + oldPetId)
        var dialogBar = utilities.dialog(this@EditPetDetails)

        call.enqueue(object : Callback<AllApiResponse.PetDetailRes> {
            override fun onResponse(call: Call<AllApiResponse.PetDetailRes>, response: Response<AllApiResponse.PetDetailRes>) {
                Log.e("getPetDetail res", "" + Gson().toJson(response.body()))
                dialogBar.cancel()
                if (response.body()!!.status.equals("200") && response.body()!!.data.size > 0) {
                    petDetailModel = response.body()!!.data[0]
/* txtInptLyt_PetName.visibility= View.VISIBLE
            txtInptLyt_Title.visibility= View.VISIBLE
            ll_Price.visibility= View.VISIBLE
            //   txtInptLyt_petType.visibility=View.VISIBLE
            txtInptLyt_breed.visibility= View.VISIBLE
            txtInptLyt_age.visibility= View.VISIBLE
            txtInptLyt_desc.visibility= View.VISIBLE*/


                    tv_subCategory.text = "" + petDetailModel!!.subCategory

                    strChoosedCityName = "" + petDetailModel!!.userCity

                    et_state.setText(petDetailModel!!.userState)
                    et_Title.setText(petDetailModel!!.petTitle)
                    et_desc.setText(petDetailModel!!.petDescription)
                    et_PetName.setText(petDetailModel!!.petName)
                    et_Price.setText(petDetailModel!!.petPrice)

                    try {
                        var price = petDetailModel!!.petPrice.split(" ")
                        et_Price.setText(price[0])
                        if (price[1].toLowerCase().contains("usd")) {
                            chkBox_dollar.callOnClick()
                        } else
                            chkBox_ruppess.callOnClick()

                    } catch (exp: java.lang.Exception) {


                    }

                    try {
                        var strPrivate= petDetailModel!!.isPrivate
                        showMobileChk=strPrivate
                        if (showMobileChk.toLowerCase().contains("yes")) {
                            chkBox_no.callOnClick()
                        } else
                            chkBox_yes.callOnClick()

                    } catch (exp: java.lang.Exception) {


                    }

                    et_petType.setText("")
                    et_breed.setText(petDetailModel!!.petBreed)
                    et_age.setText(petDetailModel!!.petAge)

                    Log.e(TAG + " getPetDetail", "size=" + response.body()!!.data.size)

                } else {
                    GlobalData.showSnackbar(this@EditPetDetails as Activity, response.body()!!.messageType)
                }
            }

            override fun onFailure(call: Call<AllApiResponse.PetDetailRes>, t: Throwable) {
                dialogBar.cancel()
                // progress_bar.setVisibility(View.GONE);
                Toast.makeText(this@EditPetDetails, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun fetchCityListApi() {
        var dialog: Dialog? = utilities.dialog(this@EditPetDetails)
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
                    for (i in 0..(cityArrayList!!.size - 1)) {
                        arrayList_CityName!!.add("" + response.body()!!.data[i].city)
                    }

                    try {
                        cityadp = ArrayAdapter<String>(this@EditPetDetails, R.layout.textview, arrayList_CityName)
                        spinner_city.setOnItemSelectedListener(this@EditPetDetails);
                        // Drop down layout style - list view with radio button
                        cityadp!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cityadp!!.setDropDownViewResource(R.layout.textview);
                        spinner_city.setAdapter(cityadp);

                        for (i in 0..(arrayList_CityName!!.size - 1))
                            {
                       if(arrayList_CityName!!.get(i).toString() ==     strChoosedCityName)
                       {
                           spinner_city.setSelection(i)
                       }
                        }

                    } catch (e: Exception) {
                        Log.e(TAG, "searchable adapter excep=" + e.toString())
                        e.printStackTrace();
                    }


                } else {
                    //swipe_refresh.isRefreshing = false

                    utilities.snackBar(tv_mainCategory, "" + response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<AllApiResponse.CityResponse>, t: Throwable) {
                t.printStackTrace()
                dialog!!.cancel()

            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this@EditPetDetails, UploadedPets::class.java)
        startActivity(intent)
        finish()
    }
}
