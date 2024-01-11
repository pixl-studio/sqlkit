# Sqlkit
Sqlkit is a SQL-based DB access library for Scala.  
Include various tools to simplify inserting, updating, and querying data.  

This can make it easier to write and maintain complex database-driven applications.

#### (almost) Not an ORM 
An ORM, or Object-Relational Mapper, is a tool that allows a programmer to work with a database using objects instead of SQL. ORMs are used to abstract away the complexity of working with a database, making it easier to write and maintain code that interacts with a database.  
Very often, abstracting and ease to jump between relations cause developers to not think about what they are doing and the number of queries fired under the hood. 

Sqlkit **is only** and **will ever be** just an **SQL Object Mapper** on steroids.

#### Not another DSL
SQL language is quite expressive. You don't need to learn a new dsl to write queries in a better way.  
Plain SQL allow you to test your queries in your favorite query tool and past it in your model in a easy way.

Sqlkit use scala string interpolation to auto bind table name, sql columns, and parameters.

#### Based on JDBC

- MySQL
- PostgreSQL 
- CockroachDB (via postgres wire protocol)
- ...

## Getting Started

### Install
```sbt
libraryDependencies ++= Seq(
  "fr.pixlstudio" %% "sqlkit" % "0.1"
)
```
#### Configuration
```
```

#### HikariCP
Sqlkit use [HikariCP](https://github.com/brettwooldridge/HikariCP) to manage the connecion pool. By far the most fatest and stable connection pool manager.

### Writing models

````scala
case class User(
  id: UUID,
  email: String,
  password: String
) extends SqlModel[User]
object User extends SqlTable[User]{
  
  def table(alias: String) = TableDef(alias)
  case class TableDef(alias: String) extends SqlTableDef[User] {
    
    val table = "user"
    
    val id = column[UUID]("id", primaryKey = true)
    val email = column[String]("email")
    val password = column[String]("password")

    def fromSql(row: SqlRow) = User(
      id = row.get(id), email = row.get(email), password = row.get(password)
    )

    def toSql(user: User) = List(
      id -> user.id, email -> user.email, password -> user.password
    )
  }
  
}
````
TableDef describe the table structure. You have to define the columns with their corresponding types.  
Column take an SqlBinder of type in implicit.

#### SqlBinder

You can write as much binder as you want for custom types.  
Each binder must define fromSql and toSql methods helping to transpose sql data into type and vice versa.

example:
```scala
class IntToSql extends SqlBinder[Int] {

  override def fromSql(o: Object): Int = {
    o match {
      case s:java.lang.Integer => s
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: Int, statement: PreparedStatement, index: Int): Unit = {
    statement.setInt(index, v)
  }

  override def unwrap(o: String): Int = java.lang.Integer.valueOf(o)
}
```

### Writing queries


````scala
object User extends SqlTable[User]{
  
  def table(alias: String) = TableDef(alias)
  case class TableDef(alias: String) extends SqlTableDef[User]{
    ...
  }
  
  def byName(name:String)(implicit session:SqlSession=autoSession): Option[User] = {
    withSql(table) { user =>
      sql"""
          SELECT ${user.*}
          FROM $user
          WHERE ${user.email} = :name
      """.as(user)
    }.on(
      "name" -> name
    ).single
  }
}
````

withSql take a table definition and aliase it
```scala
withSql(table) { user =>
```
*note you can use up to 22 parameters predefined withSql methods.  
If you really need, you can write your own withSql aliaser with more parameters, just look at the package object in sqlkit for example:
```scala
  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_]](t1: String => T1, t2: String => T2)(f: (T1, T2) => U): U = f(
    t1("x1"), t2("x2")
  )
```
Another solution would also be to start thinking about your data model :)


#### anatomy of a query
```scala
sql"""
  SELECT ${user.*}
  FROM $user
  WHERE ${user.email} = :email
"""
```
```sql"""``` is the string interpolation will transpose into SqlQuery[T]  
```${user.*}``` is the list of all columns of the table  
```$user``` is the table aliased
```$user.email``` is the column with table name aliased  
```:email``` is a PreparedStatement parameter name

will transpose to
```
  SELECT x1.id, x1.email, x1.password
  FROM user as x1
  WHERE x1.email = ?
```

#### Binding parameters

```scala
.on(
  "name" -> name
)
```
is a form of ```key -> value```. Here, the key ```name``` correpond to the ```:name``` parameter in the query.  
Each parameter is escaped by PreparedStatement to prevent sql injection attacks.

Common types:  
```String, UUID```  
Numeric types:  
```Short, Int, Long, Double, Float, BigDecimal```  
Time types:  
```LocalTime, LocalDate, LocalDateTime, Instant, Duration```


#### Binding results
```.as(user)```
will take each row and bind to user definition table (regarding fromSql method). In this case will bind to User object.  

Another possibility is to define a custom binding method  
``` 
.as { row => 
    user.fromSql(row)
}
```
Note that here the SqlQuery become SqlQuery[User]
``` 
.as { row => 
    row.get(user.email)
}
```
Note that here the SqlQuery become SqlQuery[String]
``` 
.as { row => 
    (row.get(user.id), row.get(user.email))
}
```
Note that here the SqlQuery become SqlQuery[(Int, String)]



#### Getting result

```
.single
```
will execute the query and return an ```Option[T]``` of SqlQuery type. Option[User], Option[String] or Option[(Int,String)] in our examples.

