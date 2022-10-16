package com.example.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/mongo", produces="application/json")
public class CrudController {

    @Autowired
    private CrudService crudService;

    @GetMapping("/usuarios")
    public ResponseEntity<String> getUsuarios(@RequestParam(value = "nombre") String nombre) {
        try {
            String usuarios = this.crudService.getUsuarios(nombre);
            return new ResponseEntity<String>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
