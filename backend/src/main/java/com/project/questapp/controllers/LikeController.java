package com.project.questapp.controllers;

import com.project.questapp.entities.Like;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {
    LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<Like> getAllLikes(@RequestParam Optional<Long> postId,
                                  @RequestParam Optional<Long> userId){
        return likeService.getAllLikes(postId, userId);
    }

    @GetMapping("/{likeId}")
    public Like getOneLike(@PathVariable Long likeId){
        return likeService.getOneLike(likeId);
    }

    @PostMapping
    public Like createOneLike(@RequestBody LikeCreateRequest likeCreateRequest){
        return likeService.createOneLike(likeCreateRequest);
    }

    // should this endpoint be present?
//    @PutMapping("/{likeId}")
//    public Like updateOneLike(@PathVariable Long likeId, @RequestBody LikeCreateRequest likeCreateRequest){
//        return likeService.updateOneLike(likeId, likeCreateRequest);
//    }


    @DeleteMapping("/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId){
        likeService.deleteOneLike(likeId);

    }


}