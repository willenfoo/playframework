package org.apache.playframework.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 用户在线信息
 *
 * @author Joker
 * @since 2018/8/1
 */
public class UserOnlineInfoDTO implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户ID
     */
    private Long merchantId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态[1:可用 2:违规封禁]
     */
    private Integer state;

    /**
     * 姓名
     */
    private String name;

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 角色列表
     */
    private Integer[] roles;

    private List<String> resourceCodes;

    /**
     * 测试标识（0=否，1=是）
     */
    private Integer testFlag;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getRoles() {
        return roles;
    }

    public void setRoles(Integer[] roles) {
        this.roles = roles;
    }

    public List<String> getResourceCodes() {
        return resourceCodes;
    }

    public void setResourceCodes(List<String> resourceCodes) {
        this.resourceCodes = resourceCodes;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(Integer testFlag) {
        this.testFlag = testFlag;
    }
}
