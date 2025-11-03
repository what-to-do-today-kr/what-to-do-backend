package keo.whattodo.client.ai.google

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RecommendClientTest {

    @Autowired
    private lateinit var recommendClient: RecommendClient

    @Disabled
    @Test
    fun `실행 테스트`() {

        val actual = recommendClient.run()

        println(actual)
    }
}
