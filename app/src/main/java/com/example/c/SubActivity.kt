package com.example.c

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Display.Mode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.Date

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
//        var message: String = ""
        val backbtn: Button = findViewById(R.id.back_btn)
        val savebtn: Button = findViewById(R.id.save_btn)
        val diaryedit: EditText = findViewById(R.id.diary_content)
        var message = ""
        var name = intent.getStringExtra("name")
        savebtn.isEnabled=false

        // 뒤로가기 버튼
        backbtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        savebtn.setOnClickListener{
            var output = openFileOutput("${name}.dat", Context.MODE_PRIVATE)
            var dos = DataOutputStream(output)
            dos.writeUTF(diaryedit.text.toString())
            dos.flush()
            dos.close()
            Toast.makeText(this, "저장됨",Toast.LENGTH_SHORT).show()
            backbtn.callOnClick()
        }
//         값의 변화를 체크하는 이벤트
        diaryedit.addTextChangedListener (object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, s1: Int, s2: Int, s3: Int){}
            // 값이 변경되면 실행되는 함수
            override fun onTextChanged(s: CharSequence?, s1: Int, s2:Int, s3: Int){
                // 입력값 담시
                message = diaryedit.text.toString()
                // 값 유무에 따른 버튼 활성화 여부
                savebtn.isEnabled = message.isNotEmpty() // 있으면 true 없으면 false
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        )



        // 일기 저장
//        diarybtn.setOnClickListener{
//            // 저장 객체
//            val shared: SharedPreferences = getSharedPreferences("shared", Activity.MODE_PRIVATE)
//            val editor: SharedPreferences.Editor = shared.edit()
//
//
//            val diarytext = diaryedit.text.toString()
//
//            // key, value 형태로 데이터 저장
//            editor.putString("day", diarytext).apply()
////            editor.commit()
//            // 해본 결과 이거 toast메시지가 나와서 값이 저장되는거 같은데여?
//            Toast.makeText(this, "저장됨",Toast.LENGTH_SHORT).show()
//            saveDiary(day)
//        }
    }
//    fun data(){
//        val dt:TextView = findViewById(R.id.diary_text)
//        val shared: SharedPreferences = getSharedPreferences("shared", Activity.MODE_PRIVATE)
//        if (shared != null){
//            val contents = shared.getString("day", "")
//            diaryedit.setText(contents)
//            Toast.makeText(this,"값 불러오기 성공" ,Toast.LENGTH_SHORT).show()
//        }
//    }

//    fun saveDiary(day: String?){
//        var fileOutputStream: FileOutputStream
//        try{
//            fileOutputStream = openFileOutput(day, MODE_PRIVATE)
//            val content = diaryedit.text.toString()
//            fileOutputStream.write(content.toByteArray())
//            fileOutputStream.close()
//        }catch(e:java.lang.Exception){
//            e.printStackTrace()
//        }
//    }
}