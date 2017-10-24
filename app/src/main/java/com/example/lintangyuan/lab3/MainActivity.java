package com.example.lintangyuan.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private CommonAdapter commonAdapter;
    private SimpleAdapter simpleAdapter;

    //商品信息;
    String[] _products = {"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片",
    "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
    List<String> products = new ArrayList<>(Arrays.asList(_products));

    String[] _price = {"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00", "¥ 14.90", "¥ 132.59", "¥ 141.43",
    "¥ 139.43", "¥ 28.90"};
    List<String> price = new ArrayList<>(Arrays.asList(_price));

    Integer[] _index = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    List<Integer> index = new ArrayList<>(Arrays.asList(_index));

    String[] _detail = {"作者    Johanna Basford", "产地    德国", "产地    澳大利亚", "版本    8GB", "重量    2Kg",
    "产地    英国", "重量    300g", "重量    118g", "重量    249g", "重量    640g"};
     List<String> detail = new ArrayList<>(Arrays.asList(_detail));

    Integer[] _image = {R.mipmap.enchatedforest, R.mipmap.arla, R.mipmap.devondale, R.mipmap.kindle, R.mipmap.waitrose,
    R.mipmap.mcvitie, R.mipmap.ferrero, R.mipmap.maltesers, R.mipmap.lindt, R.mipmap.borggreve};
    List<Integer> image = new ArrayList<>(Arrays.asList(_image));

   FloatingActionButton floatingActionButton;
    
    ListView listView;
    List<Map<String, Object>> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        //商品列表RecycleView;
        mRecycleView = (RecyclerView) findViewById(R.id.recycleview);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        commonAdapter = new CommonAdapter(this, R.layout.item, products) {
            @Override
            public void convert(ViewHolder holder, String s) {
                TextView temp = holder.getView(R.id.textView);
                temp.setText(s);
                TextView temp_2 = holder.getView(R.id.alif);
                temp_2.setText(s.substring(0, 1));
            }
        };
        mRecycleView.setAdapter(commonAdapter);

//        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(commonAdapter);
//        animationAdapter.setDuration(1000);
//        mRecycleView.setAdapter(animationAdapter);
//        mRecycleView.setItemAnimator(new OvershootInLeftAnimator());

        Map<String, Object> temp = new LinkedHashMap<>();
        temp.put("goods_alif", "*");
        temp.put("goods_name", "购物车");
        temp.put("goods_price", "价格");
        temp.put("goods_index", "");
        datas.add(temp);

        listView = (ListView) findViewById(R.id.listView);
        simpleAdapter = new SimpleAdapter(this, datas, R.layout.item,
                new String[] {"goods_alif", "goods_name", "goods_price", "goods_index"},
                new int[] {R.id.alif, R.id.textView, R.id.textView_price, R.id.forIndex_shoppinglist});
        listView.setAdapter(simpleAdapter);

        //切换购物车和商品列表;
        mRecycleView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRecycleView.getVisibility() == View.VISIBLE) {
                    mRecycleView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    floatingActionButton.setImageResource(R.mipmap.mainpage);
                }
                else {
                    mRecycleView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    floatingActionButton.setImageResource(R.mipmap.shoplist);
                }
            }
        });

        //商品列表点击事件;
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {//点击显示商品详情;
                Intent intent = new Intent(MainActivity.this, GoodInfo.class);
                Bundle bundle = new Bundle();
                bundle.putString("goods_name", products.get(position));
                bundle.putString("goods_price", price.get(position));
                bundle.putString("goods_detail", detail.get(position));
                bundle.putInt("goods_image", image.get(position));
                bundle.putInt("goods_index", index.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);

            }
            @Override //长按移除商品;
            public void onLongClick(int position) {
                //删除以下四项以同步信息;
                products.remove(position);
                price.remove(position);
                image.remove(position);
                detail.remove(position);
                index.remove(position);
//                commonAdapter.mDatas.remove(position);
                commonAdapter.notifyDataSetChanged();
//                commonAdapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this,"移除第"+position+"个商品",Toast.LENGTH_SHORT).show();
            }
        });

        //点击购物车列表商品显示详情;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Intent intent = new Intent(MainActivity.this, GoodInfo.class);
                TextView textView_index = (TextView) view.findViewById(R.id.forIndex_shoppinglist);
                Integer position = Integer.parseInt(textView_index.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putString("goods_name", products.get(position));
                bundle.putString("goods_price", price.get(position));
                bundle.putString("goods_detail", detail.get(position));
                bundle.putInt("goods_image", image.get(position));
                bundle.putInt("goods_index", index.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //长按删除购物车商品;
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("移除商品")
                        .setMessage("从购物车移除" + datas.get(i).get("goods_name") +"?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        datas.remove(i);
                        simpleAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Map<String, Object> temp = new LinkedHashMap<>();
                temp.put("goods_alif", extras.getString("goods_name").substring(0, 1));
                temp.put("goods_name", extras.getString("goods_name"));
                temp.put("goods_price", extras.getString("goods_price"));
                temp.put("goods_index", extras.getInt("goods_index"));
                datas.add(temp);
                simpleAdapter.notifyDataSetChanged();
            }
        }
    }
}
