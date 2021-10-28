package org.sdelaysam.store.command

enum class CommandType {
    SET,
    GET,
    DELETE,
    COUNT,
    BEGIN,
    COMMIT,
    ROLLBACK
}

val CommandType.argumentsFormat: String
    get() = when (this) {
        CommandType.SET -> "<KEY> <VALUE>"
        CommandType.GET,
        CommandType.DELETE,
        CommandType.COUNT -> "<VALUE>"
        CommandType.BEGIN,
        CommandType.COMMIT,
        CommandType.ROLLBACK -> ""
    }


sealed class Command {

    data class SetValue(val key: String, val value: String) : Command()
    data class GetValue(val key: String) : Command()
    data class DeleteValue(val key: String) : Command()
    data class CountKeysForValue(val value: String) : Command()
    object BeginTransaction : Command()
    object CommitTransaction : Command()
    object RollbackTransaction : Command()

    companion object {
        fun createFrom(type: CommandType, arguments: List<String>): Command? {
            return when {
                type == CommandType.SET && arguments.size == 2 ->
                    SetValue(arguments[0], arguments[1])
                type == CommandType.GET && arguments.size == 1 ->
                    GetValue(arguments[0])
                type == CommandType.DELETE && arguments.size == 1 ->
                    DeleteValue(arguments[0])
                type == CommandType.COUNT && arguments.size == 1 ->
                    CountKeysForValue(arguments[0])
                type == CommandType.BEGIN && arguments.isEmpty() ->
                    BeginTransaction
                type == CommandType.COMMIT && arguments.isEmpty() ->
                    CommitTransaction
                type == CommandType.ROLLBACK && arguments.isEmpty() ->
                    RollbackTransaction
                else -> null
            }
        }
    }
}