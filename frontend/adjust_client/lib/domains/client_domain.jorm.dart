// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'client_domain.dart';

// **************************************************************************
// BeanGenerator
// **************************************************************************

abstract class _ClientDomainBean implements Bean<ClientDomain> {
  final id = IntField('id');
  final username = StrField('username');
  final firstName = StrField('first_name');
  final lastName = StrField('last_name');
  final birthDate = DateTimeField('birth_date');
  final age = IntField('age');
  final gender = StrField('gender');
  final token = DoubleField('token');
  final score = DoubleField('score');
  final image = StrField('image');
  final imageContentType = StrField('image_content_type');
  Map<String, Field> _fields;
  Map<String, Field> get fields => _fields ??= {
        id.name: id,
        username.name: username,
        firstName.name: firstName,
        lastName.name: lastName,
        birthDate.name: birthDate,
        age.name: age,
        gender.name: gender,
        token.name: token,
        score.name: score,
        image.name: image,
        imageContentType.name: imageContentType,
      };
  ClientDomain fromMap(Map map) {
    ClientDomain model = ClientDomain();
    model.id = adapter.parseValue(map['id']);
    model.username = adapter.parseValue(map['username']);
    model.firstName = adapter.parseValue(map['first_name']);
    model.lastName = adapter.parseValue(map['last_name']);
    model.birthDate = adapter.parseValue(map['birth_date']);
    model.age = adapter.parseValue(map['age']);
    model.gender = adapter.parseValue(map['gender']);
    model.token = adapter.parseValue(map['token']);
    model.score = adapter.parseValue(map['score']);
    model.image = adapter.parseValue(map['image']);
    model.imageContentType = adapter.parseValue(map['image_content_type']);

    return model;
  }

  List<SetColumn> toSetColumns(ClientDomain model,
      {bool update = false, Set<String> only, bool onlyNonNull = false}) {
    List<SetColumn> ret = [];

    if (only == null && !onlyNonNull) {
      ret.add(id.set(model.id));
      ret.add(username.set(model.username));
      ret.add(firstName.set(model.firstName));
      ret.add(lastName.set(model.lastName));
      ret.add(birthDate.set(model.birthDate));
      ret.add(age.set(model.age));
      ret.add(gender.set(model.gender));
      ret.add(token.set(model.token));
      ret.add(score.set(model.score));
      ret.add(image.set(model.image));
      ret.add(imageContentType.set(model.imageContentType));
    } else if (only != null) {
      if (only.contains(id.name)) ret.add(id.set(model.id));
      if (only.contains(username.name)) ret.add(username.set(model.username));
      if (only.contains(firstName.name))
        ret.add(firstName.set(model.firstName));
      if (only.contains(lastName.name)) ret.add(lastName.set(model.lastName));
      if (only.contains(birthDate.name))
        ret.add(birthDate.set(model.birthDate));
      if (only.contains(age.name)) ret.add(age.set(model.age));
      if (only.contains(gender.name)) ret.add(gender.set(model.gender));
      if (only.contains(token.name)) ret.add(token.set(model.token));
      if (only.contains(score.name)) ret.add(score.set(model.score));
      if (only.contains(image.name)) ret.add(image.set(model.image));
      if (only.contains(imageContentType.name))
        ret.add(imageContentType.set(model.imageContentType));
    } else /* if (onlyNonNull) */ {
      if (model.id != null) {
        ret.add(id.set(model.id));
      }
      if (model.username != null) {
        ret.add(username.set(model.username));
      }
      if (model.firstName != null) {
        ret.add(firstName.set(model.firstName));
      }
      if (model.lastName != null) {
        ret.add(lastName.set(model.lastName));
      }
      if (model.birthDate != null) {
        ret.add(birthDate.set(model.birthDate));
      }
      if (model.age != null) {
        ret.add(age.set(model.age));
      }
      if (model.gender != null) {
        ret.add(gender.set(model.gender));
      }
      if (model.token != null) {
        ret.add(token.set(model.token));
      }
      if (model.score != null) {
        ret.add(score.set(model.score));
      }
      if (model.image != null) {
        ret.add(image.set(model.image));
      }
      if (model.imageContentType != null) {
        ret.add(imageContentType.set(model.imageContentType));
      }
    }

    return ret;
  }

