package com.y54.bookkeep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private RecyclerView mRv;
    private TextView nowMonth,costSum,sum,left;
    private  FloatingActionButton fab;
    double pre_sum = 1000;
    double use_sum = 0;
    private DBHelper helper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLv = (ListView) findViewById(R.id.lv);
        mRv = (RecyclerView)findViewById(R.id.rv);
        nowMonth = (TextView) findViewById(R.id.now_month);
        costSum = (TextView) findViewById(R.id.cost_sum);
        sum = (TextView) findViewById(R.id.sum);
        left = (TextView) findViewById(R.id.left);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月");
        String date=sdf.format(new Date());
        nowMonth.setText(date);


        //添加
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        //获得记一笔数据 返回hashmap
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            ContentValues values = new ContentValues();
            values.put("date",bundle.getString("date"));
            values.put("type",bundle.getString("type"));
            values.put("ioType",bundle.getString("io_type"));
            values.put("cost",bundle.getString("cost"));
            values=values;
            helper.insert(values);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Cursor cu = helper.query(-1);
        RecycleAdapter adapter = new RecycleAdapter(MainActivity.this,cu);
        mRv.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);


        write(helper.getSumCost());
        helper.colse();
    }

    class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.myViewHolder>{

        private Context mContext;
        private Cursor c;
        private String dw = "aaa";

        public RecycleAdapter(Context context,Cursor cursor) {
            this.mContext = context;
            this.c = cursor;
        }

        @NonNull
        @Override
        public RecycleAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new myViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_date,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecycleAdapter.myViewHolder holder, int position) {
            c.moveToPosition(position);
            Log.d("ttttttp",String.valueOf(position));
            Log.d("ttttttdw",dw);
            holder.cd.setText(c.getString(1));
            holder.ct.setText(c.getString(3));
            holder.cv.setText(c.getString(4));
            holder.cdd.setText(c.getString(1));

            if(dw.compareTo(c.getString(1))!=0){
                holder.cdd.setVisibility(View.VISIBLE);
                dw=c.getString(1);
                Log.d("tttttt", "draw "+c.getString(1));
            }else if(dw.compareTo(c.getString(1))==0){
                //holder.cdd.setVisibility(View.INVISIBLE);
                //holder.cdd.setHeight(0);
                Log.d("tttttt", "cancel "+dw);
            }

        }

        @Override
        public int getItemCount() {
            return c.getCount();
        }

        class myViewHolder extends RecyclerView.ViewHolder{

            private TextView cdd,cd,ct,cv;


            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                this.cdd = (TextView)itemView.findViewById(R.id.cost_datee);
                this.cd = (TextView)itemView.findViewById(R.id.cost_date);
                this.ct = (TextView)itemView.findViewById(R.id.cost_type);
                this.cv = (TextView)itemView.findViewById(R.id.cost_value);
            }
        }
    }

    private void write(double s){
        use_sum = s;
        DecimalFormat df = new DecimalFormat("#0.00");
        costSum.setText("¥"+df.format(use_sum));
        sum.setText("预算："+pre_sum);
        left.setText("剩余："+df.format(pre_sum-use_sum));
    }


}
