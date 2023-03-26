package cinema

fun makeCinema(rows: Int, seats: Int) : MutableList<MutableList<String>> {
    val cinemaPos = mutableListOf<MutableList<String>>()
    for (i in 0..rows)
    {
        cinemaPos.add(MutableList(seats + 1) {"S"})
        if (i == 0) {
            cinemaPos[i][0] = " "
            for (j in 1..seats)
            {
                cinemaPos[i][j] = j.toString()
            }
        }
        else
        {
            cinemaPos[i][0] = i.toString()
            for (j in 1..seats)
            {
                cinemaPos[i][j] = "S"
            }
        }
    }
    return cinemaPos
}

fun printCinema(cinema: MutableList<MutableList<String>>) : Unit {
    println("Cinema: ")
    for (element in cinema) {
        println(element.joinToString(" "))
    }
    println()
}

fun ticketBuy(cinema: MutableList<MutableList<String>>, rows: Int, seats: Int) : Int {
    try {
        println("Enter a row number:")
        val rowNumber = readln().toInt()
        println("Enter a seat number in that row:")
        val seatNumber = readln().toInt()
        println()
        if (rowNumber !in 1..rows || seatNumber !in 1..seats)  {
            throw Exception ("Wrong input!")
        } else if (cinema[rowNumber][seatNumber] == "B") {
            throw Exception ("That ticket has already been purchased!")
        }
        var price = 10
        if (rows * seats > 60)
        {
            if (rowNumber > rows/2)
            {
                price = 8
            }
        }
        println("Ticket price: $$price")
        cinema[rowNumber][seatNumber] = "B"
        println()
        return price
    } catch (e: Exception) {
        print("${e.message}\n")
        return 0
    }

}

fun printStatistic(totalSeats: Int, totalIncome: Int, ticketNumber: Int, income: Int): Unit {
    println("Number of purchased tickets: $ticketNumber")
    val percentage = 100 * ticketNumber.toDouble() / totalSeats.toDouble()
    val formatPercentage = "%.2f".format(percentage)
    print("Percentage: $formatPercentage%\n")
    println("Current income: $$income")
    println("Total income: $$totalIncome")
    println()
}

fun main()
{
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()
    var successfulPurchase = true
    val totalIncome = if (seats * rows <= 60) {
        rows * seats * 10
    } else {
        rows / 2 * seats * 10 + (rows - rows / 2) * 8 * seats
    }
    var ticketNumber = 0
    var income = 0
    val cinemaPos = makeCinema(rows, seats)
    println()
    var operation = 0
    do {
        if (successfulPurchase) {
            println("1. Show the seats")
            println("2. Buy a ticket")
            println("3. Statistics")
            println("0. Exit")
            operation = readln().toInt()
        } else {
            operation = 2
        }
        if (operation != 0)
            println()
        when (operation) {
            1 -> printCinema(cinemaPos)
            2 -> {
                val price = ticketBuy(cinemaPos, rows, seats)
                if ( price > 0) {
                    ++ticketNumber
                    income += price
                    successfulPurchase = true
                } else {
                    successfulPurchase = false
                }
            }
            3 -> printStatistic(rows * seats, totalIncome, ticketNumber, income)
        }
    } while (operation != 0)
    // Here i'm testing some git tools
}