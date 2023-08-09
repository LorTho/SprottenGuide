package com.example.backend.entities;

import com.example.backend.model.monthly.Daily;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Month;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("monthlyPlan")
public class MonthlyPlan {
    @Id
    String id;
    Month month;
    List<Daily> days;
}
