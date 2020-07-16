package cn.vastsky.onlineshop.installment.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author dingzhongsheng
 * @copyright blackfish
 * @date 2018/7/12.
 */

public class SoftInputUtils {
	public static void showInput(EditText editText) {
		if(editText != null) {
			editText.requestFocus();
			editText.setSelection(editText.getText().length());
			InputMethodManager imm = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(editText, 2);
		}
	}

	public static void hideInput(Activity activity) {
		View mv = activity.getWindow().peekDecorView();
		if(mv != null) {
			InputMethodManager inputmanger = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(mv.getWindowToken(), 0);
		}

	}
}
