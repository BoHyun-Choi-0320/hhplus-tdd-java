package io.hhplus.tdd;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public class PointControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void 포인트_조회() throws Exception {

    }
}
