package com.dynastech.oa.entity;

import java.util.LinkedList;

import com.dynastech.cdmetro.newui.entity.Entity;

public class ContactData extends Entity
{
  private LinkedList<Object> Contact_Depart_Users;

  public LinkedList<Object> getContact_Depart_Users()
  {
    return this.Contact_Depart_Users;
  }

  public void setContact_Depart_Users(LinkedList<Object> paramLinkedList)
  {
    this.Contact_Depart_Users = paramLinkedList;
  }
}
