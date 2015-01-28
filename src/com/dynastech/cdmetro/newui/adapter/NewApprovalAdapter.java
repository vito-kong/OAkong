package com.dynastech.cdmetro.newui.adapter;

import io.vov.vitamio.demo.VideoViewDemo;

import java.io.File;
import java.util.LinkedList;

import com.dynastech.oa.R;
import com.dynastech.cdmetro.beans.ApprovalBean;
import com.dynastech.cdmetro.beans.AttachmentsBean;
import com.dynastech.cdmetro.newui.ImageCapture;
import com.dynastech.cdmetro.newui.ImageCapture2;
import com.dynastech.cdmetro.newui.OnLineWatch;
import com.dynastech.cdmetro.newui.OnlineImage;
import com.dynastech.cdmetro.newui.common.Constant;
import com.dynastech.cdmetro.newui.entity.CommentReplyBean;
import com.dynastech.cdmetro.newui.util.ToolUtils;
import com.dynastech.cdmetro.utils.URLS;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewApprovalAdapter extends BaseAdapter {

	private Context ctx;
	private LinkedList<ApprovalBean> ApprovalBeans;
	public static final String TITLE_MODE = "0";
	private String tag = "NewApprovalAdapter";

	public NewApprovalAdapter(Context context) {
		// TODO Auto-generated constructor stub
		ctx = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ApprovalBeans == null ? 0 : ApprovalBeans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ApprovalBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(ctx).inflate(
					R.layout.new_approval_item, null);
			holder.approval_title = (TextView) convertView
					.findViewById(R.id.approval_title);
			holder.approval_time = (TextView) convertView
					.findViewById(R.id.approval_time);
			holder.approval_name = (TextView) convertView
					.findViewById(R.id.approval_name);
			holder.approval_state = (TextView) convertView
					.findViewById(R.id.approval_state);
			holder.approval_content = (TextView) convertView
					.findViewById(R.id.approval_content);
			holder.approval_attachment_list = (LinearLayout) convertView
					.findViewById(R.id.approval_attachment_list);
			holder.approval_view_content = (LinearLayout) convertView
					.findViewById(R.id.approval_view_content);
			holder.approval_reply_list = (LinearLayout) convertView
					.findViewById(R.id.approval_reply_list);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String _id = ApprovalBeans.get(position).getID();
		String _title = ApprovalBeans.get(position).getTitle();
		String _time = ApprovalBeans.get(position).getStartDate();
		String _user_info = ApprovalBeans.get(position).getUserInfo();
		String _result = ApprovalBeans.get(position).getResult();
		String _comment = ApprovalBeans.get(position).getComment();
		LinkedList<AttachmentsBean> _attachments = ApprovalBeans.get(position)
				.getAttachments();
		LinkedList<CommentReplyBean> _replys = ApprovalBeans.get(position)
				.getCommentReply();
		;
		if (TITLE_MODE.equalsIgnoreCase(_id)) {// 只有标题
			holder.approval_title.setVisibility(View.VISIBLE);
			holder.approval_title.setText(_title);
			holder.approval_view_content.setVisibility(View.GONE);
		} else {
			holder.approval_title.setVisibility(View.GONE);
			holder.approval_view_content.setVisibility(View.VISIBLE);
			holder.approval_time.setText(_time);
			holder.approval_name.setText(_user_info);
			holder.approval_state.setText(_result);
			holder.approval_content.setText(_comment);
			if (_attachments != null && _attachments.size() != 0) {// 有附件
				holder.approval_attachment_list.setVisibility(View.VISIBLE);
				holder.approval_attachment_list.removeAllViews();
				for (final AttachmentsBean att : _attachments) {
					LinearLayout l = (LinearLayout) LayoutInflater.from(ctx)
							.inflate(R.layout.approval_attachment, null);
					l.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							String extension = att.getSuffix();
							String path = null;

							if (ToolUtils.isAudioAllowRead(extension)) {
								//
								path = Constant._UPLOAD_Attach_Path_Audios
										+ att.getFilename();
								File file = new File(path);
								if (!file.exists()) {
									ctx.startActivity(new Intent(ctx,
											VideoViewDemo.class).putExtra(
											"path",
											URLS.ATTACHMENT_DOWNLOAD
													+ att.getUrl()).putExtra(
											"audio_name", att.getFilename()));
								} else {
									ctx.startActivity(new Intent(ctx,
											VideoViewDemo.class).putExtra(
											"path", path).putExtra(
											"audio_name", att.getFilename()));
								}
							} else if (ToolUtils.isVideoAllowRead(extension)) {
								path = Constant._UPLOAD_Attach_Path_Videos
										+ att.getFilename();
								File file = new File(path);

								if (!file.exists()) {
									ctx.startActivity(new Intent(ctx,
											VideoViewDemo.class).putExtra(
											"path",
											URLS.ATTACHMENT_DOWNLOAD
													+ att.getUrl()).putExtra(
											"audio_name", att.getFilename()));
								} else {
									ctx.startActivity(new Intent(ctx,
											VideoViewDemo.class).putExtra(
											"path", path).putExtra(
											"audio_name", att.getFilename()));
								}
							} else if (ToolUtils.isImageAllowRead(extension)) {

								path = Constant._UPLOAD_Attach_Path_Images
										+ att.getFilename();
								File file = new File(path);
								if (file.exists()) {

									ctx.startActivity(new Intent(ctx,
											ImageCapture.class)
											.putExtra(Constant._IMAGE_URI,
													Uri.fromFile(file))
											.putExtra(Constant._Title,
													att.getFilename())
											.putExtra(Constant._IsVisible,
													false)
											.putExtra(
													Constant._TAKE_PHOTO_PATH,
													path));

								} else {
						
									ctx.startActivity(new Intent(ctx,
											OnlineImage.class).putExtra(
											Constant._Online_Url,
											URLS.ATTACHMENT_DOWNLOAD
													+ att.getUrl()).putExtra(
											Constant._Title, att.getFilename()));
								}

							} else {
								Toast.makeText(ctx, "不支持打开该格式文件",
										Toast.LENGTH_SHORT).show();
							}

						}
					});

					TextView name = (TextView) l
							.findViewById(R.id.app_attachment_name);
					name.setText(att.getFilename() + "");
					TextView size = (TextView) l
							.findViewById(R.id.app_attachment_size);
					size.setText(att.getSize() + "KB");
					holder.approval_attachment_list.addView(l);

				
				}
			}
			
			if (_replys != null && _replys.size() != 0) {
				holder.approval_reply_list.setVisibility(View.VISIBLE);
				holder.approval_reply_list.removeAllViews();
				for ( CommentReplyBean mCommentReplyBean : _replys) {
					LinearLayout liner_reply = (LinearLayout) LayoutInflater.from(ctx)
							.inflate(R.layout.approval_reply, null);
					TextView reply_username = (TextView) liner_reply
							.findViewById(R.id.approval_reply_user);
					reply_username.setText(mCommentReplyBean.getAuthor() + "");
					TextView reply_time = (TextView) liner_reply
							.findViewById(R.id.approval_reply_time);
					reply_time.setText(mCommentReplyBean.getCreated());
					TextView reply_content = (TextView) liner_reply
							.findViewById(R.id.approval_reply_content);
					reply_content.setText(mCommentReplyBean.getComment());
					holder.approval_reply_list.addView(liner_reply);
				}
			}

		}

		return convertView;
	}

	public void setData(LinkedList<ApprovalBean> agson) {
		// TODO Auto-generated method stub
		ApprovalBeans = agson;
	}

	class ViewHolder {
		TextView approval_title, approval_time, approval_name, approval_state,
				approval_content;
		LinearLayout approval_attachment_list, approval_view_content,
				approval_reply_list;
	}

}
