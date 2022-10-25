package uz.gita.pirmuhammad.mvp.models

import android.util.Log
import uz.gita.pirmuhammad.data.repository.AppRepository
import uz.gita.pirmuhammad.data.repository.Impl.AppRepositoryImpl
import uz.gita.pirmuhammad.mvp.contract.PlayContract

class PlayModel : PlayContract.Model {

    private val repository: AppRepository = AppRepositoryImpl.getAppRepository()

    override fun getMatrixData(): Array<Array<Int>> = repository.getMatrixData()
    override fun moveLeft():Boolean = repository.moveLeft()
    override fun moveRight():Boolean = repository.moveRight()
    override fun moveUp():Boolean = repository.moveTop()
    override fun moveDown():Boolean =repository.moveBottom()
    override fun reload() = repository.reload()
    override fun getScore(): Int = repository.getScore()
    override fun getHighScore(): Int = repository.getHighScore()
    override fun saveHighScore(score: Int) = repository.saveHighScore(score)
    override fun getAnimCoordinate(): Pair<Int, Int> = repository.playAnim()
    override fun saveMatrix(matrix: Array<Array<Int>>) = repository.saveMatrix(matrix)
    override fun getSharedMatrix(): Array<Array<Int>>? = repository.getSharedMatrix()

}