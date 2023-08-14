package com.example.backend.model.monthly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Pause {
    LocalTime start;
    LocalTime end;
}
