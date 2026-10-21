package alexeyyuditsky.pricewatcher.navigation

internal data object ServicesDestination : AppDestination {

    private fun readResolve(): Any = ServicesDestination
}