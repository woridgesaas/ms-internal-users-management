package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.resignation.ResignationRequest;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationResponse;

import java.util.List;
import java.util.UUID;

public interface ResignationService {

    ResignationResponse create(ResignationRequest request);

    ResignationResponse getById(UUID id);

    List<ResignationResponse> getAll();

    ResignationResponse update(UUID id, ResignationRequest request);

    void delete(UUID id);
}