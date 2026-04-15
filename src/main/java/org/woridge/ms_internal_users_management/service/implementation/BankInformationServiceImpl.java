package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.bank_information.AddBankInformationRequest;
import org.woridge.ms_internal_users_management.dto.bank_information.AddBankInformationResponse;
import org.woridge.ms_internal_users_management.dto.bank_information.UpdateBankInformationRequest;
import org.woridge.ms_internal_users_management.exception.bank_information.*;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.mapper.BankInformationMapper;
import org.woridge.ms_internal_users_management.model.BankInformation;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.BankInformationRepository;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.BankInformationService;
import org.woridge.ms_internal_users_management.service.BankInformationValidator;
import org.woridge.ms_internal_users_management.service.UserValidator;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class BankInformationServiceImpl implements BankInformationService {

    private final BankInformationRepository bankInformationRepository;
    private final UserRepository userRepository;
    private final BankInformationValidator bankInformationValidator;
    private final UserValidator userValidator;

    @Override
    public AddBankInformationResponse AddBankInformation(UUID userId, AddBankInformationRequest addBankInformationRequest) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id : " + userId + " not found"));

        bankInformationValidator.validateBankInformationForCreation(userId, addBankInformationRequest);

        BankInformation bankInformation = BankInformationMapper.toBankInformation(addBankInformationRequest, user);

        return BankInformationMapper.toAddBankInformationResponse(bankInformationRepository.save(bankInformation));
    }

    @Override
    public void updateBankInformation(UUID userId, UUID bankInformationId, UpdateBankInformationRequest updateBankInformationRequest) {

        if (!bankInformationId.equals(updateBankInformationRequest.id())) {

            throw new BankInformationIdNotMatchedException("Bank information ids aren't matched");
        }

        userValidator.validateUser(userId);

        bankInformationValidator.validateBankInformationForUpdate(userId, updateBankInformationRequest);

        BankInformation currentBankInformation = bankInformationRepository.findByUserId(userId).orElseThrow(() ->
                new BankInformationNotFoundException("No bank information found for user with id : " + userId));

        BankInformationMapper.updateBankInformation(currentBankInformation, updateBankInformationRequest);
    }
}
