betterkoreankotlin
==================

![betterkoreankotlin](https://raw.githubusercontent.com/n42corp/betterkorean/master/hm.jpg)

betterkoreankotlin 은 android(Java) 버전 https://github.com/n42corp/betterkorean 을 Kotlin 버전으로 포팅한 라이브러리 입니다.
특정단어에 대한 조사를 자연스럽게 연결해주는 역할을 하고 있어요. 특정 단어를 서버에서 받아오거나 유저의 입력을 받는 경우 이 단어에 대한 조사를 미리 정하지 못하기 때문에 "은(는)" 이런식으로 보기 안좋게 개발을 해야하는 문제를 해결하고 싶어서 포팅을 하게 됐습니다. :)

예) 사과**가** 맜있다, 아버지**가** 바나나**를** 드신다.

은는 이가 을를 와과 으로(로) 아야 등의 조사를 지원합니다.
Kotlin 의 string extension 을 이용해서 라이브러리 install 후에 그냥 일반 스트링에 2가지 추가된 메소드만 사용하면 됩니다.

사용법
======

* "아파트".getJosa(JosaType.Type_와과) ==> "와" 리턴
* "아파트".appendJosa(JosaType.Type_와과) ==> "아파트와" 리턴

```Kotlin
val sample_1 = "아빠".appendJosa(JosaType.Type_이가) + " 방으로 들어가신다."
//결과: 아빠가 방으로 들어가신다.

val sample_2 = "집".appendJosa(JosaType.Type_으로_로) + " 가야지"
//결과: 집으로 가야지

val region = "서초1동"
val regionJosa = region.getJosa(JosaType.Type_을를)

val sample_3 = region.appendJosa(JosaType.Type_을를) + " 내 지역으로 설정할까요? (선택된 조사: $regionJosa)"
//결과: 서초1동을 내 지역으로 설정할까요? (선택된 조사: 을)
```

* [예제보기](https://github.com/n42corp/betterkoreankotlin/blob/master/app/src/main/java/com/daangn/www/betterkoreankotlinsample/MainActivity.kt).

Install
=======

Gradle: project 레벨 build.gradle 파일
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Gradle: app 레벨 build.gradle 파일
```groovy
dependencies {
	compile 'com.github.n42corp:betterkoreankotlin:0.9.4'
}
```

제공하는 인터페이스
===================

```Kotlin
fun String.getJosa(josaType: JosaType): String //특정 단어에 대한 조사만 리턴 
fun String.appendJosa(josaType: JosaType) : String //특정 단어 포함 조사를 붙여 리턴

//제공하는 조사 타입
enum class JosaType {
    Type_은는, Type_이가, Type_을를, Type_으로_로, Type_아야, Type_와과
}
```
