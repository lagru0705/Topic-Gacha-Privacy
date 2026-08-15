package com.lagru.topicgacha.data

import com.lagru.topicgacha.model.Topic
import com.lagru.topicgacha.model.TopicCategory

object LocalTopics {
    private val firstMeetingTopics = listOf(
        topic("fm_01", TopicCategory.FIRST_MEETING, "最近ハマっていることは？"),
        topic("fm_02", TopicCategory.FIRST_MEETING, "休みの日は何をして過ごすことが多い？"),
        topic("fm_03", TopicCategory.FIRST_MEETING, "最近見た映画やドラマは？"),
        topic("fm_04", TopicCategory.FIRST_MEETING, "出身地のおすすめスポットは？"),
        topic("fm_05", TopicCategory.FIRST_MEETING, "学生時代に一番頑張ったことは？"),
        topic("fm_06", TopicCategory.FIRST_MEETING, "仕事以外で得意なことは？"),
        topic("fm_07", TopicCategory.FIRST_MEETING, "最近食べて美味しかったものは？"),
        topic("fm_08", TopicCategory.FIRST_MEETING, "旅行に行くならどこがいい？"),
        topic("fm_09", TopicCategory.FIRST_MEETING, "朝型？夜型？"),
        topic("fm_10", TopicCategory.FIRST_MEETING, "最近買ってよかったものは？"),
        topic("fm_11", TopicCategory.FIRST_MEETING, "子どもの頃の夢は何だった？"),
        topic("fm_12", TopicCategory.FIRST_MEETING, "好きな音楽やアーティストは？"),
        topic("fm_13", TopicCategory.FIRST_MEETING, "最近始めたことはある？"),
        topic("fm_14", TopicCategory.FIRST_MEETING, "ストレス発散法は？"),
        topic("fm_15", TopicCategory.FIRST_MEETING, "好きな季節とその理由は？"),
        topic("fm_16", TopicCategory.FIRST_MEETING, "ペットを飼うなら何がいい？"),
        topic("fm_17", TopicCategory.FIRST_MEETING, "最近読んだ本は？"),
        topic("fm_18", TopicCategory.FIRST_MEETING, "得意料理・よく作る料理は？"),
        topic("fm_19", TopicCategory.FIRST_MEETING, "今の仕事を選んだ理由は？"),
        topic("fm_20", TopicCategory.FIRST_MEETING, "週末の過ごし方の定番は？"),
    )

    private val casualTopics = listOf(
        topic("cs_01", TopicCategory.CASUAL, "今週のハイライトは？"),
        topic("cs_02", TopicCategory.CASUAL, "最近見つけたお気に入りのカフェは？"),
        topic("cs_03", TopicCategory.CASUAL, "今ハマっているゲームやアプリは？"),
        topic("cs_04", TopicCategory.CASUAL, "最近のニュースで気になったことは？"),
        topic("cs_05", TopicCategory.CASUAL, "子どもの頃よく遊んでいたことは？"),
        topic("cs_06", TopicCategory.CASUAL, "好きなスポーツや運動は？"),
        topic("cs_07", TopicCategory.CASUAL, "最近の買い物で一番満足したものは？"),
        topic("cs_08", TopicCategory.CASUAL, "ランチは何派？（和・洋・中など）"),
        topic("cs_09", TopicCategory.CASUAL, "最近のマイブームは？"),
        topic("cs_10", TopicCategory.CASUAL, "YouTubeやSNSでよく見るジャンルは？"),
        topic("cs_11", TopicCategory.CASUAL, "通勤・通学中に何をしている？"),
        topic("cs_12", TopicCategory.CASUAL, "好きなアニメや漫画は？"),
        topic("cs_13", TopicCategory.CASUAL, "最近の天気で感じたことは？"),
        topic("cs_14", TopicCategory.CASUAL, "おすすめのPodcastやラジオは？"),
        topic("cs_15", TopicCategory.CASUAL, "今欲しいものリストの1位は？"),
        topic("cs_16", TopicCategory.CASUAL, "最近の健康習慣は？"),
        topic("cs_17", TopicCategory.CASUAL, "子どもの頃の好きな食べ物は？"),
        topic("cs_18", TopicCategory.CASUAL, "最近の「これ便利！」は？"),
        topic("cs_19", TopicCategory.CASUAL, "好きな香りやアロマは？"),
        topic("cs_20", TopicCategory.CASUAL, "今週末の予定は？"),
    )

