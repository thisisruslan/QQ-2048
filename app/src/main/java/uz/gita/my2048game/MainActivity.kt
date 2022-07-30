package uz.gita.my2048game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host = supportFragmentManager.findFragmentById(R.id.appNavHost) as NavHostFragment
        val graph = host.navController.navInflater.inflate(R.navigation.navigation)

        graph.startDestination = R.id.mainScreen
        host.navController.graph = graph
    }

}

