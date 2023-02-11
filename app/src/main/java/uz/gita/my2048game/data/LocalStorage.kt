package uz.gita.my2048game.data

import android.content.Context
import android.content.SharedPreferences
import uz.gita.my2048game.app.App

class LocalStorage private constructor() {
    private val instances: SharedPreferences = App.instance.getSharedPreferences("storage", Context.MODE_PRIVATE)

    companion object {
        private lateinit var instances: LocalStorage

        fun getInstance(): LocalStorage {
            if (!::instances.isInitialized) {
                instances = LocalStorage()
            }
            return instances
        }
    }
//    var isFirstRun : Boolean = true
    fun setIsFirstRun(state: Boolean) = instances.edit().putBoolean("isFirst", state).apply()
    fun getIsFirstRun() : Boolean = instances.getBoolean("isFirst", true)

    //save user's last score
    fun saveLastScore(score: Int) = instances.edit().putInt("score", score).apply()
    fun getLastScore(): Int = instances.getInt("score", 0)

    //save best scores
    fun saveBestScore(score: Int) = instances.edit().putInt("bestScore", score).apply()
    fun getBestScore(): Int = instances.getInt("bestScore", 0)

    //save last numbers in matrix
    fun saveLastNumbers(numbers: String) = instances.edit().putString("numbers", numbers).apply()
    fun getLastNumbers() : String = instances.getString("numbers", "0").toString()

    //save state before last state
    fun saveUndoNumbers(numbers: String) = instances.edit().putString("undoNumbers", numbers).apply()
    fun getUndoNumbers(): String = instances.getString("undoNumbers", "").toString()

    //save score before last score
    fun saveUndoScore(score: Int) = instances.edit().putInt("undoScore", score).apply()
    fun getUndoScore() : Int = instances.getInt("undoScore", 0)


    //save bestScore before last bestScore
    fun saveUndoBestScore(bestScore: Int) = instances.edit().putInt("undoBestScore", bestScore).apply()
    fun getUndoBestScore() : Int = instances.getInt("undoBestScore", 0)





}