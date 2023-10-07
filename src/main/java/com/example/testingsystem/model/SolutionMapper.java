package com.example.testingsystem.model;

import com.example.testingsystem.entity.Solution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SolutionMapper {
//    @Mapping(source = "user.first_name", target = "firstName")
//    @Mapping(source = "user.last_name", target = "lastName")
    @Mapping(target = "title", expression = "java(test.getTitle())")
    @Mapping(target = "creatorsMail", expression = "java(test.getCreator().getEmail())")
    SolutionForMail toMail(Solution solution);
}
