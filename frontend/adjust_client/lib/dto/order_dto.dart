import 'package:adjust_client/dto/cart_dto.dart';
import 'package:adjust_client/model/order.dart';
import 'package:json_annotation/json_annotation.dart';

part 'order_dto.g.dart';

@JsonSerializable()
class OrderDTO extends Order {
  CartDTO cart;
  OrderDTO(
      int id,
      String username,
      String firstName,
      String lastName,
      String phoneNumber,
      String email,
      String country,
      String state,
      String city,
      String address1,
      String address2,
      int cartId,
      this.cart)
      : super(id, username, firstName, lastName, phoneNumber, email, country,
            state, city, address1, address2, cartId);

  factory OrderDTO.fromJson(Map<String, dynamic> json) =>
      _$OrderDTOFromJson(json);

  Map<String, dynamic> toJson() => _$OrderDTOToJson(this);
}
