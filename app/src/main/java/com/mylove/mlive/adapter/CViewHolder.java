package com.mylove.mlive.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylove.mlive.R;

public class CViewHolder extends RecyclerView.ViewHolder{

	private SparseArray<View> mViews;
	private View mConvertView;

	public View getmConvertView() {
		return mConvertView;
	}

	private CViewHolder(View itemView) {
		super(itemView);
		mViews = new SparseArray<View>();
		mConvertView = itemView;
	}

	public static CViewHolder get(Context context,View convertView,ViewGroup parent, int layoutId) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
		}
		CViewHolder viewHolder = new CViewHolder(convertView);
		return viewHolder;
	}

	public <T extends View> T getView(int viewId) {

		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public CViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	public CViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);
		return this;
	}

	public CViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}
}
