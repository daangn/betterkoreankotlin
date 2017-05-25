package com.daangn.www.betterkoreankotlinsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.daangn.www.betterkoreankotlin.JosaType
import kotlinx.android.synthetic.main.activity_main.*
import strings.appendJosa
import strings.getJosa

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sample_1 = "아빠".appendJosa(JosaType.Type_이가) + " 방으로 들어가신다."
        val sample_2 = "집".appendJosa(JosaType.Type_으로_로) + " 가야지"

        val region = "서초1동"
        val regionJosa = region.getJosa(JosaType.Type_을를)

        val sample_3 = region.appendJosa(JosaType.Type_을를) + " 내 지역으로 설정할까요? (선택된 조사: $regionJosa)"
        val sample_4 =
                "연필".appendJosa(JosaType.Type_와과) + " " +
                "지우개".appendJosa(JosaType.Type_와과) + " " +
                "자".appendJosa(JosaType.Type_을를) + " 주세요"

        josaExample1.text = sample_1
        josaExample2.text = sample_2
        josaExample3.text = sample_3
        josaExample4.text = sample_4
    }
}
