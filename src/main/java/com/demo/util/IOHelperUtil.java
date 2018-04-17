package com.demo.util;

import com.demo.common.Developer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * @author zWX454479 IO流工具类
 */
public final class IOHelperUtil
{

    private IOHelperUtil()
    {

    }

    /**
     * 压缩单文件
     * 
     * @param zipFile
     * @param savePath
     * @return
     * @throws IOException
     */
    public static void zipSingleFile(File unzipFile, String savePath)
    {
        File zipFile = new File(savePath);
        InputStream inputStream = null;
        ZipOutputStream zipOutputStream = null;
        try
        {

            inputStream = new FileInputStream(unzipFile);
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOutputStream.putNextEntry(new ZipEntry(unzipFile.getName()));
            int temp = 0;
            temp = inputStream.read();
            while (temp != -1)
            {
                zipOutputStream.write(temp);
                temp = inputStream.read();
            }
        }
        catch (IOException e)
        {
            Developer.error("ZipSingleFile Execption", e);
        }
        finally
        {
            close(zipOutputStream);
            close(inputStream);
        }
    }

    /**
     * 压缩文件夹
     * 
     * @param unzipFile
     * @param savePath
     * @param zipFileName
     * @throws IOException
     */
    public static void zipFolder(File unzipFile, String savePath)
    {
        File zipFile = new File(savePath);
        InputStream inputStream = null;
        ZipOutputStream zipOutputStream = null;
        int temp = 0;
        ZipEntry zipEntry = null;
        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(zipFile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            if (unzipFile.isDirectory())
            {
                File[] listFiles = unzipFile.listFiles();
                if (listFiles != null)
                {
                    int length = listFiles.length;
                    for (int i = 0; i < length; i++)
                    {
                        inputStream = new FileInputStream(listFiles[i]);
                        zipEntry = new ZipEntry(listFiles[i].getName());
                        zipOutputStream.putNextEntry(zipEntry);
                        temp = inputStream.read();
                        while (temp != -1)
                        {
                            zipOutputStream.write(temp);
                            temp = inputStream.read();
                        }
                        close(inputStream);
                    }
                }
            }
        }
        catch (IOException e)
        {
            Developer.error("ZipFolder Execption", e);
        }
        finally
        {
            close(inputStream);
            close(zipOutputStream);
            close(fileOutputStream);
        }

    }

    /**
     * @param savePath
     *            压缩路径
     * @param files
     *            要压缩的文件
     * @param folderName
     *            压缩包内文件夹名
     * @throws IOException
     */
    public static void zipFiles(String savePath, List<File> files, String folderName)
    {
        File zipFile = new File(savePath);
        InputStream inputStream = null;
        ZipOutputStream zipOutputStream = null;
        int length = files.size();
        int temp = 0;
        ZipEntry zipEntry = null;
        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(zipFile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (int i = 0; i < length; i++)
            {
                inputStream = new FileInputStream(files.get(i));
                zipEntry = new ZipEntry(folderName + File.separator + files.get(i).getName());
                zipOutputStream.putNextEntry(zipEntry);
                temp = inputStream.read();
                while (temp != -1)
                {
                    zipOutputStream.write(temp);
                    temp = inputStream.read();
                }
                close(inputStream);
            }
        }
        catch (IOException e)
        {
            Developer.error("ZipFiles Execption", e);
        }
        finally
        {
            close(zipOutputStream);
            close(fileOutputStream);
            close(inputStream);
        }
    }

    /**
     * @param savePath
     *            压缩路径
     * @param files
     *            要压缩的文件
     * @param folderName
     *            压缩包内文件夹名
     * @throws IOException
     */
    public static void zipFiles(String savePath, List<File> files, String folderName, List<String> fileNames)
    {
        File zipFile = new File(savePath);
        InputStream inputStream = null;
        ZipOutputStream zipOutputStream = null;
        int length = files.size();
        int temp = 0;
        ZipEntry zipEntry = null;
        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(zipFile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (int i = 0; i < length; i++)
            {
                inputStream = new FileInputStream(files.get(i));
                zipEntry = new ZipEntry(folderName + File.separator + fileNames.get(i));
                zipOutputStream.putNextEntry(zipEntry);
                temp = inputStream.read();
                while (temp != -1)
                {
                    zipOutputStream.write(temp);
                    temp = inputStream.read();
                }
                close(inputStream);
            }
        }
        catch (IOException e)
        {
            Developer.error("ZipFiles Execption", e);
        }
        finally
        {
            close(inputStream);
            close(zipOutputStream);
            close(fileOutputStream);
        }

    }

