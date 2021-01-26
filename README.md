# AWS SelfLearning

# Description
 
this is just for AWS Learning about Lambda | SQS | S3 | CloudWatch

# Schema

1. sqs trigger lambda1
2. lambda1 save sqs event body message to save to S3
3. lambda1 trigger lambda2 then to get from last step S3 to get and then save to another S3 bucket

4. lambda3 is triggered by CloudWatch as a timer task