package org.sdelaysam.store.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.sdelaysam.store.Stores
import org.sdelaysam.store.command.Command
import org.sdelaysam.store.command.CommandParser
import org.sdelaysam.store.command.CommandType

// TODO: localization
class ConsoleViewModel : ViewModel() {

    private val store = Stores.linear()

    private val parser = CommandParser()

    val output = MutableStateFlow(emptyList<String>())

    fun onInput(input: String) {
        val lines = mutableListOf<String>()
        lines += ""
        lines += input
        when (val result = parser.parse(input)) {
            CommandParser.Result.NotFound -> {
                lines += "Command not found."
                lines += "Supported commands:"
                lines += CommandType.values().joinToString()
            }
            is CommandParser.Result.WrongFormat -> {
                lines += "Unable to execute command."
                lines += "Supported format:"
                lines += result.expectedFormat
            }
            is CommandParser.Result.Success -> {
                performCommand(result.command, lines)
            }
        }
        output.value += lines
    }

    private fun performCommand(command: Command, outputLines: MutableList<String>) {
        when (command) {
            is Command.SetValue -> {
                store.setValue(command.key, command.value)
            }
            is Command.GetValue -> {
                val value = store.getValue(command.key)
                outputLines += value ?: "key not set"
            }
            is Command.DeleteValue -> {
                store.deleteKey(command.key)
            }
            is Command.CountKeysForValue -> {
                val count = store.queries.countKeysByValue(command.value)
                outputLines += count.toString()
            }
            Command.BeginTransaction -> {
                store.beginTransaction()
            }
            Command.CommitTransaction -> {
                if (!store.commitTransaction()) {
                    outputLines += "no transaction"
                }
            }
            Command.RollbackTransaction -> {
                if (!store.rollbackTransaction()) {
                    outputLines += "no transaction"
                }
            }
        }
    }
}