  Future<void> createTable({bool ifNotExists = false}) async {
    final st = Sql.create(tableName, ifNotExists: ifNotExists);
    st.addInt(id.name, primary: true, isNullable: false);
    st.addStr(username.name, isNullable: false);
    st.addStr(firstName.name, isNullable: false);
    st.addStr(lastName.name, isNullable: false);
    st.addDateTime(birthDate.name, isNullable: false);
    st.addInt(age.name, isNullable: false);
    st.addStr(gender.name, isNullable: false);
    st.addDouble(token.name, isNullable: false);
    st.addDouble(score.name, isNullable: false);
    st.addStr(image.name, isNullable: false);
    st.addStr(imageContentType.name, isNullable: false);
    return adapter.createTable(st);
  }

  Future<dynamic> insert(ClientDomain model,
      {bool cascade = false,
      bool onlyNonNull = false,
      Set<String> only}) async {
    final Insert insert = inserter
        .setMany(toSetColumns(model, only: only, onlyNonNull: onlyNonNull));
    return adapter.insert(insert);
  }

  Future<void> insertMany(List<ClientDomain> models,
      {bool onlyNonNull = false, Set<String> only}) async {
    final List<List<SetColumn>> data = models
        .map((model) =>
            toSetColumns(model, only: only, onlyNonNull: onlyNonNull))
        .toList();
    final InsertMany insert = inserters.addAll(data);
    await adapter.insertMany(insert);
    return;
  }

  Future<dynamic> upsert(ClientDomain model,
      {bool cascade = false,
      Set<String> only,
      bool onlyNonNull = false,
      isForeignKeyEnabled = false}) async {
    final Upsert upsert = upserter
        .setMany(toSetColumns(model, only: only, onlyNonNull: onlyNonNull));
    return adapter.upsert(upsert);
  }

  Future<void> upsertMany(List<ClientDomain> models,
      {bool onlyNonNull = false,
      Set<String> only,
      isForeignKeyEnabled = false}) async {
    final List<List<SetColumn>> data = [];
    for (var i = 0; i < models.length; ++i) {
      var model = models[i];
      data.add(
          toSetColumns(model, only: only, onlyNonNull: onlyNonNull).toList());
    }
    final UpsertMany upsert = upserters.addAll(data);
    await adapter.upsertMany(upsert);
    return;
  }

  Future<int> update(ClientDomain model,
      {bool cascade = false,
      bool associate = false,
      Set<String> only,
      bool onlyNonNull = false}) async {
    final Update update = updater
        .where(this.id.eq(model.id))
        .setMany(toSetColumns(model, only: only, onlyNonNull: onlyNonNull));
    return adapter.update(update);
  }

  Future<void> updateMany(List<ClientDomain> models,
      {bool onlyNonNull = false, Set<String> only}) async {
    final List<List<SetColumn>> data = [];
    final List<Expression> where = [];
    for (var i = 0; i < models.length; ++i) {
      var model = models[i];
      data.add(
          toSetColumns(model, only: only, onlyNonNull: onlyNonNull).toList());
      where.add(this.id.eq(model.id));
    }
    final UpdateMany update = updaters.addAll(data, where);
    await adapter.updateMany(update);
    return;
  }

  Future<ClientDomain> find(int id,
      {bool preload = false, bool cascade = false}) async {
    final Find find = finder.where(this.id.eq(id));
    return await findOne(find);
  }

  Future<int> remove(int id) async {
    final Remove remove = remover.where(this.id.eq(id));
    return adapter.remove(remove);
  }

  Future<int> removeMany(List<ClientDomain> models) async {
// Return if models is empty. If this is not done, all records will be removed!
    if (models == null || models.isEmpty) return 0;
    final Remove remove = remover;
    for (final model in models) {
      remove.or(this.id.eq(model.id));
    }
    return adapter.remove(remove);
  }
}
