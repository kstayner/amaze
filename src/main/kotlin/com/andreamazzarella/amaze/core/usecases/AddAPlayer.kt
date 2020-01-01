package com.andreamazzarella.amaze.core.usecases

import com.andreamazzarella.amaze.core.Game
import com.andreamazzarella.amaze.core.GameId
import com.andreamazzarella.amaze.core.PlayerId
import com.andreamazzarella.amaze.persistence.GameRepository
import com.andreamazzarella.amaze.utils.Result
import com.andreamazzarella.amaze.utils.andThen
import com.andreamazzarella.amaze.utils.map
import com.andreamazzarella.amaze.utils.mapError
import java.util.UUID

object AddAPlayer {
    fun doIt(
        gameId: GameId,
        playerName: String,
        playerId: PlayerId = UUID.randomUUID()
    ): Result<PlayerId, AddAPlayerError> =
        findGame(gameId)
            .andThen { addPlayer(it, playerName, playerId) }
            .map { GameRepository.save(it) }
            .map { playerId }

    private fun addPlayer(game: Game, playerName: String, playerId: PlayerId): Result<Game, AddAPlayerError> =
        game.addPlayer(
            playerName,
            playerId
        ).mapError { AddAPlayerError(AddAPlayer.PotentialAddAPlayerError.PlayerAlreadyExists) }

    private fun findGame(gameId: GameId): Result<Game, AddAPlayerError> =
        GameRepository.find(gameId).mapError { AddAPlayerError(AddAPlayer.PotentialAddAPlayerError.GameDoesNotExist) }

    data class AddAPlayerError(val error: PotentialAddAPlayerError)
    sealed class PotentialAddAPlayerError {
        object GameDoesNotExist : PotentialAddAPlayerError()
        object PlayerAlreadyExists : PotentialAddAPlayerError()
    }
}

