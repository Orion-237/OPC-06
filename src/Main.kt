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