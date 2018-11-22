package config;

public enum Requests {
    POST("Post"),
    GET("Get");

    private String request;

    Requests(String request){
        this.request = request;
    }

    public String getRequest(){
        return request;
    }


}
