package com.demo.util;

import com.demo.common.IopConst;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



/**
 * 文件工具类
 * 
 * @author yWX418687
 * 
 */
public final class FileUtils
{
    private static volatile FileUtils fileUtils;

    private FileUtils()
    {

    }

    public static FileUtils getFileUtils()
    {
        if (fileUtils == null)
        {
            synchronized (FileUtils.class)
            {
                if (fileUtils == null)
                {
                    fileUtils = new FileUtils();
                }
            }
        }

        return fileUtils;
    }

    /**
     * 根据url删除文件
     * 
     * @param url
     * @param savePath
     *            服务器路径
     * @return 状态集合
     */
    public List<Boolean> delFileByURL(String url, String savePath)
    {
        if (url == null || savePath == null)
        {
            return null;
        }

        // 根据url获取绝对路径
        String realPath = getRealPath(url, savePath);

        File file = new File(realPath);
        // 删除文件
        return delFile(file);
    }

    /**
     * 根据url获取绝对路径
     * 
     * @param url
     * @param savePath
     * @return 绝对路径
     */
    public String getRealPath(String url, String savePath)
    {

        String[] requrlPaths = url.split("/");

        String fileName = requrlPaths[requrlPaths.length - 1];

        return savePath + '/' + fileName;
    }

    /**
     * 删除文件目录及下级文件
     * 
     * @parampath
     *            绝对路径
     * @return 状态集合
     */
    public List<Boolean> delFile(File file)
    {
        List<Boolean> flagList = new ArrayList<Boolean>(IopConst.DEFAULTNUM);

        // File file = new File(path);
        if (file.exists() && file.isDirectory())
        {
            // 如果文件是目录，清空
            // 获取目录中的文件列表
            File[] fileList = file.listFiles();
            if (fileList != null)
            {
                int length = fileList.length;
                File f = null;
                for (int i = 0; i < length; i++)
                {
                    f = fileList[i];
                    if (file.isDirectory())
                    {
                        delFile(fileList[i]);
                    }
                    flagList.add(f.delete());
                }
            }

            // 删除空目录或文件
            flagList.add(file.delete());
        }

        return flagList;
    }

    /**
     * 根据URL获取文件名(不带_之后的识别码)
     * 
     * @param url
     * @return
     */
    public String getFileNameByURL(String url)
    {
        String fileName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("_")) + url.substring(url.lastIndexOf("."));

        return fileName;
    }

    /**
     * @Method: makeFileName
     * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     * @param filename
     *            文件的原始名称
     * @return uuid+"_"+文件的原始名称
     */
    public static String makeFileName(String filename)
    {

        // 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        int i = filename.lastIndexOf(".");

        String firstName = filename.substring(0, i);
        String lastName = filename.substring(i);

        String[] split = UUID.randomUUID().toString().split("-");

        return firstName + '_' + split[split.length - 1] + lastName;
    }
}
