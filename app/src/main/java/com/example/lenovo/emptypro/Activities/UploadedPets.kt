package com.example.lenovo.emptypro.Activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.iww.classifiedolx.recyclerview.setUp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_uploaded_pets.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.home_searched_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UploadedPets : AppCompatActivity(), View.OnClickListener, OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri?) {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {
                onBackPressed()
            }

        }
    }

    private var allUploadedPetsLst: MutableList<AllApiResponse.UploadedPetsRes.UploadedPetsItem>? = null
    //    var ctx: Context?=null
    internal var service: GetDataService? = null
    var TAG = "UploadedPets "
    var utilities = Utilities()
    var dbReference = FirebaseDatabase.getInstance().reference
    var loggeInRef = dbReference.child("/AllChat")
    var allUsersListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploaded_pets)
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        allUploadedPetsLst = mutableListOf()
        img_back.setOnClickListener(this)
        tv_title.text = "Uploaded Pets"

        if (GlobalData.isConnectedToInternet(this@UploadedPets)) {
            callAllUploadedPetsApi()
        } else {
            GlobalData.showSnackbar(this@UploadedPets, getString(R.string.please_check_your_internet_connection))
        }

        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing=false
            if (GlobalData.isConnectedToInternet(this@UploadedPets)) {
                callAllUploadedPetsApi()
            } else {
                GlobalData.showSnackbar(this@UploadedPets, getString(R.string.please_check_your_internet_connection))
            }
        }

    }

    private fun makeSoldOutPet (strAction: String,dataModel: AllApiResponse.UploadedPetsRes.UploadedPetsItem?) {
            Log.e(TAG,"makeSoldOutPet   adsId="+dataModel!!.petID+"   action="+strAction)
            var dialogBar=utilities.dialog(this@UploadedPets)
            val call = service!!.addInFav(""+strAction ,""+ SharedPrefUtil.getUserId(this@UploadedPets),""+ dataModel!!.petID).enqueue(object : Callback<AllApiResponse.CommonRes> {
                override fun onResponse(
                        call: Call<AllApiResponse.CommonRes>,
                        response: Response<AllApiResponse.CommonRes>
                ) {
                    dialogBar.cancel()
                    Log.e("makeSoldOutPet res", "" + Gson().toJson(response.body()))
                    if (response.isSuccessful && (response.body()!!.status.equals("200"))) {
                        allUploadedPetsLst!!.remove(dataModel)
                        rv_uploadedPets.adapter!!.notifyDataSetChanged()
       deleteAllChat(""+dataModel!!.petID)
                    } else {
                        //swipe_refresh.isRefreshing = false
                    }
                    utilities.snackBar(rv_uploadedPets,""+response.body()!!.messageType)
                }

                override fun onFailure(call: Call<AllApiResponse.CommonRes>, t: Throwable) {
                    t.printStackTrace()
                    dialogBar.cancel()
                    //swipe_refresh.isRefreshing = false
                }
            })


    }

    private fun deleteAllChat(oldPetID: String) {
        Log.e(TAG + " ", "function searching  FireBaseQueries()")



        allUsersListener = object : ValueEventListener {
            @SuppressLint("WrongConstant")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e(TAG + " ", "function Calling  FireBaseQueries()")

                val children = dataSnapshot!!.children

                children.forEach {
                    Log.e(
                            TAG + " FireBaseQueries",
                            "  ownerId=" + it.child("ownerId").value.toString() + "    petId=" + it.child("petId").value.toString() + "   " + "intersterId=" + it.child("intersterId").value.toString()
                    )
                     var strDB_OwnerId = it.child("ownerId").value.toString()
                    var strDB_petId= it.child("petId").value.toString()

                    if (strDB_OwnerId == SharedPrefUtil.getUserId(this@UploadedPets) && strDB_petId==oldPetID) {
                        loggeInRef.child(""+it.key).removeValue();
                    }



                    }

            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.e(TAG, "getOldChat  :onCancelled", databaseError.toException())
            }
        }
        loggeInRef.addValueEventListener(allUsersListener!!)



    }

    private fun setAdapterData() {

        rv_uploadedPets.setHasFixedSize(true);
        rv_uploadedPets.layoutManager = GridLayoutManager(this@UploadedPets, 2)

        rv_uploadedPets.setUp(allUploadedPetsLst!!, R.layout.home_searched_item, { it1 ->
            val circularProgressDrawable = CircularProgressDrawable(this@UploadedPets)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Log.e(TAG + "", "UploadedPet   name=" + it1.petName + "  " + it1.category)
            if (it1.images.size > 0)
                Picasso.with(context).load(it1.images[0].img).fit().placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(this.iv_searchedItemImg)

            this.tv_searchedItemShortInfo.text = "Rs. " + it1.petPrice

            var strInfo=""
            if(!it1.petTitle.equals("") && !it1.petTitle.equals("null")  )
                strInfo=strInfo+" | "+it1.petTitle
            if(!it1.category.equals("") && !it1.category.equals("null")  )
                strInfo=strInfo+" | "+it1.category
            if(!it1.subCategory.equals("") && !it1.subCategory.equals("null")  )
                strInfo=strInfo+" | "+it1.subCategory
            this.tv_searchedItemLongInfo.text =  strInfo.substring(2)
            this.tv_searchedItemLoc.text=it1.userCity+", "+it1.userState
            this.tv_searchedItemDate.text=GlobalData.dateSplit(it1.createdOn)
            this.iv_favImg.visibility = View.GONE

            this.iv_favImg.visibility=View.VISIBLE
            this.iv_favImg.setImageResource(R.drawable.ic_three_dots)
            this.iv_favImg.setOnClickListener(View.OnClickListener {
                this.ll_operations.visibility=View.VISIBLE
            })

            this.tv_sold.setOnClickListener(View.OnClickListener {
                val dialog = AlertDialog.Builder(this@UploadedPets).setTitle("Want to make sold out !").setMessage("Are you sure you want to sold out ?")
                dialog.setPositiveButton("Confirm") { dialog, whichButton ->
                    dialog.cancel()
                    if (GlobalData.isConnectedToInternet(this@UploadedPets)) {
                        makeSoldOutPet("sold", it1)
                    } else {
                        Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_LONG).show()
                    }
                }
                val alert = dialog.create()
                alert.show()
            })

            this.tv_editPet.setOnClickListener(View.OnClickListener {
                val dialog = AlertDialog.Builder(this@UploadedPets).setTitle("Want to Edit !").setMessage("Are you sure you want to Edit ?")
                dialog.setPositiveButton("Confirm") { dialog, whichButton ->
                    dialog.cancel()
                    val intent = Intent(this@UploadedPets, EditPetDetails::class.java)
                    intent.putExtra("oldPetId", "" + it1.petID)
                    intent.putExtra("petMainCat", "" + it1.category)

                    startActivity(intent)
                    finish() }
                val alert = dialog.create()
                alert.show()
            })

            if (ll_searchedItem != null) {
                val display = (this@UploadedPets).windowManager.defaultDisplay
                ll_searchedItem.getLayoutParams().width = Math.round((display.width / 2).toFloat())
            }
            ll_searchedItem.setOnClickListener {
                if(ll_operations.visibility==View.VISIBLE)
                {
                    ll_operations.visibility=View.GONE
                }
                else {
                    val intent = Intent(this@UploadedPets, PetDetailsActivity::class.java)
                    intent.putExtra("oldPetId", "" + it1.petID)
                    startActivity(intent)
                }
            }
        }, { view1: View, i: Int -> })
        rv_uploadedPets.layoutManager = GridLayoutManager(this@UploadedPets, 2)

    }

    private fun callAllUploadedPetsApi() {


        val call = service!!.getUploadedPetsApi(SharedPrefUtil.getUserId(this@UploadedPets))

        call.enqueue(object : Callback<AllApiResponse.UploadedPetsRes> {
            override fun onResponse(call: Call<AllApiResponse.UploadedPetsRes>, response: Response<AllApiResponse.UploadedPetsRes>) {
                Log.e(TAG + " UploadedPetsApi", "response "+ Gson().toJson(response.body()));
                swipe_refresh.isRefreshing=false
                if (response.body()!!.status.equals("200") && (response.body()!!.data.size>0)) {
                    allUploadedPetsLst!!.clear()
                    allUploadedPetsLst!!.addAll(response.body()!!.data)
                    Log.e(TAG + " UploadedPetsApi", "size=" + response.body()!!.data.size)

                    setAdapterData()

                    tvNoData.visibility=View.GONE
                }
                else{
                    tvNoData.visibility=View.VISIBLE
                }
            }

            override fun onFailure(call: Call<AllApiResponse.UploadedPetsRes>, t: Throwable) {
                swipe_refresh.isRefreshing=false
                Toast.makeText(this@UploadedPets, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
if(allUsersListener!=null)
        loggeInRef.removeEventListener(allUsersListener!!)

    }
    override fun onBackPressed() {
        val intent = Intent(this@UploadedPets, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
