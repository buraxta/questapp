package com.project.questapp.services;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.repos.LikeRepository;
import com.project.questapp.repos.PostRepository;
import com.project.questapp.repos.UserRepository;
import com.project.questapp.requests.LikeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    LikeRepository likeRepository;
    UserRepository userRepository;
    PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<Like> getAllLikes(Optional<Long> postId, Optional<Long> userId) {
        if (postId.isPresent())
            return likeRepository.findByPostId(postId);
        if (userId.isPresent())
            return likeRepository.findByUserId(userId);
        return likeRepository.findAll();
    }

    public Like getOneLike(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest likeCreateRequest) {
        var like = new Like();
        Optional<User> user = userRepository.findById(likeCreateRequest.getUserId());
        user.ifPresent(like::setUser);
        Optional<Post> post = postRepository.findById(likeCreateRequest.getPostId());
        post.ifPresent(like::setPost);
        like.setId(likeCreateRequest.getId());
        return likeRepository.save(like);
    }

    public void deleteOneLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }

//    public Like updateOneLike(Long likeId, LikeCreateRequest likeCreateRequest) {
//        Optional<Like> like = likeRepository.findById(likeId);
//        if (like.isPresent()){
//            var newLike = like.get();
//            newLike.setPost(likeCreateRequest.getPostId());
//        }
//    }
}
