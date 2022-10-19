package com.example.mongodb;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
public class UpdateRequest implements Serializable {

    private HashMap<String, Object> queryParams;

    private HashMap<String, Object> updateParams;

}
