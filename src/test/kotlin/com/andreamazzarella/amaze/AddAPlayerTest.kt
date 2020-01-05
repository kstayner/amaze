package com.andreamazzarella.amaze

import assertError
import assertIsOk
import com.andreamazzarella.amaze.core.usecases.AddAPlayer
import com.andreamazzarella.amaze.core.usecases.StartAGame
import com.andreamazzarella.amaze.utils.pipe
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class AddAPlayerTest {

    @Test
    fun `adds a player to a game`() =
        StartAGame.doIt()
            .pipe { AddAPlayer.doIt(it, "runner 1", UUID.randomUUID()) }
            .pipe(::assertIsOk)

    @Test
    fun `a player cannot be added without a valid game id`() =
        AddAPlayer.doIt("some game id that does not exist", "runner 1", UUID.randomUUID())
            .pipe { assertError(it) { it.error is AddAPlayer.PotentialAddAPlayerError.GameDoesNotExist } }

    @Test
    fun `a player cannot be added if another player with the same name already exists`() {
        val gameId = StartAGame.doIt()

        AddAPlayer.doIt(gameId, "fave runner", UUID.randomUUID())

        AddAPlayer.doIt(gameId, "fave runner", UUID.randomUUID())
            .pipe { assertError(it) { it.error is AddAPlayer.PotentialAddAPlayerError.PlayerAlreadyExists } }

    }
}
