package uz.gita.pirmuhammad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uz.gita.pirmuhammad.mvp.views.MainScreen
import uz.gita.pirmuhammad.mvp.views.PlayScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainScreen()
        mainFragment.setPlayScreenListener {
            val playFragment = PlayScreen()
            openScreen(playFragment)
        }

        mainFragment.setOnResumeClickListener {
            val playFragment = PlayScreen()
            val bundle = Bundle()
            bundle.putString("key", "resume")
            playFragment.arguments = bundle
            openScreen(playFragment)
        }

        openScreenAddStack(mainFragment)
    }

    private fun openScreenAddStack(fm: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.containerView, fm)
            .commit()
    }


    private fun openScreen(fm: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerView, fm)
            .addToBackStack(fm.javaClass.name)
            .commit()
    }


}