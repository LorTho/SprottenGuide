package com.example.backend.model.schedule;

import com.example.backend.model.shift.Shifts;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WishSchedule {
    private String emoloyeeId;
    private List<Shifts> shifts;
}
