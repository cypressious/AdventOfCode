package advent

fun main(args: Array<String>) {
    Entity(100, 0, 0).equipAgainstBoss().print()
}

private fun Entity.equipAgainstBoss(): Entity? {
    if (winsAgainst(boss)) {
        return this
    }

    return shop.asSequence()
            .filter { it.canBeEquippedBy(this) }
            .map { it.equip(this).equipAgainstBoss() }
            .filterNotNull()
            .minBy { it.price }
}

tailrec private fun Entity.winsAgainst(enemy: Entity): Boolean = when {
    enemy.hitpoints <= 0 -> true
    this.hitpoints <= 0 -> false
    else -> receiveDamageFrom(enemy).winsAgainst(enemy.receiveDamageFrom(this))
}

private fun Entity.receiveDamageFrom(enemy: Entity)
        = copy(hitpoints - enemy.damagePoints.minus(armorPoints).coerceAtLeast(1))

private data class Entity(
        val hitpoints: Int,
        val damagePoints: Int,
        val armorPoints: Int,
        val weapon: String? = null,
        val armor: String? = null,
        val rings: List<String> = emptyList(),
        val price: Int = 0
)

private interface Item {
    val name: String
    val price: Int
    val damagePoints: Int
    val armorPoints: Int
    fun canBeEquippedBy(entity: Entity): Boolean
    fun Entity.equipName(): Entity

    fun equip(entity: Entity) = entity.equipName().copy(
            damagePoints = entity.damagePoints + damagePoints,
            armorPoints = entity.armorPoints + armorPoints,
            price = entity.price + price)
}

private class Weapon(
        override val name: String,
        override val price: Int,
        override val damagePoints: Int,
        override val armorPoints: Int
) : Item {
    override fun canBeEquippedBy(entity: Entity) = entity.weapon == null
    override fun Entity.equipName() = copy(weapon = name)
}

private class Armor(
        override val name: String,
        override val price: Int,
        override val damagePoints: Int,
        override val armorPoints: Int
) : Item {
    override fun canBeEquippedBy(entity: Entity) = entity.armor == null
    override fun Entity.equipName() = copy(armor = name)
}

private class Ring(
        override val name: String,
        override val price: Int,
        override val damagePoints: Int,
        override val armorPoints: Int
) : Item {
    override fun canBeEquippedBy(entity: Entity) = entity.rings.size < 2 && name !in entity.rings
    override fun Entity.equipName() = copy(rings = rings + name)
}

private val boss = Entity(104, 8, 1)

private inline fun <T : Item> String.prepare(factory: (String, Int, Int, Int) -> T)
        = splitLines().map {
    val (name, price, dmg, armor) = it.split(" +".toRegex())
    factory(name, price.toInt(), dmg.toInt(), armor.toInt())
}

private val weapons = """Dagger        8     4       0
Shortsword   10     5       0
Warhammer    25     6       0
Longsword    40     7       0
Greataxe     74     8       0""".prepare(::Weapon)

private val armor = """Leather      13     0       1
Chainmail    31     0       2
Splintmail   53     0       3
Bandedmail   75     0       4
Platemail   102     0       5""".prepare(::Armor)

private val rings = """Damage+1    25     1       0
Damage+2    50     2       0
Damage+3   100     3       0
Defense+1   20     0       1
Defense+2   40     0       2
Defense+3   80     0       3""".prepare(::Ring)

private val shop = weapons + armor + rings