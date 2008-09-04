package de.jexp.bricksandmortar.format;

import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.WorkflowStepTest;
import de.jexp.bricksandmortar.WorkflowStepTestUtils;
import static de.jexp.bricksandmortar.WorkflowStepTestUtils.*;
import de.jexp.bricksandmortar.results.FileStepResult;
import de.jexp.bricksandmortar.results.ListStepResult;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public class ExcelReportStepTest extends WorkflowStepTest<ExcelReportStep> {
    private static final String EXCEL_STEP = "excelStep";

    public void testExcelReportStep() throws IOException, BiffException {
        final Collection<Map<String, ?>> testData = WorkflowStepTestUtils.getDefaultArticleTestDataList();
        final WorkflowContext ctx = new WorkflowContext();
        ctx.addResult(new ListStepResult(step.getParamName(), testData));
        step.runStep(ctx);
        final FileStepResult result = ctx.getResult(EXCEL_STEP);
        final Workbook workbook = Workbook.getWorkbook(new ByteArrayInputStream(result.getResult()));
        final Sheet sheet = workbook.getSheet(result.getName());
        assertNotNull("excel sheet ", sheet);
        assertEquals("excel rows 2", 2, sheet.getRows());
        assertEquals("excel columns 2", 2, sheet.getColumns());
        assertEquals("excel header", NAME, sheet.getCell(0, 0).getContents());
        assertEquals("excel header", PRICE, sheet.getCell(1, 0).getContents());
        assertEquals("excel value name", TEST_ARTICLE, sheet.getCell(0, 1).getContents());
        final BigDecimal price = new BigDecimal(sheet.getCell(1, 1).getContents());
        assertEquals("excel value price", TEST_PRICE_5, price.setScale(2));
    }

    protected void setUp() {
        step = new ExcelReportStep();
        step.setParamName("result");
        step.setName(EXCEL_STEP);
    }

    protected void tearDown() {
    }
}