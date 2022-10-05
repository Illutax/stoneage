package tech.dobler.stoneage

import javax.persistence.EntityManager

fun EntityManager.flushAndClear() {
    this.flush()
    this.clear()
}
