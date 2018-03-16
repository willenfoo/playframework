package org.apache.playframework.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.playframework.security.UserUtils;
import org.apache.playframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuperController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    @Autowired
    protected ServletContext application;

    protected Logger logger = LogManager.getLogger(getClass());

    /**
     * 用户ID
     */
    protected String getUserId() {
        return UserUtils.getUserId();
    }

    protected String getParameter(String name) {
        return request.getParameter(name);
    }

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        datetimeFormat.setLenient(false);

        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    public String initDownloadFileName(String fileName, String suffixName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }
        String userAgent = request.getHeader("User-Agent");
        byte[] bytes;
        try {
            bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
            fileName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
            return String.format("attachment; filename=\"%s\"", fileName + "." + suffixName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } // name.getBytes("UTF-8")处理safari的乱码问题
        return "";
    }

}
