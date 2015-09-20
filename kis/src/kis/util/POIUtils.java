package kis.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;

/**
 * Created by jim on 2015/7/30.
 */
public class POIUtils {

    public static Cell createCell(Row row, int columnIdx, CellStyle cellStyle) {
        Cell cell = row.createCell(columnIdx);
        if (cell != null)
            cell.setCellStyle(cellStyle);
        return cell;
    }

    public static Cell createCell(Row row, int columnIdx, Date cellValue, CellStyle cellStyle) {
        Cell cell = createCell(row, columnIdx, cellStyle);
        cell.setCellValue(cellValue);
        return cell;
    }


    public static Cell createCell(Row row, int columnIdx, String cellValue, CellStyle cellStyle) {
        Cell cell = createCell(row, columnIdx, cellStyle);
        cell.setCellValue(cellValue);
        return cell;
    }
}
