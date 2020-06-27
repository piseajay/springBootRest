package com.sprinbootrest.app.post;

import com.sprinbootrest.app.user.User;
import com.sprinbootrest.app.user.UserNotFoundException;
import com.sprinbootrest.app.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/posts")
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> getUserPosts(@PathVariable Integer id){

        Optional<User> foundUser = userRepository.findById(id);
        if(!foundUser.isPresent()){
            throw new UserNotFoundException("User not found with id : "+id);
        }
        return foundUser.get().getPosts();
    }

    @PostMapping("/user/{id}/posts")
    public ResponseEntity createPost(@PathVariable Integer id, @RequestBody Post post){

        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("User not found with id : "+id);
        }
        post.setUser(user.get());

        Post savedPost = postRepository.save(post);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
