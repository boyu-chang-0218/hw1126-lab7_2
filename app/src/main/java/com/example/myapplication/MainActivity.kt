package com.example.myapplication

import android.icu.number.IntegerWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope

class MainActivity : AppCompatActivity() {
    var prograss1 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_calculate.setOnClickListener {
            when{
                ed_height.length() < 1 -> Toast.makeText( this,
                "請輸入身高",Toast.LENGTH_SHORT).show()
                ed_weight.length() < 1 -> Toast.makeText( this,
                "請輸入體重",Toast.LENGTH_SHORT).show()
                else -> runCoroutine() //執行副程式Coroutine
            }
        }
    }
    private fun  runCoroutine() {
        GlobalScope.launch{
            var a = 0
            //值小於100重複執行
            while (a <= 100) {
                try {
                    delay( 50L)//延遲0.05S
                    val msg = Message() //建立物件
                    msg.what = 1//代號
                    mHandler.sendMessage(msg)//sendMessage傳訊息
                    a++ //計數+1
                    prograss1 = a //更新進度
                }catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
    private  val mHandler = Handler{msg ->
        when (msg.what) {
            1 -> progressBar2!!.progress = prograss1!!
        }
        when(msg.what) {
            1 -> tv_progress!!.text = prograss1.toString() + "%"
        }
        ll_progress!!.visibility = View.VISIBLE
        if (prograss1 >= 100) {
            ll_progress!!.visibility = View.GONE
            val h = Integer.valueOf(ed_height!!.text.toString())//身高
            val w = Integer.valueOf(ed_weight!!.text.toString())//體重
            val  standWeight: Double
            val bodyFat: Double
            if (btn_boy!!.isChecked) { //男生
                standWeight = (h-80) * 0.7
                bodyFat = (w - 0.88 * standWeight) / w * 100
            }else { //女生
                standWeight = (h-70) * 0.6
                bodyFat = (w - 0.82 * standWeight) / w * 100
            }
            tv_weight!!.text = String.format("標準體重\n%.2f", standWeight)
            tv_bmi!!.text = String.format("體脂肪\n%.2f", bodyFat)
        }
        false
    }
}