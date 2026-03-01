package org.woridge.ms_internal_users_management.mapper;

import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationCreateRequest;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationResponse;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationUpdateRequest;
import org.woridge.ms_internal_users_management.model.Resignation;
import org.woridge.ms_internal_users_management.model.User;

@Component
public class ResignationMapper {

    // Resignation → ResignationResponse
    public ResignationResponse toResponse(Resignation resignation) {
        return new ResignationResponse(
                resignation.getId(),
                resignation.getUser().getId(),
                resignation.getUser().getFirstName() + " " + resignation.getUser().getLastName(),
                resignation.getUser().getDepartment() != null ? resignation.getUser().getDepartment().getName() : "",
                resignation.getReason(),
                resignation.getNoticeDate(),
                resignation.getResignationDate(),
                resignation.getCreatedAt()
        );
    }

    // ResignationCreateRequest → Resignation entity
    public Resignation toEntity(ResignationCreateRequest request, User user) {
        return Resignation.builder()
                .user(user)
                .reason(request.reason())
                .noticeDate(request.noticeDate())
                .resignationDate(request.resignationDate())
                .build();
    }


    public void updateEntity(Resignation resignation, ResignationUpdateRequest request) {
        resignation.setReason(request.reason());
        resignation.setNoticeDate(request.noticeDate());
        resignation.setResignationDate(request.resignationDate());
    }
}