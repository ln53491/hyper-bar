package com.example.hyperbar.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberImagePainter
import com.example.hyperbar.R
import com.example.hyperbar.screens.BoxState
import com.example.hyperbar.screens.ToggleButtonOption

fun rotation(
    height: Int,
    width: Int
): String{
    if(height > width){
        return "Portrait"
    } else {
        return "Landscape"
    }
}

var firstTime = false
var cartBool = true
var waiterFlag = false
var waiterId = 0
var waiterFromMain = true
var waiterProblem = false
var waiter = false
var ordered = false
var orderSuccessful = false
var triggerOrder = false
var triggerDoneOuter = false
var orderType = -1

var stateOfTheBox = BoxState.Collapsed

var selectedSaved = 0

var waiterLogout = false

var sessionOrders = mutableListOf<Long>()
var sessionOrdersDone = mutableListOf<Long>()

var pastOrderOrders: MutableList<Order> = mutableListOf()


var currentProductFirstTimeBool = true
var currentProduct = Product(
    categoryId = 1,
    id = 1,
    name = "Blue Hawaiian",
    description = "",
    priceEur = 5.49,
    priceHrk = 10,
    servingSize = 0.33,
    imageUrl = "https://i.ibb.co/ydqYwDP/flat-white.webp"
)

class MyButton {
    val options = arrayOf(
        ToggleButtonOption(
            "EUR",
            iconRes = null,
        ),
        ToggleButtonOption(
            "HRK",
            iconRes = null,
        )
    )
}

var currentItem = Product(
    categoryId = 1,
    id = 1,
    name = "Blue Hawaiian",
    description = "",
    priceEur = 5.49,
    priceHrk = 10,
    servingSize = 0.33,
    imageUrl = "https://i.ibb.co/ydqYwDP/flat-white.webp"
)

var savedCurrencyState = Pair("EUR", ToggleButtonOption(
    "EUR",
    iconRes = null,
))

data class Item(
    val id: Int,
    var name: String,
    var description: String?,
    var price: Double,
    var size: Double,
    var image: Int
)

data class Category(
    val id: Int,
    var catName: String
)

data class CartItem(
    var item: Product,
    var quantity: Int
)

data class Valuta(
    var id: Int,
    var eurPrice: Double,
    var hrkPrice: Int
)

//var categoryItems = listOf(
//    Category(
//        id = 1,
//        catName = "cocktail"
//    ),
//    Category(
//        id = 2,
//        catName = "beer"
//    ),
//    Category(
//        id = 3,
//        catName = "coffee"
//    )
//)

var cartItems: MutableList<CartItem> = mutableListOf()


//maknut ce se nakon Koina

var allPrices = listOf(
    Valuta(
        id = 1,
        eurPrice = 5.49,
        hrkPrice = 42
    ),
    Valuta(
        id = 2,
        eurPrice = 4.99,
        hrkPrice = 38
    ),
    Valuta(
        id = 3,
        eurPrice = 3.99,
        hrkPrice = 30
    )
)

var todaysSelectionItems = listOf(
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 2,
        name = "7 and 7",
        description = "",
        price = 4.99,
        size = 0.33,
        image = R.drawable.seven_and_seven
    ),
    Item(
        id = 3,
        name = "Affogato Coffee",
        description = "",
        price = 3.99,
        size = 0.1,
        image = R.drawable.affogato_coffee
    ),
)

var coffeeItems = listOf(
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 2,
        name = "7 and 7",
        description = "",
        price = 4.99,
        size = 0.33,
        image = R.drawable.seven_and_seven
    ),
    Item(
        id = 3,
        name = "Sex On The Beach",
        description = "",
        price = 3.99,
        size = 0.1,
        image = R.drawable.affogato_coffee
    ),
    Item(
        id = 3,
        name = "Affogato Coffee",
        description = "",
        price = 3.99,
        size = 0.1,
        image = R.drawable.affogato_coffee
    ),
    Item(
        id = 2,
        name = "7 and 7",
        description = "",
        price = 4.99,
        size = 0.33,
        image = R.drawable.seven_and_seven
    ),
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 2,
        name = "7 and 7",
        description = "",
        price = 4.99,
        size = 0.33,
        image = R.drawable.seven_and_seven
    ),
)

var hotDrinksItems = listOf(
    Item(
        id = 3,
        name = "Affogato Coffee",
        description = "",
        price = 3.99,
        size = 0.1,
        image = R.drawable.affogato_coffee
    ),
    Item(
        id = 3,
        name = "Affogato Coffee",
        description = "",
        price = 3.99,
        size = 0.1,
        image = R.drawable.affogato_coffee
    ),
)

var waterItems = listOf(
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
)

var softDrinksItems = listOf(
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 2,
        name = "7 and 7",
        description = "",
        price = 4.99,
        size = 0.33,
        image = R.drawable.seven_and_seven
    ),
    Item(
        id = 3,
        name = "Sex On The Beach",
        description = "",
        price = 3.99,
        size = 0.1,
        image = R.drawable.affogato_coffee
    ),
)

var wineItems = listOf(
    Item(
        id = 2,
        name = "7 and 7",
        description = "",
        price = 4.99,
        size = 0.33,
        image = R.drawable.seven_and_seven
    ),
    Item(
        id = 3,
        name = "Sex On The Beach",
        description = "",
        price = 3.99,
        size = 0.1,
        image = R.drawable.affogato_coffee
    ),
    Item(
        id = 2,
        name = "7 and 7",
        description = "",
        price = 4.99,
        size = 0.33,
        image = R.drawable.seven_and_seven
    ),
    Item(
        id = 2,
        name = "7 and 7",
        description = "",
        price = 4.99,
        size = 0.33,
        image = R.drawable.seven_and_seven
    ),
)

var beerItems = listOf(
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
    Item(
        id = 1,
        name = "Blue Hawaiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
)

val cocktailsItems = listOf(
    Item(
        id = 2,
        name = "7 and 7",
        description = "",
        price = 4.99,
        size = 0.33,
        image = R.drawable.seven_and_seven
    ),
    Item(
        id = 1,
        name = "Blue Hawafd sfdsdfiian",
        description = "",
        price = 5.49,
        size = 0.33,
        image = R.drawable.blue_hawaiian
    ),
)

val hardliquorItems: List<Item> = emptyList()

val allItems: List<List<Item>> = listOf(
    coffeeItems,
    hotDrinksItems,
    waterItems,
    softDrinksItems,
    beerItems,
    wineItems,
    cocktailsItems,
    hardliquorItems
)

//val categories = listOf(
//    "Coffee",
//    "Hot Drinks",
//    "Water",
//    "Soft Drinks",
//    "Beer",
//    "Wine",
//    "Cocktails",
//    "Hard Liquor"
//)