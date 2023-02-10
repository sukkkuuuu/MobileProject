package com.example.c

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import java.io.DataInputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
//import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat

import java.util.Date


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 객체 생성
        var dayText: TextView = findViewById(R.id.day_text)
        val headerText: TextView = findViewById(R.id.header_text)
        val calendarView: CalendarView = findViewById(R.id.calendarView)
        var btn: Button = findViewById(R.id.btn)
        val actionVar = supportActionBar

        actionVar?.title = "일기장"
        // 날짜 형태
        val dateFormat: DateFormat = SimpleDateFormat("yyyy년MM월dd일")
        // date타입
        val date = Date(calendarView.date)
        dayText.text = dateFormat.format(date)

        var name: String
        name = "${date.year + 1900}${if(date.month < 9) "0" + (date.month + 1).toString() else date.month + 1}${if(date.day + 5 < 10) "0" + (date.day + 5).toString() else date.day + 5}"

        val intent = Intent(this, SubActivity::class.java)

        intent.putExtra("name", name)
        btn.setOnClickListener {
            startActivity(intent)
            println("실행되기는 함")
        }
        calendarView.maxDate = date.time

        fun checkDay(calendarView: CalendarView, year: Int, month: Int, dayOfMonth: Int){
            var day = "${year}년 ${if(month < 9) "0" + (month + 1).toString() else month + 1}월 ${if(dayOfMonth < 10) "0" + dayOfMonth.toString() else dayOfMonth}일"
            dayText.text = day
            try{
                name = "${year}${if(month < 9) "0" + (month + 1).toString() else month + 1}${if(dayOfMonth < 10) "0" + dayOfMonth.toString() else dayOfMonth}"
                intent.putExtra("name", name)
                println(name)
                var input = openFileInput("${name}.dat")
                var dis = DataInputStream(input)
                var value = dis.readUTF()
                dis.close()
                headerText.visibility =  View.VISIBLE
                headerText.text = value
                btn.visibility = View.INVISIBLE
                println("실행됨")

            }catch (e: IOException){
                headerText.visibility = View.INVISIBLE
                headerText.text = ""
                btn.visibility = View.VISIBLE
                println("실행안됨")
            }
        }

        checkDay(calendarView, date.year + 1900, date.month, date.day + 5)
        //CalendarView 날짜 변환 이벤트
        calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            checkDay(calendarView, year, month, dayOfMonth)
        }
    }


}