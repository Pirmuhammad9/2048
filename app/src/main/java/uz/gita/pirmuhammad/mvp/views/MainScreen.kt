package uz.gita.pirmuhammad.mvp.views

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import uz.gita.pirmuhammad.R
import uz.gita.pirmuhammad.mvp.contract.MainContract
import uz.gita.pirmuhammad.mvp.presenter.MainPresenter

class MainScreen : Fragment(R.layout.screen_main), MainContract.View {
    private var openPlayScreenListener: (() -> Unit)? = null
    private var onResumeClickListener: (() -> Unit)? = null

    private val presenter: MainContract.Presenter = MainPresenter(this)
    private var bool = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonPlay: TextView = view.findViewById(R.id.buttonPlay)
        val resume: TextView = view.findViewById(R.id.resume)
        resume.setOnClickListener { presenter.clickContinueButton() }
        buttonPlay.setOnClickListener { presenter.clickPlayButton() }
        val info: ImageView = view.findViewById(R.id.info)
        info.setOnClickListener {
            presenter.clickAboutButton()
        }
    }

    fun setPlayScreenListener(block: () -> Unit) {
        bool = false
        openPlayScreenListener = block
    }

    override fun openPlayScreen() {
        openPlayScreenListener?.invoke()
    }

    override fun openAboutScreen() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_info, null, false)
        val dialog =
            AlertDialog.Builder(requireContext()).setCancelable(false).setView(dialogView)
                .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialogView.findViewById<TextView>(R.id.ok).setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun openContinueScreen() {
        if (presenter.isAvailableData()){
            onResumeClickListener?.invoke()
        }else{
            Toast.makeText(requireContext(), "You should play first", Toast.LENGTH_SHORT).show()
        }
    }

    fun setOnResumeClickListener(bl: () -> Unit) {
        onResumeClickListener = bl
    }
}