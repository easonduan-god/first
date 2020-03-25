package com.numberone.web.controller.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.config.Global;
import com.numberone.common.utils.file.FileUploadUtils;
import com.numberone.common.utils.file.FileUtils;
import com.numberone.framework.config.ServerConfig;

/**
 * 通用请求处理
 * 
 * @author numberone
 */
@Controller
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    /**
     * 文件上传路径
     */
    public static final String UPLOAD_PATH = "/profile/upload/";

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName,String originalFileName, boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
    	//原始名不为空
    	if(!StringUtils.isEmpty(originalFileName)){
    		try
            {
                String filePath = Global.getDownloadPath() + fileName;
                
                response.setCharacterEncoding("utf-8");
                response.setContentType(getContentType());
                response.setHeader("Content-Disposition",
                        "attachment;fileName=" + setFileDownloadHeader(request, originalFileName));
                FileUtils.writeBytes(filePath, response.getOutputStream());
                if (delete)
                {
                    FileUtils.deleteFile(filePath);
                }
            }
            catch (Exception e)
            {
                log.error("下载文件失败", e);
            }
    	}
        String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
        try
        {
            String filePath = Global.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }
    /**
     * 通用预览请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("common/preview")
    public String preview(String fileName,String originalFileName, boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
    	//原始名不为空
    	if(!StringUtils.isEmpty(originalFileName)){
    		try
            {
                String filePath = Global.getDownloadPath() + fileName;
                
                /*response.setCharacterEncoding("utf-8");
                response.setContentType(getContentType());
                response.setHeader("Content-Disposition",
                        "attachment;fileName=" + setFileDownloadHeader(request, originalFileName));*/
                FileUtils.writeBytes(filePath, response.getOutputStream());
                /*if (delete)
                {
                    FileUtils.deleteFile(filePath);
                }*/
            }
            catch (Exception e)
            {
                log.error("下载文件失败", e);
            }
    	}
    	return "preview";
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = Global.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + UPLOAD_PATH + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("originalFileName", file.getOriginalFilename());
            ajax.put("url", url);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    public String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException
    {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE"))
        {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        }
        else if (agent.contains("Firefox"))
        {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        }
        else if (agent.contains("Chrome"))
        {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        else
        {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
    
    public String getContentType(){
    	/*
         * 常见的MIME类型

		超文本标记语言文本 .html,.html text/html
		普通文本 .txt text/plain
		RTF文本 .rtf application/rtf
		GIF图形 .gif image/gif
		JPEG图形 .ipeg,.jpg image/jpeg
		au声音文件 .au audio/basic
		MIDI音乐文件 mid,.midi audio/midi,audio/x-midi
		RealAudio音乐文件 .ra, .ram audio/x-pn-realaudio
		MPEG文件 .mpg,.mpeg video/mpeg
		AVI文件 .avi video/x-msvideo
		GZIP文件 .gz application/x-gzip
		TAR文件 .tar application/x-tar
		"multipart/form-data"
         */
    	return  "multipart/form-data";
    }
}