    private val drinkingTopics = listOf(
        topic("dr_01", TopicCategory.DRINKING, "今までで一番面白かった飲み会は？"),
        topic("dr_02", TopicCategory.DRINKING, "無人島に1つだけ持っていくなら？"),
        topic("dr_03", TopicCategory.DRINKING, "もし明日仕事が休みになったら何をする？"),
        topic("dr_04", TopicCategory.DRINKING, "1億円当たったら最初に何をする？"),
        topic("dr_05", TopicCategory.DRINKING, "タイムトラベルできるならいつに行きたい？"),
        topic("dr_06", TopicCategory.DRINKING, "超能力が1つだけ使えるなら何がいい？"),
        topic("dr_07", TopicCategory.DRINKING, "人生で一番笑ったエピソードは？"),
        topic("dr_08", TopicCategory.DRINKING, "好きなお酒とその理由は？"),
        topic("dr_09", TopicCategory.DRINKING, "推しの有名人は誰？"),
        topic("dr_10", TopicCategory.DRINKING, "世界一周するならどこから行く？"),
        topic("dr_11", TopicCategory.DRINKING, "ゾンビが出たら最初に何をする？"),
        topic("dr_12", TopicCategory.DRINKING, "人生で一番の失敗談は？"),
        topic("dr_13", TopicCategory.DRINKING, "好きな居酒屋メニューは？"),
        topic("dr_14", TopicCategory.DRINKING, "もし1週間だけ別の人生を送れるなら？"),
        topic("dr_15", TopicCategory.DRINKING, "学生時代の黒歴史エピソードは？"),
        topic("dr_16", TopicCategory.DRINKING, "理想のマイホームはどんな感じ？"),
        topic("dr_17", TopicCategory.DRINKING, "人生で一番嬉しかったサプライズは？"),
        topic("dr_18", TopicCategory.DRINKING, "好きな二次会の内容は？"),
        topic("dr_19", TopicCategory.DRINKING, "もし芸能人と1日過ごせるなら誰？"),
        topic("dr_20", TopicCategory.DRINKING, "今の自分に10年前の自分から言いたいことは？"),
    )

    private val loveTopics = listOf(
        topic("lv_01", TopicCategory.LOVE, "好きなタイプは？"),
        topic("lv_02", TopicCategory.LOVE, "理想のデートは？"),
        topic("lv_03", TopicCategory.LOVE, "一目惚れしたことはある？"),
        topic("lv_04", TopicCategory.LOVE, "恋愛で大切にしていることは？"),
        topic("lv_05", TopicCategory.LOVE, "告白されたら嬉しい場所は？"),
        topic("lv_06", TopicCategory.LOVE, "付き合う前に確認したいことは？"),
        topic("lv_07", TopicCategory.LOVE, "好きな人のどんなところに惹かれる？"),
        topic("lv_08", TopicCategory.LOVE, "理想のカップルの過ごし方は？"),
        topic("lv_09", TopicCategory.LOVE, "恋愛映画やドラマのおすすめは？"),
        topic("lv_10", TopicCategory.LOVE, "長続きする恋愛の秘訣は？"),
        topic("lv_11", TopicCategory.LOVE, "デートで行きたい場所は？"),
        topic("lv_12", TopicCategory.LOVE, "恋人にしてほしいことは？"),
        topic("lv_13", TopicCategory.LOVE, "恋愛で譲れない条件は？"),
        topic("lv_14", TopicCategory.LOVE, "好きな人への連絡頻度の理想は？"),
        topic("lv_15", TopicCategory.LOVE, "記念日は大切にしたい派？"),
        topic("lv_16", TopicCategory.LOVE, "恋愛で学んだことは？"),
        topic("lv_17", TopicCategory.LOVE, "理想のプロポーズは？"),
        topic("lv_18", TopicCategory.LOVE, "付き合ったら一緒にやりたいことは？"),
        topic("lv_19", TopicCategory.LOVE, "恋愛相談をよくされる？"),
        topic("lv_20", TopicCategory.LOVE, "好きな人と話すときのテーマは？"),
    )

    private val deepTopics = listOf(
        topic("dp_01", TopicCategory.DEEP, "人生で一番大切にしている価値観は？"),
        topic("dp_02", TopicCategory.DEEP, "10年後の自分はどうなっていたい？"),
        topic("dp_03", TopicCategory.DEEP, "幸せとは何だと思う？"),
        topic("dp_04", TopicCategory.DEEP, "人生で後悔していることは？"),
        topic("dp_05", TopicCategory.DEEP, "自分を成長させてくれた経験は？"),
        topic("dp_06", TopicCategory.DEEP, "仕事とプライベート、どちらを優先する？"),
        topic("dp_07", TopicCategory.DEEP, "人間関係で大切にしていることは？"),
        topic("dp_08", TopicCategory.DEEP, "自分の強みと弱みは？"),
        topic("dp_09", TopicCategory.DEEP, "人生の転機となった出来事は？"),
        topic("dp_10", TopicCategory.DEEP, "お金と時間、どちらが大切？"),
        topic("dp_11", TopicCategory.DEEP, "理想のライフスタイルは？"),
        topic("dp_12", TopicCategory.DEEP, "自分にとっての成功とは？"),
        topic("dp_13", TopicCategory.DEEP, "最近考えている人生の選択は？"),
        topic("dp_14", TopicCategory.DEEP, "大切にしている習慣は？"),
        topic("dp_15", TopicCategory.DEEP, "自分を変えてくれた人は？"),
        topic("dp_16", TopicCategory.DEEP, "恐れていることは？"),
        topic("dp_17", TopicCategory.DEEP, "死ぬ前にやっておきたいことは？"),
        topic("dp_18", TopicCategory.DEEP, "自分らしさとは何だと思う？"),
        topic("dp_19", TopicCategory.DEEP, "社会に対して感じていることは？"),
        topic("dp_20", TopicCategory.DEEP, "今の自分に必要だと思うものは？"),
    )

    val all: List<Topic> = firstMeetingTopics +
        casualTopics +
        drinkingTopics +
        loveTopics +
        deepTopics

    private fun topic(id: String, category: TopicCategory, text: String): Topic =
        Topic(id = id, category = category, text = text)
}
