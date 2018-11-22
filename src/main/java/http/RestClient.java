package http;

import config.Requests;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class RestClient {

    private HttpClient httpClient;

    public void init(){
        httpClient = new DefaultHttpClient();
    }

    public HttpResponse getResponse(String endpoint, Requests requests, String... parameters) throws IOException {
        HttpResponse response;
        if(requests.getRequest().equals("Get")) {
            HttpGet getRequest = new HttpGet(endpoint);
            getRequest.addHeader("accept", "application/json");
            response = httpClient.execute(getRequest);
            return response;
        }
        else if(requests.getRequest().equals("Post")){
            HttpPost postRequest = new HttpPost(endpoint);
            StringBuffer postEntity = new StringBuffer();
            for (int i = 0; i < parameters.length; i++) {
                postEntity.append(parameters[i] + ",");
            }
            if(postEntity.length() > 0){
                postEntity.deleteCharAt(postEntity.length() - 1);
                StringEntity input = new StringEntity("{" + postEntity.toString() + "}");
                input.setContentType("application/json");
                postRequest.setEntity(input);
                response = httpClient.execute(postRequest);
                return response;
            }
            else{
                return null;
            }
        }
        return null;
    }

    public void releaseConnection(){
        httpClient.getConnectionManager().shutdown();
    }
}
