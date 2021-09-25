package com.yujin.phonebook_210902.datas

import java.text.SimpleDateFormat
import java.util.*

class PhoneNumData(var name: String, var phoneNum : String) {

    val birthDay = Calendar.getInstance()

    private val fileDateFormat = SimpleDateFormat("yyyy-MM-dd")

    fun getFileFormatData() : String {

        return  "${this.name},${this.phoneNum},${fileDateFormat.format( this.birthDay.time )}"

    }

    val birthDayFormatter = SimpleDateFormat("M월 d일")

    fun getFormattedBirthday() : String {

        return birthDayFormatter.format( this.birthDay.time )

    }

}