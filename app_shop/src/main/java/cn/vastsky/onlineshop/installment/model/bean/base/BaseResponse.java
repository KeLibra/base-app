package cn.vastsky.onlineshop.installment.model.bean.base;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author dingzhongsheng
 * @copyright blackfish
 * @date 2018/7/6.
 */
public class BaseResponse<T>  extends BaseBean {
//	public String errorCode;
//	public String msg;
//	public boolean success;
//	public String jwt;
//	public String token;
//	public String lmToken;

	public T data;
	public String code; // 状态码
	public String message; // 状态消息
}
