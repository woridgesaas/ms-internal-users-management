package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.post.PostResponse;
import org.woridge.ms_internal_users_management.repository.PostRepository;
import org.woridge.ms_internal_users_management.service.PostService;

import java.util.List;
@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(p -> new PostResponse(p.getId(), p.getName()))
                .toList();
    }
}
