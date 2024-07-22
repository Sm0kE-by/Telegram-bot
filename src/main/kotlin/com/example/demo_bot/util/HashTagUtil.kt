package com.example.demo_bot.util

import com.example.demo_bot.model.HandlerName
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

fun getHashTagUtilCreatePost(namePost: HandlerName): ArrayList<String> {
    val namePost = namePost
    var list: ArrayList<String> = ArrayList()
    when (namePost) {
        HandlerName.CREATE_NEW_POST_BY_CRYPTO -> {
            list.add(0, "#Проекты")
            list.add(1, "#Инвестпортфель")
            list.add(2, "#ИнвестПул")
            return list
        }

        else -> {
            list.add(0, "")
        }
    }
    return list
}