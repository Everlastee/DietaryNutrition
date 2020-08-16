package com.example.dietaryNutrition.login_register.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dietaryNutrition.Member;
import com.example.dietaryNutrition.R;
import com.example.dietaryNutrition.login_register.register.Register1;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView jumpToRegister, version;
    //DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login_login);
        jumpToRegister = findViewById(R.id.login_to_register);
        version = findViewById(R.id.version);

        Member member = (Member) getIntent().getSerializableExtra("mem");
        if(member != null) {
            email.setText(member.getEmail());
            password.setText(member.getPassword());
        }
        /*dbRef = FirebaseDatabase.getInstance().getReference().child("Member");
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                member.setEmail(email.getText().toString());
                member.setPassword(password.getText().toString());
                dbRef.push().setValue(member);
            }
        });*/
        String s = getResources().getString(R.string.login_to_register);
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(Login.this, Register1.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorPrimary));
            }
        }, 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        jumpToRegister.setText(spannableString);
        jumpToRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
