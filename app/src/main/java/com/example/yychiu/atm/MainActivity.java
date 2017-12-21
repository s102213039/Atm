package com.example.yychiu.atm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.renderscript.Script;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    boolean logon = false;
    public static final int FUNC_LOGIN=1;
    String[] func = {"餘額查詢","交易明細","最新消息","投資理財","離開"};
    int[] icons = {R.drawable.func_balance,
                    R.drawable.func_history,
                    R.drawable.func_news,
                    R.drawable.func_finance,
                    R.drawable.func_exit};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent (this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.func_exit)
                .setContentTitle("This is Title")
                .setContentText("This is Text")
                .setContentInfo("This is Info")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1,notification);

        GridView grid = (GridView)findViewById(R.id.grid);
        IconAdapter gAdapter = new IconAdapter();
        grid.setAdapter(gAdapter);
        grid.setOnItemClickListener(this);
        if(!logon){  //如果未登入，進去LoginActivity
            startActivityForResult(new Intent(this,LoginActivity.class),FUNC_LOGIN);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==FUNC_LOGIN){
            if(resultCode==RESULT_OK){
                String uid = data.getStringExtra("LOGIN_USERID");
                String pw = data.getStringExtra("LOGIN_PASSWD");
                Log.d("Result",uid+"/"+pw);
            }else{
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("設定")
                        .setMessage("Hello")
                        .setPositiveButton("OK",null)
                        .setNeutralButton("Cancel",null)
                        .show();
        }
        return super.onOptionsItemSelected(item);
    }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch ((int)id){
                case R.drawable.func_balance:
                    break;
                case R.drawable.func_history:
                    startActivity(new Intent(this,TransActivity.class));
                    break;
                case R.drawable.func_news:
                    break;
                case R.drawable.func_finance:
                    startActivity(new Intent(this,FinanceActivity.class));
                    break;
                case R.drawable.func_exit:
                    finish();
                    break;
            }
        }

    private class IconAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return icons[position];
        }

        @Override
        public long getItemId(int position) {
            return icons[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if(row==null){
                row = getLayoutInflater().inflate(R.layout.item_row,null);
                ImageView image = (ImageView)row.findViewById(R.id.item_image);
                TextView text = (TextView)row.findViewById(R.id.item_text);
                image.setImageResource(icons[position]);
                text.setText(func[position]);
            }
            return row;
        }
    }
}
