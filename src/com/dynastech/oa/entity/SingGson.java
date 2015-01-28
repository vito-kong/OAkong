package com.dynastech.oa.entity;

import com.dynastech.cdmetro.newui.entity.Entity;

public class SingGson extends Entity
{
  private LocationGson location;
  private int personId;
  private String text;

  public LocationGson getLocation()
  {
    return this.location;
  }

  public int getPersonId()
  {
    return this.personId;
  }

  public String getText()
  {
    return this.text;
  }

  public void setLocation(LocationGson paramLocationGson)
  {
    this.location = paramLocationGson;
  }

  public void setPersonId(int paramInt)
  {
    this.personId = paramInt;
  }

  public void setText(String paramString)
  {
    this.text = paramString;
  }
}

/* Location:           D:\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.dynastech.cdmetro.newui.entity.SingGson
 * JD-Core Version:    0.6.0
 */