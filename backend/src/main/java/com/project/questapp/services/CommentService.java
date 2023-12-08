package com.project.questapp.services;


import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.repos.PostRepository;
import com.project.questapp.repos.UserRepository;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getByPostId(Optional<Long> postId, Optional<Long> userId) {
        if (postId.isPresent() && userId.isPresent()){
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }
        if (postId.isPresent())
            return commentRepository.findByPostId(postId.get());
        if (userId.isPresent())
            return commentRepository.findByUserId(userId.get());
        return commentRepository.findAll();
    }

    public Comment getOneComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest commentCreateRequest) {
        Optional<User> user = userRepository.findById(commentCreateRequest.getUserId());
        Optional<Post> post = postRepository.findById(commentCreateRequest.getPostId());
        if (user != null && post != null){
            Comment toSave = new Comment();
            toSave.setId(commentCreateRequest.getId());
            toSave.setText(commentCreateRequest.getText());
            user.ifPresent(toSave::setUser);
            post.ifPresent(toSave::setPost);
            return commentRepository.save(toSave);
        }
        return null;
    }

    public Comment updateOneComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()){
            var updatedComment = comment.get();
            updatedComment.setText(commentUpdateRequest.getText());
            return commentRepository.save(updatedComment);
        }
        return null;
    }

    public void deleteOneComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
