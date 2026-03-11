package org.woridge.ms_internal_users_management.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.exception.salary.SalaryNotFoundException;
import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.model.SalaryHistory;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.SalaryHistoryRepository;
import org.woridge.ms_internal_users_management.service.SalaryService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryHistoryRepository salaryHistoryRepository;

    @Override
    public void closeCurrentSalary(User user) {

        salaryHistoryRepository
                .findByUserAndValidToIsNull(user)
                .ifPresent(history ->
                        history.setValidTo(LocalDate.now())
                );
    }

    @Override
    public void createNewSalary(User user, Post post, BigDecimal salary) {

        SalaryHistory history = new SalaryHistory();
        history.setUser(user);
        history.setSalary(salary);
        history.setPostName(post.getName());
        history.setValidFrom(LocalDate.now());

        salaryHistoryRepository.save(history);
    }

    @Override
    public SalaryHistory getCurrentSalary(User user) {

        return salaryHistoryRepository
                .findByUserAndValidToIsNull(user)
                .orElseThrow(() ->
                        new SalaryNotFoundException(
                                "No current salary for user " + user.getId()
                        )
                );
    }

    @Override
    public List<SalaryHistory> getSalaryHistory(User user) {

        return salaryHistoryRepository.findByUserOrderByValidFromDesc(user);
    }
}