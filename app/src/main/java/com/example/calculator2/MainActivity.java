package com.example.calculator2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvres, tvso;
    MaterialButton btnC, btnOp, btnClo;
    MaterialButton btnDivide, btnMultiply, btnPlus, btnMinus, btnEquals;
    MaterialButton btn0, btn1, btn2, btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    MaterialButton btnAC, btnDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvres = findViewById(R.id.tvres);
        tvso = findViewById(R.id.tvso);

        asID(btnC,R.id.btnC);
        asID(btnOp,R.id.btnOp);
        asID(btnClo,R.id.btnClo);
        asID(btnDivide,R.id.btnDivide);
        asID(btnMultiply,R.id.btnMultiply);
        asID(btnPlus,R.id.btnPlus);
        asID(btnMinus,R.id.btnMinus);
        asID(btnEquals,R.id.btnEquals);
        asID(btn0,R.id.btn0);
        asID(btn1,R.id.btn1);
        asID(btn2,R.id.btn2);
        asID(btn3,R.id.btn3);
        asID(btn4,R.id.btn4);
        asID(btn5,R.id.btn5);
        asID(btn6,R.id.btn6);
        asID(btn7,R.id.btn7);
        asID(btn8,R.id.btn8);
        asID(btn9,R.id.btn9);
        asID(btnAC,R.id.btnAC);
        asID(btnDot,R.id.btnDot);

    }
    void asID(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCal = tvso.getText().toString();

        if (buttonText.equals("AC")){
            tvso.setText("");
            tvres.setText("0");
            return;
        }
        if (buttonText.equals("=")){
           double va = eval(dataToCal);
           tvres.setText(String.valueOf(va));
           return;
        }
        if (buttonText.equals("C")){
            dataToCal = dataToCal.substring(0, dataToCal.length()-1);
        } else {
            dataToCal = dataToCal + buttonText;
        }
        tvso.setText(dataToCal);
    }
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression + term | expression - term
            // term = factor | term * factor | term / factor
            // factor = + factor | - factor | ( expression )
            //        | number | functionName factor | factor ^ factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('x')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}