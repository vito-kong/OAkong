package com.dynastech.oa.ui.entity;



public class ContactUser extends Entity
{
  private String Id;
  private boolean IsEnabled;
  private boolean IsHidden;
  private String MobilePhone;
  private String Name;
  private String ParentId;
  private String PrincipalName;
  private String UserName;
  private String displayname;

  public String getDisplayname()
  {
    return this.displayname;
  }

  public String getId()
  {
    return this.Id;
  }

  public String getMobilePhone()
  {
    return this.MobilePhone;
  }

  public String getName()
  {
    return this.Name;
  }

  public String getParentId()
  {
    return this.ParentId;
  }

  public String getPrincipalName()
  {
    return this.PrincipalName;
  }

  public String getUserName()
  {
    return this.UserName;
  }

  public boolean isIsEnabled()
  {
    return this.IsEnabled;
  }

  public boolean isIsHidden()
  {
    return this.IsHidden;
  }

  public void setDisplayname(String paramString)
  {
    this.displayname = paramString;
  }

  public void setId(String paramString)
  {
    this.Id = paramString;
  }

  public void setIsEnabled(boolean paramBoolean)
  {
    this.IsEnabled = paramBoolean;
  }

  public void setIsHidden(boolean paramBoolean)
  {
    this.IsHidden = paramBoolean;
  }

  public void setMobilePhone(String paramString)
  {
    this.MobilePhone = paramString;
  }

  public void setName(String paramString)
  {
    this.Name = paramString;
  }

  public void setParentId(String paramString)
  {
    this.ParentId = paramString;
  }

  public void setPrincipalName(String paramString)
  {
    this.PrincipalName = paramString;
  }

  public void setUserName(String paramString)
  {
    this.UserName = paramString;
  }
}