    /**
     * @param savePath
     *            压缩路径
     * @param files
     *            要压缩的文件
     * @throws IOException
     */
    public static void zipFiles(String savePath, List<File> files)
    {
        File zipFile = new File(savePath);
        InputStream inputStream = null;
        ZipOutputStream zipOutputStream = null;
        int length = files.size();
        int temp = 0;
        ZipEntry zipEntry = null;
        FileOutputStream fileOutputStream = null;
        try
        {

            fileOutputStream = new FileOutputStream(zipFile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (int i = 0; i < length; i++)
            {
                inputStream = new FileInputStream(files.get(i));
                zipEntry = new ZipEntry(files.get(i).getName());
                zipOutputStream.putNextEntry(zipEntry);
                temp = inputStream.read();
                while (temp != -1)
                {
                    zipOutputStream.write(temp);
                    temp = inputStream.read();
                }
                inputStream.close();
            }
        }
        catch (IOException e)
        {
            Developer.error("ZipFiles Execption", e);
        }
        finally
        {
            close(inputStream);
            close(fileOutputStream);
            close(zipOutputStream);
        }
    }

    /**
     * 解压缩
     * 
     * @param unzipFile
     * @param savePath
     * @throws IOException
     */
    // public static void unzipFile(File file, String savePath)
    // {
    // ZipFile zipFile = null;
    // ZipInputStream zipInputStream = null;
    // File outFile = null;
    // OutputStream out = null; // 定义输出流，用于输出每一个实体内容
    // InputStream input = null; // 定义输入流，读取每一个ZipEntry
    // FileInputStream fileInputStream = null;
    // try
    // {
    // zipFile = new ZipFile(file);
    // fileInputStream = new FileInputStream(file);
    // zipInputStream = new ZipInputStream(fileInputStream);
    // ZipEntry entry = zipInputStream.getNextEntry();
    // int temp = -1;
    // boolean mkdir = false;
    // List<Boolean> booleanTemp = new ArrayList<Boolean>(IopConst.DEFAULTNUM);
    // boolean btemp = false;
    // while (entry != null)
    // {
    // outFile = new File(savePath + entry.getName());
    // if (!outFile.getParentFile().exists())
    // {
    // mkdir = outFile.getParentFile().mkdir();
    // if (!mkdir)
    // {
    // zipFile.close();
    // zipInputStream.close();
    // return;
    // }
    // }
    // if (!outFile.exists())
    // { // 判断输出文件是否存在
    // btemp = outFile.createNewFile(); // 创建文件
    // booleanTemp.add(btemp);
    // }
    // input = zipFile.getInputStream(entry); // 得到每一个实体的输入流
    // out = new FileOutputStream(outFile); // 实例化文件输出流
    // temp = input.read();
    // while (temp != -1)
    // {
    // out.write(temp);
    // temp = input.read();
    // }
    // entry = zipInputStream.getNextEntry();
    //
    // out.close();
    // input.close();
    // }
    // }
    // catch (IOException e)
    // {
    // Developer.error("UnzipFiles Execption", e);
    //
    // }
    // finally
    // {
    // close(out);
    // close(input);
    // close(zipInputStream);
    // close(fileInputStream);
    // // fileInputStream.close();
    // // zipInputStream.close();
    // if (zipFile != null)
    // {
    // try
    // {
    // zipFile.close();
    // }
    // catch (IOException e1)
    // {
    // Developer.error("ZipFile close Execption", e1);
    // }
    // }
    // }
    // }

    /**
     * 根据指定文件名从class下把文件加载成InputStream实例
     * 
     * @param path
     * @return
     */
    public static InputStream getInputStreamFromClassPath(String path)
    {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

    /**
     * 创建目录
     * 
     * @param dirFile
     * @return
     * @throws RuntimeException
     */
    public static boolean createDir(File dirFile)
    {
        boolean flag = false;
        if (dirFile.exists())
        {
            flag = true;
        }
        else
        {
            flag = dirFile.mkdirs();
        }
        return flag;
    }

    /**
     * 删除目录
     * 
     * @param dirFile
     * @return
     * @throws RuntimeException
     */
    public static boolean deleteDir(File dirFile)
    {
        boolean flag = false;
        if (!dirFile.exists())
        {
            flag = true;
        }
        else
        {
            if (deleteSubFiles(dirFile))
            {
                flag = dirFile.delete();
            }
        }
        return flag;
    }

    /**
     * 清除文件夹下所有内容
     * 
     * @param dir
     * @return
     */
    public static boolean deleteSubFiles(File dir)
    {
        boolean flag = false;
        if (dir.exists() && dir.isDirectory())
        {
            File[] subs = dir.listFiles();
            int succ = 0;
            int fail = 0;
            int length = subs == null ? 0 : subs.length;
            File sub = null;
            try
            {
                for (int i = 0; i < length; i++)
                {
                    sub = subs[i];
                    if (sub.delete())
                    {
                        succ++;
                    }
                    else
                    {
                        fail++;
                    }
                }
                flag = true;
            }
            catch (Exception e)
            {
                return flag;
            }
            Developer.info("deleteSubFiles : ", "删除成功数 :" + succ + " | 删除失败数" + fail);
        }
        return flag;
    }

    /**
     * 获取指定文件的扩展名
     * 
     * @param fileName
     * @return
     */
    public static String getExtend(String fileName)
    {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    /**
     * 把源文件的内容复制到目标文件
     * 
     * @param src
     *            源文件
     * @param dest
     *            目标文件
     */
    public static void copy(File src, File dest) throws IOException
    {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        byte[] b = new byte[8192];
        try
        {
            bis = new BufferedInputStream(new FileInputStream(src));
            bos = new BufferedOutputStream(new FileOutputStream(dest));
            int temp = bis.read(b);
            while (temp != -1)
            {
                bos.write(b, 0, temp);
                temp = bis.read(b);
            }
            bos.flush();
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            close(bis);
            close(bos);
        }
    }

    /**
     * 把二进制文件读入字节数组，如果没有内容，字节数组为null
     * 
     * @param filePath
     * @return
     */
    public static byte[] read(String filePath)
    {
        byte[] data = null;
        BufferedInputStream in = null;
        try
        {
            in = new BufferedInputStream(new FileInputStream(filePath));
            data = new byte[in.available()];
            int read = in.read(data);
            if (read < 0)
            {
                in.close();
                throw new RuntimeException();
            }
            in.close();
        }
        catch (IOException e)
        {
            Developer.error("read Execption", e);
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    Developer.error("read Execption", e);
                }
            }
        }

        return data;
    }

    /**
     * 把字节数组为写入二进制文件，数组为null时直接返回
     * 
     * @param filePath
     * @param data
     */
    public static void write(String filePath, byte[] data)
    {
        if (data == null)
        {
            return;
        }
        try
        {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
            try
            {
                out.write(data);
            }
            finally
            {
                out.close();
            }
        }
        catch (IOException e)
        {
            Developer.error("write", e);
        }
    }

    /**
     * 同时关闭输入流和输出流，并把可能抛出的异常转换成RuntimeException
     * 
     * @param is
     * @param os
     */
    public static void close(InputStream is, OutputStream os)
    {
        close(is);
        close(os);
    }

    /**
     * 关闭输入流的工具方法，并把可能抛出的异常转换成RuntimeException
     * 
     * @param is
     */
    public static void close(InputStream is)
    {
        if (is != null)
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
                Developer.error("InputStream close Execption", e);
            }
        }
    }

    /**
     * 关闭输出流的工具方法，并把可能抛出的异常转换成RuntimeException
     * 
     * @param os
     */
    public static void close(OutputStream os)
    {
        if (os != null)
        {
            try
            {
                os.close();
            }
            catch (IOException e)
            {
                Developer.error("OutputStream close Execption", e);
            }
        }
    }

    /**
     * 关闭输出流的工具方法，并把可能抛出的异常转换成RuntimeException
     * 
     * @param os
     */
    public static void close(ZipOutputStream zos)
    {
        if (zos != null)
        {
            try
            {
                zos.close();
            }
            catch (IOException e)
            {
                Developer.error("OutputStream close Execption", e);
            }
        }
    }

    /**
     * 关闭输出流的工具方法，并把可能抛出的异常转换成RuntimeException
     * 
     * @param os
     */
    public static void close(ZipInputStream zis)
    {
        if (zis != null)
        {
            try
            {
                zis.close();
            }
            catch (IOException e)
            {
                Developer.error("OutputStream close Execption", e);
            }
        }
    }

}
