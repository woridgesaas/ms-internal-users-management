package org.woridge.ms_internal_users_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woridge.ms_internal_users_management.dto.promotiom.*;

import java.util.List;
import java.util.UUID;

public interface PromotionService {

    PromotionEmployeeInfoResponse getEmployeeInfoByCin(String cin);

    PromotionResponse promoteUser(CreatePromotionRequest request);

    PromotionResponse updatePromotion(UUID id, UpdatePromotionRequest request);

    List<PromotionResponse> getAllPromotions();
   // void deletePromotion(UUID id);
}