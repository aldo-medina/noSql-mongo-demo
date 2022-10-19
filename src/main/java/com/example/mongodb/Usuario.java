package com.example.mongodb;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario implements Serializable { //extends Document

    private String nombre;

    private String apellido;

    private Integer edad;

    private String sexo;

    private List<String> lenguajesAprendidos;

}
