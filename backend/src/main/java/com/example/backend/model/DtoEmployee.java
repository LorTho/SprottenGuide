package com.example.backend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class DtoEmployee {
    private String id;
    private String firstName;
    private String lastName;
    private ArrayList<Map<Integer, List<Time>>> weeklyTime;
}
