package uz.gita.pirmuhammad.mvp.presenter


import uz.gita.pirmuhammad.mvp.contract.PlayContract
import uz.gita.pirmuhammad.mvp.models.PlayModel

class PlayPresenter(private val view: PlayContract.View) : PlayContract.Presenter {
    private val model: PlayContract.Model = PlayModel()

    override fun startPlay() {
        view.illustrateMatrix(model.getMatrixData())
        view.setHighScore(model.getHighScore())
    }

    override fun swipeSideLeft() {
        val a = model.moveLeft()
        view.playAnim(model.getAnimCoordinate())
        if (a) view.gameOver()
        view.setScore(model.getScore())
        model.saveHighScore(model.getHighScore())
        view.illustrateMatrix(model.getMatrixData())
    }

    override fun swipeSideRight() {
        val a = model.moveRight()
        view.playAnim(model.getAnimCoordinate())
        if (a) view.gameOver()
        view.setScore(model.getScore())
        model.saveHighScore(model.getHighScore())
        view.illustrateMatrix(model.getMatrixData())
    }

    override fun swipeSideUp() {
        val a = model.moveUp()
        view.playAnim(model.getAnimCoordinate())
        if (a) view.gameOver()
        view.setScore(model.getScore())
        model.saveHighScore(model.getHighScore())
        view.illustrateMatrix(model.getMatrixData())
    }
    override fun swipeSideDown() {
        val a = model.moveDown()
        view.playAnim(model.getAnimCoordinate())
        if (a) view.gameOver()
        view.setScore(model.getScore())
        model.saveHighScore(model.getHighScore())
        view.illustrateMatrix(model.getMatrixData())
    }

    override fun reload() {
        model.reload()
        view.setScore(0)
        view.illustrateMatrix(model.getMatrixData())
    }

    override fun setScore(score: Int) {
        view.setScore(score)
    }

    override fun setHighScore(score: Int) = model.saveHighScore(score)


    override fun setOnClick() {}

    override fun gameOver() {
        view.gameOver()
    }

    override fun setRepository() {
        view.setHighScore(model.getHighScore())
    }

    override fun saveMatrix(matrix: Array<Array<Int>>) {
        model.saveMatrix(matrix)
    }

    override fun getShareMatrix() {
        view.setHighScore(model.getHighScore())
        var m = model.getSharedMatrix()
        if (m == null) m = model.getMatrixData()
        view.illustrateMatrix(m)
    }
}