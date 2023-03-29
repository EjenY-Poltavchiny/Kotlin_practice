package parking

var isParkingSpotCreated : Boolean = false
val FIND_CAR_OPERATIONS = mutableListOf("spot_by_color", "reg_by_color", "spot_by_reg")

class ParkingSpot(var spotNumber: String = "", var carNumber: String = "", var carColor: String = "", var freeFlag : Boolean = true )
{
    override fun toString(): String {
        return "$spotNumber $carNumber $carColor"
    }
}

fun comasPrint(objects: MutableList<String>) : Unit {
    for (i in objects.indices) {
        if (i != objects.size - 1) {
            print("${objects[i]}, ")
        } else {
            print("${objects[i]}\n")
        }
    }
}

fun parkingSpotCreate(size: Int) : MutableList<ParkingSpot> {
    val parkLot = ParkingSpot()
    return MutableList(size) {parkLot}
}

fun parkNewCar(parkingSpots: MutableList<ParkingSpot>, color: String, number: String ) : Unit {
    var parkSuccess = false
    for (indexes in parkingSpots.indices) {
        if (parkingSpots[indexes].freeFlag) {
            val parkingSpot = ParkingSpot(spotNumber = (indexes + 1).toString(), carNumber = number, carColor = color, freeFlag = false)
            parkingSpots[indexes] = parkingSpot
            println("${parkingSpot.carColor} car parked in spot ${parkingSpot.spotNumber}.")
            parkSuccess = true
            break
        }
    }
    if (!parkSuccess) {
        println("Sorry, the parking lot is full.")
    }
}

fun leaveSpot(parkingSpots: MutableList<ParkingSpot>, index: Int) : Unit {
    val parkLot = ParkingSpot()
    parkingSpots[index] = parkLot
    println("Spot ${index + 1} is free.")
}

fun printStatus(parkingSpots: MutableList<ParkingSpot>) : Unit {
    var hasCars = false
    for (i in parkingSpots.indices) {
        if (!parkingSpots[i].freeFlag) {
            println(parkingSpots[i])
            hasCars = true
        }
    }
    if (!hasCars) {
        println("Parking lot is empty.")
    }
}

fun InfoByAttribute(parkingSpots: MutableList<ParkingSpot>, operInfo: String, attributeValue: String) : Unit {
    val carNumbers = mutableListOf<String> ()
    when (operInfo) {
        "reg_by_color" -> {
            for (i in parkingSpots.indices) {
                if (parkingSpots[i].carColor.lowercase() == attributeValue.lowercase()) {
                    carNumbers.add(parkingSpots[i].carNumber)
                }
            }
            if (carNumbers.isEmpty()) {
                println("No cars with color $attributeValue were found.")
            } else {
                comasPrint(carNumbers)
            }
        }
        "spot_by_color" -> {
            for (i in parkingSpots.indices) {
                if (parkingSpots[i].carColor.lowercase() == attributeValue.lowercase()) {
                    carNumbers.add(parkingSpots[i].spotNumber)
                }
            }
            if (carNumbers.isEmpty()) {
                println("No cars with color $attributeValue were found.")
            } else {
                comasPrint(carNumbers)
            }
        }
        "spot_by_reg" -> {
            for (i in parkingSpots.indices) {
                if (parkingSpots[i].carNumber == attributeValue) {
                    carNumbers.add(parkingSpots[i].spotNumber)
                }
            }
            if (carNumbers.isEmpty()) {
                println("No cars with registration number $attributeValue were found.")
            } else {
                comasPrint(carNumbers)
            }
        }
    }
}

fun main() {
    var isParkingSpotCreated : Boolean = false
    var parkingSpots = mutableListOf<ParkingSpot>()
    do {
        val input = readln()
        val inputInfo = input.split(" ").toMutableList()
        val operation = inputInfo[0]
        when (operation) {
            "create" -> {
                parkingSpots = parkingSpotCreate(inputInfo[1].toInt())
                println("Created a parking lot with ${inputInfo[1]} spots.")
                isParkingSpotCreated = true
            }
            "park" -> {
                if (isParkingSpotCreated) {
                    parkNewCar(parkingSpots, color = inputInfo[2], number = inputInfo[1])
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            "leave" -> {
                if (isParkingSpotCreated) {
                    leaveSpot(parkingSpots, inputInfo[1].toInt() - 1)
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            "status" -> {
                if (isParkingSpotCreated) {
                    printStatus(parkingSpots)
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            in (FIND_CAR_OPERATIONS) -> {
                if (isParkingSpotCreated) {
                    InfoByAttribute(parkingSpots, inputInfo[0], inputInfo[1])
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
        }
    } while (operation != "exit")
}
