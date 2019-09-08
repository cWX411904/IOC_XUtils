package com.ck.xutilsioc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.xutilsioc.annotation.ContentView;
import com.ck.xutilsioc.annotation.OnClick;
import com.ck.xutilsioc.annotation.OnLongClick;
import com.ck.xutilsioc.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.app_text)
    private Button textView;
    private TextView textView2;
    private TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//2
//        Toast.makeText(this, "" + textView, Toast.LENGTH_SHORT).show();

    }

//    @OnClick({R.id.app_text, R.id.app_text1})
//    public void click(View view) {
//        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
//    }

    @Deprecated
    @OnClick({R.id.app_text,R.id.app_text1})
    public boolean click(View view) {
//        Toast.makeText(this,"---->"+textView,Toast.LENGTH_SHORT).show();
//       textView.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//
//           }
//       });

//        textView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });
        NewsDialog newsDialog = new NewsDialog(this);
        newsDialog.show();
        return false;
    }
    @Deprecated
    @OnLongClick({R.id.app_text,R.id.app_text1})
    public void longClick(View view) {

    }
}
