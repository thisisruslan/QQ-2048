package uz.gita.my2048game.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.my2048game.domain.AppRepository

class MainViewModel : ViewModel() {
    private val repository = AppRepository.getRepository()
    private val _arrayLiveData = MutableLiveData<Array<Array<Int>>>()
    val arrayLiveData: LiveData<Array<Array<Int>>> get() = _arrayLiveData
    private val _scoreLiveData = MutableLiveData<Int>()
    val scoreLiveData: LiveData<Int> get() = _scoreLiveData
    private val _bestScoreLiveData = MutableLiveData<Int>()
    val bestScoreLiveData: LiveData<Int> get() = _bestScoreLiveData

    init {
        _arrayLiveData.value = repository.array
        _scoreLiveData.value = repository.score
        _bestScoreLiveData.value = repository.bestScore1
    }

    fun saveLastNumbers() = repository.saveLastNumbers()
    fun saveUndoNumbers() = repository.saveUndoNumbers()
    fun saveUndoScore() = repository.saveUndoScore()
    fun saveUndoBestScore() = repository.saveUndoBestScore()
    fun getUndoNumbers() {
        _arrayLiveData.value = repository.getUndoNumbers()
        _scoreLiveData.value = repository.getUndoScore()
        _bestScoreLiveData.value = repository.getUndoBestScore()
    }

    fun reload() {
        repository.reload()
        _arrayLiveData.value = repository.array
        _scoreLiveData.value = repository.score
        _bestScoreLiveData.value = repository.bestScore1
    }

    fun isGameOver(): Boolean = repository.isGameOver()
    fun getScore(): Int = repository.score
    fun getBestScore(): Int = repository.getBestScore()
    fun saveLastScore(lastScore: Int) = repository.saveLastScore(lastScore)
    fun getLastScore(): Int = repository.getLastScore()
    fun isNearlyFull(): Boolean = repository.isNearlyFull()
    fun clearBestScore() {
        repository.clearBestScore()
        _bestScoreLiveData.value = repository.bestScore1
    }

    fun swipeLeft() {
        repository.swipeLeft()
        _arrayLiveData.value = repository.array
        _scoreLiveData.value = repository.score
        _bestScoreLiveData.value = repository.bestScore1
    }

    fun swipeRight() {
        repository.swipeRight()
        _arrayLiveData.value = repository.array
        _scoreLiveData.value = repository.score
        _bestScoreLiveData.value = repository.bestScore1
    }

    fun swipeUp() {
        repository.swipeUp()
        _arrayLiveData.value = repository.array
        _scoreLiveData.value = repository.score
        _bestScoreLiveData.value = repository.bestScore1
    }

    fun swipeDown() {
        repository.swipeDown()
        _arrayLiveData.value = repository.array
        _scoreLiveData.value = repository.score
        _bestScoreLiveData.value = repository.bestScore1
    }


}