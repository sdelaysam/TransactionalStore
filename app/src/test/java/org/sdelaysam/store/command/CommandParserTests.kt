package org.sdelaysam.store.command

import org.junit.Assert.assertEquals
import org.junit.Test

class CommandParserTests {

    @Test
    fun `empty input`() {
        val parser = CommandParser()
        assertNotFound(parser.parse(""))
    }

    @Test
    fun `random input`() {
        val parser = CommandParser()
        assertNotFound(parser.parse("anything unexpected"))
    }

    @Test
    fun `wrong format`() {
        val parser = CommandParser()
        assertWrongFormat(parser.parse("SET 1"), CommandType.SET)
        assertWrongFormat(parser.parse("SET 1 2 3"), CommandType.SET)
        assertWrongFormat(parser.parse("GET"), CommandType.GET)
        assertWrongFormat(parser.parse("GET 1 2"), CommandType.GET)
        assertWrongFormat(parser.parse("DELETE"), CommandType.DELETE)
        assertWrongFormat(parser.parse("DELETE 1 2"), CommandType.DELETE)
        assertWrongFormat(parser.parse("COUNT"), CommandType.COUNT)
        assertWrongFormat(parser.parse("COUNT 1 2"), CommandType.COUNT)
        assertWrongFormat(parser.parse("BEGIN 1"), CommandType.BEGIN)
        assertWrongFormat(parser.parse("COMMIT 1"), CommandType.COMMIT)
        assertWrongFormat(parser.parse("ROLLBACK 1"), CommandType.ROLLBACK)
    }

    @Test
    fun `correct format`() {
        val parser = CommandParser()
        assertSuccess(parser.parse("SET 1 2"), Command.SetValue("1", "2"))
        assertSuccess(parser.parse("set 1 2"), Command.SetValue("1", "2"))
        assertSuccess(parser.parse("GET 1"), Command.GetValue("1"))
        assertSuccess(parser.parse("Get 1"), Command.GetValue("1"))
        assertSuccess(parser.parse("DELETE 1"), Command.DeleteValue("1"))
        assertSuccess(parser.parse("DeLeTe 1"), Command.DeleteValue("1"))
        assertSuccess(parser.parse("COUNT 1"), Command.CountKeysForValue("1"))
        assertSuccess(parser.parse("count 1"), Command.CountKeysForValue("1"))
        assertSuccess(parser.parse("BEGIN"), Command.BeginTransaction)
        assertSuccess(parser.parse("begin"), Command.BeginTransaction)
        assertSuccess(parser.parse("COMMIT"), Command.CommitTransaction)
        assertSuccess(parser.parse("commit"), Command.CommitTransaction)
        assertSuccess(parser.parse("ROLLBACK"), Command.RollbackTransaction)
        assertSuccess(parser.parse("rollback"), Command.RollbackTransaction)
    }

    private fun assertNotFound(result: CommandParser.Result) {
        assertEquals(CommandParser.Result.NotFound, result)
    }

    private fun assertWrongFormat(result: CommandParser.Result, expectedCommandType: CommandType) {
        val format = "$expectedCommandType ${expectedCommandType.argumentsFormat}"
        assertEquals(CommandParser.Result.WrongFormat(format), result)
    }

    private fun assertSuccess(result: CommandParser.Result, expectedCommand: Command) {
        assertEquals(CommandParser.Result.Success(expectedCommand), result)
    }
}