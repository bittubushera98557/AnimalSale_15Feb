package com.example.lenovo.emptypro.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lenovo.emptypro.Activities.MainActivity
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance
import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.google.gson.Gson
import com.iww.classifiedolx.recyclerview.setUp
import kotlinx.android.synthetic.main.fragment_sub_cat.*
import kotlinx.android.synthetic.main.item_sub_category_lst.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
 private const val ARG_PARAM2 = "param2"

class SubCatFrag : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(position>0)
        {

            strSubCategory=strArrayList_Cat!!.get(position)
            Log.e(TAG + "strSubCategory", "" + strSubCategory)
            gotoSubCatBaseFrag()
              }
else
        {
            utility.snackBar(tv_chooseOne, "Please Choose a Sub Category")

        }
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.tv_chooseOne ->
            {
                if (subCatItemData != null) {
                    showSubCatDialog()
                } else {
                    utility.snackBar(tv_chooseOne, "check internet or try again")
                }
//                ll_buttons.visibility=View.GONE
  //              rv_subCatLst.visibility=View.VISIBLE
            }
            R.id.tv_showAll->
            {
                try {
/*
                    utility.enterNextReplaceFragment(
                            R.id.fl_subCatFrag,
                            SubCateBaseAdd.newInstance(""+mainCatId,"all"),
                            (ctx as MainActivity).supportFragmentManager
                    )
*/
                    strSubCategory="all"
//                    checkCity()
                    Log.e(TAG + "choosedSubCatID", "all" )
                    gotoSubCatBaseFrag()

                } catch (exp: Exception) {

                }

            }

        }

    }

    private fun checkCity() {

        if (cityArrayList != null) {
            chooseCityPopUp()
        } else {
            utility.snackBar(rv_subCatLst, "check internet or try again")
            getAllCities()
        }
    }
    private fun getAllCities() {
        Log.e(TAG,"getAllCities   = /city/" )
        var dialogBar=utility.dialog(context!!)

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

    private fun showSubCatDialog() {
        val subcatList = arrayOfNulls<String>(subCatItemData!!.size)

        for (i in 0..(subCatItemData!!.size - 1)) {
            var subCatName: String? = subCatItemData!![i].subCategoryTitle
            subcatList [i] = "" + subCatName
        }
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Choose a Subcategory")

        var tempChecked = 0
        builder.setSingleChoiceItems(subcatList , 0!!, DialogInterface.OnClickListener { dialog, which ->
            tempChecked = which
        })

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            strSubCategory= subCatItemData!![tempChecked!!].subCategoryTitle


            try {
            //   checkCity()
                Log.e(TAG + "choosedCityID", "" + strSubCategory)
                gotoSubCatBaseFrag()

            } catch (exp: Exception) {

            }
        })
        builder.setNegativeButton("Cancel", null)
// create and show the alert dialog
        val dialog = builder.create()
        dialog.show()


    }
    private fun chooseCityPopUp()
    {
        val cityList = arrayOfNulls<String>(cityArrayList!!.size)

        for (i in 0..(cityArrayList!!.size - 1)) {
            var stateName: String? = cityArrayList!![i].city
            cityList[i] = "" + stateName
        }
        val builder = android.app.AlertDialog.Builder(context!!)
        builder.setTitle("Choose an City")

        var tempChecked = 0
        builder.setSingleChoiceItems(cityList, 0!!, DialogInterface.OnClickListener { dialog, which ->
            tempChecked = which
        })

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

            try {
                strChoosedCityName = cityArrayList!![tempChecked!!].city

                Log.e(TAG + "strChoosedCityName", "" + strChoosedCityName)
                 gotoSubCatBaseFrag()
            } catch (exp: Exception) {

            }
        })
        builder.setNegativeButton("Cancel", null)
// create and show the alert dialog
        val dialog = builder.create()
        dialog.show()


    }

    private fun gotoSubCatBaseFrag() {
        utility.enterNextReplaceFragment(
                R.id.fl_subCatFrag,
                SubCateBaseAdd.newInstance(""+mainCatId,""+strSubCategory,""+strChoosedCityName),
                (ctx as MainActivity).supportFragmentManager
        )

    }

    // TODO: Rename and change types of parameters
  //  private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null
   // @Inject
 //   internal lateinit var apiService: ApiService
    private var mainCatId: String? = null
    var ctx: Context? = null
    var TAG="SubCatFrag "
    private var subCatItemData: MutableList<AllApiResponse.CategoryResponse.CategorySubListModel>? = null
    var cityArrayList: List<AllApiResponse.CityResponse.CityModel>? = null
    internal var service: GetDataService? = null
    var strChoosedCityName=""
    var strSubCategory=""
    //spinner_subCat
    var categoryAdap: ArrayAdapter<String>?=null
    var    strArrayList_Cat: ArrayList<String>?=null;
    var utility = Utilities()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
          //  param1 = it.getString(ARG_PARAM1)
            mainCatId= it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_cat, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        subCatItemData = mutableListOf()
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        rv_subCatLst.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_subCatLst.layoutManager = mLayoutManager

        rv_subCatLst.setUp(subCatItemData!!, R.layout.item_sub_category_lst, { it1 ->
            this.tv_subCatTitle.text = it1.subCategoryTitle

            this.ll_subCatItem.setOnClickListener {
             //   checkCity()
                gotoSubCatBaseFrag()
            }
        }, { view1: View, i: Int -> })
        utility = Utilities()
        if (!utility.isConnected(ctx!!))
            utility.snackBar(rv_subCatLst, "Please check internet connection ")

        rv_subCatLst.adapter!!.notifyDataSetChanged()
        rv_subCatLst.layoutManager = LinearLayoutManager(context)
        getAllCities()

        swipe_refresh.setOnRefreshListener {
            if (!utility.isConnected(ctx!!))
                utility.snackBar(rv_subCatLst, "Please check internet connection ")
            getAllCities()

            //   fetchAllSubCat("get_subcategory")
        }
        if (subCatItemData!!.size == 0) {
            utility.snackBar(rv_subCatLst, "No Sub Category Found")

        }

        tv_chooseOne.setOnClickListener(this)
        tv_showAll.setOnClickListener(this)
/*
if(mainCatId.equals())
    }
*/
        Log.e(TAG+"","mainCatId="+mainCatId)
   setCategoryInSingleArrLst() }

    private fun setCategoryInSingleArrLst() {

        strArrayList_Cat= ArrayList()
        strArrayList_Cat!!.clear()
        strArrayList_Cat!!.add("Choose a Sub Category")
        for (i in 0..(subCatItemData!!.size- 1)) {
            strArrayList_Cat!!.add(""+ subCatItemData!![i].subCategoryTitle)
        }

        try{
            categoryAdap= ArrayAdapter<String>(ctx!!, R.layout.textview, strArrayList_Cat)


            spinner_subCat.setOnItemSelectedListener(this@SubCatFrag);
            // Drop down layout style - list view with radio button
            categoryAdap!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categoryAdap!!.setDropDownViewResource(R.layout.textview);
            spinner_subCat.setAdapter(categoryAdap);

        } catch ( e:Exception) {
            Log.e(TAG,"searchable adapter excep="+e.toString())
            e.printStackTrace();
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
      ctx=context
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
        fun newInstance(param1: MutableList<AllApiResponse.CategoryResponse.CategorySubListModel> , param2: String) =
                SubCatFrag().apply {
                    arguments = Bundle().apply {
                        subCatItemData=param1
//                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
