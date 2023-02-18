package me.ruslan1024.kk2048game.ui.screen

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import me.ruslan1024.kk2048game.R
import me.ruslan1024.kk2048game.data.MySide
import me.ruslan1024.kk2048game.databinding.ScreenMainBinding
import me.ruslan1024.kk2048game.ui.viewmodels.MainViewModel
import me.ruslan1024.kk2048game.utils.MyTouchListener
import me.ruslan1024.kk2048game.utils.background
import me.ruslan1024.kk2048game.utils.showToast

@Keep
class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel by viewModels<MainViewModel>()
    private val views = ArrayList<TextView>(16)

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadView()


        val touchListener = MyTouchListener(requireContext())
        touchListener.setSideListener {
            if (viewModel.isNearlyFull()) {
                viewModel.saveUndoScore()
                viewModel.saveUndoBestScore()
                viewModel.saveUndoNumbers()
            }
            when (it) {
                MySide.LEFT -> {
                    viewModel.swipeLeft()
                }
                MySide.RIGHT -> {
                    viewModel.swipeRight()
                }
                MySide.UP -> {
                    viewModel.swipeUp()
                }
                MySide.DOWN -> {
                    viewModel.swipeDown()
                }
            }
            if (viewModel.isGameOver()) {
                binding.image.isClickable = true
                val alertDialog = AlertDialog.Builder(requireContext())
                        .setTitle("Game Over")
                        .setMessage("Try again!")
                        .setCancelable(false)
                        .setPositiveButton("New Game") { _: DialogInterface?, _: Int ->
                            viewModel.reload()
                            binding.image.isClickable = false
                        }
                        .setNegativeButton("Undo") { dialog: DialogInterface, _: Int ->
                            viewModel.getUndoNumbers()
                            binding.image.isClickable = false
                            dialog.dismiss()
                        }
                        .create()
                alertDialog.show()
            }
        }

        binding.group.setOnTouchListener(touchListener)
        binding.buttonReload.setOnLongClickListener {
            showToast("Reload")
            return@setOnLongClickListener true
        }
        binding.buttonReload.setOnClickListener {
            viewModel.reload()
        }
        binding.buttonMenu.setOnClickListener { menuClickListener(it) }


        viewModel.arrayLiveData.observe(viewLifecycleOwner, arrayObserver)
        viewModel.scoreLiveData.observe(viewLifecycleOwner, scoreObserver)
        viewModel.bestScoreLiveData.observe(viewLifecycleOwner, bestScoreObserver)
    }

    private fun loadView() {
        binding.bestScore.text = viewModel.getBestScore().toString()
        for (i in 0 until binding.group.childCount) {
            val l = binding.group[i] as LinearLayoutCompat
            for (j in 0 until l.childCount) {
                views.add(l[j] as TextView)
            }
        }
    }

    private val bestScoreObserver = Observer<Int> {
        binding.bestScore.text = it.toString()
    }
    private val scoreObserver = Observer<Int> {
        binding.score.text = it.toString()
    }
    private val arrayObserver = Observer<Array<Array<Int>>> {
        for (i in it.indices) {
            for (j in it[i].indices) {
                views[i * 4 + j].apply {
                    text = if (it[i][j] == 0) ""
                    else "${it[i][j]}"
                    setBackgroundResource(background(it[i][j]))
                }
            }
        }
    }

    private fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val text = """
             https://play.google.com/store/apps/details?id=${requireActivity().packageName}
             """.trimIndent()
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, "Share:"))
    }

    private fun rateApp() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=${requireActivity().packageName}")
        startActivity(intent)
    }

    private fun menuClickListener(view: View) {
        val optionsMenu = PopupMenu(requireContext(), view)
        val inflater = optionsMenu.menuInflater
        inflater.inflate(R.menu.menu, optionsMenu.menu)
        optionsMenu.show()
        optionsMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_clear -> {
                    val alertDialog = AlertDialog.Builder(requireContext())
                            .setMessage("Do you want to clear all?")
                            .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                                viewModel.reload()
                                viewModel.clearBestScore()
                            }
                            .setNegativeButton("No") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
                            .create()
                    alertDialog.show()
                }
                R.id.menu_share -> {
                    shareApp()
                }
                R.id.menu_rate -> {
                    rateApp()
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.getScore() == 0) {
            binding.score.text = viewModel.getLastScore().toString()
        } else binding.score.text = viewModel.getScore().toString()
        if (viewModel.isGameOver()) {
            viewModel.reload()
            binding.score.text = "0"
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveLastNumbers()
        viewModel.saveLastScore(viewModel.getScore())
    }

}