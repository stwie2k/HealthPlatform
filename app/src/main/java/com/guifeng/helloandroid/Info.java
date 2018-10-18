package com.guifeng.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Info extends Activity {
    final  String[] ID= new String[]{"粮","蔬","饮","肉","蔬","蔬","蔬","粮","杂"};
    final  String[] name =new String[]{"大豆","十字花科蔬菜","牛奶","海鱼","菌菇类","番茄","胡萝卜","荞麦","鸡蛋"};

    final String[] type = new String[]{"粮食","蔬菜","饮品","肉食","蔬菜","蔬菜","蔬菜","粮食","杂"};
    final String[] nut = new String[]{"蛋白质","维生素C","钙","蛋白质","微量元素",
                    "番茄红素","胡萝卜素","膳食纤维","几乎所有营养物质"};
    final String[] backcolor=new String[]{"#BB4C3B","#C48D30","#4469B0","#20A17B","#BB4C3B","#4469B0","#20A17B","#BB4C3B","#C48D30"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent intent=getIntent();
        Bundle bundle =intent.getExtras();
        String na=bundle.getString("name");
         int flag =0;
        for(int j=0;j<9;j++)
        {
            if(name[j].equals(na))
            {
                flag=j;
                break;
            }
    }
    final int tag =flag;


        RelativeLayout rl=findViewById(R.id.one);
        rl.setBackgroundColor(Color.parseColor(backcolor[tag]));
        ImageButton ib1=findViewById(R.id.back);
        ib1.setBackgroundColor(Color.parseColor(backcolor[tag]));
        ImageButton ib2=findViewById(R.id.star);
        ib2.setBackgroundColor(Color.parseColor(backcolor[tag]));

        TextView _name = findViewById(R.id.title);
        _name.setText(name[tag]);
        TextView _type = findViewById(R.id.type);
        _type.setText(type[tag]);
        TextView _nut = findViewById(R.id.nut);
        _nut.setText("富含 "+nut[tag]);

        ImageButton addButton=findViewById(R.id.add);
        addButton.setOnClickListener((view) ->
          {
              Intent intent1 = new Intent(Info.this,MainActivity.class);
              Bundle bundle1 = new Bundle();
              bundle1.putInt("position",tag);
              bundle1.putString("name",name[tag]);
              intent1.putExtras(bundle);

              setResult(1,intent1);
              Toast.makeText(getApplicationContext(),"已收藏", Toast.LENGTH_SHORT).show();

          }
        );

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener( (view)->{
            finish();
        });


        ImageView Star=findViewById(R.id.star);
        Star.setTag(0);
        Star.setOnClickListener((view) ->{
            int index=(Integer) view.getTag();
            if (index==0)
            {
                Star.setTag(1);
                Star.setImageResource(R.mipmap.full_star);
            }
            else
            {
                Star.setTag(0);
                Star.setImageResource(R.mipmap.empty_star);
            }




        });


        List<Map<String,String>> data=new ArrayList<>();
        String[] info= new String[]{"分享信息","不感兴趣","查看更多信息","出错反馈"};
        for(int i=0;i<4;i++){
            Map<String,String>temp = new LinkedHashMap<>();
            temp.put("MSG",info[i]);
            data.add(temp);
        }

        ListView listView=findViewById(R.id.lw);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,data,R.layout.buttonmessage,new String[] {"MSG"},new int[]{R.id.message});
        listView.setAdapter(simpleAdapter);



        }
}
