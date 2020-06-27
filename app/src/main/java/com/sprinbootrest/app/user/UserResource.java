package com.sprinbootrest.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    public EntityModel<User> findUser(@PathVariable Integer userId){

        User foundUser = userService.findOne(userId);

        //HATEOAS
        EntityModel<User> model = new EntityModel<>(foundUser);

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllUsers());

        model.add(linkTo.withRel("all-users"));


        if(foundUser == null){
            throw new UserNotFoundException("User Not Found with id :"+userId);
        }
        return model;
    }

    @PostMapping("/users")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        User savedUser = userService.save(user);

        URI Uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(Uri).build();

    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Integer userId){

        User user = userService.deleteUser(userId);
        if(user == null){
            throw new UserNotFoundException("User Not found with id : "+userId);
        }
    }

    @GetMapping("/jpa/users")
    public List<User> getAllJPAUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{userId}")
    public EntityModel<User> findJPAUser(@PathVariable Integer userId){

        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()){
            throw new UserNotFoundException("User Not Found with id :"+userId);
        }
        //HATEOAS
        EntityModel<User> model = new EntityModel<>(user.get());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllUsers());

        model.add(linkTo.withRel("all-users"));
        return model;
    }

    @PostMapping("jpa/users")
    public ResponseEntity addJPAUser(@Valid @RequestBody User user){

         User save = userRepository.save(user);

        URI Uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(Uri).build();
    }
}
