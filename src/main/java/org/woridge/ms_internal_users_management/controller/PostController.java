package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.dto.post.*;
import org.woridge.ms_internal_users_management.service.PostService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<SuccessResponse<AddPostResponse>> addPost(@Valid @RequestBody AddPostRequest addPostRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Post created successfully",
                                postService.addPost(addPostRequest)
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> updatePost(@PathVariable UUID id, @Valid @RequestBody UpdatePostRequest updatePostRequest) {

        postService.updatePost(id, updatePostRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Post updated successfully",
                                null
                        )
                );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> updatePostStatus(@PathVariable UUID id, @Valid @RequestBody UpdatePostStatusRequest updatePostStatusRequest) {

        postService.updatePostStatus(id, updatePostStatusRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Post status updated successfully",
                                null
                        )
                );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<GetAllPostsResponse>>> getAllPosts() {

        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "Posts retrieved successfully",
                        postService.getAllPosts()
                )

        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<GetSpecificPostResponse>> getSpecificPost(@PathVariable UUID id) {

        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "Post retrieved successfully",
                        postService.getSpecificPost(id)
                )
        );
    }
}
