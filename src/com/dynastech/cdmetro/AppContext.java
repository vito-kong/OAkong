package com.dynastech.cdmetro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.dynastech.cdmetro.newui.api.ApiClient;
import com.dynastech.cdmetro.newui.entity.Attence;
import com.dynastech.cdmetro.newui.entity.CategoryData;
import com.dynastech.cdmetro.newui.entity.Downloader;
import com.dynastech.cdmetro.newui.entity.HomeData;
import com.dynastech.cdmetro.newui.entity.User;
import com.dynastech.cdmetro.newui.entity.UserInfo;
import com.dynastech.cdmetro.newui.util.AsyncImageLoader;
import com.dynastech.cdmetro.newui.util.CyptoUtils;
import com.dynastech.cdmetro.newui.util.JsonUtils;
import com.dynastech.cdmetro.newui.util.StringUtils;
import com.dynastech.cdmetro.utils.HttpUtils;
import com.dynastech.cdmetro.utils.MD5StringUtil;
import com.dynastech.cdmetro.utils.URLS;
import com.dynastech.oa.entity.ContactData;
import com.dynastech.oa.entity.ContactFolder;
import com.dynastech.oa.entity.ContactUser;
import com.dynastech.oa.entity.DocumentsData;
import com.dynastech.oa.entity.Metadata;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class AppContext extends Application {

	private Map<String, Downloader> downloaders = new HashMap<String, Downloader>();
	private boolean login = false; // 登录状态
	public static final int PAGE_SIZE = 20;// 默认分页大小
	private String tag = "AppContext";
	private String saveImagePath;// 保存图片路径
	private AsyncImageLoader asyncImageLoader;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 注册App异常崩溃处理器
		Thread.setDefaultUncaughtExceptionHandler(AppException
				.getAppExceptionHandler());
		init();

	}

	/**
	 * 获取内存中保存图片的路径
	 * 
	 * @return
	 */
	public String getSaveImagePath() {
		return saveImagePath;
	}

	private void init() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(1500000).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.enableLogging() // Not necessary in common
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		saveImagePath = AppConfig.DEFAULT_SAVE_IMAGE_PATH;
		asyncImageLoader = new AsyncImageLoader();
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 从config配置文件里获取user
	 * @return
	 */
	public User initLoginInfo() {
		User loginUser = getLoginInfo();
		return loginUser;
	}

	/**
	 * 用户注销
	 */
	public void Logout() {
		this.login = false;

	}

	/**
	 * 清除保存的缓存
	 */
	public void cleanCookie() {
		removeProperty(AppConfig.CONF_COOKIE);
	}

	public void removeProperty(String... key) {
		AppConfig.getAppConfig(this).remove(key);
	}

	private User getLoginInfo() {
		// TODO Auto-generated method stub
		User lu = new User();
		UserInfo userinfo = new UserInfo();
		userinfo.setDisplayName(getProperty("UserInfo.DisplayName"));
		userinfo.setLoginName(getProperty("UserInfo.LoginName"));
		userinfo.setAvatarUrl(getProperty("UserInfo.AvatarUrl"));
		userinfo.setEmail(getProperty("UserInfo.Email"));
		lu.setUserInfo(userinfo);

		Attence attence = new Attence();
		attence.setLocationRepeatInterval(getProperty("Attence.LocationRepeatInterval"));
		attence.setLocationRepeatCount(StringUtils
				.toInt(getProperty("Attence.LocationRepeatCount")));
		attence.setLocationRepeatStart(StringUtils
				.toInt(getProperty("Attence.LocationRepeatStart")));
		attence.setLocationRepeatEnd(StringUtils
				.toInt(getProperty("Attence.LocationRepeatEnd")));
		lu.setAttence(attence);

		lu.setAutoLogin(StringUtils.toBool(getProperty("user.isAutoLogin")));
		lu.setRememberMe(StringUtils.toBool(getProperty("user.isRemberMe")));
		lu.setFileServerHandlerUrl(getProperty("user.FileServerHandlerUrl"));
		lu.setAddress(getProperty("user.address"));

		lu.setUsername(getProperty("user.username"));
		lu.setPwd(CyptoUtils.decode("cdmetropwd", getProperty("user.pwd")));

		return lu;
	}

	/**
	 * 保存登录信息
	 * 
	 * @param username
	 * @param pwd
	 */
	public void saveLoginInfo(final User user) {

		this.login = true;
		setProperties(new Properties() {
			{

				setProperty("UserInfo.DisplayName", user.getUserInfo()
						.getDisplayName() + "");
				setProperty("UserInfo.LoginName", user.getUserInfo()
						.getLoginName() + "");
				setProperty("UserInfo.AvatarUrl", user.getUserInfo()
						.getAvatarUrl() + "");
				setProperty("UserInfo.Email", user.getUserInfo().getEmail()
						+ "");

				setProperty("Attence.LocationRepeatInterval", user.getAttence()
						.getLocationRepeatInterval());
				setProperty("Attence.LocationRepeatCount", user.getAttence()
						.getLocationRepeatCount() + "");
				setProperty("Attence.LocationRepeatStart", user.getAttence()
						.getLocationRepeatStart() + "");
				setProperty("Attence.LocationRepeatEnd", user.getAttence()
						.getLocationRepeatEnd() + "");

				setProperty("user.isAutoLogin", user.isAutoLogin() + "");
				setProperty("user.isRemberMe", user.isRememberMe() + "");
				setProperty("user.FileServerHandlerUrl",
						user.getFileServerHandlerUrl() + "");
				setProperty("user.address", user.getAddress() + "");
				setProperty("user.username", user.getUsername() + "");
				setProperty("user.pwd",
						CyptoUtils.encode("cdmetropwd", user.getPwd()) + "");
			}
		});
	}

	public void setProperties(Properties ps) {
		AppConfig.getAppConfig(this).set(ps);
	}

	public String getProperty(String key) {
		return AppConfig.getAppConfig(this).get(key);
	}

	/**
	 * 用户登录验证
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 * @throws AppException
	 */
	public User loginVerify(String url, String account, String pwd,
			String Resolution, String MacId, String OSInfo, String Platform)
			throws AppException {
		return ApiClient.login(this, url, account, pwd, Resolution, MacId,
				OSInfo, Platform);
	}

	// 主页数据
	public HomeData _getHomeData(String url, String account, String pwd)
			throws AppException {
		return ApiClient._getHomeData(url, account, pwd);
	}

	// 列表数据
	public CategoryData _getCategoryData(User user, String _category,
			String _parent, int pageIndex, boolean isRequestNet, String domain)
			throws AppException {

		CategoryData data = null;
		String key = user.getUsername() + "_category_data_" + _parent + "_"
				+ pageIndex + "_" + PAGE_SIZE;
		key = MD5StringUtil.md5StringFor(key);
		boolean hasCache = isReadDataCache(key);
		if (isNetworkConnected() && (!hasCache || isRequestNet)) {
			// 网络请求数据
			try {
				data = ApiClient._getCategoryData(user, _category, _parent,
						pageIndex, PAGE_SIZE, domain);
				if (data != null) {
					data.setCacheKey(key);
					saveObject(data, key);
				}
			} catch (AppException e) {
				data = (CategoryData) readObject(key);
				if (data == null)
					throw e;
			}

		} else {
			// 缓存获取数据
			data = (CategoryData) readObject(key);
			if (data == null)
				data = CategoryData.get_CategoryData();
		}

		return data;

	}

	/**
	 * 保存对象
	 * 
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public boolean saveObject(Serializable ser, String file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 判断缓存数据是否可读
	 * 
	 * @param cachefile
	 * @return
	 */
	private boolean isReadDataCache(String cachefile) {
		return readObject(cachefile) != null;
	}

	/**
	 * 读取对象
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String file) {
		if (!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable) ois.readObject();

		} catch (Exception e) {
			e.printStackTrace();
			// 反序列化失败 - 删除缓存文件
			if (e instanceof InvalidClassException) {
				File data = getFileStreamPath(file);
				data.delete();
			}
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
			}
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 判断缓存是否存在
	 * 
	 * @param cachefile
	 * @return
	 */
	private boolean isExistDataCache(String cachefile) {
		boolean exist = false;
		File data = getFileStreamPath(cachefile);
		if (data.exists())
			exist = true;
		return exist;
	}

	public Map<String, Downloader> getDownloaders() {
		return downloaders;
	}

	public void setDownloaders(Map<String, Downloader> downloaders) {
		this.downloaders = downloaders;
	}

	public AsyncImageLoader getAsyncImageLoader() {
		return asyncImageLoader;
	}

	public void setAsyncImageLoader(AsyncImageLoader asyncImageLoader) {
		this.asyncImageLoader = asyncImageLoader;
	}

	public ContactData _getContactData(String type, String id, String username,
			String pwd) throws Exception {

		ContactData localContactData = new ContactData();
		LinkedList<Object> localLinkedList = new LinkedList<Object>();
		if ("root".equals(type)) {
			String root_json = HttpUtils._get_basic(URLS.CONTACT_BASE_URL_ROOT,
					username, pwd);
			Log.i(tag, "root_json:" + root_json);
			if (root_json != null && !"".equals(root_json)
					&& !"[]".equals(root_json)) {
				localLinkedList.addAll(JsonUtils.parseListsFromJsons(root_json,
						new TypeToken<LinkedList<ContactFolder>>() {
						}.getType()));
			}
		} else {
			String departs_json = HttpUtils._get_basic(URLS.CONTACT_BASE_URL
					+ File.separator + URLEncoder.encode(id, "utf-8")
					+ File.separator + "departments", username, pwd);
			Log.i(tag, "departs_json:" + departs_json);
			if (departs_json != null && !"".equals(departs_json)
					&& !"[]".equals(departs_json)) {
				localLinkedList.addAll(JsonUtils.parseListsFromJsons(
						departs_json,
						new TypeToken<LinkedList<ContactFolder>>() {
						}.getType()));
			}
			String users_json = HttpUtils._get_basic(URLS.CONTACT_BASE_URL
					+ File.separator + URLEncoder.encode(id, "utf-8")
					+ File.separator + "users", username, pwd);
			Log.i(tag, "users_json:" + users_json);
			if (users_json != null && !"".equals(users_json)
					&& !"[]".equals(users_json)) {
				localLinkedList.addAll(JsonUtils.parseListsFromJsons(
						users_json, new TypeToken<LinkedList<ContactUser>>() {
						}.getType()));
			}

		}
		localContactData.setContact_Depart_Users(localLinkedList);
		return localContactData;

	}

	public LinkedList<DocumentsData> _getDocumentData(String paramString1,
			String paramString2) throws Exception {
		LinkedList<DocumentsData> localLinkedList = new LinkedList<DocumentsData>();
		String str = HttpUtils._get_basic(URLS.DOCUMENT_URL,
				paramString1, paramString2);
		
		if (str != null && !"".equals(str) && !"[]".equals(str)) {
			JSONArray localJSONArray = new JSONObject(str).getJSONObject("d")
					.getJSONArray("results");
			for (int i = 0; i < localJSONArray.length(); i++) {
				JSONObject localJSONObject1 = (JSONObject) localJSONArray
						.opt(i);
				JSONObject localJSONObject2 = (JSONObject) localJSONObject1
						.get("__metadata");
				Metadata localMetadata = new Metadata();
				localMetadata.setUri(localJSONObject2.getString("uri"));
				localMetadata.setEtag(localJSONObject2.getString("etag"));
				localMetadata.setType(localJSONObject2.getString("type"));
				localMetadata.setEdit_media(localJSONObject2
						.getString("edit_media"));
				localMetadata.setMedia_src(localJSONObject2
						.getString("media_src"));
				localMetadata.setContent_type(localJSONObject2
						.getString("content_type"));
				localMetadata.setMedia_etag(localJSONObject2
						.getString("media_etag"));
				DocumentsData localDocumentsData = new DocumentsData();
				localDocumentsData.setmMetadata(localMetadata);
				localDocumentsData.setModify_time(localJSONObject1
						.getString("修改时间"));
				localDocumentsData
						.setFilename(localJSONObject1.getString("名称"));
				localLinkedList.add(localDocumentsData);
			}
			return localLinkedList;
		} else {
			Toast.makeText(this, "数据获取失败", 0).show();
		}
		return null;

	}
}
