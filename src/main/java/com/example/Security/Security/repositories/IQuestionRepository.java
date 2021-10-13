package com.example.Security.Security.repositories;

import com.example.Security.Security.models.AppQuestion;
import org.springframework.data.repository.CrudRepository;

public interface IQuestionRepository extends CrudRepository<AppQuestion, Long> {

}
