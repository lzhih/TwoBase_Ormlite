package com.example.groupormlite;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

public class MainActivity extends Activity
{
	private ListView myListView;
	private EditText myEditText;
	 Dao<SimpleData, Integer> dao;
	 Dao<GroupInfoData, Integer> dao2;
	 private GroupInfoData groupInfoData,groupInfoData2,groupInfoData3,groupInfoData4;
	 
	private DatabaseHelper databaseHelper = null;
	private DatabaseHelper2 databaseHelper2 = null;
	
	private static int id;
	protected final static int MENU_ADD = Menu.FIRST;
	protected final static int MENU_EDIT = Menu.FIRST + 1;
	protected final static int MENU_DELETE = Menu.FIRST + 2;

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, MENU_ADD, 0, R.string.ADD);
//		menu.add(Menu.NONE, MENU_EDIT, 0, R.string.EDIT);
//		menu.add(Menu.NONE, MENU_DELETE, 0, R.string.DELETE);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		super.onOptionsItemSelected(item);
		switch (item.getItemId())
		{
		case MENU_ADD:
			try
			{
				operation("add");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			break;
		case MENU_EDIT:
			try
			{
				operation("edit");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			break;
		case MENU_DELETE:
			try
			{
				operation("delete");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		try
		{
			fillList();
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}

	private DatabaseHelper getHelper1() {
		if (databaseHelper == null) {
			databaseHelper = DatabaseHelper.getHelper(this);
		}
		return databaseHelper;
	}
	
	private DatabaseHelper2 getHelper2() {
		if (databaseHelper2 == null) {
			databaseHelper2 = DatabaseHelper2.getHelper(this);
		}
		return databaseHelper2;
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try
		{
			dao = getHelper1().getSimpleDataDao();
			dao2 = getHelper2().getGroupInfoData();
			
//			Dao<GroupInfoData, Integer> dao = getGroupInfoData();
		
			
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myEditText = (EditText) findViewById(R.id.EditText1);
		myListView = (ListView) findViewById(R.id.ListView1);

		try
		{
			fillList();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fillList() throws SQLException
	{

		List<SimpleData> list = dao.queryForAll();
		dao2.queryForAll();
		ArrayAdapter<SimpleData> arrayAdapter = new CountsAdapter(this,
				android.R.layout.simple_expandable_list_item_1, list);
		myListView.setAdapter(arrayAdapter);
		myListView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				// TODO Auto-generated method stub
				SimpleData simpleData = (SimpleData) myListView.getAdapter()
						.getItem(arg2);
				id = simpleData.getId();
				//					Toast.makeText(getApplication(), ""+getHelper2().GroupInfoData.queryForId(simpleData.groupInfo.id), Toast.LENGTH_SHORT).show();
				
				try
				{
					//必须执行refresh来得到填充的groupInfo对象,要不然回返回Null
					getHelper2().getGroupInfoData().refresh(simpleData.getGroupInfo());
					//
					Toast.makeText(getApplication(), ""+simpleData.getGroupInfo(), Toast.LENGTH_SHORT).show();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				myEditText.setText(simpleData.getString());
			}
		});
	}

	private void operation(String cmd) throws SQLException
	{

		SimpleData simpleData = new SimpleData();
		GroupInfoData group = new GroupInfoData();
		if (myEditText.getText().toString().equals(""))
			return;
		if (cmd == "add")
		{
//			simpleData.setString(myEditText.getText().toString());
////			simpleData.setGroupInfo(groupInfoData);
//			dao.create(simpleData);
			groupInfoData = new GroupInfoData("学生","5年","20人");
			dao2.create(groupInfoData);
			groupInfoData2 = new GroupInfoData("老师","10年","10人");
			dao2.create(groupInfoData2);
			groupInfoData3 = new GroupInfoData("军人","5年","555人");
			dao2.create(groupInfoData3);
			groupInfoData4 = new GroupInfoData("演员","2年","800人");
			dao2.create(groupInfoData4);
			
			SimpleData sim = new SimpleData();
			sim.setString("AAAA");
			//存储对象
			sim.setGroupInfo(groupInfoData);
			//添加
			dao.create(sim);
			
			
			dao2.refresh(sim.getGroupInfo());
			sim.setString("BBBB");
			sim.setGroupInfo(groupInfoData2);
			dao.create(sim);
			sim.setString("CCCC");
			sim.setGroupInfo(groupInfoData3);
			dao.create(sim);
			sim.setString("DDDD");
			sim.setGroupInfo(groupInfoData4);
			dao.create(sim);
			dao2.create(group);
		}
		if (cmd == "edit")
		{
			SimpleData sim = dao.queryForId(id);
			sim.changeValue(myEditText.getText().toString());
			dao.update(sim);
		}
		if (cmd == "delete")
		{
			dao.deleteById(id);
		}
		// myCursor.requery();

	}

	private class CountsAdapter extends ArrayAdapter<SimpleData>
	{

		public CountsAdapter(Context context, int textViewResourceId,
				List<SimpleData> items)
		{
			super(context, textViewResourceId, items);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

		/*
		 * You'll need this in your class to release the helper when done.
		 */
		if (databaseHelper != null) {
			databaseHelper.close();
			databaseHelper = null;
		}
		if (databaseHelper2 != null) {
			databaseHelper2.close();
			databaseHelper2 = null;
		}
	}
}
