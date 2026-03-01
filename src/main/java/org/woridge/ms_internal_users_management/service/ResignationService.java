package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.resignation.ResignationCreateRequest;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationResponse;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface ResignationService {

    ResignationResponse create(ResignationCreateRequest request);

    ResignationResponse getById(UUID id);

    List<ResignationResponse> getAll();

    ResignationResponse update(UUID id, ResignationUpdateRequest request);

    void delete(UUID id);
}