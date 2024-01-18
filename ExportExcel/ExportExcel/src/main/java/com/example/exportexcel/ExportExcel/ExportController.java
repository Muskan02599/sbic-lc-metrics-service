package com.example.exportexcel.ExportExcel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entity")
public class ExportController {
    @Autowired
    private EntityService yourEntityService;

    @GetMapping("/export/excel")
    public String exportToExcel(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> data = yourEntityService.getAllEntities();

        return EntityService.getString(response, data);
    }

    @PostMapping("/insert")
    public void insertEntity(@RequestBody EntityClass entity) {
        // Perform validation and other business logic as needed
        yourEntityService.insertEntity(entity);
    }
}