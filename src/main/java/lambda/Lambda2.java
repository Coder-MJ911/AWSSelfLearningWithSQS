package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import s3.PutObject;

import java.util.Date;
import java.util.Map;

public class Lambda2 implements RequestHandler<Map<String, String>, String> {

    @Override
    public String handleRequest(Map<String, String> request, Context context) {
        LambdaLogger logger = context.getLogger();
        String response = "lambda2 200 OK" + request;

        //S3 object key
        String key = new Date() + "from lambda2";
        //S3 object name
        String bucketName = "lambda2s3";

        //save to S3
        PutObject s3 = new PutObject(bucketName, key);
        s3.save(response);
        logger.log("save to lambda2S3 bucket done: key = " + key + "value = " + request);
        return "";
    }
}