```
.list
```
will execute the query and return a ```List[T]``` of SqlQuery type. List[User], List[String] or List[(Int,String)] in our examples.

### Join tables
```scala
withSql(User.table, Role.table, UserRole.table) { case (user, role, userRole) =>
  sql"""
      SELECT ${user.*}, ${role.*}
      FROM ${user}
      LEFT JOIN ${userRole} ON ${userRole.userId} = ${user.id}
      LEFT JOIN ${role} ON ${role.id} = ${userRole.roleId}
      WHERE ${role.name} = :name
    """.as{ row => (user.fromSql(row), role.fromSql(row)) }
}.on(
  "name" -> name
).list
```
```withSql``` method can can take up to 22 parameters. Write your query as you will have written in raw sql and use the table alias to get results.

### Insert, update, delete

```SqlTable``` comes with predefined exec methods which generate for you insert, update, delete statetements.

#### Insert
```scala
val user = User(...)
// You can use it in 2 differents ways.
// using SqlTable
User.insert(user)
// using the SqlModel helper method. (use SqlTable internaly)
user.insert()
```

#### Batch Insert
```scala
val users = List(...)
User.batchInsert(users)
```
will insert by batch and returned the newly inserted (for generated ids)
#### Update
```scala
val user = User(...)
// You can use it in 2 differents ways.
// using SqlTable
User.update(user)
// using the SqlModel helper method. (use SqlTable internaly)
user.update()
```
#### Delete
```scala
val user = User(...)
// You can use it in 2 differents ways.
// using SqlTable
User.delete(user)
// using the SqlModel helper method. (use SqlTable internaly)
user.delete()
```
#### Auto increment
```
```

#### exec
```
```

### SqlSession

#### SqlAuto
```
```

#### SqlTx
```
```

### Tips and tricks

#### Pre-defined query
Most of the time you just want querring your model without joining another.  
```scala
where { user =>
  sql"""${user.email} = $name"""
}.single
```

It is a shortand of:
```scala
withSql(table) { user =>
  sql"""
      SELECT ${user.*}
      FROM $user
      WHERE ${user.email} = :name
  """.as(user)
}.on(
  "name" -> name
)
```
```sql"""${user.email} = $name"""``` will be merge to the pre-setted query and mapped to fromSql method of table definition.

#### Automatic pagination
Sometimes queries can have huge results rows.  
A common way to avoid performance issue is using a limit and offset window.
```scala
where { user =>
  sql"""${user.email} = $name"""
}.list(0, 10)
```
```list(offset: Long, limit: Long)```  

It fires 2 requests: One for the count (keeping from, where, ...) and the other for sublist by auto inject LIMIT CLAUSE.
Return ```SqlList[T]``` with ```T``` is the type of ```SqlQuery``` containing the number of total rows, the offset and limit parameters used and the list of result.
```scala
case class SqlList[T](
  data: List[T],
  offset: Long,
  limit: Long,
  total: Long
)
```

Note that you can use it with all type of query (with custom mapping or not).

#### On the fly parameter binding
```scala
def oneTheFly(email: String)(implicit session: SqlSession = autoSession) = {

withSql(User.table) { case (user) =>
  sql"""
      SELECT ${user.*}
      FROM ${user}
      WHERE ${user.email} = $email
    """.as { row => user.fromSql(row) }
  }.list
}
```
```$email``` will be auto-escaped to prevent sql injection.
For better performance, it is better to use named parameters because it generates a preparedStatement.

/!\ Do not use in combination with compiled query otherwise the first query will be cached definitively.

#### One to many

```scala
.oneToMany(
  row => oneTable.fromSql(row),
  row => toManyTable.fromSql(row),
)
```
This method will auto group rows by the ```one``` parser, giving a List for the ```toManyTable``` parser.
It uses a Map with one parser result as key internally. 
Note that ```toManyTable.fromSql``` will not fire exceptions in case of ```LEFT JOIN```, it's catched internally and no values will be had to List, so feel free to use it on this way.

In case of multiple ```JOIN``` and ```toManies```, to avoid duplication in list results, you can use listSets instead of list.

Full example:
```scala
withSql(User.table, Role.table, UserRole.table) { case (user, role, userRole) =>
  sql"""
      SELECT ${user.*}, ${role.*}
      FROM ${user}
      LEFT JOIN ${userRole} ON ${userRole.userId} = ${user.id}
      LEFT JOIN ${role} ON ${role.id} = ${userRole.roleId}
      WHERE ${role.name} = :name
    """.oneToMany(
    row => user.fromSql(row),
    row => role.fromSql(row),
  )
}.on(
  "name" -> name
).list
```

#### Compiled query
```scala
withSqlCompiled(User.table, Role.table, UserRole.table) { case (user, role, userRole) =>
  sql"""
      SELECT ${user.*}, ${role.*}
      FROM ${user}
      LEFT JOIN ${userRole} ON ${userRole.userId} = ${user.id}
      LEFT JOIN ${role} ON ${role.id} = ${userRole.roleId}
      WHERE ${role.name} = :name
    """.as { row =>
    (user.fromSql(row), role.fromSql(row))
  }
}.on(
  "name" -> name
).list
```
In order to avoid executing the sql interpolation at each call, you can pre-compile your query. It will then be cached with the composition of the class name, method, line as key.

Be careful, do not use on-the-fly interpolation in this case otherwise the cached query will only contain the values of the first call.

#### Encrypted column
```
```
#### Generated query
```
SqlQuery.query
```
#### Logging query
```
```