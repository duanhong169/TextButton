package top.defaults.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button0) TextButton button0;

    @OnClick({R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    void onClick(View view) {
        Toast.makeText(this, "onClick: " + view.getId(), Toast.LENGTH_SHORT).show();
    }

    @OnCheckedChanged(R.id.checkbox0)
    void onDisabledChanged(boolean checked) {
        button0.setEnabled(!checked);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
