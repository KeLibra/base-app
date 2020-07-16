package cn.vastsky.onlineshop.installment.presenter;

import org.jetbrains.annotations.NotNull;

import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.onlineshop.installment.base.BasePresenter;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.contract.LoanDetailContract;
import cn.vastsky.onlineshop.installment.model.bean.request.ContractRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.LoanDetailRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.ContractResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.LoanDetailInfoResponse;
import cn.vastsky.onlineshop.installment.service.LoanModel;

/**
 * @author: kezy
 * @create_time 2019/11/11
 * @description:
 */
public class LoanDetailPresenter
        extends BasePresenter<LoanDetailContract.LoanDetailContractView>
        implements LoanDetailContract.LoanDetailContractPresenter {

    public LoanDetailPresenter(@NotNull LoanDetailContract.LoanDetailContractView view) {
        super(view);
    }

    @Override
    public void getLoanDetailInfo(LoanDetailRequest request) {
        getView().showLoading("");
        LoanModel.getLoanDetailInfo(request, new ICallBack<LoanDetailInfoResponse>() {
            @Override
            public void onSuccess(LoanDetailInfoResponse infoResponse) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    getView().hideEmptyPage();
                    if (infoResponse != null) {
                        getView().showLoanDetailView(infoResponse);
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                getView().hideLoading();
                getView().showEmptyPage(-1);
                ToastUtil.show(err_msg);
            }
        });
    }

    @Override
    public void getContractInfo(ContractRequest request) {
        LoanModel.getContractInfo(request, new ICallBack<ContractResponse>() {
            @Override
            public void onSuccess(ContractResponse response) {
                if (isViewAttached()) {
                    if (response != null && response.contracts != null) {
                        getView().getContract(response.contracts);
                    } else {
                        getView().getContract(null);
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                getView().getContract(null);
            }
        });
    }
}
