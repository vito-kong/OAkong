package com.dynastech.oa.ui.entity;


public class Metadata extends Entity
{
  private String content_type;
  private String edit_media;
  private String etag;
  private String media_etag;
  private String media_src;
  private String type;
  private String uri;

  public String getContent_type()
  {
    return this.content_type;
  }

  public String getEdit_media()
  {
    return this.edit_media;
  }

  public String getEtag()
  {
    return this.etag;
  }

  public String getMedia_etag()
  {
    return this.media_etag;
  }

  public String getMedia_src()
  {
    return this.media_src;
  }

  public String getType()
  {
    return this.type;
  }

  public String getUri()
  {
    return this.uri;
  }

  public void setContent_type(String paramString)
  {
    this.content_type = paramString;
  }

  public void setEdit_media(String paramString)
  {
    this.edit_media = paramString;
  }

  public void setEtag(String paramString)
  {
    this.etag = paramString;
  }

  public void setMedia_etag(String paramString)
  {
    this.media_etag = paramString;
  }

  public void setMedia_src(String paramString)
  {
    this.media_src = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public void setUri(String paramString)
  {
    this.uri = paramString;
  }
}