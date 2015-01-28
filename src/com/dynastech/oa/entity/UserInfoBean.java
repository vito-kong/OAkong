package com.dynastech.oa.entity;

import java.io.Serializable;

import com.dynastech.cdmetro.newui.entity.Entity;

public class UserInfoBean
extends Entity
{
  private String AvatarUrl;
  private String DisplayName;
  private String Gender;
  private int Id;
  private String LoginName;
  private int SortKey;
  private String State;
  private String UserType;

  public String getAvatarUrl()
  {
    return this.AvatarUrl;
  }

  public String getDisplayName()
  {
    return this.DisplayName;
  }

  public String getGender()
  {
    return this.Gender;
  }

  public int getId()
  {
    return this.Id;
  }

  public String getLoginName()
  {
    return this.LoginName;
  }

  public int getSortKey()
  {
    return this.SortKey;
  }

  public String getState()
  {
    return this.State;
  }

  public String getUserType()
  {
    return this.UserType;
  }

  public void setAvatarUrl(String paramString)
  {
    this.AvatarUrl = paramString;
  }

  public void setDisplayName(String paramString)
  {
    this.DisplayName = paramString;
  }

  public void setGender(String paramString)
  {
    this.Gender = paramString;
  }

  public void setId(int paramInt)
  {
    this.Id = paramInt;
  }

  public void setLoginName(String paramString)
  {
    this.LoginName = paramString;
  }

  public void setSortKey(int paramInt)
  {
    this.SortKey = paramInt;
  }

  public void setState(String paramString)
  {
    this.State = paramString;
  }

  public void setUserType(String paramString)
  {
    this.UserType = paramString;
  }
}

/* Location:           D:\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.dynastech.cdmetro.newui.entity.UserInfoBean
 * JD-Core Version:    0.6.0
 */