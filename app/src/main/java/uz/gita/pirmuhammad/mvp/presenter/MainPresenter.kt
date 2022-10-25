package uz.gita.pirmuhammad.mvp.presenter


import uz.gita.pirmuhammad.mvp.contract.MainContract
import uz.gita.pirmuhammad.mvp.models.MainModel

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    private val mainModel = MainModel()

    override fun clickPlayButton() {
        view.openPlayScreen()
    }

    override fun clickAboutButton() {
        view.openAboutScreen()
    }

    override fun clickContinueButton() {
       view.openContinueScreen()
    }

    override fun isAvailableData(): Boolean {
        if (mainModel.isAvailableData() != null) return true
        return false
    }
}