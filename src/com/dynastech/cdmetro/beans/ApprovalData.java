package com.dynastech.cdmetro.beans;

import java.util.LinkedList;
import java.util.List;

import android.util.Pair;

public class ApprovalData {
	public static final String TAG = ApprovalData.class.getSimpleName();

	List<Pair<String, List<CommentBean>>> pairs = new LinkedList<Pair<String, List<CommentBean>>>();
	List<CommentBean> comments = new LinkedList<CommentBean>();
	List<Approval> approval = new LinkedList<Approval>();
	List<String> titles = new LinkedList<String>();

	public List<CommentBean> getFlattenedData() {
		List<CommentBean> res = new LinkedList<CommentBean>();

		for (int i = 0; i < comments.size(); i++) {
			res.addAll(getOneSection(i).second);
		}

		return res;
	}

	public Pair<String, List<CommentBean>> getOneSection(int index) {

		return new Pair<String, List<CommentBean>>(approval.get(index)
				.getTitle(), approval.get(index).getComments());
	}
}
