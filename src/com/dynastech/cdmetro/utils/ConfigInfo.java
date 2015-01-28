package com.dynastech.cdmetro.utils;

public class ConfigInfo {

	public static final String USER_PREFERENCES = "DONGGU_USER_PREFERENCES";
	public static final String PREF_USERNAME = "DONGGU_USERNAME";
	public static final String PREF_LOGINNAME = "DONGGU_LOGINNAME ";
	public static final String PREF_PASSWORD = "DONGGU_PASSWORD";
	public static final String PREF_SERVER_ADDRESS = "DONGGU_SERVER_ADDRESS";
	public static final String PREF_CATEGORY = "DONGGU_CATEGORY";
	public static final String DONGGU_SEARCHR_RECORD = "DONGGU_SEARCHR_RECORD";
	public static final String USER_ICON = "USER_ICON";
	public static final String UPLOAD_SERVER_ADDRESS = "UPLOAD_SERVER_ADDRESS";
	public static final String PREF_DISPLAYNAME = "PREF_DISPLAYNAME";
	public static final String TEXT_BODY = "TEXT_BODY";
	
	public static final String LOGIN_AUTO="LOGIN_AUTO";
	public static final String LOGIN_REMEMBER="LOGIN_REMEMBER";
	public static final class ATTACHMENT {
		public static final String FAILED = "FAILED";
	}

	public static final class UPLOADSTATE {

		public static final String IDLE = "上传等待";
		public static final String BEGIN = "上传开始";
		// public static final String RUNNING = "上传中";
		public static final String FAILED = "上传失败";
		public static final String CANCELED = "上传取消";
		public static final String FINISHED = "上传完成";
		// 上传列表action
		public static final String UPLOAD_ITEM_BEGIN = "UPLOAD_ITEM_BEGIN";
		public static final String UPLOAD_ITEM_FINISHED = "UPLOAD_ITEM_FINISHED";
		public static final String UPLOAD_ITEM_CANCEL = "UPLOAD_ITEM_CANCEL";
		public static final String UPLOAD_ITEM_DELETE = "UPLOAD_ITEM_DELETE";
		public static final String UPLOAD_ITEM_DELETE_FAILED = "UPLOAD_ITEM_DELETE_FAILED";
		public static final String UPLOAD_ITEM_FAILED = "UPLOAD_ITEM_FAILED";
		// 上传全部列表action
		public static final String UPLOADEDALL_ITEM_DELETE = "UPLOADEDALL_ITEM_DELETE";
		public static final String UPLOADEDALL_ITEM_DELETE_FAILED = "UPLOADEDALL_ITEM_DELETE_FAILED";

		public static final String FILE_TYPE_IMAGE = "FILE_TYPE_IMAGE";
		public static final String FILE_TYPE_VIDEO = "FILE_TYPE_VIDEO";
		public static final String FILE_TYPE_AUDIO = "FILE_TYPE_AUDIO";
	}

	public static final class DOWNLOADSTATE {

		public static final String IDLE = "下载等待";
		public static final String BEGIN = "下载开始";
		public static final String RUNNING = "下载进行中";
		public static final String PAUSE = "下载暂停";
		public static final String CANCEL = "下载取消";
		public static final String FAILED = "下载失败";
		public static final String FINISHED = "下载完成";

		// action
		public static final String DOWNLOAD_ITEM_BEGIN = "DOWNLOAD_ITEM_BEGIN";
		public static final String DOWNLOAD_ITEM_FINISHED = "DOWNLOAD_ITEM_FINISHED";
		public static final String DOWNLOAD_ITEM_CANCEL = "DOWNLOAD_ITEM_CANCEL";
		public static final String DOWNLOAD_ITEM_PAUSE = "DOWNLOAD_ITEM_PAUSE";
		public static final String DOWNLOAD_ITEM_FAILED = "DOWNLOAD_ITEM_FAILED";
		public static final String DOWNLOAD_ITEM_DELETE = "DOWNLOAD_ITEM_DELETE";
		public static final String DOWNLOAD_ITEM_EXIST = "DOWNLOAD_ITEM_EXIST";

		public static final int NUM_IDLE = 0;
		public static final int NUM_BEGIN = 1;
		public static final int NUM_PAUSE = 2;
		public static final int NUM_CANCEL = 3;
		public static final int NUM_FAILED = 4;
		public static final int NUM_FINISHED = 5;

		public static final int DOWNLOAD_MSG_BEGIN = 1;
		public static final int DOWNLOAD_MSG_PAUSE = 2;
		public static final int DOWNLOAD_MSG_CANCEL = 3;
		public static final int DOWNLOAD_MSG_FAILED = 4;
		public static final int DOWNLOAD_MSG_EXIST = 5;
	}

	public static final class HTTPRETURN {
		/** 网络访问数据返回出错的提示信息 **/
		public static final String HTTPERROR = "未获取 到数据！";
		public static final String HTTP_ERROR_401 = "用户名或密码错误！";
		public static final String COMMHTTPERRORS = "网络异常！";
		public static final String HTTP_ERROR_400 = "暂时没有消息!";
		// public static final String HTTP_ERROR_404="暂时没有新消息!";
		public static final String HTTP_ERROR_CODE_404 = "404";
		public static final String HTTP_ERROR_DOMAIN = "域名";

		public static final String HTTP_ERROR_MSG_DEL = "消息已经被删除!";
	}

	public static final class COMM {
		public static final String APY_KEY = "diipo_iphone_test";
		// public static final String
		// CONST_SERVER_ADDRESS="http://test1.diipo.cn";
		// public static final String
		// CONST_SERVER_ADDRESS="http://hb.diiposoft.com";
		public static final String CONST_SERVER_ADDRESS = "http://ty.diiposoft.com";

