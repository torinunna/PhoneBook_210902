package com.yujin.phonebook_210902.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.yujin.phonebook_210902.R
import com.yujin.phonebook_210902.datas.PhoneNumData
import java.util.*

class PhoneNumAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<PhoneNumData>) : ArrayAdapter<PhoneNumData>(mContext, resId, mList) {

    private val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var row = convertView
        if (row == null) {
            row = mInflater.inflate(R.layout.phone_num_list_item, null)
        }

        val nameTxt = row!!.findViewById<TextView>(R.id.nameTxt)
        val birthDayTxt = row.findViewById<TextView>(R.id.birthDayTxt)
        val phoneNumTxt = row.findViewById<TextView>(R.id.phoneNumTxt)
        val dialImg = row.findViewById<ImageView>(R.id.dialImg)
        val birthDayImg = row.findViewById<ImageView>(R.id.birthDayImg)

        val data = mList.get(position)

        nameTxt.text = data.name
        phoneNumTxt.text = data.phoneNum

        birthDayTxt.text = data.getFormattedBirthday()


        val tempBirthDay = Calendar.getInstance()

        tempBirthDay.time = data.birthDay.time


        val today = Calendar.getInstance()

        tempBirthDay.set( Calendar.YEAR,  today.get(Calendar.YEAR) )

        val diffInMillis = tempBirthDay.timeInMillis  -  today.timeInMillis

        val diffInDays = diffInMillis / 1000 / 60 / 60 / 24


        if (diffInDays in 0..10) {
            birthDayImg.visibility = View.VISIBLE
        }
        else {
            birthDayImg.visibility = View.GONE
        }


        dialImg.setOnClickListener {


            val myUri = Uri.parse("tel:${data.phoneNum}")
            val myIntent = Intent(Intent.ACTION_DIAL, myUri)

            mContext.startActivity(myIntent)

        }

        return row

    }

}