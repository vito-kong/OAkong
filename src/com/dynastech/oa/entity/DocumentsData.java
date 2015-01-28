package com.dynastech.oa.entity;

import com.dynastech.cdmetro.newui.entity.Entity;

public class DocumentsData extends Entity
{
  private String filename;
  private Metadata mMetadata;
  private String modify_time;

  public String getFilename()
  {
    return this.filename;
  }

  public String getModify_time()
  {
    return this.modify_time;
  }

  public Metadata getmMetadata()
  {
    return this.mMetadata;
  }

  public void setFilename(String paramString)
  {
    this.filename = paramString;
  }

  public void setModify_time(String paramString)
  {
    this.modify_time = paramString;
  }

  public void setmMetadata(Metadata paramMetadata)
  {
    this.mMetadata = paramMetadata;
  }
}
