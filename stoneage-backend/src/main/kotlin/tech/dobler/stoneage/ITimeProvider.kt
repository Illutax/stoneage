package tech.dobler.stoneage

import java.time.LocalDateTime

interface ITimeProvider {
    val instant: LocalDateTime

}
