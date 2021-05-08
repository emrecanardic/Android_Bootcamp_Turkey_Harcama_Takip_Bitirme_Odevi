package com.emrecan.harcamatakip.View

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.canerkaya.harcamatakip.R
import com.canerkaya.harcamatakip.Util.CustomSharedPreferences
import com.emrecan.harcamatakip.R
import com.emrecan.harcamatakip.Util.CustomSharedPreferences
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment

class IntroActivity:AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(
            AppIntroFragment.newInstance(
            title = "Harcamalarınızda Karışıklığa Son!",
            description = "Karşınızda Harcama Takip Uygulaması",
                backgroundColor = resources.getColor(R.color.colorSplash),
                titleColor = Color.DKGRAY,
                imageDrawable = R.drawable.splash_logo
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "Hazırsanız Başlayalım :)",
            description = "Harcamalarınızı dilediğiniz para biriminde kaydedin ve 4 farklı kurda otomatik olarak kaydedilsin",
            backgroundColor = resources.getColor(R.color.colorSplash),
            titleColor = Color.DKGRAY,
            imageDrawable = R.drawable.onboard_logo
        ))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        CustomSharedPreferences(applicationContext).setFirst()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        CustomSharedPreferences(applicationContext).setFirst()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}