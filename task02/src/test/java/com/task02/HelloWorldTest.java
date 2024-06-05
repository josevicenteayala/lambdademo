package com.task02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

class HelloWorldTest {

    @Mock
    private Context context;

    private HelloWorld helloWorld;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        helloWorld = new HelloWorld();
        when(context.getLogger()).thenReturn(new TestLogger());
    }

    @Test
    void shouldReturn200WhenPathIsHello() {
        Map<String, Object> input = new HashMap<>();
        input.put("rawPath", "/hello");

        Map<String, Object> result = helloWorld.handleRequest(input, context);

        assertEquals(200, result.get("statusCode"));
    }

    @Test
    void shouldReturn400WhenPathIsNotHello() {
        Map<String, Object> input = new HashMap<>();
        input.put("rawPath", "/notHello");
        Map<String, Object> requestContext = new HashMap<>();
        Map<String, Object> httpInfo = new HashMap<>();
        httpInfo.put("method", "GET");
        requestContext.put("http", httpInfo);
        input.put("requestContext", requestContext);

        Map<String, Object> result = helloWorld.handleRequest(input, context);

        assertEquals(400, result.get("statusCode"));
    }
}