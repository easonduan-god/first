package com.numberone.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件处理工具类
 * 
 * @author numberone
 */
public class FileUtils
{
    /**
     * 输出指定文件的byte数组
     * 
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 输出指定文件的byte数组,没有就创建
     * 
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytesCreate(String filePath, OutputStream os) throws IOException
    {
    	FileInputStream fis = null;
    	try
    	{
    		File file = new File(filePath);
    		if (!file.exists())
    		{
    			file.createNewFile();
    		}
    		fis = new FileInputStream(file);
    		byte[] b = new byte[1024];
    		int length;
    		while ((length = fis.read(b)) > 0)
    		{
    			os.write(b, 0, length);
    		}
    	}
    	catch (IOException e)
    	{
    		throw e;
    	}
    	finally
    	{
    		if (os != null)
    		{
    			try
    			{
    				os.close();
    			}
    			catch (IOException e1)
    			{
    				e1.printStackTrace();
    			}
    		}
    		if (fis != null)
    		{
    			try
    			{
    				fis.close();
    			}
    			catch (IOException e1)
    			{
    				e1.printStackTrace();
    			}
    		}
    	}
    }
    
    /**
     * 从输入流中获取数据，输出到文件中,没有文件就创建
     * 
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytesCreate(String filePath, InputStream is) throws IOException
    {
    	InputStream fis = is;
    	OutputStream os = null;
    	try
    	{
    		File file = new File(filePath);
    		os = new FileOutputStream(file);
    		if (!file.exists())
    		{
    			file.createNewFile();
    		}
    		byte[] b = new byte[1024];
    		int length;
    		while ((length = fis.read(b)) > 0)
    		{
    			os.write(b, 0, length);
    		}
    	}
    	catch (IOException e)
    	{
    		throw e;
    	}
    	finally
    	{
    		if (os != null)
    		{
    			try
    			{
    				os.close();
    			}
    			catch (IOException e1)
    			{
    				e1.printStackTrace();
    			}
    		}
    		if (fis != null)
    		{
    			try
    			{
    				fis.close();
    			}
    			catch (IOException e1)
    			{
    				e1.printStackTrace();
    			}
    		}
    	}
    }

    /**
     * 删除文件
     * 
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
