package com.example.groupormlite;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class SimpleData
{
	
	@DatabaseField(generatedId = true)
	int id ;
	
	@DatabaseField
	String string;
	
	@DatabaseField(canBeNull = false, foreign = true)
	GroupInfoData groupInfo;
	
	public GroupInfoData getGroupInfo()
	{
		return groupInfo;
	}
	public void setGroupInfo(GroupInfoData groupInfo)
	{
		this.groupInfo = groupInfo;
	}
	public SimpleData()
	{
		
	}
	@Override
	public String toString()
	{
		return "id = "+groupInfo.getId();
	}

	public String getString()
	{
		return string;
	}

	public void setString(String string)
	{
		this.string = string;
	}

	public int getId()
	{
		return id;
	}

	
	
	public void setId(int id)
	{
		this.id = id;
	}
	public void changeValue(String string) {
		this.string = string;
	}
	
}
