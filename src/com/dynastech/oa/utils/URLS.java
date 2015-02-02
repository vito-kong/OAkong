package com.dynastech.oa.utils;

import java.io.Serializable;

public class URLS implements Serializable {

	public static String HOST = "oa.dynastech.com";// oa.dongu.net 192.168.1.213
	public static int PORT = 8600;// 8600

	// www.oschina.net
	// public final
	// static String
	// HOST =
	// "oa.cdivtc.com";//192.168.1.213
	// www.oschina.net
	public final static String HTTP = "http://";
	public final static String DOMAIN = "dynastech.com";// dongu.net
	// public final static String DOMAIN = "cdivtc.com";
	public final static String HTTPS = "https://";

	// public final static String PORT="88";
	private final static String URL_SPLITTER = "/";
	private final static String URL_SPLIT = ":";
	public final static String LOGIN_VALIDATE_HTTP = HTTP + HOST + URL_SPLIT
			+ PORT + URL_SPLITTER
			+ "MobileService/MobileHandler.ashx?Action=Login";

	public final static String LocationService = "http://dgmobile.oicp.net:88/handlers/main.ashx";// http://oa.dynastech.com:8015/handlers/main.ashx
	public final static String LocationLogin = LocationService
			+ "?action=Login";
	public final static String LocationSend = LocationService
			+ "?action=SendContent";

	public final static String EXIT_HTTP = HTTP + HOST + URL_SPLIT + PORT
			+ URL_SPLITTER + "MobileService/MobileHandler.ashx?Action=Logoff";
	public final static String HOME_HTTP = HTTP + HOST + URL_SPLIT + PORT
			+ URL_SPLITTER
			+ "MobileService/MobileHandler.ashx?Action=GetHomeData";
	public final static String CATEGORY_HTTP = HTTP + HOST + URL_SPLIT + PORT
			+ URL_SPLITTER
			+ "MobileService/MobileHandler.ashx?Action=GetListData";
	public final static String CATEGORY_Detail_HTTP = HTTP + HOST + URL_SPLIT
			+ PORT + URL_SPLITTER
			+ "MobileService/ActPointHandler.ashx?Action=GetTaskItemByType";
	public static String FORM_HTTP = "http://oa.dynastech.com:8600";// WORD,POINT,EXCEL
	public static String FORM_INFOPATH = FORM_HTTP
			+ "/_layouts/oa/controls/InfoPath_Viewer.aspx?url=" + FORM_HTTP;// INFOPATH,OFFICLDOC
	public static String FilePath = "http://oa.dynastech.com:8601";
	public static String ATTACHMENT_ONLINEWATCH = FilePath + "/Files";
	public static String ATTACHMENT_DOWNLOAD = FilePath + "/Files";
	// public final static String FORM_HTTP="http://oa.cdivtc.com:8002";
	public static final String UPDATE_VERSION = "http://dsy189.h64.f5w.net/GetVersion.ashx";
	// public static String
	// ROOR_URL="http://demo2014.dynastech.com:8090/Platform/Api/uum/departments/root";
	public static String CONTACT_BASE_URL = "http://demo2014.dynastech.com:8090/Platform/Api/uum/departments";
	public static String CONTACT_BASE_URL_ROOT = "http://demo2014.dynastech.com:8090/Platform/Api/uum/departments/root";
	public static String DOCUMENT_URL = "http://mobile.duanhai.cn/Api/DemoDocLib";

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

}
