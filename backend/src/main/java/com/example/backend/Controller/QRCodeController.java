package com.example.backend.Controller;

import com.example.backend.Service.QRCodeService.QRCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {
    @Autowired
    private QRCodeServiceImpl qrCodeService;

    @GetMapping("{qrcode}")
    public ResponseEntity<?> getProductByCode(@PathVariable String qrcode){
        return ResponseEntity.ok(qrCodeService.getProductByCode(qrcode));
    }
}