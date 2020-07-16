package cn.vastsky.onlineshop.installment.contract;


import java.util.List;

import cn.vastsky.onlineshop.installment.base.impl.IBasePresenter;
import cn.vastsky.onlineshop.installment.base.impl.IBaseView;
import cn.vastsky.onlineshop.installment.model.bean.request.ContractRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.LoanDetailRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.ContractResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.LoanDetailInfoResponse;

/**
 * Created by airal on 2019/1/12.
 */

public interface LoanDetailContract {


    interface LoanDetailContractView extends IBaseView {
        void showLoanDetailView(LoanDetailInfoResponse response);

        void getContract(List<ContractResponse.ContractsBean> response);
    }

    interface LoanDetailContractPresenter extends IBasePresenter<LoanDetailContractView> {
        void getLoanDetailInfo(LoanDetailRequest request);

        void getContractInfo(ContractRequest request);
    }
}
