package com.fastcampus.kwave.android.rxjava6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassWord;
    private Button btnSign;

    Util util = new Util();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        btnSign.setEnabled(false);

        Observable<TextViewTextChangeEvent> idEmitter = RxTextView.textChangeEvents(editEmail);
        Observable<TextViewTextChangeEvent> pwEmitter = RxTextView.textChangeEvents(editPassWord);

        Observable.combineLatest(idEmitter,pwEmitter,
                (idEvent, pwEvent) -> {
//                    boolean checkId = idEvent.text().length() >=5;
//                    boolean checkPw = pwEvent.text().length() >=8;
                    boolean checkId = util.validateEmail(editEmail.getText().toString());
                    boolean checkPw = util.validatePassword(editPassWord.getText().toString());
                    return checkId&&checkPw;
                }
        ).subscribe(
                flag -> btnSign.setEnabled(flag)
        );
    }

    private void initView() {
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassWord = (EditText) findViewById(R.id.editPassWord);
        btnSign = (Button) findViewById(R.id.btnSign);
    }


    public class Util {
        // 이메일정규식
        public final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        public boolean validateEmail(String emailStr) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
            return matcher.find();
        }

        //비밀번호정규식
        public final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,16}$"); // 4자리 ~ 16자리까지 가능

        public boolean validatePassword(String pwStr) {
            Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr);
            return matcher.matches();
        }
        //    Util.validateEmail("가나다라");     // => false
        //    Util.validatePassword("aaa");    // => false}
    }
}
