package com.example.demo.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="todos")
public class TodoDTO {

    @Id
    private String id;

    @NotNull(message="todo cannot be null")
    private String todo;
    @NotNull(message="desc cannot be null")
    private String desc;
    @NotNull(message="completed cannot be null")
    private Boolean completed;
    private Date createdAt;
    private Date updatedAt;
}
