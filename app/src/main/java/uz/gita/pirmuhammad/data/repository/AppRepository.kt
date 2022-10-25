package uz.gita.pirmuhammad.data.repository

interface AppRepository {
    fun getMatrixData(): Array<Array<Int>>
    fun moveLeft():Boolean
    fun moveRight():Boolean
    fun moveTop():Boolean
    fun moveBottom():Boolean
    fun reload()
    fun getScore():Int
    fun getHighScore():Int
    fun saveHighScore(score:Int)
    fun playAnim():Pair<Int, Int>
    fun getSharedMatrix():Array<Array<Int>>?
    fun saveMatrix(matrix: Array<Array<Int>>)
}