package com.dynastech.oa.ui.entity;

import java.io.Serializable;

public class LoginBean
extends Entity
{
  private ConfigInfoBean ConfigInfo;
  private SessionInfoBean SessionInfo;
  private UserInfoBean UserInfo;
  private String Version;

  public ConfigInfoBean getConfigInfo()
  {
    return this.ConfigInfo;
  }

  public SessionInfoBean getSessionInfo()
  {
    return this.SessionInfo;
  }

  public UserInfoBean getUserInfo()
  {
    return this.UserInfo;
  }

  public String getVersion()
  {
    return this.Version;
  }

  public void setConfigInfo(ConfigInfoBean paramConfigInfoBean)
  {
    this.ConfigInfo = paramConfigInfoBean;
  }

  public void setSessionInfo(SessionInfoBean paramSessionInfoBean)
  {
    this.SessionInfo = paramSessionInfoBean;
  }

  public void setUserInfo(UserInfoBean paramUserInfoBean)
  {
    this.UserInfo = paramUserInfoBean;
  }

  public void setVersion(String paramString)
  {
    this.Version = paramString;
  }
}

/* Location:           D:\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.dynastech.cdmetro.newui.entity.LoginBean
 * JD-Core Version:    0.6.0
 */