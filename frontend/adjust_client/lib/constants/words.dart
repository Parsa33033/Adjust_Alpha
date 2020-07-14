import 'package:adjust_client/model/client.dart';
import 'package:enum_to_string/enum_to_string.dart';

final String RAMSARI = "دکتر سوسن رامسری";

final String LOGIN = "ورود";
final String REGISTER = "ثبت نام";
final String FORGOTTEN_PASS = "رمز عبور را فراموش کردم!";

final String EMAIL = "ایمیل";
final String PASSWORD = "رمز عبور";
final String PASSWORD_CONFIRM = "تکرار رمز عبور";

final String EMPTY = "لطفا پر کنید";
final String WRONG_EMAIL = "ایمیل اشتباه وارد شده";
final String WRONG_PASSWORD =
    "رمز عبور باید دارای حرف و عدد و بیش از 6 حرف باشد";
final String PASS_NOT_MATCH = "رمز عبور و تکرار آن یکسان نمیباشند";
final String REGISTRATION_FAILED =
    "خطا در ثبت نام! لطفا از صحت عدم ثبت نام مجدد و اتصال اینترنت خود اطمینان حاصل فرمایید.";
final String LOGIN_FAILED =
    "خطا در ورود! لطفا از صحت نام کاربری و رمز عبور وارد شده اطمینان حاصل فرمایید.";
final String RECOVER_PASSWORD =
    " ایمیل خود را که با آن ثبت نام کرده اید وارد کرده و دکمه ی تایید را فشار دهید، سپس برای برای تغییر رمز عبور خود به ایمیل خود مراجعه کنید.";
final String SUCCESS = "عملیات با موفقیت انجام شد!";
final String FAILURE = "خطا در پردازش!";
final String SET_TO_DEFAULT = "برگشت به حالت پیش فرض";
final String CART_EMPTY = "سبد خرید شما خالی میباشد.";
final String SURE_TO_EXIT = "با خروج از برنامه برای شروع مجدد باید ایمیل و رمز عبور خود را مجدداً وارد کنید. آیا مایل به خروج از برنامه میباشید؟";
final String NOT_ENOUGHT_TOKEN = "مقدار توکن کافی نمیباشد";
final String CLIENT_HAS_TUTORIAL = "شما این آموزش را دارید";
final String SURE_WITH_DECISION = "آیا از انتخاب خود اطمینان دارید؟";

final String BACK = "برگرد";
final String OK = "تایید";
final String CANCEL = "لغو";
final String LOGOUT = "خروج";
final String PROFILE = "پروفایل";
final String BUY = "پرداخت";

final String BIRTH = "تاریخ تولد";
final String FIRST_NAME = "نام";
final String LAST_NAME = "نام خانوادگی";
final String GENDER = "جنسیت";
final String UPDATE = "بروزرسانی";
final String DEFAULT = "پیش فرض";
final Map<String, String> GENDER_LIST = Map<String, String>()
  ..putIfAbsent(EnumToString.parse(Gender.FEMALE), () => "زن")
  ..putIfAbsent(EnumToString.parse(Gender.MALE), () => "مرد");


final String PHONE_NUMBER = "شماره تلفن";
final String COUNTRY = "کشور";
final String STATE = "استان";
final String CITY = "شهر";
final String ADDRESS = "آدرس";

final String ORDER = "سفارش";
