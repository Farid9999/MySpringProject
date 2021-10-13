package com.example.Security.Security.repositories;

import com.example.Security.Security.models.AppQuestion;
import com.example.Security.Security.models.AppUser;
import com.example.Security.Security.models.UserQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserQuestionRepository extends CrudRepository<UserQuestion,Long> {
    UserQuestion getById(Long id);
    Iterable<UserQuestion> findUserQuestionsByWorkerIdAndIsAnsweredNotNull(AppUser id);
    Iterable<UserQuestion> findUserQuestionByIsAnswered(Boolean isAnswers);
    Iterable<UserQuestion> findUserQuestionsByWorkerIdNotNull();
    UserQuestion findUserQuestionByWorkerIdNotNullAndAppUserAndIsAnswered(AppUser appUser,Boolean isAnswerd);
    UserQuestion findUserQuestionByIsAnsweredAndWorkerId(Boolean isAnswered , AppUser appUser);
    UserQuestion findUserQuestionByIsAnsweredAndAppUserAndWorkerIdNotNull(Boolean isAnswered,AppQuestion appQuestion);
    Iterable<UserQuestion> findUserQuestionsByAppUserAndIsAnsweredIsTrue(AppUser appUser);

}
