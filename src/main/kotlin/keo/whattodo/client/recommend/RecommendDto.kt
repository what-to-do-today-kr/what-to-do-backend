package keo.whattodo.client.recommend

data class RecommendRequest(
    val energy: Int,
    val feelingScript: String,
    val weatherScript: String,
    val goalScript: String,
)

data class RecommendResponse(
    val recommendations: List<RecommendDetailResponse>
)

data class RecommendDetailResponse(
    val title: String,
    val reason: String,
    val steps: List<String>,
)
