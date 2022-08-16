# 성장그래프 - 'BE BETTER'
 📌 [Google Play Store](https://play.google.com/store/apps/details?id=com.growth.graphh)  
 + 개발환경 : Android Studio, Java, git
 + 개발기간 : 2020.12 ~ 2021.01
 + 라이브러리 : Google material design, MPAndroidChart, firebase(FCM) ...
 + 디자인 : 무료 이미지들을 활요하여 Pixlr E로 편집

## 소개
'BE BETTER'은 자기관리를 돕기위한 앱이다.  
총 4개의 탭(일정, 어제, 오늘, 성장그래프)을 가지고 있고 각각에 탭에서는 다음과 같은 기능을 담당한다.

+ 일정  
 : 달력에 계획을 작성할 수 있다. 작성된 계획은 날짜에 맞추어 각각 '어제' 와 '오늘' 탭에 보여지게 된다.  
+ 어제  
 : 어제 계획했던 일과 어제남겼던 메시지를 확일 할 수 있다. 또한 짧은 명언을 통해 사용자에게 동기를 부여한다.  
+ 오늘  
 : 오늘 계획한 일을 확인하고, 하루를 마무리 하며 나에게 메시지를 남기거나 그날의 펑점을 매길 수 있다.  
+ 성장그래프  
 : 오늘탭에서 매긴 평점을 통해 그래프를 그린다. 사용자는 그래프를 확인하고 특정날짜의 계획과 자신에게 남긴 메시지를 확인 할 수 있다. 
 
## 기획의도
작심삼일도 3일에 한번씩 하면 된다는 말이 있다. 어떠한 계획을 세우고 마음을 다잡으면, 그게 채 3일을 못넘기니  
3일에 한번씩 마음을 다잡자는 의미인데, 어떻게해야 매번 마음을 다잡게 할 수 있을까를 고민해보았다. 그러다 문득  
최근들어 주식열풍이 굉장히 뜨겁다고 뉴스에서 보도한것이 생각이 났고, 매일 주가차트를 들여다보는 사람들의 모습이 떠올랐다.  
그렇게 생각해 낸 것이 자신의 계획과 성취도를 주가차트 처럼 매일매일 변화하는 그래프를 통해 보여주자는 것이었다.  
사람들이 매일 주가차트를 들여다보고 때로는 실망하고 때로는 기뻐하는것 처럼  
자신의 성장그래프를 들여다보고 자신에게 실망하고 뿌듯해한다면 이를통해 마음을 다잡을 수 있겠다는 생각을 하였다.

## 와이어프레임
<img src="https://user-images.githubusercontent.com/63087903/163360373-80b857b9-f5e1-460f-acf1-a41a8842ac24.jpg" width="800" height="630">

## 상세설명
1. 앱 최초실행날짜를 감지하고 하루가 지날때마다 countUP 하여 그래프의 x축이 늘어나도록 설정  
2. splash테마를 지정  
3. SharedPreferences와 File I/O 활용하여 DB저장(조금은 독특한 방식으로 활용하였다.)
4. Bottomnavigation을 이용하여 MainActivity 1개에 Fragment 4개가 연결된 방식으로 구성
```
💡 Fragment_1(일정) 에서는 달력을 보여주고 날짜를 클릭하면 "2020년 02월 01일.txt"과 같은  
 정해진 형식으로 파일(일자별 계획)을 작성/저장할 수 있도록 하였다.

💡 Fragment_2(어제) 에서는 "어제"의 날짜에 해당하는 파일명을 찾아 화면에 보여준다.(일자별 계획)  
 이곳에서 보여지는 명언은 assets폴더에 저장되어있는 파일을 불러왔으며,  
 일자별로 서로 다른 명언이 보여지도록 하였다.(ex)21일 > assets 폴더의 21번째줄)

💡 Fragment_3(오늘) 에서는 "오늘"의 날짜에 해당하는 파일명을 찾아 화면에 보여준다.(일자별 계획)  
 이를 토대로 하루를 평가하는데, 1. 본인에게 남길 메시지를 작성하고 2. 하루의 만족도(성장지표)를 체크한다.
 
💡 Fragment_4(성장그래프) 에서는 이전탭들에서 작성한 정보를 그래프를 통해 보여준다 공간이다.  
 그래프는 MPAndroidChart를 통해 그렸으며, x축은 일자를 나타내고, y축은 성장지표를 보여준다.  
 일자별정보들은 DatePickerDialog를 통해 특정날짜에대한 정보(계획한 일, 나에게남긴 메시지)를 확인할 수 있다.
```
5. Firebase와 연동하여 Cloud Messasing 서비스를 통해 사용자에게 푸시알람을 보냄으로써 앱 참여도를 높였다.

## 스크린샷
<img src="https://user-images.githubusercontent.com/63087903/115550628-08931700-a2e5-11eb-9561-e405ffd0ccd8.jpg" width="160" height="340"> <img src="https://user-images.githubusercontent.com/63087903/106424399-7463a180-64a5-11eb-8e07-f819d2736a03.jpg" width="160" height="340"> <img src="https://user-images.githubusercontent.com/63087903/106424411-7af21900-64a5-11eb-9d60-e81681599926.jpg" width="160" height="340"> <img src="https://user-images.githubusercontent.com/63087903/106424419-7ded0980-64a5-11eb-8c97-3e4b4ff21880.jpg" width="160" height="340"> <img src="https://user-images.githubusercontent.com/63087903/106424428-81809080-64a5-11eb-95c8-8187ead22b9c.jpg" width="160" height="340">

<img src="https://user-images.githubusercontent.com/63087903/106449574-72123f00-64c7-11eb-8286-2235ed82c55c.jpg" width="160" height="340"> <img src="https://user-images.githubusercontent.com/63087903/106424406-775e9200-64a5-11eb-88e7-941fda0bc060.jpg" width="160" height="340"> <img src="https://user-images.githubusercontent.com/63087903/106424415-7c234600-64a5-11eb-9693-cd4dc3d70a21.jpg" width="160" height="340"> <img src="https://user-images.githubusercontent.com/63087903/106424423-7f1e3680-64a5-11eb-9887-a8c8c36dcd9c.jpg" width="160" height="340"> <img src="https://user-images.githubusercontent.com/63087903/106424432-834a5400-64a5-11eb-9529-dae8981f6b4b.jpg" width="160" height="340">


## 사용한 오픈소스
[MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)

## 저작권
```
   Copyright [2021] [HoKeun.LEE]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

## 🛠 개발과정 겪었던 에러와 해결방안
+ 프래그먼트를 처음 접해보면서 여러 이슈들을 겪었다.
```KOTLIN
1. ANR
   앱에서 데이터를 저장하는 부분은 Fragment3에서 이루어지고 (평점작성) 
   저장한 데이터를 읽는 부분은 Fragment4에서 이루어짐. (MPChart그리기)
   
   MPChart를 그리는데 필요한 값은 x축(날짜정보)과 y축(그날의평점)이 필요한데
   날짜정보는 D-Day와 비슷한 방법으로 매일 x축이 증가하게 하였지만,
   그날의평점은 사용자가 평점을 메기고 저장버튼을 누르지 않으면 따로 저장시키지 않아서
   이틀이상 앱을 사용하지 않다가 앱을 실행하게 되면 MPChart를 그리는 과정에서
   x축(날짜정보)에 해당하는 y축(그날의평점) 정보가 존재하기 않기 때문에 
   앱이 강제종료되는 문제가 발생하였다.
   
   👉 알람메니져를 활용해 자동으로 값을 할당하는 방법을 생각해 보았으나
   매번 시스템 자원을 활용하는것이 불필요하다고 판단하였고, 
   사용자가 앱을 사용하지 않았다면 그 전날의 평점과 동일하다는 가정하에
   현재 날짜와 앱을 마지막으로 사용한 날짜의 차이만큼 이전 평점정보를 할당하도록 하였다.
   이 과정에서 ANR을 막기위해 최초 앱 실행시 Fragment3을 가장먼저 실행시켜 
   그동안의 평점정보를 자동으로 저장시키고 Fragment4 실행시 MPChart를 그리는데 문제가 없도록 하였다
```
```KOTLIN
2. Network Request
   앱에 네이티브 광고를 적용했는데, 프래그먼트위에 광고가 올라간 방식이라서
   화면 전환시 매번 admob 네트워크 요청이 일어나서 버벅거리는 문제가 발생했다.
   
   👉 기존에 replace로 프래그먼트 화면을 전환하는 방식에서 
   admob요청이 일어나는 프래그먼트를 따로 add 하여 show, hide 를 사용하였으며,
   이외에 다른 프래그먼트에서는 attach, detach 를 사용하는것으로 해결하였다.
```
```KOTLIN
3. MainActivity UI 겹침 문제
   프래그먼트를 기존의 replace방식이 아닌 add 형태로 바꿨기 때문에 
   가로화면으로 변경한다거나, 다크모드를 실행하는경우 UI가 겹쳐서 실행되었다.
   
   👉 manifest에 android:configChanges="orientation|screenSize|uiMode" 를 추가하고
   MainActivity에 onConfigurationChanged 메소드를 오버라이드하여 화면전환과 다크모드
   실행을 막으려고 하였으나 단일기능으로 화면전환 혹은 다크모드를 막는건 가능했지만
   두 기능을 전부 막으려니 기능이 정상 작동하지 않아서 오랜시행착오 끝에 결국 엑티비티를 
   재 실행하는 방향으로 결정하였다.(더 알아봐야하는 부분임)
   
   👉 하지만, 앱을 실행하였다가 홈키로 나간 후 30분 이상의 시간이 흘러 다시 앱을 실행하게 되면
   또다시 엑티비티가 중복되는 문제가 발생하였고, 이를 막기위해 Flag 설정을 해주었다.
   clearTask 와 noHistory 사용
   
   🥕 위 방식이 깔끔한 방식이라 생각되지는 않아서 설계관점에서 다시 접근해야 하지 않을까? 싶음
```
```KOTLIN
4. release 모드에서 animation효과가 발생하지 않음
   Fragment4의 MPChart 에 animate 효과를 적용하여 그래프가 그려지는 모습을 사용자에게 보여주고 싶었다.
   디버그 모드에서는 잘 되던것이 앱을 배포하기위해 release 모드로 수정하였더니 에니메이션 효과가 일어나지 않았다.
   
   👉 내가사용한 오픈소스 라이브러리인 MPAndroidChart에 들어가서
   Issue 탭을 살펴보니 나와 같은 문제를 겪은 사람들이 있었고, 그곳에서 가이드해준대로
   proguard-rules.pro 에 다음을 적용하여 해결하였다.
   
   -dontwarn com.github.mikephil.**
   -keep class com.github.mikephil.charting.animation.ChartAnimator{*;}
```
```KOTLIN
5. MPAndroidChart 에서는 테마별 차트를 지원해 주지 않았던 문제
  
   👉 TypedValue를 사용하여 해결(구글링을 통해 찾았는데 추가적으로 알아볼 필요 있음!)
   
   * 코드 발췌
   TypedValue typedValue = new TypedValue(); //그래프는 자동으로 테마가 적용되지 않아서 의도적으로 테마 적용
   Resources.Theme theme = getContext().getTheme();
   theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
   @ColorInt int color = typedValue.data;
   set1.setColor(color); /// 테마별 선 색상
   xAxis.setTextColor(color);//테마별 날짜 라벨 색상
   set1.setCircleColor(color); // 차트의 points 점 색 설정

   같은방식으로 bottomnavigation이 테마에따라 변하지 않던문제도 해결
```
#### [2021-05-21] ads - nativetemplates
: deprecated 부분 수정

#### [2021-05-30] Fragment - attach/detach
: 기존에 fragmentTransaction 을 사용할 때, replace로 화면을 전환하게되면 매번 admob 네트워크 요청이 일어나서 버벅거리는 문제가 있었고,  
 이를 해결하기위해 add,hide,show,detach,attach 를 적절히 활용해서 네트워크요청은 앱 실행후 단 한번만 일어나도록 하면서, UI가 업데이트 되도록 했다.
 그런데 이번에 각종 업데이트들을 진행하고나서 보니 attach와 detach 부분이 작동하지 않았다.
 Fragment4 에서 그래프 애니메이션효과를 주면서, 업데이트된 정보를 반영하기 위해 ```detach(fragment4).attach(fragment4) ``` 이런식으로 사용했었다.  
 뭐가 문제일까 계속 살펴보다 detach(fragment3)과 attach(fragment3)이 분리되어있는 부분은 잘 작동하는 것을 보고 위의것도 동일하게 분리시켜주었더니 잘 작동한다.
 
#### [2021-05-31] naming
: 변수명 수정

#### 6월중에 수정해야할 것
💬 1. 지금보니까 쓸데없는 코드들이 너무 많음 뷰가 너무 덕지덕지,, visivility 속성을 남발해서 뷰가 너무 무거움  
💬 2. 모든 로직이 거의다 onCreate안에 있어서 분리좀 시켜줘야겠음.  
💬 3. 확실히 아무것도 모른채로 진행했다보니 고쳐야 할 부분이 한두개가 아님. 

#### 📌 추가할 기능 
: 그래프 형태(곡선, 직선) 선택가능하도록, x축 범위(주간, 월간..) 설정가능하도록  
 Calendar 커스텀 
  
## 개선할점
+ 패턴 적용해보기
+ 테스트코드 작성해보기
+ 리팩토링
