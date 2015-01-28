package com.dynastech.cdmetro.newui.common;

import java.io.File;

public class Constant {

	public static final String _ID = "_id";
	public static final String _IMAGE_URI = "_image_uri";
	public static final String _IMAGE_SAVE = "_image_save";
	public static final String _LOGOUT = "_logout";
	public static final String _Thumbnails_ID = "Thumbnails_id";
	public static final String _Parent = "_parent";
	public static final String _Category = "_category";
	public static final String _Title = "_title";
	public static final String _Online_Url = "_online_url";
	public static final String _Local_Url = "_local_url";
	public static final String _FormType = "_formtype";
	public static final String _Attach = "Attachments";
	public static final String _Upload = "Uploads";
	public static final String _OfficialDoc = "OfficialDoc";
	public static final String _ActPoint_Data = "ActPoint.Data";
	public static final String _Todo = "Todo";
	public static final String _Did = "Did";
	public static final String _TAKE_PHOTO_PATH = "TAKE_PHOTO_PATH";
	public static final String _TAKE_PHOTO_DATA = "TAKE_PHOTO_DATA";
	public static final String _GET_PHOTO_PATH = "GET_PHOTO_PATH";
	public static final String _TAKE_VIDEO_PATH = "TAKE_VIDEO_PATH";
	public static final String _Certain_Reject = "你确定回退吗";
	public static final String _Certain_Terminal = "你确定中止吗";
	public static final String _Certain_Revocation = "你确定撤销吗";
	public static final String _Branch = "Branch";
	public static final String _Branch_Activities = "Branch_Activities";
	public static final String _Branch_Count = "Branch_Count";
	public static final String _ApprovalBy = "ApprovalBy";
	public static final String _ApprovalBy_USERS = "ApprovalBy_USERS";
	public static final String _WorkflowId = "WorkflowId";
	public static final String _Position = "Position";
	public static final String _Select_Users = "Select_Users";
	public static final String _Wps = "Wps";
	public static final String _Audio = "Audio";
	public static final String _Video = "Video";
	public static final String _Image = "Image";
	public static final String _IsVisible = "IsVisible";
	public static String _AttachFragment_Path;
	public static String _UPLOAD_Attach_Path;
	// public static String _UPLOAD_Attach_Path_Pictures;
	public static String _UPLOAD_Attach_Path_Images;
	public static String _UPLOAD_Attach_Path_Audios;
	public static String _UPLOAD_Attach_Path_Videos;
	public static final String _USER_PWD_ERROR = "_USER_PWD_ERROR";

	public static final String _MobileHandler = "/MobileService/MobileHandler.ashx";
	public static final String _SERVICE_SUBBASE = "/APService";
	public static final String _GETBACK_URL = "/APService/api/rest?method=Platform.Service.ActPointHandler.TaskTackBackIsEnable";
	public static final String _GETBACK_URL_COMMIT = "Service/api/rest?method=Platform.Service.ActPointHandler.TaskTackBack";
	public static final String _LOGIN_URL = _MobileHandler + "?action=Login";
	public static final String _HOME_URL = _MobileHandler
			+ "?action=GetHomeData";
	public static final String _Attach_URL = _SERVICE_SUBBASE
			+ "/ActPointHandler.ashx?action=GetTaskItemByType";
	public static final String _Approval_URL = _SERVICE_SUBBASE
			+ "/ActPointHandler.ashx?action=GetHistoryList";
	public static final String _GetNextApprover = _SERVICE_SUBBASE
			+ "/ActPointHandler.ashx?action=GetNextApproverInfo";
	public static final String _BranchTask = _SERVICE_SUBBASE
			+ "/ActPointHandler.ashx?action=GetNextActivity";
	public static final String _BrachCharacter = _SERVICE_SUBBASE
			+ "/ActPointHandler.ashx?action=GetApprovers";
	public static final String _BranchTask_Submit = _SERVICE_SUBBASE
			+ "/ActPointHandler.ashx?action=UpdateNextActivity";
	public static final String _Attach_Delete = "?action=DeleteWorkItemAttachment";
	public static final String _Rule = _SERVICE_SUBBASE
			+ "/ActPointHandler.ashx?action=RuleCheckByTaskCommit";
	public static final String _CATEGORY_URL = _MobileHandler
			+ "?action=GetListData";
	public static final String _READ_URL = _MobileHandler
			+ "?action=GetDocumentById";
	public static final String _ATTACH_GET_URL = "?action=GetWorkItemAttachments";
	public static final String _PERMISSION_URL = "/ApService/api/rest?method=Platform.Service.ActPointHandler.GetWorkflowPermissionList";
	public static final String _ISFirstNode = _SERVICE_SUBBASE
			+ "/ActPointHandler.ashx?action=IsFirstNode";
	public static String email_from_address = "dynastechbug@163.com";
	public static String email_from_pwd = "qiaoyang123";
	public static String email_to_address = "qy86615069@163.com";
	public static String email_host = "smtp.163.com";
	public static String email_port = "25";
}
