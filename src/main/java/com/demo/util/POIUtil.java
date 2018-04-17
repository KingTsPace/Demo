package com.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import com.demo.common.IopConst;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;


/**
 * @author zWX454479 Excel工具
 */
public final class POIUtil
{
    private final static POIUtil poiUtil = new POIUtil();

    public static POIUtil getInstance()
    {
        return poiUtil;
    }

    /**
     * 单元格获取数据 XSSF
     * 
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell)
    {
        String cellValue = IopConst.STRING_EMPTY;
        if (cell == null)
        {
            return cellValue;
        }

        switch (cell.getCellType())
        {
            case XSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell))
                {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MixedUtil.FORMARTDATETYPE_1);
                    cellValue = simpleDateFormat.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                }
                else if (cell.getNumericCellValue() == Double.valueOf(cell.getNumericCellValue()).intValue())
                {
                    cellValue = Double.valueOf(cell.getNumericCellValue()).intValue() + IopConst.STRING_EMPTY;
                }
                else
                {
                    cellValue = cell.getNumericCellValue() + IopConst.STRING_EMPTY;
                }
                break;
            case XSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                try
                {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                catch (IllegalStateException e)
                {
                    cellValue = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            case XSSFCell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = cell.getBooleanCellValue() + IopConst.STRING_EMPTY;
                break;
            default:
                cellValue = "undefined";
                break;
        }
        return cellValue;
    }

    /**
     * @param request
     * @param n 需要生成的工作簿的数量
     * @return
     * @throws IOException
     */
    public Workbook getLockedWorkBook(HttpServletRequest request, int n) throws IOException
    {
        // 获取服务器路径，获取锁定格式文件
        String filePath = request.getSession().getServletContext().getRealPath("portrait") + "/protection.xls";
        InputStream inputStream = new FileInputStream(new File(filePath));// 读入文件
        HSSFWorkbook lockWorkBook = new HSSFWorkbook(inputStream);// 生成锁定工作簿
        for (int i = 0; i < n - 1; i++)
        {
            lockWorkBook.cloneSheet(0);
        }
        return lockWorkBook;
    }

}
