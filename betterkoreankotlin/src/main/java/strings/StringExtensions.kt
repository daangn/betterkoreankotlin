package strings

import com.daangn.www.betterkoreankotlin.Analyzer
import com.daangn.www.betterkoreankotlin.JosaType

/**
 * Created by kai on 2017. 5. 25..
 */

fun String.getJosa(josaType: JosaType): String {
    val analyzer = Analyzer(this)

    return when (josaType) {
        JosaType.Type_이가, JosaType.Type_은는, JosaType.Type_을를, JosaType.Type_아야, JosaType.Type_와과 -> analyzer.getJosa(josaType)
        JosaType.Type_으로_로 -> analyzer.getJosa_으_으로()
    }
}

fun String.appendJosa(josaType: JosaType) : String = this + getJosa(josaType)
