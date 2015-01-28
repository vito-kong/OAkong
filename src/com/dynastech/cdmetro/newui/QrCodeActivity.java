package com.dynastech.cdmetro.newui;

import com.dynastech.oa.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class QrCodeActivity extends BaseActivity implements OnClickListener{
	
	private ImageView back;
	private TextView image_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcode);
		back = (ImageView) findViewById(R.id.new_back);
		image_title = (TextView) findViewById(R.id.new_title);
		image_title.setText("二维码");
		back.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==back){
			finish();
		}
		
	}
	
}
