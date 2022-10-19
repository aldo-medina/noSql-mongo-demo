package com.example.mongodb;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

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

    @PostMapping("/usuarios/addone")
    public ResponseEntity<String> addUsuario(@RequestBody Usuario usuario) {
        try {
            this.crudService.addUsuario(usuario);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/usuarios/addmany")
    public ResponseEntity<String> addUsuarios(@RequestBody List<Usuario> usuarios) {
        try {
            this.crudService.addUsuarios(usuarios);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/usuarios/deletebyquery")
    public ResponseEntity<String> deleteUsuarioByQuery(@RequestBody HashMap<String, Object> queryParams) {
        try {
            this.crudService.deleteUsuarioByQuery(queryParams);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/usuarios/update")
    public ResponseEntity<String> updateUsuario(@RequestBody UpdateRequest updateRequest) {
        try {
            this.crudService.updateUsuario(updateRequest.getQueryParams(), updateRequest.getUpdateParams());
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
