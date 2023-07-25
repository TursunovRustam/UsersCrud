package com.example.backend.Service.QRCodeService;

import com.example.backend.Projection.ProductProjectionGetAll;

public interface QRCodeService {
    ProductProjectionGetAll getProductByCode(String code);
}
