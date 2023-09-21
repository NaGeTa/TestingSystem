package com.example.testingsystem.entity;

public enum Category {

    MATH("Математика"),
    BIOLOGY("Биология"),
    INFORMATICS("Информатика"),
    RUSSIAN("Русский язык"),
    PHYSICS("Физика"),
    CHEMISTRY("Химия"),
    LITERATURE("Литература"),
    FOREIGN("Иностранный язык"),
    HISTORY("История");

    public final String value;

    Category(String value){
        this.value=value;
    }

//    void smth(){
//        Category.valueOf("HISTORY").value;
//    }
}
