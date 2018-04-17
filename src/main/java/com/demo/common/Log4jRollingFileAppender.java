package com.demo.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InterruptedIOException;

import com.demo.util.IOHelperUtil;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.CountingQuietWriter;



/**
 * @author zWX454479 定义log4J文件命名方式
 */
public class Log4jRollingFileAppender extends RollingFileAppender
{
    protected long nextRollover = 0L;

    /*
     * @see org.apache.log4j.RollingFileAppender#rollOver() 重写命名方式的方法
     */
    @Override
    public void rollOver()
    {
        // 标志位
        boolean renameSucceeded = true;
        if (this.qw != null)
        {
            long size = ((CountingQuietWriter) this.qw).getCount();
            this.nextRollover = (size + this.maxFileSize);
        }
        // 如果设置的最大文件数量>0
        if (this.maxBackupIndex > 0)
        {
            // 如果文件为第10个日志文件，则删除文件
            String newFileName = this.fileName.substring(0, this.fileName.lastIndexOf('.')) + this.maxBackupIndex
                    + this.fileName.substring(this.fileName.lastIndexOf('.'));
            File file = new File(newFileName);
            if (file.exists())
            {
                renameSucceeded = file.delete();
            }
            try
            {
                // 从第九个文件开始往前，逐一修改文件名
                for (int i = maxBackupIndex - 1; i >= 1; --i)
                {
                    if (!renameSucceeded)
                    {
                        continue;
                    }
                    file = new File(this.fileName.substring(0, this.fileName.lastIndexOf('.')) + i
                            + this.fileName.substring(this.fileName.lastIndexOf('.')));
                    // 重命名已有文件
                    if (file.exists())
                    {
                        File target = new File(this.fileName.substring(0, this.fileName.lastIndexOf('.')) + (i + 1)
                                + this.fileName.substring(this.fileName.lastIndexOf('.')));

                        FileUtils.moveFile(file, target);

                    }
                }
            }
            catch (IOException e)
            {
                renameSucceeded = false;
                Thread.currentThread().interrupt();
            }
            // 将boc.log更名为boc1.log 并重新创建boc.log文件
            if (renameSucceeded)
            {
                File target = new File(this.fileName.substring(0, this.fileName.lastIndexOf('.')) + 1
                        + this.fileName.substring(this.fileName.lastIndexOf('.')));
                closeFile();
                file = new File(this.fileName);
                try
                {
                    renameSucceeded = moveFile(file, target);
                    // }
                    // catch (IOException e)
                    // {
                    // renameSucceeded = false;
                    // Thread.currentThread().interrupt();
                    // }
                    // // renameSucceeded = file.renameTo(target);
                    // try
                    // {
                    if (!(renameSucceeded))
                    {

                        setFile(this.fileName, true, this.bufferedIO, this.bufferSize);

                    }
                }
                catch (IOException e)
                {
                    ifInterrupted(e);
                }
            }

        }
        if (!(renameSucceeded))
        {
            return;
        }
        try
        {
            setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
            this.nextRollover = 0L;
        }
        catch (IOException e)
        {
            ifInterrupted(e);
        }

    }

    /**
     * 异常处理
     * 
     * @param e
     */
    private void ifInterrupted(IOException e)
    {
        if (e instanceof InterruptedIOException)
        {
            Thread.currentThread().interrupt();
        }

    }

    /**
     * 复制和清空文件
     * 
     * @param srcFile
     * @param destFile
     * @return
     * @throws IOException
     */
    public boolean moveFile(File srcFile, File destFile) throws IOException
    {
        if (srcFile == null)
        {
            return false;
        }
        if (destFile == null)
        {
            return false;
        }
        if (!(srcFile.exists()))
        {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory())
        {
            throw new IOException("Source '" + srcFile + "' is a directory");
        }
        if (destFile.exists())
        {
            throw new FileExistsException("Destination '" + destFile + "' already exists");
        }
        if (destFile.isDirectory())
        {
            throw new IOException("Destination '" + destFile + "' is a directory");
        }
        boolean rename = srcFile.renameTo(destFile);
        if (!rename)
        {
            rename = copy(srcFile, destFile);
            if (rename)
            {
                rename = clearFile(srcFile);
            }
        }

        return rename;
    }

    // 流100MB,文件容量于100MB候便流
    final int MAX_BYTE = 100000000;

    /**
     * Copies src file to dst file. If the dst file does not exist, it is
     * created.8KB cache
     * 
     * @param src
     * @param dst
     * @throws IOException
     */
    public boolean copy(File src, File dst) throws IOException
    {

        FileInputStream in = null;
        FileOutputStream out = null;
        try
        {
            long streamTotal = 0; // 接受流容量
            int streamNum = 0; // 流需要数量
            int leave = 0; // 文件剩字符数
            byte[] inOutb = null;// byte数组接受文件数据
            int read = 0;
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);

            // 通available取流字符数
            streamTotal = in.available();
            // 取流文件需要数量
            streamNum = Double.valueOf(Math.floor(Double.valueOf(streamTotal) / Double.valueOf(MAX_BYTE))).intValue();
            // 文件剩余数量
            leave = Long.valueOf((streamTotal % MAX_BYTE)).intValue();
            // 文件容量于100MB进入循环
            if (streamNum > 0)
            {
                for (int i = 0; i < streamNum; i++)
                {
                    inOutb = new byte[MAX_BYTE];
                    // 读入流保存byte数组
                    read = in.read(inOutb, 0, MAX_BYTE);
                    if (read > 0)
                    {
                        out.write(inOutb);
                        out.flush();
                    }
                }
            }
            // 剩余流数据
            inOutb = new byte[leave];
            read = in.read(inOutb, 0, leave);
            if (read > 0)
            {
                out.write(inOutb);
                out.flush();
            }

            in.close();
            out.close();
            return true;
        }
        catch (FileNotFoundException e)
        {
            return false;
        }
        catch (IOException e)
        {
            return false;
        }
        finally
        {
            IOHelperUtil.close(in);
            IOHelperUtil.close(out);
        }
    }

    /**
     * 清空文件内容
     * 
     * @param src
     * @return
     * @throws IOException
     */
    boolean clearFile(File src) throws IOException
    {
        boolean flag = true;
        FileWriter fileWriter = null;
        try
        {
            fileWriter = new FileWriter(src);
            fileWriter.write(IopConst.STRING_EMPTY);
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e)
        {
            flag = false;
        }
        finally
        {
            if (fileWriter != null)
            {
                fileWriter.close();
            }
        }

        return flag;
    }

}
