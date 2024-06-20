package com.task03;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;
import java.util.HashMap;
import java.util.Map;

@LambdaHandler(lambdaName = "hello_world",
	roleName = "hello_world-role",
	isPublishVersion = true
)

public class HelloWorld implements RequestHandler<Object, Map<String, Object>> {

    public static final String STATUS_CODE = "statusCode";
    public static final String HELLO_PATH = "/hello";

    @Override
    public Map<String, Object> handleRequest(Object input, Context context) {
        context.getLogger().log("Calling handleRequest from lambda task02");
        context.getLogger().log("Input: " + input);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("statusCode", 200);
        resultMap.put("message", "Hello from Lambda");
        return resultMap;
    }
}
