package keo.whattodo.client.recommend

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RecommendClientTest {

    @Autowired
    private lateinit var recommendClient: RecommendClient

    @Test
    @Disabled
    fun `실행 테스트`() {
        val request = RecommendRequest(
            energy = 1,
            feelingScript = "귀찮음",
            weatherScript = "매우 맑음",
            goalScript = "머리를 비우는 충분한 휴식",
        )

        val actual = recommendClient.recommendToDo(request)

        assertThat(actual).isNotEmpty()
        println(actual)
    }
}
