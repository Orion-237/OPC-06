import docta.Doctor
import docta.Patient

fun greetings(message: String?) {
    if (message != null) {
        val l = 5
        val banner = 2 * l + message.length
        println("=".repeat(banner))
        println("-".repeat(l) + message + "-".repeat(l))
        println("=".repeat(banner))
    }
}

fun showMenu(options: List<String>, choiceExpected: Boolean = true): Int? {
    options.forEachIndexed { i, option ->
        println("\t${i + 1}. $option")
    }
    var choice: Int? = null
    if (choiceExpected) {
        println("\t${options.size + 1}. Back")
        var valid = false
        do {
            print("\n\tEnter a choice:")
            try {
                choice = readln().toInt()
                if (choice !in 1..options.size + 1) {
                    throw IllegalArgumentException("Invalid choice")
                }
                valid = true
            } catch (e: Exception) {
                println("Invalid input")
            }
        } while (!valid)
    }
    return if (choice != null && choice <= options.size) choice else null
}

// To implement the behaviour of a menu
fun menu(
    banner: String?,
    optionsUpdate: (() -> List<String>),
    callback: ((Int?) -> Unit)? = null,
    subtitle: String? = null,
) {
    var choice: Int? = null
    do {
        val options = optionsUpdate()
        greetings(banner)
        if (subtitle != null) {
            println(subtitle)
        }
        if (callback != null) {
            choice = showMenu(options)
            // Do what is supposed to be done per choice
            callback(choice)
        } else {
            showMenu(options, false)
        }
    } while (choice != null)
}

fun main() {
    val doctors = mutableListOf(
        Doctor("Abena", "Ophthalmology"),
        Doctor("Nelson", "ORL"),
        Doctor("Ryan", "General"),
        Doctor("Alex", "Cardiology"),
        Doctor("Bilong", "Physician")
    )

    val patients = mutableListOf(
        Patient("A", 14, "Head ache"),
        Patient("B", 27, "Stomach ache")
    )

    menu(
        "Welcome! You are",
        { mutableListOf("A Doctor", "A patient", "View all appointments") },
        { choice ->
            var doc: Doctor? = null
            var pat: Patient? = null
            when (choice) {
                1 -> menu(
                    "Which doctor are you?",
                    { doctors.map { it.toString() } },
                    { choice1 ->
                        if (choice1 != null) {
                            doc = doctors[choice1 - 1]
                            println(doc)
                            menu(
                                "Dr. " + doc!!.name,
                                {
                                    mutableListOf(
                                        "Add available time frames for consultation",
                                        "View your free time",
                                        "View appointments"
                                    )
                                },
                                { choice2 ->
                                    when (choice2) {
                                        1 -> doc!!.addTimeFrame()
                                        2 -> doc!!.displayAvailableTimeFrames()
                                        3 -> doc!!.displayAppointments()
                                    }
                                })
                        }
                    })

                2 -> menu(
                    "Which patient are you?",
                    { patients.map { it.toString() } },
                    { choice1 ->
                        if (choice1 != null) {
                            pat = patients[choice1 - 1]
                            menu(
                                pat!!.name,
                                { doctors.map { it.toString() } },
                                { choice2 ->
                                    if (choice2 != null){
                                        doc = doctors[choice2 - 1]
                                        if(doc!!.timeFrames.size > 0){
                                            menu(
                                                "Choose a time frame",
                                                {doc!!.timeFrames.map { it.toString() } },
                                                { choice3 ->
                                                    if (choice3 != null) {
                                                        doc!!.bookTimeFrame(doc!!.timeFrames[choice3 - 1], pat!!)
                                                    }
                                                }
                                            )
                                        }else{
                                            println("Sorry $doc is too busy")
                                            readln()
                                        }
                                    }
                                },
                                " which doctor may you want to consult?"
                            )
                        }
                    }
                )
                3 -> showMenu(doctors.flatMap { doctor -> doctor.appointments.map { it.toString() } })
            }
        })

//    var t = LocalDateTime.of(2023, 2, 23, 12, 0)
//    val t1 = t.plusHours(1)
//    val t2 = t1.plusHours(1)
//    println(LocalDateTime.now() in t..t2)

//    var f: TimeFrame = TimeFrame
}

fun gotoxy(row: Int, col: Int) {
    print("\u001B[${row};${col}H")
}

// ANSI Color and Text Effect Constants for Kotlin

// Text Effects
const val RESET = "\u001B[0m"
const val BOLD = "\u001B[1m"
const val DIM = "\u001B[2m"
const val ITALIC = "\u001B[3m"
const val UNDERLINE = "\u001B[4m"
const val BLINK = "\u001B[5m"
const val REVERSE = "\u001B[7m"
const val HIDDEN = "\u001B[8m"

// Basic Text Colors
const val BLACK = "\u001B[30m"
const val RED = "\u001B[31m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"
const val BLUE = "\u001B[34m"
const val MAGENTA = "\u001B[35m"
const val CYAN = "\u001B[36m"
const val WHITE = "\u001B[37m"

// Bright Text Colors
const val BRIGHT_BLACK = "\u001B[90m"
const val BRIGHT_RED = "\u001B[91m"
const val BRIGHT_GREEN = "\u001B[92m"
const val BRIGHT_YELLOW = "\u001B[93m"
const val BRIGHT_BLUE = "\u001B[94m"
const val BRIGHT_MAGENTA = "\u001B[95m"
const val BRIGHT_CYAN = "\u001B[96m"
const val BRIGHT_WHITE = "\u001B[97m"

// Background Colors
const val BG_BLACK = "\u001B[40m"
const val BG_RED = "\u001B[41m"
const val BG_GREEN = "\u001B[42m"
const val BG_YELLOW = "\u001B[43m"
const val BG_BLUE = "\u001B[44m"
const val BG_MAGENTA = "\u001B[45m"
const val BG_CYAN = "\u001B[46m"
const val BG_WHITE = "\u001B[47m"

// Bright Background Colors
const val BG_BRIGHT_BLACK = "\u001B[100m"
const val BG_BRIGHT_RED = "\u001B[101m"
const val BG_BRIGHT_GREEN = "\u001B[102m"
const val BG_BRIGHT_YELLOW = "\u001B[103m"
const val BG_BRIGHT_BLUE = "\u001B[104m"
const val BG_BRIGHT_MAGENTA = "\u001B[105m"
const val BG_BRIGHT_CYAN = "\u001B[106m"
const val BG_BRIGHT_WHITE = "\u001B[107m"

// Functions for Styling
fun textColor(color: String) {
    print(color)
}

fun bgColor(color: String) {
    print(color)
}

fun style(effect: String) {
    print(effect)
}

// Example Usage
//fun main() {
//    textColor(BLACK)
//    bgColor(BG_BRIGHT_WHITE)
//    style(BLINK)
////    style(REVERSE)
//    println("Bold Red Text on Yellow Background!\n\n\n\n")
//    println(RESET)
//
//    style(UNDERLINE)
//    textColor(BRIGHT_CYAN)
//    println("Underlined Bright Cyan Text")
//    println(RESET)
//
//    style(DIM)
//    textColor(GREEN)
//    println("Dim Green Text")
//    println(RESET)
//
//    style(REVERSE)
//    textColor(MAGENTA)
//    println("Reversed Magenta Text")
//    println(RESET)
//
//    println("Normal Text without Styling")
//
//    // Change the background to white and text to black using ANSI codes
//    println("\u001b[47m\u001b[30mThis text is black on a white background! 😀😁")
//    println("\u001b[0mThis resets to default.")
//
//}
