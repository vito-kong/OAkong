package com.dynastech.oa.ui.entity;

import java.io.Serializable;

public class LocationRules
extends Entity
{
  private static final long serialVersionUID = 1L;
  private int[] hours;
  private int[] weekdays;

  public int[] getHours()
  {
    return this.hours;
  }

  public int[] getWeekdays()
  {
    return this.weekdays;
  }

  public void setHours(int[] paramArrayOfInt)
  {
    this.hours = paramArrayOfInt;
  }

  public void setWeekdays(int[] paramArrayOfInt)
  {
    this.weekdays = paramArrayOfInt;
  }
}

/* Location:           D:\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.dynastech.cdmetro.newui.entity.LocationRules
 * JD-Core Version:    0.6.0
 */