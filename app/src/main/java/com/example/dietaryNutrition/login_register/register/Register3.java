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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dietaryNutrition.Member;
import com.example.dietaryNutrition.R;
import com.example.dietaryNutrition.login_register.login.Login;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register3 extends AppCompatActivity {

    EditText height, weight, age;
    RadioGroup radioGroup;
    RadioButton nonBinary, male, female;
    Button register;
    TextView jumpToLogin, version;
    DatabaseReference dbRef;

    Member.Gender gender = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register3);
        height = findViewById(R.id.register3_height);
        weight = findViewById(R.id.register3_weight);
        age = findViewById(R.id.register3_age);
        radioGroup = findViewById(R.id.register3_radioGroup);
        nonBinary = findViewById(R.id.register3_nonBinary);
        male = findViewById(R.id.register3_male);
        female = findViewById(R.id.register3_female);
        register = findViewById(R.id.register3_register);
        jumpToLogin = findViewById(R.id.register_to_login);
        version = findViewById(R.id.version);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Member");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup Group, int CheckId) {
                if (nonBinary.isChecked()){
                    nonBinary.setChecked(true);
                    Register3.this.gender = Member.Gender.NonBinary;
                }
                else if (male.isChecked()){
                    male.setChecked(true);
                    Register3.this.gender = Member.Gender.Male;
                }
                else if (female.isChecked()){
                    female.setChecked(true);
                    Register3.this.gender = Member.Gender.Female;
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(height.getText().toString().length() == 0 || weight.getText().toString().length() == 0 || age.getText().toString().length() == 0) {
                    Toast.makeText(Register3.this, "All data must be filled in", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(Register3.this.gender == null) {
                    Toast.makeText(Register3.this, "Please choose a gender", Toast.LENGTH_LONG).show();
                    return;
                }
                Member member = (Member) getIntent().getSerializableExtra("mem");
                assert member != null;
                member.setHeight(Float.parseFloat(height.getText().toString()));
                member.setWeight(Float.parseFloat(weight.getText().toString()));
                member.setAge(Integer.parseInt(age.getText().toString()));
                member.setGender(gender);
                if (member.CheckFormat3() == -1) {
                    Toast.makeText(Register3.this, "Invalid data", Toast.LENGTH_LONG).show();
                    return;
                }
                dbRef.push().setValue(member);
                Toast.makeText(Register3.this, "Registration success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Register3.this, Login.class);
                intent.putExtra("mem",member);
                startActivity(intent);
            }
        });

        String s = getResources().getString(R.string.register_to_login);
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(Register3.this, Login.class));
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
