package com.andrew.ers.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;

public class S3Service {
	
	public static final Regions REGION = Regions.US_EAST_1;
	public static final String BUCKET_NAME = "crenwelge-ers";
	public static final String KEY_PREFIX = "Receipts/receipt-";
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private AmazonS3 getClient() {
		return AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(REGION)
                .build();
	}
	
	private Date getExpireDate(int hoursLater) {
		// Set the pre-signed URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60 * hoursLater;
        expiration.setTime(expTimeMillis);
        return expiration;
	}

    public String testUploadReceipt(String username,int expenseId) throws IOException {
        String objectKey = username + KEY_PREFIX + expenseId;
        try {
            AmazonS3 s3Client = getClient();
            Date expires = getExpireDate(1);

            // Generate the pre-signed URL.
            log.info("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, objectKey)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expires);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write("This text uploaded as an object via presigned URL.");
            out.close();

            // Check the HTTP response code. To complete the upload and make the object available, 
            // you must interact with the connection object in some way.
            connection.getResponseCode();
            log.info("HTTP response code: " + connection.getResponseCode());

            // Check to make sure that the object was uploaded successfully.
            S3Object object = s3Client.getObject(BUCKET_NAME, objectKey);
            log.info("Object " + object.getKey() + " created in bucket " + object.getBucketName());
            return object.getKey();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            log.error(e.getErrorMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client  
            // couldn't parse the response from Amazon S3.
            log.error(e.getMessage());
        }
        return null;
    }

    public String getReceiptUrl(String username,int expenseId) throws IOException {
    		String objectKey = username + KEY_PREFIX + expenseId;
    		Date expires = getExpireDate(1);
    		// Generate the pre-signed URL.
        log.info("Generating pre-signed URL.");
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, objectKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expires);
        URL url = getClient().generatePresignedUrl(generatePresignedUrlRequest);
        log.info("Presigned URL: " + url.toString());
        return url.toString();
    }

}