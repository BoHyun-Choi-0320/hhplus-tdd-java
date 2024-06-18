package io.hhplus.tdd;


import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.database.dto.ChargeUserPointRequestDto;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PointControllerTest {

    UserPointTable userPointTable;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 포인트_충전() throws Exception {
        //given
        long id = 1L;
        long point = 30L;
        userPointTable.insertOrUpdate(id,point);

        Map<String, String> request = new HashMap<>();
        request.put("amount", String.valueOf(point));

        //when

        ResultActions response = mockMvc.perform(patch("/point/"+id+"/charge")
                .param(String.valueOf(request)))
                .andExpect(status().isOk());

        //Then
        assertThat(response).isEqualTo(new UserPoint(id, point, System.currentTimeMillis()));

    }

}
