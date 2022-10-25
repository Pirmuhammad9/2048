package uz.gita.pirmuhammad.mvp.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.button.MaterialButton
import uz.gita.pirmuhammad.R
import uz.gita.pirmuhammad.data.SideEnum
import uz.gita.pirmuhammad.mvp.contract.PlayContract
import uz.gita.pirmuhammad.mvp.presenter.PlayPresenter
import uz.gita.pirmuhammad.utils.MyTouchListener
import uz.gita.pirmuhammad.utils.getBackroundByAmount

class PlayScreen : Fragment(R.layout.screen_play), PlayContract.View {
    private val presenter: PlayContract.Presenter = PlayPresenter(this)
    private val buttons: MutableList<TextView> = ArrayList(16)
    private lateinit var currentScore: TextView
    private lateinit var highScore: TextView
    private lateinit var reload: ImageView
    private lateinit var gameOver: TextView
    private lateinit var main: LinearLayoutCompat
    private var pair = Pair(-1, -1)
    private var resume = ""
    private var matrix:Array<Array<Int>>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            resume = it?.get("key") as String ?: ""
        }
        currentScore = view.findViewById(R.id.currenScore)
        highScore = view.findViewById(R.id.highScoreSet)
        gameOver = view.findViewById(R.id.gameOver)
        main = view.findViewById(R.id.mainView)
        loadViews(view)
        if (resume.isNotEmpty()) presenter.getShareMatrix()
        else presenter.startPlay()
        reload = view.findViewById(R.id.reload)
        reload.setOnClickListener {
            if (gameOver.visibility == View.VISIBLE) {
                gameOver.visibility = View.GONE
                presenter.reload()
                main.isEnabled = true
            } else {
                val dialogView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.dialog, null, false)
                val dialog =
                    AlertDialog.Builder(requireContext()).setView(dialogView).setCancelable(false)
                        .create()
                dialog.show()
                dialogView.findViewById<MaterialButton>(R.id.cancelButton).setOnClickListener {
                    dialog.dismiss()
                }
                dialogView.findViewById<MaterialButton>(R.id.okButton).setOnClickListener {
                    dialog.dismiss()
                    presenter.reload()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setHighScore(currentScore.text.toString().toInt())
                val dialogView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.dialog_exit, null, true)
                val dialog =
                    AlertDialog.Builder(requireContext()).setView(dialogView).setCancelable(false)
                        .create()
                dialog.show()
                dialogView.findViewById<MaterialButton>(R.id.cancelButton).setOnClickListener {
                    dialog.dismiss()
                }
                dialogView.findViewById<MaterialButton>(R.id.okButton).setOnClickListener {
                    dialog.dismiss()
                    presenter.saveMatrix(matrix!!)
                    requireActivity().supportFragmentManager.popBackStack()
                    presenter.reload()
                }
            }
        })
    }

    private fun loadViews(view: View) {
        presenter.setRepository()
        val mainView: LinearLayoutCompat = view.findViewById(R.id.mainView)
        for (i in 0 until mainView.childCount) {
            val line: LinearLayoutCompat = mainView.getChildAt(i) as LinearLayoutCompat
            for (j in 0 until line.childCount) {
                buttons.add(line.getChildAt(j) as TextView)
            }
        }
        val myTouchListener = MyTouchListener(requireContext())
        myTouchListener.setResultListener {
            when (it) {
                SideEnum.DOWN -> presenter.swipeSideDown()
                SideEnum.UP -> presenter.swipeSideUp()
                SideEnum.LEFT -> presenter.swipeSideLeft()
                SideEnum.RIGHT -> presenter.swipeSideRight()
            }
        }
        mainView.setOnTouchListener(myTouchListener)
    }

    override fun illustrateMatrix(matrix: Array<Array<Int>>) {
        this.matrix = matrix
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) buttons[4 * i + j].text = ""
                else {
                    buttons[4 * i + j].text = matrix[i][j].toString()
                    if (pair.first == i && pair.second == j) {
                        YoYo.with(Techniques.BounceIn).duration(500).playOn(buttons[4 * i + j])
                    }
                }
                buttons[4 * i + j].setBackgroundResource(matrix[i][j].getBackroundByAmount())
            }
        }
    }

    override fun setScore(score: Int) {
        currentScore.text = score.toString()
        if (score > highScore.text.toString().toInt()) {
            highScore.text = score.toString()
            setHighScore(score)
        }
    }

    override fun setHighScore(score: Int) {
        presenter.setHighScore(score)
        if (score > highScore.text.toString().toInt()) {
            highScore.text = score.toString()
        }
    }

    override fun gameOver() {
        gameOver.visibility = View.VISIBLE
        YoYo.with(Techniques.FadeIn).duration(500).playOn(gameOver)
        reload.setBackgroundResource(R.drawable.bg_reload)
        main.isEnabled = false
    }

    override fun playAnim(pair: Pair<Int, Int>) {
        this.pair = pair
    }

}
