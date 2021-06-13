# かけいノート
スマホで家計簿を管理できるAndroidアプリを開発します。

## 開発環境
- 言語：Kotlin 1.5.10
- IDE：Android Studio 4.2.1

## 画面
以下は、2021年6月13日現在の画面イメージです。

| 家計簿画面 | 新規書き込み画面 | 種類選択画面（収入） | 種類選択画面（支出） |
| :--- | :--- | :--- | :--- |
| <img src="img/img_household_account_book.png" width="240"> | <img src="img/img_new_write.png" width="240"> | <img src="img/img_type_select_bottom_sheet_income.png" width="240"> | <img src="img/img_type_select_bottom_sheet_expense.png" width="240"> |

## 導入技術
### Kotlin
- Coroutines

### Gradle
- Kotlin DSL

### Android Jetpack
- ConstraintLayout
- RecyclerView
- LiveData
- ViewModel
- View Binding
- Data Binding
- Room

### その他
- [Material Components for Android](https://github.com/material-components/material-components-android)
- [Flipper](https://fbflipper.com/)
- [LeakCanary](https://square.github.io/leakcanary/)
- [Logger](https://github.com/orhanobut/logger)

## ToDoリスト
### アプリ機能関連
- 家計簿表示の月別表示化
  + ViewPager2による月別スライド表示対応
- 検索画面の追加
- 円グラフ画面の追加（優先度低）
- BottomNavigationの導入
- ショートカットの導入
- 音声入力による家計簿書き込み機能の追加（優先度低）
  + 参考1：[音声認識 RecognizerIntent](https://akira-watson.com/android/recognizerintent.html)
  + 参考2：[音声入力はわりと簡単に実装できる（ Speech Recognizer ）](https://kaleidot.net/android-音声入力はわりと簡単に実装できる-speech-recognizer-d9fc47de4ed1)
- ファイル出力の追加
  + csvファイル出力
  + excelファイル出力（優先度低）
    * 候補：[Apache POI](https://poi.apache.org/)
- 書き込み忘れ防止機能の追加
  + 一定期間書き込みがないときに通知を送信します。
- ダークモード対応
- アプリアイコンの作成
- 家計簿データのバックアップ機能の追加（優先度低）
  + Google ドライブなどのクラウドストレージにバックアップを保存します。
- 家計簿データの暗号化（優先度低）
  + 候補：[SQLCipher](https://github.com/sqlcipher/android-database-sqlcipher)

### 開発環境関連
- [Dagger](https://github.com/google/dagger)の導入
- ユニットテストの導入
- マルチモジュールの導入によるビルド高速化
  + モジュール構成がわかるイメージも作成します。
- アプリデザインの作成
  + ツール候補1：[Adobe XD](https://www.adobe.com/jp/products/xd.html)
  + ツール候補2：[Figma](https://www.figma.com/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)の導入（優先度低）
  + おそらく部分的な導入になると考えられます。
- デバッグビルドのアプリアイコンへのdebugリボンの追加（優先度低）
  + 候補：[Easylauncher gradle plugin for Android](https://github.com/akaita/easylauncher-gradle-plugin)
- Firebase Crashlyticsの導入
- Google Analytics for Firebaseの導入（優先度低）
- 開発版アプリの配信機能の導入（優先度低）
  + 候補1：Google Play Console
  + 候補2：[DeployGate](https://deploygate.com/?locale=ja)
