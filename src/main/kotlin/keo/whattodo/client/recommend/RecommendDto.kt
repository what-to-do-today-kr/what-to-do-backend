package keo.whattodo.client.recommend

import keo.whattodo.domain.UserRecommend
import keo.whattodo.domain.UserState

data class RecommendRequest(
    val energy: String,
    val mood: String,
    val time: String,
    val environment: String,
) {
    constructor(userState: UserState) : this(
        energy = userState.energy,
        mood = userState.mood,
        time = userState.time,
        environment = userState.environment,
    )
}

data class RecommendResponse(
    val recommendations: List<RecommendDetailResponse>
) {
    fun toDomain(userState: UserState): List<UserRecommend> {
        return recommendations.mapIndexed { index, detailResponse ->
            detailResponse.toDomain(index + 1, userState)
        }
    }
}

data class RecommendDetailResponse(
    val title: String,
    val reason: String,
    val step1: String,
    val step2: String,
    val step3: String,
    val tip: String,
) {
    fun toDomain(order: Int, userState: UserState) = UserRecommend(
        userState = userState,
        title = title,
        reason = reason,
        step1 = step1,
        step2 = step2,
        step3 = step3,
        sequence = order,
        tip = tip,
    )
}
