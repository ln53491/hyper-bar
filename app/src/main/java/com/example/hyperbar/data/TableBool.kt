package com.example.hyperbar.data

class TableBool {
    var value: Boolean = false
    var tableNum: Int = 0
    var cameFromIntro: Boolean = false

    fun update(val2: Boolean) {
        value = val2
    }

    fun refreshTableNum(val2: Int) {
        tableNum = val2
    }

    fun updateCameFromIntro(val2: Boolean) {
        cameFromIntro = val2
    }
}

val tables: List<Int> = listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16)
var tableBool = TableBool()

var fromItemScreen = false

var loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
        "sed do eiusmod tempor incididunt ut labore et dolore magna " +
        "aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
        "ullamco laboris nisi ut aliquip ex ea commodo consequat." +
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
        "sed do eiusmod tempor incididunt ut labore et dolore magna " +
        "aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
        "ullamco laboris nisi ut aliquip ex ea commodo consequat." +
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
        "sed do eiusmod tempor incididunt ut labore et dolore magna " +
        "aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
        "ullamco laboris nisi ut aliquip ex ea commodo consequat." +
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
        "sed do eiusmod tempor incididunt ut labore et dolore magna " +
        "aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
        "ullamco laboris nisi ut aliquip ex ea commodo consequat."