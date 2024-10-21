package com.example.demo_bot.util


fun getThreeRowsOfButtons(
    names: List<String>,
    callbackNext: String,
    callbackBack: String
): List<List<Pair<String, String>>> {
    val listNames = ArrayList<List<Pair<String, String>>>()
    for (i in names.indices step 3) {
        if (i + 2 <= names.size - 1) {
            listNames.add(
                listOf(
                    "$callbackNext|${names[i]}" to names[i],
                    "$callbackNext|${names[i + 1]}" to names[i + 1],
                    "$callbackNext|${names[i + 2]}" to names[i + 2]
                )
            )
        } else if (i + 1 <= names.size - 1) {
            val remainingNames = names.slice(i..<names.size)
            listNames.addAll(getTwoRowsOfButtons(remainingNames, callbackNext, callbackBack))
        }
        if (i + 2 == names.size - 1) {
            listNames.add(listOf(callbackBack to "Назад"))
        }
    }
    return listNames
}

fun getTwoRowsOfButtons(
    names: List<String>,
    callbackNext: String,
    callbackBack: String
): List<List<Pair<String, String>>> {
    val listNames = ArrayList<List<Pair<String, String>>>()
    for (i in names.indices step 2) {
        if (i + 1 <= names.size - 1) {
            listNames.add(
                listOf(
                    "$callbackNext|${names[i]}" to names[i],
                    "$callbackNext|${names[i + 1]}" to names[i + 1]
                )
            )
        } else {
            val remainingNames = names.slice(i..<names.size)
            listNames.addAll(getOneRowsOfButtons(remainingNames, callbackNext, callbackBack))
        }
        if (i + 1 == names.size - 1) {
            listNames.add(listOf(callbackBack to "Назад"))
        }
    }
    return listNames
}

fun getOneRowsOfButtons(
    names: List<String>,
    callbackNext: String,
    callbackBack: String
): List<List<Pair<String, String>>> {
    val list = ArrayList<List<Pair<String, String>>>()
    names.forEach {
        list.add(
            listOf(
                "$callbackNext|${it}" to it
            )
        )
    }
    list.add(listOf(callbackBack to "Назад"))
    return list
}