package ru.yandex.praktikumchatapp.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen

const val INIT_DELAY = 1000L
const val DELAY_FACTOR = 2L

class ChatRepository(
    private val api: ChatApi = ChatApi()
) {

    private var currentDelay = INIT_DELAY

    fun getReplyMessage(): Flow<String> {
        return api.getReply()
            .retryWhen { exception, _ ->
                delay(currentDelay)
                currentDelay *= DELAY_FACTOR
                true
            }
            .onEach {
                currentDelay = INIT_DELAY
            }
    }
}