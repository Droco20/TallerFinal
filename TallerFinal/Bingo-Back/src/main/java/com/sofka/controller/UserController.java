package com.sofka.controller;
import com.sofka.domain.User;
import com.sofka.service.UserService;
import com.sofka.util.Response;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/** Cada controlador permite darle funcionalidad a el programa */

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController extends User {

    @Autowired
    private UserService userService;

    private Response response = new Response();
    private Long id;

    @GetMapping(path = "/")
    public Map<String, String> index() {
        var res = new HashMap<String, String>();
        res.put("message", "Hola Dani ;)");
        return res;
    }

    /** Permite leer los usuarios */

    @GetMapping(path = "/users")
    public Response list(){
        try {
            response.data = userService.list();
        } catch (Exception exc) {
            response.error = true;
            response.message = exc.getMessage();
            response.status = "ERROR";
        }
        return response;
    }

    /** Permite crear users */

    @PostMapping(path = "/user")
    public ResponseEntity<Response> create(UserController user){
        response.data = user;
        try {
            log.info("usuario a crear: {}", user);
            userService.save(user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception exc) {
            response.status = exc.getCause().toString();
            response.error = true;
            if (Pattern.compile("(user.use_email_UNIQUE)").matcher(exc.getMessage()).find()) {
                response.message = "Usuario duplicado";
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            } else {
                response.message = exc.getMessage();
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /** Permite borrar usuarios */

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<UserController> delete(UserController user){
        log.info("Usuario a borrar: {}", user);
        userService.delete(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



}
