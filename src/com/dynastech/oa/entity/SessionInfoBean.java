package com.dynastech.oa.entity;

import java.io.Serializable;

import com.dynastech.cdmetro.newui.entity.Entity;

public class SessionInfoBean
extends Entity
{
  private static final long serialVersionUID = 1L;
  private String ActiveTime;
  private String CreateTime;
  private String SessionCode;

  public String getActiveTime()
  {
    return this.ActiveTime;
  }

  public String getCreateTime()
  {
    return this.CreateTime;
  }

  public String getSessionCode()
  {
    return this.SessionCode;
  }

  public void setActiveTime(String paramString)
  {
    this.ActiveTime = paramString;
  }

  public void setCreateTime(String paramString)
  {
    this.CreateTime = paramString;
  }

  public void setSessionCode(String paramString)
  {
    this.SessionCode = paramString;
  }
}

/* Location:           D:\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.dynastech.cdmetro.newui.entity.SessionInfoBean
 * JD-Core Version:    0.6.0
 */