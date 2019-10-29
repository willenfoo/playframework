package org.apache.playframework.web.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.api.R;
import org.apache.playframework.domain.PagerResult;
import org.apache.playframework.domain.SimpleResult;
import org.apache.playframework.exception.CustomErrorCode;
import org.apache.playframework.util.BeanCopierUtils;
import org.apache.playframework.util.HttpServletUtils;
import org.apache.playframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 所有Controller都应该继承该类，但是要看具体需求
 * 
 * @author willenfoo
 */
public class BaseController extends ApiController {

    public static String CHARSET_UTF8 = "UTF-8";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 成功简单结果，接口里面， 返回一个字段的， 都调用这个方法返回
     * @param data
     * @param <T>
     * @return
     */
    protected <T> R<SimpleResult<T>> successResult(T data) {
        return R.ok(new SimpleResult(data));
    }

    /**
     * 分页结果返回
     * @param pagerResult
     * @param <T>
     * @return
     */
    protected <T> R<PagerResult<T>> successPager(PagerResult<?> pagerResult, Class<T> clasz) {
        PagerResult<T> pagerResultResp = new PagerResult<>();
        pagerResultResp.setTotal(pagerResult.getTotal());
        pagerResultResp.setRecords(BeanCopierUtils.copyToList(pagerResult.getRecords(), clasz));
        return R.ok(pagerResultResp);
    }

    /**
     * 得到用户id， 提供给手机端的API接口可以获取用户
     * @return
     */
    public Long getUserId() {
        String userId = request.getHeader("userId");
        //用户未登录，会抛出异常
        Assert.notNull(CustomErrorCode.USER_NOT_LOGIN, userId);
        return Long.valueOf(userId);
    }

    /**
     * 得到用户真实姓名
     * @return
     */
    public Long getRealName() {
        String userId = request.getHeader("realName");
        //用户未登录，会抛出异常
        Assert.notNull(CustomErrorCode.USER_NOT_LOGIN, userId);
        return Long.valueOf(userId);
    }

    /**
     * 得到该用户的所在商家id
     * @return
     */
    public Long getMerchantId() {
        String userId =request.getHeader("merchantId");
        //用户未登录，会抛出异常
        Assert.notNull(CustomErrorCode.USER_NOT_LOGIN, userId);
        return  Long.valueOf(userId);
    }

    /**
     * 得到appId， 提供给手机端的API接口可以获取用户
     * @return
     */
    public Integer getAppId() {
        String appId = request.getHeader("appId");
        //用户未登录，会抛出异常
        if (appId == null) {
            logger.warn("appId不能为空");
            Assert.notNull(CustomErrorCode.PARAMETER__ERROR, appId);
        }
        return Integer.valueOf(appId);
    }

    public String getIpAddr() {
        return HttpServletUtils.getIpAddr(request);
    }

    public String getInputStream() {
        return getInputStream(CHARSET_UTF8);
    }

    public String getInputStream(String charset) {
        return HttpServletUtils.getInputStream(request, charset);
    }

    /**
     * 获得请求路径
     * @return
     */
    public String getRequestPath() {
        return HttpServletUtils.getRequestPath(request);
    }

    public Map<String, String> getParameterMap() {
        return HttpServletUtils.getParameterMap(request);
    }

    public boolean isAjaxRequest() {
        return HttpServletUtils.isAjaxRequest(request);
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
