package com.example.dietaryNutrition.login_register.register;

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
import android.widget.Toast;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dietaryNutrition.Member;
import com.example.dietaryNutrition.R;
import com.example.dietaryNutrition.login_register.login.Login;

public class Register2 extends AppCompatActivity {

    EditText password1, password2;
    Button next;
    TextView jumpToLogin, version;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);
        password1 = findViewById(R.id.register2_password1);
        password2 = findViewById(R.id.register2_password2);
        next = findViewById(R.id.register2_next);
        jumpToLogin = findViewById(R.id.register_to_login);
        version = findViewById(R.id.version);
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!password1.getText().toString().equals(password2.getText().toString())) {
                    Toast.makeText(Register2.this, "The two passwords entered are inconsistent", Toast.LENGTH_LONG).show();
                    return;
                }
                Member member = (Member) getIntent().getSerializableExtra("mem");
                assert member != null;
                member.setPassword(password2.getText().toString());
                if(member.CheckFormat2() == -1) {
                    Toast.makeText(Register2.this, "The password should not be less than 6 characters or digits", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(Register2.this, Register3.class);
                intent.putExtra("mem",member);
                startActivity(intent);
            }
        });

        String s = getResources().getString(R.string.register_to_login);
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(Register2.this, Login.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorPrimary));
            }
        }, 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        jumpToLogin.setText(spannableString);
        jumpToLogin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                assert view != null;
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if ((v instanceof EditText)) {
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
