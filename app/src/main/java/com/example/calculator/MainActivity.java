package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    StringBuilder str;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btn_dot,btn_ac,btn_back,btn_add,btn_sub,btn_mul,btn_div,btn_ans,btn_left,btn_right;
    TextView input_data,output_data;
    boolean ans=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str = new StringBuilder();
        input_data = findViewById(R.id.input_data);
        output_data = findViewById(R.id.output_data);

        btn1 = findViewById(R.id.button_1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('1');
            }
        });

        btn2 = findViewById(R.id.button_2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('2');
            }
        });

        btn3 = findViewById(R.id.button_3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('3');
            }
        });

        btn4 = findViewById(R.id.button_4);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                input('4');
            }
        });

        btn5 = findViewById(R.id.button_5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('5');
            }
        });

        btn6 = findViewById(R.id.button_6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('6');
            }
        });

        btn7 = findViewById(R.id.button_7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('7');
            }
        });

        btn8 = findViewById(R.id.button_8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('8');
            }
        });

        btn9 = findViewById(R.id.button_9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('9');
            }
        });

        btn0 = findViewById(R.id.button_0);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('0');
            }
        });

        btn_dot = findViewById(R.id.button_dot);
        btn_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('.');
            }
        });

        btn_ac = findViewById(R.id.button_ac);
        btn_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = new StringBuilder();
                input_data.setText(str.toString());
                output_data.setText("0");
            }
        });

        btn_back = findViewById(R.id.button_backspace);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str.length()>0) {
                    str.deleteCharAt(str.length() - 1);
                    output_data.setText(str.toString());
                }
                ans=false;
            }
        });

        btn_add = findViewById(R.id.button_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('+');
            }
        });

        btn_sub = findViewById(R.id.button_sub);
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('-');
            }
        });

        btn_mul = findViewById(R.id.button_mul);
        btn_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('*');
            }
        });

        btn_div = findViewById(R.id.button_div);
        btn_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('/');
            }
        });

        btn_ans = findViewById(R.id.button_ans);
        btn_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str.length()==0){
                    return;
                }
                brackets();
                input_data.setText(str.toString());
                double result = calculation();
                if(result == (int)result){
                    output_data.setText(""+(int)result);
                }
                else{
                    output_data.setText(""+result);
                }
                ans=true;
            }
        });

        btn_left = findViewById(R.id.button_left_bracket);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input('(');
            }
        });

        btn_right = findViewById(R.id.button_right_bracket);
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(')');
            }
        });
    }
    private void input(char ch){
        if(ans){
            str = new StringBuilder();
            if(ch=='*' || ch == '/' || ch=='-' || ch=='+'){
                str.append(output_data.getText());
            }
            ans=false;
        }
        if(numeric(ch)){
            str.append(ch);
        }
        else if(str.length()>1 && ch=='.'){
            for(int i=str.length()-1; i>=0; i--){
                char c=str.charAt(i);
                if(c=='.') return;
                if(!numeric(c) && c!='.'){
                    break;
                }
            }
            if(!numeric(str.charAt(str.length()-1))) str.append('0');
            str.append(ch);
        }
        else if(str.length()>1 && !numeric(str.charAt(str.length()-1))){
            input_operator(ch,str.charAt(str.length()-1));
        }
        else{
            if(str.length()==0 && ch!='(' && ch!='+' && ch!='-'){
                str.append('0');
            }
            str.append(ch);
        }
        output_data.setText(str.toString());
    }

    private boolean numeric(char ch){
        return ch>='0' && ch<='9';
    }

    private void input_operator(char ch,char prev){
        if(prev=='.'){
            str.append("0"+ch);
        }
        else if(ch=='(' || prev=='('){
            if(ch!='*' && ch!='/'){
                str.append(ch);
            }
        }
        else if(ch==')' || prev==')'){
            if(prev==')'){
                str.append(ch);
            }
        }
        else{
            if(str.charAt(str.length()-2)=='(' && (ch=='*' || ch=='/'))
                return;
            str.setCharAt(str.length()-1,ch);
        }
    }

    private void brackets(){
        int count=0;
        char last=str.charAt(str.length()-1);
        if(last=='*' || last=='-' || last=='+' || last=='/'){
            str.deleteCharAt(str.length()-1);
        }
        for(char ch:str.toString().toCharArray()){
            if(ch=='('){
                count++;
            }
            if(ch==')'){
                count--;
            }
            if(count<0){
                str.insert(0,'(');
                count++;
            }
        }
        for(;count>0;count--){
            str.append(')');
        }
    }

    private double calculation(){
        Stack<Double> operands = new Stack();
        Stack<Character> operators = new Stack();
        StringBuilder s = new StringBuilder();
        char prev='(';
        str.append(")");
        operators.push(prev);
        for(char ch:str.toString().toCharArray()) {
            if (numeric(ch) || ch == '.' || ch=='E' || ch=='e') {
                if (prev == ')') {
                    operators.push('*');
                }
                s.append(ch);
            } else {
                if (s.length() != 0) {
                    operands.push(Double.parseDouble(s.toString()));
                    s = new StringBuilder();
                }
                if (ch == '(') {
                    if (numeric(prev)) {
                        operators.push('*');
                    }
                    operators.push(ch);
                } else if (ch == '*' || ch == '/') {
                    while(operators.peek()=='*' || operators.peek()=='/'){
                            operands.push(operation(operators.pop(),operands.pop(),operands.pop()));
                    }
                    operators.push(ch);
                }
                else{
                    if(prev=='('){
                        operands.push(0.0);
                    }
                    while(operators.peek()!='('){
                            operands.push(operation(operators.pop(),operands.pop(),operands.pop()));
                    }
                    if(ch=='+' || ch=='-'){
                        operators.push(ch);
                    }
                    else{
                        operators.pop();
                    }
                }
            }
            prev = ch;
        }
        str.deleteCharAt(str.length()-1);
        return operands.peek();
    }

    private double operation(char op , double num1 , double num2){
        switch(op){
            case '*' : return num1*num2;
            case '/' : return num2/num1;
            case '+' : return num2+num1;
            case '-' : return num2-num1;
        }
        return 0;
    }
}