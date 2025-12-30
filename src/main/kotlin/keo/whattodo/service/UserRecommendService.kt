package keo.whattodo.service

import jakarta.transaction.Transactional
import keo.whattodo.client.recommend.RecommendClient
import keo.whattodo.client.recommend.RecommendRequest
import keo.whattodo.domain.UserRecommend
import keo.whattodo.repository.UserRecommendRepository
import keo.whattodo.repository.UserStateRepository
import keo.whattodo.repository.findByIdOrElseThrow
import org.springframework.stereotype.Service

@Service
class UserRecommendService(
    private val userStateRepository: UserStateRepository,
    private val userRecommendRepository: UserRecommendRepository,
    private val recommendClient: RecommendClient,
) {

    fun createRecommends(userStateId: Long): List<UserRecommend> {
        val userState = userStateRepository.findByIdOrElseThrow(userStateId)
        val recommendations = recommendClient.recommendToDo(RecommendRequest(userState))
        return userRecommendRepository.saveAll(recommendations.toDomain(userState))
    }

    @Transactional
    fun selectRecommend(id: Long): UserRecommend {
        val recommend = userRecommendRepository.findByIdOrElseThrow(id)
        recommend.select()
        return recommend
    }
}
