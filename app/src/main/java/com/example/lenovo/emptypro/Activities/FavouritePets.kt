package com.example.lenovo.emptypro.Activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance
import com.example.lenovo.emptypro.Fragments.AdvertisementDetailsFrag
import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.example.lenovo.emptypro.Utils.GlobalData
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import com.google.gson.Gson
import com.iww.classifiedolx.recyclerview.setUp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_favourite_pets.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.home_searched_item.view.*
import kotlinx.android.synthetic.main.item_top_cate.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouritePets : AppCompatActivity(), View.OnClickListener , OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri?) {
    }
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.img_back->
            {
                onBackPressed()
            }

        }

    }

    private var allFavouritePetsLst: MutableList<AllApiResponse.FavouritePetsRes.FavouriteItemModel>? = null

    internal var service: GetDataService?=null
    var  TAG ="FavouritePets "
    var utilities= Utilities()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_pets)
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        allFavouritePetsLst = mutableListOf()
        img_back.setOnClickListener(this)

        tv_title.text = "Favourite Pets"
        rv_favouritePets.setHasFixedSize(true);
        rv_favouritePets.layoutManager = GridLayoutManager(this@FavouritePets, 2)

        rv_favouritePets.setUp(allFavouritePetsLst!!, R.layout.home_searched_item, { it1 ->
            val circularProgressDrawable = CircularProgressDrawable(this@FavouritePets)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Log.e(TAG+"","callAllFavourite  name="+it1.petName+ "  "+ it1.category)
            if(it1.images.size>0)
                Picasso.with(context).load(""+it1.images[0].img).fit().placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(this.iv_searchedItemImg)


            this.tv_searchedItemShortInfo.text = "Rs. " + it1.petPrice

            var strInfo=""
            if(!it1.petTitle.equals("") && !it1.petTitle.equals("null")  )
                strInfo=strInfo+" | "+it1.petTitle
            if(!it1.category.equals("") && !it1.category.equals("null")  )
                strInfo=strInfo+" | "+it1.category
            if(!it1.subCategory.equals("") && !it1.subCategory.equals("null")  )
                strInfo=strInfo+" | "+it1.subCategory
            this.tv_searchedItemLongInfo.text =  strInfo.substring(2)
            this.  tv_searchedItemLoc.text=it1.userCity+", "+it1.userState
            this.  tv_searchedItemDate.text=GlobalData.dateSplit(it1.createdOn)
            this.iv_favImg.visibility=View.VISIBLE
            this.iv_favImg.setImageResource(R.drawable.ic_favorite_filled)
            this.iv_favImg.setOnClickListener{
                     if(utilities.isConnected(this@FavouritePets))
                        callChangeFavApi("remove", it1)
                    else
                        utilities.snackBar(rv_favouritePets,"Please check internet ")

                }



            if ( ll_searchedItem!= null) {
                val display = (this@FavouritePets).windowManager.defaultDisplay
                ll_searchedItem.getLayoutParams().width= Math.round((display.width / 2).toFloat())
            }
            ll_searchedItem.setOnClickListener {
                val intent = Intent(this@FavouritePets, PetDetailsActivity::class.java)
                intent.putExtra("oldPetId", "" + it1.petID)
                startActivity(intent)

            }
        }, { view1: View, i: Int -> })
        rv_favouritePets.layoutManager = GridLayoutManager(this@FavouritePets, 2)
        if (GlobalData.isConnectedToInternet(this@FavouritePets)) {
            callAllFavouritePetsApi()
        } else {
            GlobalData.showSnackbar(this@FavouritePets, getString(R.string.please_check_your_internet_connection))
        }
        swipe_refresh.setOnRefreshListener {
            if (GlobalData.isConnectedToInternet(this@FavouritePets)) {
                callAllFavouritePetsApi()
            } else {
                swipe_refresh.isRefreshing=false
                GlobalData.showSnackbar(this@FavouritePets, getString(R.string.please_check_your_internet_connection))
            }
        }
    }
    private fun callChangeFavApi(strAction: String,dataModel: AllApiResponse.FavouritePetsRes.FavouriteItemModel?) {
        Log.e(TAG,"callChangeFavApi   adsId="+dataModel!!.petID+"   action="+strAction)
var dialogBar=utilities.dialog(this@FavouritePets)
        val call = service!!.addInFav(""+strAction ,""+ SharedPrefUtil.getUserId(this@FavouritePets),""+ dataModel!!.petID).enqueue(object : Callback<AllApiResponse.CommonRes> {
            override fun onResponse(
                    call: Call<AllApiResponse.CommonRes>,
                    response: Response<AllApiResponse.CommonRes>
            ) {
dialogBar.cancel()
                Log.e("callAddInFavApi res", "" + Gson().toJson(response.body()))
                if (response.isSuccessful && (response.body()!!.status.equals("200"))) {
                    allFavouritePetsLst!!.remove(dataModel)
                    rv_favouritePets.adapter!!.notifyDataSetChanged()
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

    private fun callAllFavouritePetsApi() {
        swipe_refresh.isRefreshing=true
        val call = service!!.getFavouritePetsApi(SharedPrefUtil.getUserId(this@FavouritePets))
        call.enqueue(object : Callback<AllApiResponse.FavouritePetsRes> {
            override fun onResponse(call: Call<AllApiResponse.FavouritePetsRes>, response: Response<AllApiResponse.FavouritePetsRes>) {
                swipe_refresh.isRefreshing=false
                Log.e(TAG + " callAllFavourite", "response   " + Gson().toJson(response.body()))
                if(response.body()!!.status.equals("200") && response.body()!!.data.size>0 ) {
                    allFavouritePetsLst!!.clear()
                    allFavouritePetsLst!!.addAll(response.body()!!.data)
                    Log.e(TAG + " callAllFavourite", "size=" + response.body()!!.data.size)

                    rv_favouritePets.adapter!!.notifyDataSetChanged()
                }
                else{
                    utilities.snackBar(rv_favouritePets,"No Favourite pets record found")
                }
            }

            override fun onFailure(call: Call<AllApiResponse.FavouritePetsRes>, t: Throwable) {
                swipe_refresh.isRefreshing=false
                 Toast.makeText(this@FavouritePets, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })

    }


    override fun onBackPressed() {
        val intent = Intent(this@FavouritePets, MainActivity::class.java)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        startActivity(intent)
         finish()
    }

}

