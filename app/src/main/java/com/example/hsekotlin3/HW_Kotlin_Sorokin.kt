data class Transaction(val category: String, val type: String, val amount: Int)

var balance = 0
var transactions = mutableListOf<Transaction>()

fun main() {
    val command0 = "Exit"
    val command1 = "Add expense"
    val command2 = "Add income"
    val command3 = "Delete last transaction"
    val command4 = "Transaction history"

    while (true) {
        println("===========================")
        println("BALANCE: $balance")
        println("===========================")
        println("MENU:")
        println("4 - $command4")
        println("3 - $command3")
        println("2 - $command2")
        println("1 - $command1")
        println("0 - $command0")
        println("===========================")

        val input = readLine()
//        println("*** *** ***")
        when (input) {
            "0" -> return
            "1" -> {
                val (category, expense) = readExpense()
                balance -= expense
                transactions.add(Transaction(category, "Expense", expense))
            }
            "2" -> {
                val (category, income) = readIncome()
                balance += income
                transactions.add(Transaction(category, "Income", income))
            }
            "3" -> {
                if (transactions.isNotEmpty()) {
                    val lastTransaction = transactions.last()
                    if (lastTransaction.type == "Expense") balance += lastTransaction.amount else balance -= lastTransaction.amount
                    transactions.removeAt(transactions.size - 1)
                    println("Last transaction deleted.")
                } else {
                    println("No transactions to delete.")
                }
            }
            "4" -> {
                transactionHistory()
            }
            else -> println("Unknown command. Please use 0, 1, 2, 3, or 4.")
        }
//        println("*** *** ***\n")
    }
}

fun transactionHistory() {
    println("All history or by category?")
    println("0 - All history")
    println("1 - By category")
    when (readLine()) {
        "0" -> {
            if (transactions.isEmpty()) {
                println("No transactions found.")
            } else {
                transactions.forEach { println("${it.type} in '${it.category}': ${it.amount}") }
            }
        }
        "1" -> {
            println("Enter category:")
            val category = readLine() ?: ""
            val filteredTransactions = transactions.filter { it.category.equals(category, ignoreCase = true) }
            if (filteredTransactions.isEmpty()) {
                println("No transactions found for category '$category'")
            } else {
                filteredTransactions.forEach { println("${it.type}: ${it.amount}") }
            }
        }
        else -> println("Invalid input.")
    }
}

fun readIncome(): Pair<String, Int> = readTransaction("Income")

fun readExpense(): Pair<String, Int> = readTransaction("Expense")

fun readTransaction(type: String): Pair<String, Int> {
    while (true) {
        println("Enter $type category:")
        val category = readLine() ?: ""
        println("Enter $type amount:")
        val amountStr = readLine()
        val amountInt = amountStr?.toIntOrNull()
        if (amountInt != null) {
            return Pair(category, amountInt)
        } else {
            println("Invalid input. Please enter a valid number.")
        }
    }
}
