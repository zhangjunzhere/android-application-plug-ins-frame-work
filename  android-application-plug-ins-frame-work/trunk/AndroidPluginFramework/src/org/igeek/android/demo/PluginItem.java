package org.igeek.android.demo;


import org.igeek.android.pluginframework.R;
import org.igeek.android.pluginframework.beans.PluginFeatureMethod;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 作者 E-mail:hangxin1940@gmail.com
 * @version 创建时间：2011-12-16 上午11:39:35
 * 插件item视图
 */
public class PluginItem extends LinearLayout {
	private Context context;

	private TextView tvDes;
	private TextView tvSubTit;
	private ImageView ivLogo;
	private LinearLayout llFeatures;
	
	public PluginItem(Context context) {
		super(context);
		
		this.context=context;
		
		LayoutInflater layoutinflater = LayoutInflater.from(context);
		View view = layoutinflater.inflate(R.layout.plugin_item, this, true);
		
		ivLogo=(ImageView) view.findViewById(R.id.item_ivLogo);
		tvDes=(TextView) view.findViewById(R.id.item_tvDesc);
		tvSubTit=(TextView) view.findViewById(R.id.item_tvSubTitle);
		llFeatures=(LinearLayout) view.findViewById(R.id.item_lvFeatures);
	}
	
	public void setLogoImage(Drawable d){
		ivLogo.setImageDrawable(d);
	}
	
	public void setDes(String text){
		tvDes.setText(text);
	}
	
	public void setSubTit(String text){
		tvSubTit.setText(text);
	}
	
	public void addPluginMethod(PluginFeatureMethod method,OnClickListener ocl){
		Button btn=new Button(context);
		btn.setText(method.getDescription());
		btn.setOnClickListener(ocl);
		llFeatures.addView(btn);
	}

}
