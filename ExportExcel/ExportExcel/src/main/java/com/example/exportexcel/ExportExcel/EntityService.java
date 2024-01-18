package com.example.exportexcel.ExportExcel;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class EntityService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllEntities() {
        String query = "SELECT * FROM excel";
        boolean val = executeQuery(query);
        if (val) {
            return jdbcTemplate.queryForList(query);
        } else {
            System.out.println("Not Valid query");
            return null;
        }
    }

    public void insertEntity(EntityClass entity) {
        String query = "INSERT INTO excel (name, age) VALUES (?, ?)";
        jdbcTemplate.update(query, entity.getName(), entity.getAge());
    }

    public boolean executeQuery(String query) {
        try {
            // Parse the query
            Statement statement = CCJSqlParserUtil.parse(query);

            // Validate the query to allow only SELECT statements
            if (isValidSelectQuery(statement)) {
                jdbcTemplate.execute(query);
                return true;
            } else {
                System.out.println("Invalid query! Only SELECT queries are allowed.");
                return false;
            }
        } catch (JSQLParserException e) {
            System.out.println("Invalid query syntax!");
            return false;
        }
    }

    private boolean isValidSelectQuery(Statement statement) {
        // Check if the parsed statement is a SELECT statement
        if (statement instanceof Select) {
            Select selectStatement = (Select) statement;

            return true;
        } else {
            System.out.println("Invalid query! Only SELECT queries are allowed.");
            return false;
        }
    }
    static String getString(HttpServletResponse response, List<Map<String, Object>> data) throws IOException {
        // Create Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Entity Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        int colNum = 0;
        for (String columnName : data.get(0).keySet()) {
            Cell cell = headerRow.createCell(colNum++);
            cell.setCellValue(columnName);
        }

        // Create data rows
        int rowNum = 1;
        for (Map<String, Object> rowMap : data) {
            Row row = sheet.createRow(rowNum++);
            colNum = 0;
            for (Object value : rowMap.values()) {
                Cell cell = row.createCell(colNum++);
                if (value != null) {
                    cell.setCellValue(value.toString());
                } else {
                    cell.setCellValue("");
                }
            }
        }

        // Set the response headers
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=entity_data.xlsx");

        ByteArrayOutputStream baos =  new ByteArrayOutputStream();

        // Write to response stream
        workbook.write(baos);
        String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());

        workbook.close();
        return base64;
    }

}
