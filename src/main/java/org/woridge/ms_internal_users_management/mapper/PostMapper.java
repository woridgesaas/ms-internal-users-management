package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.post.*;
import org.woridge.ms_internal_users_management.model.Post;

public class PostMapper {

    public static Post toPost(AddPostRequest addPostRequest, String canonicalizeName) {

        return Post
                .builder()
                .name(canonicalizeName)
                .baseSalary(addPostRequest.baseSalary())
                .status(addPostRequest.status())
                .build();
    }

    public static void updatePost(Post post, UpdatePostRequest updatePostRequest, String canonicalizeName) {

        post.setName(canonicalizeName);
        post.setBaseSalary(updatePostRequest.baseSalary());
    }

    public static AddPostResponse toAddPostResponse(Post post) {

        return AddPostResponse
                .builder()
                .id(post.getId())
                .name(post.getName())
                .baseSalary(post.getBaseSalary())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static GetAllPostsResponse toGetAllPostsResponse(Post post) {

        return GetAllPostsResponse
                .builder()
                .id(post.getId())
                .name(post.getName())
                .baseSalary(post.getBaseSalary())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static GetSpecificPostResponse toGetSpecificPostResponse(Post post) {

        return GetSpecificPostResponse
                .builder()
                .id(post.getId())
                .name(post.getName())
                .baseSalary(post.getBaseSalary())
                .status(post.getStatus())
                .build();
    }
}
