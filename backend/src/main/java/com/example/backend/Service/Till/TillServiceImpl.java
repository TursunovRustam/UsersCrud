package com.example.backend.Service.Till;

import com.example.backend.Entity.Till;
import com.example.backend.Repo.TillRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TillServiceImpl implements TillService{
    public final TillRepo tillRepo;
    @Override
    public List<Till> getAllTills() {
        return tillRepo.findAll();
    }
    @Override
    public Till saveTill(Till till) {
        return tillRepo.save(till);
    }

    @Override
    public Till getTillById(UUID id) {
        return tillRepo.findById(id).orElseThrow();
    }
}
