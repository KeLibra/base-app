package cn.vastsky.onlineshop.installment.model.bean.response;

import java.util.List;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 *
 */

public class ContractResponse extends BaseBean {

    public List<ContractsBean> contracts;


    public static class ContractsBean {
        /**
         * contractUrl : http://www.baidu.com
         * contractName : sds
         */

        public String contractUrl;
        public String contractName;
    }
}
