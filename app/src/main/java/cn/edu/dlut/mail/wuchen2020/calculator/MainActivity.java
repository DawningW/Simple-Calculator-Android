package cn.edu.dlut.mail.wuchen2020.calculator;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import brookelovesummer.OperateString2;

public class MainActivity extends AppCompatActivity {
    OperateString2 operator = new OperateString2();

    TextView textLast;
    TextView textInput;
    ToggleButton buttonShift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configuration configuration = getResources().getConfiguration();
        getSupportActionBar().setTitle("Calculator - wc");
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
            buttonShift = findViewById(R.id.button_shift);
            buttonShift.setOnClickListener(this::onShiftClick);
        }
        textLast = findViewById(R.id.text_last);
        textInput = findViewById(R.id.text_input);
        String lastStr = "";
        String inputStr = "";
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("LAST"))
                lastStr = savedInstanceState.getString("LAST");
            if (savedInstanceState.containsKey("INPUT"))
                inputStr = savedInstanceState.getString("INPUT");
        }
        textLast.setText(lastStr);
        textInput.setText(inputStr);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("LAST", "" + textLast.getText());
        outState.putString("INPUT", "" + textInput.getText());
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_0) {
            append("0");
        } else if (id == R.id.button_1) {
            append("1");
        } else if (id == R.id.button_2) {
            append("2");
        } else if (id == R.id.button_3) {
            append("3");
        } else if (id == R.id.button_4) {
            append("4");
        } else if (id == R.id.button_5) {
            append("5");
        } else if (id == R.id.button_6) {
            append("6");
        } else if (id == R.id.button_7) {
            append("7");
        } else if (id == R.id.button_8) {
            append("8");
        } else if (id == R.id.button_9) {
            append("9");
        } else if (id == R.id.button_point) {
            append(".");
        } else if (id == R.id.button_percent) {
            append("%");
        } else if (id == R.id.button_add) {
            append("+");
        } else if (id == R.id.button_sub) {
            append("-");
        } else if (id == R.id.button_mul) {
            append("*");
        } else if (id == R.id.button_div) {
            append("/");
        } else if (id == R.id.button_E) {
            append("*10^");
        } else if (id == R.id.button_factorial) {
            append("!");
        } else if (id == R.id.button_lbracket) {
            append("(");
        } else if (id == R.id.button_rbracket) {
            append(")");
        } else if (id == R.id.button_square) {
            append("^2");
        } else if (id == R.id.button_sqrt) {
            append("√");
        } else if (id == R.id.button_pow) {
            append("^(");
        } else if (id == R.id.button_ln) {
            append("ln(");
        } else if (id == R.id.button_lg) {
            append("lg(");
        } else if (id == R.id.button_e) {
            append("e");
        } else if (id == R.id.button_sin) {
            append(!buttonShift.isChecked() ? "sin(" : "arcsin(");
        } else if (id == R.id.button_cos) {
            append(!buttonShift.isChecked() ? "cos(" : "arccos(");
        } else if (id == R.id.button_tan) {
            append(!buttonShift.isChecked() ? "tan(" : "arctan(");
        } else if (id == R.id.button_rad) {
            toast("尚未实现哦~");
        } else if (id == R.id.button_pi) {
            append("π");
        } else if (id == R.id.button_del) {
            delete();
        } else if (id == R.id.button_clear) {
            clear();
        } else if (id == R.id.button_equal) {
            calc();
        } else {
            toast("错误: 无效的按钮");
        }
    }

    public void onShiftClick(View view) {
        if (buttonShift.isChecked()) {
            ((Button) findViewById(R.id.button_sin)).setText("sin⁻¹");
            ((Button) findViewById(R.id.button_cos)).setText("cos⁻¹");
            ((Button) findViewById(R.id.button_tan)).setText("tan⁻¹");
        } else {
            ((Button) findViewById(R.id.button_sin)).setText("sin");
            ((Button) findViewById(R.id.button_cos)).setText("cos");
            ((Button) findViewById(R.id.button_tan)).setText("tan");
        }
    }

    private void append(String str) {
        textInput.append(str);
    }

    private void delete() {
        if (textInput.length() > 0) {
            StringBuilder sb = new StringBuilder(textInput.getText());
            sb.deleteCharAt(sb.length() - 1);
            textInput.setText(sb.toString());
        }
    }

    private void clear() {
        textLast.setText("");
        textInput.setText("");
    }

    private void calc() {
        if (textInput.length() > 0) {
            String str = "" + textInput.getText();
            textLast.setText(str);
            textInput.setText(operator.calculate(str));
        }
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
