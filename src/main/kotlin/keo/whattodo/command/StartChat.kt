package keo.whattodo.command

import keo.whattodo.service.UserStateService
import org.springframework.stereotype.Component

@Component
class StartChat(private val userStateService: UserStateService) {

    suspend fun startChat(platform: String, userId: String, roomId: String) : Long {
        val userState = userStateService.create(platform, userId, roomId)
        return userState.id
    }
}
