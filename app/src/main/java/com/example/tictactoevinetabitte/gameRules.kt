package com.example.tictactoevinetabitte
// Par pamatu spēles loģikai izmantots:
// https://github.com/usmaanz/Tic-Tac-Toe/blob/master/app/src/main/java/com/usmaan/tictactoe/GameManager.kt

//Klase, kuraa glābā objekta pozīcīju
data class ViewsPosition(val gridRow: Int, val gridColumn: Int)
// Klase uzvaras līniju definēšanai
enum class WinLine {
    FIRSTROW,
    SECONDROW,
    THIRDROW,
    FIRSTCOLUMN,
    SECONDCOLUMN,
    THIRDCOLUMN,
    DIAGONAL1,
    DIAGONAL2
}

class GameRules {
    private var player = 1
    private var pvcPlayer = 1
    private var pvcComputer = 2
    val pvcPlayerSym = "X"
    val pvcComputerSym = "O"
    var firstPlayerPoints = 0
    var secondPlayerPoints = 0

    val playerSymbols: String get() {
        return if (player == 1) "X" else "O"
    }
    // Masīvs, kas glābā spēles laukumu un tās stāvokļus
    private var possiblePositions = arrayOf(
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0)
    )
    // Funkcija, kura īsteno gājienus, balstoties uz nospiesto šūnu
    fun makeMove(viewPosition: ViewsPosition): WinLine? {
        possiblePositions[viewPosition.gridRow][viewPosition.gridColumn] = player
        val winLine = gameEndCheck()
        if (winLine == null) {
            //maina spēlētājus / kuram gājiens tagad
            player = 3 - player
        } else {
            if (player == 1) {
                firstPlayerPoints++
            }
            else if (player == 2) {
                secondPlayerPoints++
            }
        }
        return winLine
    }

//------------------------------------------
    // Nepabeigta PvC režīma realizācija
    // Īsteno spēlētāja gājienu un punktu piešķiri
    fun makeMovePlayer(viewPosition: ViewsPosition): WinLine? {
        possiblePositions[viewPosition.gridRow][viewPosition.gridColumn] = pvcPlayer
        val winLine = gameEndCheckPlayer()
        if (winLine != null) {
            firstPlayerPoints++
        }
    return winLine
    }
    // Īsteno tieši datora gājienu un punktu piešķiri
    fun makeMoveComputer(viewPosition: ViewsPosition): WinLine? {
        possiblePositions[viewPosition.gridRow][viewPosition.gridColumn] = pvcComputer
        val winLine = gameEndCheckComputer()
        if (winLine != null) {
            secondPlayerPoints++
        }
        return winLine
    }
    // Nejauši izvēlas šūnas pozīciju no tukšām masīva possibleMoves pozīcījām,
    // ja pozīcīja ir aizņemta ģenerē jaunu nejaušo vērtību
    // *viss kods no gameRules.kt uz to brīdi* un
    // prompt: how to implement simple computer moves into this tic tac toe logic:
    fun computerMove(): ViewsPosition {
        var randomRow = (0..2).random()
        var randomCol = (0..2).random()
        while (possiblePositions[randomRow][randomCol] != 0) {
            randomRow = (0..2).random()
            randomCol = (0..2).random()
        }
        return ViewsPosition(randomRow, randomCol)
    }
