package com.ohhonghong.bangto;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class GroupMenuActivity extends Activity {
	private ListView mListView = null;
	private GroupAdapter mAdapter = null;
	View dlgview;
	ImageButton groupAddButton;
	EditText etGroupName;

	Typeface childFont;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupmenu);
		
		final ActionBar actionBar = getActionBar();
	      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	      actionBar.setDisplayShowTitleEnabled(false);
	      actionBar.setDisplayShowHomeEnabled(false);
	      actionBar.hide();
		
		mListView = (ListView) findViewById(R.id.listview);
		groupAddButton = (ImageButton) findViewById(R.id.groupAddButton);
		mAdapter = new GroupAdapter(this);
		mListView.setAdapter(mAdapter);

		mAdapter.addItem("ù ��° �׷�");
		mAdapter.addItem("�� ��° �׷�");
		mAdapter.addItem("�� ��° �׷�");
		mAdapter.addItem("�� ��° �׷�");
		mAdapter.addItem("�ټ� ��° �׷�");
		mAdapter.addItem("���� ��° �׷�");
		mAdapter.addItem("�ϰ� ��° �׷�");
		mAdapter.addItem("���� ��° �׷�");
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				// �ΰ��� ��� ��� ��밡���ϴ�.
				// Data data = (Data) parent.getItemAtPosition(position);
		        //Data data = mList.get(position);
		         
		        // ���� ��Ƽ��Ƽ�� �ѱ� Bundle �����͸� �����.
		        //Bundle extras = new Bundle();
		        //extras.putString("title", data.getTitle());
		        //extras.putString("description", data.getDescription());
		        //extras.putInt("color", data.getColor());
		         
		         
		        Intent intent = new Intent(getApplicationContext(), TabMainActivity.class);
		         
		        // ������ ���� Bundle�� ����Ʈ�� �ִ´�.
		        //intent.putExtras(extras);
		        // ��Ƽ��Ƽ�� �����Ѵ�.
		        startActivity(intent);
			}
		});

		groupAddButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dlgview = (View) View.inflate(GroupMenuActivity.this, R.layout.groupmenu_add_dialog, null);
				AlertDialog.Builder dlg = new AlertDialog.Builder(GroupMenuActivity.this);

				dlg.setView(dlgview);
				etGroupName = (EditText) dlgview.findViewById(R.id.etGroupName);

				// �Էµ� ������ �޾Ƶ帮�ڴ�. (Ȯ�� ��ư)
				dlg.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String groupName = etGroupName.getText().toString();
						mAdapter.addItem(groupName);
					}
				});

				// ��ҹ�ư ������ ��
				dlg.setNegativeButton("���", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dlg.show();
			}
		});
	}

	class ViewHolder {
		public TextView GroupName;
	}

	class GroupAdapter extends BaseAdapter {
		private Context mContext = null;
		private ArrayList<ListDataGroup> mListData = new ArrayList<ListDataGroup>();

		public GroupAdapter(Context mContext) {
			super();
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			return mListData.size();
		}

		@Override
		public Object getItem(int position) {
			return mListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();

				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.groupmenu_item, null);

				holder.GroupName = (TextView) convertView.findViewById(R.id.tvGroupName);
				
				//TextView tv = (TextView) convertView.findViewById(R.id.tvGroupName);
				//Typeface face = Typeface.createFromAsset(getAssets(), "fonts/KoreanYNMYTL.ttf");

				//tv.setTypeface(face);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ListDataGroup mData = mListData.get(position);

			holder.GroupName.setText(mData.groupName);

			return convertView;
		}

		public void addItem(String tvGroupName) {
			ListDataGroup addInfo = null;
			addInfo = new ListDataGroup();
			addInfo.groupName = tvGroupName;
			mListData.add(addInfo);
		}

		public void remove(int position) {
			mListData.remove(position);
			dataChange();
		}

		public void dataChange() {
			mAdapter.notifyDataSetChanged();
		}

	}
}