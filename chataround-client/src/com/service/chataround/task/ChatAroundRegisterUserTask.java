package com.service.chataround.task;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.service.chataround.ChatAroundSettingActivity;
import com.service.chataround.R;
import com.service.chataround.dto.register.RegisterUserRequestDto;
import com.service.chataround.util.ChatAroundHttpClient;

public class ChatAroundRegisterUserTask extends AsyncTask<Object, Integer, RegisterUserRequestDto> {
	public static String TAG = ChatAroundRegisterUserTask.class.getName();
	protected final Context mContext;
	protected final Fragment fragment;
	private ProgressDialog dialog;
	
	public ChatAroundRegisterUserTask(Context ctx, Fragment fragment) {
		this.mContext = ctx;
		this.fragment = fragment;
		dialog = new ProgressDialog(mContext);
	}

	@Override
	protected void onPreExecute() {
		this.dialog.setMessage(mContext.getString(R.string.progress_register_message));
        this.dialog.show();
	}

	@Override
	protected RegisterUserRequestDto doInBackground(Object... params) {
		RegisterUserRequestDto result = null;
		try {
			if (params[0] instanceof RegisterUserRequestDto) {
				RegisterUserRequestDto dto = (RegisterUserRequestDto) params[0];
					String url2call = (String) params[1];
					result = ChatAroundHttpClient.postSpringData(url2call, RegisterUserRequestDto.class, dto);
			}
		} catch (Exception e) {
			Log.e("validateUser error.loading.datafromserver",
					"Error loading data from server", e);
		}
		return result;
	}

	@Override
	protected void onPostExecute(RegisterUserRequestDto result) {
		if (dialog.isShowing()) {
            dialog.dismiss();
        }
		ChatAroundSettingActivity frag = (ChatAroundSettingActivity)mContext;
			frag.finishTaskRegisterUser(result);			
	}

}