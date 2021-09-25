package com.yujin.phonebook_210902

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yujin.phonebook_210902.adapters.PhoneNumAdapter
import com.yujin.phonebook_210902.datas.PhoneNumData
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.text.SimpleDateFormat

class MainActivity : BaseActivity() {

    val mPhoneNumList = ArrayList<PhoneNumData>()

    lateinit var mAdapter : PhoneNumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        addPhoneNumBtn.setOnClickListener {

            val myIntent = Intent(mContext, EditPhoneNumActivity::class.java)
            startActivity(myIntent)

        }
    }

    override fun setValues() {

        mAdapter = PhoneNumAdapter(mContext, R.layout.phone_num_list_item, mPhoneNumList)

        phoneNumListView.adapter = mAdapter

        readPhoneBookFromFile()

    }


    override fun onResume() {
        super.onResume()

        readPhoneBookFromFile()

    }

    fun readPhoneBookFromFile() {

        val myFile = File(filesDir, "phoneBook.txt")

        if (!myFile.exists()) {
            Log.d("파일없음", "아직 메모된 내용이 없습니다.")
            return
        }

        val fr = FileReader(myFile)
        val br = BufferedReader(fr)

        val sdf = SimpleDateFormat("yyyy-MM-dd")

        mPhoneNumList.clear()

        while (true) {

            val line = br.readLine()

            if (line == null) {

                break
            }

            Log.d("읽어온 한줄", line)


            val infos = line.split(",")

            val phoneNumData = PhoneNumData(infos[0], infos[1])

            phoneNumData.birthDay.time = sdf.parse(infos[2])

            mPhoneNumList.add(phoneNumData)


        }

        br.close()
        fr.close()

        mAdapter.notifyDataSetChanged()

    }
}