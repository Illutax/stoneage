package tech.dobler.stoneage.service

import java.time.LocalDateTime

interface ITimeProvider {
    val instant: LocalDateTime

}
