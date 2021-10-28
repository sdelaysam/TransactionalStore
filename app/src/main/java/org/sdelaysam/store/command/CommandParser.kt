package org.sdelaysam.store.command

class CommandParser {

    sealed class Result {
        data class Success(val command: Command): Result()
        data class WrongFormat(val expectedFormat: String): Result()
        object NotFound : Result()
    }

    fun parse(input: String): Result {
        val tokens = input.split(" ")

        val commandName = tokens
            .firstOrNull()
            ?.uppercase()
            ?: return Result.NotFound

        val commandType = CommandType.values()
            .find { it.name == commandName }
            ?: return Result.NotFound

        val command = Command.createFrom(commandType, tokens.drop(1))
            ?: return Result.WrongFormat("$commandName ${commandType.argumentsFormat}")

        return Result.Success(command)
    }
}