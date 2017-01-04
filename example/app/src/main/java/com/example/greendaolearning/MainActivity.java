package com.example.greendaolearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.greendaolearning.greendao.Test0Dao;
import com.example.greendaolearning.model.Test0;
import com.example.greendaolearning.util.DbHelper;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText insertName,updataName,updataNewName,deleteName,queryName;
    private Test0Dao test0Dao;
    private List<Test0> listdata;
    private ListView listView;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
}

    private void initView(){
        insertName= (EditText) findViewById(R.id.insertName);
        updataName= (EditText) findViewById(R.id.updataName);
        updataNewName= (EditText) findViewById(R.id.updataNewName);
        deleteName= (EditText) findViewById(R.id.deleteName);
        queryName= (EditText) findViewById(R.id.queryName);
        listView= (ListView) findViewById(R.id.dblist);
        findViewById(R.id.insertBtn).setOnClickListener(this);
        findViewById(R.id.updataBtn).setOnClickListener(this);
        findViewById(R.id.deleteBtn).setOnClickListener(this);
        findViewById(R.id.queryBtn).setOnClickListener(this);
        findViewById(R.id.queryAllBtn).setOnClickListener(this);

        test0Dao = DbHelper.getInstance(getApplication()).getDaoSession().getTest0Dao();
        listdata=new ArrayList<>();
        adapter=new MyAdapter();
        listView.setAdapter(adapter);
    }
    private void initData(){
        listdata.addAll(getDbList(null));
        adapter.notifyDataSetChanged();
    }

    /**
     * 插入数据
     * @param test0
     */
    public void insertTest0(Test0 test0){
        if(test0.getDatetime()<=0){
            test0.setDatetime(System.currentTimeMillis());
        }
        for (int i=0;i<1000;i++){
            Test0 test222=new Test0();
            test222.setUsername("name+i");
            test222.setDatetime(System.currentTimeMillis());
            test0Dao.insert(test222);
        }
    }

    /**
     * 更新数据
     * @param name
     * @param newName
     */
    public void updataTest0(String name,String newName){
        QueryBuilder<Test0> qb = test0Dao.queryBuilder();
        if(name!=null && !name.equals("")){
            qb.where(new WhereCondition.StringCondition("username='"+name+"'"));
            Test0 test=null;
            if(qb.list().size()>0)
                test=qb.list().get(0);
            if(test!=null){
                test.setUsername(newName);
                test0Dao.update(test);
            }
        }
    }

    /**
     * 删除数据
     * @param name
     * @return
     */
    public List<Test0> deleteTest0(String name){
        QueryBuilder<Test0> qb = test0Dao.queryBuilder();
        List<Test0> list=null;
        if(name!=null && !name.equals("")){
            qb.where(new WhereCondition.StringCondition("username='"+name+"'"));
            list = qb.list();
            qb.buildDelete().executeDeleteWithoutDetachingEntities();
           /* if(qb.list().size()>0)
                test=qb.list().get(0);
            if(test!=null){
                test0Dao.delete(test);
            }*/
        }
        if(list==null){
            list=new ArrayList<>();
        }
        return list;
    }

    /**
     * 查询数据
     * @param name
     * @return
     */
    public List<Test0> queryTest0(String name){
        QueryBuilder<Test0> qb = test0Dao.queryBuilder();
        List<Test0> list=null;
        if(name!=null && !name.equals("")){
            qb.where(new WhereCondition.StringCondition("username like '%"+name+"%'"));
            list = qb.list();
        }
        if(list==null){
            list=new ArrayList<>();
        }
        return list;
    }

    /**
     * 获取全部数据
     * @param condition
     * @return
     */
    public List<Test0> getDbList(String condition){
        QueryBuilder<Test0> qb = test0Dao.queryBuilder();
        if(condition!=null && !condition.equals("")){
            qb.where(Test0Dao.Properties.Username.eq(condition));
           /* qb.offset(0);//分页开始index
            qb.limit(5);//每页数量*/
        }
        qb.orderDesc(Test0Dao.Properties.Datetime);
        List<Test0> list = qb.list();
        return list;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.insertBtn:
                if(!insertName.getText().toString().equals("")) {
                    Test0 test0 = new Test0();
                    test0.setUsername(insertName.getText().toString());
                    test0.setDatetime(System.currentTimeMillis());
                    insertTest0(test0);
                    listdata.add(test0);
                }
                break;
            case R.id.updataBtn:
                updataTest0(updataName.getText().toString(),updataNewName.getText().toString());
                listdata.clear();
                listdata.addAll(getDbList(null));
                break;
            case R.id.deleteBtn:
                listdata.removeAll(deleteTest0(deleteName.getText().toString()));
                break;
            case R.id.queryBtn:
                listdata.clear();
                listdata.addAll(queryTest0(queryName.getText().toString()));
                break;
            case R.id.queryAllBtn:
                listdata.clear();
                listdata.addAll(getDbList(null));
                break;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * list adapter
     */
    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listdata.size();
        }

        @Override
        public Object getItem(int i) {
            return listdata.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder=null;
            if(view==null){
                viewHolder=new ViewHolder();
                view= LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item,null);
                viewHolder.item_name= (TextView) view.findViewById(R.id.item_name);
                viewHolder.item_time= (TextView) view.findViewById(R.id.item_time);
                view.setTag(viewHolder);
            }else
                viewHolder= (ViewHolder) view.getTag();

            viewHolder.item_name.setText(listdata.get(i).getUsername());
            viewHolder.item_time.setText(listdata.get(i).getDatetime()+"");
            return view;
        }

        final class ViewHolder{
            TextView item_name,item_time;
        }
    }
}
