package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.post.*;
import org.woridge.ms_internal_users_management.exception.post.PostAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.post.PostIdNotMatchedException;
import org.woridge.ms_internal_users_management.exception.post.PostNameDuplicatedException;
import org.woridge.ms_internal_users_management.exception.post.PostNotFoundException;
import org.woridge.ms_internal_users_management.mapper.PostMapper;
import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.repository.PostRepository;
import org.woridge.ms_internal_users_management.service.PostService;
import org.woridge.ms_internal_users_management.utils.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public AddPostResponse addPost(AddPostRequest addPostRequest) {

        String canonicalizeName = StringUtils.canonicalizeString(addPostRequest.name());

        if (postRepository.existsByName(canonicalizeName)) {

            throw new PostNameDuplicatedException("Post with the following name : " + canonicalizeName + " already exists");
        }

        Post post = PostMapper.toPost(addPostRequest, canonicalizeName);

        return PostMapper.toAddPostResponse(postRepository.save(post));
    }

    @Override
    public void updatePost(UUID id, UpdatePostRequest updatePostRequest) {

        Post post = validatePostIdAndFetchPost(id, updatePostRequest.id());

        String canonicalName = StringUtils.canonicalizeString(updatePostRequest.name());

        if (!hasPostChanged(post, updatePostRequest, canonicalName)) {

            return;
        }

        isPostNameAvailable(post, canonicalName);

        PostMapper.updatePost(post, updatePostRequest, canonicalName);
    }

    @Override
    public void updatePostStatus(UUID id, UpdatePostStatusRequest updatePostStatusRequest) {

        Post post = validatePostIdAndFetchPost(id, updatePostStatusRequest.id());

        if (post.getStatus() == updatePostStatusRequest.status()) {
            return;
        }

        post.setStatus(updatePostStatusRequest.status());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetAllPostsResponse> getAllPosts() {

        return postRepository.findAll()
                .stream()
                .map(PostMapper::toGetAllPostsResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GetSpecificPostResponse getSpecificPost(UUID id) {

        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException("Post with the following id : " + id + " not found"));

        return PostMapper.toGetSpecificPostResponse(post);
    }

    private boolean hasPostChanged(Post post, UpdatePostRequest updatePostRequest, String canonicalName) {

        return !post.getName().equals(canonicalName)
                || post.getBaseSalary().compareTo(updatePostRequest.baseSalary()) != 0;
    }

    private void isPostNameAvailable(Post post, String canonicalName) {

        if (!post.getName().equals(canonicalName)
                && postRepository.existsByName(canonicalName)) {

            throw new PostAlreadyExistsException("Post with the following name : " + canonicalName + " already exists");
        }
    }

    private Post validatePostIdAndFetchPost(UUID idFromURI, UUID idFromBody) {

        if (!idFromURI.equals(idFromBody)) {

            throw new PostIdNotMatchedException("Post ids aren't matched");
        }

        return postRepository.findById(idFromURI).orElseThrow(() ->
                new PostNotFoundException("Post with the following id : " + idFromURI + " not found"));
    }
}
