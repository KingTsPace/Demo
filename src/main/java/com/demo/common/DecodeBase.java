package com.demo.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 导出Service抽出的公共方法，包括解析base64编码方法和获得一个随机文件名方法
 * 
 * @author yWX418694
 * 
 */
public class DecodeBase
{
    /**
     * 打印日志的实体
     */
    protected final static Logger logger = Logger.getLogger("operation");

    /**
     * 解析base64，返回图片所在路径
     * 
     * @param base64Info
     * @return picPath 图片保存路径
     */
    public static String decodeBase64(String base64Info, File filePath)
    {
        OutputStream os = null;
        if (StringUtils.isEmpty(base64Info))
        {
            return null;
        }
        Base64 decoder = new Base64();
        String[] arr = base64Info.split("base64,");
        // 数据中：data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABI4AAAEsCAYAAAClh/jbAAA
        // ... 在"base64,"之后的才是图片信息
        String picPath = filePath + String.valueOf('/') + UUID.randomUUID().toString() + ".png";
        try
        {
            byte[] buffer = decoder.decode(arr[1]);
            os = new FileOutputStream(picPath);
            os.write(buffer);
        }
        catch (IOException e)
        {
            throw new RuntimeException();
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {
                    logger.error("io erorr : " + e.getMessage());
                }
            }
        }
        return picPath;
    }

    /**
     * 获得一个随机、唯一的文件名
     * 
     * @return fileName 文件名
     */
    public static String getFileName()
    {
        java.util.Date dt = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = fmt.format(dt);
        return fileName;
    }

    public static HSSFSheet insertPicture(HSSFWorkbook workBook, HSSFSheet sheet, String picBase, String savePath, HSSFClientAnchor anchor)
    {
        logger.info("enter insertPicture ... ");
        try
        {
            File file = new File(savePath);
            File fileCache = new File(savePath);

            picBase = picBase.replaceAll(" ", "+");
            String picPath = DecodeBase.decodeBase64(picBase, file);
            File fileTemp = new File(picPath);
            BufferedImage image = ImageIO.read(fileTemp);
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            ByteArrayOutputStream byteArrayOut = null;
            byteArrayOut = new ByteArrayOutputStream();
            ImageIO.setCacheDirectory(fileCache);
            ImageIO.write(image, "png", byteArrayOut);
            // 插入图片
            patriarch.createPicture(anchor, workBook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        }

        catch (IOException e)
        {
            logger.error("insertPicture :", e);
        }
        catch (RuntimeException e)
        {
            logger.error("insertPicture :", e);
        }
        return sheet;
    }
}
