
package com.asyntask;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class AsyntaskActivity extends AppCompatActivity {


    private ProgressDialog progressDialog;
    private int progress = 0;
    private int MAX_PROGRESS = 100;
    private TextView tvProgress;
    private DownAsynTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyntask);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        progressDialog = new ProgressDialog(this);
        task = new DownAsynTask();
        task.execute();

    }

    class DownAsynTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
            Log.i("onCancelled", "取消");
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            while (progress < 10000) {
                progress = progress + 2;
                Log.i("当前的进度", "progress==" + progress);
                publishProgress(progress);
                if(isCancelled())
                {
                    break;
                }
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            Log.i("onPostExecute", "提交成功");
            if (aBoolean) {
                Toast.makeText(AsyntaskActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AsyntaskActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog.show();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvProgress.setText("当前下载进度：" + values[0] + "%");
//            progressDialog.setMessage("当前下载进度：" + values[0] + "%");
        }

    }
    public void cancelTask(View v) {
        task.cancel(true);
    }
}
