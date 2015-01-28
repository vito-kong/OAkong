package com.dynastech.oa.entity;

import java.io.Serializable;

import com.dynastech.cdmetro.newui.entity.Entity;

public class LoginGson
extends Entity
{
  private LoginBean Data;
  private String Message;
  private boolean Success;

  public LoginBean getData()
  {
    return this.Data;
  }

  public String getMessage()
  {
    return this.Message;
  }

  public boolean isSuccess()
  {
    return this.Success;
  }

  public void setData(LoginBean paramLoginBean)
  {
    this.Data = paramLoginBean;
  }

  public void setMessage(String paramString)
  {
    this.Message = paramString;
  }

  public void setSuccess(boolean paramBoolean)
  {
    this.Success = paramBoolean;
  }
}

/* Location:           D:\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.dynastech.cdmetro.newui.entity.LoginGson
 * JD-Core Version:    0.6.0
 */