package com.yujin.phonebook_210902

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.yujin.phonebook_210902.datas.PhoneNumData
import kotlinx.android.synthetic.main.activity_edit_phone_num.*
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class EditPhoneNumActivity : BaseActivity() {

//    선택된 날짜의 기본값 : 화면을 연 일시.
    val mSelectedDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_phone_num)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        okBtn.setOnClickListener {

//            1. 입력한 값들을 변수에 저장
            val inputName = nameEdt.text.toString()
            val inputPhoneNum = phoneNumEdt.text.toString()

//            생년월일 (Calendar) -> 1988-05-05   String  => SimpleDateFormat

            val sdf = SimpleDateFormat("yyyy-MM-dd")

//            날짜로 저장할 String은 폰번데이터 클래스가 같이 만들어줌.
//            val birthDayStr = sdf.format( mSelectedDate.time )

//            2. 폰번 데이터 객체로 만들자. (클래스 추가)

            val savePhoneNumData = PhoneNumData(inputName, inputPhoneNum)
//            폰번데이터의 생년월일 -> 선택한날짜에 적힌 년월일 그대로 대입.
//            하나의 Calendar값 -> 다른 Calendar에 옮겨적기
            savePhoneNumData.birthDay.time = mSelectedDate.time

//            3. 해당 폰번을 -> "이름,폰번,생년월일" 양식으로 가공 -> 파일에 저장.

            val saveStr = savePhoneNumData.getFileFormatData()
            Log.d("파일에저장할문장", saveStr)


            savePhoneNumToFile(saveStr)

//            4. 토스트로 저장성공 안내 + 화면 종료
            Toast.makeText(mContext, "전화번호가 추가로 저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()


        }

        selectBirthDayBtn.setOnClickListener {

//            달력처럼, 날짜 선택 팝업 출현 (안배운 부분)

            val dateSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                    Log.d("선택된 월", month.toString())

//                    날짜가 선택되면 실행해줄 코드. (년 / 월 / 일 세개의 Int를 가지고 처리하자.)

                    mSelectedDate.set(year, month, dayOfMonth)

//                    날짜 선택이 완료 (Calendar) 되면 -> birthDayTxt 에 반영 (String으로 반영).
//                    Calendar -> String 가공 : SimpleDateFormat
//                    1988. 08. 08.  양식으로 가공.

                    val sdf = SimpleDateFormat("yyyy. MM. dd.")

                    birthDayTxt.text = sdf.format(  mSelectedDate.time  )



                }
            }

//            달력 띄울때 넣는 날짜? 기본 선택값을 넣자.
//            기본 : 오늘 날짜.=> Calendar를 하나 만들면 : 기본값이 오늘날짜.
//            Calendar 만든다 : 멤버변수로 만들어서 -> 선택된 값을 저장하는 용도로도.
            val datePickerDialog = DatePickerDialog(mContext, dateSetListener,
                mSelectedDate.get(Calendar.YEAR), mSelectedDate.get(Calendar.MONTH), mSelectedDate.get(Calendar.DAY_OF_MONTH))

            datePickerDialog.show()

        }

    }

    override fun setValues() {

    }

//    문장을 넘겨받아서, 폰의 파일에 기록해주는 기능.

    fun savePhoneNumToFile(content : String) {

//        파일 저장용 폴더에, phoneBook.txt 파일로 경로 설정.
        val myFile = File(filesDir, "phoneBook.txt")

        val fw = FileWriter(myFile, true)
        val bw = BufferedWriter(fw)

        bw.append(content)
        bw.newLine()

        bw.close()
        fw.close()

        Log.d("파일추가", content)

    }

}