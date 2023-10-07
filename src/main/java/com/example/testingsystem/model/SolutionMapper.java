package com.example.testingsystem.model;

import com.example.testingsystem.entity.Solution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SolutionMapper {
    @Mapping(source = "user.first_name", target = "firstName")
    @Mapping(source = "user.last_name", target = "lastName")
    @Mapping(target = "title", expression = "java(solution.getTest().getTitle())")
    @Mapping(target = "creatorsMail", expression = "java(solution.getTest().getCreator().getEmail())")
    @Mapping(target = "mark", expression = "java(solution.getMark().value)")
    SolutionForMail toMail(Solution solution);
}
