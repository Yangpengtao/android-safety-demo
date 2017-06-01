package com.example.spannablesdemo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	private TextView mTv;
	private String str="ÎÒµÄˆDÆ¬£º";
	private final String SMILE="[smile]";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv=(TextView) findViewById(R.id.textView);
        Drawable drawable=getResources().getDrawable(R.drawable.ic_launcher);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        
        SpannableString spannable=new SpannableString(str+SMILE);
        
        ImageSpan span=new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
        
        spannable.setSpan(span, str.length(),(str+ SMILE).length(),
        		Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        mTv.setText(spannable);
        h(0, str.length());
    }
    
    
    public void h(int start ,int end){
    	SpannableStringBuilder spBuilder=new SpannableStringBuilder(str);
    	
    	ForegroundColorSpan span=new ForegroundColorSpan(Color.RED);
    	
    	spBuilder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    	mTv.setText(spBuilder);
    }

}
