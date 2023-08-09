package com.example.backend.model.monthly;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@RequiredArgsConstructor
@Data
public class Pause {
    LocalTime start;
    LocalTime end;
}
