package models

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class CompaniesSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val c = Companies.syntax("c")

  behavior of "Companies"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Companies.find(123)
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Companies.findBy(sqls.eq(c.id, 123))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Companies.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Companies.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Companies.findAllBy(sqls.eq(c.id, 123))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Companies.countBy(sqls.eq(c.id, 123))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Companies.create(id = 123, name = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Companies.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Companies.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Companies.findAll().head
    val deleted = Companies.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Companies.find(123)
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Companies.findAll()
    entities.foreach(e => Companies.destroy(e))
    val batchInserted = Companies.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