		public static final String FORM_REFRESH = "form_refresh";

		public static String MSG_ID = "id";
		public static String NOTIFY_ID = "notifyid";
		public static String ROOT_ID = "rootid";
		public static String USER_ID = "id";
		public static String USER_NAME = "name";
		public static String THUMBNAIL = "thumbnail";
		public static String PIC_URL = "pic_url";

		public static String WORKSPACE_TYPE = "workspace_type";
		public static String WORKSPACE_ID = "workspace_id";
		public static String WORKSPACE_NAME = "workspace_name";
		public static String FORM_ID = "form_id";
		public static String FILTER_ID = "filter_id";
		public static String FORM_NAME = "form_name";
		public static String FORM_WORK = "form_work";

		public static String FIELD_TYPE_NUMBER = "number";
		public static String FIELD_TYPE_MONEY = "money";
		public static String FIELD_TYPE_DATE = "date";
		public static String FIELD_TYPE_SELECT = "select";
		public static String FIELD_TYPE_AUTOID = "autoid";

		public static String FIELD_POSITION = "field_position";
		public static String FIELD_VALUE = "field_value";
		public static String FIELD_SELECTED = "field_selected";

		public static String FIELD_CURRENT_VALUE = "field_current_value";
		public static String FIELD_SELECT_OPTIONS = "field_select_options";

		public static String ACTION_FLAG = "flag";

		public static String HTML = "html";
		/** 1：消息，0：私信 **/
		public static String LETTER_TYPE = "0";
		public static String MSG_TYPE = "1";

		public static String DIRECT_EMAIL = "send_email";
		public static String SEND_LETTER = "send_letter";
		public static String DIRECT_MENTION = "mention";

		public static String SEND_TYPE = "send_type";
		// 1，消息，2，消息回复，3，私信，4，私信回复
		public static String WEIBO_MSG = "1";
		public static String WEIBO_MSG_REPLY = "2";
		public static String WEIBO_LETTER = "3";
		public static String WEIBO_LETTER_REPLY = "4";
		public static String WEIBO_MSG_REFER = "5";

		public static String FORM_ADD_CONTACT = "6";// 表单中添加联系人

		public static String DETAIL_ID = "detail_id"; // 指定要评论的的消息ID

		public static String PW_TYPE_REPLY = "1";
		public static String PW_TYPE_SHARE = "4";

		// content 结构中的type
		public static String CONTENT_TYPE_LINK = "0";
		public static String CONTENT_TYPE_PIC = "2";
		public static String CONTENT_TYPE_VEDIO = "3";
		public static String CONTENT_TYPE_PLAINTEXT = "8";
		public static String CONTENT_TYPE_RICH_HTML = "10";
		public static String CONTENT_TYPE_RICH_EMBEDDED = "11";
		public static String CONTENT_TYPE_RICH_FORM = "12";

		public static String APP_ID = "app_id";
		public static String APP_TITLE = "app_title";

		// app_type
		public static String APP_TYPE = "app_type"; //
		public static String APP_TYPE_APPROVE = "12330"; // 审批
		public static String APP_TYPE_MEETING = "12332";
		public static String APP_TYPE_EVENT = "12334";
		public static String APP_TYPE_TASK = "12335";

		public static String APP_TYPE_PARA_MEETING = "1"; // meeting
		public static String APP_TYPE_PARA_EVENT = "2"; // event
		public static String APP_TYPE_PARA_TASK = "3"; // task
		public static String APP_TYPE_PARA_APPROVE = "4"; // approve

		public static String APP_TASK_INIT = "0";// init
		public static String APP_TASK_DONE = "1";// done
		public static String APP_TASK_UNDONE = "2";// undone

		// meeting para
		public static String APP_MEETING_INIT = "0";// init
		public static String APP_MEETING_JOIN = "1";// join
		public static String APP_MEETING_UNJOIN = "2";// un join
		public static String APP_MEETING_UNKNOW = "3";// unknow

		// event para
		public static String APP_EVENT_INIT = "0";// init
		public static String APP_EVENT_JOIN = "1";// join
		public static String APP_EVENT_UNJOIN = "2";// un join
		public static String APP_EVENT_UNKNOW = "3";// unknow

		// approve para
		public static String APP_APPROVE_INIT = "0";// init
		public static String APP_APPROVE_PASS = "1";// pass
		public static String APP_APPROVE_UNPASS = "2";// unpass

		public static String ITEM_TYPE = "itemType";

		// when some reply happened in the WeiboDetailsActivity,the Home
		// Activity should get data again from the server
		public static boolean UPDATE_DATA = false;

		public static String LOGOUT = "logout";

	}

	public static boolean IS_LOGOUT = false;

	// the unread msg was review,so the MsgActivity UI should update here
	public static boolean IS_UN_READ_VIEW = false;

	// call in the onActivityResult() function.
	public static int CALLBACK_CREATE_WEIBO = 1;
	public static int CALLBACK_REPLY_WEIBO = 2;
	public static int CALLBACK_CREATE_LETTER = 3;
	public static int CALLBACK_REPLY_LETTER = 4;
	public static int CALLBACK_REFER = 5;
	public static int CALLBACK_FIELD_VALUE = 6;
	public static int CALLBACK_FORM_ADD_CONTACT = 7;
	public static int CALLBACK_FORM_SELECT = 8;
	public static int CALLBACK_CREATE_FROM = 9;
	public static int CALLBACK_FORM_CHANGE_WS = 10;
	public static int CALLBACK_FORM_EDIT = 11;
	public static int CALLBACK_MAP_FORM_EDIT = 12;
	public static int CALLBACK_SEEKBAR_FORM_EDIT = 13;

}
