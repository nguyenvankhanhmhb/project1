package com.example.ProjectAlias03.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class JsonService {
    public static <T> List<T> fromJsonArray(String json, Class<T> clazz) {
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(json, listType);
    }
}
