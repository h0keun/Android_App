# My_First_App - 'BE BETTER'
 [Google Play Store](https://play.google.com/store/apps/details?id=com.growth.graphh)

## 소개
'BE BETTER'은 자기관리를 돕기위한 앱이다.  
총 4개의 탭(일정, 어제, 오늘, 성장그래프)을 가지고 있고 각각에 탭에서는 다음과 같은 기능을 담당한다.

+ 일정  
 달력에 계획을 작성할 수 있다. 작성된 계획은 날짜에 맞추어 각각 '어제' 와 '오늘' 탭에 보여지게 된다.  
+ 어제  
 어제 계획했던 일과 어제남겼던 메시지를 확일 할 수 있다. 또한 짧은 명언을 통해 사용자에게 동기를 부여한다.  
+ 오늘  
 오늘 계획한 일을 확인하고, 하루를 마무리 하며 나에게 메시지를 남기거나 그날의 펑점을 매길 수 있다.  
+ 성장그래프  
오늘탭에서 매긴 평점을 통해 그래프를 그린다. 사용자는 그래프를 확인하고 특정날짜의 계획과 자신에게 남긴 메시지를 확인 할 수 있다. 
 
## 개발배경
작심삼일도 3일에 한번씩 하면 된다는 말이 있다. 어떠한 계획을 세우고 마음을 다잡으면, 그게 채 3일을 못넘기니  
3일에 한번씩 마음을 다잡자는 의미인데, 어떻게해야 매번 마음을 다잡게 할 수 있을까를 고민해보았다. 그러다 문득  
최근들어 주식열풍이 굉장히 뜨겁다고 뉴스에서 보도한것이 생각이 났고, 매일 주가차트를 들여다보는 사람들의 모습이 떠올랐다.  
그렇게 생각해 낸 것이 자신의 계획과 성취도를 주가차트 처럼 매일매일 변화하는 그래프를 통해 보여주자는 것이었다.  
사람들이 매일 주가차트를 들여다보고 때로는 실망하고 때로는 기뻐하는것 처럼  
자신의 성장그래프를 들여다보고 자신에게 실망하고 뿌듯해한다면 이를통해 마음을 다잡을 수 있겠다는 생각을 하였다.

## 상세설명
1. 앱 최초실행을 감지하기위해 임의의 변수 초기값을 true로 두고 if문을 통해 최초실행날짜를 SharedPreferences로 저장하였다.  
 최초실행날짜 저장 이후에 임의의 변수를 false로 저장하여, 이후의 실행시 if문을 다시 읽지 않도록 하였다.  
 ``` JAVA
pref_start = getSharedPreferences("pref_start", MODE_PRIVATE);
        boolean isFirstRun = pref_start.getBoolean("isFirstRun", true);
        if (isFirstRun == true) {
            ...
            실행날짜 day_first를 millis로 변환하여 SharedPreferences로 저장
            ...
            pref_start.edit().putBoolean("isFirstRun", false).apply();
        }
```
2. 저장된 최초날짜와 앱 실행시의 날짜를 millis 로 반환하여 두 날짜간의 차를 계산하고  
 이를통해 하루가 지날때마다 그래프의 x축이 증가하고 그래프를 그린지 며칠이 되었는지를 표시했다.
  ``` JAVA
...   
day_count = counting.getTimeInMillis() / 86400000; //앱 실행하는 날짜 millis단위로 변환(최초 실행날짜 아님)
...
count = day_count - day_first + 1; // 앱 실행하는 오늘날짜 - 앱 최초 처음 실행한 날짜 = d-day
```
3. splash테마를 지정하여 앱 실행시 제일먼저 화면에 나오도록 하였다.
```JAVA
protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.Theme_Graphh);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
```
4. Bottomnavigation을 이용하여 MainActivity 1개에 Fragment 4개가 연결된 방식으로 구성하였다.
>+ Fragment_1(일정) 에서는 calendarView를 통해 달력을 보여주고 onSelectedDayChange와 FileInputStream을 통해  
"2020년 02월 01일.txt"와 같은 정해진 형식으로 파일을 저장하였다.
```JAVA
calendarView.setOnDateChangeListener(...{//달력의 날짜를 클릭하였을 때 선택한 날짜를 감지
    @Override
    public void onSelectedDayChange(...){
        ...
        diaryTextView.setText(String.format("%d년 %d월 %d일",year,month+1,dayOfMonth));
        ...
        checkDay(year,month,dayOfMonth);
    }
});

...

public void checkDay(int cYear,int cMonth,int cDay){//선택한 날짜를 파일명으로 생성하기 위함
    fname=""+cYear+"년 "+(cMonth+1)+"월 "+cDay+"일.txt";//저장할 파일 이름설정
    FileInputStream fis=null;
    try{
        fis=getActivity().openFileInput(fname);
        byte[] fileData=new byte[fis.available()];
        fis.read(fileData);
        fis.close();
        str=new String(fileData);
        ...
     }catch (Exception e){
         e.printStackTrace();
     }
 }
 
...
```
>+ Fragment_2(어제) 에서는 SimpleDateFormat을 "yyyy년 mm월 dd일" 처럼 일정탭에서 저장한 파일명과 동일한 형식으로 출력하고  
Calendar.getInstance();에서 Date를 -1 하여 어제의 날짜에 해당하는 파일명을 읽어오도록 하였다.  
사용자에게 보여질 명언은 assets폴더에 저장되어있는 파일을 BufferedReader을 통해 불러왔으며 readLine();  
을 통해 해당날짜에 해당하는 줄을 읽어오도록 하였다.
```JAVA
is = am.open("famous_saying"); //assets폴더의 파일명
BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int i = 1; i < day_famous; i++) { //day_famous는 SimpleDateFormat에서 받은 날짜(dd)
                br.readLine();
            }
```
>+ Fragment_3(오늘) 에서는 위와 동일한 방법으로 오늘날짜에 해당하는 파일명을 읽도록 하였고,  
그날의 메시지는 SimpleDateFormat을 위와는 다른형태(ex)"yyyy년mm월dd일")로 저장하여 일정과 메시지가 중복되지 않도록 하였다.  
그날의 평점은 RatingBar를 통해 별의 갯수를 읽어서 오늘이 해당하는날짜(x축)의 값(y축)이 변하도록 하였다.
또한 그날그날의 평점(=u)이 배열로 저장되어 그래프를 그려야 하기 때문에 토큰을 이용해 값을 구분짓고 저장하였다.
```JAVA
SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        ss = prefs.getString("name", "");
        //u를 배열로 저장하기 위함, 그래프 그릴 때 배열값이 필요, 토큰을 이용해 배열값을 구분짓고 저장함
        StringTokenizer st = new StringTokenizer(ss, ",");
        while (st.hasMoreTokens()) {
            list.add(Float.parseFloat(st.nextToken()));
        } 
        ...
        ...
        list.add(0, u);
        ss = "";
        for (Float i : list) {
            ss += i + ",";
        }
        SharedPreferences.Editor editors = prefs.edit();
        editors.putString("name", ss);
        editors.commit();
```
>+ Fragment_4(성장그래프) 에서는 이전탭들에서 작성한 정보를 그래프를 통해 보여지는 공간이다.  
그래프는 오픈소스인 [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)를 통해 그렸고,  
DatePickerDialog를 통해 원하는 날짜에대한 정보(계획한 일, 나에게남긴 메시지)를 확인할 수 있게 하였다.  
일정탭에서 저장한 d-day를 통해 x축의 갯수를 지정하였고 오늘탭에서 저장한 배열값을 뿌려줌으로써 그렸다.
```JAVA
...
ArrayList<Entry> values = new ArrayList<>();
       ...
       for (int i = 2; i <k_count +1 ; i++) {
           values.add(new Entry(i, xtx.get(k_count-i))); //오늘탭에서 저장한 배열을 읽는 부분
       }
       values.add(new Entry(k_count+1,u)); //오늘에 해당하는 그래프값(변화되는 u값이 보여진다.)
       ...
       ...
       final ArrayList<String> xLabel = new ArrayList<>(); //x축 라벨설정
       calendar.add(calendar.DATE, -(k_count) - 2);
       for (int j = 0; j <= k_count + 1; j++) {
           calendar.add(calendar.DAY_OF_YEAR, 1);
           Date date = calendar.getTime();
           String txtDate = mFormat.format(date);
           xLabel.add(txtDate);
       }
       xLabel.set(k_count + 1, "오늘");
       return xLabel.get((int) value);
```        


