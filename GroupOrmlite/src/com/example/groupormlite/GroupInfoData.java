package com.example.groupormlite;

import com.j256.ormlite.field.DatabaseField;

public class GroupInfoData
{
	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField(indexName = "groupInfo_string",index = true,defaultValue = "ABC")
	String string;
	@DatabaseField(indexName = "groupInfo_old",index = true,defaultValue = "ABC")
	String old;
	@DatabaseField(indexName = "groupInfo_num",index = true,defaultValue = "ABC")
	String num;
	
	
	
	public GroupInfoData()
	{
	}
	
	GroupInfoData(String string , String old , String num)
	{
		this.string = string;
		this.old = old ;
		this.num = num;
	}
	
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getString()
	{
		return string;
	}
	public void setString(String string)
	{
		this.string = string;
	}

	public String getOld()
	{
		return old;
	}
	public void setOld(String old)
	{
		this.old = old;
	}
	public String getNum()
	{
		return num;
	}
	public void setNum(String num)
	{
		this.num = num;
	}
	@Override
	public String toString()
	{
		return "id = "+id+" \t: "+"人数: = "+num+"年龄 = "+old+"  职业 = "+string;
	}
	
	
}
