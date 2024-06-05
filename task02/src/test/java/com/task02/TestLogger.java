package com.task02;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class TestLogger implements LambdaLogger {
    private final StringBuilder log = new StringBuilder();
    @Override
    public void log(String message) {
        log.append(message).append("\n");
    }

    @Override
    public void log(byte[] message) {
        log.append(new String(message)).append("\n");
    }
}
