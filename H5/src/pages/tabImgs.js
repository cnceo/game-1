// 清推、混推、大九TAB图片
import img1 from '../assets/imgs/img_Rules_clearpush.png'
import img2 from '../assets/imgs/img_Rules_mixedpush.png'
import img3 from '../assets/imgs/img_Rules_bignine.png'
import img1Hover from '../assets/imgs/img_Rules_clearpush_hover.png'
import img2Hover from '../assets/imgs/img_Rules_mixedpush_hover.png'
import img3Hover from '../assets/imgs/img_Rules_bignine_hover.png'

// 游戏和系统设置TAB图片
import tab1 from '../assets/imgs/img_Setup_System.png'
import tab2 from '../assets/imgs/img_Setup_Gamesettings.png'
import tab1Hover from '../assets/imgs/img_Setup_System_hover.png'
import tab2Hover from '../assets/imgs/img_Setup_Gamesettings_hover.png'

// 设置游戏分数
import score1 from '../assets/imgs/img_Create_20points.png'
import score2 from '../assets/imgs/img_Create_50points.png'
import score3 from '../assets/imgs/img_Create_100points.png'
import score4 from '../assets/imgs/img_Create_200points.png'

// 设置游戏局数
import table1 from '../assets/imgs/img_Create_10Ju.png'
import table2 from '../assets/imgs/img_Create_30Ju.png'

// 桌面和游戏设置背景图片
import card1 from '../assets/imgs/card1.png'
import card2 from '../assets/imgs/card2.png'
import card3 from '../assets/imgs/card3.png'
import card4 from '../assets/imgs/img_Setup_Card1.png'
import card5 from '../assets/imgs/img_Setup_Card2.png'
import card6 from '../assets/imgs/img_Setup_Card3.png'

// 加入房间数字图片
import num1 from '../assets/imgs/img_Join_1.png'
import num2 from '../assets/imgs/img_Join_2.png'
import num3 from '../assets/imgs/img_Join_3.png'
import num4 from '../assets/imgs/img_Join_4.png'
import num5 from '../assets/imgs/img_Join_5.png'
import num6 from '../assets/imgs/img_Join_6.png'
import num7 from '../assets/imgs/img_Join_7.png'
import num8 from '../assets/imgs/img_Join_8.png'
import num9 from '../assets/imgs/img_Join_9.png'
import numr from '../assets/imgs/img_Join_heavycount.png'
import num0 from '../assets/imgs/img_Join_0.png'
import numd from '../assets/imgs/img_Join_Delete.png'

// 出、天、坎拦门
import cm from '../assets/imgs/img-Stoppingdoor-goout.png'
import tm from '../assets/imgs/img-Stoppingdoor-tianmen.png'
import km from '../assets/imgs/img-Stoppingdoor-kanmen.png'

// 出、天、坎背景图
import cbg from '../assets/imgs/img_Bet_chu.png'
import tbg from '../assets/imgs/img_Bet_tian.png'
import kbg from '../assets/imgs/img_Bet_kan.png'

// 赢、输、和图
import win from '../assets/imgs/img-Chips-win.png'
import lose from '../assets/imgs/img-Chips-shu.png'
import tie from '../assets/imgs/img-Chips-he.png'

// 输赢硬币图
import one from '../assets/imgs/img-Chips-one.png'
import five from '../assets/imgs/img-Chips-five.png'
import ten from '../assets/imgs/img-Chips-ten.png'

// 下注硬币图
import tone from '../assets/imgs/img_Bet_chouma1.png'
import tfive from '../assets/imgs/img_Bet_chouma5.png'
import tten from '../assets/imgs/img_Bet_chouma10.png'
import tmax20 from '../assets/imgs/img_max_score_20.jpg'
import tmax50 from '../assets/imgs/img_max_score_50.jpg'
import tmax100 from '../assets/imgs/img_max_score_100.jpg'
import tmax200 from '../assets/imgs/img_max_score_200.jpg'
const tabsImgs = {
  imgs: [img1, img2, img3],
  hoverImgs: [img1Hover, img2Hover, img3Hover],
  tabs: [tab1, tab2],
  hoverTabs: [tab1Hover, tab2Hover],
  scores: [score1, score2, score3, score4],
  tables: [table1, table2],
  cards: [card1, card2, card3, card4, card5, card6],
  nums: [
    [{img: num1, num: 1}, {img: num2, num: 2}, {img: num3, num: 3}],
    [{img: num4, num: 4}, {img: num5, num: 5}, {img: num6, num: 6}],
    [{img: num7, num: 7}, {img: num8, num: 8}, {img: num9, num: 9}],
    [{img: numr, num: '重输'}, {img: num0, num: 0}, {img: numd, num: '删除'}]
  ],
  mtype: [cm, tm, km],
  tbg: [cbg, tbg, kbg],
  result: [win, lose, tie],
  coins: [five, one, ten, five, ten, one, five, one],
  lowz: [{
    img: tone,
    value: 1
  },
  {
    img: tfive,
    value: 5
  },
  {
    img: tten,
    value: 10
  }],
  lowzMax: [
    {
      img: tmax20,
      value: 20
    },
    {
      img: tmax50,
      value: 50
    },
    {
      img: tmax100,
      value: 100
    },
    {
      img: tmax200,
      value: 200
    }
  ]
}

export default tabsImgs
