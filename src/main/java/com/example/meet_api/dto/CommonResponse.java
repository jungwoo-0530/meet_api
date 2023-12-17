package com.example.meet_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonResponse<T> extends BaseResponse{

    private int count;
    private T data;
    private String message;

    public CommonResponse(T data) {

        this.data = data;
        if(data instanceof List){
            this.count = ((List<?>)data).size();
        }else{
            this.count = 1;
        }

    }

    public CommonResponse(T data, String message) {
        this.message = message;
        this.data = data;
        if (data instanceof List) {
            this.count = ((List<?>) data).size();
        } else {
            this.count = 1;
        }
    }
    public CommonResponse(String message){
        this.message = message;
    }



}
