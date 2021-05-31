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
 
## 개발배경
작심삼일도 3일에 한번씩 하면 된다는 말이 있다. 어떠한 계획을 세우고 마음을 다잡으면, 그게 채 3일을 못넘기니  
3일에 한번씩 마음을 다잡자는 의미인데, 어떻게해야 매번 마음을 다잡게 할 수 있을까를 고민해보았다. 그러다 문득  
최근들어 주식열풍이 굉장히 뜨겁다고 뉴스에서 보도한것이 생각이 났고, 매일 주가차트를 들여다보는 사람들의 모습이 떠올랐다.  
그렇게 생각해 낸 것이 자신의 계획과 성취도를 주가차트 처럼 매일매일 변화하는 그래프를 통해 보여주자는 것이었다.  
사람들이 매일 주가차트를 들여다보고 때로는 실망하고 때로는 기뻐하는것 처럼  
자신의 성장그래프를 들여다보고 자신에게 실망하고 뿌듯해한다면 이를통해 마음을 다잡을 수 있겠다는 생각을 하였다.

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

#### [2021-05-21] ads - nativetemplates
: deprecated 부분 수정

#### [2021-05-30] Fragment - attach/detach
: 기존에 fragmentTransaction 을 사용할 때, replace로 화면을 전환하게되면 매번 admob 네트워크 요청이 일어나서 버벅거리는 문제가 있었고,  
 이를 해결하기위해 add,hide,show,detach,attach 를 적절히 활용해서 네트워크요청은 앱 실행후 단 한번만 일어나도록 하면서, UI가 업데이트 되도록 했다.
 그런데 이번에 각종 업데이트들을 진행하고나서 보니 attach와 detach 부분이 작동하지 않았다.
 Fragment4 에서 그래프 애니메이션효과를 주면서, 업데이트된 정보를 반영하기 위해 ```detach(fragment4).attach(fragment4) ``` 이런식으로 사용했었다.  
 뭐가 문제일까 계속 살펴보다 detach(fragment3)과 attach(fragment3)이 분리되어있는 부분은 잘 작동하는 것을 보고 위의것도 동일하게 분리시켜주었더니 잘 작동한다.
 왜 그런건지 이유는 : 
 
#### [2021-05-31] naming
: 변수명 수정

지금보니까 쓸데없는 코드들이 너무 많음 빨리 수정하자

📌 추가할 기능 : 그래프 형태(곡선, 직선) 선택가능하도록, x축 범위 설정가능하도록 
  
## 개선하며 공부할 점
+ 모든 로직이 거의다 onCreate안에 있어서 분리좀 시켜줘야겟다
+ 패턴 적용해보기 - 공부좀 더하고
+ 테스트코드 작성해보기 - 공부좀 더하고
+ 리팩토링 - 공부좀 더하고
