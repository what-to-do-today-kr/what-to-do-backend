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
            energy = "5",
            mood = "신나는",
            time = "30분",
            environment = "실내",
        )

        val actual = recommendClient.recommendToDo(request)

        assertThat(actual).isNotNull()
        println(actual)
    }
}
