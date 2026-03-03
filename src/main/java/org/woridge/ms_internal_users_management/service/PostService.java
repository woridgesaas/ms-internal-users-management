package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.post.PostResponse;

import java.util.List;

public interface PostService {
    List<PostResponse> getAllPosts();
}
