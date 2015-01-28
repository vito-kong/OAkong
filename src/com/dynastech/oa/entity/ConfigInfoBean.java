package com.dynastech.oa.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.dynastech.cdmetro.newui.entity.Entity;

public class ConfigInfoBean
extends Entity
{
  private int GetLocationFrequency;
  private ArrayList<LocationRules> GetLocationRules;
  private int SendLocationFrequency;
  private int SocketPort;

  public int getGetLocationFrequency()
  {
    return this.GetLocationFrequency;
  }

  public ArrayList<LocationRules> getGetLocationRules()
  {
    return this.GetLocationRules;
  }

  public int getSendLocationFrequency()
  {
    return this.SendLocationFrequency;
  }

  public int getSocketPort()
  {
    return this.SocketPort;
  }

  public void setGetLocationFrequency(int paramInt)
  {
    this.GetLocationFrequency = paramInt;
  }

  public void setGetLocationRules(ArrayList<LocationRules> paramArrayList)
  {
    this.GetLocationRules = paramArrayList;
  }

  public void setSendLocationFrequency(int paramInt)
  {
    this.SendLocationFrequency = paramInt;
  }

  public void setSocketPort(int paramInt)
  {
    this.SocketPort = paramInt;
  }
}

/* Location:           D:\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.dynastech.cdmetro.newui.entity.ConfigInfoBean
 * JD-Core Version:    0.6.0
 */