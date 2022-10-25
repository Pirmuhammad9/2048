package uz.gita.pirmuhammad.mvp.contract

interface PlayContract {
    interface Model {
        fun getMatrixData(): Array<Array<Int>>
        fun moveLeft(): Boolean
        fun moveRight(): Boolean
        fun moveUp(): Boolean
        fun moveDown(): Boolean
        fun reload()
        fun getScore(): Int
        fun getHighScore(): Int
        fun saveHighScore(score: Int)
        fun getAnimCoordinate(): Pair<Int, Int>
        fun saveMatrix(matrix: Array<Array<Int>>)
        fun getSharedMatrix(): Array<Array<Int>>?
    }

    interface View {
        fun illustrateMatrix(matrix: Array<Array<Int>>)
        fun setScore(score: Int)
        fun setHighScore(score: Int)
        fun gameOver()
        fun playAnim(pair: Pair<Int, Int>)
    }

    interface Presenter {
        fun startPlay()
        fun swipeSideLeft()
        fun swipeSideRight()
        fun swipeSideUp()
        fun swipeSideDown()
        fun reload()
        fun setScore(score: Int)
        fun setHighScore(score: Int)
        fun setOnClick()
        fun gameOver()
        fun setRepository()
        fun saveMatrix(matrix: Array<Array<Int>>)
        fun getShareMatrix()
    }
}