// Abas funkcijas pārbauda vai kāds no spēlētājiem ar saviem gājieniem ir izveidojis uzvaras līniju
// Vajadzēja dublēt funkciju gan spēlētājam, gan datoram, jo PvC šajā variantā neīsteno spēlētāju
// maiņu kā player = 3 - player un pārbaude nestrādā tā kā vajag
    private fun gameEndCheckPlayer(): WinLine? {
    if (possiblePositions[0][0] == pvcPlayer && possiblePositions[0][1] == pvcPlayer &&
        possiblePositions[0][2] == pvcPlayer) {
        return WinLine.FIRSTROW
    } else if (possiblePositions[1][0] == pvcPlayer && possiblePositions[1][1] == pvcPlayer &&
        possiblePositions[1][2] == pvcPlayer) {
        return WinLine.SECONDROW
    } else if (possiblePositions[2][0] == pvcPlayer && possiblePositions[2][1] == pvcPlayer &&
        possiblePositions[2][2] == pvcPlayer) {
        return WinLine.THIRDROW
    } else if (possiblePositions[0][0] == pvcPlayer && possiblePositions[1][1] == pvcPlayer &&
        possiblePositions[2][2] == pvcPlayer) {
        return WinLine.DIAGONAL1
    } else if (possiblePositions[0][2] == pvcPlayer && possiblePositions[1][1] == pvcPlayer &&
        possiblePositions[2][0] == pvcPlayer) {
        return WinLine.DIAGONAL2
    } else if (possiblePositions[0][0] == pvcPlayer && possiblePositions[1][0] == pvcPlayer &&
        possiblePositions[2][0] == pvcPlayer) {
        return WinLine.FIRSTCOLUMN
    } else if (possiblePositions[0][1] == pvcPlayer && possiblePositions[1][1] == pvcPlayer &&
        possiblePositions[2][1] == pvcPlayer) {
        return WinLine.SECONDCOLUMN
    } else if (possiblePositions[0][2] == pvcPlayer && possiblePositions[1][2] == pvcPlayer &&
        possiblePositions[2][2] == pvcPlayer) {
        return WinLine.THIRDCOLUMN
    } else {
        return null
    }
}
private fun gameEndCheckComputer(): WinLine? {
    if (possiblePositions[0][0] == pvcComputer && possiblePositions[0][1] == pvcComputer &&
        possiblePositions[0][2] == pvcComputer) {
        return WinLine.FIRSTROW
    } else if (possiblePositions[1][0] == pvcComputer && possiblePositions[1][1] == pvcComputer &&
        possiblePositions[1][2] == pvcComputer) {
        return WinLine.SECONDROW
    } else if (possiblePositions[2][0] == pvcComputer && possiblePositions[2][1] == pvcComputer &&
        possiblePositions[2][2] == pvcComputer) {
        return WinLine.THIRDROW
    } else if (possiblePositions[0][0] == pvcComputer && possiblePositions[1][1] == pvcComputer &&
        possiblePositions[2][2] == pvcComputer) {
        return WinLine.DIAGONAL1
    } else if (possiblePositions[0][2] == pvcComputer && possiblePositions[1][1] == pvcComputer &&
        possiblePositions[2][0] == pvcComputer) {
        return WinLine.DIAGONAL2
    } else if (possiblePositions[0][0] == pvcComputer && possiblePositions[1][0] == pvcComputer &&
        possiblePositions[2][0] == pvcComputer) {
        return WinLine.FIRSTCOLUMN
    } else if (possiblePositions[0][1] == pvcComputer && possiblePositions[1][1] == pvcComputer &&
        possiblePositions[2][1] == pvcComputer) {
        return WinLine.SECONDCOLUMN
    } else if (possiblePositions[0][2] == pvcComputer && possiblePositions[1][2] == pvcComputer &&
        possiblePositions[2][2] == pvcComputer) {
        return WinLine.THIRDCOLUMN
    } else {
        return null
    }
}
//---------------------------

    // Pārbauda visu masīvu, meklējot uzvaras līniju, ja atrod, atgriež to, pretējā gadījumā null vērtību
    private fun gameEndCheck(): WinLine? {
        if (possiblePositions[0][0] == player && possiblePositions[0][1] == player &&
            possiblePositions[0][2] == player) {
            return WinLine.FIRSTROW
        } else if (possiblePositions[1][0] == player && possiblePositions[1][1] == player &&
            possiblePositions[1][2] == player) {
            return WinLine.SECONDROW
        } else if (possiblePositions[2][0] == player && possiblePositions[2][1] == player &&
            possiblePositions[2][2] == player) {
            return WinLine.THIRDROW
        } else if (possiblePositions[0][0] == player && possiblePositions[1][1] == player &&
            possiblePositions[2][2] == player) {
            return WinLine.DIAGONAL1
        } else if (possiblePositions[0][2] == player && possiblePositions[1][1] == player &&
            possiblePositions[2][0] == player) {
            return WinLine.DIAGONAL2
        } else if (possiblePositions[0][0] == player && possiblePositions[1][0] == player &&
            possiblePositions[2][0] == player) {
            return WinLine.FIRSTCOLUMN
        } else if (possiblePositions[0][1] == player && possiblePositions[1][1] == player &&
            possiblePositions[2][1] == player) {
            return WinLine.SECONDCOLUMN
        } else if (possiblePositions[0][2] == player && possiblePositions[1][2] == player &&
            possiblePositions[2][2] == player) {
            return WinLine.THIRDCOLUMN
        } else {
            return null
        }
    }
    // Atjauno masīva sākuma vērtības un mainīgo, kas palīdz noteikt, kura spēlētāja kārta
    fun resetPossiblePositions() {
        possiblePositions = arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(0, 0, 0),
            intArrayOf(0, 0, 0)
        )
        player = 1
    }
}