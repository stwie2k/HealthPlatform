package com.guifeng.helloandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    final  String[] ID= new String[]{"粮","蔬","饮","肉","蔬","蔬","蔬","粮","杂"};
    final  String[] name =new String[]{"大豆","十字花科蔬菜","牛奶","海鱼","菌菇类",
            "番茄","胡萝卜","荞麦","鸡蛋"};
    List<Map<String,String>> data = new ArrayList<>();
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         setRecyclerView();
        setlistview();
        setFloatingActionButton();
    }
    public void setRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<Map<String,String>> data=new ArrayList<>();

        for(int i=0;i<9;i++){
            Map<String,String>temp = new LinkedHashMap<>();
            temp.put("ID",ID[i]);
            temp.put("name",name[i]);
            data.add(temp);

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final MyRecyclerViewAdapter myAdapter = new MyRecyclerViewAdapter<Map<String,String>>(MainActivity.this, R.layout.items, data) {
            @Override
            public void convert(MyViewHolder holder, Map<String,String> s) {
                // Colloction是自定义的一个类，封装了数据信息，也可以直接将数据做成一个Map，那么这里就是Map<String, Object>
                TextView name = holder.getView(R.id.name);
                name.setText(s.get("name"));
                TextView first = holder.getView(R.id.ID);
                first.setText(s.get("ID"));
            }
        };
        myAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener(){
            @Override
            public void onClick(int position){
                Intent intent = new Intent(MainActivity.this,Info.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                bundle.putString("name",name[position]);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }

            @Override
            public void onLongClick(final int position){
                for(int i=position;i<8;i++) {
                    ID[i]=ID[i+1];
                    name[i]=name[i+1];
            }
                myAdapter.removeItem(position);
                Toast.makeText(getApplicationContext(),"移除第"+position+"个商品",Toast.LENGTH_SHORT).show();

            }
        });


        recyclerView.setAdapter(myAdapter);

    }
   public void setlistview()
   {
       ListView listview = (ListView)findViewById(R.id.listView);

        simpleAdapter = new SimpleAdapter(this,data,R.layout.shoplist,
               new String[] {"icon","itemname"},new int[]{R.id.Icon,R.id.Name});


       listview.setAdapter(simpleAdapter);

       listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // 处理单击事件
               Intent intent=new Intent(MainActivity.this,Info.class);

               ConstraintLayout layout = (ConstraintLayout)view;
               TextView status = (TextView) layout.findViewById(R.id.Name);
               String _name=status.getText().toString();
               int index=0;
               for(int j=0;j<9;j++)
               {
                   if(name[j].equals(_name))
                   {
                       index=j;
                       break;
                   }
               }
               Bundle bundle = new Bundle();
               bundle.putInt("position",index);
               bundle.putString("name",_name);
               intent.putExtras(bundle);
               startActivityForResult(intent,1);


           }
       });
       listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView,View view,final int position,long l) {

               ConstraintLayout layout = (ConstraintLayout)view;
               TextView status = (TextView) layout.findViewById(R.id.Name);
               String _name=status.getText().toString();


               final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
               alertDialog.setTitle("删除");
               alertDialog.setMessage("确定删除" + _name + "?");
               alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Toast.makeText(getApplicationContext(), "取消删除", Toast.LENGTH_SHORT).show();
                           }
                       });
               alertDialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                           @Override
                           public void onClick(DialogInterface dialog, int which){
                               data.remove(position);
                               simpleAdapter.notifyDataSetChanged();
                               Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                           }
                       }).show();
               return true;
           }
       });



   }
    public void setFloatingActionButton()
    {
        final FloatingActionButton fb = findViewById(R.id.btn);
       final RecyclerView recyclerView = findViewById(R.id.recyclerView);
       final ListView listView = (ListView)findViewById(R.id.listView);

        fb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int recycle1=recyclerView.getVisibility();//得到recycleView的状态
                int list1=listView.getVisibility();
                if(recycle1==View.VISIBLE && list1==View.INVISIBLE){
                    recycle1=View.INVISIBLE;
                    list1=View.VISIBLE;
                    recyclerView.setVisibility(recycle1);
                    listView.setVisibility(list1);
                    fb.setImageResource(R.mipmap.mainpage);
                }
                else{
                    recycle1=View.VISIBLE;
                    list1=View.INVISIBLE;
                    recyclerView.setVisibility(recycle1);
                    listView.setVisibility(list1);
                    fb.setImageResource(R.mipmap.collect);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data1) {
        super.onActivityResult(requestCode, resultCode, data1);
        // RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
        // operation succeeded. 默认值是-1


        if (resultCode == 1) {
            if (requestCode == 1) {

                Bundle bundle=data1.getExtras();

                int tag = bundle.getInt("position");
                Map<String,String>temp = new LinkedHashMap<>();
              temp.put("icon",ID[tag]);
              temp.put("itemname",name[tag]);


                data.add(temp);
                 simpleAdapter.notifyDataSetChanged();


            }
        }
    }

}
