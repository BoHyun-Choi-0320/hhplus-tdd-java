package io.hhplus.tdd;

import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.repository.UserPointRepository;
import io.hhplus.tdd.service.UserPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserPointService userPointService;


    @Test
    void 사용자포인트조회() throws Exception {
        // given
        long id = 1L;
        long point = 100L;

        // Mocking the behavior of userPointService.userPointCharge()
        given(userPointService.userPointCharge(id, point))
                .willReturn(new UserPoint(id, point, System.currentTimeMillis()));

        // when
        ResultActions actions = mockMvc.perform(get("/point/{id}", id));

        // then
        actions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}
