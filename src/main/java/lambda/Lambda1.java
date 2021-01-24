package lambda;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaAsyncClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import s3.PutObject;

import java.util.Date;

public class Lambda1 implements RequestHandler<SQSEvent, String> {
    @Override
    public String handleRequest(SQSEvent event, Context context) {
        //print log to cloud watch
        LambdaLogger logger = context.getLogger();
        StringBuilder response = new StringBuilder("lambda1 200 OK");
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            String bodyMsg = msg.getBody();
            response.append(bodyMsg);
        }
        logger.log(response.toString());

        //S3 object key
        String key = new Date() + "from lambda1";
        //S3 object name
        String bucketName = "lambda1s3";

        //save to S3
        PutObject s3 = new PutObject(bucketName, key);
        s3.save(response.toString());


        //trigger lambda2 to get msg from S3
        final String DEFAULT_LAMBDA_REGION = "ap-southeast-2";
        final String LAMBDA_FUNCTION_NAME = "AWSLambdaAndSqs-dev-Lambda2";

        AWSLambda client = AWSLambdaAsyncClient.builder().withRegion(DEFAULT_LAMBDA_REGION).build();
        InvokeRequest request = new InvokeRequest();

        //get from S3 bucket
        String payload = s3.get(bucketName, key);
        logger.log("get from lambda1S3 bucket done: key = " + key + "value = " + payload);

        //trigger lambda2
        request.withFunctionName(LAMBDA_FUNCTION_NAME).withPayload("{\"payload\": \"" + payload + "\"}");
        InvokeResult invoke = client.invoke(request);
        logger.log(request.toString());
        logger.log("Result invoking " + LAMBDA_FUNCTION_NAME + ": " + invoke);
        return response.toString();
    }
}