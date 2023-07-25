package com.example.backend.Service.Till;

import com.example.backend.Entity.Till;

import java.util.List;
import java.util.UUID;

public interface TillService {
    List<Till> getAllTills();

    Till saveTill(Till till);

    Till getTillById(UUID id);
}
