package com.example.backend.entities;

import com.example.backend.model.shift.Shifts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("employees")
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private List<Shifts> nextWeek;
}
