package top.defaults.textbuttonapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.button1) TextButton button1;
    @BindView(R.id.button2) TextButton button2;
    @BindView(R.id.button3) TextButton button3;
    @BindView(R.id.button4) TextButton button4;
    @BindView(R.id.button100) TextButton button100;
    List<TextButton> buttons = new ArrayList<>();

    @OnClick({R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button100})
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
        button0.setText(checked ? "disabled" : "enabled");
        for (TextButton button: buttons) {
            button.setEnabled(!checked);
        }
    }

    @OnCheckedChanged(R.id.checkbox1)
    void onHighlightedChanged(boolean checked) {
        for (TextButton button: buttons) {
            button.setHighlighted(checked);
        }
    }

    @OnCheckedChanged(R.id.checkbox2)
    void onWithRippleChanged(boolean checked) {
        for (TextButton button: buttons) {
            if (checked) {
                button.addRipple();
            } else {
                button.removeRipple();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button100);

        button100.setEffects(new TextButtonEffect() {
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
