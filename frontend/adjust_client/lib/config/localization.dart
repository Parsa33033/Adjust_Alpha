import 'package:intl/intl.dart';

DateTime getFormattedDateTime(String dateTime) {
  return DateFormat('yyyy/MM/dd').parse(dateTime);
}