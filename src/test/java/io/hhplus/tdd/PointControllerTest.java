package io.hhplus.tdd;


import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.database.dto.ChargeUserPointRequestDto;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.HashMap;
import java.util.Map;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PointControllerTest {

    UserPointTable userPointTable;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 포인트_충전() throws Exception {
        //given
        long id = 1L;
        long amount = 30L;

        //when

        final ResultActions response = mockMvc.perform(patch("/point/"+id+"/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(amount)))
                .andExpect(status().isOk());

        // then
        assertThat(response).isEqualTo(new UserPoint(id, amount, System.currentTimeMillis()));
    }

}
