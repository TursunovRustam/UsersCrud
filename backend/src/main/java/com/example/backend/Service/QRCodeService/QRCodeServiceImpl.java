package com.example.backend.Service.QRCodeService;

import com.example.backend.Projection.ProductProjectionGetAll;
import com.example.backend.Repo.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {
    @Autowired
    private ProductRepo productRepo;
    @Override
    public ProductProjectionGetAll getProductByCode(String code) {
        return productRepo.getByCode(code);
    }
}
