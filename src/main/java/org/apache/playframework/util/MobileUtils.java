package org.apache.playframework.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by IntelliJ IDEA.
 * User: xialei@centuryinvest.cn
 * Date: 2017/11/3 10:39
 * Description: 手机号信息查询
 * @Modified  GuoHonglin   	E-mail:	guo.honglin@centchain.com
 */
public class MobileUtils {

	private  static final Log LOG = LogFactory.getLog(MobileUtils.class);
	
	//校验中国大陆手机号码
	/**
	 * @Fields IS_CHINA_PHONE_LEGAL_REGEXP : 中国大陆手机号段校验正则
	 * 	<p>大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
	 * 	<p>此方法中前三位格式有：
	 * 		<p>13+任意数
	 * 		<p>14+任意5-9
	 * 		<p>15+除4的任意数
	 *		<p>166
	 * 		<p>17+除9的任意数
	 * 		<p>18+任意数0-9
	 * 		<p>19+任意数8-9
	 * ===============================================================
	 *   <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
	 *        			、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
	 *   <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
	 *   <p>电信的号段：133、153、180（未启用）、189</p>
	 *               
	 *   <p>三大运营商最新号段 (截止2017年11月01日 ) 
	 *     <p>移动号段：134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 198
	 *     <p>联通号段：130 131 132 145 146 155 156 166 171 175 176 185 186
	 *     <p>电信号段：133 149 153 173 174 177 180 181 189 199
	 *     <p>虚拟运营商:170
	 * 
	 */
	public  static final String IS_CHINA_PHONE_LEGAL_REGEXP = "^((13[0-9])|(14[5-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9]))\\d{8}$";
	/**
	 * @Fields IS_CHINA_MOBILE_PHONE_LEGAL_REGEXP : 中国移动：手机号段校验正则
	 * <p>截止2017年11月01日
	 * <p>移动号段：134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 198
	 */
	public  static final String IS_CHINA_MOBILE_PHONE_LEGAL_REGEXP = "^((13[4-9])|(14[7-8])|(15[0,1,2,7-9])|(17[2,8])|(18[2-4,7,8])|(198))\\d{8}$";
	/**
	 * @Fields IS_CHINA_TELECOM_PHONE_LEGAL_REGEXP : 中国电信：手机号段校验正则
	 * <p>截止2017年11月01日
	 * <p>电信号段：133 149 153 173 174 177 180 181 189 199
	 */
	public  static final String IS_CHINA_TELECOM_PHONE_LEGAL_REGEXP = "^((133)|(149)|(153)|(17[3,4,7])|(18[0,1,9])|(199))\\d{8}$";
	/**
	 * @Fields IS_CHINA_UNICOM_PHONE_LEGAL_REGEXP : 中国联通：手机号段校验正则
	 * <p>截止2017年11月01日
	 * <p>联通号段：130 131 132 145 146 155 156 166 171 175 176 185 186
	 */
	public  static final String IS_CHINA_UNICOM_PHONE_LEGAL_REGEXP = "^((13[0-2])|(14[5-6])|(15[5-6])|(166)|(17[1,,5-6])|(18[5-6]))\\d{8}$";
	/**
	 * @Fields IS_CHINA_VIRTUAL_OPERATOR_PHONE_LEGAL_REGEXP : 虚拟运营商：手机号段校验正则
	 * <p>截止2017年11月01日
	 * <p>虚拟运营商:170
	 */
	public  static final String IS_CHINA_VIRTUAL_OPERATOR_PHONE_LEGAL_REGEXP = "^(170)\\d{8}$";
	
	
	/**
	 * @Title			: 	isChinaPhoneLegal
	 * @author			:	GuoHonglin   	E-mail:	guo.honglin@centchain.com
	 * @param	phone 	:	中国移动、中国联通、中国电信、虚拟运营商的手机号码
	 * @param	regExp	:	运营商手机号段的校验正则
	 * @throws PatternSyntaxException    当校验正则语法错误的时候会报这个异常
	 * @return 			:	boolean    	 校验结果，验证成功返回true，验证失败返回false
	 * @version 		:   V1.0   @date 2017年11月17日 下午1:12:58 
	 * @Description     :   中国大陆手机号段 所属运营商验证
	 */
	public static boolean isChinaPhoneLegal(String phone,String regExp) throws PatternSyntaxException {
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	
	/**
     * 判断是否是移动号码
	 * @param number
     * @return
     */
	public static boolean isMobileNum(String number) {
		if (StringUtils.isBlank(number)) {
			return false;
		} else {
			String regex = "^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$";
			return number.matches(regex);
		}
	}

	public static void main(String[] args) {
		System.out.println(isMobileNum("15618087588"));
		System.out.println(isChinaPhoneLegal("15618087588",IS_CHINA_MOBILE_PHONE_LEGAL_REGEXP));
		System.out.println(isChinaPhoneLegal("15618087588",IS_CHINA_PHONE_LEGAL_REGEXP));
		System.out.println(isChinaPhoneLegal("15618087588",IS_CHINA_TELECOM_PHONE_LEGAL_REGEXP));
		System.out.println(isChinaPhoneLegal("15618087588",IS_CHINA_UNICOM_PHONE_LEGAL_REGEXP));
		System.out.println(isChinaPhoneLegal("15618087588",IS_CHINA_VIRTUAL_OPERATOR_PHONE_LEGAL_REGEXP));
	}
}
