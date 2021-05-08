package com.emrecan.harcamatakip.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.canerkaya.harcamatakip.Util.CustomSharedPreferences
import com.emrecan.harcamatakip.Util.CustomSharedPreferences

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val first = CustomSharedPreferences(applicationContext).isFirst()
        if (first != "not"){
            val intent = Intent(this,IntroActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}