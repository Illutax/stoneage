package tech.dobler.stoneage

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TimeProvider: ITimeProvider{
    override val instant get() = LocalDateTime.now()!!
}