## 스크린샷
<img src="https://user-images.githubusercontent.com/63087903/106424399-7463a180-64a5-11eb-8e07-f819d2736a03.jpg" width="200" height="400"> <img src="https://user-images.githubusercontent.com/63087903/106424411-7af21900-64a5-11eb-9d60-e81681599926.jpg" width="200" height="400"> <img src="https://user-images.githubusercontent.com/63087903/106424419-7ded0980-64a5-11eb-8c97-3e4b4ff21880.jpg" width="200" height="400"> <img src="https://user-images.githubusercontent.com/63087903/106424428-81809080-64a5-11eb-95c8-8187ead22b9c.jpg" width="200" height="400">

<img src="https://user-images.githubusercontent.com/63087903/106424406-775e9200-64a5-11eb-88e7-941fda0bc060.jpg" width="200" height="400"> <img src="https://user-images.githubusercontent.com/63087903/106424415-7c234600-64a5-11eb-9693-cd4dc3d70a21.jpg" width="200" height="400"> <img src="https://user-images.githubusercontent.com/63087903/106424423-7f1e3680-64a5-11eb-9887-a8c8c36dcd9c.jpg" width="200" height="400"> <img src="https://user-images.githubusercontent.com/63087903/106424432-834a5400-64a5-11eb-9529-dae8981f6b4b.jpg" width="200" height="400">


## 사용한 오픈소스
[MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)

## 저작권
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
