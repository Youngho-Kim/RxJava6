### RxJava를 활용한 LoginPage 만들기
[전체소스코드](https://github.com/Youngho-Kim/RxJava6/blob/master/app/src/main/java/com/fastcampus/kwave/android/rxjava6/LoginActivity.java)

##### 로그인 페이지
1. 주 기능은 로그인을 할때 정규식을 활용해 ID와 PW를 입력.  
정규식에 맞지 않는 경우 sign in 버튼이 비활성화 되도록 하였음
``` java
        Observable.combineLatest(idEmitter,pwEmitter,
                (idEvent, pwEvent) -> {
                    boolean checkId = util.validateEmail(editEmail.getText().toString());
                    boolean checkPw = util.validatePassword(editPassWord.getText().toString());
                    return checkId && checkPw;
                }
        ).subscribe(
                flag -> btnSign.setEnabled(flag)
        );
```
  
  
2. TextViewTextChangeEvent는 텍스트뷰에서 변경 사항이 생기면 알려주는 것이다.  
EditText의 경우 TextView를 상속 받고 있기 때문에 적용이 가능하다.
```java
        Observable<TextViewTextChangeEvent> idEmitter = RxTextView.textChangeEvents(editEmail);
        Observable<TextViewTextChangeEvent> pwEmitter = RxTextView.textChangeEvents(editPassWord);
```
  
3. combineLatest는 두 Observable 중 한쪽에서 발행할 때 각 Observable이 발행 한 최신 항목을 지정된 
함수를 통해 결합하고이 함수의 결과에 따라 항목을 방행한다.
CombineLatest 연산자는 Zip과 비슷한 방식으로 작동하지만 Zip은 각각의 압축 된 Observable이 이전에 
압축 해제 된 항목을 발행할 때만 항목을 발행하는 반면 CombineLatest는 Observable 중 하나가 항목을 
발행할 때마다 항목을 발행한다.   
Observables에서 항목을 발행하면 CombineLatest는 제공 한 함수를 사용하여 다른 Observables에서 각각 
가장 최근에 발행 된 항목을 결합하고 해당 함수의 반환 값을 내보낸다.  
[설명사진보기](http://reactivex.io/documentation/ko/operators/images/combineLatest.png)
    

