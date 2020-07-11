// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_domain.dart';

// **************************************************************************
// BeanGenerator
// **************************************************************************

abstract class _UserDomainBean implements Bean<UserDomain> {
  final id = StrField('id');
  final login = StrField('login');
  final firstName = StrField('first_name');
  final lastName = StrField('last_name');
  final email = StrField('email');
  final imageUrl = StrField('image_url');
  final langKey = StrField('lang_key');
  Map<String, Field> _fields;
  Map<String, Field> get fields => _fields ??= {
        id.name: id,
        login.name: login,
        firstName.name: firstName,
        lastName.name: lastName,
        email.name: email,
        imageUrl.name: imageUrl,
        langKey.name: langKey,
      };
  UserDomain fromMap(Map map) {
    UserDomain model = UserDomain();
    model.id = adapter.parseValue(map['id']);
    model.login = adapter.parseValue(map['login']);
    model.firstName = adapter.parseValue(map['first_name']);
    model.lastName = adapter.parseValue(map['last_name']);
    model.email = adapter.parseValue(map['email']);
    model.imageUrl = adapter.parseValue(map['image_url']);
    model.langKey = adapter.parseValue(map['lang_key']);

    return model;
  }

  List<SetColumn> toSetColumns(UserDomain model,
      {bool update = false, Set<String> only, bool onlyNonNull = false}) {
    List<SetColumn> ret = [];

    if (only == null && !onlyNonNull) {
      ret.add(id.set(model.id));
      ret.add(login.set(model.login));
      ret.add(firstName.set(model.firstName));
      ret.add(lastName.set(model.lastName));
      ret.add(email.set(model.email));
      ret.add(imageUrl.set(model.imageUrl));
      ret.add(langKey.set(model.langKey));
    } else if (only != null) {
      if (only.contains(id.name)) ret.add(id.set(model.id));
      if (only.contains(login.name)) ret.add(login.set(model.login));
      if (only.contains(firstName.name))
        ret.add(firstName.set(model.firstName));
      if (only.contains(lastName.name)) ret.add(lastName.set(model.lastName));
      if (only.contains(email.name)) ret.add(email.set(model.email));
      if (only.contains(imageUrl.name)) ret.add(imageUrl.set(model.imageUrl));
      if (only.contains(langKey.name)) ret.add(langKey.set(model.langKey));
    } else /* if (onlyNonNull) */ {
      if (model.id != null) {
        ret.add(id.set(model.id));
      }
      if (model.login != null) {
        ret.add(login.set(model.login));
      }
      if (model.firstName != null) {
        ret.add(firstName.set(model.firstName));
      }
      if (model.lastName != null) {
        ret.add(lastName.set(model.lastName));
      }
      if (model.email != null) {
        ret.add(email.set(model.email));
      }
      if (model.imageUrl != null) {
        ret.add(imageUrl.set(model.imageUrl));
      }
      if (model.langKey != null) {
        ret.add(langKey.set(model.langKey));
      }
    }

    return ret;
  }

  Future<void> createTable({bool ifNotExists = false}) async {
    final st = Sql.create(tableName, ifNotExists: ifNotExists);
    st.addStr(id.name, primary: true, isNullable: false);
    st.addStr(login.name, isNullable: false);
    st.addStr(firstName.name, isNullable: false);
    st.addStr(lastName.name, isNullable: false);
    st.addStr(email.name, isNullable: false);
    st.addStr(imageUrl.name, isNullable: false);
    st.addStr(langKey.name, isNullable: false);
    return adapter.createTable(st);
  }

  Future<dynamic> insert(UserDomain model,
      {bool cascade = false,
      bool onlyNonNull = false,
      Set<String> only}) async {
    final Insert insert = inserter
        .setMany(toSetColumns(model, only: only, onlyNonNull: onlyNonNull));
    return adapter.insert(insert);
  }

  Future<void> insertMany(List<UserDomain> models,
      {bool onlyNonNull = false, Set<String> only}) async {
    final List<List<SetColumn>> data = models
        .map((model) =>
            toSetColumns(model, only: only, onlyNonNull: onlyNonNull))
        .toList();
    final InsertMany insert = inserters.addAll(data);
    await adapter.insertMany(insert);
    return;
  }

  Future<dynamic> upsert(UserDomain model,
      {bool cascade = false,
      Set<String> only,
      bool onlyNonNull = false,
      isForeignKeyEnabled = false}) async {
    final Upsert upsert = upserter
        .setMany(toSetColumns(model, only: only, onlyNonNull: onlyNonNull));
    return adapter.upsert(upsert);
  }

  Future<void> upsertMany(List<UserDomain> models,
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

  Future<int> update(UserDomain model,
      {bool cascade = false,
      bool associate = false,
      Set<String> only,
      bool onlyNonNull = false}) async {
    final Update update = updater
        .where(this.id.eq(model.id))
        .setMany(toSetColumns(model, only: only, onlyNonNull: onlyNonNull));
    return adapter.update(update);
  }

  Future<void> updateMany(List<UserDomain> models,
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

  Future<UserDomain> find(String id,
      {bool preload = false, bool cascade = false}) async {
    final Find find = finder.where(this.id.eq(id));
    return await findOne(find);
  }

  Future<int> remove(String id) async {
    final Remove remove = remover.where(this.id.eq(id));
    return adapter.remove(remove);
  }

  Future<int> removeMany(List<UserDomain> models) async {
// Return if models is empty. If this is not done, all records will be removed!
    if (models == null || models.isEmpty) return 0;
    final Remove remove = remover;
    for (final model in models) {
      remove.or(this.id.eq(model.id));
    }
    return adapter.remove(remove);
  }
}
