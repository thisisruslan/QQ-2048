package uz.gita.my2048game.utils

import uz.gita.my2048game.R

object BackgroundUtils {
    val bgList = arrayOf(
        R.drawable.bg_item_0,
        R.drawable.bg_item_2,
        R.drawable.bg_item_4,
        R.drawable.bg_item_8,
        R.drawable.bg_item_16,
        R.drawable.bg_item_32,
        R.drawable.bg_item_64,
        R.drawable.bg_item_128,
        R.drawable.bg_item_256,
        R.drawable.bg_item_512,
        R.drawable.bg_item_1024,
        R.drawable.bg_item_2048,
        R.drawable.bg_item_4096,
        R.drawable.bg_item_8192,
        R.drawable.bg_item_16384,
        R.drawable.bg_item_32768,
        R.drawable.bg_item_65536
    )
}

fun background(_amount :Int) : Int {
    var degree = 0
    var amount = _amount

    while (amount > 0) {
        degree ++
        amount /=2
    }
    return BackgroundUtils.bgList[degree]
}