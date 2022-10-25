package uz.gita.pirmuhammad.mvp.contract

interface MainContract {

    interface Model{
        fun isAvailableData():Boolean
    }

    interface View {
        fun openPlayScreen()
        fun openAboutScreen()
        fun openContinueScreen()
    }

    interface Presenter {
        fun clickPlayButton()
        fun clickAboutButton()
        fun clickContinueButton()
        fun isAvailableData():Boolean
    }

}