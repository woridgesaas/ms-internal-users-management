package org.woridge.ms_internal_users_management.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationRequest;
import org.woridge.ms_internal_users_management.dto.resignation.ResignationResponse;
import org.woridge.ms_internal_users_management.exception.resignation.ResignationAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.resignation.ResignationNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.model.Resignation;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.ResignationRepository;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.ResignationService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ResignationServiceImpl implements ResignationService {

    private final ResignationRepository resignationRepository;
    private final UserRepository userRepository;

    @Override
    public ResignationResponse create(ResignationRequest request) {


        User user = userRepository.findByCIN(request.cin())
                .orElseThrow(() -> new UserNotFoundException("User not found with CIN: " + request.cin()));

        if (resignationRepository.existsByUser(user)) {
            throw new ResignationAlreadyExistsException("Resignation already exists for this user");
        }

        Resignation resignation = Resignation.builder()
                .user(user)
                .reason(request.reason())
                .noticeDate(request.noticeDate())
                .resignationDate(request.resignationDate())
                .build();

        return toResponse(resignationRepository.save(resignation));
    }

    @Override

    public ResignationResponse getById(UUID id) {

        return toResponse(findResignationById(id));
    }

    @Override

    public List<ResignationResponse> getAll() {

        return resignationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ResignationResponse update(UUID id, ResignationRequest request) {

        Resignation resignation = findResignationById(id);

        resignation.setReason(request.reason());
        resignation.setNoticeDate(request.noticeDate());
        resignation.setResignationDate(request.resignationDate());

        return toResponse(resignationRepository.save(resignation));
    }

    @Override
    public void delete(UUID id) {

        resignationRepository.delete(findResignationById(id));
    }

    private Resignation findResignationById(UUID id) {
        return resignationRepository.findById(id)
                .orElseThrow(() -> new ResignationNotFoundException("Resignation not found with id: " + id));
    }

    private ResignationResponse toResponse(Resignation resignation) {
        return new ResignationResponse(
                resignation.getId(),
                resignation.getUser().getId(),
                resignation.getUser().getFirstName() + " " + resignation.getUser().getLastName(),
                resignation.getUser().getDepartment() != null ? resignation.getUser().getDepartment().getName() : "", // ✅
                resignation.getReason(),
                resignation.getNoticeDate(),
                resignation.getResignationDate(),
                resignation.getCreatedAt()
        );
    }
}