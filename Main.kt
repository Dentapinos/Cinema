package cinema

fun byTicket(rowCinema: Int, rowsCinema: Int, listMy: MutableList<Int>): String {
    var rep = true
    var noMatches = 0
    var theyRow = 0
    var theyNumInRow = 0
    while (rep) {
        println("\nEnter a row number:")
        theyRow = readln().toInt()  //ряд
        println("Enter a seat number in that row:")
        theyNumInRow = readln().toInt() //местo
        if (listMy.isEmpty() && theyRow <= rowCinema && theyNumInRow <= rowsCinema) {
            rep = false
            if (rowCinema * rowsCinema <= 60) { println("Ticket price: $10") }
            else { println(if (theyRow <= rowCinema / 2) "Ticket price: $10" else "Ticket price: $8") }
        } else {
            if (theyRow <= rowCinema && theyNumInRow <= rowsCinema) {
                for (k in 0 until listMy.size step 2) { if (theyRow == listMy[k] && theyNumInRow == listMy[k + 1]) noMatches++ }
                if (noMatches == 0) {
                    rep = if (rowCinema * rowsCinema <= 60) {
                        println("\nTicket price: $10")
                        false
                    } else {
                        println(if (theyRow <= rowCinema / 2) "Ticket price: $10" else "Ticket price: $8")
                        false
                    }
                } else {
                    println("\nThat ticket has already been purchased!")
                    noMatches = 0
                }
            } else {
                println("\nWrong input!")
            }
        }
    }
    return (" $theyRow $theyNumInRow")
}

fun seats(row: Int, column: Int, myList: MutableList<Int>) {
    val list: MutableList<MutableList<String>> = mutableListOf()
    for (j in 0..row) {
        val followingLists = mutableListOf<String>()
        if (j == 0) {
            for (i in 0 until column) {
                followingLists.add((i + 1).toString())
            }
            followingLists.add(0, " ")
            list.add(followingLists)
        } else {
            for (k in 0 until column) {
                followingLists.add(k, "S")
            }
            followingLists.add(0, j.toString())
            list.add(followingLists)
        }
    }
    if (myList.isNotEmpty()) {
        for (f in 0 until myList.size step 2) {
            list[myList[f]].removeAt(myList[f + 1])
            list[myList[f]].add(myList[f + 1], "B")
        }
    }
    println("\nCinema:")
    for (n in 0 until list.size) {
        println(list[n].joinToString(" "))
    }
}

fun menu() {  //меню выбора
    println("\n1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun statistika(myList: MutableList<Int>, rows: Int, seats: Int) {
    val ticketPurchased = if (myList.isNotEmpty()) myList.size / 2 else 0
    val pTicketPurchased = if (myList.isNotEmpty()) ticketPurchased.toDouble() * 100 / (rows * seats) else 0.00
    val formatPercentage = "%.2f".format(pTicketPurchased)
    val totalIncome = if (rows * seats < 60) 10 * seats * rows else rows / 2 * seats * 10 + (rows - rows / 2) * seats * 8
    var operatingIncome = 0
    if (rows * seats < 60) { operatingIncome = myList.size / 2 * 10 }
    else { for (i in 0 until myList.size step 2) {
        val expensivRank = rows / 2
        operatingIncome += if (myList[i] in 1..expensivRank) 10 else 8
    }
    }
    println("\nNumber of purchased tickets: $ticketPurchased")
    println("Percentage: $formatPercentage%")
    println("Current income: $$operatingIncome")
    println("Total income: $$totalIncome")
}

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt() //рядов
    println("Enter the number of seats in each row:")
    val seatsInRows = readln().toInt() //мест в ряду

    var str = ""
    var myList: MutableList<Int> = mutableListOf()
    var myL: MutableList<String>

    while (true) {
        menu()
        when (readln().toInt()) {
            1 -> seats(rows, seatsInRows, myList)
            2 -> str += byTicket(rows, seatsInRows, myList)
            3 -> statistika(myList, rows, seatsInRows)
            0 -> break
        }
        val regex = "\\s+".toRegex()
        myL = str.split(regex).toMutableList()
        myL.removeAt(0)
        myList = myL.map { it.toInt() }.toMutableList()
    }
}