package com.example.backend.entities;

import com.example.backend.model.schedule.ShiftSchedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Data
@Document("workSchedule")
public class WorkSchedule {
    @Id
    private String id;
    private String name;
    private List<ShiftSchedule> drivers;
    private List<ShiftSchedule> kitchen;
}
