package top.defaults.textbuttonapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.ele.uetool.UETool;
import top.defaults.logger.Logger;
import top.defaults.view.TextButton;
import top.defaults.view.TextButtonEffect;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button0) TextButton button0;
    @BindView(R.id.button5) TextButton button5;

    @OnClick({R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
    void onClick(TextView view) {
        Logger.d("Clicked: %s", view.getText());
    }

    @OnCheckedChanged(R.id.ue_tool)
    void onUeToolShow(boolean show) {
        if (show) {
            UETool.showUETMenu();
        } else {
            UETool.dismissUETMenu();
        }
    }

    @OnCheckedChanged(R.id.checkbox0)
    void onDisabledChanged(boolean checked) {
        button0.setEnabled(!checked);
        button0.setText(checked ? "disabled" : "enabled");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        button5.setEffect(new TextButtonEffect() {
            private TextButton textButton;

            @Override
            public void init(TextButton textButton) {
                this.textButton = textButton;
            }

            @Override
            public void actionDown() {
                textButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void actionUp() {
                textButton.setVisibility(View.VISIBLE);
            }
        });
    }
}
