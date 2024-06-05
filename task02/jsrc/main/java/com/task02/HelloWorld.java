package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.lambda.LambdaUrlConfig;
import com.syndicate.deployment.model.Architecture;
import com.syndicate.deployment.model.DeploymentRuntime;
import com.syndicate.deployment.model.RetentionSetting;
import com.syndicate.deployment.model.lambda.url.AuthType;
import com.syndicate.deployment.model.lambda.url.InvokeMode;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@LambdaHandler(lambdaName = "hello_world",
	roleName = "hello_world-url-role",
	isPublishVersion = false,
    runtime = DeploymentRuntime.JAVA11,
    architecture = Architecture.ARM64,
	logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
@LambdaUrlConfig(authType = AuthType.NONE, invokeMode = InvokeMode.BUFFERED)
public class HelloWorld implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    public static final String STATUS_CODE = "statusCode";
    public static final String HELLO_PATH = "/hello";

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        context.getLogger().log("Calling handleRequest from lambda task02");
        context.getLogger().log("Input: " + input);
        String path = (String) input.get("rawPath");
        Map<String, Object> resultMap = new HashMap<>();
        if (HELLO_PATH.equals(path)) {
            resultMap.put(STATUS_CODE, 200);
            resultMap.put("body", "{\"statusCode\": 200, \"message\": \"Hello from Lambda\"}");
        } else {
            resultMap.put(STATUS_CODE, 400);
            Map<String, Object> requestContext = (Map<String, Object>) input.get("requestContext");
            Map<String, Object> httpInfo = (Map<String, Object>) requestContext.get("http");

            context.getLogger().log("HTTP Info: " + httpInfo);
            context.getLogger().log("HTTP Info Class: " + httpInfo.getClass());

            String responseBodyTemplate = "\"statusCode\": 400, \"message\": \"Bad request syntax or unsupported method. Request path: {0}. HTTP method: {1}\"";
            String responseBody = "{" + MessageFormat.format(responseBodyTemplate, path, httpInfo.get("method")) + "}";

            resultMap.put("body", responseBody);
        }
        context.getLogger().log("ResultMap: " + resultMap);
        return resultMap;
    }
}
