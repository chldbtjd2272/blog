package com.blog.resttemplatetest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(value = {UserService.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockRestServiceServer mockServer;


    private String orderApiUrl = "http://localhost:8090/order?orderNo=";

    @Test
    public void name() {
        //given
        String expectOrderNo = "1";
        Long expectAmount = 1000L;
        LocalDateTime expectOrderDateTime = LocalDateTime.of(2018,9,29,0,0);
        String expectResult = "{\"orderNo\":\"1\",\"amount\":1000,\"orderDateTime\":\"2018-09-29 00:00:00\"}";
        mockServer.expect(requestTo(orderApiUrl+expectOrderNo))
                .andRespond(withSuccess(expectResult, MediaType.APPLICATION_JSON));

        //when
        OrderDto response = userService.getUserOrder(expectOrderNo);

        //then
        assertThat(response.getOrderNo()).isEqualTo(expectOrderNo);
        assertThat(response.getAmount()).isEqualTo(expectAmount);
        assertThat(response.getOrderDateTime()).isEqualTo(expectOrderDateTime);

    }
}