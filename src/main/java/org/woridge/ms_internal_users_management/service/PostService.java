package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.post.*;

import java.util.List;
import java.util.UUID;

public interface PostService {

    AddPostResponse addPost(AddPostRequest addPostRequest);

    void updatePost(UUID id, UpdatePostRequest updatePostRequest);

    void updatePostStatus(UUID id, UpdatePostStatusRequest updatePostStatusRequest);

    List<GetAllPostsResponse> getAllPosts();

    GetSpecificPostResponse getSpecificPost(UUID id);
}
