package com.example.lintangyuan.lab3;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GoodInfo extends AppCompatActivity {

    ListView listView;
    List<Map<String, Object>> data = new ArrayList<>();
    String[] info = new String[] {"一键下单", "分享商品", "不感兴趣", "查看更多商品促销信息"};
    boolean add = false;

    String goods_name = null;
    String goods_price = null;
    String goods_detail = null;
    Integer goods_image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.goods_info);

        //读取参数；
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            goods_name = extras.getString("goods_name");
            goods_price = extras.getString("goods_price");
            goods_detail = extras.getString("goods_detail");
            goods_image = extras.getInt("goods_image");
        }

        //文字处理;
        TextView name = (TextView) findViewById(R.id.textView_goodName);
        name.setText(goods_name);
        ImageView image = (ImageView) findViewById(R.id.imageview_goods);
        image.setImageResource(goods_image);
        TextView detail = (TextView) findViewById(R.id.textView_goodInfo_detail);
        detail.setText(goods_detail);
        TextView price = (TextView) findViewById(R.id.textView_goodInfo_price);
        price.setText(goods_price);

        for(int i = 0; i < 4; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("goods_info", info[i]);
            data.add(temp);
        }

        //下方列表;
        listView = (ListView) findViewById(R.id.listView_2);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.goods_list,
                new String[] {"goods_info"}, new int[] {R.id.textView_goodList});
        listView.setAdapter(simpleAdapter);

        //返回点击;
        ImageView back = (ImageView) findViewById(R.id.imageview_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodInfo.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("goods_name", goods_name);
                bundle.putString("goods_price", goods_price);
                intent.putExtras(bundle);
                if(add == true) setResult(RESULT_OK, intent);
                else setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        //点击添加到购物车;
        ImageView shoplist = (ImageView) findViewById(R.id.shoplist);
        shoplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add = true;
                Toast.makeText(GoodInfo.this, "商品已添加到购物车", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
