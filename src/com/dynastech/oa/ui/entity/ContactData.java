package com.dynastech.oa.ui.entity;

import java.util.LinkedList;

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
