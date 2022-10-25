package uz.gita.pirmuhammad.data.repository.Impl

import android.util.Log
import uz.gita.pirmuhammad.data.local.pref.SharedPref
import uz.gita.pirmuhammad.data.repository.AppRepository
import kotlin.random.Random

class AppRepositoryImpl private constructor() : AppRepository {
    private var score: Int = 0;
    private val shared = SharedPref.getInstance()
    private var stateTop: Boolean = true
    private var stateBottom: Boolean = true
    private var stateRight: Boolean = true
    private var stateLeft: Boolean = true
    private lateinit var pairs: ArrayList<Pair<Int, Int>>
    private var pair: Pair<Int, Int> = Pair(-1, -1)
    private var values = ArrayList<Int>()
    private var matrixClone: Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    companion object {
        private var obj: AppRepository? = null
        fun init() {
            if (obj != null) return
            obj = AppRepositoryImpl()
        }

        fun getAppRepository(): AppRepository {
            return obj!!
        }
    }

    private var ADD_ELEMENT = 2
    private var matrix: Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    init {
        addNewElementToMatrix()
        addNewElementToMatrix()
    }

    override fun getMatrixData(): Array<Array<Int>> = matrix

    override fun moveLeft(): Boolean {
        cloneMatrix()
        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var b = true
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[i][j])
                else {
                    if (amount.last() == matrix[i][j] && b) {
                        score += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        b = false
                    } else {
                        b = true
                        amount.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }
            for (j in amount.indices) {
                matrix[i][j] = amount[j]
            }
        }
        if (checkForEquality()) {
            addNewElementToMatrix()
            stateLeft = true
        } else {
            pair = Pair(-1, -1)
            stateLeft = false
            if (!stateTop && !stateLeft && !stateBottom && !stateRight) return true
        }
        return false
    }

    private fun addNewElementToMatrix() {
        val coordinates = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) {
                    coordinates.add(Pair(i, j))
                }
            }
        }
        if (coordinates.size > 0) {
            val randomIndex = Random.nextInt(0, coordinates.size)
            matrix[coordinates[randomIndex].first][coordinates[randomIndex].second] = ADD_ELEMENT
            pair = Pair(coordinates[randomIndex].first, coordinates[randomIndex].second)
        }
    }

    override fun moveRight(): Boolean {
        cloneMatrix()
        values = ArrayList()
        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[i][j])
                else {
                    if (amount.last() == matrix[i][j] && bool) {
                        score += amount.last() * 2
                        values.add(amount.last() * 2)
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        amount.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }
            var l = -1
            for (k in 0 until amount.size) {
                l++
                matrix[i][3 - k] = amount[k]
            }
        }

        if (checkForEquality()) {
            addNewElementToMatrix()
            stateRight = true
        } else {
            stateRight = false
            pair = Pair(-1, -1)
            if (!stateTop && !stateLeft && !stateBottom && !stateRight) return true
        }
        return false
    }
    override fun moveTop(): Boolean {
        cloneMatrix()
        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].indices) {
                if (matrix[j][i] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[j][i])
                else {
                    if (amount.last() == matrix[j][i] && bool) {
                        score += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }
            for (j in 0 until amount.size) {
                matrix[j][i] = amount[j]
            }
        }
        if (checkForEquality()) {
            addNewElementToMatrix()
            stateTop = true
        } else {
            stateTop = false
            pair = Pair(-1, -1)
            if (!stateTop && !stateLeft && !stateBottom && !stateRight) return true
        }
        return false
    }
    override fun moveBottom(): Boolean {
        cloneMatrix()
        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[j][i] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[j][i])
                else {
                    if (amount.last() == matrix[j][i] && bool) {
                        score += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }
            for (j in amount.size - 1 downTo 0) {
                matrix[3 - j][i] = amount[j]
            }
        }
        if (checkForEquality()) {
            addNewElementToMatrix()
            stateBottom = true
        } else {
            stateBottom = false
            pair = Pair(-1, -1)
            if (!stateTop && !stateLeft && !stateBottom && !stateRight) return true
        }
        return false
    }

    override fun reload() {
        matrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        addNewElementToMatrix()
        addNewElementToMatrix()
        score = 0
    }

    override fun getScore() = score
    override fun getHighScore(): Int = shared.score
    override fun saveHighScore(score: Int) {
        shared.score = score
    }

    override fun playAnim(): Pair<Int, Int> {
        return pair
    }
    private fun cloneMatrix() {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrixClone[i][j] = matrix[i][j]
            }
        }
    }
    private fun checkForEquality(): Boolean {
        var b = false
        pairs = ArrayList()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrixClone[i][j] != matrix[i][j]) {
                    b = true
                }
            }
        }
        return b
    }
    override fun saveMatrix(matrix: Array<Array<Int>>) {
        val st = StringBuilder()
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                st.append(matrix[i][j]).append("#")
            }
        }
        shared.matrix = st.toString()
    }
    override fun getSharedMatrix(): Array<Array<Int>>? {
        shared.matrix?.let {
            if (it.isNotEmpty()){
                val st = it.split("#")
                val matrixShared: Array<Array<Int>> = arrayOf(
                    arrayOf(0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0)
                )
                var k = 0
                for (i in matrixShared.indices) {
                    for (j in 0 until matrixShared[i].size) {
                        matrixShared[i][j] = st[k].toInt()
                        k++
                    }
                }
                matrix = matrixShared
                return matrixShared
            }
        }
        return null
    }
}