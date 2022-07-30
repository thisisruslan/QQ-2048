package uz.gita.my2048game.domain

import uz.gita.my2048game.data.LocalStorage
import uz.gita.my2048game.utils.show
import uz.gita.my2048game.utils.timber
import kotlin.random.Random

class AppRepository private constructor() {
    companion object {
        private lateinit var instance: AppRepository
        fun getRepository(): AppRepository {
            if (!Companion::instance.isInitialized) {
                instance = AppRepository()
            }
            return instance
        }
    }

    var localStorage: LocalStorage = LocalStorage.getInstance()
    var score = localStorage.getLastScore();
    var bestScore1 = localStorage.getBestScore();


    private val ADD_AMOUNT = 2
    val array = arrayOf(arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0))

    init {
        if (localStorage.getIsFirstRun()) {
            start()
            localStorage.setIsFirstRun(false)
        } else {
            getLastNumbers()
        }
    }


//    fun isNewRecord(): Boolean {
//        if (score > bestScore1) {
//            return true
//        }
//        return false
//    }

    fun saveLastNumbers() {
        val builder = StringBuilder()
        for (i in 0 until 4)
            for (j in 0 until 4) {
                builder.append("${array[i][j]}@@@")
            }
        localStorage.saveLastNumbers(builder.toString())
    }

    private fun getLastNumbers() {
        val list = localStorage.getLastNumbers().split("@@@")
        var index = 0
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                array[i][j] = list[index++].toInt()
            }
        }
    }

    fun saveUndoScore() = localStorage.saveUndoScore(score)
    fun getUndoScore(): Int {
        score = localStorage.getUndoScore()
        return localStorage.getUndoScore()
    }

    fun saveUndoBestScore() = localStorage.saveUndoBestScore(bestScore1)
    fun getUndoBestScore(): Int {
        bestScore1 = localStorage.getUndoBestScore()
        return localStorage.getUndoBestScore()
    }


    fun getUndoNumbers(): Array<Array<Int>> {
        val list = localStorage.getUndoNumbers().split("@@@")
        var index = 0
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                array[i][j] = list[index++].toInt()
            }
        }
        return array
    }

    fun saveUndoNumbers() {
        val builder = StringBuilder()
        for (i in 0 until 4)
            for (j in 0 until 4) {
                builder.append("${array[i][j]}@@@")
            }
        localStorage.saveUndoNumbers(builder.toString())
    }


    fun getBestScore(): Int = localStorage.getBestScore()
    fun getLastScore(): Int = localStorage.getLastScore()
    fun saveLastScore(lastScore: Int) = localStorage.saveLastScore(lastScore)


    fun isNearlyFull(): Boolean {
        var counter = 0
        for (i in 0 until 4) {
            for (j in 0 until 4)
                if (array[i][j] == 0) {
                    counter++
                    if (counter == 2) return false
                }

        }
        return true
    }

    fun isFull(): Boolean {
        for (i in 0 until 4) {
            for (j in 0 until 4) if (array[i][j] == 0) return false
        }
        return true
    }

    fun isGameOver(): Boolean {
        if (!isFull()) return false
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (i - 1 >= 0) if (array[i - 1][j] == array[i][j]) return false
                if (i + 1 < 4) if (array[i + 1][j] == array[i][j]) return false
                if (j - 1 >= 0) if (array[i][j - 1] == array[i][j]) return false
                if (j + 1 < 4) if (array[i][j + 1] == array[i][j]) return false
            }
        }
        return true
    }

    fun clearBestScore() {
        bestScore1 = score
        localStorage.saveBestScore(score)
    }

    private fun start() {
        val list = ArrayList<Pair<Int, Int>>()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (array[i][j] == 0)
                    list.add(Pair(i, j))
            }
        }
        val index = Random.nextInt(0, list.size)
        var index2 = Random.nextInt(0, list.size)
        while (index2 == index) {
            index2 = Random.nextInt(0, list.size)
        }
        array[list[index].first][list[index].second] = ADD_AMOUNT
        array[list[index2].first][list[index2].second] = ADD_AMOUNT
    }

    fun reload() {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                array[i][j] = 0
            }
        }
        score = 0
        start()
    }

    private fun addNewAmount() {
        val list = ArrayList<Pair<Int, Int>>()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (array[i][j] == 0)
                    list.add(Pair(i, j))
            }
        }
        if (list.size != 0) {
            val index = Random.nextInt(0, list.size)
            array[list[index].first][list[index].second] = ADD_AMOUNT
        }
    }

    fun swipeLeft() {
        for (i in array.indices) {
            val list = ArrayList<Int>()
            for (j in 0 until 4) {
                if (array[i][j] != 0)
                    list.add(array[i][j])
            }
            var index = 0
            while (index < list.size - 1) {
                if (list[index] == list[index + 1]) {
                    list[index] *= 2
                    score += list[index]
                    if (score > bestScore1) {
                        bestScore1 = score
                        localStorage.saveBestScore(score)
                    }
                    list.removeAt(index + 1)
                }
                index++
            }


            for (j in 0 until 4) {
                array[i][j] = if (j < list.size) list[j]
                else 0
            }
        }
        addNewAmount()
    }

    fun swipeRight() {
        for (i in array.indices) {
            val list = ArrayList<Int>()
            for (j in array[i].indices) {
                if (array[i][j] != 0)
                    list.add(array[i][j])
            }
            var index = list.size - 1
            while (index > 0) {
                if (list[index] == list[index - 1]) {
                    list[index] *= 2
                    score += list[index]
                    if (score > bestScore1) {
                        bestScore1 = score
                        localStorage.saveBestScore(score)
                    }
                    list.removeAt(index - 1)
                    index--
                }
                index--
            }
            for (j in 0 until 4) {
                if (j < 4 - list.size) array[i][j] = 0
                else array[i][j] = list[j - (4 - list.size)]
            }
        }
        addNewAmount()
    }

    fun swipeUp() {
        for (j in array.indices) {
            val list = ArrayList<Int>()
            for (i in 0 until 4) {
                if (array[i][j] != 0)
                    list.add(array[i][j])
            }


            var index = 0
            while (index < list.size - 1) {
                if (list[index] == list[index + 1]) {
                    list[index] *= 2
                    score += list[index]
                    if (score > bestScore1) {
                        bestScore1 = score
                        localStorage.saveBestScore(score)
                    }
                    list.removeAt(index + 1)
                }
                index++
            }

            for (i in 0 until 4) {
                array[i][j] = if (i < list.size) list[i]
                else 0
            }
        }
        addNewAmount()
    }

    fun swipeDown() {
        for (j in array.indices) {
            val list = ArrayList<Int>()
            for (i in 0 until 4) {
                if (array[i][j] != 0)
                    list.add(array[i][j])
            }

            var index = list.size - 1
            while (index > 0) {
                if (list[index] == list[index - 1]) {
                    list[index] *= 2
                    score += list[index]
                    if (score > bestScore1) {
                        bestScore1 = score
                        localStorage.saveBestScore(score)
                    }
                    list.removeAt(index - 1)
                    index--
                }
                index--
            }
            timber(list.show())

            for (i in 0 until 4) {
                if (i < 4 - list.size) array[i][j] = 0
                else array[i][j] = list[i - (4 - list.size)]
            }
        }
        addNewAmount()
    }

}