package de.jexp.bricksandmortar.format;

import static de.jexp.bricksandmortar.util.TextUtils.*;
import de.jexp.bricksandmortar.results.ErrorStepResult;
import de.jexp.bricksandmortar.results.FileStepResult;
import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.NamedWorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.StepResult;
import jxl.Workbook;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by mh14 on 06.07.2007 11:30:09
 */
public class ExcelReportStep extends NamedWorkflowStep {
    public void runStep(final WorkflowContext workflowContext) {
        boolean first=true;
        for (final String paramName : getParamNames()) {
            final ListStepResult result = workflowContext.getResult(paramName);
            // todo consistent handling of result names
            final String resultName = first ? getName() : paramName+".xls";
            setOnWorkflowContext(workflowContext, processResult(result, resultName));
            first=false;
        }
    }

    protected StepResult processResult(final ListStepResult result, final String resultName) {
        try {
            final byte[] bytes = createExcelWorkbook(result.getResult());
            return createExcelFileStepResult(resultName, bytes);
        } catch (IOException e) {
            return new ErrorStepResult(resultName, e);
        } catch (WriteException e) {
            return new ErrorStepResult(resultName, e);
        }
    }

    protected byte[] createExcelWorkbook(final Collection<Map<String, ?>> result) throws IOException, WriteException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream(10000);
        final WritableWorkbook workbook = Workbook.createWorkbook(bos);
        final WritableSheet sheet = workbook.createSheet(getName(), 0);
        if (!result.isEmpty()) {
            // todo header without content
            createHeader(sheet,0,result.iterator().next());
            createContent(result, sheet);
        }
        workbook.write();
        workbook.close();
        return bos.toByteArray();
    }

    protected void createContent(final Collection<Map<String, ?>> result, final WritableSheet sheet) throws WriteException {
        int rowIdx = 1;
        for (final Map<String, ?> row : result) {
            createRow(sheet, rowIdx++, row.values());
        }
    }

    protected void createHeader(final WritableSheet sheet, final int rowIdx, final Map<String, ?> row) throws WriteException {
        final Collection<String> titles = makeTitles(row.keySet());
        createRow(sheet, rowIdx, titles);
    }

    protected StepResult createExcelFileStepResult(final String name, final byte[] bytes) {
        final FileStepResult fileStepResult = new FileStepResult(name, bytes, "xls");
        fileStepResult.setContentType("application/x-msexcel");
        return fileStepResult;
    }

    private static void createRow(final WritableSheet sheet, final int row, final Iterable<?> values) throws WriteException {
        int column = 0;
        for (final Object value : values) {
            final WritableCell cell;
            if (value instanceof Number)
                cell = new jxl.write.Number(column, row, ((Number) value).doubleValue());
            else if (value instanceof Date)
                cell = new jxl.write.DateTime(column, row, ((Date) value));
            else
                cell = new jxl.write.Label(column, row, value == null ? null : value.toString());
            sheet.addCell(cell);
            column++;
        }
    }
}