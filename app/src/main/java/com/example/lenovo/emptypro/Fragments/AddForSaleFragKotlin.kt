package com.example.lenovo.emptypro.Fragments

import SubCatFragForAdd
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.lenovo.emptypro.Activities.MainActivity
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance
import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.example.lenovo.emptypro.Utils.GlobalData
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import com.google.gson.Gson
import com.iww.classifiedolx.Fragments.AddAdvertise.FieldForAddNewAdvertise
import com.iww.classifiedolx.recyclerview.setUp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_add_for_sale_frag_kotlin.*
import kotlinx.android.synthetic.main.item_top_cate.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddForSaleFragKotlin : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
         when(v!!.id)
         {
             R.id.fl_frag_add_for_sale->
             {

             }

         }    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var mainCatItemData: MutableList<AllApiResponse.CategoryResponse.CategoryMainListModel>? = null
    var ctx: Context? = null
    internal var service: GetDataService? = null
    var TAG = "AddForSaleFragKotlin "
    var advertiseAddRequestParams:  AllApiResponse.AddAdvertiseRequest?= null

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
        return inflater.inflate(R.layout.fragment_add_for_sale_frag_kotlin, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        mainCatItemData = mutableListOf()
        advertiseAddRequestParams= AllApiResponse.AddAdvertiseRequest()

        rv_mainCatForSale.setHasFixedSize(true);
        rv_mainCatForSale.layoutManager = GridLayoutManager(context, 2)
        rv_mainCatForSale.setUp(mainCatItemData!!, R.layout.item_top_cate, { it1 ->
            this.tv_mainCatTxt.text = it1.title
            val circularProgressDrawable = CircularProgressDrawable(ctx as Activity)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Log.e(TAG + "", "rv_mainCatForSale=" + it1.imgUrl)
            Picasso.with(context).load("" + it1.imgUrl).placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(this.iv_mainCatImg)


            if (ll_mainCat != null) {
                val display = (context as Activity).windowManager.defaultDisplay
                ll_mainCat.getLayoutParams().width = Math.round((display.width / 2).toFloat())
            }
            this.ll_mainCat.setOnClickListener {
                var utilities = Utilities()

/* if(SharedPrefUtil.getUserFirstName(ctx!!).replace(" ".toRegex(),"").equals(""))
 {
utilities.snackBar(rv_mainCatForSale,"Please, Firstly update your profile")
 }
     else {*/
   if( it1.title.toLowerCase().equals("others"))
   {

       advertiseAddRequestParams!!.CatId=""+it1.categoryID
       advertiseAddRequestParams!!.CatName=it1.title
       advertiseAddRequestParams!!.SubCatId=""
       advertiseAddRequestParams!!.SubCatName=""

       utilities.enterNextReplaceFragment(


               R.id.fl_frag_add_for_sale,
               FieldForAddNewAdvertise.newInstance(advertiseAddRequestParams!!,""),
               (ctx as MainActivity).supportFragmentManager
       )

   }else{
       utilities.enterNextReplaceFragment(R.id.fl_frag_add_for_sale, SubCatFragForAdd.newInstance(it1.subCat, "" + it1.categoryID + "~~" + it1.title), (ctx as MainActivity).supportFragmentManager)
  // }

 }
            }
        }, { view1: View, i: Int -> })

        if (GlobalData.isConnectedToInternet(activity)) {
            getMainCateApi()
         } else {
            GlobalData.showSnackbar(activity!!, getString(R.string.please_check_your_internet_connection))
        }
         rv_mainCatForSale.layoutManager = GridLayoutManager(context, 2)
        fl_frag_add_for_sale.setOnClickListener(this)
    }

    private fun getMainCateApi() {
        tvNoData.visibility=View.VISIBLE
        val call = service!!.allCateListApi()

        call.enqueue(object : Callback<AllApiResponse.CategoryResponse> {
            override fun onResponse(call: Call<AllApiResponse.CategoryResponse>, response: Response<AllApiResponse.CategoryResponse>) {
                Log.e(TAG + " getAllCateApi", "" + Gson().toJson(response.body()))
                mainCatItemData!!.clear()
                mainCatItemData!!.addAll(response.body()!!.data)
                tvNoData.visibility=View.GONE

                rv_mainCatForSale.adapter!!.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<AllApiResponse.CategoryResponse>, t: Throwable) {
                // progress_bar.setVisibility(View.GONE);
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })

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
        fun newInstance(param1: String, param2: String) =
                AddForSaleFragKotlin().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
